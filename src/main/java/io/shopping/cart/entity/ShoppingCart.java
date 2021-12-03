package io.shopping.cart.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import io.shopping.cart.api.CartApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An event sourced entity. */
public class ShoppingCart extends AbstractShoppingCart {
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  public ShoppingCart(EventSourcedEntityContext context) {
  }

  @Override
  public CartEntity.CartState emptyState() {
    return CartEntity.CartState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addItem(CartEntity.CartState state, CartApi.AddLineItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> changeItem(CartEntity.CartState state, CartApi.ChangeLineItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> removeItem(CartEntity.CartState state, CartApi.RemoveLineItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> checkoutCart(CartEntity.CartState state, CartApi.CheckoutShoppingCart command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> shippedCart(CartEntity.CartState state, CartApi.ShippedShoppingCart command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> deliveredCart(CartEntity.CartState state, CartApi.DeliveredShoppingCart command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> deleteCart(CartEntity.CartState state, CartApi.DeleteShoppingCart command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<CartApi.ShoppingCart> setDates(CartEntity.CartState state, CartApi.SetShoppingCartDates command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<CartApi.ShoppingCart> getCart(CartEntity.CartState state, CartApi.GetShoppingCart command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public CartEntity.CartState itemAdded(CartEntity.CartState state, CartEntity.ItemAdded event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  @Override
  public CartEntity.CartState itemChanged(CartEntity.CartState state, CartEntity.ItemChanged event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  @Override
  public CartEntity.CartState itemRemoved(CartEntity.CartState state, CartEntity.ItemRemoved event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  @Override
  public CartEntity.CartState cartCheckedOut(CartEntity.CartState state, CartEntity.CartCheckedOut event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  @Override
  public CartEntity.CartState cartShipped(CartEntity.CartState state, CartEntity.CartShipped event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  @Override
  public CartEntity.CartState cartDelivered(CartEntity.CartState state, CartEntity.CartDelivered event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  @Override
  public CartEntity.CartState cartDeleted(CartEntity.CartState state, CartEntity.CartDeleted event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  @Override
  public CartEntity.CartState datesChanged(CartEntity.CartState state, CartEntity.DatesChanged event) {
    return Cart
        .fromState(state)
        .handle(event)
        .toState();
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.AddLineItem command) {
    if (state.getCheckedOutUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is already checked out"));
    }
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is deleted"));
    }
    if (command.getCustomerId().isEmpty()) {
      return Optional.of(effects().error("Customer ID is required"));
    }
    if (command.getProductId().isEmpty()) {
      return Optional.of(effects().error("Product ID is required"));
    }
    if (command.getProductName().isEmpty()) {
      return Optional.of(effects().error("Product name is required"));
    }
    if (command.getQuantity() <= 0) {
      return Optional.of(effects().error("Quantity must be greater than 0"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.ChangeLineItem command) {
    if (state.getCartId().isEmpty()) {
      return Optional.of(effects().error("Shopping cart is empty"));
    }
    if (state.getCheckedOutUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is already checked out"));
    }
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is deleted"));
    }
    if (command.getProductId().isEmpty()) {
      return Optional.of(effects().error("Product id is required"));
    }
    if (command.getQuantity() <= 0) {
      return Optional.of(effects().error("Quantity must be greater than 0"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.RemoveLineItem command) {
    if (state.getCartId().isEmpty()) {
      return Optional.of(effects().error("Shopping cart is empty"));
    }
    if (state.getCheckedOutUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is already checked out"));
    }
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is deleted"));
    }
    if (command.getProductId().isEmpty()) {
      return Optional.of(effects().error("Product id is required"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.CheckoutShoppingCart command) {
    if (state.getCartId().isEmpty()) {
      return Optional.of(effects().error("Shopping cart is empty"));
    }
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is deleted"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.ShippedShoppingCart command) {
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is deleted"));
    }
    if (state.getCheckedOutUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Shopping cart is not checked out"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.DeliveredShoppingCart command) {
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart is deleted"));
    }
    if (state.getCheckedOutUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Shopping cart is not checked out"));
    }
    if (state.getShippedUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Shopping cart is not shipped"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.DeleteShoppingCart command) {
    if (state.getDeliveredUtc().getSeconds() != 0) {
      return Optional.of(effects().error("Cannot delete delivered order"));
    }
    if (state.getShippedUtc().getSeconds() != 0) {
      return Optional.of(effects().error("Cannot delete shipped order"));
    }
    if (state.getCheckedOutUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Cannot delete checked out order"));
    }
    return Optional.empty();
  }

  private Optional<Effect<CartApi.ShoppingCart>> reject(CartEntity.CartState state, CartApi.SetShoppingCartDates command) {
    return reject(command.getCheckedOutUtc(), "checked out")
        .or(() -> reject(command.getShippedUtc(), "shipped"))
        .or(() -> reject(command.getDeliveredUtc(), "delivered"))
        .or(() -> reject(command.getDeletedUtc(), "deleted"))
        .or(() -> Optional.empty());
  }

  private Optional<Effect<CartApi.ShoppingCart>> reject(String utc, String name) {
    if (utc.trim().isEmpty()) {
      return Optional.empty();
    }
    try {
      dateFormat.parse(utc);
      return Optional.empty();
    } catch (ParseException e) {
      return Optional.of(effects().error(String.format("Invalid %s date: %s", name, e.getMessage())));
    }
  }

  private Optional<Effect<CartApi.ShoppingCart>> reject(CartEntity.CartState state, CartApi.GetShoppingCart command) {
    if (state.getCartId().isEmpty()) {
      return Optional.of(effects().error("Shopping cart is empty"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.AddLineItem command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.ChangeLineItem command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.RemoveLineItem command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.CheckoutShoppingCart command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.ShippedShoppingCart command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.DeliveredShoppingCart command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.DeleteShoppingCart command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<CartApi.ShoppingCart> handle(CartEntity.CartState state, CartApi.SetShoppingCartDates command) {
    return effects()
        .emitEvent(event(state, command))
        .thenReply(newState -> toApi(newState));
  }

  private Effect<CartApi.ShoppingCart> handle(CartEntity.CartState state, CartApi.GetShoppingCart command) {
    return effects().reply(toApi(state));
  }

  private static CartEntity.ItemAdded event(CartEntity.CartState state, CartApi.AddLineItem command) {
    var lineItem = CartEntity.LineItem
        .newBuilder()
        .setProductId(command.getProductId())
        .setProductName(command.getProductName())
        .setQuantity(command.getQuantity())
        .build();
    return CartEntity.ItemAdded.newBuilder()
        .setCartId(command.getCartId())
        .setCustomerId(command.getCustomerId())
        .setLineItem(lineItem)
        .build();
  }

  private static CartEntity.ItemChanged event(CartEntity.CartState state, CartApi.ChangeLineItem command) {
    return CartEntity.ItemChanged
        .newBuilder()
        .setCartId(state.getCartId())
        .setProductId(command.getProductId())
        .setQuantity(command.getQuantity())
        .build();
  }

  private static CartEntity.ItemRemoved event(CartEntity.CartState state, CartApi.RemoveLineItem command) {
    return CartEntity.ItemRemoved
        .newBuilder()
        .setCartId(state.getCartId())
        .setProductId(command.getProductId())
        .build();
  }

  private static CartEntity.CartCheckedOut event(CartEntity.CartState state, CartApi.CheckoutShoppingCart command) {
    return CartEntity.CartCheckedOut
        .newBuilder()
        .setCartId(state.getCartId())
        .setCheckedOutUtc(Cart.timestampNow())
        .build();
  }

  private static CartEntity.CartShipped event(CartEntity.CartState state, CartApi.ShippedShoppingCart command) {
    return CartEntity.CartShipped
        .newBuilder()
        .setCartId(state.getCartId())
        .setShippedUtc(Cart.timestampNow())
        .build();
  }

  private static CartEntity.CartDelivered event(CartEntity.CartState state, CartApi.DeliveredShoppingCart command) {
    return CartEntity.CartDelivered
        .newBuilder()
        .setCartId(state.getCartId())
        .setDeliveredUtc(Cart.timestampNow())
        .build();
  }

  private static CartEntity.CartDeleted event(CartEntity.CartState state, CartApi.DeleteShoppingCart command) {
    return CartEntity.CartDeleted
        .newBuilder()
        .setCartId(state.getCartId())
        .setDeletedUtc(Cart.timestampNow())
        .build();
  }

  private static CartEntity.DatesChanged event(CartEntity.CartState state, CartApi.SetShoppingCartDates command) {
    return CartEntity.DatesChanged
        .newBuilder()
        .setCartId(state.getCartId())
        .setCheckedOutUtc(toTimeStamp(command.getCheckedOutUtc()))
        .setShippedUtc(toTimeStamp(command.getShippedUtc()))
        .setDeliveredUtc(toTimeStamp(command.getDeliveredUtc()))
        .setDeletedUtc(toTimeStamp(command.getDeletedUtc()))
        .build();
  }

  private static CartApi.ShoppingCart toApi(CartEntity.CartState state) {
    return CartApi.ShoppingCart
        .newBuilder()
        .setCartId(state.getCartId())
        .setCustomerId(state.getCustomerId())
        .setCheckedOutUtc(toUtc(state.getCheckedOutUtc()))
        .setShippedUtc(toUtc(state.getShippedUtc()))
        .setDeliveredUtc(toUtc(state.getDeliveredUtc()))
        .setDeletedUtc(toUtc(state.getDeletedUtc()))
        .addAllLineItems(toApi(state.getLineItemsList()))
        .build();
  }

  private static List<CartApi.LineItem> toApi(List<CartEntity.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> CartApi.LineItem
            .newBuilder()
            .setProductId(lineItem.getProductId())
            .setProductName(lineItem.getProductName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  private static String toUtc(Timestamp timestamp) {
    if (timestamp.getSeconds() == 0) {
      return "";
    }
    return dateFormat.format(new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000));
  }

  static Timestamp toTimeStamp(String utc) {
    if (utc.isEmpty()) {
      return Timestamp.newBuilder().build();
    }
    try {
      var time = dateFormat.parse(utc);
      return Timestamp.newBuilder()
          .setSeconds(time.getTime() / 1000)
          .setNanos((int) (time.getTime() % 1000) * 1000000)
          .build();
    } catch (ParseException e) {
      throw new RuntimeException("Invalid UTC date: " + utc, e);
    }
  }

  static class Cart {
    CartEntity.CartState state;
    final Map<String, CartEntity.LineItem> lineItems = new LinkedHashMap<>();

    Cart(CartEntity.CartState state) {
      this.state = state;
      state.getLineItemsList().forEach(lineItem -> lineItems.put(lineItem.getProductId(), lineItem));
    }

    static Cart fromState(CartEntity.CartState state) {
      return new Cart(state);
    }

    Cart handle(CartEntity.ItemAdded event) {
      lineItems.computeIfPresent(event.getLineItem().getProductId(),
          (productId, lineItem) -> lineItem
              .toBuilder()
              .setQuantity(lineItem.getQuantity() + event.getLineItem().getQuantity())
              .build());
      lineItems.computeIfAbsent(event.getLineItem().getProductId(),
          productId -> CartEntity.LineItem
              .newBuilder()
              .setProductId(event.getLineItem().getProductId())
              .setProductName(event.getLineItem().getProductName())
              .setQuantity(event.getLineItem().getQuantity())
              .build());
      state = state.toBuilder()
          .setCartId(event.getCartId())
          .setCustomerId(event.getCustomerId())
          .clearLineItems()
          .addAllLineItems(lineItems.values())
          .build();
      return this;
    }

    Cart handle(CartEntity.ItemChanged event) {
      lineItems.computeIfPresent(event.getProductId(),
          (productId, lineItem) -> lineItem
              .toBuilder()
              .setQuantity(event.getQuantity())
              .build());
      state = state.toBuilder()
          .clearLineItems()
          .addAllLineItems(lineItems.values())
          .build();
      return this;
    }

    Cart handle(CartEntity.ItemRemoved event) {
      lineItems.remove(event.getProductId());
      state = state
          .toBuilder()
          .clearLineItems()
          .addAllLineItems(lineItems.values())
          .build();
      return this;
    }

    Cart handle(CartEntity.CartCheckedOut event) {
      state = state
          .toBuilder()
          .setCheckedOutUtc(timestampNow())
          .build();
      return this;
    }

    Cart handle(CartEntity.CartShipped event) {
      state = state
          .toBuilder()
          .setShippedUtc(timestampNow())
          .build();
      return this;
    }

    Cart handle(CartEntity.CartDelivered event) {
      state = state
          .toBuilder()
          .setDeliveredUtc(timestampNow())
          .build();
      return this;
    }

    Cart handle(CartEntity.CartDeleted event) {
      state = state
          .toBuilder()
          .setDeletedUtc(timestampNow())
          .build();
      return this;
    }

    Cart handle(CartEntity.DatesChanged event) {
      state = state
          .toBuilder()
          .setCheckedOutUtc(fromStateOrEvent(state.getCheckedOutUtc(), event.getCheckedOutUtc()))
          .setShippedUtc(fromStateOrEvent(state.getShippedUtc(), event.getShippedUtc()))
          .setDeliveredUtc(fromStateOrEvent(state.getDeliveredUtc(), event.getDeliveredUtc()))
          .setDeletedUtc(fromStateOrEvent(state.getDeletedUtc(), event.getDeletedUtc()))
          .build();
      return this;
    }

    CartEntity.CartState toState() {
      return state;
    }

    static Timestamp fromStateOrEvent(Timestamp state, Timestamp event) {
      return event.getSeconds() == 0 ? state : event;
    }

    static Timestamp timestampNow() {
      var now = Instant.now();
      return Timestamp
          .newBuilder()
          .setSeconds(now.getEpochSecond())
          .setNanos(now.getNano())
          .build();
    }
  }
}
