package io.mystore.stock.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.stock.entity.StockOrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class StockOrderToStockSkuItemAction extends AbstractStockOrderToStockSkuItemAction {

  public StockOrderToStockSkuItemAction(ActionCreationContext creationContext) {}

  /** Handler for "OnStockOrderCreated". */
  @Override
  public Effect<Empty> onStockOrderCreated(StockOrderEntity.StockOrderCreated stockOrderCreated) {
    throw new RuntimeException("The command handler for `OnStockOrderCreated` is not implemented, yet");
  }
  /** Handler for "IgnoreOtherEvents". */
  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    throw new RuntimeException("The command handler for `IgnoreOtherEvents` is not implemented, yet");
  }
}
