package io.shopping.cart.view;

import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.akkaserverless.javasdk.view.View;
import io.shopping.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ProductsViewImpl extends AbstractProductsView {
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  public ProductsViewImpl(ViewContext context) {
  }

  @Override
  public PurchasedProductsView.PurchasedProduct emptyState() {
    return PurchasedProductsView.PurchasedProduct.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<PurchasedProductsView.PurchasedProduct> processItemCheckedOut(PurchasedProductsView.PurchasedProduct state, CartEntity.ItemCheckedOut itemCheckedOut) {
    return effects().updateState(
        state.toBuilder()
            .setCustomerId(itemCheckedOut.getCustomerId())
            .setCartId(itemCheckedOut.getCartId())
            .setProductId(itemCheckedOut.getLineItem().getProductId())
            .setProductName(itemCheckedOut.getLineItem().getProductName())
            .setQuantity(itemCheckedOut.getLineItem().getQuantity())
            .setPurchasedUtc(toUtc(itemCheckedOut.getCheckedOutUtc()))
            .build());
  }

  @Override
  public UpdateEffect<PurchasedProductsView.PurchasedProduct> ignoreOtherEvents(PurchasedProductsView.PurchasedProduct state, Any any) {
    return effects().ignore();
  }

  static String toUtc(Timestamp timestamp) {
    return dateFormat.format(new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000));
  }
}
