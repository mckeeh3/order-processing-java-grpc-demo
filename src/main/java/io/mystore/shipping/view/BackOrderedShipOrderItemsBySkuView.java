package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.shipping.entity.ShipOrderItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class BackOrderedShipOrderItemsBySkuView extends AbstractBackOrderedShipOrderItemsBySkuView {

  public BackOrderedShipOrderItemsBySkuView(ViewContext context) {}

  @Override
  public ShipOrderItemModel.ShipOrderItem emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state");
  }

  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> processOrderItemCreated(
    ShipOrderItemModel.ShipOrderItem state, ShipOrderItemEntity.OrderItemCreated orderItemCreated) {
    throw new UnsupportedOperationException("Update handler for 'ProcessOrderItemCreated' not implemented yet");
  }
  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> processSkuItemAddedToOrder(
    ShipOrderItemModel.ShipOrderItem state, ShipOrderItemEntity.SkuItemAddedToOrder skuItemAddedToOrder) {
    throw new UnsupportedOperationException("Update handler for 'ProcessSkuItemAddedToOrder' not implemented yet");
  }
  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> processOrderItemBackOrdered(
    ShipOrderItemModel.ShipOrderItem state, ShipOrderItemEntity.OrderItemBackOrdered orderItemBackOrdered) {
    throw new UnsupportedOperationException("Update handler for 'ProcessOrderItemBackOrdered' not implemented yet");
  }
  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> ignoreOtherEvents(
    ShipOrderItemModel.ShipOrderItem state, Any any) {
    throw new UnsupportedOperationException("Update handler for 'IgnoreOtherEvents' not implemented yet");
  }
}

