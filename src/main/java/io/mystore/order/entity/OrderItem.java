package io.mystore.order.entity;

import kalix.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.order.api.OrderItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderItem extends AbstractOrderItem {
  static final Logger log = LoggerFactory.getLogger(OrderItem.class);

  public OrderItem(ValueEntityContext context) {
  }

  @Override
  public OrderItemEntity.OrderItemState emptyState() {
    return OrderItemEntity.OrderItemState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createOrderItem(OrderItemEntity.OrderItemState state, OrderItemApi.CreateOrderItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> shippedOrderItem(OrderItemEntity.OrderItemState state,
      OrderItemApi.ShippedOrderItemCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<OrderItemApi.GetOrderItemResponse> getOrderItem(OrderItemEntity.OrderItemState state,
      OrderItemApi.GetOrderItemRequest request) {
    return effects().reply(toApi(state));
  }

  private Effect<Empty> handle(OrderItemEntity.OrderItemState state, OrderItemApi.CreateOrderItemCommand command) {
    log.info("state: {}\nCreateOrderItemCommand: {}", state, command);

    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderItemEntity.OrderItemState state, OrderItemApi.ShippedOrderItemCommand command) {
    log.info("state: {}\nShipOrderItemCommand: {}", state, command);

    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private OrderItemEntity.OrderItemState updateState(OrderItemEntity.OrderItemState state,
      OrderItemApi.CreateOrderItemCommand command) {
    return OrderItemEntity.OrderItemState
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setCustomerId(command.getCustomerId())
        .setSkuId(command.getSkuId())
        .setSkuName(command.getSkuName())
        .setQuantity(command.getQuantity())
        .setOrderedUtc(command.getOrderedUtc())
        .build();
  }

  private OrderItemEntity.OrderItemState updateState(OrderItemEntity.OrderItemState state,
      OrderItemApi.ShippedOrderItemCommand command) {
    return state
        .toBuilder()
        .setShippedUtc(command.getShippedUtc())
        .build();
  }

  private OrderItemApi.GetOrderItemResponse toApi(OrderItemEntity.OrderItemState state) {
    return OrderItemApi.GetOrderItemResponse
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setCustomerId(state.getCustomerId())
        .setSkuId(state.getSkuId())
        .setSkuName(state.getSkuName())
        .setQuantity(state.getQuantity())
        .setOrderedUtc(state.getOrderedUtc())
        .setShippedUtc(state.getShippedUtc())
        .build();
  }
}
