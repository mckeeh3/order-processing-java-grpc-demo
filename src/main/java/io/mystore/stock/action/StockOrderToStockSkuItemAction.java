package io.mystore.stock.action;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.stock.api.StockSkuItemApi;
import io.mystore.stock.entity.StockOrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockOrderToStockSkuItemAction extends AbstractStockOrderToStockSkuItemAction {

  public StockOrderToStockSkuItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onStockOrderCreated(StockOrderEntity.StockOrderCreated stockOrderCreated) {

    var results = stockOrderCreated.getStockSkuItemsList().stream()
        .map(stockSkuItem -> components().stockSkuItem().createStockSkuItem(toStockSkuItemCommand(stockSkuItem)).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  static StockSkuItemApi.CreateStockSkuItemCommand toStockSkuItemCommand(StockOrderEntity.StockSkuItem stockSkuItem) {
    return StockSkuItemApi.CreateStockSkuItemCommand
        .newBuilder()
        .setStockSkuItemId(stockSkuItem.getStockSkuItemId())
        .setSkuId(stockSkuItem.getSkuId())
        .setSkuName(stockSkuItem.getSkuName())
        .setStockOrderId(stockSkuItem.getStockOrderId())
        .build();
  }
}
