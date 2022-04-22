package io.mystore.stock.view;

import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.stock.entity.StockOrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockOrdersAvailableView extends AbstractStockOrdersAvailableView {

  public StockOrdersAvailableView(ViewContext context) {
  }

  @Override
  public StockOrdersModel.StockOrder emptyState() {
    return StockOrdersModel.StockOrder.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<StockOrdersModel.StockOrder> onStockOrderCreated(StockOrdersModel.StockOrder state,
      StockOrderEntity.StockOrderCreated event) {
    return effects().updateState(StockOrderEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<StockOrdersModel.StockOrder> onStockSkuItemShipped(StockOrdersModel.StockOrder state,
      StockOrderEntity.StockSkuItemShipped event) {
    return effects().updateState(StockOrderEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<StockOrdersModel.StockOrder> onStockSkuItemReleased(StockOrdersModel.StockOrder state,
      StockOrderEntity.StockSkuItemReleased event) {
    return effects().updateState(StockOrderEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<StockOrdersModel.StockOrder> ignoreOtherEvents(StockOrdersModel.StockOrder state, Any any) {
    return effects().ignore();
  }
}
