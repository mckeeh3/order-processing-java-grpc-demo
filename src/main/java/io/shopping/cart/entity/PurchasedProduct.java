package io.shopping.cart.entity;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;

import io.shopping.cart.api.PurchasedProductApi;
import io.shopping.cart.entity.PurchasedProductEntity.PurchasedProductState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class PurchasedProduct extends AbstractPurchasedProduct {

  public PurchasedProduct(ValueEntityContext context) {
  }

  @Override
  public PurchasedProductEntity.PurchasedProductState emptyState() {
    return PurchasedProductEntity.PurchasedProductState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addPurchasedProduct(PurchasedProductEntity.PurchasedProductState state, PurchasedProductApi.PurchasedProduct command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private PurchasedProductState updateState(PurchasedProductEntity.PurchasedProductState state, PurchasedProductApi.PurchasedProduct command) {
    return state
        .toBuilder()
        .setCustomerId(command.getCustomerId())
        .setCartId(command.getCartId())
        .setProductId(command.getProductId())
        .setProductName(command.getProductName())
        .setQuantity(command.getQuantity())
        .build();
  }
}
