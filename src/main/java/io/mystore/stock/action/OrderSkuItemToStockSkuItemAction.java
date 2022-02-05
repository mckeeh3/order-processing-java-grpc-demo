package io.mystore.stock.action;

import java.util.Random;
import java.util.concurrent.CompletionStage;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.ShipOrderItemApi;
import io.mystore.shipping.entity.ShipOrderItemEntity;
import io.mystore.stock.api.StockSkuItemApi;
import io.mystore.stock.view.StockSkuItemsAvailableModel;
import io.mystore.stock.view.StockSkuItemsModel.StockSkuItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemToStockSkuItemAction extends AbstractOrderSkuItemToStockSkuItemAction {
  static final Random random = new Random();

  public OrderSkuItemToStockSkuItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onStockSkuItemRequired(ShipOrderItemEntity.SkuItemRequired skuItemRequired) {
    return effects().asyncReply(
        components().stockSkuItemsAvailableView().getStockSkuItemsAvailable(
            StockSkuItemsAvailableModel.GetStockSkuItemsAvailableRequest
                .newBuilder()
                .setSkuId(skuItemRequired.getSkuId())
                .build())
            .execute()
            .thenCompose(response -> onAvailableShipSkuItems(skuItemRequired, response)));
  }

  @Override
  public Effect<Empty> onStockSkuItemReleased(ShipOrderItemEntity.SkuItemReleasedFromOrder skuItemReleasedFromOrder) {
    return effects().asyncReply(
        components().stockSkuItem().releaseStockSkuItem(
            StockSkuItemApi.ReleaseStockSkuItemCommand
                .newBuilder()
                .setSkuId(skuItemReleasedFromOrder.getSkuId())
                .setSkuItemId(skuItemReleasedFromOrder.getSkuItemId())
                .setOrderId(skuItemReleasedFromOrder.getOrderId())
                .setOrderSkuItemId(skuItemReleasedFromOrder.getOrderItemId())
                .build())
            .execute());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private CompletionStage<Empty> onAvailableShipSkuItems(
      ShipOrderItemEntity.SkuItemRequired skuItemRequired, StockSkuItemsAvailableModel.GetStockSkuItemsAvailableResponse response) {
    var count = response.getStockSkuItemsCount();
    if (count > 0) {
      return requestShipSkuItem(skuItemRequired, response.getStockSkuItemsList().get(random.nextInt(count)));
    } else {
      return backOrderShipOrderItem(skuItemRequired);
    }
  }

  private CompletionStage<Empty> requestShipSkuItem(
      ShipOrderItemEntity.SkuItemRequired skuItemRequired, StockSkuItem shipSkuItem) {
    return components().stockSkuItem().joinStockSkuItem(
        StockSkuItemApi.JoinStockSkuItemCommand
            .newBuilder()
            .setOrderId(skuItemRequired.getOrderId())
            .setOrderSkuItemId(skuItemRequired.getOrderItemId())
            .setSkuItemId(shipSkuItem.getSkuItemId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> backOrderShipOrderItem(ShipOrderItemEntity.SkuItemRequired skuItemRequired) {
    return components().shipOrderItem().backOrderOrderItem(
        ShipOrderItemApi.BackOrderOrderItemCommand
            .newBuilder()
            .setOrderId(skuItemRequired.getOrderId())
            .setOrderItemId(skuItemRequired.getOrderItemId())
            .build())
        .execute();
  }
}
