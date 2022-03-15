package io.mystore.stock.view;

import io.mystore.TimeTo;
import io.mystore.stock.entity.StockSkuItemEntity;
import io.mystore.stock.view.StockSkuItemsModel.StockSkuItem;

class StockSkuItemEventHandler {

  public static StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockSkuItemCreated event) {
    return state
        .toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setStockOrderId(event.getStockOrderId())
        .build();
  }

  public static StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  public static StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return state
        .toBuilder()
        .setOrderId("")
        .setOrderSkuItemId("")
        .setShippedUtc(TimeTo.zero())
        .build();
  }

  public static StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  public static StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return state
        .toBuilder()
        .setOrderId("")
        .setOrderSkuItemId("")
        .setShippedUtc(TimeTo.zero())
        .build();
  }
}
