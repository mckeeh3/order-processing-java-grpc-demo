package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.ShipOrderItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItem extends AbstractShipOrderItem {

  public ShipOrderItem(ValueEntityContext context) {
  }

  @Override
  public ShipOrderItemEntity.ShipOrderItemState emptyState() {
    return ShipOrderItemEntity.ShipOrderItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addShipOrderItem(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.ShipOrderItem command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<ShipOrderItemApi.ShipOrderItem> getShipOrderItem(ShipOrderItemEntity.ShipOrderItemState state, ShipOrderItemApi.GetShipOrderItemRequest command) {
    return effects().reply(toApi(state));
  }

  private ShipOrderItemEntity.ShipOrderItemState updateState(ShipOrderItemEntity.ShipOrderItemState state, io.mystore.shipping.api.ShipOrderItemApi.ShipOrderItem command) {
    return state
        .toBuilder()
        .setCustomerId(command.getCustomerId())
        .setOrderId(command.getOrderId())
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setQuantity(command.getQuantity())
        .build();
  }

  private ShipOrderItemApi.ShipOrderItem toApi(ShipOrderItemEntity.ShipOrderItemState state) {
    return ShipOrderItemApi.ShipOrderItem
        .newBuilder()
        .setCustomerId(state.getCustomerId())
        .setOrderId(state.getOrderId())
        .setSkuId(state.getSkuId())
        .setSkuName(state.getSkuName())
        .setQuantity(state.getQuantity())
        .build();
  }
}
