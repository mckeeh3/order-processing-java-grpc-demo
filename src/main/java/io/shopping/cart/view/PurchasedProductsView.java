package io.shopping.cart.view;

import com.akkaserverless.javasdk.view.ViewContext;
import com.akkaserverless.javasdk.view.View;
import io.shopping.cart.entity.PurchasedProductEntity;
import com.google.protobuf.Any;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class PurchasedProductsView extends AbstractPurchasedProductsView {

  public PurchasedProductsView(ViewContext context) {}

  @Override
  public PurchasedProductsOuter.PurchasedProduct emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state");
  }

  @Override
  public View.UpdateEffect<PurchasedProductsOuter.PurchasedProduct> processItemCheckedOut(
    PurchasedProductsOuter.PurchasedProduct state, PurchasedProductEntity.PurchasedProductState purchasedProductState) {
    throw new UnsupportedOperationException("Update handler for 'ProcessItemCheckedOut' not implemented yet");
  }
  @Override
  public View.UpdateEffect<PurchasedProductsOuter.PurchasedProduct> ignoreOtherEvents(
    PurchasedProductsOuter.PurchasedProduct state, Any any) {
    throw new UnsupportedOperationException("Update handler for 'IgnoreOtherEvents' not implemented yet");
  }
}

