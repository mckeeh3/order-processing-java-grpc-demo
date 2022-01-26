package io.mystore.shipping.action;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.order.entity.OrderEntity;
import io.mystore.order.entity.OrderEntity.OrderCreated;
import io.mystore.shipping.api.ShipOrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderToShipOrderAction extends AbstractOrderToShipOrderAction {

  public OrderToShipOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderCreated(OrderEntity.OrderCreated orderCreated) {
    return effects().forward(components().shipOrder().addShipOrder(toShipOrder(orderCreated)));
  }

  private ShipOrderApi.AddShipOrderCommand toShipOrder(OrderCreated orderCreated) {
    return ShipOrderApi.AddShipOrderCommand
        .newBuilder()
        .setOrderId(orderCreated.getOrderId())
        .setCustomerId(orderCreated.getCustomerId())
        .setOrderedUtc(orderCreated.getOrderedUtc())
        .addAllOrderItems(toShipOrderItems(orderCreated.getOrderItemsList()))
        .build();
  }

  private List<ShipOrderApi.OrderItemFromOrder> toShipOrderItems(List<OrderEntity.OrderItem> orderItems) {
    return orderItems.stream()
        .map(orderItem -> ShipOrderApi.OrderItemFromOrder
            .newBuilder()
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
