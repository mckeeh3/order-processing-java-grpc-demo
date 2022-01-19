package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity.Effect;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import io.mystore.shipping.api.ShipSkuItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An event sourced entity. */
public class ShoppingCart extends AbstractShoppingCart {

  @SuppressWarnings("unused")
  private final String entityId;

  public ShoppingCart(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public ShipSkuItemEntity.ShipSkuItemState emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty entity state");
  }

  @Override
  public Effect<Empty> addItem(ShipSkuItemEntity.ShipSkuItemState currentState, ShipSkuItemApi.AddSkuItem addSkuItem) {
    return effects().error("The command handler for `AddItem` is not implemented, yet");
  }

  @Override
  public Effect<Empty> addShipOrderItem(ShipSkuItemEntity.ShipSkuItemState currentState, ShipSkuItemApi.AddShipOrderItemToSku addShipOrderItemToSku) {
    return effects().error("The command handler for `AddShipOrderItem` is not implemented, yet");
  }

  @Override
  public ShipSkuItemEntity.ShipSkuItemState skuItemAdded(ShipSkuItemEntity.ShipSkuItemState currentState, ShipSkuItemEntity.SkuItemAdded skuItemAdded) {
    throw new RuntimeException("The event handler for `SkuItemAdded` is not implemented, yet");
  }
  @Override
  public ShipSkuItemEntity.ShipSkuItemState shipOrderItemAdded(ShipSkuItemEntity.ShipSkuItemState currentState, ShipSkuItemEntity.ShipOrderItemAdded shipOrderItemAdded) {
    throw new RuntimeException("The event handler for `ShipOrderItemAdded` is not implemented, yet");
  }

}
