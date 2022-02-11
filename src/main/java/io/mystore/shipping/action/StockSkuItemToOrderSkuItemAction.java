package io.mystore.shipping.action;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.OrderSkuItemApi;
import io.mystore.shipping.view.OrderSkuItemModel;
import io.mystore.shipping.view.OrderSkuItemsBackOrderedBySkuModel;
import io.mystore.stock.api.StockSkuItemApi;
import io.mystore.stock.entity.StockSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItemToOrderSkuItemAction extends AbstractStockSkuItemToOrderSkuItemAction {
  static final Random random = new Random();
  static final Logger log = LoggerFactory.getLogger(StockSkuItemToOrderSkuItemAction.class);

  public StockSkuItemToOrderSkuItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onStockSkuItemCreated(StockSkuItemEntity.StockSkuItemCreated stockSkuItemCreated) {
    log.info("onStockSkuItemCreated: {}", stockSkuItemCreated);

    return notifyStockSkuItemOfBackOrderedOrderItem(stockSkuItemCreated.getSkuId(), stockSkuItemCreated.getStockSkuItemId());
  }

  @Override
  public Effect<Empty> onJoinedToStockSkuItem(StockSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    return effects().forward(components().orderSkuItem().joinToStockSkuItem(toJoinToStockSkuItem(joinedToStockSkuItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private Effect<Empty> notifyStockSkuItemOfBackOrderedOrderItem(String skuId, String stockSkuItemId) {
    return effects().asyncReply(
        components().orderSkuItemsBackOrderedBySkuView().getOrderSkuItemsBackOrderedBySku(
            OrderSkuItemsBackOrderedBySkuModel.GetOrderSkuItemsBackOrderedBySkuRequest
                .newBuilder()
                .setSkuId(skuId)
                .build())
            .execute()
            .thenCompose(response -> joinBackOrderedToSkuItem(skuId, stockSkuItemId, response)));
  }

  private CompletionStage<Empty> joinBackOrderedToSkuItem(String skuId, String stockSkuItemId, OrderSkuItemsBackOrderedBySkuModel.GetOrderSkuItemsBackOrderedBySkuResponse response) {
    var count = response.getOrderSkuItemsCount();
    log.info("joinBackOrderedToSkuItem: skuId: {}, count: {}", skuId, count);

    if (count > 0) {
      return joinToStockSkuItem(stockSkuItemId, response.getOrderSkuItems(random.nextInt(count)));
    } else {
      return CompletableFuture.completedStage(Empty.getDefaultInstance());
    }
  }

  private CompletionStage<Empty> joinToStockSkuItem(String stockSkuItemId, OrderSkuItemModel.OrderSkuItem orderSkuItem) {
    return components().stockSkuItem().joinStockSkuItem(
        StockSkuItemApi.JoinStockSkuItemCommand
            .newBuilder()
            .setOrderId(orderSkuItem.getOrderId())
            .setOrderSkuItemId(orderSkuItem.getOrderSkuItemId())
            .setSkuId(orderSkuItem.getSkuId())
            .setStockSkuItemId(stockSkuItemId)
            .build())
        .execute();
  }

  static OrderSkuItemApi.JoinToStockSkuItemCommand toJoinToStockSkuItem(StockSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    return OrderSkuItemApi.JoinToStockSkuItemCommand
        .newBuilder()
        .setOrderId(joinedToStockSkuItem.getOrderId())
        .setOrderSkuItemId(joinedToStockSkuItem.getOrderSkuItemId())
        .setSkuId(joinedToStockSkuItem.getSkuId())
        .setStockSkuItemId(joinedToStockSkuItem.getStockSkuItemId())
        .setShippedUtc(joinedToStockSkuItem.getShippedUtc())
        .build();
  }
}
