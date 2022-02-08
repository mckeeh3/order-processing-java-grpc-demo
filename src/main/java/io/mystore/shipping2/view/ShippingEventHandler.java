package io.mystore.shipping2.view;

import java.util.List;
import java.util.stream.Collectors;

import io.mystore.shipping2.entity.ShippingEntity;

class ShippingEventHandler {
  private ShippingModel.Shipping state;

  private ShippingEventHandler(ShippingModel.Shipping state) {
    this.state = state;
  }

  static ShippingEventHandler fromState(ShippingModel.Shipping state) {
    return new ShippingEventHandler(state);
  }

  ShippingModel.Shipping toState() {
    return state;
  }

  ShippingEventHandler handle(ShippingEntity.OrderCreated orderCreated) {
    state = state.toBuilder()
        .setCustomerId(orderCreated.getCustomerId())
        .setOrderId(orderCreated.getOrderId())
        .setOrderedUtc(orderCreated.getOrderedUtc())
        .addAllOrderItems(orderCreated.getOrderItemsList())
        .build();
    return this;
  }

  ShippingEventHandler handle(ShippingEntity.OrderShipped orderShipped) {
    state = state.toBuilder()
        .setShippedUtc(orderShipped.getShippedUtc())
        .build();
    return this;
  }

  ShippingEventHandler handle(ShippingEntity.OrderItemShipped orderItemShipped) {
    state = state.toBuilder()
        .clearOrderItems()
        .addAllOrderItems(updateOrderItems(state, orderItemShipped))
        .build();
    return this;
  }

  ShippingEventHandler handle(ShippingEntity.OrderSkuItemShipped orderSkuItemShipped) {
    state = state.toBuilder()
        .clearOrderItems()
        .addAllOrderItems(updateOrderItems(state, orderSkuItemShipped))
        .build();
    return this;
  }

  static List<ShippingEntity.OrderItem> updateOrderItems(ShippingModel.Shipping state, ShippingEntity.OrderItemShipped orderItemShipped) {
    return state.getOrderItemsList().stream()
        .map(orderItem -> {
          if (orderItem.getSkuId().equals(orderItemShipped.getSkuId())) {
            return orderItem.toBuilder()
                .setShippedUtc(orderItemShipped.getShippedUtc())
                .build();
          } else {
            return orderItem;
          }
        })
        .collect(Collectors.toList());
  }

  static List<ShippingEntity.OrderItem> updateOrderItems(ShippingModel.Shipping state, ShippingEntity.OrderSkuItemShipped orderSkuItemShipped) {
    return state.getOrderItemsList().stream()
        .map(orderItem -> {
          if (orderItem.getSkuId().equals(orderSkuItemShipped.getSkuId())) {
            return orderItem.toBuilder()
                .clearOrderSkuItems()
                .addAllOrderSkuItems(updateOrderSkuItems(orderItem.getOrderSkuItemsList(), orderSkuItemShipped))
                .build();
          } else {
            return orderItem;
          }
        })
        .collect(Collectors.toList());
  }

  static List<ShippingEntity.OrderSkuItem> updateOrderSkuItems(List<ShippingEntity.OrderSkuItem> orderSkuItems, ShippingEntity.OrderSkuItemShipped orderSkuItemShipped) {
    return orderSkuItems.stream()
        .map(orderSkuItem -> {
          if (orderSkuItem.getOrderSkuItemId().equals(orderSkuItemShipped.getOrderSkuItemId())) {
            return orderSkuItem.toBuilder()
                .setShippedUtc(orderSkuItemShipped.getShippedUtc())
                .build();
          } else {
            return orderSkuItem;
          }
        })
        .collect(Collectors.toList());
  }
}
