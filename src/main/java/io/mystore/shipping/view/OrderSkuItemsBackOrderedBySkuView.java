package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.shipping.entity.OrderSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemsBackOrderedBySkuView extends AbstractOrderSkuItemsBackOrderedBySkuView {

  public OrderSkuItemsBackOrderedBySkuView(ViewContext context) {
  }

  @Override
  public OrderSkuItemModel.OrderSkuItem emptyState() {
    return OrderSkuItemModel.OrderSkuItem.getDefaultInstance();
  }

  @Override
  public UpdateEffect<OrderSkuItemModel.OrderSkuItem> onOrderSkuItemCreated(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.OrderSkuItemCreated event) {
    return effects().updateState(OrderSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<OrderSkuItemModel.OrderSkuItem> onStockRequestedJoinToOrderAccepted(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return effects().updateState(OrderSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<OrderSkuItemModel.OrderSkuItem> onStockRequestedJoinToOrderRejected(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return effects().updateState(OrderSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<OrderSkuItemModel.OrderSkuItem> onOrderRequestedJoinToStockAccepted(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return effects().updateState(OrderSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<OrderSkuItemModel.OrderSkuItem> onOrderRequestedJoinToStockRejected(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return effects().updateState(OrderSkuItemEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<OrderSkuItemModel.OrderSkuItem> onBackOrderedOrderSkuItem(OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.BackOrderedOrderSkuItem event) {
    return effects().updateState(OrderSkuItemEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<OrderSkuItemModel.OrderSkuItem> ignoreOtherEvents(OrderSkuItemModel.OrderSkuItem state, Any any) {
    return effects().ignore();
  }
}
