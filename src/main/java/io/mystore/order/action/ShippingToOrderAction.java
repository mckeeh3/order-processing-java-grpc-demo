package io.mystore.order.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.order.api.OrderApi;
import io.mystore.shipping2.entity.ShippingEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingToOrderAction extends AbstractShippingToOrderAction {
  static final Logger log = LoggerFactory.getLogger(ShippingToOrderAction.class);

  public ShippingToOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderShipped(ShippingEntity.OrderShipped orderShipped) {
    log.info("onOrderShipped: {}", orderShipped);

    return effects().forward(components().order().shippedOrder(toShippedOrder(orderShipped)));
  }

  @Override
  public Effect<Empty> onOrderItemShipped(ShippingEntity.OrderItemShipped orderItemShipped) {
    log.info("onOrderItemShipped: {}", orderItemShipped);

    return effects().forward(components().order().shippedOrderItem(toShippedOrderItem(orderItemShipped)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  static OrderApi.ShippedOrderCommand toShippedOrder(ShippingEntity.OrderShipped orderShipped) {
    return OrderApi.ShippedOrderCommand
        .newBuilder()
        .setOrderId(orderShipped.getOrderId())
        .setShippedUtc(orderShipped.getShippedUtc())
        .build();
  }

  static OrderApi.ShippedOrderSkuCommand toShippedOrderItem(ShippingEntity.OrderItemShipped orderItemShipped) {
    return OrderApi.ShippedOrderSkuCommand
        .newBuilder()
        .setOrderId(orderItemShipped.getOrderId())
        .setSkuId(orderItemShipped.getSkuId())
        .setShippedUtc(orderItemShipped.getShippedUtc())
        .build();
  }
}
