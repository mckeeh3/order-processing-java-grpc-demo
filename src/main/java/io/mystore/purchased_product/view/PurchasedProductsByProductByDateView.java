package io.mystore.purchased_product.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.purchased_product.entity.PurchasedProductEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class PurchasedProductsByProductByDateView extends AbstractPurchasedProductsByProductByDateView {

  public PurchasedProductsByProductByDateView(ViewContext context) {
  }

  @Override
  public PurchasedProductsModel.PurchasedProduct emptyState() {
    return PurchasedProductsModel.PurchasedProduct.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<PurchasedProductsModel.PurchasedProduct> processItemCheckedOut(PurchasedProductsModel.PurchasedProduct state, PurchasedProductEntity.PurchasedProductState command) {
    return effects().updateState(
        state.toBuilder()
            .setCustomerId(command.getCustomerId())
            .setOrderId(command.getCartId())
            .setSkuId(command.getSkuId())
            .setSkuName(command.getSkuName())
            .setQuantity(command.getQuantity())
            .setPurchasedUtc(command.getPurchasedUtc())
            .build());
  }

  @Override
  public View.UpdateEffect<PurchasedProductsModel.PurchasedProduct> ignoreOtherEvents(PurchasedProductsModel.PurchasedProduct state, Any any) {
    return effects().ignore();
  }
}
