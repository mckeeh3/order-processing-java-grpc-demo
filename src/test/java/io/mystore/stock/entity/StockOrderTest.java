package io.mystore.stock.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.stock.api.StockOrderApi;
// import io.mystore.stock.api.StockOrderApi.CreateStockOrderCommand;
// import io.mystore.stock.api.StockOrderApi.GetStockOrderRequest;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockOrderTest {
  static final Logger log = LoggerFactory.getLogger(StockOrderTest.class);

  @Test
  public void exampleTest() {
    // StockOrderTestKit testKit = StockOrderTestKit.of(StockOrder::new);
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
  public void createStockOrderTest() {
    StockOrderTestKit testKit = StockOrderTestKit.of(StockOrder::new);

    testKit.createStockOrder(StockOrderApi.CreateStockOrderCommand
        .newBuilder()
        .setStockOrderId("order-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    var stockOrder = testKit.getStockOrder(StockOrderApi.GetStockOrderRequest
        .newBuilder()
        .setStockOrderId("order-1")
        .build());

    log.info("reply: {}", stockOrder.getReply());

    assertEquals("order-1", stockOrder.getReply().getStockOrderId());
  }

  @Test
  public void joinStockSkuItemTest() {
    StockOrderTestKit testKit = StockOrderTestKit.of(StockOrder::new);

    var stockOrderId = "stock-order-1";
    var orderId = "order-1";
    var orderSkuItemId = "order-sku-item-1";

    testKit.createStockOrder(StockOrderApi.CreateStockOrderCommand
        .newBuilder()
        .setStockOrderId(stockOrderId)
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    var stockOrder = testKit.getStockOrder(StockOrderApi.GetStockOrderRequest
        .newBuilder()
        .setStockOrderId("order-1")
        .build());

    var stockSkuItem = stockOrder.getReply().getStockSkuItemsList().get(0);

    assertTrue(stockSkuItem.getOrderId().isEmpty());
    assertTrue(stockSkuItem.getOrderSkuItemId().isEmpty());
    assertTrue(stockSkuItem.getShippedUtc().getSeconds() == 0);

    testKit.joinStockSkuItem(StockOrderApi.JoinStockSkuItemToStockOrderCommand
        .newBuilder()
        .setStockOrderId(stockOrderId)
        .setSkuId("sku-1")
        .setStockSkuItemId(stockOrder.getReply().getStockSkuItemsList().get(0).getStockSkuItemId())
        .setOrderId(orderId)
        .setOrderSkuItemId(orderSkuItemId)
        .setShippedUtc(TimeTo.now())
        .build());

    stockOrder = testKit.getStockOrder(StockOrderApi.GetStockOrderRequest
        .newBuilder()
        .setStockOrderId(stockOrderId)
        .build());

    log.info("reply: {}", stockOrder.getReply());

    stockSkuItem = stockOrder.getReply().getStockSkuItemsList().get(0);

    assertEquals(stockOrderId, stockOrder.getReply().getStockOrderId());
    assertEquals(orderSkuItemId, stockSkuItem.getOrderSkuItemId());
    assertTrue(stockSkuItem.getShippedUtc().getSeconds() > 0);
    assertTrue(stockOrder.getReply().getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void releaseStockSkuItemTest() {
    // StockOrderTestKit testKit = StockOrderTestKit.of(StockOrder::new);
    // EventSourcedResult<Empty> result = testKit.releaseStockSkuItem(ReleaseStockSkuItemCommand.newBuilder()...build());
  }

  @Test
  public void getStockOrderTest() {
    // StockOrderTestKit testKit = StockOrderTestKit.of(StockOrder::new);
    // EventSourcedResult<StockOrder> result = testKit.getStockOrder(GetStockOrderRequest.newBuilder()...build());
  }

}
