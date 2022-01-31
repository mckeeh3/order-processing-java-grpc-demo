package io.mystore.shipping.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.StockItemApi;
import io.mystore.shipping.entity.ShipSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItemToStockItemAction extends AbstractShipSkuItemToStockItemAction {

  public ShipSkuItemToStockItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onJoinedToOrderItem(ShipSkuItemEntity.JoinedToOrderItem shipOrderItemAdded) {
    return effects().forward(components().stockItem().ship(toStockItem(shipOrderItemAdded)));
  }

  @Override
  public Effect<Empty> onReleasedFromOrderItem(ShipSkuItemEntity.ReleasedFromOrderItem releasedFromOrderItem) {
    return effects().forward(components().stockItem().release(toStockItem(releasedFromOrderItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private StockItemApi.ShipStockItem toStockItem(ShipSkuItemEntity.JoinedToOrderItem joinedToOrderItem) {
    return StockItemApi.ShipStockItem
        .newBuilder()
        .setSkuId(joinedToOrderItem.getSkuId())
        .setSkuItemId(joinedToOrderItem.getSkuItemId())
        .setOrderId(joinedToOrderItem.getOrderId())
        .setOrderItemId(joinedToOrderItem.getOrderItemId())
        .setShippedUtc(joinedToOrderItem.getShippedUtc())
        .build();
  }

  private StockItemApi.ReleaseStockItem toStockItem(ShipSkuItemEntity.ReleasedFromOrderItem releasedFromOrderItem) {
    return StockItemApi.ReleaseStockItem
        .newBuilder()
        .setSkuId(releasedFromOrderItem.getSkuId())
        .setSkuItemId(releasedFromOrderItem.getSkuItemId())
        .setOrderId(releasedFromOrderItem.getOrderId())
        .setOrderItemId(releasedFromOrderItem.getOrderItemId())
        .build();
  }
}
