package io.mystore.order.action;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.order.api.OrderApi;
import io.mystore.order.api.OrderApi.LineItem;
import io.mystore.cart.entity.CartEntity;
import io.mystore.cart.entity.CartEntity.CartState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToOrderAction extends AbstractCartToOrderAction {

  public CartToOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onCartCheckedOut(CartEntity.CartCheckedOut command) {
    var result = components().order().addOrder(toOrder(command.getCartState()));

    return effects().forward(result);
  }

  private OrderApi.Order toOrder(CartState state) {
    return OrderApi.Order
        .newBuilder()
        .setOrderId(state.getCartId())
        .setCustomerId(state.getCustomerId())
        .setOrderedUtc(state.getCheckedOutUtc())
        .addAllLineItems(toLineItems(state.getLineItemsList()))
        .build();
  }

  private List<LineItem> toLineItems(List<CartEntity.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> OrderApi.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
