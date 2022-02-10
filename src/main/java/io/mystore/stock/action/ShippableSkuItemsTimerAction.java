package io.mystore.stock.action;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.shipping2.entity.OrderSkuItemEntity;
import io.mystore.shipping2.entity.OrderSkuItemEntity.BackOrderedOrderSkuItem;
import io.mystore.shipping2.view.OrderSkuItemsBackOrderedBySkuModel.GetOrderSkuItemsBackOrderedBySkuRequest;
import io.mystore.shipping2.view.OrderSkuItemsBackOrderedBySkuModel.GetOrderSkuItemsBackOrderedBySkuResponse;
import io.mystore.shipping2.view.OrderSkuItemModel.OrderSkuItem;
import io.mystore.stock.api.ShippableSkuItemsTimerApi;
import io.mystore.stock.api.StockSkuItemApi;
import io.mystore.stock.api.ShippableSkuItemsTimerApi.CreateShippableSkuItemsTimerCommand;
import io.mystore.stock.entity.ShippableSkuItemsTimerEntity;
import io.mystore.stock.entity.StockSkuItemEntity;
import io.mystore.stock.entity.StockSkuItemEntity.ReleasedFromStockSkuItem;
import io.mystore.stock.view.StockSkuItemsAvailableModel;
import io.mystore.stock.view.StockSkuItemsAvailableModel.GetStockSkuItemsAvailableResponse;
import io.mystore.stock.view.StockSkuItemsModel.StockSkuItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippableSkuItemsTimerAction extends AbstractShippableSkuItemsTimerAction {
  static final Random random = new Random();
  static final Logger log = LoggerFactory.getLogger(ShippableSkuItemsTimerAction.class);

  public ShippableSkuItemsTimerAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onBackOrderedOrderSkuItem(OrderSkuItemEntity.BackOrderedOrderSkuItem backOrderedOrderSkuItem) {
    log.info("onBackOrderedOrderSkuItem: {}", backOrderedOrderSkuItem);

    return effects().forward(components().shippableSkuItemsTimer().createShippableSkuItemsTimer(toShippableSkuItemsTimer(backOrderedOrderSkuItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherOrderSkuItemEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> onReleasedFromStockSkuItem(StockSkuItemEntity.ReleasedFromStockSkuItem releasedFromStockSkuItem) {
    log.info("onReleasedFromStockSkuItem: {}", releasedFromStockSkuItem);

    return effects().forward(components().shippableSkuItemsTimer().createShippableSkuItemsTimer(toShippableSkuItemsTimer(releasedFromStockSkuItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherStockSkuItemEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> onPingShippableSkuItemsTimer(ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState shippableSkuItemsTimerState) {
    log.info("onPingShippableSkuItemsTimer: {}", shippableSkuItemsTimerState);

    return effects().asyncReply(queryStockSkuItemsAvailable(shippableSkuItemsTimerState.getSkuId()));
  }

  static ShippableSkuItemsTimerApi.CreateShippableSkuItemsTimerCommand toShippableSkuItemsTimer(BackOrderedOrderSkuItem backOrderedOrderSkuItem) {
    return ShippableSkuItemsTimerApi.CreateShippableSkuItemsTimerCommand
        .newBuilder()
        .setSkuId(backOrderedOrderSkuItem.getSkuId())
        .build();
  }

  static CreateShippableSkuItemsTimerCommand toShippableSkuItemsTimer(ReleasedFromStockSkuItem releasedFromStockSkuItem) {
    return ShippableSkuItemsTimerApi.CreateShippableSkuItemsTimerCommand
        .newBuilder()
        .setSkuId(releasedFromStockSkuItem.getSkuId())
        .build();
  }

  private CompletionStage<Empty> queryStockSkuItemsAvailable(String skuId) {
    return components().stockSkuItemsAvailableView().getStockSkuItemsAvailable(
        StockSkuItemsAvailableModel.GetStockSkuItemsAvailableRequest
            .newBuilder()
            .setSkuId(skuId)
            .build())
        .execute()
        .thenCompose(response -> queryOrderSkuItemsBackOrdered(skuId, response));
  }

  private CompletionStage<Empty> queryOrderSkuItemsBackOrdered(String skuId, GetStockSkuItemsAvailableResponse stockSkuItemsAvailable) {
    return components().orderSkuItemsBackOrderedBySkuView().getOrderSkuItemsBackOrderedBySku(
        GetOrderSkuItemsBackOrderedBySkuRequest
            .newBuilder()
            .setSkuId(skuId)
            .build())
        .execute()
        .thenCompose(response -> joinStockSkuItemsToOrderSkuItems(skuId, stockSkuItemsAvailable, response));
  }

  private CompletionStage<Empty> joinStockSkuItemsToOrderSkuItems(
      String skuId, GetStockSkuItemsAvailableResponse stockSkuItemsAvailable, GetOrderSkuItemsBackOrderedBySkuResponse orderSkuItemsBackOrdered) {
    var countStockSkuItemsAvailable = stockSkuItemsAvailable.getStockSkuItemsCount();
    var countOrderSkuItemsBackOrdered = orderSkuItemsBackOrdered.getOrderSkuItemsCount();

    log.info("skuId: {}, stock SKU items available: {}, order SKU items back ordered: {}", skuId, countStockSkuItemsAvailable, countOrderSkuItemsBackOrdered);

    var results = IntStream.range(0, Math.min(countStockSkuItemsAvailable, countOrderSkuItemsBackOrdered))
        .mapToObj(i -> joinStockSkuItemToOrderSkuItem(stockSkuItemsAvailable.getStockSkuItems(i), orderSkuItemsBackOrdered.getOrderSkuItems(i)))
        .collect(Collectors.toList());

    return CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .completeOnTimeout(null, 2, TimeUnit.SECONDS) // todo: make configurable
        .thenCompose(v -> pingBackOrderTimer(skuId));
  }

  private CompletionStage<Empty> joinStockSkuItemToOrderSkuItem(StockSkuItem stockSkuItem, OrderSkuItem orderSkuItem) {
    log.info("join stockSkuItem: {}\nto back ordered orderSkuItem: {}", stockSkuItem, orderSkuItem);

    return components().stockSkuItem().joinStockSkuItem(
        StockSkuItemApi.JoinStockSkuItemCommand
            .newBuilder()
            .setStockSkuItemId(stockSkuItem.getStockSkuItemId())
            .setSkuId(stockSkuItem.getSkuId())
            .setOrderId(orderSkuItem.getOrderId())
            .setOrderSkuItemId(orderSkuItem.getOrderSkuItemId())
            .setShippedUtc(TimeTo.now())
            .setStockOrderId(stockSkuItem.getStockOrderId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> pingBackOrderTimer(String skuId) {
    return components().shippableSkuItemsTimer().pingShippableSkuItemsTimer(
        ShippableSkuItemsTimerApi.PingShippableSkuItemsTimerCommand
            .newBuilder()
            .setSkuId(skuId)
            .build())
        .execute();
  }
}
