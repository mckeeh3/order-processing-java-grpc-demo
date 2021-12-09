package io.shopping.cart.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.shopping.cart.api.PurchasedProductApi;
import io.shopping.cart.entity.PurchasedProductEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ProductsViewImpl extends AbstractProductsView {
  private static final Logger log = LoggerFactory.getLogger(ProductsViewImpl.class);
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  public ProductsViewImpl(ViewContext context) {
  }

  @Override
  public PurchasedProductApi.PurchasedProduct emptyState() {
    return PurchasedProductApi.PurchasedProduct.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<PurchasedProductApi.PurchasedProduct> processItemCheckedOut(PurchasedProductApi.PurchasedProduct state, PurchasedProductEntity.PurchasedProductState event) {
    if (state.getProductId().isEmpty()) {
      log.info("+Insert {}, {}, {}", event.getCustomerId(), event.getCartId(), event.getProductId());
    } else {
      log.info("-Update {}, {}, {}", state.getCustomerId(), state.getCartId(), state.getProductId());
      log.info(">Update {}, {}, {}", event.getCustomerId(), event.getCartId(), event.getProductId());
    }
    return effects().updateState(
        state.toBuilder()
            .setCustomerId(event.getCustomerId())
            .setCartId(event.getCartId())
            .setProductId(event.getProductId())
            .setProductName(event.getProductName())
            .setQuantity(event.getQuantity())
            .setPurchasedUtc(event.getPurchasedUtc())
            .build());
  }

  @Override
  public UpdateEffect<PurchasedProductApi.PurchasedProduct> ignoreOtherEvents(PurchasedProductApi.PurchasedProduct state, Any any) {
    return effects().ignore();
  }

  static String toUtc(Timestamp timestamp) {
    return dateFormat.format(new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000));
  }
}
