package io.mystore.order.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.order.action.ShipOrderToOrderItemAction;
import io.mystore.order.action.ShipOrderToOrderItemActionTestKit;
import io.mystore.shipping.entity.ShipOrderEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderToOrderItemActionTest {

  @Test
  public void exampleTest() {
    ShipOrderToOrderItemActionTestKit testKit = ShipOrderToOrderItemActionTestKit.of(ShipOrderToOrderItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onOrderSkuShippedTest() {
    ShipOrderToOrderItemActionTestKit testKit = ShipOrderToOrderItemActionTestKit.of(ShipOrderToOrderItemAction::new);
    // ActionResult<Empty> result = testKit.onOrderSkuShipped(ShipOrderEntity.OrderSkuShipped.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShipOrderToOrderItemActionTestKit testKit = ShipOrderToOrderItemActionTestKit.of(ShipOrderToOrderItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
