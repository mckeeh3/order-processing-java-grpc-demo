package io.shopping.cart.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import io.shopping.cart.api.CartApi;
import io.shopping.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrdersByDateView extends AbstractOrdersByDateView {
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  public OrdersByDateView(ViewContext context) {
  }

  @Override
  public CartApi.ShoppingCart emptyState() {
    return CartApi.ShoppingCart.getDefaultInstance();
  }

  @Override
  public UpdateEffect<CartApi.ShoppingCart> processCartCheckedOut(CartApi.ShoppingCart state, CartEntity.CartCheckedOut event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<CartApi.ShoppingCart> processCartShipped(CartApi.ShoppingCart state, CartEntity.CartShipped event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<CartApi.ShoppingCart> processCartDelivered(CartApi.ShoppingCart state, CartEntity.CartDelivered event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<CartApi.ShoppingCart> processCartDeleted(CartApi.ShoppingCart state, CartEntity.CartDeleted event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<CartApi.ShoppingCart> processDatesChanged(CartApi.ShoppingCart state, CartEntity.DatesChanged event) {
    return effects()
        .updateState(
            ShoppingCart
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<CartApi.ShoppingCart> ignoreOtherEvents(CartApi.ShoppingCart state, Any any) {
    return effects().ignore();
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

    CartApi.ShoppingCart toState() {
      return state;
    }

    ShoppingCart handle(CartEntity.CartCheckedOut event) {
      state = state.toBuilder()
          .setCustomerId(event.getCartState().getCustomerId())
          .setCartId(event.getCartState().getCartId())
          .setCheckedOutUtc(toUtc(event.getCartState().getCheckedOutUtc()))
          .addAllLineItems(toApi(event.getCartState().getLineItemsList()))
          .build();
      return this;
    }

    private Iterable<? extends CartApi.LineItem> toApi(List<CartEntity.LineItem> lineItems) {
      return lineItems.stream()
          .map(lineItem -> CartApi.LineItem.newBuilder()
              .setProductId(lineItem.getProductId())
              .setProductName(lineItem.getProductName())
              .setQuantity(lineItem.getQuantity())
              .build())
          .collect(Collectors.toList());
    }

    ShoppingCart handle(CartEntity.CartShipped event) {
      state = state.toBuilder()
          .setShippedUtc(toUtc(event.getShippedUtc()))
          .build();
      return this;
    }

    ShoppingCart handle(CartEntity.CartDelivered event) {
      state = state.toBuilder()
          .setDeliveredUtc(toUtc(event.getDeliveredUtc()))
          .build();
      return this;
    }

    ShoppingCart handle(CartEntity.CartDeleted event) {
      state = state.toBuilder()
          .setDeletedUtc(toUtc(event.getDeletedUtc()))
          .build();
      return this;
    }

    ShoppingCart handle(CartEntity.DatesChanged event) {
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
