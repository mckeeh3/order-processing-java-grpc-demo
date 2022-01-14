package io.mystore.order.entity;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import io.mystore.order.api.OrderApi;
import io.mystore.order.api.OrderApi.DeleteOrder;
import io.mystore.order.api.OrderApi.DeliveredOrder;
import io.mystore.order.api.OrderApi.LineItem;
import io.mystore.order.api.OrderApi.ShippedOrder;
import io.mystore.order.entity.OrderEntity.OrderState;

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
  public Effect<Empty> shippedCart(OrderState state, ShippedOrder command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> deliveredCart(OrderState state, DeliveredOrder command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> deleteCart(OrderState state, DeleteOrder command) {
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
        .setShippedUtc(command.getShippedUtc())
        .setDeliveredUtc(command.getDeliveredUtc())
        .setDeletedUtc(command.getDeletedUtc())
        .clearLineItems()
        .addAllLineItems(toState(command.getLineItemsList()))
        .build();
  }

  private OrderEntity.OrderState updateState(OrderState state, ShippedOrder command) {
    return state
        .toBuilder()
        .setShippedUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderState state, DeliveredOrder command) {
    return state
        .toBuilder()
        .setDeliveredUtc(timestampNow())
        .build();
  }

  private OrderEntity.OrderState updateState(OrderState state, DeleteOrder command) {
    return state
        .toBuilder()
        .setDeletedUtc(timestampNow())
        .build();
  }

  private List<OrderEntity.LineItem> toState(List<LineItem> lineItems) {
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
        .setDeletedUtc(state.getDeletedUtc())
        .addAllLineItems(toApi(state.getLineItemsList()))
        .build();
  }

  private List<LineItem> toApi(List<OrderEntity.LineItem> lineItems) {
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
