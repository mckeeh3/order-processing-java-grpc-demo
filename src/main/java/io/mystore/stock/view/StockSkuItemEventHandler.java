package io.mystore.stock.view;

import io.mystore.TimeTo;
import io.mystore.stock.entity.StockSkuItemEntity;

class StockSkuItemEventHandler {

  static StockSkuItemsModel.StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockSkuItemCreated event) {
    return state
        .toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setStockOrderId(event.getStockOrderId())
        .build();
  }

  static StockSkuItemsModel.StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static StockSkuItemsModel.StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return state; // no change
  }

  static StockSkuItemsModel.StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static StockSkuItemsModel.StockSkuItem handle(StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return state
        .toBuilder()
        .setOrderId("")
        .setOrderSkuItemId("")
        .setShippedUtc(TimeTo.zero())
        .build();
  }
}
