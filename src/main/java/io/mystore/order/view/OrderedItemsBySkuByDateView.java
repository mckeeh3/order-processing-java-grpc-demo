package io.mystore.order.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.order.entity.OrderItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderedItemsBySkuByDateView extends AbstractOrderedItemsBySkuByDateView {

  public OrderedItemsBySkuByDateView(ViewContext context) {}

  @Override
  public OrderItemModel.OrderedItem emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state");
  }

  @Override
  public View.UpdateEffect<OrderItemModel.OrderedItem> onOrdered(
    OrderItemModel.OrderedItem state, OrderItemEntity.OrderItemState orderItemState) {
    throw new UnsupportedOperationException("Update handler for 'OnOrdered' not implemented yet");
  }
  @Override
  public View.UpdateEffect<OrderItemModel.OrderedItem> ignoreOtherEvents(
    OrderItemModel.OrderedItem state, Any any) {
    throw new UnsupportedOperationException("Update handler for 'IgnoreOtherEvents' not implemented yet");
  }
}

