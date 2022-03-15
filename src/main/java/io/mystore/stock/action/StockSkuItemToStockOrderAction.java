package io.mystore.stock.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.stock.api.StockOrderApi;
import io.mystore.stock.entity.StockSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItemToStockOrderAction extends AbstractStockSkuItemToStockOrderAction {
  static final Logger log = LoggerFactory.getLogger(StockSkuItemToStockOrderAction.class);

  public StockSkuItemToStockOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStockAccepted(StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    log.info("onOrderRequestedJoinToStockAccepted: {}", event);

    return effects().forward(components().stockOrder().joinStockSkuItem(toStockOrder(event)));
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStockRejected(StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    log.info("onOrderRequestedJoinToStockRejected: {}", event);

    return effects().forward(components().stockOrder().releaseStockSkuItem(toStockOrder(event)));
  }

  @Override
  public Effect<Empty> onStockRequestedJoinToOrderAccepted(StockSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    log.info("onStockRequestedJoinToOrderAccepted: {}", event);

    return effects().forward(components().stockOrder().joinStockSkuItem(toStockOrder(event)));
  }

  @Override
  public Effect<Empty> onStockRequestedJoinToOrderRejected(StockSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    log.info("onStockRequestedJoinToOrderRejected: {}", event);

    return effects().forward(components().stockOrder().releaseStockSkuItem(toStockOrder(event)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  static StockOrderApi.JoinStockSkuItemToStockOrderCommand toStockOrder(StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return StockOrderApi.JoinStockSkuItemToStockOrderCommand
        .newBuilder()
        .setStockOrderId(event.getStockOrderId())
        .setSkuId(event.getSkuId())
        .setStockSkuItemId(event.getStockSkuItemId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static StockOrderApi.JoinStockSkuItemToStockOrderCommand toStockOrder(StockSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return StockOrderApi.JoinStockSkuItemToStockOrderCommand
        .newBuilder()
        .setStockOrderId(event.getStockOrderId())
        .setSkuId(event.getSkuId())
        .setStockSkuItemId(event.getStockSkuItemId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand toStockOrder(StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand
        .newBuilder()
        .setStockOrderId(event.getStockOrderId())
        .setSkuId(event.getSkuId())
        .setStockSkuItemId(event.getStockSkuItemId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .build();
  }

  static StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand toStockOrder(StockSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand
        .newBuilder()
        .setStockOrderId(event.getStockOrderId())
        .setSkuId(event.getSkuId())
        .setStockSkuItemId(event.getStockSkuItemId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .build();
  }
}
