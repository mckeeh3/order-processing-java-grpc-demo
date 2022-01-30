package io.mystore.order.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.order.action.ShipOrderToOrderAction;
import io.mystore.order.action.ShipOrderToOrderActionTestKit;
import io.mystore.shipping.entity.ShipOrderEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderToOrderActionTest {

  @Test
  public void exampleTest() {
    ShipOrderToOrderActionTestKit testKit = ShipOrderToOrderActionTestKit.of(ShipOrderToOrderAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onOrderShippedTest() {
    ShipOrderToOrderActionTestKit testKit = ShipOrderToOrderActionTestKit.of(ShipOrderToOrderAction::new);
    // ActionResult<Empty> result = testKit.onOrderShipped(ShipOrderEntity.OrderShipped.newBuilder()...build());
  }

  @Test
  public void onOrderSkuShippedTest() {
    ShipOrderToOrderActionTestKit testKit = ShipOrderToOrderActionTestKit.of(ShipOrderToOrderAction::new);
    // ActionResult<Empty> result = testKit.onOrderSkuShipped(ShipOrderEntity.OrderSkuShipped.newBuilder()...build());
  }

  @Test
  public void onOrderItemShippedTest() {
    ShipOrderToOrderActionTestKit testKit = ShipOrderToOrderActionTestKit.of(ShipOrderToOrderAction::new);
    // ActionResult<Empty> result = testKit.onOrderItemShipped(ShipOrderEntity.OrderItemShipped.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShipOrderToOrderActionTestKit testKit = ShipOrderToOrderActionTestKit.of(ShipOrderToOrderAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
