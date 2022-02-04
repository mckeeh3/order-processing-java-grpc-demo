package io.mystore.stock.entity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.stock.api.StockOrderApi;
import io.mystore.stock.api.StockSkuItemEvents;
import io.mystore.stock.api.StockOrderApi.CreateStockOrderCommand;
import io.mystore.stock.api.StockOrderApi.GetStockOrderRequest;
import io.mystore.stock.api.StockSkuItemEvents.JoinStockSkuItemCommand;
import io.mystore.stock.api.StockSkuItemEvents.ReleaseStockSkuItemCommand;
import io.mystore.stock.entity.StockOrderEntity.StockOrderState;
import io.mystore.stock.entity.StockOrderEntity.StockSkuItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockOrder extends AbstractStockOrder {
  static final Logger log = LoggerFactory.getLogger(StockOrder.class);

  public StockOrder(EventSourcedEntityContext context) {
  }

  @Override
  public StockOrderEntity.StockOrderState emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty entity state");
  }

  @Override
  public Effect<Empty> createStockOrder(StockOrderEntity.StockOrderState state, StockOrderApi.CreateStockOrderCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> joinStockSkuItem(StockOrderEntity.StockOrderState state, StockSkuItemEvents.JoinStockSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> releaseStockSkuItem(StockOrderEntity.StockOrderState state, StockSkuItemEvents.ReleaseStockSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<StockOrderApi.StockOrder> getStockOrder(StockOrderEntity.StockOrderState state, StockOrderApi.GetStockOrderRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public StockOrderEntity.StockOrderState stockOrderCreated(StockOrderEntity.StockOrderState state, StockOrderEntity.StockOrderCreated event) {
    return state
        .toBuilder()
        .setStockOrderId(event.getStockOrderId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setQuantity(event.getQuantity())
        .addAllStockSkuItems(toStockSkuItemsList(event))
        .build();
  }

  @Override
  public StockOrderEntity.StockOrderState stockSkuItemJoined(StockOrderEntity.StockOrderState state, StockOrderEntity.StockSkuItemJoined event) {
    var stockSkuItems = toStockSkuItemsList(state, event);

    return state
        .toBuilder()
        .setShippedUtc(areAllItemsShipped(stockSkuItems))
        .clearStockSkuItems()
        .addAllStockSkuItems(stockSkuItems)
        .build();
  }

  @Override
  public StockOrderEntity.StockOrderState stockSkuItemReleased(StockOrderEntity.StockOrderState state, StockOrderEntity.StockSkuItemReleased event) {
    throw new RuntimeException("The event handler for `StockSkuItemReleased` is not implemented, yet");
  }

  private Optional<Effect<Empty>> reject(StockOrderState state, CreateStockOrderCommand command) {
    if (command.getStockOrderId().isEmpty()) {
      return Optional.of(effects().error("stockOrderId is empty"));
    }
    if (command.getSkuId().isEmpty()) {
      return Optional.of(effects().error("skuId is empty"));
    }
    if (command.getSkuName().isEmpty()) {
      return Optional.of(effects().error("skuName is empty"));
    }
    if (command.getQuantity() <= 0) {
      return Optional.of(effects().error("quantity is <= 0"));
    }
    return Optional.empty();
  }

  private Optional<Effect<StockOrderApi.StockOrder>> reject(StockOrderState state, GetStockOrderRequest command) {
    if (state.getStockOrderId().isEmpty()) {
      return Optional.of(effects().error("stock order has not been created yet"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(StockOrderState state, CreateStockOrderCommand command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockOrderState state, JoinStockSkuItemCommand command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockOrderState state, ReleaseStockSkuItemCommand command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<StockOrderApi.StockOrder> handle(StockOrderState state, GetStockOrderRequest command) {
    return effects()
        .reply(StockOrderApi.StockOrder
            .newBuilder()
            .setStockOrderId(state.getStockOrderId())
            .setSkuId(state.getSkuId())
            .setSkuName(state.getSkuName())
            .setQuantity(state.getQuantity())
            .setShippedUtc(state.getShippedUtc())
            .addAllStockSkuItems(toApi(state.getStockSkuItemsList()))
            .build());
  }

  private Iterable<? extends io.mystore.stock.api.StockSkuItemApi.StockSkuItem> toApi(List<StockSkuItem> stockSkuItemsList) {
    return null;
  }

  private StockOrderEntity.StockOrderCreated eventFor(StockOrderState state, CreateStockOrderCommand command) {
    return null;
  }

  private StockOrderEntity.StockSkuItemJoined eventFor(StockOrderState state, JoinStockSkuItemCommand command) {
    return null;
  }

  private StockOrderEntity.StockSkuItemReleased eventFor(StockOrderState state, ReleaseStockSkuItemCommand command) {
    return null;
  }

  private List<StockOrderEntity.StockSkuItem> toStockSkuItemsList(StockOrderEntity.StockOrderCreated event) {
    return IntStream.range(0, event.getQuantity())
        .mapToObj(i -> StockOrderEntity.StockSkuItem.newBuilder()
            .setSkuItemId(UUID.randomUUID().toString())
            .setSkuId(event.getSkuId())
            .setSkuName(event.getSkuName())
            .setOrderId(event.getStockOrderId())
            .build())
        .collect(Collectors.toList());
  }

  private List<StockOrderEntity.StockSkuItem> toStockSkuItemsList(StockOrderEntity.StockOrderState state, StockOrderEntity.StockSkuItemJoined event) {
    return state.getStockSkuItemsList().stream()
        .map(stockSkuItem -> {
          if (stockSkuItem.getSkuId().equals(event.getSkuId())) {
            return stockSkuItem.toBuilder()
                .setOrderId(event.getStockOrderId())
                .setOrderSkuItemId(event.getOrderItemId())
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return stockSkuItem;
          }
        })
        .collect(Collectors.toList());
  }

  private Timestamp areAllItemsShipped(List<StockOrderEntity.StockSkuItem> stockSkuItems) {
    return stockSkuItems.stream()
        .filter(item -> item.getShippedUtc() == null || item.getShippedUtc().getSeconds() == 0)
        .findFirst()
        .isPresent() ? timestampZero() : timestampNow();
  }

  static Timestamp timestampZero() {
    return Timestamp
        .newBuilder()
        .setSeconds(0)
        .setNanos(0)
        .build();
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
