package io.order.entity;

import com.akkaserverless.javasdk.testkit.ValueEntityResult;
import com.akkaserverless.javasdk.valueentity.ValueEntity;
import com.google.protobuf.Empty;
import io.order.api.OrderApi;
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
    // ValueEntityResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
    // verify the final state after the command
    // assertEquals(expectedState, testKit.getState());
  }

  @Test
  public void addOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // ValueEntityResult<Empty> result = testKit.addOrder(Order.newBuilder()...build());
  }


  @Test
  public void getOrderTest() {
    OrderTestKit testKit = OrderTestKit.of(Order::new);
    // ValueEntityResult<Order> result = testKit.getOrder(GetOrderRequest.newBuilder()...build());
  }

}
