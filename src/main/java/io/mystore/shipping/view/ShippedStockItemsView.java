package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.entity.StockItemEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippedStockItemsView extends AbstractShippedStockItemsView {

  public ShippedStockItemsView(ViewContext context) {
  }

  @Override
  public ShippedStockItemsModel.ShippedStockItem emptyState() {
    return ShippedStockItemsModel.ShippedStockItem.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<ShippedStockItemsModel.ShippedStockItem> onCreated(
      ShippedStockItemsModel.ShippedStockItem state, StockItemEntity.StockItemCreated event) {
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
  public View.UpdateEffect<ShippedStockItemsModel.ShippedStockItem> onShipped(
      ShippedStockItemsModel.ShippedStockItem state, StockItemEntity.StockItemShipped event) {
    return effects()
        .updateState(
            state
                .toBuilder()
                .setOrderId(event.getOrderId())
                .setOrderItemId(event.getOrderItemId())
                .setShippedUtc(event.getShippedUtc())
                .build());
  }

  @Override
  public View.UpdateEffect<ShippedStockItemsModel.ShippedStockItem> onReleased(
      ShippedStockItemsModel.ShippedStockItem state, StockItemEntity.StockItemReleased event) {
    if (state.getOrderItemId() == event.getOrderItemId()) {
      return effects()
          .updateState(
              state
                  .toBuilder()
                  .setOrderId("")
                  .setOrderItemId("")
                  .setShippedUtc(timestampZero())
                  .build());
    } else {
      return effects().updateState(state);
    }
  }

  @Override
  public View.UpdateEffect<ShippedStockItemsModel.ShippedStockItem> ignoreOtherEvents(ShippedStockItemsModel.ShippedStockItem state, Any any) {
    return effects().ignore();
  }

  static Timestamp timestampZero() {
    return Timestamp
        .newBuilder()
        .setSeconds(0)
        .setNanos(0)
        .build();
  }
}
