package io.mystore.shipping.action;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    var results = requests(createStockRequest)
        .map(request -> components().stockItem().create(request).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  private Stream<StockItemApi.CreateStockItem> requests(StockFrontendService.CreateStockRequest createStockRequest) {
    return IntStream.range(0, createStockRequest.getQuantity())
        .mapToObj(i -> StockItemApi.CreateStockItem
            .newBuilder()
            .setSkuId(createStockRequest.getSkuId())
            .setSkuItemId(UUID.randomUUID().toString())
            .setSkuName(createStockRequest.getSkuName())
            .build());
  }
}
