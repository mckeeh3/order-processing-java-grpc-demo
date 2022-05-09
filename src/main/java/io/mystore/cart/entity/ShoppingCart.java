package io.mystore.cart.entity;

import java.util.List;
import java.util.Optional;

import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.cart.api.CartApi;
// This class was initially generated based on the .proto definition by Kalix tooling.
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
  public Effect<Empty> addItem(CartEntity.CartState state, CartApi.AddLineItemCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> changeItem(CartEntity.CartState state, CartApi.ChangeLineItemCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> removeItem(CartEntity.CartState state, CartApi.RemoveLineItemCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> checkoutCart(CartEntity.CartState state, CartApi.CheckoutShoppingCartCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> deleteCart(CartEntity.CartState state, CartApi.DeleteShoppingCartCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<CartApi.ShoppingCart> getCart(CartEntity.CartState state, CartApi.GetShoppingCartRequest request) {
    return reject(state, request).orElseGet(() -> handle(state, request));
  }

  @Override
  public CartEntity.CartState itemAdded(CartEntity.CartState state, CartEntity.ItemAdded event) {
    return updateState(state, event);
  }

  @Override
  public CartEntity.CartState itemChanged(CartEntity.CartState state, CartEntity.ItemChanged event) {
    return updateState(state, event);
  }

  @Override
  public CartEntity.CartState itemRemoved(CartEntity.CartState state, CartEntity.ItemRemoved event) {
    return updateState(state, event);
  }

  @Override
  public CartEntity.CartState cartCheckedOut(CartEntity.CartState state, CartEntity.CartCheckedOut event) {
    return updateState(state, event);
  }

  @Override
  public CartEntity.CartState cartDeleted(CartEntity.CartState state, CartEntity.CartDeleted event) {
    return updateState(state, event);
  }

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.AddLineItemCommand command) {
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

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.ChangeLineItemCommand command) {
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

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.RemoveLineItemCommand command) {
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

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.CheckoutShoppingCartCommand command) {
    if (state.getCartId().isEmpty()) {
      return Optional.of(effects().error("Shopping cart is empty"));
    }
    if (state.getLineItemsCount() == 0) {
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

  private Optional<Effect<Empty>> reject(CartEntity.CartState state, CartApi.DeleteShoppingCartCommand command) {
    if (state.getCartId().isEmpty()) {
      return Optional.of(effects().error("Shopping cart is empty"));
    }
    if (state.getDeliveredUtc().getSeconds() != 0) {
      return Optional.of(effects().error("Cannot delete delivered order"));
    }
    if (state.getCheckedOutUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Cannot delete checked out order"));
    }
    if (state.getDeletedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart already deleted"));
    }
    return Optional.empty();
  }

  private Optional<Effect<CartApi.ShoppingCart>> reject(CartEntity.CartState state,
      CartApi.GetShoppingCartRequest command) {
    if (state.getCartId().isEmpty()) {
      return Optional.of(effects().error("Shopping cart is empty"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.AddLineItemCommand command) {
    log.info("state: {}\nAddLineItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.ChangeLineItemCommand command) {
    log.info("state: {}\nChangeLineItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.RemoveLineItemCommand command) {
    log.info("state: {}\nRemoveLineItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.CheckoutShoppingCartCommand command) {
    log.info("state: {}\nCheckoutShoppingCartCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(CartEntity.CartState state, CartApi.DeleteShoppingCartCommand command) {
    log.info("state: {}\nDeleteShoppingCartCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<CartApi.ShoppingCart> handle(CartEntity.CartState state, CartApi.GetShoppingCartRequest command) {
    return effects().reply(toApi(state));
  }

  static CartEntity.ItemAdded eventFor(CartEntity.CartState state, CartApi.AddLineItemCommand command) {
    var lineItem = CartEntity.LineItem
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setQuantity(command.getQuantity())
        .build();

    return CartEntity.ItemAdded
        .newBuilder()
        .setCartId(command.getCartId())
        .setCustomerId(command.getCustomerId())
        .setLineItem(lineItem)
        .build();
  }

  static CartEntity.ItemChanged eventFor(CartEntity.CartState state, CartApi.ChangeLineItemCommand command) {
    return CartEntity.ItemChanged
        .newBuilder()
        .setCartId(state.getCartId())
        .setSkuId(command.getSkuId())
        .setQuantity(command.getQuantity())
        .build();
  }

  static CartEntity.ItemRemoved eventFor(CartEntity.CartState state, CartApi.RemoveLineItemCommand command) {
    return CartEntity.ItemRemoved
        .newBuilder()
        .setCartId(state.getCartId())
        .setSkuId(command.getSkuId())
        .build();
  }

  static CartEntity.CartCheckedOut eventFor(CartEntity.CartState state, CartApi.CheckoutShoppingCartCommand command) {
    var checkedOutState = state.toBuilder()
        .setCheckedOutUtc(TimeTo.now())
        .build();

    return CartEntity.CartCheckedOut
        .newBuilder()
        .setCartState(checkedOutState)
        .build();
  }

  static CartEntity.CartDeleted eventFor(CartEntity.CartState state, CartApi.DeleteShoppingCartCommand command) {
    return CartEntity.CartDeleted
        .newBuilder()
        .setCartId(state.getCartId())
        .setDeletedUtc(TimeTo.now())
        .build();
  }

  static CartApi.ShoppingCart toApi(CartEntity.CartState state) {
    return CartApi.ShoppingCart
        .newBuilder()
        .setCartId(state.getCartId())
        .setCustomerId(state.getCustomerId())
        .setCheckedOutUtc(state.getCheckedOutUtc())
        .setDeletedUtc(state.getDeletedUtc())
        .addAllLineItems(toApi(state.getLineItemsList()))
        .build();
  }

  static List<CartApi.LineItem> toApi(List<CartEntity.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> CartApi.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .toList();
  }

  static CartEntity.CartState updateState(CartEntity.CartState state, CartEntity.ItemAdded event) {
    var skuInCart = state.getLineItemsList().stream()
        .anyMatch(lineItem -> lineItem.getSkuId().equals(event.getLineItem().getSkuId()));

    if (skuInCart) {
      return state.toBuilder()
          .clearLineItems()
          .addAllLineItems(updateLineItemQuantity(state, event))
          .build();
    } else {
      return state.toBuilder()
          .setCartId(event.getCartId())
          .setCustomerId(event.getCustomerId())
          .addLineItems(event.getLineItem())
          .build();
    }
  }

  static CartEntity.CartState updateState(CartEntity.CartState state, CartEntity.ItemChanged event) {
    return state.toBuilder()
        .clearLineItems()
        .addAllLineItems(changeLineItemQuantity(state, event))
        .build();
  }

  static CartEntity.CartState updateState(CartEntity.CartState state, CartEntity.ItemRemoved event) {
    return state.toBuilder()
        .clearLineItems()
        .addAllLineItems(removeLineItem(state, event.getSkuId()))
        .build();
  }

  static CartEntity.CartState updateState(CartEntity.CartState state, CartEntity.CartCheckedOut event) {
    return state.toBuilder()
        .setCheckedOutUtc(TimeTo.now())
        .build();
  }

  static CartEntity.CartState updateState(CartEntity.CartState state, CartEntity.CartDeleted event) {
    return state.toBuilder()
        .setDeletedUtc(TimeTo.now())
        .build();
  }

  static List<CartEntity.LineItem> updateLineItemQuantity(CartEntity.CartState state, CartEntity.ItemAdded event) {
    return state.getLineItemsList().stream()
        .map(lineItem -> {
          if (lineItem.getSkuId().equals(event.getLineItem().getSkuId())) {
            return lineItem.toBuilder()
                .setQuantity(lineItem.getQuantity() + event.getLineItem().getQuantity())
                .build();
          } else {
            return lineItem;
          }
        })
        .toList();
  }

  static List<CartEntity.LineItem> changeLineItemQuantity(CartEntity.CartState state, CartEntity.ItemChanged event) {
    return state.getLineItemsList().stream()
        .map(lineItem -> {
          if (lineItem.getSkuId().equals(event.getSkuId())) {
            return lineItem.toBuilder()
                .setQuantity(event.getQuantity())
                .build();
          } else {
            return lineItem;
          }
        })
        .toList();
  }

  static List<CartEntity.LineItem> removeLineItem(CartEntity.CartState state, String skuId) {
    return state.getLineItemsList().stream()
        .filter(lineItem -> !lineItem.getSkuId().equals(skuId))
        .toList();
  }
}
