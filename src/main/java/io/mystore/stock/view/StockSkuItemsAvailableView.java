package io.mystore.stock.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.stock.entity.StockSkuItemEntityX;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItemsAvailableView extends AbstractStockSkuItemsAvailableView {

  public StockSkuItemsAvailableView(ViewContext context) {}

  @Override
  public StockSkuItemOuterClass.StockSkuItem emptyState() {
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state");
  }

  @Override
  public View.UpdateEffect<StockSkuItemOuterClass.StockSkuItem> onStockSkuItemCreated(
    StockSkuItemOuterClass.StockSkuItem state, StockSkuItemEntityX.StockSkuItemCreated stockSkuItemCreated) {
    throw new UnsupportedOperationException("Update handler for 'OnStockSkuItemCreated' not implemented yet");
  }
  @Override
  public View.UpdateEffect<StockSkuItemOuterClass.StockSkuItem> onJoinedToStockSkuItem(
    StockSkuItemOuterClass.StockSkuItem state, StockSkuItemEntityX.JoinedToStockSkuItem joinedToStockSkuItem) {
    throw new UnsupportedOperationException("Update handler for 'OnJoinedToStockSkuItem' not implemented yet");
  }
  @Override
  public View.UpdateEffect<StockSkuItemOuterClass.StockSkuItem> onReleasedFromStockSkuItem(
    StockSkuItemOuterClass.StockSkuItem state, StockSkuItemEntityX.ReleasedFromStockSkuItem releasedFromStockSkuItem) {
    throw new UnsupportedOperationException("Update handler for 'OnReleasedFromStockSkuItem' not implemented yet");
  }
  @Override
  public View.UpdateEffect<StockSkuItemOuterClass.StockSkuItem> ignoreOtherEvents(
    StockSkuItemOuterClass.StockSkuItem state, Any any) {
    throw new UnsupportedOperationException("Update handler for 'IgnoreOtherEvents' not implemented yet");
  }
}

