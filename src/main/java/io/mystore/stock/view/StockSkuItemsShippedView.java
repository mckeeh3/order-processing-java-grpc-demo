package io.mystore.stock.view;

import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.stock.entity.StockSkuItemEntity;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItemsShippedView extends AbstractStockSkuItemsShippedView {

  public StockSkuItemsShippedView(ViewContext context) {
  }

  @Override
  public StockSkuItemsModel.StockSkuItem emptyState() {
    return StockSkuItemsModel.StockSkuItem.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<StockSkuItemsModel.StockSkuItem> onStockSkuItemCreated(StockSkuItemsModel.StockSkuItem state,
      StockSkuItemEntity.StockSkuItemCreated event) {
    return effects().updateState(StockSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<StockSkuItemsModel.StockSkuItem> onOrderRequestedJoinToStockAccepted(
      StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.OrderRequestedJoinToStockAccepted event) {
    return effects().updateState(StockSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<StockSkuItemsModel.StockSkuItem> onOrderRequestedJoinToStockRejected(
      StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.OrderRequestedJoinToStockRejected event) {
    return effects().updateState(StockSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<StockSkuItemsModel.StockSkuItem> onStockRequestedJoinToOrderAccepted(
      StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockRequestedJoinToOrderAccepted event) {
    return effects().updateState(StockSkuItemEventHandler.handle(state, event));
  }

  @Override
  public UpdateEffect<StockSkuItemsModel.StockSkuItem> onStockRequestedJoinToOrderRejected(
      StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockRequestedJoinToOrderRejected event) {
    return effects().updateState(StockSkuItemEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<StockSkuItemsModel.StockSkuItem> ignoreOtherEvents(StockSkuItemsModel.StockSkuItem state,
      Any any) {
    return effects().ignore();
  }
}
