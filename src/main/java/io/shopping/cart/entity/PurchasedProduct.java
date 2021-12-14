package io.shopping.cart.entity;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.shopping.cart.api.PurchasedProductApi;
import io.shopping.cart.entity.PurchasedProductEntity.PurchasedProductState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class PurchasedProduct extends AbstractPurchasedProduct {
  private static final Logger log = LoggerFactory.getLogger(PurchasedProduct.class);

  public PurchasedProduct(ValueEntityContext context) {
  }

  @Override
  public PurchasedProductEntity.PurchasedProductState emptyState() {
    return PurchasedProductEntity.PurchasedProductState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addPurchasedProduct(PurchasedProductEntity.PurchasedProductState state, PurchasedProductApi.PurchasedProduct command) {
    log.info("{}", state);
    log.info("{}", command);
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
        .setPurchasedUtc(command.getPurchasedUtc())
        .build();
  }
}
