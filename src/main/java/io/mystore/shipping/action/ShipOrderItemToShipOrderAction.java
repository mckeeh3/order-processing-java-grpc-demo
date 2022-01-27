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
  public Effect<Empty> onJoinedSkuItemToOrderItem(ShipOrderItemEntity.JoinedSkuItemToOrderItem joinedSkuItemToOrderItem) {
    log.info("onJoinedSkuItemToOrderItem: JoinedSkuItemToOrderItem {}", joinedSkuItemToOrderItem);

    return effects().forward(components().shipOrder().shippedOrderItem(toShippedOrderItemCommand(joinedSkuItemToOrderItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private ShipOrderApi.ShippedOrderItemCommand toShippedOrderItemCommand(ShipOrderItemEntity.JoinedSkuItemToOrderItem joinedSkuItemToOrderItem) {
    return ShipOrderApi.ShippedOrderItemCommand
        .newBuilder()
        .setOrderId(joinedSkuItemToOrderItem.getOrderId())
        .setOrderItemId(joinedSkuItemToOrderItem.getOrderItemId())
        .setSkuId(joinedSkuItemToOrderItem.getSkuId())
        .setSkuItemId(joinedSkuItemToOrderItem.getSkuItemId())
        .setShippedUtc(joinedSkuItemToOrderItem.getShippedUtc())
        .build();
  }
}
