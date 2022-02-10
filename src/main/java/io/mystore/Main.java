package io.mystore;

import com.akkaserverless.javasdk.AkkaServerless;
import io.mystore.cart.entity.ShoppingCart;
import io.mystore.cart.view.CartsByCustomerByDateView;
import io.mystore.cart.view.CartsByCustomerView;
import io.mystore.cart.view.CartsByDateView;
import io.mystore.order.action.CartToOrderAction;
import io.mystore.order.action.OrderToOrderItemAction;
import io.mystore.order.action.ShippingToOrderAction;
import io.mystore.order.entity.Order;
import io.mystore.order.entity.OrderItem;
import io.mystore.order.view.OrderedItemsByCustomerByDateView;
import io.mystore.order.view.OrderedItemsByDateView;
import io.mystore.order.view.OrderedItemsBySkuByDateView;
import io.mystore.order.view.OrdersByCustomerByDateView;
import io.mystore.order.view.OrdersByDateView;
import io.mystore.shipping2.action.OrderSkuItemToShippingAction;
import io.mystore.shipping2.action.OrderToShippingAction;
import io.mystore.shipping2.action.ShippingToOrderSkuItemAction;
import io.mystore.shipping2.action.StockSkuItemToOrderSkuItemAction;
import io.mystore.shipping2.entity.OrderSkuItem;
import io.mystore.shipping2.entity.Shipping;
import io.mystore.shipping2.view.OrderSkuItemsBackOrderedBySkuView;
import io.mystore.shipping2.view.OrderSkuItemsByOrderView;
import io.mystore.shipping2.view.OrderSkuItemsShippedBySkuView;
import io.mystore.shipping2.view.ShippingByCustomerByDateView;
import io.mystore.shipping2.view.ShippingByDateView;
import io.mystore.stock.action.OrderSkuItemToStockSkuItemAction;
import io.mystore.stock.action.ShippableSkuItemsTimerAction;
import io.mystore.stock.action.StockOrderToStockSkuItemAction;
import io.mystore.stock.action.StockSkuItemToStockOrderAction;
import io.mystore.stock.entity.ShippableSkuItemsTimer;
import io.mystore.stock.entity.StockOrder;
import io.mystore.stock.entity.StockSkuItem;
import io.mystore.stock.view.StockOrdersAvailableView;
import io.mystore.stock.view.StockOrdersShippedView;
import io.mystore.stock.view.StockSkuItemsAvailableView;
import io.mystore.stock.view.StockSkuItemsShippedView;
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
      OrderSkuItem::new,
      ShippableSkuItemsTimer::new,
      Shipping::new,
      ShoppingCart::new,
      StockOrder::new,
      StockSkuItem::new,
      CartToOrderAction::new,
      CartsByCustomerView::new,
      CartsByCustomerByDateView::new,
      CartsByDateView::new,
      OrderSkuItemToShippingAction::new,
      OrderSkuItemToStockSkuItemAction::new,
      OrderSkuItemsBackOrderedBySkuView::new,
      OrderSkuItemsByOrderView::new,
      OrderSkuItemsShippedBySkuView::new,
      OrderToOrderItemAction::new,
      OrderToShippingAction::new,
      OrderedItemsByCustomerByDateView::new,
      OrderedItemsByDateView::new,
      OrderedItemsBySkuByDateView::new,
      OrdersByCustomerByDateView::new,
      OrdersByDateView::new,
      ShippableSkuItemsTimerAction::new,
      ShippingByCustomerByDateView::new,
      ShippingByDateView::new,
      ShippingToOrderAction::new,
      ShippingToOrderSkuItemAction::new,
      StockOrderToStockSkuItemAction::new,
      StockOrdersAvailableView::new,
      StockOrdersShippedView::new,
      StockSkuItemToOrderSkuItemAction::new,
      StockSkuItemToStockOrderAction::new,
      StockSkuItemsAvailableView::new,
      StockSkuItemsShippedView::new);
  }

  public static void main(String[] args) throws Exception {
    LOG.info("starting the Akka Serverless service");
    createAkkaServerless().start();
  }
}
