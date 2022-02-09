package io.mystore.stock.entity;

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

import io.mystore.TimeTo;
import io.mystore.stock.api.StockOrderApi;
import io.mystore.stock.api.StockSkuItemApi;

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
    return StockOrderEntity.StockOrderState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createStockOrder(StockOrderEntity.StockOrderState state, StockOrderApi.CreateStockOrderCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> joinStockSkuItem(StockOrderEntity.StockOrderState state, StockOrderApi.JoinStockSkuItemToStockOrderCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> releaseStockSkuItem(StockOrderEntity.StockOrderState state, StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand command) {
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
        .addAllStockSkuItems(event.getStockSkuItemsList())
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
    var stockSkuItems = toStockSkuItemsList(state, event);

    return state
        .toBuilder()
        .setShippedUtc(areAllItemsShipped(stockSkuItems))
        .clearStockSkuItems()
        .addAllStockSkuItems(stockSkuItems)
        .build();
  }

  private Optional<Effect<Empty>> reject(StockOrderEntity.StockOrderState state, StockOrderApi.CreateStockOrderCommand command) {
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
    if (!state.getStockOrderId().isEmpty()) {
      return Optional.of(effects().error("stockOrderId already exists"));
    }
    return Optional.empty();
  }

  private Optional<Effect<StockOrderApi.StockOrder>> reject(StockOrderEntity.StockOrderState state, StockOrderApi.GetStockOrderRequest command) {
    if (state.getStockOrderId().isEmpty()) {
      return Optional.of(effects().error("stock order has not been created yet"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(StockOrderEntity.StockOrderState state, StockOrderApi.CreateStockOrderCommand command) {
    log.info("state: {}\nCreateStockOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockOrderEntity.StockOrderState state, StockOrderApi.JoinStockSkuItemToStockOrderCommand command) {
    log.info("state: {}\nJoinStockSkuItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockOrderEntity.StockOrderState state, StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand command) {
    log.info("state: {}\nReleaseStockSkuItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<StockOrderApi.StockOrder> handle(StockOrderEntity.StockOrderState state, StockOrderApi.GetStockOrderRequest command) {
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

  static StockOrderEntity.StockOrderCreated eventFor(StockOrderEntity.StockOrderState state, StockOrderApi.CreateStockOrderCommand command) {
    return StockOrderEntity.StockOrderCreated
        .newBuilder()
        .setStockOrderId(command.getStockOrderId())
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setQuantity(command.getQuantity())
        .addAllStockSkuItems(toStockSkuItemsList(command))
        .build();
  }

  static StockOrderEntity.StockSkuItemJoined eventFor(StockOrderEntity.StockOrderState state, StockOrderApi.JoinStockSkuItemToStockOrderCommand command) {
    return StockOrderEntity.StockSkuItemJoined
        .newBuilder()
        .setStockOrderId(state.getStockOrderId())
        .setSkuId(command.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .build();
  }

  static StockOrderEntity.StockSkuItemReleased eventFor(StockOrderEntity.StockOrderState state, StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand command) {
    return StockOrderEntity.StockSkuItemReleased
        .newBuilder()
        .setStockOrderId(state.getStockOrderId())
        .setSkuId(command.getSkuId())
        .setStockSkuItemId(command.getStockSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .build();
  }

  static List<StockOrderEntity.StockSkuItem> toStockSkuItemsList(StockOrderApi.CreateStockOrderCommand command) {
    return IntStream.range(0, command.getQuantity())
        .mapToObj(i -> StockOrderEntity.StockSkuItem.newBuilder()
            .setStockSkuItemId(UUID.randomUUID().toString())
            .setSkuId(command.getSkuId())
            .setSkuName(command.getSkuName())
            .setStockOrderId(command.getStockOrderId())
            .build())
        .collect(Collectors.toList());
  }

  static List<StockOrderEntity.StockSkuItem> toStockSkuItemsList(StockOrderEntity.StockOrderState state, StockOrderEntity.StockSkuItemJoined event) {
    return state.getStockSkuItemsList().stream()
        .map(stockSkuItem -> {
          if (stockSkuItem.getStockSkuItemId().equals(event.getStockSkuItemId())) {
            return stockSkuItem.toBuilder()
                .setOrderId(event.getStockOrderId())
                .setOrderSkuItemId(event.getOrderSkuItemId())
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return stockSkuItem;
          }
        })
        .collect(Collectors.toList());
  }

  static List<StockOrderEntity.StockSkuItem> toStockSkuItemsList(StockOrderEntity.StockOrderState state, StockOrderEntity.StockSkuItemReleased event) {
    return state.getStockSkuItemsList().stream()
        .map(stockSkuItem -> {
          if (stockSkuItem.getStockSkuItemId().equals(event.getStockSkuItemId())) {
            return stockSkuItem.toBuilder()
                .setOrderId("")
                .setOrderSkuItemId("")
                .setShippedUtc(TimeTo.zero())
                .build();
          } else {
            return stockSkuItem;
          }
        })
        .collect(Collectors.toList());
  }

  static Timestamp areAllItemsShipped(List<StockOrderEntity.StockSkuItem> stockSkuItems) {
    return stockSkuItems.stream()
        .filter(item -> item.getShippedUtc() == null || item.getShippedUtc().getSeconds() == 0)
        .findFirst()
        .isPresent() ? TimeTo.zero() : TimeTo.now();
  }

  static List<StockSkuItemApi.StockSkuItem> toApi(List<StockOrderEntity.StockSkuItem> stockSkuItems) {
    return stockSkuItems.stream()
        .map(stockSkuItem -> StockSkuItemApi.StockSkuItem
            .newBuilder()
            .setStockOrderId(stockSkuItem.getStockOrderId())
            .setStockSkuItemId(stockSkuItem.getStockSkuItemId())
            .setSkuId(stockSkuItem.getSkuId())
            .setSkuName(stockSkuItem.getSkuName())
            .setOrderId(stockSkuItem.getOrderId())
            .setOrderSkuItemId(stockSkuItem.getOrderSkuItemId())
            .setShippedUtc(stockSkuItem.getShippedUtc())
            .build())
        .collect(Collectors.toList());
  }
}
