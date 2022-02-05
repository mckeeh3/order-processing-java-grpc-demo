package io.mystore.stock.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.stock.entity.StockSkuItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
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
  public View.UpdateEffect<StockSkuItemsModel.StockSkuItem> onStockSkuItemCreated(
      StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.StockSkuItemCreated stockSkuItemCreated) {
    return effects()
        .updateState(
            StockSkuItemEventHandler
                .fromState(state)
                .handle(stockSkuItemCreated)
                .toState());
  }

  @Override
  public View.UpdateEffect<StockSkuItemsModel.StockSkuItem> onJoinedToStockSkuItem(
      StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.JoinedToStockSkuItem joinedToStockSkuItem) {
    return effects()
        .updateState(
            StockSkuItemEventHandler
                .fromState(state)
                .handle(joinedToStockSkuItem)
                .toState());
  }

  @Override
  public View.UpdateEffect<StockSkuItemsModel.StockSkuItem> onReleasedFromStockSkuItem(
      StockSkuItemsModel.StockSkuItem state, StockSkuItemEntity.ReleasedFromStockSkuItem releasedFromStockSkuItem) {
    return effects()
        .updateState(
            StockSkuItemEventHandler
                .fromState(state)
                .handle(releasedFromStockSkuItem)
                .toState());
  }

  @Override
  public View.UpdateEffect<StockSkuItemsModel.StockSkuItem> ignoreOtherEvents(StockSkuItemsModel.StockSkuItem state, Any any) {
    return effects().ignore();
  }
}
