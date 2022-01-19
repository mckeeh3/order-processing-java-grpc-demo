package io.mystore;

import com.akkaserverless.javasdk.AkkaServerless;
import io.mystore.cart.view.CartsByCustomerByDateView;
import io.mystore.cart.view.CartsByCustomerView;
import io.mystore.cart.view.CartsByDateView;
import io.mystore.order.action.CartToOrderAction;
import io.mystore.order.entity.Order;
import io.mystore.order.view.OrdersByCustomerByDateView;
import io.mystore.order.view.OrdersByDateView;
import io.mystore.purchased_product.action.CartToPurchasedProductAction;
import io.mystore.purchased_product.entity.PurchasedProduct;
import io.mystore.purchased_product.view.PurchasedProductsByCustomerByDateView;
import io.mystore.purchased_product.view.PurchasedProductsByDateView;
import io.mystore.purchased_product.view.PurchasedProductsByProductByDateView;
import io.mystore.shipping.action.CartToShipOrderAction;
import io.mystore.shipping.action.CartToShipOrderItemAction;
import io.mystore.shipping.entity.ShipOrder;
import io.mystore.shipping.entity.ShipOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public final class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static AkkaServerless createAkkaServerless() {
    // The AkkaServerlessFactory automatically registers any generated Actions, Views or Entities,
    // and is kept up-to-date with any changes in your protobuf definitions.
    // If you prefer, you may remove this and manually register these components in a
    // `new AkkaServerless()` instance.
    return AkkaServerlessFactory.withComponents(
      Order::new,
      PurchasedProduct::new,
      ShipOrder::new,
      ShipOrderItem::new,
      io.mystore.shipping.entity.ShoppingCart::new,
      io.mystore.cart.entity.ShoppingCart::new,
      CartToOrderAction::new,
      CartToPurchasedProductAction::new,
      CartToShipOrderAction::new,
      CartToShipOrderItemAction::new,
      CartsByCustomerView::new,
      CartsByCustomerByDateView::new,
      CartsByDateView::new,
      OrdersByCustomerByDateView::new,
      OrdersByDateView::new,
      PurchasedProductsByCustomerByDateView::new,
      PurchasedProductsByDateView::new,
      PurchasedProductsByProductByDateView::new);
  }

  public static void main(String[] args) throws Exception {
    LOG.info("starting the Akka Serverless service");
    createAkkaServerless().start();
  }
}
