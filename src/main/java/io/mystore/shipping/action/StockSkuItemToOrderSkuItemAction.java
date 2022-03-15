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
import io.mystore.shipping.view.OrderSkuItemsNotShippedBySkuModel;
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
  public Effect<Empty> onStockRequestedJoinToOrder(StockSkuItemEntity.StockRequestedJoinToOrder event) {
    return effects().asyncReply(queryNotShippedOrderSkuItems(event));
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStockAccepted(StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return effects().forward(components().orderSkuItem().orderRequestedJoinToStockAccepted(toOrderRequestedJoinToStockAccepted(event)));
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStockRejected(StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return effects().forward(components().orderSkuItem().orderRequestedJoinToStockRejected(toOrderRequestedJoinToStockRejected(event)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private CompletionStage<Empty> queryNotShippedOrderSkuItems(StockSkuItemEntity.StockRequestedJoinToOrder event) {
    return components().orderSkuItemsNotShippedBySkuView().getOrderSkuItemsNotShippedBySku(
        OrderSkuItemsNotShippedBySkuModel.GetOrderSkuItemsNotShippedBySkuRequest
            .newBuilder()
            .setSkuId(event.getSkuId())
            .build())
        .execute()
        .thenCompose(response -> onStockRequestedJoinToOrder(event, response));
  }

  private CompletionStage<Empty> onStockRequestedJoinToOrder(StockSkuItemEntity.StockRequestedJoinToOrder event, OrderSkuItemsNotShippedBySkuModel.GetOrderSkuItemsNotShippedBySkuResponse response) {
    var count = response.getOrderSkuItemsCount();
    if (count == 0) {
      return CompletableFuture.completedFuture(Empty.getDefaultInstance());
    } else {
      var orderSkuItem = response.getOrderSkuItems(random.nextInt(count));
      return components().orderSkuItem().stockRequestsJoinToOrder(toStockRequestsJoinToOrder(event, orderSkuItem)).execute();
    }
  }

  static OrderSkuItemApi.StockRequestsJoinToOrderCommand toStockRequestsJoinToOrder(StockSkuItemEntity.StockRequestedJoinToOrder event, OrderSkuItemModel.OrderSkuItem orderSkuItem) {
    return OrderSkuItemApi.StockRequestsJoinToOrderCommand
        .newBuilder()
        .setOrderSkuItemId(orderSkuItem.getOrderSkuItemId())
        .setOrderId(orderSkuItem.getOrderId())
        .setSkuId(event.getSkuId())
        .setStockSkuItemId(event.getStockSkuItemId())
        .build();
  }

  static OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand toOrderRequestedJoinToStockAccepted(StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand
        .newBuilder()
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setOrderId(event.getOrderId())
        .setSkuId(event.getSkuId())
        .setStockSkuItemId(event.getStockSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand toOrderRequestedJoinToStockRejected(StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand
        .newBuilder()
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setOrderId(event.getOrderId())
        .setSkuId(event.getSkuId())
        .setStockSkuItemId(event.getStockSkuItemId())
        .build();
  }
}
