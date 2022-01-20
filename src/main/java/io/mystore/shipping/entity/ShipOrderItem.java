package io.mystore.shipping.entity;

import java.time.Instant;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.api.ShipOrderItemApi;
import io.mystore.shipping.entity.ShipOrderItemEntity.ShipOrderItemState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItem extends AbstractShipOrderItem {

  public ShipOrderItem(EventSourcedEntityContext context) {
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState emptyState() {
    return ShipOrderItemEntity.ShipOrderItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createShipOrderItem(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.ShipOrderItem command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> addSkuItemToOrder(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.SkuItem command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> placeOnBackOrder(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.BackOrderShipOrderItem command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> stockAlert(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.StockAlertShipOrderItem command) {
    return handle(state, command);
  }

  @Override
  public Effect<ShipOrderItemApi.ShipOrderItem> getShipOrderItem(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.GetShipOrderItemRequest command) {
    return handle(state, command);
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState orderItemCreated(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemEntity.OrderItemCreated event) {
    return state
        .toBuilder()
        .setCustomerId(event.getCustomerId())
        .setOrderId(event.getOrderId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setSkuItemId("")
        .setOrderedUtc(event.getOrderedUtc())
        .build();
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState skuItemAddedToOrder(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemEntity.SkuItemAddedToOrder event) {
    return state
        .toBuilder()
        .setSkuItemId(event.getSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .setBackOrderedUtc(timestampZero())
        .build();
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState skuItemReleasedFromOrder(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemEntity.SkuItemReleasedFromOrder event) {
    return state; // no state change, the event notifies ship-sku-item to release the sku-item
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState orderItemBackOrdered(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemEntity.OrderItemBackOrdered event) {
    return state
        .toBuilder()
        .setBackOrderedUtc(event.getBackOrderedUtc())
        .build();
  }

  private Effect<Empty> handle(ShipOrderItemState state, ShipOrderItemApi.ShipOrderItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.SkuItem command) {
    if (state.getSkuItemId().isEmpty()) {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else { // sku-item already added to order - tell ship-sku-item to release the sku-item
      return effects()
          .emitEvent(eventForReleaseSkuItem(state))
          .thenReply(newState -> Empty.getDefaultInstance());
    }
  }

  private Effect<Empty> handle(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.BackOrderShipOrderItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.StockAlertShipOrderItem command) {
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<ShipOrderItemApi.ShipOrderItem> handle(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.GetShipOrderItemRequest command) {
    return effects().reply(
        ShipOrderItemApi.ShipOrderItem
            .newBuilder()
            .setCustomerId(state.getCustomerId())
            .setOrderId(state.getOrderId())
            .setSkuId(state.getSkuId())
            .setSkuName(state.getSkuName())
            .setOrderedUtc(state.getOrderedUtc())
            .setShippedUtc(state.getShippedUtc())
            .setBackOrderedUtc(state.getBackOrderedUtc())
            .build());
  }

  private ShipOrderItemEntity.OrderItemCreated eventFor(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.ShipOrderItem command) {
    return ShipOrderItemEntity.OrderItemCreated
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .build();
  }

  private ShipOrderItemEntity.SkuItemAddedToOrder eventFor(ShipOrderItemState state, ShipOrderItemApi.SkuItem command) {
    return ShipOrderItemEntity.SkuItemAddedToOrder
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .setShippedUtc(command.getShippedUtc())
        .build();
  }

  private ShipOrderItemEntity.OrderItemBackOrdered eventFor(ShipOrderItemState state, ShipOrderItemApi.BackOrderShipOrderItem command) {
    return ShipOrderItemEntity.OrderItemBackOrdered
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .setBackOrderedUtc(timestampNow())
        .build();
  }

  private ShipOrderItemEntity.OrderItemCreated eventFor(ShipOrderItemState state, ShipOrderItemApi.StockAlertShipOrderItem command) {
    return ShipOrderItemEntity.OrderItemCreated // this event tells skip-sku-item to find some items to ship
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .build();
  }

  private ShipOrderItemEntity.SkuItemReleasedFromOrder eventForReleaseSkuItem(ShipOrderItemState state) {
    return ShipOrderItemEntity.SkuItemReleasedFromOrder
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setSkuItemId(state.getSkuItemId())
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