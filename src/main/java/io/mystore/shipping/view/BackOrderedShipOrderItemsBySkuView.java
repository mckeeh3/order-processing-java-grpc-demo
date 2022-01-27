package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.entity.ShipOrderItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class BackOrderedShipOrderItemsBySkuView extends AbstractBackOrderedShipOrderItemsBySkuView {

  public BackOrderedShipOrderItemsBySkuView(ViewContext context) {
  }

  @Override
  public ShipOrderItemModel.ShipOrderItem emptyState() {
    return ShipOrderItemModel.ShipOrderItem.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> processOrderItemCreated(
      ShipOrderItemModel.ShipOrderItem state, ShipOrderItemEntity.OrderItemCreated orderItemCreated) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setOrderId(orderItemCreated.getOrderId())
                .setOrderItemId(orderItemCreated.getOrderItemId())
                .setSkuId(orderItemCreated.getSkuId())
                .setSkuName(orderItemCreated.getSkuName())
                .build());
  }

  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> processSkuItemAddedToOrder(
      ShipOrderItemModel.ShipOrderItem state, ShipOrderItemEntity.SkuItemAddedToOrder skuItemAddedToOrder) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setBackOrderedUtc(timestampZero())
                .build());
  }

  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> processOrderItemBackOrdered(
      ShipOrderItemModel.ShipOrderItem state, ShipOrderItemEntity.OrderItemBackOrdered orderItemBackOrdered) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setBackOrderedUtc(orderItemBackOrdered.getBackOrderedUtc())
                .build());
  }

  @Override
  public View.UpdateEffect<ShipOrderItemModel.ShipOrderItem> ignoreOtherEvents(
      ShipOrderItemModel.ShipOrderItem state, Any any) {
    return effects().ignore();
  }

  static Timestamp timestampZero() {
    return Timestamp
        .newBuilder()
        .setSeconds(0)
        .setNanos(0)
        .build();
  }
}
