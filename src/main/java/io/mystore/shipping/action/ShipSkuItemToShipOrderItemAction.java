package io.mystore.shipping.action;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShipOrderItemApi;
import io.mystore.shipping.entity.ShipSkuItemEntity;
import io.mystore.shipping.view.BackOrderedShipOrderItemsBySkuModel;
import io.mystore.shipping.view.ShipOrderItemModel;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItemToShipOrderItemAction extends AbstractShipSkuItemToShipOrderItemAction {
  static final Logger log = LoggerFactory.getLogger(ShipSkuItemToShipOrderItemAction.class);

  public ShipSkuItemToShipOrderItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onSkuItemCreated(ShipSkuItemEntity.SkuItemCreated skuItemCreated) {
    return notifyBackOrderedOfAvailableSkuItem(skuItemCreated.getSkuId());
  }

  @Override
  public Effect<Empty> onShipOrderItemAdded(ShipSkuItemEntity.OrderItemAdded shipOrderItemAdded) {
    return effects().forward(components().shipOrderItem().addSkuItemToOrder(toSkuOrderItem(shipOrderItemAdded)));
  }

  @Override
  public Effect<Empty> onReleaseOrderItemFromSkuItem(ShipSkuItemEntity.ReleasedSkuItemFromOrder releasedSkuItemFromOrder) {
    return notifyBackOrderedOfAvailableSkuItem(releasedSkuItemFromOrder.getSkuId());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private Effect<Empty> notifyBackOrderedOfAvailableSkuItem(String skuId) {
    return effects().asyncReply(
        components().backOrderedShipOrderItemsBySkuView().getBackOrderedShipOrderItemsBySku(
            BackOrderedShipOrderItemsBySkuModel.GetBackOrderedOrderItemsBySkuRequest
                .newBuilder()
                .setSkuId(skuId)
                .build())
            .execute()
            .thenCompose(response -> sendStockAlertsToBackOrderedOrderItems(response)));
  }

  private CompletionStage<Empty> sendStockAlertsToBackOrderedOrderItems(BackOrderedShipOrderItemsBySkuModel.GetBackOrderedOrderItemsBySkuResponse response) {
    var results = response.getShipOrderItemsList().stream()
        .map(shipOrderItem -> components().shipOrderItem().stockAlert(stockAlertOrderItem(shipOrderItem)).execute())
        .collect(Collectors.toList());

    return CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> Empty.getDefaultInstance());
  }

  private ShipOrderItemApi.StockAlertOrderItem stockAlertOrderItem(ShipOrderItemModel.ShipOrderItem shipOrderItem) {
    return ShipOrderItemApi.StockAlertOrderItem
        .newBuilder()
        .setOrderItemId(shipOrderItem.getOrderItemId())
        .setSkuId(shipOrderItem.getSkuId())
        .build();
  }

  private ShipOrderItemApi.SkuOrderItem toSkuOrderItem(ShipSkuItemEntity.OrderItemAdded shipOrderItemAdded) {
    return ShipOrderItemApi.SkuOrderItem
        .newBuilder()
        .setOrderItemId(shipOrderItemAdded.getOrderItemId())
        .setSkuItemId(shipOrderItemAdded.getSkuItemId())
        .setShippedUtc(shipOrderItemAdded.getShippedUtc())
        .build();
  }
}
