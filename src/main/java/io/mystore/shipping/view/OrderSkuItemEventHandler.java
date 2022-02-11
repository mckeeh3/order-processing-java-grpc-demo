package io.mystore.shipping.view;

import io.mystore.TimeTo;
import io.mystore.shipping.entity.OrderSkuItemEntity;
import io.mystore.shipping.view.OrderSkuItemModel.OrderSkuItem;

class OrderSkuItemEventHandler {
  private OrderSkuItemModel.OrderSkuItem state;

  public OrderSkuItemEventHandler(OrderSkuItem state) {
    this.state = state;
  }

  static OrderSkuItemEventHandler fromState(OrderSkuItemModel.OrderSkuItem state) {
    return new OrderSkuItemEventHandler(state);
  }

  OrderSkuItemModel.OrderSkuItem toState() {
    return state;
  }

  OrderSkuItemEventHandler handle(OrderSkuItemEntity.CreatedOrderSkuItem createdOrderSkuItem) {
    state = state
        .toBuilder()
        .setOrderId(createdOrderSkuItem.getOrderId())
        .setOrderSkuItemId(createdOrderSkuItem.getOrderSkuItemId())
        .setSkuId(createdOrderSkuItem.getSkuId())
        .setSkuName(createdOrderSkuItem.getSkuName())
        .build();

    return this;
  }

  OrderSkuItemEventHandler handle(OrderSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    state = state
        .toBuilder()
        .setStockSkuItemId(joinedToStockSkuItem.getStockSkuItemId())
        .setShippedUtc(joinedToStockSkuItem.getShippedUtc())
        .setBackOrderedUtc(TimeTo.zero())
        .build();

    return this;
  }

  OrderSkuItemEventHandler handle(OrderSkuItemEntity.BackOrderedOrderSkuItem backOrderedOrderSkuItem) {
    state = state
        .toBuilder()
        .setBackOrderedUtc(backOrderedOrderSkuItem.getBackOrderedUtc())
        .build();

    return this;
  }
}
