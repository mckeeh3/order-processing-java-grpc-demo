package io.mystore.order.entity;

import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.List;

import com.google.protobuf.Timestamp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.order.api.OrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderTest {
  static final Logger log = LoggerFactory.getLogger(OrderTest.class);

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
    // OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.createOrder(CreateOrderRequest.newBuilder()...build());
  }

  @Test
  public void shippedOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(OrderApi.CreateOrderCommand
        .newBuilder()
        .setOrderId("order-1")
        .setCustomerId("customer-1")
        .setOrderedUtc(timestampNow())
        .addAllOrderItems(
            List.of(
                OrderApi.OrderItem
                    .newBuilder()
                    .setSkuId("sku-1")
                    .setSkuName("sku-name-1")
                    .setQuantity(1)
                    .build(),
                OrderApi.OrderItem
                    .newBuilder()
                    .setSkuId("sku-2")
                    .setSkuName("sku-name-2")
                    .setQuantity(2)
                    .build(),
                OrderApi.OrderItem
                    .newBuilder()
                    .setSkuId("sku-3")
                    .setSkuName("sku-name-3")
                    .setQuantity(3)
                    .build()))
        .build());

    testKit.shippedOrderItem(OrderApi.ShippedOrderSkuCommand
        .newBuilder()
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setShippedUtc(timestampNow())
        .build());

    var order = testKit.getOrder(OrderApi.GetOrderRequest
        .newBuilder()
        .setOrderId("order-1")
        .build());
    log.info("order: {}", order.getReply());

    assertTrue(order.getReply().getOrderItemsList().get(0).getShippedUtc() != null);
    assertTrue(order.getReply().getOrderItemsList().get(0).getShippedUtc().getSeconds() > 0);

    testKit.shippedOrderItem(OrderApi.ShippedOrderSkuCommand
        .newBuilder()
        .setOrderId("order-1")
        .setSkuId("sku-2")
        .setShippedUtc(timestampNow())
        .build());

    order = testKit.getOrder(OrderApi.GetOrderRequest
        .newBuilder()
        .setOrderId("order-1")
        .build());
    log.info("order: {}", order.getReply());

    assertTrue(order.getReply().getOrderItemsList().get(1).getShippedUtc() != null);
    assertTrue(order.getReply().getOrderItemsList().get(1).getShippedUtc().getSeconds() > 0);

    testKit.shippedOrderItem(OrderApi.ShippedOrderSkuCommand
        .newBuilder()
        .setOrderId("order-1")
        .setSkuId("sku-3")
        .setShippedUtc(timestampNow())
        .build());

    order = testKit.getOrder(OrderApi.GetOrderRequest
        .newBuilder()
        .setOrderId("order-1")
        .build());
    log.info("order: {}", order.getReply());

    assertTrue(order.getReply().getOrderItemsList().get(2).getShippedUtc() != null);
    assertTrue(order.getReply().getOrderItemsList().get(2).getShippedUtc().getSeconds() > 0);

    testKit.shippedOrder(OrderApi.ShippedOrderCommand
        .newBuilder()
        .setOrderId("order-1")
        .setShippedUtc(timestampNow())
        .build());

    order = testKit.getOrder(OrderApi.GetOrderRequest
        .newBuilder()
        .setOrderId("order-1")
        .build());
    log.info("order: {}", order.getReply());

    assertTrue(order.getReply().getShippedUtc() != null);
    assertTrue(order.getReply().getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void deliveredOrderTest() {
    // OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.deliveredOrder(DeliveredOrderRequest.newBuilder()...build());
  }

  @Test
  public void returnedOrderTest() {
    // OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.returnedOrder(ReturnedOrderRequest.newBuilder()...build());
  }

  @Test
  public void canceledOrderTest() {
    // OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.canceledOrder(CanceledOrderRequest.newBuilder()...build());
  }

  @Test
  public void shippedOrderItemTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);

    testKit.createOrder(OrderApi.CreateOrderCommand
        .newBuilder()
        .setOrderId("order-1")
        .setCustomerId("customer-1")
        .setOrderedUtc(timestampNow())
        .addAllOrderItems(
            List.of(OrderApi.OrderItem
                .newBuilder()
                .setSkuId("sku-1")
                .setSkuName("sku-name-1")
                .setQuantity(1)
                .build()))
        .build());

    testKit.shippedOrderItem(OrderApi.ShippedOrderSkuCommand
        .newBuilder()
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setShippedUtc(timestampNow())
        .build());

    var order = testKit.getOrder(OrderApi.GetOrderRequest
        .newBuilder()
        .setOrderId("order-1")
        .build());
    log.info("order: {}", order.getReply());

    assertTrue(order.getReply().getOrderItemsList().get(0).getShippedUtc() != null);
    assertTrue(order.getReply().getOrderItemsList().get(0).getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void getOrderTest() {
    // OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Order> result = testKit.getOrder(GetOrderRequest.newBuilder()...build());
  }

  static Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }
}
