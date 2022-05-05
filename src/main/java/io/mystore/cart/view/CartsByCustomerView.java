package io.mystore.cart.view;

import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;

import io.mystore.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartsByCustomerView extends AbstractCartsByCustomerView {

  public CartsByCustomerView(ViewContext context) {
  }

  @Override
  public CartModel.Cart emptyState() {
    return CartModel.Cart.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> onItemAdded(CartModel.Cart state, CartEntity.ItemAdded event) {
    return effects().updateState(CartEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> onItemChanged(CartModel.Cart state, CartEntity.ItemChanged event) {
    return effects().updateState(CartEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> onItemRemoved(CartModel.Cart state, CartEntity.ItemRemoved event) {
    return effects().updateState(CartEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> onCartCheckedOut(CartModel.Cart state, CartEntity.CartCheckedOut event) {
    return effects().updateState(CartEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> onCartDeleted(CartModel.Cart state, CartEntity.CartDeleted event) {
    return effects().updateState(CartEventHandler.handle(state, event));
  }
}
