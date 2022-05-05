package io.mystore.stock.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.mystore.TimeTo;
import io.mystore.stock.api.StockSkuItemApi;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItemTest {

  @Test
  public void exampleTest() {
    // StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);
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
  public void createStockSkuItemTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    var response = testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    assertEquals(2, response.getAllEvents().size());
    var stockSkuItemCreated = response.getNextEventOfType(StockSkuItemEntity.StockSkuItemCreated.class);
    var stockRequestedJoinToOrder = response.getNextEventOfType(StockSkuItemEntity.StockRequestedJoinToOrder.class);

    assertEquals("stock-sku-item-1", stockSkuItemCreated.getStockSkuItemId());
    assertEquals("sku-1", stockSkuItemCreated.getSkuId());
    assertEquals("sku-name-1", stockSkuItemCreated.getSkuName());
    assertEquals("stock-order-1", stockSkuItemCreated.getStockOrderId());

    assertEquals("stock-sku-item-1", stockRequestedJoinToOrder.getStockSkuItemId());
    assertEquals("sku-1", stockRequestedJoinToOrder.getSkuId());
    assertEquals("stock-order-1", stockRequestedJoinToOrder.getStockOrderId());

    var state = testKit.getState();

    assertEquals("stock-sku-item-1", state.getStockSkuItemId());
    assertEquals("sku-1", state.getSkuId());
    assertEquals("sku-name-1", state.getSkuName());
    assertEquals("stock-order-1", state.getStockOrderId());
  }

  @Test
  public void orderRequestsJoinToStockTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    var response = testKit.orderRequestsJoinToStock(toOrderRequestsJoinToStockCommand("stock-sku-item-1", "order-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var orderRequestedJoinToStockAccepted = response.getNextEventOfType(StockSkuItemEntity.OrderRequestedJoinToStockAccepted.class);

    assertEquals("stock-sku-item-1", orderRequestedJoinToStockAccepted.getStockSkuItemId());
    assertEquals("sku-1", orderRequestedJoinToStockAccepted.getSkuId());
    assertEquals("stock-order-1", orderRequestedJoinToStockAccepted.getStockOrderId());
    assertEquals("order-sku-item-1", orderRequestedJoinToStockAccepted.getOrderSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStockAccepted.getOrderId());

    var state = testKit.getState();

    assertEquals("stock-sku-item-1", state.getStockSkuItemId());
    assertEquals("sku-1", state.getSkuId());
    assertEquals("sku-name-1", state.getSkuName());
    assertEquals("stock-order-1", state.getStockOrderId());
    assertEquals("order-sku-item-1", state.getOrderSkuItemId());
    assertEquals("order-1", state.getOrderId());
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void orderRequestsJoinToStockIdempotentTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    testKit.orderRequestsJoinToStock(toOrderRequestsJoinToStockCommand("stock-sku-item-1", "order-sku-item-1"));
    var response = testKit.orderRequestsJoinToStock(toOrderRequestsJoinToStockCommand("stock-sku-item-1", "order-sku-item-1"));

    assertEquals(0, response.getAllEvents().size());
  }

  @Test
  public void orderRequestsJoinToStockAlreadyJoinedTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    testKit.orderRequestsJoinToStock(toOrderRequestsJoinToStockCommand("stock-sku-item-1", "order-sku-item-1"));
    var response = testKit.orderRequestsJoinToStock(toOrderRequestsJoinToStockCommand("stock-sku-item-1", "order-sku-item-2"));

    assertEquals(1, response.getAllEvents().size());
    var orderRequestedJoinToStockRejected = response.getNextEventOfType(StockSkuItemEntity.OrderRequestedJoinToStockRejected.class);

    assertEquals("stock-sku-item-1", orderRequestedJoinToStockRejected.getStockSkuItemId());
    assertEquals("sku-1", orderRequestedJoinToStockRejected.getSkuId());
    assertEquals("stock-order-1", orderRequestedJoinToStockRejected.getStockOrderId());
    assertEquals("order-sku-item-2", orderRequestedJoinToStockRejected.getOrderSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStockRejected.getOrderId());

    var state = testKit.getState();

    assertEquals("stock-sku-item-1", state.getStockSkuItemId());
    assertEquals("sku-1", state.getSkuId());
    assertEquals("sku-name-1", state.getSkuName());
    assertEquals("stock-order-1", state.getStockOrderId());
    assertEquals("order-sku-item-1", state.getOrderSkuItemId());
    assertEquals("order-1", state.getOrderId());
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void orderRequestsJoinToStockRejectedTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    testKit.orderRequestsJoinToStock(toOrderRequestsJoinToStockCommand("stock-sku-item-1", "order-sku-item-1"));
    var response = testKit.orderRequestsJoinToStockRejected(toOrderRequestsJoinToStockRejectedCommand("stock-sku-item-1", "order-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var orderRequestedJoinToStockRejected = response.getNextEventOfType(StockSkuItemEntity.OrderRequestedJoinToStockRejected.class);

    assertEquals("stock-sku-item-1", orderRequestedJoinToStockRejected.getStockSkuItemId());
    assertEquals("sku-1", orderRequestedJoinToStockRejected.getSkuId());
    assertEquals("stock-order-1", orderRequestedJoinToStockRejected.getStockOrderId());
    assertEquals("order-sku-item-1", orderRequestedJoinToStockRejected.getOrderSkuItemId());
    assertEquals("order-1", orderRequestedJoinToStockRejected.getOrderId());

    var state = testKit.getState();

    assertTrue(state.getOrderSkuItemId().isEmpty());
    assertTrue(state.getOrderId().isEmpty());
    assertEquals(0, state.getShippedUtc().getSeconds());
  }

  @Test
  public void stockRequestedJoinToOrderAcceptedTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    var response = testKit.stockRequestedJoinToOrderAccepted(toStockRequestedJoinToOrderAcceptedCommand("stock-sku-item-1", "order-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var stockRequestedJoinToOrderAccepted = response.getNextEventOfType(StockSkuItemEntity.StockRequestedJoinToOrderAccepted.class);

    assertEquals("stock-sku-item-1", stockRequestedJoinToOrderAccepted.getStockSkuItemId());
    assertEquals("sku-1", stockRequestedJoinToOrderAccepted.getSkuId());
    assertEquals("stock-order-1", stockRequestedJoinToOrderAccepted.getStockOrderId());
    assertEquals("order-sku-item-1", stockRequestedJoinToOrderAccepted.getOrderSkuItemId());
    assertEquals("order-1", stockRequestedJoinToOrderAccepted.getOrderId());
    assertTrue(stockRequestedJoinToOrderAccepted.getShippedUtc().getSeconds() > 0);

    var state = testKit.getState();

    assertEquals("order-sku-item-1", state.getOrderSkuItemId());
    assertEquals("order-1", state.getOrderId());
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void stockRequestedJoinToOrderAcceptedIdempotentTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    testKit.stockRequestedJoinToOrderAccepted(toStockRequestedJoinToOrderAcceptedCommand("stock-sku-item-1", "order-sku-item-1"));
    var response = testKit.stockRequestedJoinToOrderAccepted(toStockRequestedJoinToOrderAcceptedCommand("stock-sku-item-1", "order-sku-item-1"));

    assertEquals(0, response.getAllEvents().size());
  }

  @Test
  public void stockRequestedJoinToOrderAlreadyAcceptedTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));
    testKit.stockRequestedJoinToOrderAccepted(toStockRequestedJoinToOrderAcceptedCommand("stock-sku-item-1", "order-sku-item-1"));
    var response = testKit.stockRequestedJoinToOrderAccepted(toStockRequestedJoinToOrderAcceptedCommand("stock-sku-item-1", "order-sku-item-2"));

    assertEquals(2, response.getAllEvents().size());
    var stockRequestedJoinToOrder = response.getNextEventOfType(StockSkuItemEntity.StockRequestedJoinToOrder.class);
    var stockRequestedJoinToOrderRejected = response.getNextEventOfType(StockSkuItemEntity.StockRequestedJoinToOrderRejected.class);

    assertEquals("stock-sku-item-1", stockRequestedJoinToOrder.getStockSkuItemId());
    assertEquals("sku-1", stockRequestedJoinToOrder.getSkuId());
    assertEquals("stock-order-1", stockRequestedJoinToOrder.getStockOrderId());

    assertEquals("stock-sku-item-1", stockRequestedJoinToOrderRejected.getStockSkuItemId());
    assertEquals("sku-1", stockRequestedJoinToOrderRejected.getSkuId());
    assertEquals("stock-order-1", stockRequestedJoinToOrderRejected.getStockOrderId());
    assertEquals("order-sku-item-2", stockRequestedJoinToOrderRejected.getOrderSkuItemId());
    assertEquals("order-1", stockRequestedJoinToOrderRejected.getOrderId());

    var state = testKit.getState();

    assertEquals("order-sku-item-1", state.getOrderSkuItemId());
    assertEquals("order-1", state.getOrderId());
    assertTrue(state.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void stockRequestedJoinToOrderRejectedTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));
    var response = testKit.stockRequestedJoinToOrderRejected(toStockRequestedJoinToOrderRejectedCommand("stock-sku-item-1", "order-sku-item-1"));

    assertEquals(1, response.getAllEvents().size());
    var stockRequestedJoinToOrder = response.getNextEventOfType(StockSkuItemEntity.StockRequestedJoinToOrder.class);

    assertEquals("stock-sku-item-1", stockRequestedJoinToOrder.getStockSkuItemId());
    assertEquals("sku-1", stockRequestedJoinToOrder.getSkuId());
    assertEquals("stock-order-1", stockRequestedJoinToOrder.getStockOrderId());
  }

  @Test
  public void stockRequestedJoinToOrderRejectedAlreadyJoinedTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));
    testKit.stockRequestedJoinToOrderAccepted(toStockRequestedJoinToOrderAcceptedCommand("stock-sku-item-1", "order-sku-item-1"));
    var response = testKit.stockRequestedJoinToOrderRejected(toStockRequestedJoinToOrderRejectedCommand("stock-sku-item-1", "order-sku-item-1"));

    assertEquals(0, response.getAllEvents().size());
  }

  @Test
  public void getStockSkuItemTest() {
    StockSkuItemTestKit testKit = StockSkuItemTestKit.of(StockSkuItem::new);

    testKit.createStockSkuItem(toCreateStockSkuItemCommand("stock-sku-item-1"));

    var response = testKit.getStockSkuItem(
        StockSkuItemApi.GetStockSKuItemRequest
            .newBuilder()
            .setStockSkuItemId("stock-sku-item-1")
            .build());

    var reply = response.getReply();

    assertEquals("stock-sku-item-1", reply.getStockSkuItemId());
    assertEquals("sku-1", reply.getSkuId());
    assertEquals("sku-name-1", reply.getSkuName());
    assertEquals("stock-order-1", reply.getStockOrderId());
    assertTrue(reply.getOrderSkuItemId().isEmpty());
    assertTrue(reply.getOrderId().isEmpty());
    assertTrue(reply.getShippedUtc().getSeconds() == 0);
  }

  static StockSkuItemApi.CreateStockSkuItemCommand toCreateStockSkuItemCommand(String string) {
    return StockSkuItemApi.CreateStockSkuItemCommand
        .newBuilder()
        .setStockSkuItemId(string)
        .setSkuId("sku-1")
        .setSkuName("sku-name-1")
        .setStockOrderId("stock-order-1")
        .build();
  }

  static StockSkuItemApi.OrderRequestsJoinToStockCommand toOrderRequestsJoinToStockCommand(String stockSkuItemId, String orderSkuItemId) {
    return StockSkuItemApi.OrderRequestsJoinToStockCommand
        .newBuilder()
        .setStockSkuItemId(stockSkuItemId)
        .setSkuId("sku-1")
        .setStockOrderId("stock-order-1")
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId("order-1")
        .build();
  }

  static StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand toOrderRequestsJoinToStockRejectedCommand(String stockSkuItemId, String orderSkuItemId) {
    return StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand
        .newBuilder()
        .setStockSkuItemId(stockSkuItemId)
        .setSkuId("sku-1")
        .setStockOrderId("stock-order-1")
        .setOrderSkuItemId(orderSkuItemId)
        .setOrderId("order-1")
        .build();
  }

  static StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand toStockRequestedJoinToOrderAcceptedCommand(String stockSkuItemId, String orderSkuItemId) {
    return StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand
        .newBuilder()
        .setStockSkuItemId(stockSkuItemId)
        .setSkuId("sku-1")
        .setOrderId("order-1")
        .setOrderSkuItemId(orderSkuItemId)
        .setShippedUtc(TimeTo.now())
        .setStockOrderId("stock-order-1")
        .build();
  }

  static StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand toStockRequestedJoinToOrderRejectedCommand(String stockSkuItemId, String orderSkuItemId) {
    return StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand
        .newBuilder()
        .setSkuId("sku-1")
        .setStockSkuItemId(stockSkuItemId)
        .setOrderId("order-1")
        .setOrderSkuItemId(orderSkuItemId)
        .setStockOrderId("stock-order-1")
        .build();
  }
}
