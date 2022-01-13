package io.shopping.cart.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.shopping.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CustomerOrdersByDateView extends AbstractCustomerOrdersByDateView {

  public CustomerOrdersByDateView(ViewContext context) {
  }

  @Override
  public OrderOuter.Order emptyState() {
    return OrderOuter.Order.getDefaultInstance();
  }

  @Override
  public UpdateEffect<OrderOuter.Order> processCartCheckedOut(OrderOuter.Order state, CartEntity.CartCheckedOut event) {
    return effects()
        .updateState(
            OrderInner
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<OrderOuter.Order> processCartShipped(OrderOuter.Order state, CartEntity.CartShipped event) {
    return effects()
        .updateState(
            OrderInner
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<OrderOuter.Order> processCartDelivered(OrderOuter.Order state, CartEntity.CartDelivered event) {
    return effects()
        .updateState(
            OrderInner
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<OrderOuter.Order> processCartDeleted(OrderOuter.Order state, CartEntity.CartDeleted event) {
    return effects()
        .updateState(
            OrderInner
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<OrderOuter.Order> processDatesChanged(OrderOuter.Order state, CartEntity.DatesChanged event) {
    return effects()
        .updateState(
            OrderInner
                .fromState(state)
                .handle(event)
                .toState());
  }

  @Override
  public UpdateEffect<OrderOuter.Order> ignoreOtherEvents(OrderOuter.Order state, Any any) {
    return effects().ignore();
  }

  static class OrderInner {
    OrderOuter.Order state;
    final Map<String, OrderOuter.LineItem> items = new LinkedHashMap<>();

    private OrderInner(OrderOuter.Order state) {
      this.state = state;
      state.getLineItemsList().forEach(lineItem -> items.put(lineItem.getProductId(), lineItem));
    }

    static OrderInner fromState(OrderOuter.Order state) {
      return new OrderInner(state);
    }

    OrderOuter.Order toState() {
      return state;
    }

    OrderInner handle(CartEntity.CartCheckedOut event) {
      state = state.toBuilder()
          .setCustomerId(event.getCartState().getCustomerId())
          .setCartId(event.getCartState().getCartId())
          .setCheckedOutUtc(event.getCartState().getCheckedOutUtc())
          .addAllLineItems(toApi(event.getCartState().getLineItemsList()))
          .build();
      return this;
    }

    private Iterable<? extends OrderOuter.LineItem> toApi(List<CartEntity.LineItem> lineItems) {
      return lineItems.stream()
          .map(lineItem -> OrderOuter.LineItem.newBuilder()
              .setProductId(lineItem.getProductId())
              .setProductName(lineItem.getProductName())
              .setQuantity(lineItem.getQuantity())
              .build())
          .collect(Collectors.toList());
    }

    OrderInner handle(CartEntity.CartShipped event) {
      state = state.toBuilder()
          .setShippedUtc(event.getShippedUtc())
          .build();
      return this;
    }

    OrderInner handle(CartEntity.CartDelivered event) {
      state = state.toBuilder()
          .setDeliveredUtc(event.getDeliveredUtc())
          .build();
      return this;
    }

    OrderInner handle(CartEntity.CartDeleted event) {
      state = state.toBuilder()
          .setDeletedUtc(event.getDeletedUtc())
          .build();
      return this;
    }

    OrderInner handle(CartEntity.DatesChanged event) {
      state = state.toBuilder()
          .setCheckedOutUtc(event.getCheckedOutUtc())
          .setShippedUtc(event.getShippedUtc())
          .setDeliveredUtc(event.getDeliveredUtc())
          .setDeletedUtc(event.getDeletedUtc())
          .build();
      return this;
    }
  }
}
