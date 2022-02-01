package io.mystore.order.entity;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
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
    log.info("state: {}\ncreateOrderItemCommand: {}", state, command);

    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<OrderItemApi.GetOrderItemResponse> getOrderItem(OrderItemEntity.OrderItemState state, OrderItemApi.GetOrderItemRequest command) {
    return effects().reply(toApi(state));
  }

  private OrderItemEntity.OrderItemState updateState(OrderItemEntity.OrderItemState state, OrderItemApi.CreateOrderItemCommand command) {
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

  private OrderItemApi.GetOrderItemResponse toApi(OrderItemEntity.OrderItemState state) {
    return OrderItemApi.GetOrderItemResponse
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setCustomerId(state.getCustomerId())
        .setSkuId(state.getSkuId())
        .setSkuName(state.getSkuName())
        .setQuantity(state.getQuantity())
        .setOrderedUtc(state.getOrderedUtc())
        .build();
  }
}
