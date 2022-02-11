package io.mystore.shipping.action;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.OrderSkuItemApi;
import io.mystore.shipping.entity.ShippingEntity;
import io.mystore.shipping.entity.ShippingEntity.OrderCreated;
import io.mystore.shipping.entity.ShippingEntity.OrderItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingToOrderSkuItemAction extends AbstractShippingToOrderSkuItemAction {
  static final Logger log = LoggerFactory.getLogger(ShippingToOrderSkuItemAction.class);

  public ShippingToOrderSkuItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderCreated(ShippingEntity.OrderCreated orderCreated) {
    log.info("onOrderCreated: {}", orderCreated);

    var results = orderCreated.getOrderItemsList().stream()
        .flatMap(orderItems -> toCreateOrderSkuItemCommands(orderCreated, orderItems))
        .map(orderItem -> components().orderSkuItem().createOrderSkuItem(orderItem).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  static Stream<OrderSkuItemApi.CreateOrderSkuItemCommand> toCreateOrderSkuItemCommands(OrderCreated orderCreated, OrderItem orderItems) {
    return orderItems.getOrderSkuItemsList().stream()
        .map(orderItem -> OrderSkuItemApi.CreateOrderSkuItemCommand
            .newBuilder()
            .setCustomerId(orderCreated.getCustomerId())
            .setOrderId(orderCreated.getOrderId())
            .setOrderSkuItemId(orderItem.getOrderSkuItemId())
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItems.getSkuName())
            .setOrderedUtc(orderCreated.getOrderedUtc())
            .build());
  }
}
