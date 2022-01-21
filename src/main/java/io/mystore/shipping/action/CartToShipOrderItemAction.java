package io.mystore.shipping.action;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.cart.entity.CartEntity;
import io.mystore.shipping.api.ShipOrderItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToShipOrderItemAction extends AbstractCartToShipOrderItemAction {

  public CartToShipOrderItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onCartCheckedOut(CartEntity.CartCheckedOut cartCheckedOut) {
    var results = cartCheckedOut.getCartState().getLineItemsList().stream()
        .flatMap(lineItem -> shipOrderItems(cartCheckedOut, lineItem))
        .map(shipOrderItem -> components().shipOrderItem().createShipOrderItem(shipOrderItem).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  Stream<ShipOrderItemApi.ShipOrderItem> shipOrderItems(CartEntity.CartCheckedOut cartCheckedOut, CartEntity.LineItem lineItem) {
    return IntStream.range(0, lineItem.getQuantity())
        .mapToObj(ignore -> ShipOrderItemApi.ShipOrderItem
            .newBuilder()
            .setCustomerId(cartCheckedOut.getCartState().getCustomerId())
            .setOrderId(cartCheckedOut.getCartState().getCartId())
            .setOrderItemId(UUID.randomUUID().toString())
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setOrderedUtc(cartCheckedOut.getCartState().getCheckedOutUtc())
            .build());
  }
}
