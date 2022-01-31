package io.mystore.shipping.entity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShipOrderItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItem extends AbstractShipOrderItem {
  static final Logger log = LoggerFactory.getLogger(ShipOrderItem.class);

  public ShipOrderItem(EventSourcedEntityContext context) {
  }

  @Override
  public ShipOrderItemEntity.OrderItemState emptyState() {
    return ShipOrderItemEntity.OrderItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createOrderItem(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.CreateOrderItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> joinToSkuItem(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.JoinToSkuItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> backOrderOrderItem(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.BackOrderOrderItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<ShipOrderItemApi.OrderItem> getShipOrderItem(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.GetOrderItemRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public ShipOrderItemEntity.OrderItemState orderItemCreated(ShipOrderItemEntity.OrderItemState state, ShipOrderItemEntity.OrderItemCreated event) {
    return state
        .toBuilder()
        .setCustomerId(event.getCustomerId())
        .setOrderId(event.getOrderId())
        .setOrderItemId(event.getOrderItemId())
        .setSkuId(event.getSkuId())
        .setSkuName(event.getSkuName())
        .setSkuItemId("")
        .setOrderedUtc(event.getOrderedUtc())
        .build();
  }

  @Override
  public ShipOrderItemEntity.OrderItemState skuItemRequired(ShipOrderItemEntity.OrderItemState state, ShipOrderItemEntity.SkuItemRequired event) {
    return state;
  }

  @Override
  public ShipOrderItemEntity.OrderItemState joinedToSkuItem(ShipOrderItemEntity.OrderItemState state, ShipOrderItemEntity.JoinedToSkuItem event) {
    return state
        .toBuilder()
        .setSkuItemId(event.getSkuItemId())
        .setShippedUtc(event.getShippedUtc())
        .setBackOrderedUtc(timestampZero())
        .build();
  }

  @Override
  public ShipOrderItemEntity.OrderItemState skuItemReleasedFromOrder(ShipOrderItemEntity.OrderItemState state, ShipOrderItemEntity.SkuItemReleasedFromOrder event) {
    return state; // no state change, the event notifies ship-sku-item to release the sku-item
  }

  @Override
  public ShipOrderItemEntity.OrderItemState orderItemBackOrdered(ShipOrderItemEntity.OrderItemState state, ShipOrderItemEntity.OrderItemBackOrdered event) {
    return state
        .toBuilder()
        .setBackOrderedUtc(event.getBackOrderedUtc())
        .build();
  }

  private Optional<Effect<ShipOrderItemApi.OrderItem>> reject(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.GetOrderItemRequest command) {
    if (state.getOrderId().isEmpty()) {
      return Optional.of(effects().error("Order item for order-item-id '" + command.getOrderItemId() + "' does not exist"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.CreateOrderItemCommand command) {
    log.info("state: {}, CreateOrderItemCommand: {}", state, command);

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.JoinToSkuItemCommand command) {
    log.info("state: {}, JoinToSkuItemCommand: {}", state, command);

    if (state.getSkuItemId().isEmpty()) {
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else if (state.getSkuItemId().equals(command.getSkuItemId())) {
      return effects().reply(Empty.getDefaultInstance()); // idempotent
    } else {
      return effects() // sku-item already added to order - tell ship-sku-item to release the sku-item
          .emitEvent(eventForReleaseSkuItem(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    }
  }

  private Effect<Empty> handle(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.BackOrderOrderItemCommand command) {
    log.info("state: {}, BackOrderShipOrderItem: {}", state, command);

    if (state.getSkuItemId().isEmpty()) { // don't back order if already shipped
      return effects()
          .emitEvent(eventFor(state, command))
          .thenReply(newState -> Empty.getDefaultInstance());
    } else {
      return effects().reply(Empty.getDefaultInstance());
    }
  }

  private Effect<ShipOrderItemApi.OrderItem> handle(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.GetOrderItemRequest command) {
    return effects().reply(
        ShipOrderItemApi.OrderItem
            .newBuilder()
            .setCustomerId(state.getCustomerId())
            .setOrderId(state.getOrderId())
            .setOrderItemId(state.getOrderItemId())
            .setSkuId(state.getSkuId())
            .setSkuName(state.getSkuName())
            .setOrderedUtc(state.getOrderedUtc())
            .setShippedUtc(state.getShippedUtc())
            .setBackOrderedUtc(state.getBackOrderedUtc())
            .build());
  }

  private List<?> eventsFor(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.CreateOrderItemCommand command) {
    var orderItemCreated = ShipOrderItemEntity.OrderItemCreated
        .newBuilder()
        .setCustomerId(command.getCustomerId())
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setOrderedUtc(command.getOrderedUtc())
        .build();

    var shipSkuItemRequired = ShipOrderItemEntity.SkuItemRequired
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setOrderItemId(command.getOrderItemId())
        .setSkuId(command.getSkuId())
        .build();

    return List.of(orderItemCreated, shipSkuItemRequired);
  }

  private ShipOrderItemEntity.JoinedToSkuItem eventFor(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.JoinToSkuItemCommand command) {
    return ShipOrderItemEntity.JoinedToSkuItem
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setOrderItemId(state.getOrderItemId())
        .setSkuId(state.getSkuId())
        .setSkuItemId(command.getSkuItemId())
        .setShippedUtc(command.getShippedUtc())
        .build();
  }

  private ShipOrderItemEntity.OrderItemBackOrdered eventFor(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.BackOrderOrderItemCommand command) {
    return ShipOrderItemEntity.OrderItemBackOrdered
        .newBuilder()
        .setBackOrderedUtc(timestampNow())
        .build();
  }

  private ShipOrderItemEntity.SkuItemReleasedFromOrder eventForReleaseSkuItem(ShipOrderItemEntity.OrderItemState state, ShipOrderItemApi.JoinToSkuItemCommand command) {
    return ShipOrderItemEntity.SkuItemReleasedFromOrder
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setOrderItemId(state.getOrderItemId())
        .setSkuId(state.getSkuId())
        .setSkuItemId(command.getSkuItemId())
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
