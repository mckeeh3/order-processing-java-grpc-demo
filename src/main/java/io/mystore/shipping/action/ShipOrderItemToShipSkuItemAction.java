package io.mystore.shipping.action;

import java.util.Random;
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
  static final Random random = new Random();

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
                .setSkuId(skuItemReleasedFromOrder.getSkuId())
                .setSkuItemId(skuItemReleasedFromOrder.getSkuItemId())
                .setOrderId(skuItemReleasedFromOrder.getOrderId())
                .setOrderItemId(skuItemReleasedFromOrder.getOrderItemId())
                .build())
            .execute());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private CompletionStage<Empty> onAvailableShipSkuItems(
      ShipOrderItemEntity.SkuItemRequired skuItemRequired, AvailableShipSkuItemsModel.GetAvailableShipSkuItemsResponse response) {
    var count = response.getShipSkuItemsCount();
    if (count > 0) {
      return requestShipSkuItem(skuItemRequired, response.getShipSkuItemsList().get(random.nextInt(count)));
    } else {
      return backOrderShipOrderItem(skuItemRequired);
    }
  }

  private CompletionStage<Empty> requestShipSkuItem(
      ShipOrderItemEntity.SkuItemRequired skuItemRequired, AvailableShipSkuItemsModel.ShipSkuItem shipSkuItem) {
    return components().shipSkuItem().joinToOrderItem(
        ShipSkuItemApi.JoinToOrderItemCommand
            .newBuilder()
            .setOrderId(skuItemRequired.getOrderId())
            .setOrderItemId(skuItemRequired.getOrderItemId())
            .setSkuItemId(shipSkuItem.getSkuItemId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> backOrderShipOrderItem(ShipOrderItemEntity.SkuItemRequired skuItemRequired) {
    return components().shipOrderItem().backOrderOrderItem(
        ShipOrderItemApi.BackOrderOrderItemCommand
            .newBuilder()
            .setOrderItemId(skuItemRequired.getOrderItemId())
            .build())
        .execute();
  }
}
