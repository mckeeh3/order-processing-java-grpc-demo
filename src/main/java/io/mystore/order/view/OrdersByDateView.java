package io.mystore.order.view;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.order.entity.OrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrdersByDateView extends AbstractOrdersByDateView {

  public OrdersByDateView(ViewContext context) {
  }

  @Override
  public OrderModel.Order emptyState() {
    return OrderModel.Order.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<OrderModel.Order> onOrdered(OrderModel.Order state, OrderEntity.OrderCreated orderCreated) {
    return effects().updateState(
        state.toBuilder()
            .setOrderId(orderCreated.getOrderId())
            .setCustomerId(orderCreated.getCustomerId())
            .setOrderedUtc(orderCreated.getOrderedUtc())
            .clearOrderItems()
            .addAllOrderItems(toOrderItems(orderCreated.getOrderItemsList()))
            .build());
  }

  @Override
  public UpdateEffect<OrderModel.Order> onOrderShipped(OrderModel.Order state, OrderEntity.OrderShipped orderShipped) {
    return effects().updateState(
        state.toBuilder()
            .setShippedUtc(orderShipped.getShippedUtc())
            .build());
  }

  @Override
  public UpdateEffect<OrderModel.Order> onOrderItemShipped(OrderModel.Order state, OrderEntity.OrderItemShipped orderItemShipped) {
    var orderItems = state.getOrderItemsList().stream()
        .map(item -> {
          if (item.getSkuId().equals(orderItemShipped.getSkuId())) {
            return item.toBuilder()
                .setShippedUtc(orderItemShipped.getShippedUtc())
                .build();
          } else {
            return item;
          }
        })
        .collect(Collectors.toList());

    return effects().updateState(
        state.toBuilder()
            .clearOrderItems()
            .addAllOrderItems(orderItems)
            .build());
  }

  private List<OrderModel.OrderItem> toOrderItems(List<OrderEntity.OrderItem> orderItems) {
    return orderItems.stream()
        .map(lineItem -> OrderModel.OrderItem.newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public View.UpdateEffect<OrderModel.Order> ignoreOtherEvents(OrderModel.Order state, Any any) {
    return effects().ignore();
  }
}
