package io.mystore.shipping.entity;

import java.time.Instant;
import java.util.Optional;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.api.ShipSkuItemApi;
import io.mystore.shipping.api.ShipSkuItemApi.CreateShipSkuItem;
import io.mystore.shipping.entity.ShipSkuItemEntity.ShipSkuItemState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItem extends AbstractShipSkuItem {

  public ShipSkuItem(EventSourcedEntityContext context) {
  }

  @Override
  public ShipSkuItemEntity.ShipSkuItemState emptyState() {
    return ShipSkuItemEntity.ShipSkuItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createSkuItem(ShipSkuItemEntity.ShipSkuItemState state, ShipSkuItemApi.CreateShipSkuItem command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> addShipOrderItem(ShipSkuItemEntity.ShipSkuItemState state, ShipSkuItemApi.AddShipOrderItemToSkuItem command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> releaseShipOrderItem(ShipSkuItemEntity.ShipSkuItemState state, ShipSkuItemApi.ReleaseShipOrderItemFromSkuItem command) {
    return handle(state, command);
  }

  @Override
  public ShipSkuItemEntity.ShipSkuItemState skuItemCreated(ShipSkuItemEntity.ShipSkuItemState state, ShipSkuItemEntity.SkuItemCreated event) {
    return state
        .toBuilder()
        .setSkuId(event.getSkuId())
        .setSkuItemId(event.getSkuItemId())
        .setOrderId("")
        .setOrderItemId("")
        .build();
  }

  @Override
  public ShipSkuItemEntity.ShipSkuItemState shipOrderItemAdded(ShipSkuItemEntity.ShipSkuItemState state, ShipSkuItemEntity.ShipOrderItemAdded event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setOrderItemId(event.getOrderItemId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  @Override
  public ShipSkuItemEntity.ShipSkuItemState releasedSkuItemFromOrder(ShipSkuItemEntity.ShipSkuItemState state, ShipSkuItemEntity.ReleasedSkuItemFromOrder event) {
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

  private Optional<Effect<Empty>> reject(ShipSkuItemState state, CreateShipSkuItem command) {
    if (!state.getOrderItemId().isEmpty()) {
      return Optional.of(effects().error("skuItem is not available"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(ShipSkuItemState state, ShipSkuItemApi.CreateShipSkuItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipSkuItemState state, ShipSkuItemApi.AddShipOrderItemToSkuItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipSkuItemState state, ShipSkuItemApi.ReleaseShipOrderItemFromSkuItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private ShipSkuItemEntity.SkuItemCreated eventFor(ShipSkuItemState state, ShipSkuItemApi.CreateShipSkuItem command) {
    return ShipSkuItemEntity.SkuItemCreated
        .newBuilder()
        .setSkuId(command.getSkuId())
        .setSkuItemId(command.getSkuItemId())
        .build();
  }

  private ShipSkuItemEntity.ShipOrderItemAdded eventFor(ShipSkuItemState state, ShipSkuItemApi.AddShipOrderItemToSkuItem command) {
    return ShipSkuItemEntity.ShipOrderItemAdded
        .newBuilder()
        .setSkuItemId(command.getSkuItemId())
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .setShippedUtc(timestampNow())
        .build();
  }

  private ShipSkuItemEntity.ReleasedSkuItemFromOrder eventFor(ShipSkuItemState state, ShipSkuItemApi.ReleaseShipOrderItemFromSkuItem command) {
    return ShipSkuItemEntity.ReleasedSkuItemFromOrder
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

  static Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }
}
