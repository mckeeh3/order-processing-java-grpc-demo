package io.mystore.shipping2.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping2.api.ShippingApi;
import io.mystore.shipping2.entity.OrderSkuItemEntity;
import io.mystore.shipping2.entity.OrderSkuItemEntity.JoinedToStockSkuItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemToShippingAction extends AbstractOrderSkuItemToShippingAction {
  static final Logger log = LoggerFactory.getLogger(OrderSkuItemToShippingAction.class);

  public OrderSkuItemToShippingAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onJoinedToStockSkuItem(OrderSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    log.info("onJoinedToStockSkuItem: JoinedToStockSkuItem {}", joinedToStockSkuItem);

    return effects().forward(components().shipping().shippedOrderSkuItem(toShippedOrderSkuItemCommand(joinedToStockSkuItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private ShippingApi.ShippedOrderSkuItemCommand toShippedOrderSkuItemCommand(JoinedToStockSkuItem joinedToStockSkuItem) {
    return ShippingApi.ShippedOrderSkuItemCommand
        .newBuilder()
        .setOrderId(joinedToStockSkuItem.getOrderId())
        .setOrderSkuItemId(joinedToStockSkuItem.getOrderSkuItemId())
        .setSkuId(joinedToStockSkuItem.getSkuId())
        .setStockSkuItemId(joinedToStockSkuItem.getStockSkuItemId())
        .setShippedUtc(joinedToStockSkuItem.getShippedUtc())
        .build();
  }
}
