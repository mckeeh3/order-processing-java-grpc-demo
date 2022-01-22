package io.mystore.shipping.entity;

import java.util.Optional;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.api.StockItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockItem extends AbstractStockItem {

  public StockItem(EventSourcedEntityContext context) {
  }

  @Override
  public StockItemEntity.StockItemState emptyState() {
    return StockItemEntity.StockItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> create(StockItemEntity.StockItemState state, StockItemApi.CreateStockItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> ship(StockItemEntity.StockItemState state, StockItemApi.ShipStockItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> release(StockItemEntity.StockItemState state, StockItemApi.ReleaseStockItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<StockItemApi.StockItem> get(StockItemEntity.StockItemState state, StockItemApi.GetStockItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public StockItemEntity.StockItemState stockItemCreated(StockItemEntity.StockItemState state, StockItemEntity.StockItemCreated event) {
    return state
        .toBuilder()
        .setSkuItemId(event.getSkuItemId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .build();
  }

  @Override
  public StockItemEntity.StockItemState stockItemShipped(StockItemEntity.StockItemState state, StockItemEntity.StockItemShipped event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderItemId(event.getOrderItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  @Override
  public StockItemEntity.StockItemState stockItemReleased(StockItemEntity.StockItemState state, StockItemEntity.StockItemReleased event) {
    if (state.getOrderItemId().equals(event.getOrderItemId())) {
      return state
          .toBuilder()
          .setOrderId("")
          .setOrderItemId("")
          .setShippedUtc(timestampZero())
          .build();
    } else {
      return state;
    }
  }

  private Optional<Effect<Empty>> reject(StockItemEntity.StockItemState state, StockItemApi.CreateStockItem command) {
    if (command.getSkuItemId().isEmpty()) {
      return Optional.of(effects().error("skuItemId is empty"));
    }
    if (command.getSkuId().isEmpty()) {
      return Optional.of(effects().error("skuId is empty"));
    }
    if (command.getSkuName().isEmpty()) {
      return Optional.of(effects().error("skuName is empty"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(StockItemEntity.StockItemState state, StockItemApi.ShipStockItem command) {
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(StockItemEntity.StockItemState state, StockItemApi.ReleaseStockItem command) {
    return Optional.empty();
  }

  private Optional<Effect<StockItemApi.StockItem>> reject(StockItemEntity.StockItemState state, StockItemApi.GetStockItem command) {
    if (state.getSkuItemId().isEmpty()) {
      return Optional.of(effects().error("stock item '" + command.getSkuItemId() + "' not found"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(StockItemEntity.StockItemState state, StockItemApi.CreateStockItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockItemEntity.StockItemState state, StockItemApi.ShipStockItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockItemEntity.StockItemState state, StockItemApi.ReleaseStockItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<StockItemApi.StockItem> handle(StockItemEntity.StockItemState state, StockItemApi.GetStockItem command) {
    return effects().reply(
        StockItemApi.StockItem
            .newBuilder()
            .setSkuItemId(state.getSkuItemId())
            .setSkuId(state.getSkuId())
            .setSkuName(state.getSkuName())
            .setOrderId(state.getOrderId())
            .setOrderItemId(state.getOrderItemId())
            .setShippedUtc(state.getShippedUtc())
            .build());
  }

  private StockItemEntity.StockItemCreated eventFor(StockItemEntity.StockItemState state, StockItemApi.CreateStockItem command) {
    return StockItemEntity.StockItemCreated
        .newBuilder()
        .setSkuItemId(command.getSkuItemId())
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .build();
  }

  private StockItemEntity.StockItemShipped eventFor(StockItemEntity.StockItemState state, StockItemApi.ShipStockItem command) {
    return StockItemEntity.StockItemShipped
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .setShippedUtc(command.getShippedUtc())
        .build();
  }

  private StockItemEntity.StockItemReleased eventFor(StockItemEntity.StockItemState state, StockItemApi.ReleaseStockItem command) {
    return StockItemEntity.StockItemReleased
        .newBuilder()
        .setSkuItemId(command.getSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .build();
  }

  static Timestamp timestampZero() {
    return Timestamp
        .newBuilder()
        .setSeconds(0)
        .setNanos(0)
        .build();
  }
}
