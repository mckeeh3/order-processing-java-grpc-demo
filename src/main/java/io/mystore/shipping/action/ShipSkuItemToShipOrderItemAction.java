package io.mystore.shipping.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.entity.ShipSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class ShipSkuItemToShipOrderItemAction extends AbstractShipSkuItemToShipOrderItemAction {

  public ShipSkuItemToShipOrderItemAction(ActionCreationContext creationContext) {}

  /** Handler for "OnSkuItemCreated". */
  @Override
  public Effect<Empty> onSkuItemCreated(ShipSkuItemEntity.SkuItemCreated skuItemCreated) {
    throw new RuntimeException("The command handler for `OnSkuItemCreated` is not implemented, yet");
  }
  /** Handler for "OnShipOrderItemAdded". */
  @Override
  public Effect<Empty> onShipOrderItemAdded(ShipSkuItemEntity.ShipOrderItemAdded shipOrderItemAdded) {
    throw new RuntimeException("The command handler for `OnShipOrderItemAdded` is not implemented, yet");
  }
  /** Handler for "IgnoreOtherEvents". */
  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    throw new RuntimeException("The command handler for `IgnoreOtherEvents` is not implemented, yet");
  }
}
