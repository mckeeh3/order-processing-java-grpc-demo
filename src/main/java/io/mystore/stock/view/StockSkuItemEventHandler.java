package io.mystore.stock.view;

import io.mystore.TimeTo;
import io.mystore.stock.entity.StockSkuItemEntity;
import io.mystore.stock.view.StockSkuItemsModel.StockSkuItem;

class StockSkuItemEventHandler {
  private StockSkuItemsModel.StockSkuItem state;

  StockSkuItemEventHandler(StockSkuItem state) {
    this.state = state;
  }

  static StockSkuItemEventHandler fromState(StockSkuItem state) {
    return new StockSkuItemEventHandler(state);
  }

  StockSkuItem toState() {
    return state;
  }

  StockSkuItemEventHandler handle(StockSkuItemEntity.StockSkuItemCreated stockSkuItemCreated) {
    state = state
        .toBuilder()
        .setStockSkuItemId(stockSkuItemCreated.getStockSkuItemId())
        .setSkuId(stockSkuItemCreated.getSkuId())
        .setSkuName(stockSkuItemCreated.getSkuName())
        .setStockOrderId(stockSkuItemCreated.getStockOrderId())
        .build();

    return this;
  }

  StockSkuItemEventHandler handle(StockSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    state = state
        .toBuilder()
        .setOrderId(joinedToStockSkuItem.getOrderId())
        .setOrderSkuItemId(joinedToStockSkuItem.getOrderSkuItemId())
        .setShippedUtc(joinedToStockSkuItem.getShippedUtc())
        .build();

    return this;
  }

  StockSkuItemEventHandler handle(StockSkuItemEntity.ReleasedFromStockSkuItem releasedFromStockSkuItem) {
    state = state
        .toBuilder()
        .setOrderId("")
        .setOrderSkuItemId("")
        .setShippedUtc(TimeTo.zero())
        .build();

    return this;
  }
}
