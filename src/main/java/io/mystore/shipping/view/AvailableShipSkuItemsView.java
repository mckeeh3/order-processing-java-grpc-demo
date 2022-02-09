package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.TimeTo;
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
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> onSkuItemCreated(
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
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> onJoinedToOrderItem(
      AvailableShipSkuItemsModel.ShipSkuItem state, ShipSkuItemEntity.JoinedToOrderItem joinedToOrderItem) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setShippedUtc(joinedToOrderItem.getShippedUtc())
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableShipSkuItemsModel.ShipSkuItem> onReleasedFromOrderItem(
      AvailableShipSkuItemsModel.ShipSkuItem state, ShipSkuItemEntity.ReleasedFromOrderItem releasedFromOrderItem) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setShippedUtc(TimeTo.zero())
                .build());
  }

  @Override
  public UpdateEffect<ShipSkuItem> ignoreOtherEvents(ShipSkuItem state, Any any) {
    return effects().ignore();
  }
}
