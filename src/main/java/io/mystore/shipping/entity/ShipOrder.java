package io.mystore.shipping.entity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

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
  public Effect<Empty> addShipOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.AddShipOrderCommand command) {
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
  public ShipOrderEntity.ShipOrderState orderItemShipped(ShipOrderEntity.ShipOrderState state, ShipOrderEntity.OrderItemShipped event) {
    return state
        .toBuilder()
        .clearShipOrderItems()
        .addAllShipOrderItems(
            state.getShipOrderItemsList().stream()
                .map(shipOrderItem -> shipOrderItem
                    .toBuilder()
                    .setShippedUtc(event.getShippedUtc())
                    .build())
                .collect(Collectors.toList()))
        .build();
  }

  private Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.AddShipOrderCommand command) {
    log.info("ShipOrder state: {}, AddShipOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderItemCommand command) {
    log.info("ShipOrder state: {}, ShippedOrderItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<ShipOrderApi.ShipOrder> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.GetShipOrderRequest command) {
    return effects().reply(toApi(state));
  }

  private ShipOrderEntity.ShipOrderCreated eventFor(ShipOrderEntity.ShipOrderState state, ShipOrderApi.AddShipOrderCommand command) {
    return ShipOrderEntity.ShipOrderCreated
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setCustomerId(command.getCustomerId())
        .setOrderedUtc(command.getOrderedUtc())
        .addAllOrderItems(toAllOrderItems(command.getOrderItemsList()))
        .addAllShipOrderItems(toAllShipOrderItems(command.getOrderItemsList()))
        .build();
  }

  private ShipOrderEntity.OrderItemShipped eventFor(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderItemCommand command) {
    return ShipOrderEntity.OrderItemShipped
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .setSkuItemId(command.getSkuItemId())
        .setShippedUtc(command.getShippedUtc())
        .build();
  }

  private List<ShipOrderEntity.OrderItem> toAllOrderItems(List<ShipOrderApi.OrderItemFromOrder> orderItems) {
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

  private List<ShipOrderEntity.ShipOrderItems> toAllShipOrderItems(List<ShipOrderApi.OrderItemFromOrder> orderItems) {
    return orderItems.stream()
        .map(item -> ShipOrderEntity.ShipOrderItems
            .newBuilder()
            .setSkuId(item.getSkuId())
            .setSkuName(item.getSkuName())
            .setQuantity(item.getQuantity())
            .setShippedUtc(item.getShippedUtc())
            .addAllShipOrderItems(toAllEntityShipOrderItems(orderItems))
            .build())
        .collect(Collectors.toList());
  }

  private List<ShipOrderEntity.ShipOrderItem> toAllEntityShipOrderItems(List<ShipOrderApi.OrderItemFromOrder> orderItems) {
    return orderItems.stream()
        .flatMap(orderItem -> toShipOrderItems(orderItem))
        .collect(Collectors.toList());
  }

  private Stream<ShipOrderEntity.ShipOrderItem> toShipOrderItems(ShipOrderApi.OrderItemFromOrder orderItem) {
    return IntStream.range(0, orderItem.getQuantity())
        .mapToObj(i -> ShipOrderEntity.ShipOrderItem
            .newBuilder()
            .setOrderItemId(UUID.randomUUID().toString())
            .setSkuId(orderItem.getSkuId())
            .setShippedUtc(orderItem.getShippedUtc())
            .build());
  }

  private ShipOrderApi.ShipOrder toApi(ShipOrderEntity.ShipOrderState state) {
    return ShipOrderApi.ShipOrder
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setCustomerId(state.getCustomerId())
        .setOrderedUtc(state.getOrderedUtc())
        .addAllOrderItems(toOrderItems(state.getOrderItemsList()))
        .addAllShipOrderItems(toShipOrderItems(state.getShipOrderItemsList()))
        .build();
  }

  private List<ShipOrderApi.OrderItemFromOrder> toOrderItems(List<ShipOrderEntity.OrderItem> orderItems) {
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

  private List<ShipOrderApi.ShipOrderItems> toShipOrderItems(List<ShipOrderEntity.ShipOrderItems> shipOrderItems) {
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

  private List<ShipOrderApi.ShipOrderItem> toShipOrderItemsList(List<ShipOrderEntity.ShipOrderItem> shipOrderItems) {
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
}
