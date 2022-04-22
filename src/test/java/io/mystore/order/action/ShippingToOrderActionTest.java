package io.mystore.order.action;

import akka.stream.javadsl.Source;
import kalix.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.order.action.ShippingToOrderAction;
import io.mystore.order.action.ShippingToOrderActionTestKit;
import io.mystore.shipping.entity.ShippingEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingToOrderActionTest {

  @Test
  public void exampleTest() {
    ShippingToOrderActionTestKit testKit = ShippingToOrderActionTestKit.of(ShippingToOrderAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onOrderShippedTest() {
    ShippingToOrderActionTestKit testKit = ShippingToOrderActionTestKit.of(ShippingToOrderAction::new);
    // ActionResult<Empty> result =
    // testKit.onOrderShipped(ShippingEntity.OrderShipped.newBuilder()...build());
  }

  @Test
  public void onOrderItemShippedTest() {
    ShippingToOrderActionTestKit testKit = ShippingToOrderActionTestKit.of(ShippingToOrderAction::new);
    // ActionResult<Empty> result =
    // testKit.onOrderItemShipped(ShippingEntity.OrderItemShipped.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShippingToOrderActionTestKit testKit = ShippingToOrderActionTestKit.of(ShippingToOrderAction::new);
    // ActionResult<Empty> result =
    // testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
