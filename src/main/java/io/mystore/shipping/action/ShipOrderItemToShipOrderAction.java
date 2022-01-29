package io.mystore.shipping.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShipOrderApi;
import io.mystore.shipping.entity.ShipOrderItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItemToShipOrderAction extends AbstractShipOrderItemToShipOrderAction {
  static final Logger log = LoggerFactory.getLogger(ShipOrderItemToShipOrderAction.class);

  public ShipOrderItemToShipOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onJoinedToSkuItem(ShipOrderItemEntity.JoinedToSkuItem joinedToSkuItem) {
    log.info("onJoinedToSkuItem: JoinedToSkuItem {}", joinedToSkuItem);

    return effects().forward(components().shipOrder().shippedOrderItem(toJoinedToSkuItem(joinedToSkuItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private ShipOrderApi.ShippedOrderItemCommand toJoinedToSkuItem(ShipOrderItemEntity.JoinedToSkuItem joinedToSkuItem) {
    return ShipOrderApi.ShippedOrderItemCommand
        .newBuilder()
        .setOrderId(joinedToSkuItem.getOrderId())
        .setOrderItemId(joinedToSkuItem.getOrderItemId())
        .setSkuId(joinedToSkuItem.getSkuId())
        .setSkuItemId(joinedToSkuItem.getSkuItemId())
        .setShippedUtc(joinedToSkuItem.getShippedUtc())
        .build();
  }
}
