package io.mystore.shipping.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.mystore.TimeTo;
import io.mystore.shipping.api.OrderSkuItemApi;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemTest {

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

    var response = testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    assertEquals(2, response.getAllEvents().size());
    var orderSkuItemCreated = response.getNextEventOfType(OrderSkuItemEntity.OrderSkuItemCreated.class);
    var orderRequestedJoinToStock = response.getNextEventOfType(OrderSkuItemEntity.OrderRequestedJoinToStock.class);

    assertEquals("order-sku-item-1", orderSkuItemCreated.getOrderSkuItemId());
    assertEquals("customer-1", orderSkuItemCreated.getCustomerId());
    assertEquals("order-1", orderSkuItemCreated.getOrderId());
    assertEquals("sku-1", orderSkuItemCreated.getSkuId());
    assertEquals("sku-name-1", orderSkuItemCreated.getSkuName());
    assertTrue(orderSkuItemCreated.getOrderedUtc().getSeconds() > 0);

    assertEquals("order-sku-item-1", orderRequestedJoinToStock.getOrderSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStock.getOrderId());
    assertEquals("sku-1", orderRequestedJoinToStock.getSkuId());

    var state = testKit.getState();

    assertEquals("order-sku-item-1", state.getOrderSkuItemId());
    assertEquals("order-1", state.getOrderId());
    assertEquals("sku-1", state.getSkuId());
    assertEquals("sku-name-1", state.getSkuName());
    assertEquals("customer-1", state.getCustomerId());
    assertTrue(state.getOrderedUtc().getSeconds() > 0);
  }

  @Test
  public void stockRequestsJoinToOrderTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    var response = testKit.stockRequestsJoinToOrder(toStockRequestsJoinToOrderCommand("order-sku-item-1", "stock-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var stockRequestedJoinToOrderAccepted = response.getNextEventOfType(OrderSkuItemEntity.StockRequestedJoinToOrderAccepted.class);

    assertEquals("order-sku-item-1", stockRequestedJoinToOrderAccepted.getOrderSkuItemId());
    assertEquals("stock-sku-item-1", stockRequestedJoinToOrderAccepted.getStockSkuItemId());
    assertEquals("order-1", stockRequestedJoinToOrderAccepted.getOrderId());
    assertEquals("sku-1", stockRequestedJoinToOrderAccepted.getSkuId());
    assertEquals("order-1", stockRequestedJoinToOrderAccepted.getOrderId());
    assertTrue(stockRequestedJoinToOrderAccepted.getShippedUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertEquals("order-sku-item-1", state.getOrderSkuItemId());
    assertEquals("stock-sku-item-1", state.getStockSkuItemId());
    assertEquals("order-1", state.getOrderId());
    assertEquals("sku-1", state.getSkuId());
    assertEquals("sku-name-1", state.getSkuName());
    assertEquals("customer-1", state.getCustomerId());
    assertTrue(state.getOrderedUtc().getSeconds() > 0);
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void stockRequestsJoinToOrderIdempotentTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    testKit.stockRequestsJoinToOrder(toStockRequestsJoinToOrderCommand("order-sku-item-1", "stock-sku-item-1"));
    var response = testKit.stockRequestsJoinToOrder(toStockRequestsJoinToOrderCommand("order-sku-item-1", "stock-sku-item-1"));

    assertEquals(0, response.getAllEvents().size());
  }

  @Test
  public void stockRequestsJoinToOrderAlreadyJoinedTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    testKit.stockRequestsJoinToOrder(toStockRequestsJoinToOrderCommand("order-sku-item-1", "stock-sku-item-1"));
    var response = testKit.stockRequestsJoinToOrder(toStockRequestsJoinToOrderCommand("order-sku-item-1", "stock-sku-item-2"));

    assertEquals(1, response.getAllEvents().size());
    var stockRequestedJoinToOrderRejected = response.getNextEventOfType(OrderSkuItemEntity.StockRequestedJoinToOrderRejected.class);

    assertEquals("order-sku-item-1", stockRequestedJoinToOrderRejected.getOrderSkuItemId());
    assertEquals("stock-sku-item-2", stockRequestedJoinToOrderRejected.getStockSkuItemId());
    assertEquals("order-1", stockRequestedJoinToOrderRejected.getOrderId());
    assertEquals("sku-1", stockRequestedJoinToOrderRejected.getSkuId());
    assertEquals("stock-order-1", stockRequestedJoinToOrderRejected.getStockOrderId());

    var state = testKit.getState();

    assertEquals("order-sku-item-1", state.getOrderSkuItemId());
    assertEquals("stock-sku-item-1", state.getStockSkuItemId());
    assertEquals("order-1", state.getOrderId());
    assertEquals("sku-1", state.getSkuId());
    assertEquals("sku-name-1", state.getSkuName());
    assertEquals("customer-1", state.getCustomerId());
    assertTrue(state.getOrderedUtc().getSeconds() > 0);
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void stockRequestsJoinToOrderRejectedTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    testKit.stockRequestsJoinToOrder(toStockRequestsJoinToOrderCommand("order-sku-item-1", "stock-sku-item-1"));
    var response = testKit.stockRequestsJoinToOrderRejected(toStockRequestsJoinToOrderRejectedCommand("order-sku-item-1", "stock-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var stockRequestedJoinToOrderRejected = response.getNextEventOfType(OrderSkuItemEntity.StockRequestedJoinToOrderRejected.class);

    assertEquals("order-sku-item-1", stockRequestedJoinToOrderRejected.getOrderSkuItemId());
    assertEquals("stock-sku-item-1", stockRequestedJoinToOrderRejected.getStockSkuItemId());
    assertEquals("order-1", stockRequestedJoinToOrderRejected.getOrderId());
    assertEquals("sku-1", stockRequestedJoinToOrderRejected.getSkuId());
    assertEquals("stock-order-1", stockRequestedJoinToOrderRejected.getStockOrderId());

    var state = testKit.getState();

    assertTrue(state.getStockSkuItemId().isEmpty());
    assertTrue(state.getStockOrderId().isEmpty());
    assertEquals(0, state.getShippedUtc().getSeconds());
  }

  @Test
  public void orderRequestedJoinToStockAcceptedTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    var response = testKit.orderRequestedJoinToStockAccepted(toOrderRequestedJoinToStockAcceptedCommand("order-sku-item-1", "stock-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var orderRequestedJoinToStockAccepted = response.getNextEventOfType(OrderSkuItemEntity.OrderRequestedJoinToStockAccepted.class);

    assertEquals("order-sku-item-1", orderRequestedJoinToStockAccepted.getOrderSkuItemId());
    assertEquals("stock-sku-item-1", orderRequestedJoinToStockAccepted.getStockSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStockAccepted.getOrderId());
    assertEquals("sku-1", orderRequestedJoinToStockAccepted.getSkuId());
    assertEquals("stock-order-1", orderRequestedJoinToStockAccepted.getStockOrderId());
    assertTrue(orderRequestedJoinToStockAccepted.getShippedUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertEquals("stock-sku-item-1", state.getStockSkuItemId());
    assertEquals("stock-order-1", state.getStockOrderId());
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void orderRequestedJoinToStockAcceptedIdempotentTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    testKit.orderRequestedJoinToStockAccepted(toOrderRequestedJoinToStockAcceptedCommand("order-sku-item-1", "stock-sku-item-1"));
    var response = testKit.orderRequestedJoinToStockAccepted(toOrderRequestedJoinToStockAcceptedCommand("order-sku-item-1", "stock-sku-item-1"));

    assertEquals(0, response.getAllEvents().size());
  }

  @Test
  public void orderRequestedJoinToStockAlreadyAcceptedTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    testKit.orderRequestedJoinToStockAccepted(toOrderRequestedJoinToStockAcceptedCommand("order-sku-item-1", "stock-sku-item-1"));
    var response = testKit.orderRequestedJoinToStockAccepted(toOrderRequestedJoinToStockAcceptedCommand("order-sku-item-1", "stock-sku-item-2"));

    assertEquals(2, response.getAllEvents().size());
    var orderRequestedJoinToStock = response.getNextEventOfType(OrderSkuItemEntity.OrderRequestedJoinToStock.class);
    var orderRequestedJoinToStockRejected = response.getNextEventOfType(OrderSkuItemEntity.OrderRequestedJoinToStockRejected.class);

    assertEquals("order-sku-item-1", orderRequestedJoinToStock.getOrderSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStock.getOrderId());
    assertEquals("sku-1", orderRequestedJoinToStock.getSkuId());

    assertEquals("order-sku-item-1", orderRequestedJoinToStockRejected.getOrderSkuItemId());
    assertEquals("stock-sku-item-2", orderRequestedJoinToStockRejected.getStockSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStockRejected.getOrderId());
    assertEquals("sku-1", orderRequestedJoinToStockRejected.getSkuId());
    assertEquals("stock-order-1", orderRequestedJoinToStockRejected.getStockOrderId());

    var state = testKit.getState();

    assertEquals("stock-sku-item-1", state.getStockSkuItemId());
    assertEquals("stock-order-1", state.getStockOrderId());
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void orderRequestedJoinToStockRejectedTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));
    var response = testKit.orderRequestedJoinToStockRejected(toOrderRequestedJoinToStockRejectedCommand("order-sku-item-1", "stock-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var orderRequestedJoinToStock = response.getNextEventOfType(OrderSkuItemEntity.OrderRequestedJoinToStock.class);

    assertEquals("order-sku-item-1", orderRequestedJoinToStock.getOrderSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStock.getOrderId());
    assertEquals("sku-1", orderRequestedJoinToStock.getSkuId());
  }

  @Test
  public void orderRequestedJoinToStockRejectedAlreadyJoinedTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));
    testKit.orderRequestedJoinToStockAccepted(toOrderRequestedJoinToStockAcceptedCommand("order-sku-item-1", "stock-sku-item-1"));
    var response = testKit.orderRequestedJoinToStockRejected(toOrderRequestedJoinToStockRejectedCommand("order-sku-item-1", "stock-sku-item-2"));

    assertEquals(0, response.getAllEvents().size());
  }

  @Test
  public void backOrderOrderSkuItemTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    var response = testKit.backOrderOrderSkuItem(toBackOrderOrderSkuItemCommand("order-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var backOrderedOrderSkuItem = response.getNextEventOfType(OrderSkuItemEntity.BackOrderedOrderSkuItem.class);

    assertEquals("order-sku-item-1", backOrderedOrderSkuItem.getOrderSkuItemId());
    assertEquals("order-1", backOrderedOrderSkuItem.getOrderId());
    assertEquals("sku-1", backOrderedOrderSkuItem.getSkuId());
    assertTrue(backOrderedOrderSkuItem.getBackOrderedUtc().getSeconds() > 0);
  }

  @Test
  public void backOrderOrderSkuItemAlreadyShippedTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));
    testKit.stockRequestsJoinToOrder(toStockRequestsJoinToOrderCommand("order-sku-item-1", "stock-sku-item-1"));

    var response = testKit.backOrderOrderSkuItem(toBackOrderOrderSkuItemCommand("order-sku-item-1"));

    assertEquals(0, response.getAllEvents().size());
  }

  @Test
  public void getOrderSkuItemTest() {
    OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);

    testKit.createOrderSkuItem(toCreateOrderSkuItemCommand("order-sku-item-1"));

    var response = testKit.getOrderSkuItem(
        OrderSkuItemApi.GetOrderSkuItemRequest
            .newBuilder()
            .setOrderSkuItemId("order-sku-item-1")
            .build());

    var reply = response.getReply();

    assertEquals("customer-1", reply.getCustomerId());
    assertEquals("order-1", reply.getOrderId());
    assertEquals("order-sku-item-1", reply.getOrderSkuItemId());
    assertEquals("sku-1", reply.getSkuId());
    assertEquals("sku-name-1", reply.getSkuName());
    assertTrue(reply.getStockSkuItemId().isEmpty());
    assertTrue(reply.getStockOrderId().isEmpty());
    assertTrue(reply.getOrderedUtc().getSeconds() > 0);
    assertTrue(reply.getShippedUtc().getSeconds() == 0);
    assertTrue(reply.getBackOrderedUtc().getSeconds() == 0);
  }

  static OrderSkuItemApi.CreateOrderSkuItemCommand toCreateOrderSkuItemCommand(String orderSkuItemId) {
    return OrderSkuItemApi.CreateOrderSkuItemCommand
        .newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setCustomerId("customer-1")
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setSkuName("sku-name-1")
        .setOrderedUtc(TimeTo.now())
        .build();
  }

  static OrderSkuItemApi.StockRequestsJoinToOrderCommand toStockRequestsJoinToOrderCommand(String orderSkuItemId, String stockSkuItemId) {
    return OrderSkuItemApi.StockRequestsJoinToOrderCommand
        .newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setStockSkuItemId(stockSkuItemId)
        .setStockOrderId("stock-order-1")
        .build();
  }

  static OrderSkuItemApi.BackOrderOrderSkuItemCommand toBackOrderOrderSkuItemCommand(String orderSkuItemId) {
    return OrderSkuItemApi.BackOrderOrderSkuItemCommand
        .newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .build();
  }

  static OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand toOrderRequestedJoinToStockAcceptedCommand(String orderSkuItemId, String stockSkuItemId) {
    return OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand
        .newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setStockSkuItemId(stockSkuItemId)
        .setShippedUtc(TimeTo.now())
        .setStockOrderId("stock-order-1")
        .build();
  }

  static OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand toOrderRequestedJoinToStockRejectedCommand(String orderSkuItemId, String stockSkuItemId) {
    return OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand
        .newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setStockSkuItemId(stockSkuItemId)
        .build();
  }

  static OrderSkuItemApi.StockRequestsJoinToOrderRejectedCommand toStockRequestsJoinToOrderRejectedCommand(String orderSkuItemId, String stockSkuItemId) {
    return OrderSkuItemApi.StockRequestsJoinToOrderRejectedCommand
        .newBuilder()
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setStockSkuItemId(stockSkuItemId)
        .setStockOrderId("stock-order-1")
        .build();
  }
}
