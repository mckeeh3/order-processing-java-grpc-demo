package io.mystore.stock.action;

import java.util.Random;
import java.util.concurrent.CompletionStage;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping2.api.OrderSkuItemApi;
import io.mystore.shipping2.entity.OrderSkuItemEntity;
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
  public Effect<Empty> onStockSkuItemRequired(OrderSkuItemEntity.StockSkuItemRequired stockSkuItemRequired) {
    return effects().asyncReply(
        components().stockSkuItemsAvailableView().getStockSkuItemsAvailable(
            StockSkuItemsAvailableModel.GetStockSkuItemsAvailableRequest
                .newBuilder()
                .setSkuId(stockSkuItemRequired.getSkuId())
                .build())
            .execute()
            .thenCompose(response -> onAvailableShipSkuItems(stockSkuItemRequired, response)));
  }

  @Override
  public Effect<Empty> onStockSkuItemReleased(OrderSkuItemEntity.ReleasedFromOrderSkuItem releasedFromOrderSkuItem) {
    return effects().asyncReply(
        components().stockSkuItem().releaseStockSkuItem(
            StockSkuItemApi.ReleaseStockSkuItemCommand
                .newBuilder()
                .setSkuId(releasedFromOrderSkuItem.getSkuId())
                .setStockSkuItemId(releasedFromOrderSkuItem.getStockSkuItemId())
                .setOrderId(releasedFromOrderSkuItem.getOrderId())
                .setOrderSkuItemId(releasedFromOrderSkuItem.getOrderSkuItemId())
                .build())
            .execute());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private CompletionStage<Empty> onAvailableShipSkuItems(
      OrderSkuItemEntity.StockSkuItemRequired stockSkuItemRequired, StockSkuItemsAvailableModel.GetStockSkuItemsAvailableResponse response) {
    var count = response.getStockSkuItemsCount();
    if (count > 0) {
      return requestShipSkuItem(stockSkuItemRequired, response.getStockSkuItemsList().get(random.nextInt(count)));
    } else {
      return backOrderShipOrderItem(stockSkuItemRequired);
    }
  }

  private CompletionStage<Empty> requestShipSkuItem(
      OrderSkuItemEntity.StockSkuItemRequired skuItemRequired, StockSkuItem shipSkuItem) {
    return components().stockSkuItem().joinStockSkuItem(
        StockSkuItemApi.JoinStockSkuItemCommand
            .newBuilder()
            .setOrderId(skuItemRequired.getOrderId())
            .setOrderSkuItemId(skuItemRequired.getOrderSkuItemId())
            .setStockSkuItemId(shipSkuItem.getStockSkuItemId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> backOrderShipOrderItem(OrderSkuItemEntity.StockSkuItemRequired skuItemRequired) {
    return components().orderSkuItem().backOrderOrderSkuItem(
        OrderSkuItemApi.BackOrderOrderSkuItemCommand
            .newBuilder()
            .setOrderId(skuItemRequired.getOrderId())
            .setOrderSkuItemId(skuItemRequired.getOrderSkuItemId())
            .build())
        .execute();
  }
}
