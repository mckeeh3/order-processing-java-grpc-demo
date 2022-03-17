package io.mystore.stock.entity;

import java.util.List;
import java.util.Optional;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.stock.api.StockSkuItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItem extends AbstractStockSkuItem {
  static final Logger log = LoggerFactory.getLogger(StockSkuItem.class);

  public StockSkuItem(EventSourcedEntityContext context) {
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState emptyState() {
    return StockSkuItemEntity.StockSkuItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createStockSkuItem(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.CreateStockSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> orderRequestsJoinToStock(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.OrderRequestsJoinToStockCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> orderRequestsJoinToStockRejected(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> stockRequestedJoinToOrderAccepted(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> stockRequestedJoinToOrderRejected(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<StockSkuItemApi.GetStockSKuItemResponse> getStockSkuItem(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.GetStockSKuItemRequest request) {
    return reject(state, request).orElseGet(() -> handle(state, request));
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState stockSkuItemCreated(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockSkuItemCreated event) {
    return updateState(state, event);
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState orderRequestedJoinToStockAccepted(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return updateState(state, event);
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState orderRequestedJoinToStockRejected(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return updateState(state, event);
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState stockRequestedJoinToOrder(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockRequestedJoinToOrder event) {
    return updateState(state, event);
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState stockRequestedJoinToOrderAccepted(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return updateState(state, event);
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState stockRequestedJoinToOrderRejected(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return updateState(state, event);
  }

  private Optional<Effect<StockSkuItemApi.GetStockSKuItemResponse>> reject(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.GetStockSKuItemRequest request) {
    if (state.getStockSkuItemId().isEmpty()) {
      log.warn("No stock SKU item found for skuItemId: '{}'\nstate:\n{}\nGetStockSKuItemRequest: {}", request.getStockSkuItemId(), state, request);
      return Optional.of(effects().error("No stock SKU item found for stockSkuItemId: " + request.getStockSkuItemId()));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.CreateStockSkuItemCommand command) {
    log.info("state: {}\nCreateStockSkuItemCommand: {}", state, command);

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.OrderRequestsJoinToStockCommand command) {
    log.info("state: {}\nOrderRequestsJoinToStockCommand: {}", state, command);

    if (state.getOrderSkuItemId().equals(command.getOrderSkuItemId())) {
      return effects().reply(Empty.getDefaultInstance()); // already joined - idempotent
    } else {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    }
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand command) {
    log.info("state: {}\nOrderRequestsJoinToStockRejectedCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand command) {
    log.info("state: {}\nStockRequestedJoinToOrderAcceptedCommand: {}", state, command);

    if (state.getOrderSkuItemId().equals(command.getOrderSkuItemId())) {
      return effects().reply(Empty.getDefaultInstance()); // already joined - idempotent
    } else {
      return effects()
          .emitEvents(eventsFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    }
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand command) {
    log.info("state: {}\nStockRequestedJoinToOrderRejectedCommand: {}", state, command);

    if (state.getOrderSkuItemId().isEmpty()) {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else {
      return effects().reply(Empty.getDefaultInstance()); // already joined - ignore
    }
  }

  static List<?> eventsFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.CreateStockSkuItemCommand command) {
    var stockSkuItemCreated = StockSkuItemEntity.StockSkuItemCreated
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setStockOrderId(command.getStockOrderId())
        .build();

    var stockRequestedJoinToOrder = StockSkuItemEntity.StockRequestedJoinToOrder
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setStockOrderId(command.getStockOrderId())
        .build();

    return List.of(stockSkuItemCreated, stockRequestedJoinToOrder);
  }

  private Object eventFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.OrderRequestsJoinToStockCommand command) {
    if (!state.getOrderSkuItemId().isEmpty() && !state.getOrderSkuItemId().equals(command.getOrderSkuItemId())) {
      return StockSkuItemEntity.OrderRequestedJoinToStockRejected
          .newBuilder()
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setStockOrderId(command.getStockOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setOrderId(command.getOrderId())
          .build();
    } else {
      return StockSkuItemEntity.OrderRequestedJoinToStockAccepted
          .newBuilder()
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setStockOrderId(command.getStockOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setShippedUtc(TimeTo.now())
          .setOrderId(command.getOrderId())
          .build();
    }
  }

  private StockSkuItemEntity.OrderRequestedJoinToStockRejected eventFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand command) {
    return StockSkuItemEntity.OrderRequestedJoinToStockRejected
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setSkuId(command.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setStockOrderId(command.getStockOrderId())
        .build();
  }

  private List<?> eventsFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand command) {
    if (state.getOrderSkuItemId().isEmpty()) {
      return List.of(StockSkuItemEntity.StockRequestedJoinToOrderAccepted
          .newBuilder()
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setOrderId(command.getOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setShippedUtc(command.getShippedUtc())
          .setStockOrderId(command.getStockOrderId())
          .build());
    } else {
      var stockRequestedJoinToOrder = StockSkuItemEntity.StockRequestedJoinToOrder
          .newBuilder()
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setStockOrderId(command.getStockOrderId())
          .build();

      var stockRequestedJoinToOrderRejected = StockSkuItemEntity.StockRequestedJoinToOrderRejected
          .newBuilder()
          .setSkuId(command.getSkuId())
          .setStockSkuItemId(command.getStockSkuItemId())
          .setStockOrderId(command.getStockOrderId())
          .setOrderSkuItemId(command.getOrderSkuItemId())
          .setOrderId(command.getOrderId())
          .build();

      return List.of(stockRequestedJoinToOrder, stockRequestedJoinToOrderRejected);
    }
  }

  private StockSkuItemEntity.StockRequestedJoinToOrder eventFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand command) {
    return StockSkuItemEntity.StockRequestedJoinToOrder
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setStockOrderId(command.getStockOrderId())
        .build();
  }

  private Effect<StockSkuItemApi.GetStockSKuItemResponse> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.GetStockSKuItemRequest request) {
    return effects().reply(
        StockSkuItemApi.GetStockSKuItemResponse
            .newBuilder()
            .setStockOrderId(state.getStockOrderId())
            .setSkuId(state.getSkuId())
            .setSkuName(state.getSkuName())
            .setStockSkuItemId(state.getStockSkuItemId())
            .setOrderId(state.getOrderId())
            .setOrderSkuItemId(state.getOrderSkuItemId())
            .setShippedUtc(state.getShippedUtc())
            .build());
  }

  private StockSkuItemEntity.StockSkuItemState updateState(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockSkuItemCreated event) {
    return state
        .toBuilder()
        .setStockSkuItemId(event.getStockSkuItemId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setStockOrderId(event.getStockOrderId())
        .build();
  }

  private StockSkuItemEntity.StockSkuItemState updateState(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return state.toBuilder()
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setOrderId(event.getOrderId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  private StockSkuItemEntity.StockSkuItemState updateState(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    if (state.getOrderSkuItemId().equals(event.getOrderSkuItemId())) {
      return state.toBuilder()
          .setOrderSkuItemId("")
          .setOrderId("")
          .setShippedUtc(TimeTo.zero())
          .build();
    } else {
      return state;
    }
  }

  private StockSkuItemEntity.StockSkuItemState updateState(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockRequestedJoinToOrder event) {
    return state;
  }

  private StockSkuItemEntity.StockSkuItemState updateState(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return state.toBuilder()
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setOrderId(event.getOrderId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  private StockSkuItemEntity.StockSkuItemState updateState(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    if (state.getOrderSkuItemId().equals(event.getOrderSkuItemId())) {
      return state.toBuilder()
          .setOrderSkuItemId("")
          .setOrderId("")
          .setShippedUtc(TimeTo.zero())
          .build();
    } else {
      return state;
    }
  }
}
