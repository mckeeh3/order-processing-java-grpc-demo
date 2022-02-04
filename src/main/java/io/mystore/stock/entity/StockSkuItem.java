package io.mystore.stock.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.stock.api.StockSkuItemApi;
import io.mystore.stock.api.StockSkuItemEvents;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItem extends AbstractStockSkuItem {
  static final Logger log = LoggerFactory.getLogger(StockSkuItem.class);

  public StockSkuItem(EventSourcedEntityContext context) {
  }

  @Override
  public StockSkuItemEntityX.StockSkuItemState emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty entity state");
  }

  @Override
  public Effect<Empty> createStockSkuItem(StockSkuItemEntityX.StockSkuItemState state, StockSkuItemApi.CreateStockSkuItemCommand command) {
    return effects().error("The command handler for `CreateStockSkuItem` is not implemented, yet");
  }

  @Override
  public Effect<Empty> joinStockSkuItem(StockSkuItemEntityX.StockSkuItemState state, StockSkuItemEvents.JoinStockSkuItemCommand command) {
    return effects().error("The command handler for `JoinStockSkuItem` is not implemented, yet");
  }

  @Override
  public Effect<Empty> releaseStockSkuItem(StockSkuItemEntityX.StockSkuItemState state, StockSkuItemEvents.ReleaseStockSkuItemCommand command) {
    return effects().error("The command handler for `ReleaseStockSkuItem` is not implemented, yet");
  }

  @Override
  public Effect<StockSkuItemApi.StockSkuItem> getStockSkuItem(StockSkuItemEntityX.StockSkuItemState state, StockSkuItemApi.GetStockSKuItemRequest command) {
    return effects().error("The command handler for `GetStockSkuItem` is not implemented, yet");
  }

  @Override
  public StockSkuItemEntityX.StockSkuItemState stockSkuItemCreated(StockSkuItemEntityX.StockSkuItemState state, StockSkuItemEntityX.StockSkuItemCreated event) {
    throw new RuntimeException("The event handler for `StockSkuItemCreated` is not implemented, yet");
  }

  @Override
  public StockSkuItemEntityX.StockSkuItemState joinedToStockSkuItem(StockSkuItemEntityX.StockSkuItemState state, StockSkuItemEntityX.JoinedToStockSkuItem event) {
    throw new RuntimeException("The event handler for `JoinedToStockSkuItem` is not implemented, yet");
  }

  @Override
  public StockSkuItemEntityX.StockSkuItemState releasedFromStockSkuItem(StockSkuItemEntityX.StockSkuItemState state, StockSkuItemEntityX.ReleasedFromStockSkuItem event) {
    throw new RuntimeException("The event handler for `ReleasedFromStockSkuItem` is not implemented, yet");
  }
}
