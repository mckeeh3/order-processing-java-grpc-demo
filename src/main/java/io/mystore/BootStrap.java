package io.mystore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.cart.entity.ShoppingCart;
import io.mystore.cart.entity.ShoppingCartProvider;
import io.mystore.cart.view.CartsByCustomerByDateView;
import io.mystore.cart.view.CartsByCustomerByDateViewProvider;
import io.mystore.cart.view.CartsByCustomerView;
import io.mystore.cart.view.CartsByCustomerViewProvider;
import io.mystore.cart.view.CartsByDateView;
import io.mystore.cart.view.CartsByDateViewProvider;
import io.mystore.order.action.CartToOrderAction;
import io.mystore.order.action.CartToOrderActionProvider;
import io.mystore.order.action.OrderToOrderItemAction;
import io.mystore.order.action.OrderToOrderItemActionProvider;
import io.mystore.order.action.ShippingToOrderAction;
import io.mystore.order.action.ShippingToOrderActionProvider;
import io.mystore.order.entity.Order;
import io.mystore.order.entity.OrderItem;
import io.mystore.order.entity.OrderItemProvider;
import io.mystore.order.entity.OrderProvider;
import io.mystore.order.view.OrderedItemsByCustomerByDateView;
import io.mystore.order.view.OrderedItemsByCustomerByDateViewProvider;
import io.mystore.order.view.OrderedItemsByDateView;
import io.mystore.order.view.OrderedItemsByDateViewProvider;
import io.mystore.order.view.OrderedItemsBySkuByDateView;
import io.mystore.order.view.OrderedItemsBySkuByDateViewProvider;
import io.mystore.order.view.OrdersByCustomerByDateView;
import io.mystore.order.view.OrdersByCustomerByDateViewProvider;
import io.mystore.order.view.OrdersByDateView;
import io.mystore.order.view.OrdersByDateViewProvider;
import io.mystore.shipping.action.OrderSkuItemToShippingAction;
import io.mystore.shipping.action.OrderSkuItemToShippingActionProvider;
import io.mystore.shipping.action.OrderToShippingAction;
import io.mystore.shipping.action.OrderToShippingActionProvider;
import io.mystore.shipping.action.ShippingToOrderSkuItemAction;
import io.mystore.shipping.action.ShippingToOrderSkuItemActionProvider;
import io.mystore.shipping.action.StockSkuItemToOrderSkuItemAction;
import io.mystore.shipping.action.StockSkuItemToOrderSkuItemActionProvider;
import io.mystore.shipping.entity.OrderSkuItem;
import io.mystore.shipping.entity.OrderSkuItemProvider;
import io.mystore.shipping.entity.Shipping;
import io.mystore.shipping.entity.ShippingProvider;
import io.mystore.shipping.view.OrderSkuItemsBackOrderedBySkuView;
import io.mystore.shipping.view.OrderSkuItemsBackOrderedBySkuViewProvider;
import io.mystore.shipping.view.OrderSkuItemsByOrderView;
import io.mystore.shipping.view.OrderSkuItemsByOrderViewProvider;
import io.mystore.shipping.view.OrderSkuItemsNotShippedBySkuView;
import io.mystore.shipping.view.OrderSkuItemsNotShippedBySkuViewProvider;
import io.mystore.shipping.view.OrderSkuItemsShippedBySkuView;
import io.mystore.shipping.view.OrderSkuItemsShippedBySkuViewProvider;
import io.mystore.shipping.view.ShippingByCustomerByDateView;
import io.mystore.shipping.view.ShippingByCustomerByDateViewProvider;
import io.mystore.shipping.view.ShippingByDateView;
import io.mystore.shipping.view.ShippingByDateViewProvider;
import io.mystore.stock.action.OrderSkuItemToStockSkuItemAction;
import io.mystore.stock.action.OrderSkuItemToStockSkuItemActionProvider;
import io.mystore.stock.action.StockOrderToStockSkuItemAction;
import io.mystore.stock.action.StockOrderToStockSkuItemActionProvider;
import io.mystore.stock.action.StockSkuItemToStockOrderAction;
import io.mystore.stock.action.StockSkuItemToStockOrderActionProvider;
import io.mystore.stock.entity.StockOrder;
import io.mystore.stock.entity.StockOrderProvider;
import io.mystore.stock.entity.StockSkuItem;
import io.mystore.stock.entity.StockSkuItemProvider;
import io.mystore.stock.view.StockOrdersAvailableView;
import io.mystore.stock.view.StockOrdersAvailableViewProvider;
import io.mystore.stock.view.StockOrdersShippedView;
import io.mystore.stock.view.StockOrdersShippedViewProvider;
import io.mystore.stock.view.StockSkuItemsAvailableView;
import io.mystore.stock.view.StockSkuItemsAvailableViewProvider;
import io.mystore.stock.view.StockSkuItemsShippedView;
import io.mystore.stock.view.StockSkuItemsShippedViewProvider;
import kalix.javasdk.Kalix;

class BootStrap {
  static Logger log = LoggerFactory.getLogger(BootStrap.class);

  static Kalix createKalix() {
    var kalix = new Kalix();
    return kalix
        .register(CartToOrderActionProvider.of(CartToOrderAction::new))
        .register(CartsByCustomerByDateViewProvider.of(CartsByCustomerByDateView::new).withViewId(viewIdOf(CartsByCustomerByDateView.class)))
        .register(CartsByCustomerViewProvider.of(CartsByCustomerView::new).withViewId(viewIdOf(CartsByCustomerView.class)))
        .register(CartsByDateViewProvider.of(CartsByDateView::new).withViewId(viewIdOf(CartsByDateView.class)))
        .register(OrderItemProvider.of(OrderItem::new))
        .register(OrderProvider.of(Order::new))
        .register(OrderSkuItemProvider.of(OrderSkuItem::new))
        .register(OrderSkuItemToShippingActionProvider.of(OrderSkuItemToShippingAction::new))
        .register(OrderSkuItemToStockSkuItemActionProvider.of(OrderSkuItemToStockSkuItemAction::new))
        .register(OrderSkuItemsBackOrderedBySkuViewProvider.of(OrderSkuItemsBackOrderedBySkuView::new).withViewId(viewIdOf(OrderSkuItemsBackOrderedBySkuView.class)))
        .register(OrderSkuItemsByOrderViewProvider.of(OrderSkuItemsByOrderView::new).withViewId(viewIdOf(OrderSkuItemsByOrderView.class)))
        .register(OrderSkuItemsNotShippedBySkuViewProvider.of(OrderSkuItemsNotShippedBySkuView::new).withViewId(viewIdOf(OrderSkuItemsNotShippedBySkuView.class)))
        .register(OrderSkuItemsShippedBySkuViewProvider.of(OrderSkuItemsShippedBySkuView::new).withViewId(viewIdOf(OrderSkuItemsShippedBySkuView.class)))
        .register(OrderToOrderItemActionProvider.of(OrderToOrderItemAction::new))
        .register(OrderToShippingActionProvider.of(OrderToShippingAction::new))
        .register(OrderedItemsByCustomerByDateViewProvider.of(OrderedItemsByCustomerByDateView::new).withViewId(viewIdOf(OrderedItemsByCustomerByDateView.class)))
        .register(OrderedItemsByDateViewProvider.of(OrderedItemsByDateView::new).withViewId(viewIdOf(OrderedItemsByDateView.class)))
        .register(OrderedItemsBySkuByDateViewProvider.of(OrderedItemsBySkuByDateView::new).withViewId(viewIdOf(OrderedItemsBySkuByDateView.class)))
        .register(OrdersByCustomerByDateViewProvider.of(OrdersByCustomerByDateView::new).withViewId(viewIdOf(OrdersByCustomerByDateView.class)))
        .register(OrdersByDateViewProvider.of(OrdersByDateView::new).withViewId(viewIdOf(OrdersByDateView.class)))
        .register(ShippingByCustomerByDateViewProvider.of(ShippingByCustomerByDateView::new).withViewId(viewIdOf(ShippingByCustomerByDateView.class)))
        .register(ShippingByDateViewProvider.of(ShippingByDateView::new).withViewId(viewIdOf(ShippingByDateView.class)))
        .register(ShippingProvider.of(Shipping::new))
        .register(ShippingToOrderActionProvider.of(ShippingToOrderAction::new))
        .register(ShippingToOrderSkuItemActionProvider.of(ShippingToOrderSkuItemAction::new))
        .register(ShoppingCartProvider.of(ShoppingCart::new))
        .register(StockOrderProvider.of(StockOrder::new))
        .register(StockOrderToStockSkuItemActionProvider.of(StockOrderToStockSkuItemAction::new))
        .register(StockOrdersAvailableViewProvider.of(StockOrdersAvailableView::new).withViewId(viewIdOf(StockOrdersAvailableView.class)))
        .register(StockOrdersShippedViewProvider.of(StockOrdersShippedView::new).withViewId(viewIdOf(StockOrdersShippedView.class)))
        .register(StockSkuItemProvider.of(StockSkuItem::new))
        .register(StockSkuItemToOrderSkuItemActionProvider.of(StockSkuItemToOrderSkuItemAction::new))
        .register(StockSkuItemToStockOrderActionProvider.of(StockSkuItemToStockOrderAction::new))
        .register(StockSkuItemsAvailableViewProvider.of(StockSkuItemsAvailableView::new).withViewId(viewIdOf(StockSkuItemsAvailableView.class)))
        .register(StockSkuItemsShippedViewProvider.of(StockSkuItemsShippedView::new).withViewId(viewIdOf(StockSkuItemsShippedView.class)));
  }

  private static String viewIdOf(Class<?> clazz) {
    var className = clazz.getSimpleName();
    var viewId = viewIdOf(className);
    log.info("view Id '{}' ({}) for '{}", viewId, viewId.length(), className);
    return viewIdOf(clazz.getSimpleName());
  }

  private static String viewIdOf(String given) {
    return viewIdOf(new StringBuilder(given), given.length() - 1).toString();
  }

  private static StringBuilder viewIdOf(StringBuilder given, int idx) {
    var s = removeIfVowelAt(given, idx);
    return s.length() <= 21 || idx == 0 ? s : viewIdOf(s, idx - 1);
  }

  private static StringBuilder removeIfVowelAt(StringBuilder given, int idx) {
    return isVowel(given, idx) ? given.deleteCharAt(idx) : given;
  }

  private static final String vowels = "aeiouAEIOU";

  private static boolean isVowel(StringBuilder given, int idx) {
    return vowels.contains(String.valueOf(given.charAt(idx)));
  }
}
