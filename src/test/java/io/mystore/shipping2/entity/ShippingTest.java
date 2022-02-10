package io.mystore.shipping2.entity;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.testkit.EventSourcedResult;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.shipping2.api.ShippingApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingTest {
  static final Logger log = LoggerFactory.getLogger(ShippingTest.class);

  @Test
  public void exampleTest() {
    // ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);
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
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    var sku2quantity = 2;
    var sku3quantity = 3;
    var result = testKit.createOrder(ShippingApi.CreateOrderCommand.newBuilder()
        .setOrderId("order-id")
        .setCustomerId("customer-id")
        .setOrderedUtc(TimeTo.now())
        .addAllOrderItems(
            List.of(
                ShippingApi.OrderItem.newBuilder()
                    .setSkuId("sku-2")
                    .setSkuName("sku name 2")
                    .setQuantity(sku2quantity)
                    .build(),
                ShippingApi.OrderItem.newBuilder()
                    .setSkuId("sku-3")
                    .setSkuName("sku name 3")
                    .setQuantity(sku3quantity)
                    .build()))
        .build());

    var events = result.getAllEvents();
    events.forEach(event -> log.info("event({}): {}", event.getClass().getName(), event));

    assertEquals(1, events.size());

    var orderCreated = (ShippingEntity.OrderCreated) events.get(0);
    var results = orderCreated.getOrderItemsList().stream()
        .flatMap(orderItems -> orderItems.getOrderSkuItemsList().stream())
        .collect(Collectors.toList());

    log.info("results({}): {}", results.size(), results);
    assertEquals(sku2quantity + sku3quantity, results.size());
  }

  @Test
  public void shippedOrderSkuItemTest() {
    // ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);
    // EventSourcedResult<Empty> result = testKit.shippedOrderSkuItem(ShippedOrderSkuItemCommand.newBuilder()...build());
  }

  @Test
  public void getOrderTest() {
    // ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);
    // EventSourcedResult<Order> result = testKit.getOrder(GetOrderRequest.newBuilder()...build());
  }

  @Test
  public void shippedOrderWithItemQuantltyOne() {
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    testKit.createOrder(ShippingApi.CreateOrderCommand.newBuilder()
        .setOrderId("order-id")
        .setCustomerId("customer-id")
        .setOrderedUtc(TimeTo.now())
        .addAllOrderItems(
            List.of(
                ShippingApi.OrderItem.newBuilder()
                    .setSkuId("sku-1")
                    .setSkuName("sku name 1")
                    .setQuantity(1)
                    .build()))
        .build());

    var orderBefore = testKit.getOrder(ShippingApi.GetOrderRequest.newBuilder()
        .setOrderId("order-id")
        .build());
    log.info("orderBefore: {}", orderBefore.getReply());

    shipOrderItem("sku-1", 0, true, "order-sku-item-1-1", 0, true, true, testKit, orderBefore);
  }

  @Test
  public void shippedOrderWithMultipleOrderItemsMultipleQuantity() {
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    testKit.createOrder(ShippingApi.CreateOrderCommand.newBuilder()
        .setOrderId("order-id")
        .setCustomerId("customer-id")
        .setOrderedUtc(TimeTo.now())
        .addAllOrderItems(
            List.of(
                ShippingApi.OrderItem.newBuilder()
                    .setSkuId("sku-2")
                    .setSkuName("sku name 2")
                    .setQuantity(2)
                    .build(),
                ShippingApi.OrderItem.newBuilder()
                    .setSkuId("sku-3")
                    .setSkuName("sku name 3")
                    .setQuantity(3)
                    .build()))
        .build());

    var orderBefore = testKit.getOrder(ShippingApi.GetOrderRequest.newBuilder()
        .setOrderId("order-id")
        .build());
    log.info("orderBefore: {}", orderBefore.getReply());

    shipOrderItem("sku-2", 0, false, "order-sku-item-2-1", 0, true, false, testKit, orderBefore);
    shipOrderItem("sku-2", 0, true, "order-sku-item-2-2", 1, true, false, testKit, orderBefore);
    shipOrderItem("sku-3", 1, false, "order-sku-item-3-1", 0, true, false, testKit, orderBefore);
    shipOrderItem("sku-3", 1, false, "order-sku-item-3-2", 1, true, false, testKit, orderBefore);
    shipOrderItem("sku-3", 1, true, "order-sku-item-3-3", 2, true, true, testKit, orderBefore);
  }

  private void shipOrderItem(
      String skuId, int skuIdx, boolean orderItemShipped,
      String orderSkuItemId, int orderSkuItemIdx, boolean orderSkuItemShipped,
      boolean orderShipped,
      ShippingTestKit testKit, EventSourcedResult<ShippingApi.Order> shipOrderBefore) {
    var command = ShippingApi.ShippedOrderSkuItemCommand
        .newBuilder()
        .setOrderId("order-id")
        .setOrderSkuItemId(shipOrderBefore.getReply().getOrderItemsList().get(skuIdx).getOrderSkuItemsList().get(orderSkuItemIdx).getOrderSkuItemId())
        .setSkuId(skuId)
        .setStockSkuItemId("stock-sku-item-id-" + skuId)
        .setShippedUtc(TimeTo.now())
        .build();
    testKit.shippedOrderSkuItem(command);

    var shipOrderAfter = logOrder("order-id", "shipOrderAfter:", testKit);

    assertEquals(orderShipped, 0 != shipOrderAfter.getReply().getShippedUtc().getSeconds());
    assertEquals(orderItemShipped, 0 != shipOrderAfter.getReply()
        .getOrderItemsList().get(skuIdx)
        .getShippedUtc().getSeconds());
    assertEquals(orderItemShipped, 0 != shipOrderAfter.getReply()
        .getOrderItemsList().get(skuIdx)
        .getShippedUtc().getSeconds());
    assertEquals(orderSkuItemShipped, 0 != shipOrderAfter.getReply()
        .getOrderItemsList().get(skuIdx)
        .getOrderSkuItemsList().get(orderSkuItemIdx)
        .getShippedUtc().getSeconds());
  }

  static EventSourcedResult<ShippingApi.Order> logOrder(String orderId, String message, ShippingTestKit testKit) {
    var shipOrder = testKit.getOrder(ShippingApi.GetOrderRequest.newBuilder()
        .setOrderId(orderId)
        .build());
    log.info("{}: {}", message, shipOrder.getReply());
    return shipOrder;
  }
}
