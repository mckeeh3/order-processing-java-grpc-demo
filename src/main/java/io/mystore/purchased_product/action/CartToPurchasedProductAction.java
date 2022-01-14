package io.mystore.purchased_product.action;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.prchased_product.api.PurchasedProductApi;
import io.mystore.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToPurchasedProductAction extends AbstractCartToPurchasedProductAction {

  public CartToPurchasedProductAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onCartCheckedOut(CartEntity.CartCheckedOut cartCheckedOut) {
    var results = cartCheckedOut.getCartState().getLineItemsList().stream()
        .map(lineItem -> PurchasedProductApi.PurchasedProduct.newBuilder()
            .setCustomerId(cartCheckedOut.getCartState().getCustomerId())
            .setCartId(cartCheckedOut.getCartState().getCartId())
            .setProductId(lineItem.getProductId())
            .setProductName(lineItem.getProductName())
            .setQuantity(lineItem.getQuantity())
            .setPurchasedUtc(cartCheckedOut.getCartState().getCheckedOutUtc())
            .build())
        .map(purchasedProduct -> components().purchasedProduct().addPurchasedProduct(purchasedProduct).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
