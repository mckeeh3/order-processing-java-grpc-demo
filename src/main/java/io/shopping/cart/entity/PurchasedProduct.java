package io.shopping.cart.entity;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;
import io.shopping.cart.api.PurchasedProductApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** A value entity. */
public class PurchasedProduct extends AbstractPurchasedProduct {
  @SuppressWarnings("unused")
  private final String entityId;

  public PurchasedProduct(ValueEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public PurchasedProductEntity.PurchasedProductState emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty entity state");
  }

  @Override
  public Effect<Empty> addPurchasedProduct(PurchasedProductEntity.PurchasedProductState currentState, PurchasedProductApi.PurchasedProduct purchasedProduct) {
    return effects().error("The command handler for `AddPurchasedProduct` is not implemented, yet");
  }
}
