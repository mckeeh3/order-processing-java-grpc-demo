package io.mystore.shipping.view;

import java.util.List;

import io.mystore.shipping.entity.ShippingEntity;

class ShippingEventHandler {

  static ShippingModel.Shipping handle(ShippingModel.Shipping state, ShippingEntity.OrderCreated orderCreated) {
    return state
        .toBuilder()
        .setCustomerId(orderCreated.getCustomerId())
        .setOrderId(orderCreated.getOrderId())
        .setOrderedUtc(orderCreated.getOrderedUtc())
        .addAllOrderItems(orderCreated.getOrderItemsList())
        .build();
  }

  static ShippingModel.Shipping handle(ShippingModel.Shipping state, ShippingEntity.OrderShipped orderShipped) {
    return state
        .toBuilder()
        .setShippedUtc(orderShipped.getShippedUtc())
        .build();
  }

  static ShippingModel.Shipping handle(ShippingModel.Shipping state, ShippingEntity.OrderItemShipped orderItemShipped) {
    return state
        .toBuilder()
        .clearOrderItems()
        .addAllOrderItems(updateOrderItems(state, orderItemShipped))
        .build();
  }

  static ShippingModel.Shipping handle(ShippingModel.Shipping state, ShippingEntity.OrderSkuItemShipped orderSkuItemShipped) {
    return state
        .toBuilder()
        .clearOrderItems()
        .addAllOrderItems(updateOrderItems(state, orderSkuItemShipped))
        .build();
  }

  static List<ShippingEntity.OrderItem> updateOrderItems(ShippingModel.Shipping state, ShippingEntity.OrderItemShipped orderItemShipped) {
    return state.getOrderItemsList().stream()
        .map(orderItem -> {
          if (orderItem.getSkuId().equals(orderItemShipped.getSkuId())) {
            return orderItem
                .toBuilder()
                .setShippedUtc(orderItemShipped.getShippedUtc())
                .build();
          } else {
            return orderItem;
          }
        })
        .toList();
  }

  static List<ShippingEntity.OrderItem> updateOrderItems(ShippingModel.Shipping state, ShippingEntity.OrderSkuItemShipped orderSkuItemShipped) {
    return state.getOrderItemsList().stream()
        .map(orderItem -> {
          if (orderItem.getSkuId().equals(orderSkuItemShipped.getSkuId())) {
            return orderItem
                .toBuilder()
                .clearOrderSkuItems()
                .addAllOrderSkuItems(updateOrderSkuItems(orderItem.getOrderSkuItemsList(), orderSkuItemShipped))
                .build();
          } else {
            return orderItem;
          }
        })
        .toList();
  }

  static List<ShippingEntity.OrderSkuItem> updateOrderSkuItems(List<ShippingEntity.OrderSkuItem> orderSkuItems, ShippingEntity.OrderSkuItemShipped orderSkuItemShipped) {
    return orderSkuItems.stream()
        .map(orderSkuItem -> {
          if (orderSkuItem.getOrderSkuItemId().equals(orderSkuItemShipped.getOrderSkuItemId())) {
            return orderSkuItem
                .toBuilder()
                .setStockSkuItemId(orderSkuItemShipped.getStockSkuItemId())
                .setShippedUtc(orderSkuItemShipped.getShippedUtc())
                .build();
          } else {
            return orderSkuItem;
          }
        })
        .toList();
  }
}
