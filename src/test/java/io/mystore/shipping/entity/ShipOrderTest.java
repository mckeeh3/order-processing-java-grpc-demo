package io.mystore.shipping.entity;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.List;

import com.akkaserverless.javasdk.testkit.EventSourcedResult;
import com.google.protobuf.Timestamp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShipOrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderTest {
  static final Logger log = LoggerFactory.getLogger(ShipOrderTest.class);

  @Test
  public void exampleTest() {
    // ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
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
  public void createShipOrderTest() {
    // ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // EventSourcedResult<Empty> result = testKit.createShipOrder(CreateShipOrderCommand.newBuilder()...build());
  }

  @Test
  public void shippedOrderItemTestWithOneItemQuantityOne() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);

    testKit.createShipOrder(ShipOrderApi.CreateShipOrderCommand.newBuilder()
        .setOrderId("order-id")
        .setCustomerId("customer-id")
        .setOrderedUtc(timestampNow())
        .addAllOrderItems(List.of(ShipOrderApi.OrderItemFromOrder.newBuilder()
            .setSkuId("sku-1")
            .setSkuName("sku-name")
            .setQuantity(1)
            .build()))
        .build());

    var shipOrderBefore = testKit.getShipOrder(ShipOrderApi.GetShipOrderRequest.newBuilder()
        .setOrderId("order-id")
        .build());
    log.info("shipOrderBefore: {}", shipOrderBefore.getReply());

    shipOrderItem("sku-1", 0, true, "sku-item-1-1", 0, true, true, testKit, shipOrderBefore);
  }

  @Test
  public void shippedOrderItemTestWithMultipleItemsMultipleQuantity() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);

    testKit.createShipOrder(ShipOrderApi.CreateShipOrderCommand.newBuilder()
        .setOrderId("order-id")
        .setCustomerId("customer-id")
        .setOrderedUtc(timestampNow())
        .addAllOrderItems(
            List.of(
                ShipOrderApi.OrderItemFromOrder.newBuilder()
                    .setSkuId("sku-1")
                    .setSkuName("sku-name-1")
                    .setQuantity(2)
                    .build(),
                ShipOrderApi.OrderItemFromOrder.newBuilder()
                    .setSkuId("sku-2")
                    .setSkuName("sku-name-2")
                    .setQuantity(3)
                    .build()))
        .build());

    var shipOrderBefore = testKit.getShipOrder(ShipOrderApi.GetShipOrderRequest.newBuilder()
        .setOrderId("order-id")
        .build());
    log.info("shipOrderBefore: {}", shipOrderBefore.getReply());

    shipOrderItem("sku-1", 0, false, "sku-item-1-1", 0, true, false, testKit, shipOrderBefore);
    shipOrderItem("sku-1", 0, true, "sku-item-1-2", 1, true, false, testKit, shipOrderBefore);
    shipOrderItem("sku-2", 1, false, "sku-item-2-1", 0, true, false, testKit, shipOrderBefore);
    shipOrderItem("sku-2", 1, false, "sku-item-2-2", 1, true, false, testKit, shipOrderBefore);
    shipOrderItem("sku-2", 1, true, "sku-item-2-3", 2, true, true, testKit, shipOrderBefore);
  }

  private void shipOrderItem(
      String skuId, int skuIdx, boolean skuShipped,
      String skuItemId, int skuItemIdx, boolean skuItemShipped,
      boolean orderShipped,
      ShipOrderTestKit testKit, EventSourcedResult<ShipOrderApi.ShipOrder> shipOrderBefore) {
    var command = ShipOrderApi.ShippedOrderItemCommand
        .newBuilder()
        .setOrderId("order-id")
        .setOrderItemId(shipOrderBefore.getReply().getShipOrderItemsList().get(skuIdx).getShipOrderItemsList().get(skuItemIdx).getOrderItemId())
        .setSkuId(skuId)
        .setSkuItemId(skuItemId)
        .setShippedUtc(timestampNow())
        .build();
    testKit.shippedOrderItem(command);

    var shipOrderAfter = logShipOrder("order-id", "shipOrderAfter:", testKit);

    log.info("ShippedOrderItemCommand: {}", command);
    log.info("shipOrderAfter: {}", shipOrderAfter.getReply());

    assertEquals(orderShipped, 0 != shipOrderAfter.getReply().getShippedUtc().getSeconds());
    assertEquals(skuShipped, 0 != shipOrderAfter.getReply()
        .getOrderItemsList().get(skuIdx)
        .getShippedUtc().getSeconds());
    assertEquals(skuShipped, 0 != shipOrderAfter.getReply()
        .getShipOrderItemsList().get(skuIdx)
        .getShippedUtc().getSeconds());
    assertEquals(skuItemShipped, 0 != shipOrderAfter.getReply()
        .getShipOrderItemsList().get(skuIdx)
        .getShipOrderItemsList().get(skuItemIdx)
        .getShippedUtc().getSeconds());
  }

  @Test
  public void getShipOrderTest() {
    // ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // EventSourcedResult<ShipOrder> result = testKit.getShipOrder(GetShipOrderRequest.newBuilder()...build());
  }

  static Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }

  static EventSourcedResult<ShipOrderApi.ShipOrder> logShipOrder(String orderId, String message, ShipOrderTestKit testKit) {
    var shipOrder = testKit.getShipOrder(ShipOrderApi.GetShipOrderRequest.newBuilder()
        .setOrderId(orderId)
        .build());
    log.info("{}: {}", message, shipOrder.getReply());
    return shipOrder;
  }
}
