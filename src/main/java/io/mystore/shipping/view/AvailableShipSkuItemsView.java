package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.entity.ShipSkuItemEntity;
import io.mystore.shipping.view.AvailableShipSkuItemsModel.ShipSkuItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class AvailableShipSkuItemsView extends AbstractAvailableShipSkuItemsView {

  public AvailableShipSkuItemsView(ViewContext context) {
  }

  @Override
  public AvailableShipSkuItemsModel.ShipSkuItem emptyState() {
    return AvailableShipSkuItemsModel.ShipSkuItem.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> processSkuItemCreated(
      AvailableShipSkuItemsModel.ShipSkuItem state, ShipSkuItemEntity.SkuItemCreated skuItemCreated) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setSkuItemId(skuItemCreated.getSkuItemId())
                .setSkuId(skuItemCreated.getSkuId())
                .setSkuName(skuItemCreated.getSkuName())
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> processShipOrderItemAdded(
      AvailableShipSkuItemsModel.ShipSkuItem state, ShipSkuItemEntity.OrderItemAdded shipOrderItemAdded) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setShippedUtc(shipOrderItemAdded.getShippedUtc())
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> processReleasedSkuItemFromOrder(
      AvailableShipSkuItemsModel.ShipSkuItem state, ShipSkuItemEntity.ReleasedSkuItemFromOrder releasedSkuItemFromOrder) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setShippedUtc(timestampZero())
                .build());
  }

  @Override
  public UpdateEffect<ShipSkuItem> ignoreOtherEvents(ShipSkuItem state, Any any) {
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
