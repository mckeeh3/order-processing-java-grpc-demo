package io.mystore.stock.entity;

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
  public Effect<Empty> joinStockSkuItem(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.JoinStockSkuItemCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> releaseStockSkuItem(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.ReleaseStockSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<StockSkuItemApi.StockSkuItem> getStockSkuItem(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.GetStockSKuItemRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState stockSkuItemCreated(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.StockSkuItemCreated event) {
    return state
        .toBuilder()
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setSkuItemId(event.getSkuItemId())
        .setOrderId("")
        .setOrderSkuItemId("")
        .build();
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState joinedToStockSkuItem(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.JoinedToStockSkuItem event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderSkuItemId(event.getOrderSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  @Override
  public StockSkuItemEntity.StockSkuItemState releasedFromStockSkuItem(StockSkuItemEntity.StockSkuItemState state, StockSkuItemEntity.ReleasedFromStockSkuItem event) {
    if (state.getOrderSkuItemId().equals(event.getOrderSkuItemId())) {
      return state
          .toBuilder()
          .setOrderId("")
          .setOrderSkuItemId("")
          .setShippedUtc(TimeTo.zero())
          .build();
    } else {
      return state;
    }
  }

  private Optional<Effect<Empty>> reject(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.JoinStockSkuItemCommand command) {
    if (!state.getOrderSkuItemId().isEmpty() && !state.getOrderSkuItemId().equals(command.getOrderSkuItemId())) {
      log.info("skuItem is already assigned to another orderItem\nstate:\n{}\nJoinToOrderItemCommand: {}", state, command);
      return Optional.of(effects().error("skuItem is not available"));
    }
    return Optional.empty();
  }

  private Optional<Effect<StockSkuItemApi.StockSkuItem>> reject(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.GetStockSKuItemRequest command) {
    if (state.getStockOrderId().isEmpty()) {
      return Optional.of(effects().error("No stock SKU item found"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.CreateStockSkuItemCommand command) {
    log.info("state: {}\nCreateStockSkuItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.JoinStockSkuItemCommand command) {
    log.info("state: {}\nJoinToOrderItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.ReleaseStockSkuItemCommand command) {
    log.info("state: {}\nReleaseStockSkuItemCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<StockSkuItemApi.StockSkuItem> handle(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.GetStockSKuItemRequest command) {
    return effects().reply(
        StockSkuItemApi.StockSkuItem
            .newBuilder()
            .setStockOrderId(state.getStockOrderId())
            .setSkuId(state.getSkuId())
            .setSkuName(state.getSkuName())
            .setSkuItemId(state.getSkuItemId())
            .setOrderId(state.getOrderId())
            .setOrderSkuItemId(state.getOrderSkuItemId())
            .setShippedUtc(state.getShippedUtc())
            .build());
  }

  private StockSkuItemEntity.StockSkuItemCreated eventFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.CreateStockSkuItemCommand command) {
    return StockSkuItemEntity.StockSkuItemCreated
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setSkuItemId(command.getSkuItemId())
        .build();
  }

  private StockSkuItemEntity.JoinedToStockSkuItem eventFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.JoinStockSkuItemCommand command) {
    return StockSkuItemEntity.JoinedToStockSkuItem
        .newBuilder()
        .setSkuId(state.getSkuId())
        .setSkuItemId(state.getSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setShippedUtc(TimeTo.now())
        .build();
  }

  private StockSkuItemEntity.ReleasedFromStockSkuItem eventFor(StockSkuItemEntity.StockSkuItemState state, StockSkuItemApi.ReleaseStockSkuItemCommand command) {
    return StockSkuItemEntity.ReleasedFromStockSkuItem
        .newBuilder()
        .setSkuId(state.getSkuId())
        .setSkuItemId(state.getSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderSkuItemId(command.getOrderSkuItemId())
        .setStockOrderId(state.getStockOrderId())
        .build();
  }
}
