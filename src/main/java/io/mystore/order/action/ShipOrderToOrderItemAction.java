package io.mystore.order.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.order.api.OrderItemApi;
import io.mystore.shipping.entity.ShipOrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderToOrderItemAction extends AbstractShipOrderToOrderItemAction {

  public ShipOrderToOrderItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderSkuShipped(ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    return effects().forward(components().orderItem().shippedOrderItem(toShippedOrderItem(orderSkuShipped)));
  }

  static OrderItemApi.ShippedOrderItemCommand toShippedOrderItem(ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    return OrderItemApi.ShippedOrderItemCommand
        .newBuilder()
        .setOrderId(orderSkuShipped.getOrderId())
        .setSkuId(orderSkuShipped.getSkuId())
        .setShippedUtc(orderSkuShipped.getShippedUtc())
        .build();
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
