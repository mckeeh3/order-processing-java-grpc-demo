package io.mystore.order.action;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.order.entity.OrderEntity;
import io.mystore.prchased_product.api.OrderItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderToOrderedItemsAction extends AbstractOrderToOrderedItemsAction {

  public OrderToOrderedItemsAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderCreated(OrderEntity.OrderCreated orderCreated) {
    var results = orderCreated.getOrderItemsList().stream()
        .map(orderItem -> OrderItemApi.OrderItemCommand
            .newBuilder()
            .setCustomerId(orderCreated.getCustomerId())
            .setOrderId(orderCreated.getOrderId())
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .setOrderedUtc(orderCreated.getOrderedUtc())
            .build())
        .map(orderItemCommand -> components().orderItem().createOrderItem(orderItemCommand).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
