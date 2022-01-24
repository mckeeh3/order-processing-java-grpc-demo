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

public class OrdersByCustomerByDateView extends AbstractOrdersByCustomerByDateView {

  public OrdersByCustomerByDateView(ViewContext context) {
  }

  @Override
  public OrderModel.Order emptyState() {
    return OrderModel.Order.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<OrderModel.Order> processOrdered(OrderModel.Order state, OrderEntity.OrderState orderState) {
    return effects().updateState(
        state.toBuilder()
            .setOrderId(orderState.getOrderId())
            .setCustomerId(orderState.getCustomerId())
            .setOrderedUtc(orderState.getOrderedUtc())
            .setShippedUtc(orderState.getShippedUtc())
            .setDeliveredUtc(orderState.getDeliveredUtc())
            .setReturnedUtc(orderState.getReturnedUtc())
            .setCanceledUtc(orderState.getCanceledUtc())
            .clearOrderItems()
            .addAllOrderItems(toOrderItems(orderState.getOrderItemsList()))
            .build());
  }

  private List<OrderModel.OrderItem> toOrderItems(List<OrderEntity.OrderItem> lineItems) {
    return lineItems.stream()
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
