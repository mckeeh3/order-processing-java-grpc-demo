package io.mystore.shipping.action;

import kalix.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShippingApi;
import io.mystore.shipping.entity.OrderSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemToShippingAction extends AbstractOrderSkuItemToShippingAction {
  static final Logger log = LoggerFactory.getLogger(OrderSkuItemToShippingAction.class);

  public OrderSkuItemToShippingAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onStockRequestedJoinToOrderAccepted(OrderSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return effects().forward(components().shipping().shippedOrderSkuItem(
        ShippingApi.ShippedOrderSkuItemCommand
            .newBuilder()
            .setOrderId(event.getOrderId())
            .setOrderSkuItemId(event.getOrderSkuItemId())
            .setSkuId(event.getSkuId())
            .setStockSkuItemId(event.getStockSkuItemId())
            .setShippedUtc(event.getShippedUtc())
            .build()));
  }

  @Override
  public Effect<Empty> onStockRequestedJoinToOrderRejected(OrderSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return effects().reply(Empty.getDefaultInstance()); // TODO remove this event handler
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStockAccepted(OrderSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return effects().forward(components().shipping().shippedOrderSkuItem(
        ShippingApi.ShippedOrderSkuItemCommand
            .newBuilder()
            .setOrderId(event.getOrderId())
            .setOrderSkuItemId(event.getOrderSkuItemId())
            .setSkuId(event.getSkuId())
            .setStockSkuItemId(event.getStockSkuItemId())
            .setShippedUtc(event.getShippedUtc())
            .build()));
  }

  @Override
  public Effect<Empty> onOrderRequestedJoinToStockRejected(OrderSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return effects().forward(components().shipping().releaseOrderSkuItem(
        ShippingApi.ReleaseOrderSkuItemCommand
            .newBuilder()
            .setOrderId(event.getOrderId())
            .setOrderSkuItemId(event.getOrderSkuItemId())
            .setSkuId(event.getSkuId())
            .setStockSkuItemId(event.getStockSkuItemId())
            .build()));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
