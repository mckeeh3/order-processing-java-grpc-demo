package io.mystore.stock.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.stock.entity.StockSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class StockSkuItemToStockOrderAction extends AbstractStockSkuItemToStockOrderAction {

  public StockSkuItemToStockOrderAction(ActionCreationContext creationContext) {
  }

  /** Handler for "OnJoinedToStockSkuItem". */
  @Override
  public Effect<Empty> onJoinedToStockSkuItem(StockSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    throw new RuntimeException("The command handler for `OnJoinedToStockSkuItem` is not implemented, yet");
  }

  /** Handler for "OnReleasedFromStockSkuItem". */
  @Override
  public Effect<Empty> onReleasedFromStockSkuItem(StockSkuItemEntity.ReleasedFromStockSkuItem releasedFromStockSkuItem) {
    throw new RuntimeException("The command handler for `OnReleasedFromStockSkuItem` is not implemented, yet");
  }

  /** Handler for "IgnoreOtherEvents". */
  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    throw new RuntimeException("The command handler for `IgnoreOtherEvents` is not implemented, yet");
  }
}
