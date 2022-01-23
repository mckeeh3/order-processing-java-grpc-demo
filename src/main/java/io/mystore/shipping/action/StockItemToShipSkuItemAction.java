package io.mystore.shipping.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.ShipSkuItemApi;
import io.mystore.shipping.entity.StockItemEntity;
import io.mystore.shipping.entity.StockItemEntity.StockItemCreated;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockItemToShipSkuItemAction extends AbstractStockItemToShipSkuItemAction {

  public StockItemToShipSkuItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onCreateStockItem(StockItemEntity.StockItemCreated stockItemCreated) {
    return effects().forward(components().shipSkuItem().createSkuItem(toCreateSkuItemFromStock(stockItemCreated)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private ShipSkuItemApi.CreateSkuItemFromStock toCreateSkuItemFromStock(StockItemCreated stockItemCreated) {
    return ShipSkuItemApi.CreateSkuItemFromStock
        .newBuilder()
        .setSkuId(stockItemCreated.getSkuId())
        .setSkuItemId(stockItemCreated.getSkuItemId())
        .setSkuName(stockItemCreated.getSkuName())
        .build();
  }
}
