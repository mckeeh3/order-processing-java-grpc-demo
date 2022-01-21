package io.mystore.shipping.action;

import java.util.concurrent.CompletionStage;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.ShipOrderItemApi;
import io.mystore.shipping.api.ShipSkuItemApi;
import io.mystore.shipping.entity.ShipOrderItemEntity;
import io.mystore.shipping.view.AvailableShipSkuItemsModel;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItemToShipSkuItemAction extends AbstractShipOrderItemToShipSkuItemAction {

  public ShipOrderItemToShipSkuItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onShipSkuItemRequired(ShipOrderItemEntity.ShipSkuItemRequired shipSkuItemRequired) {
    return effects().asyncReply(
        components().availableShipSkuItemsView().getAvailableShipSkuItems(
            AvailableShipSkuItemsModel.GetAvailableShipSkuItemsRequest
                .newBuilder()
                .setSkuId(shipSkuItemRequired.getSkuId())
                .build())
            .execute()
            .thenCompose(response -> onAvailableShipSkuItems(shipSkuItemRequired, response)));
  }

  @Override
  public Effect<Empty> onSkuItemReleasedFromOrder(ShipOrderItemEntity.SkuItemReleasedFromOrder skuItemReleasedFromOrder) {
    return effects().asyncReply(
        components().shipSkuItem().releaseShipOrderItem(
            ShipSkuItemApi.ReleaseShipOrderItemFromSkuItem
                .newBuilder()
                .setSkuItemId(skuItemReleasedFromOrder.getSkuItemId())
                .build())
            .execute());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private CompletionStage<Empty> onAvailableShipSkuItems(
      ShipOrderItemEntity.ShipSkuItemRequired shipSkuItemRequired, AvailableShipSkuItemsModel.GetAvailableShipSkuItemsResponse response) {
    if (response.getShipSkuItemsCount() > 0) {
      return requestShipSkuItem(shipSkuItemRequired, response.getShipSkuItemsList().get(0));
    } else {
      return backOrderShipOrderItem(shipSkuItemRequired);
    }
  }

  private CompletionStage<Empty> requestShipSkuItem(
      ShipOrderItemEntity.ShipSkuItemRequired shipSkuItemRequired, AvailableShipSkuItemsModel.ShipSkuItem shipSkuItem) {
    return components().shipSkuItem().addShipOrderItem(
        ShipSkuItemApi.AddShipOrderItemToSkuItem
            .newBuilder()
            .setOrderId(shipSkuItemRequired.getOrderId())
            .setOrderItemId(shipSkuItemRequired.getOrderItemId())
            .setSkuItemId(shipSkuItem.getSkuItemId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> backOrderShipOrderItem(ShipOrderItemEntity.ShipSkuItemRequired shipSkuItemRequired) {
    return components().shipOrderItem().placeOnBackOrder(
        ShipOrderItemApi.BackOrderShipOrderItem
            .newBuilder()
            .setOrderItemId(shipSkuItemRequired.getOrderItemId())
            .build())
        .execute();
  }
}
