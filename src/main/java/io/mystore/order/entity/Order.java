package io.mystore.order.entity;

import java.util.List;
import java.util.Optional;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.order.api.OrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class Order extends AbstractOrder {
  static final Logger log = LoggerFactory.getLogger(Order.class);

  public Order(EventSourcedEntityContext context) {
  }

  @Override
  public OrderEntity.OrderState emptyState() {
    return OrderEntity.OrderState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createOrder(OrderEntity.OrderState state, OrderApi.CreateOrderCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> shippedOrder(OrderEntity.OrderState state, OrderApi.ShippedOrderCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> deliveredOrder(OrderEntity.OrderState state, OrderApi.DeliveredOrderCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> returnedOrder(OrderEntity.OrderState state, OrderApi.ReturnedOrderCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> canceledOrder(OrderEntity.OrderState state, OrderApi.CanceledOrderCommand command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> shippedOrderItem(OrderEntity.OrderState state, OrderApi.ShippedOrderSkuCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<OrderApi.Order> getOrder(OrderEntity.OrderState state, OrderApi.GetOrderRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public OrderEntity.OrderState orderCreated(OrderEntity.OrderState state, OrderEntity.OrderCreated event) {
    return state
        .toBuilder()
        .setOrderId(event.getOrderId())
        .setCustomerId(event.getCustomerId())
        .setOrderedUtc(event.getOrderedUtc())
        .addAllOrderItems(event.getOrderItemsList())
        .build();
  }

  @Override
  public OrderEntity.OrderState orderShipped(OrderEntity.OrderState state, OrderEntity.OrderShipped event) {
    return state
        .toBuilder()
        .setShippedUtc(event.getShippedUtc())
        .build();
  }

  @Override
  public OrderEntity.OrderState orderDelivered(OrderEntity.OrderState state, OrderEntity.OrderDelivered event) {
    return state
        .toBuilder()
        .setDeliveredUtc(event.getDeliveredUtc())
        .build();
  }

  @Override
  public OrderEntity.OrderState orderReturned(OrderEntity.OrderState state, OrderEntity.OrderReturned event) {
    return state
        .toBuilder()
        .setReturnedUtc(event.getReturnedUtc())
        .build();
  }

  @Override
  public OrderEntity.OrderState orderCancelled(OrderEntity.OrderState state, OrderEntity.OrderCancelled event) {
    return state
        .toBuilder()
        .setCanceledUtc(event.getCanceledUtc())
        .build();
  }

  @Override
  public OrderEntity.OrderState orderItemShipped(OrderEntity.OrderState state, OrderEntity.OrderItemShipped event) {
    var orderItems = updateShippedOrderItem(state.getOrderItemsList(), event);

    return state
        .toBuilder()
        .clearOrderItems()
        .addAllOrderItems(orderItems)
        .build();
  }

  private Optional<Effect<Empty>> reject(OrderEntity.OrderState state, OrderApi.DeliveredOrderCommand command) {
    if (state.getCanceledUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Shopping has been canceled"));
    }
    if (state.getShippedUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Shopping cart is not shipped"));
    }
    if (state.getDeliveredUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart already delivered"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(OrderEntity.OrderState state, OrderApi.ReturnedOrderCommand command) {
    if (state.getCanceledUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Shopping has been canceled"));
    }
    if (state.getDeliveredUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Shopping has not been delivered delivered"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(OrderEntity.OrderState state, OrderApi.CanceledOrderCommand command) {
    if (state.getDeliveredUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Shopping cart already delivered"));
    }
    return Optional.empty();
  }

  private Optional<Effect<OrderApi.Order>> reject(OrderEntity.OrderState state, OrderApi.GetOrderRequest command) {
    if (state.getOrderId().isEmpty()) {
      return Optional.of(effects().error("Order not found"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(OrderEntity.OrderState state, OrderApi.CreateOrderCommand command) {
    log.info("state: {}, CreateOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderEntity.OrderState state, OrderApi.ShippedOrderCommand command) {
    log.info("state: {}, ShippedOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderEntity.OrderState state, OrderApi.DeliveredOrderCommand command) {
    log.info("state: {}, DeliveredOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderEntity.OrderState state, OrderApi.ReturnedOrderCommand command) {
    log.info("state: {}, ReturnedOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderEntity.OrderState state, OrderApi.CanceledOrderCommand command) {
    log.info("state: {}, CanceledOrderCommand: {}", state, command);

    return effects()
        .emitEvent(eventFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(OrderEntity.OrderState state, OrderApi.ShippedOrderSkuCommand command) {
    log.info("state: {}, ShippedOrderSkuCommand: {}", state, command);

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  static OrderEntity.OrderCreated eventFor(OrderEntity.OrderState state, OrderApi.CreateOrderCommand command) {
    return OrderEntity.OrderCreated
        .newBuilder()
        .setOrderId(command.getOrderId())
        .setCustomerId(command.getCustomerId())
        .setOrderedUtc(command.getOrderedUtc())
        .addAllOrderItems(toOrderITems(command.getOrderItemsList()))
        .build();
  }

  static OrderEntity.OrderShipped eventFor(OrderEntity.OrderState state, OrderApi.ShippedOrderCommand command) {
    return OrderEntity.OrderShipped
        .newBuilder()
        .setShippedUtc(command.getShippedUtc())
        .build();
  }

  static OrderEntity.OrderDelivered eventFor(OrderEntity.OrderState state, OrderApi.DeliveredOrderCommand command) {
    return OrderEntity.OrderDelivered
        .newBuilder()
        .setDeliveredUtc(TimeTo.now())
        .build();
  }

  static OrderEntity.OrderReturned eventFor(OrderEntity.OrderState state, OrderApi.ReturnedOrderCommand command) {
    return OrderEntity.OrderReturned
        .newBuilder()
        .setReturnedUtc(TimeTo.now())
        .build();
  }

  static OrderEntity.OrderCancelled eventFor(OrderEntity.OrderState state, OrderApi.CanceledOrderCommand command) {
    return OrderEntity.OrderCancelled
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setCanceledUtc(TimeTo.now())
        .build();
  }

  static List<?> eventsFor(OrderEntity.OrderState state, OrderApi.ShippedOrderSkuCommand command) {
    var orderItemShipped = OrderEntity.OrderItemShipped
        .newBuilder()
        .setOrderId(state.getOrderId())
        .setSkuId(command.getSkuId())
        .setShippedUtc(command.getShippedUtc())
        .build();

    var notShipped = state.getOrderItemsList().stream()
        .filter(orderItem -> orderItem.getShippedUtc().getSeconds() == 0)
        .toList();

    if (notShipped.size() == 0 || notShipped.size() == 1 && notShipped.get(0).getSkuId().equals(command.getSkuId())) {
      var orderShipped = OrderEntity.OrderShipped
          .newBuilder()
          .setOrderId(state.getOrderId())
          .setShippedUtc(TimeTo.now())
          .build();

      return List.of(orderItemShipped, orderShipped);
    } else {
      return List.of(orderItemShipped);
    }
  }

  private Effect<OrderApi.Order> handle(OrderEntity.OrderState state, OrderApi.GetOrderRequest command) {
    return effects().reply(
        OrderApi.Order
            .newBuilder()
            .setOrderId(state.getOrderId())
            .setCustomerId(state.getCustomerId())
            .setOrderedUtc(state.getOrderedUtc())
            .setShippedUtc(state.getShippedUtc())
            .setDeliveredUtc(state.getDeliveredUtc())
            .setReturnedUtc(state.getReturnedUtc())
            .setCanceledUtc(state.getCanceledUtc())
            .addAllOrderItems(toOrderItems(state.getOrderItemsList()))
            .build());
  }

  static List<OrderApi.OrderItem> toOrderItems(List<OrderEntity.OrderItem> orderItems) {
    return orderItems.stream()
        .map(orderItem -> OrderApi.OrderItem
            .newBuilder()
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .setShippedUtc(orderItem.getShippedUtc())
            .build())
        .toList();
  }

  static List<OrderEntity.OrderItem> toOrderITems(List<OrderApi.OrderItem> orderItems) {
    return orderItems.stream()
        .map(orderItem -> OrderEntity.OrderItem
            .newBuilder()
            .setSkuId(orderItem.getSkuId())
            .setSkuName(orderItem.getSkuName())
            .setQuantity(orderItem.getQuantity())
            .build())
        .toList();
  }

  static List<OrderEntity.OrderItem> updateShippedOrderItem(List<OrderEntity.OrderItem> orderItems, OrderEntity.OrderItemShipped event) {
    return orderItems.stream()
        .map(orderItem -> {
          if (orderItem.getSkuId().equals(event.getSkuId())) {
            return orderItem
                .toBuilder()
                .setShippedUtc(event.getShippedUtc())
                .build();
          } else {
            return orderItem;
          }
        })
        .toList();
  }
}
