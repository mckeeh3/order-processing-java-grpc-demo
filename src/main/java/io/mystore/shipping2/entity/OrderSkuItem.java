package io.mystore.shipping2.entity;

import java.util.List;
import java.util.Optional;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.shipping2.api.OrderSkuItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An event sourced entity. */
public class OrderSkuItem extends AbstractOrderSkuItem {
  static final Logger log = LoggerFactory.getLogger(OrderSkuItem.class);

  public OrderSkuItem(EventSourcedEntityContext context) {
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState emptyState() {
    return OrderSkuItemEntity.OrderSkuItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.CreateOrderSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> joinToStockSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.JoinToStockSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> backOrderOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.BackOrderOrderSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<OrderSkuItemApi.OrderSkuItem> getOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.GetOrderSkuItemRequest request) {
    return reject(state, request).orElseGet(() -> handle(state, request));
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState createdOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.CreatedOrderSkuItem event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState stockSkuItemRequired(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.StockSkuItemRequired event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState joinedToStockSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.JoinedToStockSkuItem event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState releasedFromOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.ReleasedFromOrderSkuItem event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState backOrderedOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.BackOrderedOrderSkuItem event) {
    return updateState(state, event);
  }

  private Optional<Effect<OrderSkuItemApi.OrderSkuItem>> reject(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.GetOrderSkuItemRequest request) {
    if (state.getOrderSkuItemId().isEmpty()) {
      return Optional.of(effects().error(String.format("Order SKU item for order-sku-item-id '%s' does not exist", request.getOrderSkuItemId())));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.CreateOrderSkuItemCommand command) {
    log.info("state: {}\nCreateOrderSkuItemCommand: {}", state, command);

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.JoinToStockSkuItemCommand command) {
    log.info("state: {}\nJoinToStockSkuItemCommand: {}", state, command);

    if (state.getStockSkuItemId().isEmpty()) {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else if (state.getStockSkuItemId().equals(command.getStockSkuItemId())) {
      return effects().reply(Empty.getDefaultInstance()); // idempotent
    } else {
      return effects() // stock-sku-item already added to order-sku-item - tell stock-sku-item to release the from order-sku-item
          .emitEvent(eventForReleaseSkuItem(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    }
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.BackOrderOrderSkuItemCommand command) {
    log.info("state: {}\nBackOrderOrderSkuItemCommand: {}", state, command);

    if (state.getStockSkuItemId().isEmpty()) {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else {
      return effects().reply(Empty.getDefaultInstance());
    }
  }

  private Effect<OrderSkuItemApi.OrderSkuItem> handle(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.GetOrderSkuItemRequest request) {
    return effects().reply(
        OrderSkuItemApi.OrderSkuItem
            .newBuilder()
            .setCustomerId(state.getCustomerId())
            .setOrderId(state.getOrderId())
            .setOrderSkuItemId(state.getOrderSkuItemId())
            .setSkuId(state.getSkuId())
            .setSkuName(state.getSkuName())
            .setStockSkuItemId(state.getStockSkuItemId())
            .setOrderedUtc(state.getOrderedUtc())
            .setShippedUtc(state.getShippedUtc())
            .setBackOrderedUtc(state.getBackOrderedUtc())
            .build());
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.CreatedOrderSkuItem event) {
    return state
        .toBuilder()
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setCustomerId(event.getCustomerId())
        .setOrderId(event.getOrderId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setOrderedUtc(event.getOrderedUtc())
        .build();
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.StockSkuItemRequired event) {
    return state; // non-state changing event, this event notifies stock-sku-item to attempt to join to this order-sku-item
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.JoinedToStockSkuItem event) {
    return state
        .toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .setBackOrderedUtc(TimeTo.zero())
        .build();
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.ReleasedFromOrderSkuItem event) {
    return state; // no state change, this event notifies stock-sku-item to release the sku-item
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.BackOrderedOrderSkuItem event) {
    return state
        .toBuilder()
        .setBackOrderedUtc(event.getBackOrderedUtc())
        .build();
  }

  static List<?> eventsFor(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.CreateOrderSkuItemCommand command) {
    var orderSkuItemCreated = OrderSkuItemEntity.CreatedOrderSkuItem
        .newBuilder()
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setCustomerId(command.getCustomerId())
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setOrderedUtc(command.getOrderedUtc())
        .build();

    var stockSkuItemRequired = OrderSkuItemEntity.StockSkuItemRequired
        .newBuilder()
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .build();

    return List.of(orderSkuItemCreated, stockSkuItemRequired);
  }

  static OrderSkuItemEntity.JoinedToStockSkuItem eventFor(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.JoinToStockSkuItemCommand command) {
    return OrderSkuItemEntity.JoinedToStockSkuItem
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setOrderSkuItemId(state.getOrderSkuItemId())
        .setSkuId(state.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setShippedUtc(command.getShippedUtc())
        .build();
  }

  static OrderSkuItemEntity.BackOrderedOrderSkuItem eventFor(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.BackOrderOrderSkuItemCommand command) {
    return OrderSkuItemEntity.BackOrderedOrderSkuItem
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setOrderSkuItemId(state.getOrderSkuItemId())
        .setSkuId(state.getSkuId())
        .setBackOrderedUtc(TimeTo.now())
        .build();
  }

  static OrderSkuItemEntity.ReleasedFromOrderSkuItem eventForReleaseSkuItem(OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemApi.JoinToStockSkuItemCommand command) {
    return OrderSkuItemEntity.ReleasedFromOrderSkuItem
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setOrderSkuItemId(state.getOrderSkuItemId())
        .setSkuId(state.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .build();
  }
}
