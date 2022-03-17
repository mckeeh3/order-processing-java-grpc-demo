package io.mystore.cart.view;

import java.util.List;

import io.mystore.cart.entity.CartEntity;
import io.mystore.cart.entity.CartEntity.ItemAdded;
import io.mystore.cart.view.CartModel.Cart;
import io.mystore.cart.view.CartModel.LineItem;

public class CartEventHandler {

  static CartModel.Cart handle(CartModel.Cart state, CartEntity.ItemAdded event) {
    var exists = state.getLineItemsList().stream()
        .anyMatch(lineItem -> lineItem.getSkuId().equals(event.getLineItem().getSkuId()));

    if (!exists) {
      return state.toBuilder()
          .setCartId(event.getCartId())
          .setCustomerId(event.getCustomerId())
          .clearLineItems()
          .addLineItems(toLineItem(event.getLineItem()))
          .build();
    } else {
      return state.toBuilder()
          .setCartId(event.getCartId())
          .setCustomerId(event.getCustomerId())
          .clearLineItems()
          .addAllLineItems(incrementQuantity(state, event))
          .build();
    }
  }

  static List<LineItem> incrementQuantity(Cart state, ItemAdded event) {
    return state.getLineItemsList().stream()
        .map(lineItem -> {
          if (lineItem.getSkuId().equals(event.getLineItem().getSkuId())) {
            return incrementQuantity(event, lineItem);
          } else {
            return lineItem;
          }
        })
        .toList();
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

  static CartModel.Cart handle(CartModel.Cart state, CartEntity.ItemChanged event) {
    return state.toBuilder()
        .clearLineItems()
        .addAllLineItems(changeQuantity(state, event))
        .build();
  }

  static List<CartModel.LineItem> changeQuantity(CartModel.Cart state, CartEntity.ItemChanged event) {
    return state.getLineItemsList().stream()
        .map(lineItem -> {
          if (lineItem.getSkuId().equals(event.getSkuId())) {
            return changeQuantity(event, lineItem);
          } else {
            return lineItem;
          }
        })
        .toList();
  }

  static CartModel.LineItem changeQuantity(CartEntity.ItemChanged event, CartModel.LineItem lineItem) {
    return lineItem
        .toBuilder()
        .setQuantity(event.getQuantity())
        .build();
  }

  static CartModel.Cart handle(CartModel.Cart state, CartEntity.ItemRemoved event) {
    var lineItems = state.getLineItemsList().stream()
        .filter(lineItem -> !lineItem.getSkuId().equals(event.getSkuId()))
        .toList();

    return state.toBuilder()
        .clearLineItems()
        .addAllLineItems(lineItems)
        .build();
  }

  static CartModel.Cart handle(CartModel.Cart state, CartEntity.CartCheckedOut event) {
    return state.toBuilder()
        .setCheckedOutUtc(event.getCartState().getCheckedOutUtc())
        .build();
  }

  static CartModel.Cart handle(CartModel.Cart state, CartEntity.CartDeleted event) {
    return state.toBuilder()
        .setDeletedUtc(event.getDeletedUtc())
        .build();
  }
}
