package io.shopping.cart.action;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Empty;

import io.shopping.cart.api.PurchasedProductApi;
import io.shopping.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToPurchasedProductAction extends AbstractCartToPurchasedProductAction {

  public CartToPurchasedProductAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onItemCheckedOut(CartEntity.ItemCheckedOut itemCheckedOut) {
    var purchasedProduct = PurchasedProductApi.PurchasedProduct
        .newBuilder()
        .setCustomerId(itemCheckedOut.getCustomerId())
        .setCartId(itemCheckedOut.getCartId())
        .setProductId(itemCheckedOut.getLineItem().getProductId())
        .setProductName(itemCheckedOut.getLineItem().getProductName())
        .setQuantity(itemCheckedOut.getLineItem().getQuantity())
        .build();

    return effects().forward(components().purchasedProduct().addPurchasedProduct(purchasedProduct));
  }
}
