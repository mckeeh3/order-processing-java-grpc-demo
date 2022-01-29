package io.mystore.shipping.action;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.api.ShipOrderItemApi;
import io.mystore.shipping.entity.ShipOrderEntity;
import io.mystore.shipping.entity.ShipOrderEntity.ShipOrderCreated;
import io.mystore.shipping.entity.ShipOrderEntity.ShipOrderItems;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderToShipOrderItemAction extends AbstractShipOrderToShipOrderItemAction {

  public ShipOrderToShipOrderItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onShipOrderCreated(ShipOrderEntity.ShipOrderCreated shipOrderCreated) {
    var results = shipOrderCreated.getShipOrderItemsList().stream()
        .flatMap(shipOrderItems -> toShipOrderItems(shipOrderCreated, shipOrderItems))
        .map(shipOrderItem -> components().shipOrderItem().createOrderItem(shipOrderItem).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  private Stream<ShipOrderItemApi.CreateOrderItemCommand> toShipOrderItems(ShipOrderCreated shipOrderCreated, ShipOrderItems shipOrderItems) {
    return shipOrderItems.getShipOrderItemsList().stream()
        .map(shipOrderItem -> ShipOrderItemApi.CreateOrderItemCommand
            .newBuilder()
            .setCustomerId(shipOrderCreated.getCustomerId())
            .setOrderId(shipOrderCreated.getOrderId())
            .setOrderItemId(shipOrderItem.getOrderItemId())
            .setSkuId(shipOrderItem.getSkuId())
            .setSkuName(shipOrderItems.getSkuName())
            .setOrderedUtc(shipOrderCreated.getOrderedUtc())
            .build());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
