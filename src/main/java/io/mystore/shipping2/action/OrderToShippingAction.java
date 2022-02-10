package io.mystore.shipping2.action;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.order.entity.OrderEntity;
import io.mystore.shipping2.api.ShippingApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderToShippingAction extends AbstractOrderToShippingAction {
  static final Logger log = LoggerFactory.getLogger(OrderToShippingAction.class);

  public OrderToShippingAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderCreated(OrderEntity.OrderCreated orderCreated) {
    log.info("onOrderCreated: {}", orderCreated);

    return effects().forward(components().shipping().createOrder(toCreateOrderCommand(orderCreated)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  static ShippingApi.CreateOrderCommand toCreateOrderCommand(OrderEntity.OrderCreated orderCreated) {
    return ShippingApi.CreateOrderCommand
        .newBuilder()
        .setOrderId(orderCreated.getOrderId())
        .setCustomerId(orderCreated.getCustomerId())
        .setOrderedUtc(orderCreated.getOrderedUtc())
        .addAllOrderItems(toOrderItems(orderCreated.getOrderItemsList()))
        .build();
  }

  static List<ShippingApi.OrderItem> toOrderItems(List<OrderEntity.OrderItem> orderItems) {
    return orderItems.stream()
        .map(orderItem -> ShippingApi.OrderItem
            .newBuilder()
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }
}
