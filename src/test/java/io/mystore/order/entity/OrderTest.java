package io.mystore.order.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.akkaserverless.javasdk.testkit.EventSourcedResult;
import com.google.protobuf.Empty;
import io.mystore.order.api.OrderApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderTest {

  @Test
  public void exampleTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
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
    // EventSourcedResult<Empty> result = testKit.createOrder(CreateOrderRequest.newBuilder()...build());
  }


  @Test
  public void shippedOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.shippedOrder(ShippedOrderRequest.newBuilder()...build());
  }


  @Test
  public void deliveredOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.deliveredOrder(DeliveredOrderRequest.newBuilder()...build());
  }


  @Test
  public void returnedOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.returnedOrder(ReturnedOrderRequest.newBuilder()...build());
  }


  @Test
  public void canceledOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.canceledOrder(CanceledOrderRequest.newBuilder()...build());
  }


  @Test
  public void shippedOrderItemTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Empty> result = testKit.shippedOrderItem(ShippedOrderItemRequest.newBuilder()...build());
  }


  @Test
  public void getOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // EventSourcedResult<Order> result = testKit.getOrder(GetOrderRequest.newBuilder()...build());
  }

}
