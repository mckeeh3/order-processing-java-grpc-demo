package io.mystore.shipping.entity;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

import io.mystore.TimeTo;
import io.mystore.shipping.api.ShippingApi;
import io.mystore.shipping.entity.ShippingEntity.OrderSkuItem;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingTest {

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

    var skuIdQuantity2 = 2;
    var skuIdQuantity3 = 3;
    var response = testKit.createOrder(toCreateOrderCommand(skuIdQuantity2, skuIdQuantity3));

    assertEquals(1, response.getAllEvents().size());
    var orderCreated = response.getNextEventOfType(ShippingEntity.OrderCreated.class);

    assertEquals(2, orderCreated.getOrderItemsCount());
    assertEquals("sku-2", orderCreated.getOrderItems(0).getSkuId());
    assertEquals("sku-3", orderCreated.getOrderItems(1).getSkuId());
    assertEquals(skuIdQuantity2, orderCreated.getOrderItems(0).getQuantity());
    assertEquals(skuIdQuantity3, orderCreated.getOrderItems(1).getQuantity());

    var orderSkuItems = orderCreated.getOrderItemsList().stream()
        .flatMap(orderItems -> orderItems.getOrderSkuItemsList().stream())
        .toList();

    assertEquals(skuIdQuantity2 + skuIdQuantity3, orderSkuItems.size());

    assertEquals("sku-2", orderSkuItems.get(0).getSkuId());
    assertEquals("sku-2", orderSkuItems.get(1).getSkuId());
    assertEquals("sku-3", orderSkuItems.get(2).getSkuId());
    assertEquals("sku-3", orderSkuItems.get(3).getSkuId());
    assertEquals("sku-3", orderSkuItems.get(4).getSkuId());

    assertEquals("sku name 2", orderSkuItems.get(0).getSkuName());
    assertEquals("sku name 2", orderSkuItems.get(1).getSkuName());
    assertEquals("sku name 3", orderSkuItems.get(2).getSkuName());
    assertEquals("sku name 3", orderSkuItems.get(3).getSkuName());
    assertEquals("sku name 3", orderSkuItems.get(4).getSkuName());

    var state = testKit.getState();

    assertEquals("order-id", state.getOrderId());
    assertEquals("customer-id", state.getCustomerId());
    assertTrue(state.getOrderedUtc().getSeconds() > 0);

    assertEquals(2, state.getOrderItemsCount());
    assertEquals(skuIdQuantity2, state.getOrderItems(0).getOrderSkuItemsCount());
    assertEquals(skuIdQuantity3, state.getOrderItems(1).getOrderSkuItemsCount());

    assertEquals("sku-2", state.getOrderItems(0).getSkuId());
    assertEquals("sku-3", state.getOrderItems(1).getSkuId());
    assertEquals("sku name 2", state.getOrderItems(0).getSkuName());
    assertEquals("sku name 3", state.getOrderItems(1).getSkuName());
    assertEquals(skuIdQuantity2, state.getOrderItems(0).getQuantity());
    assertEquals(skuIdQuantity3, state.getOrderItems(1).getQuantity());

    assertEquals("sku-2", state.getOrderItems(0).getOrderSkuItems(0).getSkuId());
    assertEquals("sku-2", state.getOrderItems(0).getOrderSkuItems(1).getSkuId());
    assertEquals("sku-3", state.getOrderItems(1).getOrderSkuItems(0).getSkuId());
    assertEquals("sku-3", state.getOrderItems(1).getOrderSkuItems(1).getSkuId());
    assertEquals("sku-3", state.getOrderItems(1).getOrderSkuItems(2).getSkuId());

    assertEquals("sku name 2", state.getOrderItems(0).getOrderSkuItems(0).getSkuName());
    assertEquals("sku name 2", state.getOrderItems(0).getOrderSkuItems(1).getSkuName());
    assertEquals("sku name 3", state.getOrderItems(1).getOrderSkuItems(0).getSkuName());
    assertEquals("sku name 3", state.getOrderItems(1).getOrderSkuItems(1).getSkuName());
    assertEquals("sku name 3", state.getOrderItems(1).getOrderSkuItems(2).getSkuName());

    assertEquals("customer-id", state.getOrderItems(0).getOrderSkuItems(0).getCustomerId());
    assertEquals("order-id", state.getOrderItems(0).getOrderSkuItems(0).getOrderId());
    assertTrue(state.getOrderItems(0).getOrderSkuItems(0).getOrderedUtc().getSeconds() > 0);
    assertFalse(state.getOrderItems(0).getOrderSkuItems(0).getOrderSkuItemId().isEmpty());
  }

  @Test
  public void shippedOrderSkuItemTest() {
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    testKit.createOrder(toCreateOrderCommand(1));

    var state = testKit.getState();

    var response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-1", getOrderSkuItem(state, 0, 0)));

    assertEquals(3, response.getAllEvents().size());

    var orderSkuItemShipped = response.getNextEventOfType(ShippingEntity.OrderSkuItemShipped.class);
    var orderItemShipped = response.getNextEventOfType(ShippingEntity.OrderItemShipped.class);
    var orderShipped = response.getNextEventOfType(ShippingEntity.OrderShipped.class);

    assertEquals("order-id", orderSkuItemShipped.getOrderId());
    assertEquals(getOrderSkuItemId(state, 0, 0), orderSkuItemShipped.getOrderSkuItemId());
    assertEquals("sku-1", orderSkuItemShipped.getSkuId());
    assertEquals("stock-sku-item-1", orderSkuItemShipped.getStockSkuItemId());
    assertTrue(orderSkuItemShipped.getShippedUtc().getSeconds() > 0);

    assertEquals("order-id", orderItemShipped.getOrderId());
    assertEquals("sku-1", orderItemShipped.getSkuId());
    assertTrue(orderItemShipped.getShippedUtc().getSeconds() > 0);

    assertEquals("order-id", orderShipped.getOrderId());
    assertTrue(orderShipped.getShippedUtc().getSeconds() > 0);
  }

  @Test
  public void releaseOrderSkuItemTest() {
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    testKit.createOrder(toCreateOrderCommand(1));

    var state = testKit.getState();

    testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-1", getOrderSkuItem(state, 0, 0)));
    var response = testKit.releaseOrderSkuItem(toReleaseOrderSkuItemCommand("stock-sku-item-1", getOrderSkuItem(state, 0, 0)));

    assertEquals(3, response.getAllEvents().size());

    var orderSkuItemReleased = response.getNextEventOfType(ShippingEntity.OrderSkuItemReleased.class);
    var orderItemReleased = response.getNextEventOfType(ShippingEntity.OrderItemReleased.class);
    var orderReleased = response.getNextEventOfType(ShippingEntity.OrderReleased.class);

    assertEquals("order-id", orderSkuItemReleased.getOrderId());
    assertEquals(getOrderSkuItemId(state, 0, 0), orderSkuItemReleased.getOrderSkuItemId());
    assertEquals("sku-1", orderSkuItemReleased.getSkuId());
    assertEquals("stock-sku-item-1", orderSkuItemReleased.getStockSkuItemId());

    assertEquals("order-id", orderItemReleased.getOrderId());
    assertEquals("sku-1", orderItemReleased.getSkuId());

    assertEquals("order-id", orderReleased.getOrderId());
  }

  @Test
  public void getOrderTest() {
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    testKit.createOrder(toCreateOrderCommand(1));

    var reply = testKit.getOrder(
        ShippingApi.GetOrderRequest
            .newBuilder()
            .setOrderId("order-id")
            .build())
        .getReply();

    assertEquals("order-id", reply.getOrderId());
    assertEquals("customer-id", reply.getCustomerId());
    assertTrue(reply.getOrderedUtc().getSeconds() > 0);

    assertEquals(1, reply.getOrderItemsCount());
    assertEquals("sku-1", reply.getOrderItems(0).getSkuId());
    assertEquals("sku name 1", reply.getOrderItems(0).getSkuName());
    assertEquals(1, reply.getOrderItems(0).getQuantity());
  }

  @Test
  public void shippedOrderWithMultipleOrderItemsMultipleQuantity() {
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));

    var state = testKit.getState();

    var response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-1", getOrderSkuItem(state, 0, 0)));
    assertEquals(1, response.getAllEvents().size());
    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-2", getOrderSkuItem(state, 0, 1)));
    assertEquals(2, response.getAllEvents().size());

    response.getNextEventOfType(ShippingEntity.OrderSkuItemShipped.class);
    response.getNextEventOfType(ShippingEntity.OrderItemShipped.class);

    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-3", getOrderSkuItem(state, 1, 0)));
    assertEquals(1, response.getAllEvents().size());
    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-4", getOrderSkuItem(state, 1, 1)));
    assertEquals(1, response.getAllEvents().size());
    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-5", getOrderSkuItem(state, 1, 2)));
    assertEquals(3, response.getAllEvents().size());

    response.getNextEventOfType(ShippingEntity.OrderSkuItemShipped.class);
    response.getNextEventOfType(ShippingEntity.OrderItemShipped.class);
    response.getNextEventOfType(ShippingEntity.OrderShipped.class);
  }

  @Test
  public void releaseOrderWithMultipleOrderItemsMultipleQuantity() {
    ShippingTestKit testKit = ShippingTestKit.of(Shipping::new);

    testKit.createOrder(toCreateOrderCommand(2, 3));

    var state = testKit.getState();

    var response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-1", getOrderSkuItem(state, 0, 0)));
    assertEquals(1, response.getAllEvents().size());
    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-2", getOrderSkuItem(state, 0, 1)));
    assertEquals(2, response.getAllEvents().size());

    response.getNextEventOfType(ShippingEntity.OrderSkuItemShipped.class);
    response.getNextEventOfType(ShippingEntity.OrderItemShipped.class);

    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-3", getOrderSkuItem(state, 1, 0)));
    assertEquals(1, response.getAllEvents().size());
    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-4", getOrderSkuItem(state, 1, 1)));
    assertEquals(1, response.getAllEvents().size());
    response = testKit.shippedOrderSkuItem(toShippedOrderSkuItemCommand("stock-sku-item-5", getOrderSkuItem(state, 1, 2)));
    assertEquals(3, response.getAllEvents().size());

    response.getNextEventOfType(ShippingEntity.OrderSkuItemShipped.class);
    response.getNextEventOfType(ShippingEntity.OrderItemShipped.class);
    response.getNextEventOfType(ShippingEntity.OrderShipped.class);

    response = testKit.releaseOrderSkuItem(toReleaseOrderSkuItemCommand("stock-sku-item-1", getOrderSkuItem(state, 0, 0)));
    assertEquals(3, response.getAllEvents().size());

    response.getNextEventOfType(ShippingEntity.OrderSkuItemReleased.class);
    response.getNextEventOfType(ShippingEntity.OrderItemReleased.class);
    response.getNextEventOfType(ShippingEntity.OrderReleased.class);

    response = testKit.releaseOrderSkuItem(toReleaseOrderSkuItemCommand("stock-sku-item-2", getOrderSkuItem(state, 0, 1)));
    assertEquals(1, response.getAllEvents().size());

    response = testKit.releaseOrderSkuItem(toReleaseOrderSkuItemCommand("stock-sku-item-3", getOrderSkuItem(state, 1, 0)));
    assertEquals(2, response.getAllEvents().size());
    response = testKit.releaseOrderSkuItem(toReleaseOrderSkuItemCommand("stock-sku-item-4", getOrderSkuItem(state, 1, 1)));
    assertEquals(1, response.getAllEvents().size());
    response = testKit.releaseOrderSkuItem(toReleaseOrderSkuItemCommand("stock-sku-item-5", getOrderSkuItem(state, 1, 2)));
    assertEquals(1, response.getAllEvents().size());
  }

  static ShippingApi.CreateOrderCommand toCreateOrderCommand(int... skuIdQuantity) {
    return ShippingApi.CreateOrderCommand
        .newBuilder()
        .setOrderId("order-id")
        .setCustomerId("customer-id")
        .setOrderedUtc(TimeTo.now())
        .addAllOrderItems(
            IntStream.range(0, skuIdQuantity.length)
                .mapToObj(i -> ShippingApi.OrderItem.newBuilder()
                    .setSkuId("sku-" + skuIdQuantity[i])
                    .setSkuName("sku name " + skuIdQuantity[i])
                    .setQuantity(skuIdQuantity[i])
                    .build())
                .toList())
        .build();
  }

  static ShippingApi.ShippedOrderSkuItemCommand toShippedOrderSkuItemCommand(String stockSkuItemId, OrderSkuItem orderSkuItems) {
    return ShippingApi.ShippedOrderSkuItemCommand
        .newBuilder()
        .setOrderId(orderSkuItems.getOrderId())
        .setOrderSkuItemId(orderSkuItems.getOrderSkuItemId())
        .setSkuId(orderSkuItems.getSkuId())
        .setShippedUtc(TimeTo.now())
        .setStockSkuItemId(stockSkuItemId)
        .build();
  }

  static ShippingApi.ReleaseOrderSkuItemCommand toReleaseOrderSkuItemCommand(String stockSkuItemId, OrderSkuItem orderSkuItems) {
    return ShippingApi.ReleaseOrderSkuItemCommand
        .newBuilder()
        .setOrderId(orderSkuItems.getOrderId())
        .setOrderSkuItemId(orderSkuItems.getOrderSkuItemId())
        .setSkuId(orderSkuItems.getSkuId())
        .setStockSkuItemId(stockSkuItemId)
        .build();
  }

  static ShippingEntity.OrderSkuItem getOrderSkuItem(ShippingEntity.OrderState state, int orderItemIdx, int orderSkuItemIdx) {
    return state.getOrderItems(orderItemIdx).getOrderSkuItems(orderSkuItemIdx);
  }

  static String getOrderSkuItemId(ShippingEntity.OrderState state, int orderItemIdx, int orderSkuItemIdx) {
    return getOrderSkuItem(state, orderItemIdx, orderSkuItemIdx).getOrderSkuItemId();
  }
}
