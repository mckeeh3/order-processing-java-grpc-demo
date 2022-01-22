package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.entity.ShipOrderItemEntity;
import io.mystore.shipping.view.BackOrderedShipOrderItemsModel.ShipOrderItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class BackOrderedShipOrderItemsView extends AbstractBackOrderedShipOrderItemsView {

  public BackOrderedShipOrderItemsView(ViewContext context) {
  }

  @Override
  public BackOrderedShipOrderItemsModel.ShipOrderItem emptyState() {
    return BackOrderedShipOrderItemsModel.ShipOrderItem.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<BackOrderedShipOrderItemsModel.ShipOrderItem> processOrderItemCreated(
      BackOrderedShipOrderItemsModel.ShipOrderItem state, ShipOrderItemEntity.OrderItemCreated orderItemCreated) {
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
  public View.UpdateEffect<BackOrderedShipOrderItemsModel.ShipOrderItem> processSkuItemAddedToOrder(
      BackOrderedShipOrderItemsModel.ShipOrderItem state, ShipOrderItemEntity.SkuItemAddedToOrder skuItemAddedToOrder) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setBackOrderedUtc(timestampZero())
                .build());
  }

  @Override
  public View.UpdateEffect<BackOrderedShipOrderItemsModel.ShipOrderItem> processOrderItemBackOrdered(
      BackOrderedShipOrderItemsModel.ShipOrderItem state, ShipOrderItemEntity.OrderItemBackOrdered orderItemBackOrdered) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setBackOrderedUtc(orderItemBackOrdered.getBackOrderedUtc())
                .build());
  }

  @Override
  public UpdateEffect<ShipOrderItem> ignoreOtherEvents(ShipOrderItem state, Any any) {
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
