package io.mystore.cart.entity;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.cart.api.CartApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCart extends AbstractShoppingCart {
  static final Logger log = LoggerFactory.getLogger(ShoppingCart.class);

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
    if (command.getSkuId().isEmpty()) {
      return Optional.of(effects().error("Sku ID is required"));
    }
    if (command.getSkuName().isEmpty()) {
      return Optional.of(effects().error("Sku name is required"));
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
    if (command.getSkuId().isEmpty()) {
      return Optional.of(effects().error("Sku id is required"));
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
    if (command.getSkuId().isEmpty()) {
      return Optional.of(effects().error("Sku id is required"));
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
    if (state.getCheckedOutUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart already checked out"));
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
    if (state.getShippedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart already shipped"));
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
    if (state.getDeliveredUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart already delivered"));
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
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart already deleted"));
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

  private Optional<Effect<CartApi.ShoppingCart>> reject(Timestamp utc, String name) {
    return Optional.empty();
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
    log.info("Cart state: {}, checkoutShoppingCart: {}", state, command);

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
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
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
        .setSkuId(command.getSkuId())
        .setQuantity(command.getQuantity())
        .build();
  }

  private static CartEntity.ItemRemoved event(CartEntity.CartState state, CartApi.RemoveLineItem command) {
    return CartEntity.ItemRemoved
        .newBuilder()
        .setCartId(state.getCartId())
        .setSkuId(command.getSkuId())
        .build();
  }

  private static CartEntity.CartCheckedOut event(CartEntity.CartState state, CartApi.CheckoutShoppingCart command) {
    var checkedOutState = state.toBuilder()
        .setCheckedOutUtc(Cart.timestampNow())
        .build();
    return CartEntity.CartCheckedOut
        .newBuilder()
        .setCartState(checkedOutState)
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
        .setCheckedOutUtc(command.getCheckedOutUtc())
        .setShippedUtc(command.getShippedUtc())
        .setDeliveredUtc(command.getDeliveredUtc())
        .setDeletedUtc(command.getDeletedUtc())
        .build();
  }

  private static CartApi.ShoppingCart toApi(CartEntity.CartState state) {
    return CartApi.ShoppingCart
        .newBuilder()
        .setCartId(state.getCartId())
        .setCustomerId(state.getCustomerId())
        .setCheckedOutUtc(state.getCheckedOutUtc())
        .setShippedUtc(state.getShippedUtc())
        .setDeliveredUtc(state.getDeliveredUtc())
        .setDeletedUtc(state.getDeletedUtc())
        .addAllLineItems(toApi(state.getLineItemsList()))
        .build();
  }

  private static List<CartApi.LineItem> toApi(List<CartEntity.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> CartApi.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  static class Cart {
    CartEntity.CartState state;
    final Map<String, CartEntity.LineItem> lineItems = new LinkedHashMap<>();

    Cart(CartEntity.CartState state) {
      this.state = state;
      state.getLineItemsList().forEach(lineItem -> lineItems.put(lineItem.getSkuId(), lineItem));
    }

    static Cart fromState(CartEntity.CartState state) {
      return new Cart(state);
    }

    Cart handle(CartEntity.ItemAdded event) {
      lineItems.computeIfPresent(event.getLineItem().getSkuId(), (skuId, lineItem) -> incrementQuantity(event, lineItem));
      lineItems.computeIfAbsent(event.getLineItem().getSkuId(), skuId -> toLineItem(event));

      state = state.toBuilder()
          .setCartId(event.getCartId())
          .setCustomerId(event.getCustomerId())
          .clearLineItems()
          .addAllLineItems(lineItems.values())
          .build();
      return this;
    }

    static CartEntity.LineItem incrementQuantity(CartEntity.ItemAdded event, CartEntity.LineItem lineItem) {
      return lineItem
          .toBuilder()
          .setQuantity(lineItem.getQuantity() + event.getLineItem().getQuantity())
          .build();
    }

    static CartEntity.LineItem toLineItem(CartEntity.ItemAdded event) {
      return CartEntity.LineItem
          .newBuilder()
          .setSkuId(event.getLineItem().getSkuId())
          .setSkuName(event.getLineItem().getSkuName())
          .setQuantity(event.getLineItem().getQuantity())
          .build();
    }

    Cart handle(CartEntity.ItemChanged event) {
      lineItems.computeIfPresent(event.getSkuId(), (skuId, lineItem) -> changeQuantity(event, lineItem));

      state = state.toBuilder()
          .clearLineItems()
          .addAllLineItems(lineItems.values())
          .build();
      return this;
    }

    static CartEntity.LineItem changeQuantity(CartEntity.ItemChanged event, CartEntity.LineItem lineItem) {
      return lineItem
          .toBuilder()
          .setQuantity(event.getQuantity())
          .build();
    }

    Cart handle(CartEntity.ItemRemoved event) {
      lineItems.remove(event.getSkuId());

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
