package io.mystore.order.action;

import kalix.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.order.api.OrderApi;
import io.mystore.shipping.entity.ShippingEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingToOrderAction extends AbstractShippingToOrderAction {
  static final Logger log = LoggerFactory.getLogger(ShippingToOrderAction.class);

  public ShippingToOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderShipped(ShippingEntity.OrderShipped event) {
    log.info("onOrderShipped: {}", event);

    return effects().forward(components().order().shippedOrder(toShippedOrder(event)));
  }

  @Override
  public Effect<Empty> onOrderReleased(ShippingEntity.OrderReleased event) {
    log.info("onOrderReleased: {}", event);

    return effects().forward(components().order().releasedOrder(toReleasedOrder(event)));
  }

  @Override
  public Effect<Empty> onOrderItemShipped(ShippingEntity.OrderItemShipped event) {
    log.info("onOrderItemShipped: {}", event);

    return effects().forward(components().order().shippedOrderItem(toShippedOrderItem(event)));
  }

  @Override
  public Effect<Empty> onOrderItemReleased(ShippingEntity.OrderItemReleased event) {
    log.info("onOrderItemReleased: {}", event);

    return effects().forward(components().order().releasedOrderItem(toShippedOrderItem(event)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  static OrderApi.ShippedOrderCommand toShippedOrder(ShippingEntity.OrderShipped event) {
    return OrderApi.ShippedOrderCommand
        .newBuilder()
        .setOrderId(event.getOrderId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static OrderApi.ReleasedOrderCommand toReleasedOrder(ShippingEntity.OrderReleased event) {
    return OrderApi.ReleasedOrderCommand
        .newBuilder()
        .setOrderId(event.getOrderId())
        .build();
  }

  static OrderApi.ShippedOrderSkuCommand toShippedOrderItem(ShippingEntity.OrderItemShipped event) {
    return OrderApi.ShippedOrderSkuCommand
        .newBuilder()
        .setOrderId(event.getOrderId())
        .setSkuId(event.getSkuId())
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  static OrderApi.ReleasedOrderSkuCommand toShippedOrderItem(ShippingEntity.OrderItemReleased event) {
    return OrderApi.ReleasedOrderSkuCommand
        .newBuilder()
        .setOrderId(event.getOrderId())
        .setSkuId(event.getSkuId())
        .build();
  }
}
