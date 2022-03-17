package io.mystore.stock.view;

import java.util.List;

import com.google.protobuf.Timestamp;

import io.mystore.TimeTo;
import io.mystore.stock.entity.StockOrderEntity;

class StockOrderEventHandler {

  static StockOrdersModel.StockOrder handle(StockOrdersModel.StockOrder state, StockOrderEntity.StockOrderCreated stockOrderCreated) {
    return state
        .toBuilder()
        .setStockOrderId(stockOrderCreated.getStockOrderId())
        .setSkuId(stockOrderCreated.getSkuId())
        .setSkuName(stockOrderCreated.getSkuName())
        .setQuantity(stockOrderCreated.getQuantity())
        .addAllStockSkuItems(toStockSkuItems(stockOrderCreated))
        .build();
  }

  static StockOrdersModel.StockOrder handle(StockOrdersModel.StockOrder state, StockOrderEntity.StockSkuItemShipped stockSkuItemShipped) {
    var stockSkuItems = toStockSkuItems(state, stockSkuItemShipped);

    return state
        .toBuilder()
        .setShippedUtc(areAllItemsShipped(stockSkuItems))
        .clearStockSkuItems()
        .addAllStockSkuItems(stockSkuItems)
        .build();
  }

  static StockOrdersModel.StockOrder handle(StockOrdersModel.StockOrder state, StockOrderEntity.StockSkuItemReleased stockSkuItemReleased) {
    return state
        .toBuilder()
        .clearStockSkuItems()
        .addAllStockSkuItems(toStockSkuItems(state, stockSkuItemReleased))
        .build();
  }

  static List<StockSkuItemsModel.StockSkuItem> toStockSkuItems(StockOrderEntity.StockOrderCreated stockOrderCreated) {
    return stockOrderCreated.getStockSkuItemsList().stream()
        .map(stockSkuItem -> StockSkuItemsModel.StockSkuItem
            .newBuilder()
            .setStockSkuItemId(stockSkuItem.getStockSkuItemId())
            .setSkuId(stockSkuItem.getSkuId())
            .setSkuName(stockSkuItem.getSkuName())
            .setStockOrderId(stockSkuItem.getStockOrderId())
            .build())
        .toList();
  }

  static List<StockSkuItemsModel.StockSkuItem> toStockSkuItems(StockOrdersModel.StockOrder state, StockOrderEntity.StockSkuItemShipped event) {
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
        .toList();
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
        .toList();
  }

  static Timestamp areAllItemsShipped(List<StockSkuItemsModel.StockSkuItem> stockSkuItems) {
    return stockSkuItems.stream()
        .allMatch(item -> isShipped(item.getShippedUtc()))
            ? TimeTo.now()
            : TimeTo.zero();
  }

  static boolean isShipped(Timestamp shippedUtc) {
    return shippedUtc != null && shippedUtc.getSeconds() > 0;
  }
}
