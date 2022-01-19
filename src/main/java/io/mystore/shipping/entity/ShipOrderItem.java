package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

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
  public Effect<Empty> addShipOrderItem(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.ShipOrderItem command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> addSkuItem(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.SkuItem command) {
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
  public ShipOrderItemEntity.ShipOrderItemState orderItemAdded(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemEntity.OrderItemAdded event) {
    return state
        .toBuilder()
        .build();
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState skuItemAddedToOrder(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemEntity.SkuItemAddedToOrder event) {
    return state
        .toBuilder()
        // .setSkuItemId(event.getSkuItemId())
        .build();
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState skuItemReleasedFromOrder(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemEntity.SkuItemReleasedFromOrder event) {
    return state
        .toBuilder()
        // TODO
        .build();
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
    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
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

  private ShipOrderItemEntity.OrderItemAdded eventFor(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.ShipOrderItem command) {
    return ShipOrderItemEntity.OrderItemAdded
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

  private ShipOrderItemEntity.SkuItemReleasedFromOrder eventFor(ShipOrderItemState state, ShipOrderItemApi.BackOrderShipOrderItem command) {
    return ShipOrderItemEntity.SkuItemReleasedFromOrder
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .build();
  }

  private ShipOrderItemEntity.OrderItemAdded eventFor(ShipOrderItemState state, ShipOrderItemApi.StockAlertShipOrderItem command) {
    return ShipOrderItemEntity.OrderItemAdded
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .build();
  }
}
