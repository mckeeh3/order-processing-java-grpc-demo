package io.mystore.shipping.entity;

import java.util.List;
import java.util.Optional;

import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.shipping.api.OrderSkuItemApi;

// This class was initially generated based on the .proto definition by Kalix tooling.
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
  public Effect<Empty> createOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.CreateOrderSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> stockRequestsJoinToOrder(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.StockRequestsJoinToOrderCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> stockRequestsJoinToOrderRejected(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.StockRequestsJoinToOrderRejectedCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> orderRequestedJoinToStockAccepted(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> orderRequestedJoinToStockRejected(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> backOrderOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.BackOrderOrderSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<OrderSkuItemApi.GetOrderSkuItemResponse> getOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.GetOrderSkuItemRequest request) {
    return reject(state, request).orElseGet(() -> handle(state, request));
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState orderSkuItemCreated(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.OrderSkuItemCreated event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState stockRequestedJoinToOrderAccepted(
      OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState stockRequestedJoinToOrderRejected(
      OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState orderRequestedJoinToStock(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.OrderRequestedJoinToStock event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState orderRequestedJoinToStockAccepted(
      OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState orderRequestedJoinToStockRejected(
      OrderSkuItemEntity.OrderSkuItemState state, OrderSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return updateState(state, event);
  }

  @Override
  public OrderSkuItemEntity.OrderSkuItemState backOrderedOrderSkuItem(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.BackOrderedOrderSkuItem event) {
    return updateState(state, event);
  }

  private Optional<Effect<OrderSkuItemApi.GetOrderSkuItemResponse>> reject(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.GetOrderSkuItemRequest request) {
    if (state.getOrderSkuItemId().isEmpty()) {
      return Optional.of(effects().error(
          String.format("Order SKU item for order-sku-item-id '%s' does not exist", request.getOrderSkuItemId())));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.CreateOrderSkuItemCommand command) {
    log.info("state: {}\nCreateOrderSkuItemCommand: {}", state, command);

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.StockRequestsJoinToOrderCommand command) {
    log.info("state: {}\nStockRequestsJoinToOrderCommand: {}", state, command);

    if (state.getStockSkuItemId().equals(command.getStockSkuItemId())) {
      return effects().reply(Empty.getDefaultInstance()); // already joined - idempotent
    } else {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    }
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.StockRequestsJoinToOrderRejectedCommand command) {
    log.info("state: {}\nStockRequestsJoinToOrderRejectedCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand command) {
    log.info("state: {}\nOrderRequestedJoinToStockAcceptedCommand: {}", state, command);

    if (state.getStockSkuItemId().equals(command.getStockSkuItemId())) {
      return effects().reply(Empty.getDefaultInstance()); // already joined - idempotent
    } else {
      return effects()
          .emitEvents(eventsFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    }
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand command) {
    log.info("state: {}\nOrderRequestedJoinToStockRejectedCommand: {}", state, command);

    if (state.getStockSkuItemId().isEmpty()) {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else {
      return effects().reply(Empty.getDefaultInstance()); // already joined - ignore
    }
  }

  private Effect<Empty> handle(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.BackOrderOrderSkuItemCommand command) {
    log.info("state: {}\nBackOrderOrderSkuItemCommand: {}", state, command);

    if (state.getStockSkuItemId().isEmpty()) {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else {
      return effects().reply(Empty.getDefaultInstance());
    }
  }

  static List<?> eventsFor(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.CreateOrderSkuItemCommand command) {
    var orderSkuItemCreated = OrderSkuItemEntity.OrderSkuItemCreated
        .newBuilder()
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setCustomerId(command.getCustomerId())
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setOrderedUtc(command.getOrderedUtc())
        .build();

    var stockSkuItemRequired = OrderSkuItemEntity.OrderRequestedJoinToStock
        .newBuilder()
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .build();

    return List.of(orderSkuItemCreated, stockSkuItemRequired);
  }

  static Object eventFor(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.StockRequestsJoinToOrderCommand command) {
    if (!state.getStockSkuItemId().isEmpty() && !state.getStockSkuItemId().equals(command.getStockSkuItemId())) {
      return OrderSkuItemEntity.StockRequestedJoinToOrderRejected
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setStockOrderId(command.getStockOrderId())
          .build();
    } else {
      return OrderSkuItemEntity.StockRequestedJoinToOrderAccepted
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setShippedUtc(TimeTo.now())
          .setStockOrderId(command.getStockOrderId())
          .build();
    }
  }

  private OrderSkuItemEntity.StockRequestedJoinToOrderRejected eventFor(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.StockRequestsJoinToOrderRejectedCommand command) {
    return OrderSkuItemEntity.StockRequestedJoinToOrderRejected
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setStockOrderId(command.getStockOrderId())
        .build();
  }

  static List<?> eventsFor(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand command) {
    if (state.getStockSkuItemId().isEmpty()) {
      return List.of(OrderSkuItemEntity.OrderRequestedJoinToStockAccepted
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setShippedUtc(command.getShippedUtc())
          .setStockOrderId(command.getStockOrderId())
          .build());
    } else {
      var orderRequestedJoinToStock = OrderSkuItemEntity.OrderRequestedJoinToStock
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setSkuId(command.getSkuId())
          .build();

      var orderRequestedJoinToStockRejected = OrderSkuItemEntity.OrderRequestedJoinToStockRejected
          .newBuilder()
          .setOrderId(command.getOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setStockOrderId(command.getStockOrderId())
          .build();

      return List.of(orderRequestedJoinToStock, orderRequestedJoinToStockRejected);
    }
  }

  static OrderSkuItemEntity.OrderRequestedJoinToStock eventFor(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand command) {
    return OrderSkuItemEntity.OrderRequestedJoinToStock
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setSkuId(command.getSkuId())
        .build();
  }

  static OrderSkuItemEntity.BackOrderedOrderSkuItem eventFor(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.BackOrderOrderSkuItemCommand command) {
    return OrderSkuItemEntity.BackOrderedOrderSkuItem
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setSkuId(command.getSkuId())
        .setBackOrderedUtc(TimeTo.now())
        .build();
  }

  private Effect<OrderSkuItemApi.GetOrderSkuItemResponse> handle(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemApi.GetOrderSkuItemRequest request) {
    return effects().reply(
        OrderSkuItemApi.GetOrderSkuItemResponse
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

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.OrderSkuItemCreated event) {
    return state.toBuilder()
        .setCustomerId(event.getCustomerId())
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setOrderedUtc(event.getOrderedUtc())
        .build();
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return state.toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setStockOrderId(event.getStockOrderId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    if (state.getStockSkuItemId().equals(event.getStockSkuItemId())) {
      return state.toBuilder()
          .setStockSkuItemId("")
          .setStockOrderId("")
          .setShippedUtc(TimeTo.zero())
          .build();
    } else {
      return state;
    }
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.OrderRequestedJoinToStock event) {
    return state;
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return state.toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setStockOrderId(event.getStockOrderId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    if (state.getStockSkuItemId().equals(event.getStockSkuItemId())) {
      return state.toBuilder()
          .setStockSkuItemId("")
          .setStockOrderId("")
          .setShippedUtc(TimeTo.zero())
          .build();
    } else {
      return state;
    }
  }

  private OrderSkuItemEntity.OrderSkuItemState updateState(OrderSkuItemEntity.OrderSkuItemState state,
      OrderSkuItemEntity.BackOrderedOrderSkuItem event) {
    return state.toBuilder()
        .setBackOrderedUtc(event.getBackOrderedUtc())
        .build();
  }
}
