package io.mystore.stock.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.entity.ShipOrderItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class OrderSkuItemToStockSkuItemAction extends AbstractOrderSkuItemToStockSkuItemAction {

  public OrderSkuItemToStockSkuItemAction(ActionCreationContext creationContext) {}

  /** Handler for "OnStockSkuItemRequired". */
  @Override
  public Effect<Empty> onStockSkuItemRequired(ShipOrderItemEntity.SkuItemRequired skuItemRequired) {
    throw new RuntimeException("The command handler for `OnStockSkuItemRequired` is not implemented, yet");
  }
  /** Handler for "OnStockSkuItemReleased". */
  @Override
  public Effect<Empty> onStockSkuItemReleased(ShipOrderItemEntity.SkuItemReleasedFromOrder skuItemReleasedFromOrder) {
    throw new RuntimeException("The command handler for `OnStockSkuItemReleased` is not implemented, yet");
  }
  /** Handler for "IgnoreOtherEvents". */
  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    throw new RuntimeException("The command handler for `IgnoreOtherEvents` is not implemented, yet");
  }
}
