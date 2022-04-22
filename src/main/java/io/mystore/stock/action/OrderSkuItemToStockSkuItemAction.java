package io.mystore.stock.action;

import java.util.Random;
import java.util.concurrent.CompletionStage;

import kalix.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.OrderSkuItemApi;
import io.mystore.shipping.entity.OrderSkuItemEntity;
import io.mystore.shipping.entity.OrderSkuItemEntity.OrderRequestedJoinToStockRejected;
import io.mystore.stock.api.StockSkuItemApi;
import io.mystore.stock.view.StockSkuItemsAvailableModel;
import io.mystore.stock.view.StockSkuItemsModel;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemToStockSkuItemAction extends AbstractOrderSkuItemToStockSkuItemAction {
  static final Random random = new Random();
  static final Logger log = LoggerFactory.getLogger(OrderSkuItemToStockSkuItemAction.class);

  public OrderSkuItemToStockSkuItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStock(OrderSkuItemEntity.OrderRequestedJoinToStock event) {
    log.info("onOrderRequestedJoinToStockStock: {}", event);

    return effects().asyncReply(queryAvailableStockItems(event));
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStockRejected(OrderSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    log.info("onOrderRequestedJoinToStockRejected: {}", event);

    return effects().forward(
        components().stockSkuItem().orderRequestsJoinToStockRejected(toOrderRequestedJoinToStockRejected(event)));
  }

  @Override
  public Effect<Empty> onStockRequestedJoinToOrderAccepted(OrderSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    log.info("onStockRequestedJoinToOrderAccepted: {}", event);

    return effects().forward(components().stockSkuItem()
        .stockRequestedJoinToOrderAccepted(toStockRequestedJoinToOrderAcceptedCommand(event)));
  }

  @Override
  public Effect<Empty> onStockRequestedJoinToOrderRejected(OrderSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    log.info("onStockRequestedJoinToOrderRejected: {}", event);

    return effects().forward(components().stockSkuItem()
        .stockRequestedJoinToOrderRejected(toStockRequestedJoinToOrderRejectedCommand(event)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private CompletionStage<Empty> queryAvailableStockItems(OrderSkuItemEntity.OrderRequestedJoinToStock event) {
    return components().stockSkuItemsAvailableView().getStockSkuItemsAvailable(
        StockSkuItemsAvailableModel.GetStockSkuItemsAvailableRequest
            .newBuilder()
            .setSkuId(event.getSkuId())
            .build())
        .execute()
        .thenCompose(response -> onAvailableShipSkuItems(event, response));
  }

  private CompletionStage<Empty> onAvailableShipSkuItems(OrderSkuItemEntity.OrderRequestedJoinToStock event,
      StockSkuItemsAvailableModel.GetStockSkuItemsAvailableResponse response) {
    var count = response.getStockSkuItemsCount();
    if (count > 0) {
      return orderRequestedJoinToStock(event, response.getStockSkuItemsList().get(random.nextInt(count)));
    } else {
      log.info("No stock sku items available to join to order sku item {} {}", event.getSkuId(),
          event.getOrderSkuItemId());
      return backOrderShipOrderItem(event);
    }
  }

  private CompletionStage<Empty> orderRequestedJoinToStock(OrderSkuItemEntity.OrderRequestedJoinToStock event,
      StockSkuItemsModel.StockSkuItem shipSkuItem) {
    return components().stockSkuItem().orderRequestsJoinToStock(
        StockSkuItemApi.OrderRequestsJoinToStockCommand
            .newBuilder()
            .setStockSkuItemId(shipSkuItem.getStockSkuItemId())
            .setSkuId(event.getSkuId())
            .setOrderId(event.getOrderId())
            .setOrderSkuItemId(event.getOrderSkuItemId())
            .setStockOrderId(shipSkuItem.getStockOrderId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> backOrderShipOrderItem(OrderSkuItemEntity.OrderRequestedJoinToStock event) {
    return components().orderSkuItem().backOrderOrderSkuItem(
        OrderSkuItemApi.BackOrderOrderSkuItemCommand
            .newBuilder()
            .setOrderId(event.getOrderId())
            .setOrderSkuItemId(event.getOrderSkuItemId())
            .build())
        .execute();
  }

  static StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand toOrderRequestedJoinToStockRejected(
      OrderRequestedJoinToStockRejected event) {
    return StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand
        .newBuilder()
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setSkuId(event.getSkuId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setStockOrderId(event.getStockOrderId())
        .build();
  }

  static StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand toStockRequestedJoinToOrderAcceptedCommand(
      OrderSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand
        .newBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setSkuId(event.getSkuId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .setStockOrderId(event.getStockOrderId())
        .build();
  }

  static StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand toStockRequestedJoinToOrderRejectedCommand(
      OrderSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand
        .newBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setSkuId(event.getSkuId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setStockOrderId(event.getStockOrderId())
        .build();
  }
}
