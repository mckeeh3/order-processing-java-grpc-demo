package io.mystore.shipping.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShipOrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrder extends AbstractShipOrder {
  static final Logger log = LoggerFactory.getLogger(ShipOrder.class);

  public ShipOrder(EventSourcedEntityContext context) {
  }

  @Override
  public ShipOrderEntity.ShipOrderState emptyState() {
    return ShipOrderEntity.ShipOrderState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createShipOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.CreateShipOrderCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> shippedOrderItem(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<ShipOrderApi.ShipOrder> getShipOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.GetShipOrderRequest command) {
    return handle(state, command);
  }

  @Override
  public ShipOrderEntity.ShipOrderState shipOrderCreated(ShipOrderEntity.ShipOrderState state, ShipOrderEntity.ShipOrderCreated event) {
    return state
        .toBuilder()
        .setCustomerId(event.getCustomerId())
        .setOrderId(event.getOrderId())
        .setOrderedUtc(event.getOrderedUtc())
        .addAllOrderItems(event.getOrderItemsList())
        .addAllShipOrderItems(event.getShipOrderItemsList())
        .build();
  }

  @Override
  public ShipOrderEntity.ShipOrderState orderShipped(ShipOrderEntity.ShipOrderState state, ShipOrderEntity.OrderShipped event) {
    return state
        .toBuilder()
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  @Override
  public ShipOrderEntity.ShipOrderState orderSkuShipped(ShipOrderEntity.ShipOrderState state, ShipOrderEntity.OrderSkuShipped event) {
    return updateStateFor(state, event);
  }

  @Override
  public ShipOrderEntity.ShipOrderState orderItemShipped(ShipOrderEntity.ShipOrderState state, ShipOrderEntity.OrderItemShipped event) {
    return updateStateFor(state, event);
  }

  Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.CreateShipOrderCommand command) {
    log.info("state: {}, CreateShipOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderItemCommand command) {
    log.info("state: {}, ShippedOrderItemCommand: {}", state, command);

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  Effect<ShipOrderApi.ShipOrder> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.GetShipOrderRequest command) {
    return effects().reply(toApi(state));
  }

  static ShipOrderEntity.ShipOrderCreated eventFor(ShipOrderEntity.ShipOrderState state, ShipOrderApi.CreateShipOrderCommand command) {
    return ShipOrderEntity.ShipOrderCreated
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setCustomerId(command.getCustomerId())
        .setOrderedUtc(command.getOrderedUtc())
        .addAllOrderItems(toAllOrderItems(command.getOrderItemsList()))
        .addAllShipOrderItems(toAllShipOrderItems(command.getOrderItemsList()))
        .build();
  }

  static List<?> eventsFor(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderItemCommand command) {
    var orderItemShipped = ShipOrderEntity.OrderItemShipped
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .setSkuId(command.getSkuId())
        .setSkuItemId(command.getSkuItemId())
        .setShippedUtc(command.getShippedUtc())
        .build();

    var updatedState = updateStateFor(state, orderItemShipped);

    var isSkuItemShipped = areAllSkuOrderItemsShipped(updatedState, command.getSkuId());
    var isOrderShipped = areAllOrderItemsShipped(updatedState);

    if (isSkuItemShipped && isOrderShipped) {
      var orderSkuShipped = ShipOrderEntity.OrderSkuShipped
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setSkuId(command.getSkuId())
          .setShippedUtc(command.getShippedUtc())
          .build();

      var orderShipped = ShipOrderEntity.OrderShipped
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setShippedUtc(command.getShippedUtc())
          .build();

      return List.of(orderItemShipped, orderSkuShipped, orderShipped); // order of events is important
    } else if (isSkuItemShipped) {
      var orderSkuShipped = ShipOrderEntity.OrderSkuShipped
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setSkuId(command.getSkuId())
          .setShippedUtc(command.getShippedUtc())
          .build();

      return List.of(orderItemShipped, orderSkuShipped); // order of events is important
    } else if (isOrderShipped) {
      var orderShipped = ShipOrderEntity.OrderShipped
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setShippedUtc(command.getShippedUtc())
          .build();

      return List.of(orderItemShipped, orderShipped); // order of events is important
    } else {
      return List.of(orderItemShipped);
    }
  }

  static List<ShipOrderEntity.OrderItem> toAllOrderItems(List<ShipOrderApi.OrderItemFromOrder> orderItems) {
    return orderItems.stream()
        .map(item -> ShipOrderEntity.OrderItem
            .newBuilder()
            .setSkuId(item.getSkuId())
            .setSkuName(item.getSkuName())
            .setQuantity(item.getQuantity())
            .setShippedUtc(item.getShippedUtc())
            .build())
        .collect(Collectors.toList());
  }

  static List<ShipOrderEntity.ShipOrderItems> toAllShipOrderItems(List<ShipOrderApi.OrderItemFromOrder> orderItems) {
    return orderItems.stream()
        .map(item -> ShipOrderEntity.ShipOrderItems
            .newBuilder()
            .setSkuId(item.getSkuId())
            .setSkuName(item.getSkuName())
            .setQuantity(item.getQuantity())
            .setShippedUtc(item.getShippedUtc())
            .addAllShipOrderItems(toAllEntityShipOrderItems(item))
            .build())
        .collect(Collectors.toList());
  }

  static List<ShipOrderEntity.ShipOrderItem> toAllEntityShipOrderItems(ShipOrderApi.OrderItemFromOrder orderItem) {
    return toShipOrderItems(orderItem)
        .collect(Collectors.toList());
  }

  static Stream<ShipOrderEntity.ShipOrderItem> toShipOrderItems(ShipOrderApi.OrderItemFromOrder orderItem) {
    return IntStream.range(0, orderItem.getQuantity())
        .mapToObj(i -> ShipOrderEntity.ShipOrderItem
            .newBuilder()
            .setOrderItemId(UUID.randomUUID().toString())
            .setSkuId(orderItem.getSkuId())
            .setShippedUtc(orderItem.getShippedUtc())
            .build());
  }

  static ShipOrderApi.ShipOrder toApi(ShipOrderEntity.ShipOrderState state) {
    return ShipOrderApi.ShipOrder
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setCustomerId(state.getCustomerId())
        .setOrderedUtc(state.getOrderedUtc())
        .setShippedUtc(state.getShippedUtc())
        .addAllOrderItems(toOrderItems(state.getOrderItemsList()))
        .addAllShipOrderItems(toShipOrderItems(state.getShipOrderItemsList()))
        .build();
  }

  static List<ShipOrderApi.OrderItemFromOrder> toOrderItems(List<ShipOrderEntity.OrderItem> orderItems) {
    return orderItems.stream()
        .map(item -> ShipOrderApi.OrderItemFromOrder
            .newBuilder()
            .setSkuId(item.getSkuId())
            .setSkuName(item.getSkuName())
            .setQuantity(item.getQuantity())
            .setShippedUtc(item.getShippedUtc())
            .build())
        .collect(Collectors.toList());
  }

  static List<ShipOrderApi.ShipOrderItems> toShipOrderItems(List<ShipOrderEntity.ShipOrderItems> shipOrderItems) {
    return shipOrderItems.stream()
        .map(item -> ShipOrderApi.ShipOrderItems
            .newBuilder()
            .setSkuId(item.getSkuId())
            .setSkuName(item.getSkuName())
            .setQuantity(item.getQuantity())
            .setShippedUtc(item.getShippedUtc())
            .addAllShipOrderItems(toShipOrderItemsList(item.getShipOrderItemsList()))
            .build())
        .collect(Collectors.toList());
  }

  static List<ShipOrderApi.ShipOrderItem> toShipOrderItemsList(List<ShipOrderEntity.ShipOrderItem> shipOrderItems) {
    return shipOrderItems.stream()
        .map(item -> ShipOrderApi.ShipOrderItem
            .newBuilder()
            .setSkuId(item.getSkuId())
            .setSkuItemId(item.getSkuItemId())
            .setOrderItemId(item.getOrderItemId())
            .setShippedUtc(item.getShippedUtc())
            .build())
        .collect(Collectors.toList());
  }

  static ShipOrderEntity.ShipOrderState updateStateFor(ShipOrderEntity.ShipOrderState state, ShipOrderEntity.OrderSkuShipped event) {
    var orderItemsList = state.getOrderItemsList().stream()
        .map(item -> {
          if (item.getSkuId().equals(event.getSkuId())) {
            return item.toBuilder()
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return item;
          }
        })
        .collect(Collectors.toList());
    var shipOrderItemsList = state.getShipOrderItemsList().stream()
        .map(item -> {
          if (item.getSkuId().equals(event.getSkuId())) {
            return item.toBuilder()
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return item;
          }
        })
        .collect(Collectors.toList());
    return state.toBuilder()
        .clearOrderItems()
        .addAllOrderItems(orderItemsList)
        .clearShipOrderItems()
        .addAllShipOrderItems(shipOrderItemsList)
        .build();
  }

  static ShipOrderEntity.ShipOrderState updateStateFor(ShipOrderEntity.ShipOrderState state, ShipOrderEntity.OrderItemShipped event) {
    var updatedShipOrderItemsList = updateShipOrderItemsList(state.getShipOrderItemsList(), event);

    return state
        .toBuilder()
        .clearShipOrderItems()
        .addAllShipOrderItems(updatedShipOrderItemsList)
        .build();
  }

  static List<ShipOrderEntity.ShipOrderItems> updateShipOrderItemsList(List<ShipOrderEntity.ShipOrderItems> shipOrderItemsList, ShipOrderEntity.OrderItemShipped event) {
    return shipOrderItemsList.stream()
        .map(shipOrderItems -> {
          if (shipOrderItems.getSkuId().equals(event.getSkuId())) {
            return updateShipOrderItemList(shipOrderItems, event);
          } else {
            return shipOrderItems;
          }
        })
        .collect(Collectors.toList());
  }

  static ShipOrderEntity.ShipOrderItems updateShipOrderItemList(ShipOrderEntity.ShipOrderItems shipOrderItems, ShipOrderEntity.OrderItemShipped event) {
    var updatedList = shipOrderItems.getShipOrderItemsList().stream()
        .map(shipOrderItem -> {
          if (shipOrderItem.getOrderItemId().equals(event.getOrderItemId())) {
            return shipOrderItem.toBuilder()
                .setSkuItemId(event.getSkuItemId())
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return shipOrderItem;
          }
        })
        .collect(Collectors.toList());

    return shipOrderItems.toBuilder()
        .setShippedUtc(updateShippedUtc(updatedList, shipOrderItems.getShippedUtc(), event.getShippedUtc()))
        .clearShipOrderItems()
        .addAllShipOrderItems(updatedList)
        .build();
  }

  static Timestamp updateShippedUtc(List<ShipOrderEntity.ShipOrderItem> updatedList, Timestamp notShippedUtc, Timestamp shippedUtc) {
    var notAllShipped = updatedList.stream()
        .filter(shipOrderItem -> isNotShipped(shipOrderItem.getShippedUtc()))
        .findFirst()
        .isPresent();
    if (notAllShipped) {
      return notShippedUtc;
    } else {
      return shippedUtc;
    }
  }

  static boolean areAllOrderItemsShipped(ShipOrderEntity.ShipOrderState state) {
    return state.getShipOrderItemsList().stream()
        .flatMap(mapper -> mapper.getShipOrderItemsList().stream())
        .filter(item -> isNotShipped(item.getShippedUtc()))
        .findFirst()
        .isEmpty();
  }

  static boolean areAllSkuOrderItemsShipped(ShipOrderEntity.ShipOrderState state, String skuId) {
    return state.getShipOrderItemsList().stream()
        .flatMap(mapper -> mapper.getShipOrderItemsList().stream())
        .filter(item -> item.getSkuId().equals(skuId) && isNotShipped(item.getShippedUtc()))
        .findFirst()
        .isEmpty();
  }

  static Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }

  static Timestamp timestampZero() {
    return Timestamp
        .newBuilder()
        .setSeconds(0)
        .setNanos(0)
        .build();
  }

  static boolean isShipped(Timestamp shippedUtc) {
    return shippedUtc != null && shippedUtc.getSeconds() != 0;
  }

  static boolean isNotShipped(Timestamp shippedUtc) {
    return shippedUtc == null || shippedUtc.getSeconds() == 0;
  }
}
