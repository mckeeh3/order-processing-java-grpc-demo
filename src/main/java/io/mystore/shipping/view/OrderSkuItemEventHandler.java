package io.mystore.shipping.view;

import io.mystore.TimeTo;
import io.mystore.shipping.entity.OrderSkuItemEntity;

class OrderSkuItemEventHandler {

  public static OrderSkuItemModel.OrderSkuItem handle(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.OrderSkuItemCreated event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .build();
  }

  public static OrderSkuItemModel.OrderSkuItem handle(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return state
        .toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .setBackOrderedUtc(TimeTo.zero())
        .build();
  }

  public static OrderSkuItemModel.OrderSkuItem handle(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return state
        .toBuilder()
        .setStockSkuItemId("")
        .setShippedUtc(TimeTo.zero())
        .setBackOrderedUtc(TimeTo.zero())
        .build();
  }

  public static OrderSkuItemModel.OrderSkuItem handle(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return state
        .toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .setBackOrderedUtc(TimeTo.zero())
        .build();
  }

  public static OrderSkuItemModel.OrderSkuItem handle(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return state
        .toBuilder()
        .setStockSkuItemId("")
        .setShippedUtc(TimeTo.zero())
        .setBackOrderedUtc(TimeTo.zero())
        .build();
  }

  public static OrderSkuItemModel.OrderSkuItem handle(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.BackOrderedOrderSkuItem event) {
    return state
        .toBuilder()
        .setBackOrderedUtc(event.getBackOrderedUtc())
        .build();
  }
}
