package io.mystore.shipping2.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.TimeTo;
import io.mystore.shipping2.entity.OrderSkuItemEntity;

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
  public View.UpdateEffect<OrderSkuItemModel.OrderSkuItem> onCreatedOrderSkuItem(
      OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.CreatedOrderSkuItem createdOrderSkuItem) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setOrderId(createdOrderSkuItem.getOrderId())
                .setOrderSkuItemId(createdOrderSkuItem.getOrderSkuItemId())
                .setSkuId(createdOrderSkuItem.getSkuId())
                .setSkuName(createdOrderSkuItem.getSkuName())
                .build());
  }

  @Override
  public View.UpdateEffect<OrderSkuItemModel.OrderSkuItem> onJoinedToStockSkuItem(
      OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setBackOrderedUtc(TimeTo.zero())
                .build());
  }

  @Override
  public View.UpdateEffect<OrderSkuItemModel.OrderSkuItem> onBackOrderedOrderSkuItem(
      OrderSkuItemModel.OrderSkuItem state, OrderSkuItemEntity.BackOrderedOrderSkuItem backOrderedOrderSkuItem) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setBackOrderedUtc(backOrderedOrderSkuItem.getBackOrderedUtc())
                .build());
  }

  @Override
  public View.UpdateEffect<OrderSkuItemModel.OrderSkuItem> ignoreOtherEvents(OrderSkuItemModel.OrderSkuItem state, Any any) {
    return effects().ignore();
  }
}
