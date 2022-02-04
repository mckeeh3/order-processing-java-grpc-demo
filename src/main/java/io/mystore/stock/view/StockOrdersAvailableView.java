package io.mystore.stock.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.stock.entity.StockOrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockOrdersAvailableView extends AbstractStockOrdersAvailableView {

  public StockOrdersAvailableView(ViewContext context) {}

  @Override
  public StockOrderOuterClass.StockOrder emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state");
  }

  @Override
  public View.UpdateEffect<StockOrderOuterClass.StockOrder> onStockOrderCreated(
    StockOrderOuterClass.StockOrder state, StockOrderEntity.StockOrderCreated stockOrderCreated) {
    throw new UnsupportedOperationException("Update handler for 'OnStockOrderCreated' not implemented yet");
  }
  @Override
  public View.UpdateEffect<StockOrderOuterClass.StockOrder> onStockSkuItemJoined(
    StockOrderOuterClass.StockOrder state, StockOrderEntity.StockSkuItemJoined stockSkuItemJoined) {
    throw new UnsupportedOperationException("Update handler for 'OnStockSkuItemJoined' not implemented yet");
  }
  @Override
  public View.UpdateEffect<StockOrderOuterClass.StockOrder> onStockSkuItemReleased(
    StockOrderOuterClass.StockOrder state, StockOrderEntity.StockSkuItemReleased stockSkuItemReleased) {
    throw new UnsupportedOperationException("Update handler for 'OnStockSkuItemReleased' not implemented yet");
  }
  @Override
  public View.UpdateEffect<StockOrderOuterClass.StockOrder> ignoreOtherEvents(
    StockOrderOuterClass.StockOrder state, Any any) {
    throw new UnsupportedOperationException("Update handler for 'IgnoreOtherEvents' not implemented yet");
  }
}

