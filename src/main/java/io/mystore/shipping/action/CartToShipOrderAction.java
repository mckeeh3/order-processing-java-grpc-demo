package io.mystore.shipping.action;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.cart.entity.CartEntity;
import io.mystore.shipping.api.ShipOrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToShipOrderAction extends AbstractCartToShipOrderAction {

  public CartToShipOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onCartCheckedOut(CartEntity.CartCheckedOut cartCheckedOut) {
    return effects().forward(components().shipOrder().addShipOrder(toShipOrder(cartCheckedOut)));
  }

  private ShipOrderApi.ShipOrder toShipOrder(CartEntity.CartCheckedOut cartCheckedOut) {
    return ShipOrderApi.ShipOrder
        .newBuilder()
        .setOrderId(cartCheckedOut.getCartState().getCartId())
        .setCustomerId(cartCheckedOut.getCartState().getCustomerId())
        .setOrderedUtc(cartCheckedOut.getCartState().getCheckedOutUtc())
        .addAllLineItems(toLineItems(cartCheckedOut.getCartState().getLineItemsList()))
        .build();
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private List<ShipOrderApi.LineItem> toLineItems(List<CartEntity.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> ShipOrderApi.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }
}
