package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import kalix.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.order.entity.OrderEntity;
import io.mystore.shipping.action.OrderToShippingAction;
import io.mystore.shipping.action.OrderToShippingActionTestKit;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderToShippingActionTest {

  @Test
  public void exampleTest() {
    OrderToShippingActionTestKit testKit = OrderToShippingActionTestKit.of(OrderToShippingAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onOrderCreatedTest() {
    OrderToShippingActionTestKit testKit = OrderToShippingActionTestKit.of(OrderToShippingAction::new);
    // ActionResult<Empty> result =
    // testKit.onOrderCreated(OrderEntity.OrderCreated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    OrderToShippingActionTestKit testKit = OrderToShippingActionTestKit.of(OrderToShippingAction::new);
    // ActionResult<Empty> result =
    // testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
