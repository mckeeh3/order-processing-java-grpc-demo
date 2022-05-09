package io.mystore.order.entity;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

import io.mystore.TimeTo;
import io.mystore.order.api.OrderApi;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderTest {

  @Test
  public void exampleTest() {
    // OrderTestKit testKit = OrderTestKit.of(Order::new);
    // use the testkit to execute a command
    // of events emitted, or a final updated state:
    // EventSourcedResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the emitted events
    // ExpectedEvent actualEvent = result.getNextEventOfType(ExpectedEvent.class);
    // assertEquals(expectedEvent, actualEvent)
    // verify the final state after applying the events
    // assertEquals(expectedState, testKit.getState());
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void createOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    var skuIdQuantity2 = 2;
    var skuIdQuantity3 = 3;
    var response = testKit.createOrder(toCreateOrderCommand(skuIdQuantity2, skuIdQuantity3));

    assertEquals(1, response.getAllEvents().size());
    var orderCreated = response.getNextEventOfType(OrderEntity.OrderCreated.class);

    assertEquals(2, orderCreated.getOrderItemsCount());
    assertEquals("sku-2", orderCreated.getOrderItems(0).getSkuId());
    assertEquals("sku-3", orderCreated.getOrderItems(1).getSkuId());
    assertEquals("sku name 2", orderCreated.getOrderItems(0).getSkuName());
    assertEquals("sku name 3", orderCreated.getOrderItems(1).getSkuName());
    assertEquals(skuIdQuantity2, orderCreated.getOrderItems(0).getQuantity());
    assertEquals(skuIdQuantity3, orderCreated.getOrderItems(1).getQuantity());

    var state = testKit.getState();

    assertEquals("order-id", state.getOrderId());
    assertEquals("customer-id", state.getCustomerId());
    assertTrue(state.getOrderedUtc().getSeconds() > 0);

    assertEquals(2, state.getOrderItemsCount());
    assertEquals("sku-2", state.getOrderItems(0).getSkuId());
    assertEquals("sku-3", state.getOrderItems(1).getSkuId());
    assertEquals("sku name 2", state.getOrderItems(0).getSkuName());
    assertEquals("sku name 3", state.getOrderItems(1).getSkuName());
    assertEquals(skuIdQuantity2, state.getOrderItems(0).getQuantity());
    assertEquals(skuIdQuantity3, state.getOrderItems(1).getQuantity());
  }

  @Test
  public void shippedOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(1));
    var response = testKit.shippedOrder(toShippedOrderCommand());

    assertEquals(1, response.getAllEvents().size());
    var orderShipped = response.getNextEventOfType(OrderEntity.OrderShipped.class);

    assertEquals("order-id", orderShipped.getOrderId());
    assertTrue(orderShipped.getShippedUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void releasedOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(1));
    testKit.shippedOrder(toShippedOrderCommand());
    var response = testKit.releasedOrder(toReleasedOrderCommand());

    assertEquals(1, response.getAllEvents().size());
    var orderReleased = response.getNextEventOfType(OrderEntity.OrderReleased.class);

    assertEquals("order-id", orderReleased.getOrderId());
    assertTrue(orderReleased.getShippedUtc().getSeconds() == 0);

    var state = testKit.getState();

    assertTrue(state.getShippedUtc().getSeconds() == 0);
  }

  @Test
  public void shippedOrderItemTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));

    var response = testKit.shippedOrderItem(toShippedOrderItemCommand("sku-2"));

    assertEquals(1, response.getAllEvents().size());
    var orderItemShipped = response.getNextEventOfType(OrderEntity.OrderItemShipped.class);

    assertEquals("order-id", orderItemShipped.getOrderId());
    assertEquals("sku-2", orderItemShipped.getSkuId());
    assertTrue(orderItemShipped.getShippedUtc().getSeconds() > 0);

    response = testKit.shippedOrderItem(toShippedOrderItemCommand("sku-3"));

    assertEquals(2, response.getAllEvents().size());
    orderItemShipped = response.getNextEventOfType(OrderEntity.OrderItemShipped.class);
    var orderShipped = response.getNextEventOfType(OrderEntity.OrderShipped.class);

    assertEquals("order-id", orderShipped.getOrderId());
    assertTrue(orderShipped.getShippedUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void releasedOrderItemTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));

    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-2"));
    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-3"));

    var response = testKit.releasedOrderItem(toReleasedOrderItemCommand("sku-2"));

    assertEquals(2, response.getAllEvents().size());
    var orderItemReleased = response.getNextEventOfType(OrderEntity.OrderItemReleased.class);
    var orderReleased = response.getNextEventOfType(OrderEntity.OrderReleased.class);

    assertEquals("order-id", orderItemReleased.getOrderId());
    assertEquals("sku-2", orderItemReleased.getSkuId());
    assertTrue(orderItemReleased.getShippedUtc().getSeconds() == 0);

    response = testKit.releasedOrderItem(toReleasedOrderItemCommand("sku-3"));

    assertEquals(1, response.getAllEvents().size());
    orderItemReleased = response.getNextEventOfType(OrderEntity.OrderItemReleased.class);

    assertEquals("order-id", orderReleased.getOrderId());
    assertTrue(orderReleased.getShippedUtc().getSeconds() == 0);

    var state = testKit.getState();

    assertTrue(state.getShippedUtc().getSeconds() == 0);
  }

  @Test
  public void deliveredOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));

    var response = testKit.deliveredOrder(toDeliveredOrderCommand());

    assertEquals(0, response.getAllEvents().size());
    assertTrue("cannot deliver order before shipped", response.isError());

    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-2"));
    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-3"));
    testKit.shippedOrder(toShippedOrderCommand());

    response = testKit.deliveredOrder(toDeliveredOrderCommand());

    assertEquals(1, response.getAllEvents().size());
    var orderDelivered = response.getNextEventOfType(OrderEntity.OrderDelivered.class);

    assertEquals("order-id", orderDelivered.getOrderId());
    assertTrue(orderDelivered.getDeliveredUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertTrue(state.getDeliveredUtc().getSeconds() > 0);
  }

  @Test
  public void returnedOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));

    var response = testKit.returnedOrder(toReturnedOrderCommand());

    assertEquals(0, response.getAllEvents().size());
    assertTrue("cannot return order before shipped", response.isError());

    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-2"));
    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-3"));
    testKit.shippedOrder(toShippedOrderCommand());

    testKit.deliveredOrder(toDeliveredOrderCommand());

    response = testKit.returnedOrder(toReturnedOrderCommand());

    assertEquals(1, response.getAllEvents().size());
    var orderReturned = response.getNextEventOfType(OrderEntity.OrderReturned.class);

    assertEquals("order-id", orderReturned.getOrderId());
    assertTrue(orderReturned.getReturnedUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertTrue(state.getReturnedUtc().getSeconds() > 0);
  }

  @Test
  public void canceledOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));

    var response = testKit.canceledOrder(toCanceledOrderCommand());

    assertEquals(0, response.getAllEvents().size());
    assertTrue("cannot cancel order before shipped", response.isError());

    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-2"));
    testKit.shippedOrderItem(toShippedOrderItemCommand("sku-3"));
    testKit.shippedOrder(toShippedOrderCommand());

    response = testKit.canceledOrder(toCanceledOrderCommand());

    assertEquals(1, response.getAllEvents().size());
    var orderCancelled = response.getNextEventOfType(OrderEntity.OrderCancelled.class);

    assertEquals("order-id", orderCancelled.getOrderId());
    assertTrue(orderCancelled.getCanceledUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertTrue(state.getCanceledUtc().getSeconds() > 0);
  }

  @Test
  public void getOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));
    var response = testKit.getOrder(toGetOrderCommand());
    var reply = response.getReply();

    assertEquals("order-id", reply.getOrderId());
    assertEquals("customer-id", reply.getCustomerId());
    assertEquals(2, reply.getOrderItemsCount());
    assertEquals("sku-2", reply.getOrderItems(0).getSkuId());
    assertEquals("sku-3", reply.getOrderItems(1).getSkuId());
    assertEquals("sku name 2", reply.getOrderItems(0).getSkuName());
    assertEquals("sku name 3", reply.getOrderItems(1).getSkuName());
    assertEquals(2, reply.getOrderItems(0).getQuantity());
    assertEquals(3, reply.getOrderItems(1).getQuantity());
  }

  static OrderApi.CreateOrderCommand toCreateOrderCommand(int... skuIdQuantity) {
    return OrderApi.CreateOrderCommand
        .newBuilder()
        .setOrderId("order-id")
        .setCustomerId("customer-id")
        .setOrderedUtc(TimeTo.now())
        .addAllOrderItems(
            IntStream.range(0, skuIdQuantity.length)
                .mapToObj(i -> OrderApi.OrderItem.newBuilder()
                    .setSkuId("sku-" + skuIdQuantity[i])
                    .setSkuName("sku name " + skuIdQuantity[i])
                    .setQuantity(skuIdQuantity[i])
                    .build())
                .toList())
        .build();
  }

  static OrderApi.ShippedOrderCommand toShippedOrderCommand() {
    return OrderApi.ShippedOrderCommand
        .newBuilder()
        .setOrderId("order-id")
        .setShippedUtc(TimeTo.now())
        .build();
  }

  static OrderApi.ReleasedOrderCommand toReleasedOrderCommand() {
    return OrderApi.ReleasedOrderCommand
        .newBuilder()
        .setOrderId("order-id")
        .build();
  }

  static OrderApi.ShippedOrderSkuCommand toShippedOrderItemCommand(String skuId) {
    return OrderApi.ShippedOrderSkuCommand
        .newBuilder()
        .setOrderId("order-id")
        .setSkuId(skuId)
        .setShippedUtc(TimeTo.now())
        .build();
  }

  static OrderApi.ReleasedOrderSkuCommand toReleasedOrderItemCommand(String skuId) {
    return OrderApi.ReleasedOrderSkuCommand
        .newBuilder()
        .setOrderId("order-id")
        .setSkuId(skuId)
        .build();
  }

  static OrderApi.GetOrderRequest toGetOrderCommand() {
    return OrderApi.GetOrderRequest
        .newBuilder()
        .setOrderId("order-id")
        .build();
  }

  static OrderApi.DeliveredOrderCommand toDeliveredOrderCommand() {
    return OrderApi.DeliveredOrderCommand
        .newBuilder()
        .setOrderId("order-id")
        .build();
  }

  static OrderApi.ReturnedOrderCommand toReturnedOrderCommand() {
    return OrderApi.ReturnedOrderCommand
        .newBuilder()
        .setOrderId("order-id")
        .build();
  }

  static OrderApi.CanceledOrderCommand toCanceledOrderCommand() {
    return OrderApi.CanceledOrderCommand
        .newBuilder()
        .setOrderId("order-id")
        .build();
  }
}
