package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import io.mystore.TimeTo;
import io.mystore.shipping.entity.StockItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class AvailableStockItemsView extends AbstractAvailableStockItemsView {

  public AvailableStockItemsView(ViewContext context) {
  }

  @Override
  public AvailableStockItemsModel.AvailableStockItem emptyState() {
    return AvailableStockItemsModel.AvailableStockItem.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<AvailableStockItemsModel.AvailableStockItem> onCreated(
      AvailableStockItemsModel.AvailableStockItem state, StockItemEntity.StockItemCreated event) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setSkuId(event.getSkuId())
                .setSkuItemId(event.getSkuItemId())
                .setSkuName(event.getSkuName())
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableStockItemsModel.AvailableStockItem> onShipped(
      AvailableStockItemsModel.AvailableStockItem state, StockItemEntity.StockItemShipped event) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setShippedUtc(event.getShippedUtc())
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableStockItemsModel.AvailableStockItem> onReleased(
      AvailableStockItemsModel.AvailableStockItem state, StockItemEntity.StockItemReleased event) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setShippedUtc(TimeTo.zero())
                .build());
  }

  @Override
  public View.UpdateEffect<AvailableStockItemsModel.AvailableStockItem> ignoreOtherEvents(AvailableStockItemsModel.AvailableStockItem state, Any any) {
    return effects().ignore();
  }
}
