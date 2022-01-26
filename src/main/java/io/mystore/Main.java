package io.mystore;

import com.akkaserverless.javasdk.AkkaServerless;
import io.mystore.cart.entity.ShoppingCart;
import io.mystore.cart.view.CartsByCustomerByDateView;
import io.mystore.cart.view.CartsByCustomerView;
import io.mystore.cart.view.CartsByDateView;
import io.mystore.order.action.CartToOrderAction;
import io.mystore.order.action.OrderToOrderedItemsAction;
import io.mystore.order.entity.Order;
import io.mystore.order.entity.OrderItem;
import io.mystore.order.view.OrderedItemsByCustomerByDateView;
import io.mystore.order.view.OrderedItemsByDateView;
import io.mystore.order.view.OrderedItemsBySkuByDateView;
import io.mystore.order.view.OrdersByCustomerByDateView;
import io.mystore.order.view.OrdersByDateView;
import io.mystore.purchased_product.action.CartToPurchasedProductAction;
import io.mystore.purchased_product.entity.PurchasedProduct;
import io.mystore.purchased_product.view.PurchasedProductsByCustomerByDateView;
import io.mystore.purchased_product.view.PurchasedProductsByDateView;
import io.mystore.purchased_product.view.PurchasedProductsByProductByDateView;
import io.mystore.shipping.action.OrderToShipOrderAction;
import io.mystore.shipping.action.ShipOrderItemToShipSkuItemAction;
import io.mystore.shipping.action.ShipOrderToShipOrderItemAction;
import io.mystore.shipping.action.ShipSkuItemToShipOrderItemAction;
import io.mystore.shipping.action.ShipSkuItemToStockItemAction;
import io.mystore.shipping.action.StockItemFrontendAction;
import io.mystore.shipping.action.StockItemToShipSkuItemAction;
import io.mystore.shipping.entity.ShipOrder;
import io.mystore.shipping.entity.ShipOrderItem;
import io.mystore.shipping.entity.ShipSkuItem;
import io.mystore.shipping.entity.StockItem;
import io.mystore.shipping.view.AvailableShipSkuItemsView;
import io.mystore.shipping.view.AvailableStockItemsView;
import io.mystore.shipping.view.BackOrderedShipOrderItemsBySkuView;
import io.mystore.shipping.view.BackOrderedShipOrderItemsView;
import io.mystore.shipping.view.ShippedStockItemsView;
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
      OrderItem::new,
      PurchasedProduct::new,
      ShipOrder::new,
      ShipOrderItem::new,
      ShipSkuItem::new,
      ShoppingCart::new,
      StockItem::new,
      AvailableShipSkuItemsView::new,
      AvailableStockItemsView::new,
      BackOrderedShipOrderItemsView::new,
      BackOrderedShipOrderItemsBySkuView::new,
      CartToOrderAction::new,
      CartToPurchasedProductAction::new,
      CartsByCustomerView::new,
      CartsByCustomerByDateView::new,
      CartsByDateView::new,
      OrderToOrderedItemsAction::new,
      OrderToShipOrderAction::new,
      OrderedItemsByCustomerByDateView::new,
      OrderedItemsByDateView::new,
      OrderedItemsBySkuByDateView::new,
      OrdersByCustomerByDateView::new,
      OrdersByDateView::new,
      PurchasedProductsByCustomerByDateView::new,
      PurchasedProductsByDateView::new,
      PurchasedProductsByProductByDateView::new,
      ShipOrderItemToShipSkuItemAction::new,
      ShipOrderToShipOrderItemAction::new,
      ShipSkuItemToShipOrderItemAction::new,
      ShipSkuItemToStockItemAction::new,
      ShippedStockItemsView::new,
      StockItemFrontendAction::new,
      StockItemToShipSkuItemAction::new);
  }

  public static void main(String[] args) throws Exception {
    LOG.info("starting the Akka Serverless service");
    createAkkaServerless().start();
  }
}
