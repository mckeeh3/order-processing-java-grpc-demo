package io.mystore.order.view;

import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.order.entity.OrderItemEntity;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderedItemsBySkuByDateView extends AbstractOrderedItemsBySkuByDateView {

  public OrderedItemsBySkuByDateView(ViewContext context) {
  }

  @Override
  public OrderItemModel.OrderedItem emptyState() {
    return OrderItemModel.OrderedItem.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<OrderItemModel.OrderedItem> onOrdered(OrderItemModel.OrderedItem state,
      OrderItemEntity.OrderItemState orderItemState) {
    return effects().updateState(
        state.toBuilder()
            .setCustomerId(orderItemState.getCustomerId())
            .setOrderId(orderItemState.getOrderId())
            .setSkuId(orderItemState.getSkuId())
            .setSkuName(orderItemState.getSkuName())
            .setQuantity(orderItemState.getQuantity())
            .setOrderedUtc(orderItemState.getOrderedUtc())
            .setShippedUtc(orderItemState.getShippedUtc())
            .build());
  }

  @Override
  public View.UpdateEffect<OrderItemModel.OrderedItem> ignoreOtherEvents(OrderItemModel.OrderedItem state, Any any) {
    return effects().ignore();
  }
}
