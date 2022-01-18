package io.mystore.order.entity;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import io.mystore.order.api.OrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class Order extends AbstractOrder {

  public Order(ValueEntityContext context) {
  }

  @Override
  public OrderEntity.OrderState emptyState() {
    return OrderEntity.OrderState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addOrder(OrderEntity.OrderState state, OrderApi.Order command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> shippedOrder(OrderEntity.OrderState state, OrderApi.ShippedOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> deliveredOrder(OrderEntity.OrderState state, OrderApi.DeliveredOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> returnedOrder(OrderEntity.OrderState state, OrderApi.ReturnedOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> canceledOrder(OrderEntity.OrderState state, OrderApi.CanceledOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.Order command) {
    return OrderEntity.OrderState
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setCustomerId(command.getCustomerId())
        .setOrderedUtc(command.getOrderedUtc())
        .clearLineItems()
        .addAllLineItems(toState(command.getLineItemsList()))
        .build();
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.ShippedOrderRequest command) {
    return state
        .toBuilder()
        .setShippedUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.DeliveredOrderRequest command) {
    return state
        .toBuilder()
        .setDeliveredUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.ReturnedOrderRequest command) {
    return state
        .toBuilder()
        .setReturnedUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.CanceledOrderRequest command) {
    return state
        .toBuilder()
        .setCanceledUtc(timestampNow())
        .build();
  }

  private List<OrderEntity.LineItem> toState(List<OrderApi.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> OrderEntity.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public Effect<OrderApi.Order> getOrder(OrderEntity.OrderState state, OrderApi.GetOrderRequest command) {
    return effects().reply(toApi(state));
  }

  private OrderApi.Order toApi(OrderEntity.OrderState state) {
    return OrderApi.Order
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setCustomerId(state.getCustomerId())
        .setOrderedUtc(state.getOrderedUtc())
        .setShippedUtc(state.getShippedUtc())
        .setDeliveredUtc(state.getDeliveredUtc())
        .setReturnedUtc(state.getReturnedUtc())
        .setCanceledUtc(state.getCanceledUtc())
        .addAllLineItems(toApi(state.getLineItemsList()))
        .build();
  }

  private List<OrderApi.LineItem> toApi(List<OrderEntity.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> OrderApi.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  static Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }
}
