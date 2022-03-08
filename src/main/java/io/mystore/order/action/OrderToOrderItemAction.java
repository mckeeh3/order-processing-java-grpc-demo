package io.mystore.order.action;

import java.util.concurrent.CompletableFuture;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.order.api.OrderItemApi;
import io.mystore.order.entity.OrderEntity;
import io.mystore.order.entity.OrderEntity.OrderItemShipped;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderToOrderItemAction extends AbstractOrderToOrderItemAction {
  static final Logger log = LoggerFactory.getLogger(OrderToOrderItemAction.class);

  public OrderToOrderItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderCreated(OrderEntity.OrderCreated orderCreated) {
    log.info("onOrderCreated: {}", orderCreated);

    var results = orderCreated.getOrderItemsList().stream()
        .map(orderItem -> OrderItemApi.CreateOrderItemCommand
            .newBuilder()
            .setCustomerId(orderCreated.getCustomerId())
            .setOrderId(orderCreated.getOrderId())
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .setOrderedUtc(orderCreated.getOrderedUtc())
            .build())
        .map(orderItemCommand -> components().orderItem().createOrderItem(orderItemCommand).execute())
        .toList();

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> onOrderItemShipped(OrderEntity.OrderItemShipped orderItemShipped) {
    log.info("onOrderItemShipped: {}", orderItemShipped);

    return effects().forward(components().orderItem().shippedOrderItem(toApi(orderItemShipped)));
  }

  static OrderItemApi.ShippedOrderItemCommand toApi(OrderItemShipped orderItemShipped) {
    return OrderItemApi.ShippedOrderItemCommand
        .newBuilder()
        .setOrderId(orderItemShipped.getOrderId())
        .setSkuId(orderItemShipped.getSkuId())
        .setShippedUtc(orderItemShipped.getShippedUtc())
        .build();
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
