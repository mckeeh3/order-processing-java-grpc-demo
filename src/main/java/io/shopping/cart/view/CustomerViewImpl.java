package io.shopping.cart.view;

import com.akkaserverless.javasdk.view.ViewContext;
import io.shopping.cart.api.CartApi;
import com.akkaserverless.javasdk.view.View;
import io.shopping.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CustomerViewImpl extends AbstractCustomerView {

  public CustomerViewImpl(ViewContext context) {
  }

  @Override
  public CartApi.ShoppingCart emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processItemAdded(
      CartApi.ShoppingCart state, CartEntity.ItemAdded itemAdded) {
    throw new UnsupportedOperationException("Update handler for 'ProcessItemAdded' not implemented yet");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processItemChanged(
      CartApi.ShoppingCart state, CartEntity.ItemChanged itemChanged) {
    throw new UnsupportedOperationException("Update handler for 'ProcessItemChanged' not implemented yet");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processItemRemoved(
      CartApi.ShoppingCart state, CartEntity.ItemRemoved itemRemoved) {
    throw new UnsupportedOperationException("Update handler for 'ProcessItemRemoved' not implemented yet");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartCheckedOut(
      CartApi.ShoppingCart state, CartEntity.CartCheckedOut cartCheckedOut) {
    throw new UnsupportedOperationException("Update handler for 'ProcessCartCheckedOut' not implemented yet");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartShipped(
      CartApi.ShoppingCart state, CartEntity.CartShipped cartShipped) {
    throw new UnsupportedOperationException("Update handler for 'ProcessCartShipped' not implemented yet");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartDelivered(
      CartApi.ShoppingCart state, CartEntity.CartDelivered cartDelivered) {
    throw new UnsupportedOperationException("Update handler for 'ProcessCartDelivered' not implemented yet");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartDeleted(
      CartApi.ShoppingCart state, CartEntity.CartDeleted cartDeleted) {
    throw new UnsupportedOperationException("Update handler for 'ProcessCartDeleted' not implemented yet");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processDatesChanged(
      CartApi.ShoppingCart state, CartEntity.DatesChanged datesChanged) {
    throw new UnsupportedOperationException("Update handler for 'ProcessDatesChanged' not implemented yet");
  }
}
