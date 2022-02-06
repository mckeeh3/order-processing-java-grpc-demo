package io.mystore.shipping2.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.akkaserverless.javasdk.testkit.EventSourcedResult;
import com.google.protobuf.Empty;

import io.mystore.TimeTo;
import io.mystore.shipping2.api.OrderSkuItemApi;
import io.mystore.shipping2.api.ShippingApi;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

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

    testKit.createOrderSkuItem(OrderSkuItemApi.CreateOrderSkuItemCommand.newBuilder()
        .setOrderSkuItemId("orderSkuItemId")
        .setCustomerId("customerId")
        .setOrderId("order-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setOrderedUtc(TimeTo.now())
        .build());

    var orderSkuItem = testKit.getOrderSkuItem(OrderSkuItemApi.GetOrderSkuItemRequest.newBuilder()
        .setOrderSkuItemId("orderSkuItemId")
        .build());
    log.info("orderSkuItem: {}", orderSkuItem.getReply());
  }

  @Test
  public void joinToStockSkuItemTest() {
    // OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);
    // EventSourcedResult<Empty> result = testKit.joinToStockSkuItem(JoinToStockSkuItemCommand.newBuilder()...build());
  }

  @Test
  public void backOrderOrderSkuItemTest() {
    // OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);
    // EventSourcedResult<Empty> result =
    // testKit.backOrderOrderSkuItem(BackOrderOrderSkuItemCommand.newBuilder()...build());
  }

  @Test
  public void getOrderSkuItemTest() {
    // OrderSkuItemTestKit testKit = OrderSkuItemTestKit.of(OrderSkuItem::new);
    // EventSourcedResult<OrderSkuItem> result = testKit.getOrderSkuItem(GetOrderSkuItemRequest.newBuilder()...build());
  }

}
