package io.mystore.cart.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;

import io.mystore.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartsByCustomerByDateView extends AbstractCartsByCustomerByDateView {

  public CartsByCustomerByDateView(ViewContext context) {
  }

  @Override
  public CartModel.Cart emptyState() {
    return CartModel.Cart.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> processItemAdded(CartModel.Cart state, CartEntity.ItemAdded event) {
    return effects()
        .updateState(
            Cart.fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> processItemChanged(CartModel.Cart state, CartEntity.ItemChanged event) {
    return effects()
        .updateState(
            Cart.fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> processItemRemoved(CartModel.Cart state, CartEntity.ItemRemoved event) {
    return effects()
        .updateState(
            Cart.fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> processCartCheckedOut(CartModel.Cart state, CartEntity.CartCheckedOut event) {
    return effects()
        .updateState(
            Cart.fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> processCartDeleted(CartModel.Cart state, CartEntity.CartDeleted event) {
    return effects()
        .updateState(
            Cart.fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartModel.Cart> processDatesChanged(CartModel.Cart state, CartEntity.DatesChanged event) {
    return effects()
        .updateState(
            Cart.fromState(state)
                .handle(event)
                .toState());
  }
}
