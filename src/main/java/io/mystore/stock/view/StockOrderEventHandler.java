package io.mystore.stock.view;

import java.util.List;
import java.util.stream.Collectors;

import com.google.protobuf.Timestamp;

import io.mystore.TimeTo;
import io.mystore.stock.entity.StockOrderEntity;

class StockOrderEventHandler {

  private StockOrdersModel.StockOrder state;

  StockOrderEventHandler(StockOrdersModel.StockOrder state) {
    this.state = state;
  }

  static StockOrderEventHandler fromState(StockOrdersModel.StockOrder state) {
    return new StockOrderEventHandler(state);
  }

  StockOrdersModel.StockOrder toState() {
    return state;
  }

  StockOrderEventHandler handle(StockOrderEntity.StockOrderCreated stockOrderCreated) {
    state = state
        .toBuilder()
        .setStockOrderId(stockOrderCreated.getStockOrderId())
        .setSkuId(stockOrderCreated.getSkuId())
        .setSkuName(stockOrderCreated.getSkuName())
        .setQuantity(stockOrderCreated.getQuantity())
        .addAllStockSkuItems(toStockSkuItems(stockOrderCreated))
        .build();

    return this;
  }

  StockOrderEventHandler handle(StockOrderEntity.StockSkuItemJoined stockSkuItemJoined) {
    var stockSkuItems = toStockSkuItems(state, stockSkuItemJoined);

    state = state
        .toBuilder()
        .setShippedUtc(areAllItemsShipped(stockSkuItems))
        .clearStockSkuItems()
        .addAllStockSkuItems(stockSkuItems)
        .build();

    return this;
  }

  StockOrderEventHandler handle(StockOrderEntity.StockSkuItemReleased stockSkuItemReleased) {
    state = state
        .toBuilder()
        .clearStockSkuItems()
        .addAllStockSkuItems(toStockSkuItems(state, stockSkuItemReleased))
        .build();

    return this;
  }

  private List<StockSkuItemsModel.StockSkuItem> toStockSkuItems(StockOrderEntity.StockOrderCreated stockOrderCreated) {
    return stockOrderCreated.getStockSkuItemsList().stream()
        .map(stockSkuItem -> StockSkuItemsModel.StockSkuItem
            .newBuilder()
            .setStockSkuItemId(stockSkuItem.getStockSkuItemId())
            .setSkuId(stockSkuItem.getSkuId())
            .setSkuName(stockSkuItem.getSkuName())
            .setStockOrderId(stockSkuItem.getStockOrderId())
            .build())
        .collect(Collectors.toList());
  }

  static List<StockSkuItemsModel.StockSkuItem> toStockSkuItems(StockOrdersModel.StockOrder state, StockOrderEntity.StockSkuItemJoined event) {
    return state.getStockSkuItemsList().stream()
        .map(stockSkuItem -> {
          if (stockSkuItem.getStockSkuItemId().equals(event.getStockSkuItemId())) {
            return stockSkuItem.toBuilder()
                .setOrderId(event.getStockOrderId())
                .setOrderSkuItemId(event.getOrderSkuItemId())
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return stockSkuItem;
          }
        })
        .collect(Collectors.toList());
  }

  static List<StockSkuItemsModel.StockSkuItem> toStockSkuItems(StockOrdersModel.StockOrder state, StockOrderEntity.StockSkuItemReleased event) {
    return state.getStockSkuItemsList().stream()
        .map(stockSkuItem -> {
          if (stockSkuItem.getStockSkuItemId().equals(event.getStockSkuItemId())) {
            return stockSkuItem.toBuilder()
                .setOrderId("")
                .setOrderSkuItemId("")
                .setShippedUtc(TimeTo.zero())
                .build();
          } else {
            return stockSkuItem;
          }
        })
        .collect(Collectors.toList());
  }

  private Timestamp areAllItemsShipped(List<StockSkuItemsModel.StockSkuItem> stockSkuItems) {
    return stockSkuItems.stream()
        .filter(item -> item.getShippedUtc() == null || item.getShippedUtc().getSeconds() == 0)
        .findFirst()
        .isPresent() ? TimeTo.zero() : TimeTo.now();
  }
}
