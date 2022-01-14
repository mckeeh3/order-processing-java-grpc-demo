package io.mystore.purchased_product.view;

import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.purchased_product.entity.PurchasedProductEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class PurchasedProductsView extends AbstractPurchasedProductsView {

  public PurchasedProductsView(ViewContext context) {
  }

  @Override
  public PurchasedProductsOuter.PurchasedProduct emptyState() {
    return PurchasedProductsOuter.PurchasedProduct.getDefaultInstance();
  }

  @Override
  public UpdateEffect<PurchasedProductsOuter.PurchasedProduct> processItemCheckedOut(PurchasedProductsOuter.PurchasedProduct state, PurchasedProductEntity.PurchasedProductState command) {
    return effects().updateState(
        state.toBuilder()
            .setCustomerId(command.getCustomerId())
            .setCartId(command.getCartId())
            .setProductId(command.getProductId())
            .setProductName(command.getProductName())
            .setQuantity(command.getQuantity())
            .setPurchasedUtc(command.getPurchasedUtc())
            .build());
  }

  @Override
  public UpdateEffect<PurchasedProductsOuter.PurchasedProduct> ignoreOtherEvents(PurchasedProductsOuter.PurchasedProduct state, Any any) {
    return effects().ignore();
  }
}
