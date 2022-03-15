package io.mystore.shipping.entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.shipping.api.ShippingApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class Shipping extends AbstractShipping {
  static final Logger log = LoggerFactory.getLogger(Shipping.class);

  public Shipping(EventSourcedEntityContext context) {
  }

  @Override
  public ShippingEntity.OrderState emptyState() {
    return ShippingEntity.OrderState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createOrder(ShippingEntity.OrderState state, ShippingApi.CreateOrderCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> shippedOrderSkuItem(ShippingEntity.OrderState state, ShippingApi.ShippedOrderSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<ShippingApi.Order> getOrder(ShippingEntity.OrderState state, ShippingApi.GetOrderRequest request) {
    return reject(state, request).orElseGet(() -> handle(state, request));
  }

  @Override
  public ShippingEntity.OrderState orderCreated(ShippingEntity.OrderState state, ShippingEntity.OrderCreated event) {
    return updateState(state, event);
  }

  @Override
  public ShippingEntity.OrderState orderShipped(ShippingEntity.OrderState state, ShippingEntity.OrderShipped event) {
    return updateState(state, event);
  }

  @Override
  public ShippingEntity.OrderState orderItemShipped(ShippingEntity.OrderState state, ShippingEntity.OrderItemShipped event) {
    return updateState(state, event);
  }

  @Override
  public ShippingEntity.OrderState orderSkuItemShipped(ShippingEntity.OrderState state, ShippingEntity.OrderSkuItemShipped event) {
    return updateStateFor(state, event);
  }

  private Optional<Effect<ShippingApi.Order>> reject(ShippingEntity.OrderState state, ShippingApi.GetOrderRequest request) {
    if (state.getOrderId().isEmpty()) {
      return Optional.of(effects().error(String.format("Order for order-id '%s' does not exist", request.getOrderId())));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(ShippingEntity.OrderState state, ShippingApi.CreateOrderCommand command) {
    log.info("state: {}, CreateOrderCommand {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShippingEntity.OrderState state, ShippingApi.ShippedOrderSkuItemCommand command) {
    log.info("state: {}, ShippedOrderSkuItemCommand {}", state, command);

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<ShippingApi.Order> handle(ShippingEntity.OrderState state, ShippingApi.GetOrderRequest request) {
    return effects().reply(
        ShippingApi.Order
            .newBuilder()
            .setOrderId(state.getOrderId())
            .setCustomerId(state.getCustomerId())
            .setOrderedUtc(state.getOrderedUtc())
            .setShippedUtc(state.getShippedUtc())
            .addAllOrderItems(toApi(state.getOrderItemsList()))
            .build());
  }

  static ShippingEntity.OrderCreated eventFor(ShippingEntity.OrderState state, ShippingApi.CreateOrderCommand command) {
    return ShippingEntity.OrderCreated
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setCustomerId(command.getCustomerId())
        .setOrderedUtc(command.getOrderedUtc())
        .addAllOrderItems(toEntityOrderItems(state, command))
        .build();
  }

  static List<?> eventsFor(ShippingEntity.OrderState state, ShippingApi.ShippedOrderSkuItemCommand command) {
    var orderSkuItemShipped = ShippingEntity.OrderSkuItemShipped
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setSkuId(command.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setShippedUtc(command.getShippedUtc())
        .build();

    var updatedState = updateStateFor(state, orderSkuItemShipped);

    var isOrderItemShipped = areAllOrderSkuItemsShipped(updatedState, command.getSkuId());
    var isOrderShipped = areAllOrderItemsShipped(updatedState);

    if (isOrderItemShipped && isOrderShipped) {
      var orderItemShipped = ShippingEntity.OrderItemShipped
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setSkuId(command.getSkuId())
          .setShippedUtc(command.getShippedUtc())
          .build();

      var orderShipped = ShippingEntity.OrderShipped
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setShippedUtc(command.getShippedUtc())
          .build();

      return List.of(orderSkuItemShipped, orderItemShipped, orderShipped);
    } else if (isOrderItemShipped) {
      var orderItemShipped = ShippingEntity.OrderItemShipped
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setSkuId(command.getSkuId())
          .setShippedUtc(command.getShippedUtc())
          .build();

      return List.of(orderSkuItemShipped, orderItemShipped);
    } else {
      return List.of(orderSkuItemShipped);
    }
  }

  static ShippingEntity.OrderState updateState(ShippingEntity.OrderState state, ShippingEntity.OrderCreated event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setCustomerId(event.getCustomerId())
        .setOrderedUtc(event.getOrderedUtc())
        .addAllOrderItems(event.getOrderItemsList())
        .build();
  }

  static ShippingEntity.OrderState updateState(ShippingEntity.OrderState state, ShippingEntity.OrderShipped event) {
    return state
        .toBuilder()
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static ShippingEntity.OrderState updateState(ShippingEntity.OrderState state, ShippingEntity.OrderItemShipped event) {
    var orderItems = state.getOrderItemsList().stream()
        .map(orderItem -> {
          if (orderItem.getSkuId().equals(event.getSkuId())) {
            return orderItem
                .toBuilder()
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return orderItem;
          }
        })
        .toList();

    return state
        .toBuilder()
        .clearOrderItems()
        .addAllOrderItems(orderItems)
        .build();
  }

  static ShippingEntity.OrderState updateStateFor(ShippingEntity.OrderState state, ShippingEntity.OrderSkuItemShipped event) {
    return state
        .toBuilder()
        .clearOrderItems()
        .addAllOrderItems(updateOrderItems(state.getOrderItemsList(), event))
        .build();
  }

  static List<ShippingEntity.OrderItem> updateOrderItems(List<ShippingEntity.OrderItem> orderItems, ShippingEntity.OrderSkuItemShipped event) {
    return orderItems.stream()
        .map(orderItem -> {
          if (orderItem.getSkuId().equals(event.getSkuId())) {
            return updateOrderSkuItems(orderItem, event);
          } else {
            return orderItem;
          }
        })
        .toList();
  }

  static ShippingEntity.OrderItem updateOrderSkuItems(ShippingEntity.OrderItem orderItem, ShippingEntity.OrderSkuItemShipped event) {
    var updatedOrderSkuItems = orderItem.getOrderSkuItemsList().stream()
        .map(orderSkuItem -> {
          if (orderSkuItem.getOrderSkuItemId().equals(event.getOrderSkuItemId())) {
            return orderSkuItem.toBuilder()
                .setStockSkuItemId(event.getStockSkuItemId())
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return orderSkuItem;
          }
        })
        .toList();

    return orderItem.toBuilder()
        .setShippedUtc(updateShippedUtc(updatedOrderSkuItems, event))
        .clearOrderSkuItems()
        .addAllOrderSkuItems(updatedOrderSkuItems)
        .build();
  }

  static Timestamp updateShippedUtc(List<ShippingEntity.OrderSkuItem> updatedOrderSkuItems, ShippingEntity.OrderSkuItemShipped event) {
    return updatedOrderSkuItems.stream()
        .anyMatch(orderSkuItem -> isNotShipped(orderSkuItem.getShippedUtc()))
            ? TimeTo.zero()
            : event.getShippedUtc();
  }

  static boolean areAllOrderSkuItemsShipped(ShippingEntity.OrderState state, String skuId) {
    return state.getOrderItemsList().stream()
        .filter(orderItem -> orderItem.getSkuId().equals(skuId))
        .flatMap(orderItem -> orderItem.getOrderSkuItemsList().stream())
        .allMatch(orderSkuItem -> orderSkuItem.getSkuId().equals(skuId) && isShipped(orderSkuItem.getShippedUtc()));
  }

  static boolean areAllOrderItemsShipped(ShippingEntity.OrderState state) {
    return state.getOrderItemsList().stream()
        .flatMap(orderItem -> orderItem.getOrderSkuItemsList().stream())
        .allMatch(orderSkuItem -> isShipped(orderSkuItem.getShippedUtc()));
  }

  static List<ShippingEntity.OrderItem> toEntityOrderItems(ShippingEntity.OrderState state, ShippingApi.CreateOrderCommand command) {
    return command.getOrderItemsList().stream()
        .map(orderItem -> ShippingEntity.OrderItem
            .newBuilder()
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .addAllOrderSkuItems(toEntityOrderSkuItems(state, command, orderItem))
            .build())
        .toList();
  }

  static List<ShippingEntity.OrderSkuItem> toEntityOrderSkuItems(ShippingEntity.OrderState state, ShippingApi.CreateOrderCommand command, ShippingApi.OrderItem orderItem) {
    return IntStream.range(0, orderItem.getQuantity())
        .mapToObj(i -> ShippingEntity.OrderSkuItem
            .newBuilder()
            .setCustomerId(command.getCustomerId())
            .setOrderId(command.getOrderId())
            .setOrderSkuItemId(UUID.randomUUID().toString())
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setOrderedUtc(command.getOrderedUtc())
            .build())
        .toList();
  }

  static List<ShippingApi.OrderItem> toApi(List<ShippingEntity.OrderItem> orderItems) {
    return orderItems.stream()
        .map(orderItem -> ShippingApi.OrderItem
            .newBuilder()
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .setShippedUtc(orderItem.getShippedUtc())
            .addAllOrderSkuItems(toApiOrderSkuItems(orderItem.getOrderSkuItemsList()))
            .build())
        .toList();
  }

  static List<ShippingApi.OrderSkuItem> toApiOrderSkuItems(List<ShippingEntity.OrderSkuItem> orderSkuItems) {
    return orderSkuItems.stream()
        .map(orderSkuItem -> ShippingApi.OrderSkuItem
            .newBuilder()
            .setCustomerId(orderSkuItem.getCustomerId())
            .setOrderId(orderSkuItem.getOrderId())
            .setOrderSkuItemId(orderSkuItem.getOrderSkuItemId())
            .setSkuId(orderSkuItem.getSkuId())
            .setSkuName(orderSkuItem.getSkuName())
            .setStockSkuItemId(orderSkuItem.getStockSkuItemId())
            .setOrderedUtc(orderSkuItem.getOrderedUtc())
            .setShippedUtc(orderSkuItem.getShippedUtc())
            .build())
        .toList();
  }

  static boolean isShipped(Timestamp shippedUtc) {
    return shippedUtc != null && shippedUtc.getSeconds() > 0;
  }

  static boolean isNotShipped(Timestamp shippedUtc) {
    return shippedUtc == null || shippedUtc.getSeconds() == 0;
  }
}
