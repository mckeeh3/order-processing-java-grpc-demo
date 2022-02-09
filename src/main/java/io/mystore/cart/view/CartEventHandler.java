package io.mystore.cart.view;

import java.util.LinkedHashMap;
import java.util.Map;

import io.mystore.cart.entity.CartEntity;

public class CartEventHandler {
  CartModel.Cart state;
  final Map<String, CartModel.LineItem> items = new LinkedHashMap<>();

  private CartEventHandler(CartModel.Cart state) {
    this.state = state;
    state.getLineItemsList().forEach(lineItem -> items.put(lineItem.getSkuId(), lineItem));
  }

  static CartEventHandler fromState(CartModel.Cart state) {
    return new CartEventHandler(state);
  }

  CartModel.Cart toState() {
    return state;
  }

  CartEventHandler handle(CartEntity.ItemAdded event) {
    items.computeIfPresent(event.getLineItem().getSkuId(), (skuId, lineItem) -> incrementQuantity(event, lineItem));
    items.computeIfAbsent(event.getLineItem().getSkuId(), skuId -> toLineItem(event.getLineItem()));

    state = state.toBuilder()
        .setCartId(event.getCartId())
        .setCustomerId(event.getCustomerId())
        .clearLineItems()
        .addAllLineItems(items.values())
        .build();
    return this;
  }

  static CartModel.LineItem incrementQuantity(CartEntity.ItemAdded event, CartModel.LineItem lineItem) {
    return lineItem
        .toBuilder()
        .setQuantity(lineItem.getQuantity() + event.getLineItem().getQuantity())
        .build();
  }

  static CartModel.LineItem toLineItem(CartEntity.LineItem lineItem) {
    return CartModel.LineItem
        .newBuilder()
        .setSkuId(lineItem.getSkuId())
        .setSkuName(lineItem.getSkuName())
        .setQuantity(lineItem.getQuantity())
        .build();
  }

  CartEventHandler handle(CartEntity.ItemChanged event) {
    items.computeIfPresent(event.getSkuId(), (skuId, lineItem) -> changeQuantity(event, lineItem));

    state = state.toBuilder()
        .clearLineItems()
        .addAllLineItems(items.values())
        .build();
    return this;
  }

  CartModel.LineItem changeQuantity(CartEntity.ItemChanged event, CartModel.LineItem lineItem) {
    return lineItem
        .toBuilder()
        .setQuantity(event.getQuantity())
        .build();
  }

  CartEventHandler handle(CartEntity.ItemRemoved event) {
    items.remove(event.getSkuId());

    state = state.toBuilder()
        .clearLineItems()
        .addAllLineItems(items.values())
        .build();
    return this;
  }

  CartEventHandler handle(CartEntity.CartCheckedOut event) {
    state = state.toBuilder()
        .setCheckedOutUtc(event.getCartState().getCheckedOutUtc())
        .build();
    return this;
  }

  CartEventHandler handle(CartEntity.CartDeleted event) {
    state = state.toBuilder()
        .setDeletedUtc(event.getDeletedUtc())
        .build();
    return this;
  }

  CartEventHandler handle(CartEntity.DatesChanged event) {
    state = state.toBuilder()
        .setCheckedOutUtc(event.getCheckedOutUtc())
        .setDeletedUtc(event.getDeletedUtc())
        .build();
    return this;
  }
}
