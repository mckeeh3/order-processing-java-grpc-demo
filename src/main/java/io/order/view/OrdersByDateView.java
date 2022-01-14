package io.order.view;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.order.entity.OrderEntity;
import io.order.view.OrderOuter.LineItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrdersByDateView extends AbstractOrdersByDateView {

  public OrdersByDateView(ViewContext context) {
  }

  @Override
  public OrderOuter.Order emptyState() {
    return OrderOuter.Order.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<OrderOuter.Order> processOrdered(OrderOuter.Order state, OrderEntity.OrderState orderState) {
    return effects().updateState(
        state.toBuilder()
            .setOrderId(orderState.getOrderId())
            .setCustomerId(orderState.getCustomerId())
            .setOrderedUtc(orderState.getOrderedUtc())
            .setShippedUtc(orderState.getShippedUtc())
            .setDeliveredUtc(orderState.getDeliveredUtc())
            .setDeletedUtc(orderState.getDeletedUtc())
            .clearLineItems()
            .addAllLineItems(toLineItems(orderState.getLineItemsList()))
            .build());
  }

  private List<LineItem> toLineItems(List<OrderEntity.LineItem> lineItems) {
    return lineItems.stream()
        .map(lineItem -> LineItem.newBuilder()
            .setProductId(lineItem.getProductId())
            .setProductName(lineItem.getProductName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public View.UpdateEffect<OrderOuter.Order> ignoreOtherEvents(OrderOuter.Order state, Any any) {
    return effects().ignore();
  }
}
