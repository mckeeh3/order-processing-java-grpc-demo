package io.mystore.shipping.entity;

import java.time.Instant;
import java.util.Optional;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShipSkuItemApi;
import io.mystore.shipping.api.ShipSkuItemApi.AddOrderItemToSkuItem;
import io.mystore.shipping.entity.ShipSkuItemEntity.SkuItemState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItem extends AbstractShipSkuItem {
  static final Logger log = LoggerFactory.getLogger(ShipSkuItem.class);

  public ShipSkuItem(EventSourcedEntityContext context) {
  }

  @Override
  public ShipSkuItemEntity.SkuItemState emptyState() {
    return ShipSkuItemEntity.SkuItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createSkuItem(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.CreateSkuItemFromStock command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> addOrderItem(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.AddOrderItemToSkuItem command) {
    log.info("addOrderItem: state.orderItemId {}, AddOrderItemToSkuItem.orderItemId {}",
        state.getOrderItemId().isEmpty() ? "(empty)" : state.getOrderItemId(), command.getOrderItemId());
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> releaseOrderItem(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.ReleaseOrderItemFromSkuItem command) {
    return handle(state, command);
  }

  @Override
  public ShipSkuItemEntity.SkuItemState skuItemCreated(ShipSkuItemEntity.SkuItemState state, ShipSkuItemEntity.SkuItemCreated event) {
    return state
        .toBuilder()
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setSkuItemId(event.getSkuItemId())
        .setOrderId("")
        .setOrderItemId("")
        .build();
  }

  @Override
  public ShipSkuItemEntity.SkuItemState orderItemAdded(ShipSkuItemEntity.SkuItemState state, ShipSkuItemEntity.OrderItemAdded event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderItemId(event.getOrderItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  @Override
  public ShipSkuItemEntity.SkuItemState releasedSkuItemFromOrder(ShipSkuItemEntity.SkuItemState state, ShipSkuItemEntity.ReleasedSkuItemFromOrder event) {
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

  private Optional<Effect<Empty>> reject(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.CreateSkuItemFromStock command) {
    if (!state.getOrderItemId().isEmpty()) {
      return Optional.of(effects().error("skuItem is not available"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(SkuItemState state, AddOrderItemToSkuItem command) {
    if (!state.getOrderItemId().isEmpty() && !state.getOrderItemId().equals(command.getOrderItemId())) {
      return Optional.of(effects().error("skuItem is not available"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.CreateSkuItemFromStock command) {
    log.info("ShipSkuItem state: {}, CreateSkuItemFromStock: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.AddOrderItemToSkuItem command) {
    log.info("ShipSkuItem state: {}, AddOrderItemToSkuItem: {}", state, command);

    if (state.getOrderItemId().equals(command.getOrderItemId())) {
      return effects().reply(Empty.getDefaultInstance()); // already added, idempotent
    }

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.ReleaseOrderItemFromSkuItem command) {
    log.info("ShipSkuItem state: {}, ReleaseOrderItemFromSkuItem: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private ShipSkuItemEntity.SkuItemCreated eventFor(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.CreateSkuItemFromStock command) {
    return ShipSkuItemEntity.SkuItemCreated
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setSkuItemId(command.getSkuItemId())
        .build();
  }

  private ShipSkuItemEntity.OrderItemAdded eventFor(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.AddOrderItemToSkuItem command) {
    return ShipSkuItemEntity.OrderItemAdded
        .newBuilder()
        .setSkuItemId(command.getSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .setShippedUtc(timestampNow())
        .build();
  }

  private ShipSkuItemEntity.ReleasedSkuItemFromOrder eventFor(ShipSkuItemEntity.SkuItemState state, ShipSkuItemApi.ReleaseOrderItemFromSkuItem command) {
    return ShipSkuItemEntity.ReleasedSkuItemFromOrder
        .newBuilder()
        .setSkuId(command.getSkuId())
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

  static Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }
}
