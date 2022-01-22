package io.mystore.shipping.action;

import java.util.UUID;
import java.util.stream.IntStream;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.StockItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockItemFrontendAction extends AbstractStockItemFrontendAction {

  public StockItemFrontendAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> createStockItems(StockFrontendService.CreateStockRequest createStockRequest) {
    IntStream.range(0, createStockRequest.getQuantity()).forEach(i -> {
      var request = StockItemApi.CreateStockItem
          .newBuilder()
          .setSkuId(createStockRequest.getSkuId())
          .setSkuItemId(UUID.randomUUID().toString())
          .setSkuName(createStockRequest.getSkuName())
          .build();
      effects().forward(components().stockItem().create(request));
    });

    return effects().reply(Empty.getDefaultInstance());
  }
}
