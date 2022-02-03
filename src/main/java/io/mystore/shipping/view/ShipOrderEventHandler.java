package io.mystore.shipping.view;

import java.util.List;
import java.util.stream.Collectors;

import io.mystore.shipping.entity.ShipOrderEntity;

class ShipOrderEventHandler {
  ShipOrderModel.ShipOrder state;

  ShipOrderEventHandler(ShipOrderModel.ShipOrder state) {
    this.state = state;
  }

  static ShipOrderEventHandler fromState(ShipOrderModel.ShipOrder state) {
    return new ShipOrderEventHandler(state);
  }

  ShipOrderModel.ShipOrder toState() {
    return state;
  }

  ShipOrderEventHandler handle(ShipOrderEntity.ShipOrderCreated shipOrderCreated) {
    state = state.toBuilder()
        .setCustomerId(shipOrderCreated.getCustomerId())
        .setOrderId(shipOrderCreated.getOrderId())
        .setOrderedUtc(shipOrderCreated.getOrderedUtc())
        .addAllOrderItems(shipOrderCreated.getOrderItemsList())
        .addAllShipOrderItems(shipOrderCreated.getShipOrderItemsList())
        .build();

    return this;
  }

  ShipOrderEventHandler handle(ShipOrderEntity.OrderShipped orderShipped) {
    state = state.toBuilder()
        .setShippedUtc(orderShipped.getShippedUtc())
        .build();

    return this;
  }

  ShipOrderEventHandler handle(ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    state = state.toBuilder()
        .clearOrderItems()
        .addAllOrderItems(updateOrderItemsShippedSku(state, orderSkuShipped))
        .clearShipOrderItems()
        .addAllShipOrderItems(updateShipOrderItemsShippedSku(state, orderSkuShipped))
        .build();

    return this;
  }

  ShipOrderEventHandler handle(ShipOrderEntity.OrderItemShipped orderItemShipped) {
    state = state.toBuilder()
        .clearShipOrderItems()
        .addAllShipOrderItems(updateShipOrderItemsShippedItem(state, orderItemShipped))
        .build();

    return this;
  }

  static List<ShipOrderEntity.OrderItem> updateOrderItemsShippedSku(ShipOrderModel.ShipOrder state, ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    return state.getOrderItemsList().stream()
        .map(item -> {
          if (item.getSkuId().equals(orderSkuShipped.getSkuId())) {
            return item.toBuilder()
                .setShippedUtc(orderSkuShipped.getShippedUtc())
                .build();
          } else {
            return item;
          }
        })
        .collect(Collectors.toList());
  }

  static List<ShipOrderEntity.ShipOrderItems> updateShipOrderItemsShippedSku(ShipOrderModel.ShipOrder state, ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    return state.getShipOrderItemsList().stream()
        .map(item -> {
          if (item.getSkuId().equals(orderSkuShipped.getSkuId())) {
            return item.toBuilder()
                .setShippedUtc(orderSkuShipped.getShippedUtc())
                .build();
          } else {
            return item;
          }
        })
        .collect(Collectors.toList());
  }

  static List<ShipOrderEntity.ShipOrderItems> updateShipOrderItemsShippedItem(ShipOrderModel.ShipOrder state, ShipOrderEntity.OrderItemShipped orderItemShipped) {
    return state.getShipOrderItemsList().stream()
        .map(skuItem -> {
          if (skuItem.getSkuId().equals(orderItemShipped.getSkuId())) {
            return skuItem.toBuilder()
                .clearShipOrderItems()
                .addAllShipOrderItems(updateShipOrderItemsShippedItem(skuItem.getShipOrderItemsList(), orderItemShipped))
                .build();
          } else {
            return skuItem;
          }
        })
        .collect(Collectors.toList());
  }

  static List<ShipOrderEntity.ShipOrderItem> updateShipOrderItemsShippedItem(List<ShipOrderEntity.ShipOrderItem> shipOrderItems, ShipOrderEntity.OrderItemShipped orderItemShipped) {
    return shipOrderItems.stream()
        .map(item -> {
          if (item.getOrderItemId().equals(orderItemShipped.getOrderItemId())) {
            return item.toBuilder()
                .setSkuItemId(orderItemShipped.getSkuItemId())
                .setShippedUtc(orderItemShipped.getShippedUtc())
                .build();
          } else {
            return item;
          }
        })
        .collect(Collectors.toList());
  }
}
