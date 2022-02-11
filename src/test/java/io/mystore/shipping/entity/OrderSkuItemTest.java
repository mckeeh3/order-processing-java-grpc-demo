package io.mystore.shipping.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.shipping.api.OrderSkuItemApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemTest {
  static final Logger log = LoggerFactory.getLogger(OrderSkuItemTest.class);

  @Test
  public void exampleTest() {
    // OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);
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
  public void createOrderSkuItemTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    var orderSkuItemId = "orderSkuItemId";
    var orderId = "orderId";
    var skuId = "skuId";

    testKit.createOrderSkuItem(OrderSkuItemApi.CreateOrderSkuItemCommand.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setCustomerId("customerId")
        .setOrderId(orderId)
        .setSkuId(skuId)
        .setSkuName("sku name 1")
        .setOrderedUtc(TimeTo.now())
        .build());

    var orderSkuItem = testKit.getOrderSkuItem(OrderSkuItemApi.GetOrderSkuItemRequest.newBuilder()
        .setOrderSkuItemId("orderSkuItemId")
        .build());
    log.info("orderSkuItem: {}", orderSkuItem.getReply());

    assertEquals(orderSkuItemId, orderSkuItem.getReply().getOrderSkuItemId());
    assertEquals(orderId, orderSkuItem.getReply().getOrderId());
    assertEquals(skuId, orderSkuItem.getReply().getSkuId());
    assertNotEquals(0, orderSkuItem.getReply().getOrderedUtc().getSeconds());
    assertEquals(0, orderSkuItem.getReply().getShippedUtc().getSeconds());
    assertEquals(0, orderSkuItem.getReply().getBackOrderedUtc().getSeconds());
  }

  @Test
  public void joinToStockSkuItemTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    var orderSkuItemId = "orderSkuItemId";
    var orderId = "orderId";
    var skuId = "skuId";
    var stockSkuItemId = "stockSkuItemId";

    testKit.createOrderSkuItem(OrderSkuItemApi.CreateOrderSkuItemCommand.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setCustomerId("customerId")
        .setOrderId(orderId)
        .setSkuId(skuId)
        .setSkuName("sku name 1")
        .setOrderedUtc(TimeTo.now())
        .build());

    testKit.joinToStockSkuItem(OrderSkuItemApi.JoinToStockSkuItemCommand.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId(orderId)
        .setSkuId(skuId)
        .setStockSkuItemId(stockSkuItemId)
        .setShippedUtc(TimeTo.now())
        .build());

    var orderSkuItem = testKit.getOrderSkuItem(OrderSkuItemApi.GetOrderSkuItemRequest.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .build());
    log.info("orderSkuItem: {}", orderSkuItem.getReply());

    assertNotEquals(0, orderSkuItem.getReply().getShippedUtc().getSeconds());
  }

  @Test
  public void backOrderOrderSkuItemTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    var orderSkuItemId = "orderSkuItemId";
    var orderId = "orderId";
    var skuId = "skuId";
    var stockSkuItemId = "stockSkuItemId";

    testKit.createOrderSkuItem(OrderSkuItemApi.CreateOrderSkuItemCommand.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setCustomerId("customerId")
        .setOrderId(orderId)
        .setSkuId(skuId)
        .setSkuName("sku name 1")
        .setOrderedUtc(TimeTo.now())
        .build());

    testKit.backOrderOrderSkuItem(OrderSkuItemApi.BackOrderOrderSkuItemCommand.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId(orderId)
        .setSkuId(skuId)
        .build());

    var orderSkuItem = testKit.getOrderSkuItem(OrderSkuItemApi.GetOrderSkuItemRequest.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .build());
    log.info("orderSkuItem: {}", orderSkuItem.getReply());

    assertNotEquals(0, orderSkuItem.getReply().getBackOrderedUtc().getSeconds());

    testKit.joinToStockSkuItem(OrderSkuItemApi.JoinToStockSkuItemCommand.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId(orderId)
        .setSkuId(skuId)
        .setStockSkuItemId(stockSkuItemId)
        .setShippedUtc(TimeTo.now())
        .build());

    orderSkuItem = testKit.getOrderSkuItem(OrderSkuItemApi.GetOrderSkuItemRequest.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .build());
    log.info("orderSkuItem: {}", orderSkuItem.getReply());

    assertNotEquals(0, orderSkuItem.getReply().getShippedUtc().getSeconds());
    assertEquals(0, orderSkuItem.getReply().getBackOrderedUtc().getSeconds());

    testKit.backOrderOrderSkuItem(OrderSkuItemApi.BackOrderOrderSkuItemCommand.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId(orderId)
        .setSkuId(skuId)
        .build());

    orderSkuItem = testKit.getOrderSkuItem(OrderSkuItemApi.GetOrderSkuItemRequest.newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .build());
    log.info("orderSkuItem: {}", orderSkuItem.getReply());

    assertNotEquals(0, orderSkuItem.getReply().getShippedUtc().getSeconds());
    assertEquals(0, orderSkuItem.getReply().getBackOrderedUtc().getSeconds());
  }

  @Test
  public void getOrderSkuItemTest() {
    // OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);
    // EventSourcedResult<OrderSkuItem> result = testKit.getOrderSkuItem(GetOrderSkuItemRequest.newBuilder()...build());
  }
}
