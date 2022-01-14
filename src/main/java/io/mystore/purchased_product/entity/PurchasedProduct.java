package io.mystore.purchased_product.entity;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;

import io.mystore.prchased_product.api.PurchasedProductApi;
import io.mystore.prchased_product.api.PurchasedProductApi.GetPurchasedProductRequest;
import io.mystore.purchased_product.entity.PurchasedProductEntity.PurchasedProductState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** A value entity. */
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

  @Override
  public Effect<PurchasedProductApi.PurchasedProduct> getPurchasedProduct(PurchasedProductState state, GetPurchasedProductRequest command) {
    return effects().reply(toApi(state));
  }

  private PurchasedProductState updateState(PurchasedProductEntity.PurchasedProductState state, PurchasedProductApi.PurchasedProduct command) {
    return state
        .toBuilder()
        .setCustomerId(command.getCustomerId())
        .setCartId(command.getCartId())
        .setProductId(command.getProductId())
        .setProductName(command.getProductName())
        .setQuantity(command.getQuantity())
        .setPurchasedUtc(command.getPurchasedUtc())
        .build();
  }

  private PurchasedProductApi.PurchasedProduct toApi(PurchasedProductState state) {
    return PurchasedProductApi.PurchasedProduct
        .newBuilder()
        .setCustomerId(state.getCustomerId())
        .setCartId(state.getCartId())
        .setProductId(state.getProductId())
        .setProductName(state.getProductName())
        .setQuantity(state.getQuantity())
        .setPurchasedUtc(state.getPurchasedUtc())
        .build();
  }
}