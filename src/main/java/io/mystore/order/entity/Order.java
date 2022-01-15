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
  public Effect<Empty> shippedCart(OrderEntity.OrderState state, OrderApi.ShippedOrder command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> deliveredCart(OrderEntity.OrderState state, OrderApi.DeliveredOrder command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> returnedCart(OrderEntity.OrderState state, OrderApi.ReturnedOrder command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> canceledCart(OrderEntity.OrderState state, OrderApi.CanceledOrder command) {
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

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.ShippedOrder command) {
    return state
        .toBuilder()
        .setShippedUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.DeliveredOrder command) {
    return state
        .toBuilder()
        .setDeliveredUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.ReturnedOrder command) {
    return state
        .toBuilder()
        .setReturnedUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderEntity.OrderState state, OrderApi.CanceledOrder command) {
    return state
        .toBuilder()
        .setCanceledUtc(timestampNow())
        .build();
  }

  private List<OrderEntity.LineItem> toState(List<OrderApi.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> OrderEntity.LineItem
            .newBuilder()
            .setProductId(lineItem.getProductId())
            .setProductName(lineItem.getProductName())
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
            .setProductId(lineItem.getProductId())
            .setProductName(lineItem.getProductName())
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
