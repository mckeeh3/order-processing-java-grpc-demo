package io.mystore.shipping.action;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.ShipOrderItemApi;
import io.mystore.shipping.entity.ShipSkuItemEntity;
import io.mystore.shipping.view.BackOrderedShipOrderItemsModel;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItemToShipOrderItemAction extends AbstractShipSkuItemToShipOrderItemAction {

  public ShipSkuItemToShipOrderItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onSkuItemCreated(ShipSkuItemEntity.SkuItemCreated skuItemCreated) {
    return effects().asyncReply(
        components().backOrderedShipOrderItemsView().getBackOrderedShipOrderItems(
            BackOrderedShipOrderItemsModel.GetBackOrderedOrderItemsRequest
                .newBuilder()
                .build())
            .execute()
            .thenCompose(response -> sendStockAlertsToBackOrderedOrderItems(skuItemCreated, response)));
  }

  @Override
  public Effect<Empty> onShipOrderItemAdded(ShipSkuItemEntity.OrderItemAdded shipOrderItemAdded) {
    return effects().forward(components().shipOrderItem().addSkuItemToOrder(toSkuOrderItem(shipOrderItemAdded)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private CompletionStage<Empty> sendStockAlertsToBackOrderedOrderItems(
      ShipSkuItemEntity.SkuItemCreated skuItemCreated, BackOrderedShipOrderItemsModel.GetBackOrderedOrderItemsResponse response) {
    var results = response.getShipOrderItemsList().stream()
        .map(shipOrderItem -> components().shipOrderItem().stockAlert(stockAlertOrderItem(shipOrderItem)).execute())
        .collect(Collectors.toList());

    return CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> Empty.getDefaultInstance());
  }

  private ShipOrderItemApi.StockAlertOrderItem stockAlertOrderItem(BackOrderedShipOrderItemsModel.ShipOrderItem shipOrderItem) {
    return ShipOrderItemApi.StockAlertOrderItem
        .newBuilder()
        .setOrderItemId(shipOrderItem.getOrderItemId())
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
