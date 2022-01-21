package io.mystore.shipping.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.entity.ShipOrderItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class ShipOrderItemToShipSkuItemAction extends AbstractShipOrderItemToShipSkuItemAction {

  public ShipOrderItemToShipSkuItemAction(ActionCreationContext creationContext) {}

  /** Handler for "OnShipSkuItemRequired". */
  @Override
  public Effect<Empty> onShipSkuItemRequired(ShipOrderItemEntity.ShipSkuItemRequired shipSkuItemRequired) {
    throw new RuntimeException("The command handler for `OnShipSkuItemRequired` is not implemented, yet");
  }
  /** Handler for "OnSkuItemReleasedFromOrder". */
  @Override
  public Effect<Empty> onSkuItemReleasedFromOrder(ShipOrderItemEntity.SkuItemReleasedFromOrder skuItemReleasedFromOrder) {
    throw new RuntimeException("The command handler for `OnSkuItemReleasedFromOrder` is not implemented, yet");
  }
  /** Handler for "IgnoreOtherEvents". */
  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    throw new RuntimeException("The command handler for `IgnoreOtherEvents` is not implemented, yet");
  }
}
