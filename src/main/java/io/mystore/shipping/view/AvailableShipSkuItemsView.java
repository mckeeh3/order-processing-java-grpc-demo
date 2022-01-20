package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import io.mystore.shipping.entity.ShipSkuItemEntity;

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
                .setAvailableToShip(true)
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> processShipOrderItemAdded(
      AvailableShipSkuItemsModel.ShipSkuItem state, ShipSkuItemEntity.ShipOrderItemAdded shipOrderItemAdded) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setAvailableToShip(false)
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> processReleasedSkuItemFromOrder(
      AvailableShipSkuItemsModel.ShipSkuItem state, ShipSkuItemEntity.ReleasedSkuItemFromOrder releasedSkuItemFromOrder) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setAvailableToShip(true)
                .build());
  }
}
