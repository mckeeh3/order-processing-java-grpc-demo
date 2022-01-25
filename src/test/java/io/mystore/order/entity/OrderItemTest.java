package io.mystore.order.entity;

import com.akkaserverless.javasdk.testkit.ValueEntityResult;
import com.akkaserverless.javasdk.valueentity.ValueEntity;
import com.google.protobuf.Empty;
import io.mystore.order.api.OrderItemApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderItemTest {

  @Test
  public void exampleTest() {
    OrderItemTestKit testKit = OrderItemTestKit.of(OrderItem::new);
    // use the testkit to execute a command
    // of events emitted, or a final updated state:
    // ValueEntityResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
    // verify the final state after the command
    // assertEquals(expectedState, testKit.getState());
  }

  @Test
  public void createOrderItemTest() {
    OrderItemTestKit testKit = OrderItemTestKit.of(OrderItem::new);
    // ValueEntityResult<Empty> result = testKit.createOrderItem(OrderItemCommand.newBuilder()...build());
  }


  @Test
  public void getOrderItemTest() {
    OrderItemTestKit testKit = OrderItemTestKit.of(OrderItem::new);
    // ValueEntityResult<GetOrderItemResponse> result = testKit.getOrderItem(GetOrderItemRequest.newBuilder()...build());
  }

}
