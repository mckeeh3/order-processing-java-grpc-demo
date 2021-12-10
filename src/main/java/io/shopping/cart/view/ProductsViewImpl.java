package io.shopping.cart.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  public PurchasedProductsView.PurchasedProduct emptyState() {
    return PurchasedProductsView.PurchasedProduct.getDefaultInstance();
  }

  @Override
  public UpdateEffect<PurchasedProductsView.PurchasedProduct> processItemCheckedOut(PurchasedProductsView.PurchasedProduct state, PurchasedProductEntity.PurchasedProductState command) {
    if (state.getProductId().isEmpty()) {
      log.info("+Insert {}, {}, {}, {}", command.getCustomerId(), command.getCartId(), command.getProductId(), toUtc(command.getPurchasedUtc()));
    } else {
      log.info("-Update {}, {}, {}", state.getCustomerId(), state.getCartId(), state.getProductId());
      log.info(">Update {}, {}, {}", command.getCustomerId(), command.getCartId(), command.getProductId());
    }
    return effects().updateState(
        state.toBuilder()
            .setCustomerId(command.getCustomerId())
            .setCartId(command.getCartId())
            .setProductId(command.getProductId())
            .setProductName(command.getProductName())
            .setQuantity(command.getQuantity())
            .setPurchasedUtc(toUtc(command.getPurchasedUtc()))
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
