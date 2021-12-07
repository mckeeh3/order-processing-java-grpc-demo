package io.shopping.cart.view;

import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Timestamp;

import io.shopping.cart.api.CartApi;
import io.shopping.cart.api.CartApi.LineItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.akkaserverless.javasdk.view.View;
import io.shopping.cart.entity.CartEntity;
import io.shopping.cart.entity.CartEntity.ItemChanged;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CustomerViewImpl extends AbstractCustomerView {
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  public CustomerViewImpl(ViewContext context) {
  }

  @Override
  public CartApi.ShoppingCart emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state");
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processItemAdded(CartApi.ShoppingCart state, CartEntity.ItemAdded event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processItemChanged(CartApi.ShoppingCart state, CartEntity.ItemChanged event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processItemRemoved(CartApi.ShoppingCart state, CartEntity.ItemRemoved event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartCheckedOut(CartApi.ShoppingCart state, CartEntity.CartCheckedOut event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartShipped(CartApi.ShoppingCart state, CartEntity.CartShipped event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartDelivered(CartApi.ShoppingCart state, CartEntity.CartDelivered event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processCartDeleted(CartApi.ShoppingCart state, CartEntity.CartDeleted event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public View.UpdateEffect<CartApi.ShoppingCart> processDatesChanged(CartApi.ShoppingCart state, CartEntity.DatesChanged event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  static class ShoppingCart {
    CartApi.ShoppingCart state;
    final Map<String, CartApi.LineItem> items = new LinkedHashMap<>();

    private ShoppingCart(CartApi.ShoppingCart state) {
      this.state = state;
      state.getLineItemsList().forEach(lineItem -> items.put(lineItem.getProductId(), lineItem));
    }

    static ShoppingCart fromState(CartApi.ShoppingCart state) {
      return new ShoppingCart(state);
    }

    public CartApi.ShoppingCart toState() {
      return state;
    }

    public ShoppingCart handle(CartEntity.ItemAdded event) {
      items.computeIfPresent(event.getLineItem().getProductId(), (productId, lineItem) -> incrementQuantity(event, lineItem));
      items.computeIfAbsent(event.getLineItem().getProductId(), productId -> toLineItem(event.getLineItem()));

      state = state.toBuilder()
          .setCartId(event.getCartId())
          .setCustomerId(event.getCustomerId())
          .clearLineItems()
          .addAllLineItems(items.values())
          .build();
      return this;
    }

    private static CartApi.LineItem incrementQuantity(CartEntity.ItemAdded event, CartApi.LineItem lineItem) {
      return CartApi.LineItem
          .newBuilder(lineItem)
          .setQuantity(lineItem.getQuantity() + event.getLineItem().getQuantity())
          .build();
    }

    private static CartApi.LineItem toLineItem(CartEntity.LineItem lineItem) {
      return CartApi.LineItem
          .newBuilder()
          .setProductId(lineItem.getProductId())
          .setProductName(lineItem.getProductName())
          .setQuantity(lineItem.getQuantity())
          .build();
    }

    public ShoppingCart handle(CartEntity.ItemChanged event) {
      items.computeIfPresent(event.getProductId(), (productId, lineItem) -> changeQuantity(event, lineItem));

      state = state.toBuilder()
          .clearLineItems()
          .addAllLineItems(items.values())
          .build();
      return this;
    }

    private CartApi.LineItem changeQuantity(ItemChanged event, LineItem lineItem) {
      return lineItem
          .toBuilder()
          .setQuantity(event.getQuantity())
          .build();
    }

    public ShoppingCart handle(CartEntity.ItemRemoved event) {
      items.remove(event.getProductId());

      state = state.toBuilder()
          .clearLineItems()
          .addAllLineItems(items.values())
          .build();
      return this;
    }

    public ShoppingCart handle(CartEntity.CartCheckedOut event) {
      state = state.toBuilder()
          .setCheckedOutUtc(toUtc(event.getCheckedOutUtc()))
          .build();
      return this;
    }

    public ShoppingCart handle(CartEntity.CartShipped event) {
      state = state.toBuilder()
          .setShippedUtc(toUtc(event.getShippedUtc()))
          .build();
      return this;
    }

    public ShoppingCart handle(CartEntity.CartDelivered event) {
      state = state.toBuilder()
          .setDeliveredUtc(toUtc(event.getDeliveredUtc()))
          .build();
      return this;
    }

    public ShoppingCart handle(CartEntity.CartDeleted event) {
      state = state.toBuilder()
          .setDeletedUtc(toUtc(event.getDeletedUtc()))
          .build();
      return this;
    }

    public ShoppingCart handle(CartEntity.DatesChanged event) {
      state = state.toBuilder()
          .setCheckedOutUtc(toUtc(event.getCheckedOutUtc()))
          .setShippedUtc(toUtc(event.getShippedUtc()))
          .setDeliveredUtc(toUtc(event.getDeliveredUtc()))
          .setDeletedUtc(toUtc(event.getDeletedUtc()))
          .build();
      return this;
    }

    private static String toUtc(Timestamp timestamp) {
      if (timestamp.getSeconds() == 0) {
        return "";
      }
      return dateFormat.format(new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000));
    }
  }
}
