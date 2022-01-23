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
  public Effect<Empty> onShipSkuItemRequired(ShipOrderItemEntity.SkuItemRequired skuItemRequired) {
    return effects().asyncReply(
        components().availableShipSkuItemsView().getAvailableShipSkuItems(
            AvailableShipSkuItemsModel.GetAvailableShipSkuItemsRequest
                .newBuilder()
                .setSkuId(skuItemRequired.getSkuId())
                .build())
            .execute()
            .thenCompose(response -> onAvailableShipSkuItems(skuItemRequired, response)));
  }

  @Override
  public Effect<Empty> onSkuItemReleasedFromOrder(ShipOrderItemEntity.SkuItemReleasedFromOrder skuItemReleasedFromOrder) {
    return effects().asyncReply(
        components().shipSkuItem().releaseOrderItem(
            ShipSkuItemApi.ReleaseOrderItemFromSkuItem
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
      ShipOrderItemEntity.SkuItemRequired skuItemRequired, AvailableShipSkuItemsModel.GetAvailableShipSkuItemsResponse response) {
    if (response.getShipSkuItemsCount() > 0) {
      return requestShipSkuItem(skuItemRequired, response.getShipSkuItemsList().get(0));
    } else {
      return backOrderShipOrderItem(skuItemRequired);
    }
  }

  private CompletionStage<Empty> requestShipSkuItem(
      ShipOrderItemEntity.SkuItemRequired skuItemRequired, AvailableShipSkuItemsModel.ShipSkuItem shipSkuItem) {
    return components().shipSkuItem().addOrderItem(
        ShipSkuItemApi.AddOrderItemToSkuItem
            .newBuilder()
            .setOrderId(skuItemRequired.getOrderId())
            .setOrderItemId(skuItemRequired.getOrderItemId())
            .setSkuItemId(shipSkuItem.getSkuItemId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> backOrderShipOrderItem(ShipOrderItemEntity.SkuItemRequired skuItemRequired) {
    return components().shipOrderItem().placeOnBackOrder(
        ShipOrderItemApi.BackOrderOrderItem
            .newBuilder()
            .setOrderItemId(skuItemRequired.getOrderItemId())
            .build())
        .execute();
  }
}
