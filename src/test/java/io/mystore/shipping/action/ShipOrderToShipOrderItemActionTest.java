package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.ShipOrderToShipOrderItemAction;
import io.mystore.shipping.action.ShipOrderToShipOrderItemActionTestKit;
import io.mystore.shipping.entity.ShipOrderEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderToShipOrderItemActionTest {

  @Test
  public void exampleTest() {
    ShipOrderToShipOrderItemActionTestKit testKit = ShipOrderToShipOrderItemActionTestKit.of(ShipOrderToShipOrderItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onShipOrderCreatedTest() {
    ShipOrderToShipOrderItemActionTestKit testKit = ShipOrderToShipOrderItemActionTestKit.of(ShipOrderToShipOrderItemAction::new);
    // ActionResult<Empty> result = testKit.onShipOrderCreated(ShipOrderEntity.ShipOrderCreated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShipOrderToShipOrderItemActionTestKit testKit = ShipOrderToShipOrderItemActionTestKit.of(ShipOrderToShipOrderItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
