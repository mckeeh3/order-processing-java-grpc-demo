package io.mystore.order.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.order.api.OrderApi;
import io.mystore.shipping.entity.ShipOrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderToOrderAction extends AbstractShipOrderToOrderAction {

  public ShipOrderToOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderShipped(ShipOrderEntity.OrderShipped orderShipped) {
    return effects().forward(components().order().shippedOrder(toShippedOrder(orderShipped)));
  }

  @Override
  public Effect<Empty> onOrderSkuShipped(ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    return effects().forward(components().order().shippedOrderItem(toShippedOrderItem(orderSkuShipped)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private OrderApi.ShippedOrderCommand toShippedOrder(ShipOrderEntity.OrderShipped orderShipped) {
    return OrderApi.ShippedOrderCommand
        .newBuilder()
        .setOrderId(orderShipped.getOrderId())
        .setShippedUtc(orderShipped.getShippedUtc())
        .build();
  }

  private OrderApi.ShippedOrderItemCommand toShippedOrderItem(ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    return OrderApi.ShippedOrderItemCommand
        .newBuilder()
        .setOrderId(orderSkuShipped.getOrderId())
        .setSkuId(orderSkuShipped.getSkuId())
        .setShippedUtc(orderSkuShipped.getShippedUtc())
        .build();
  }
}
