package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.ShipOrderItemToShipOrderAction;
import io.mystore.shipping.action.ShipOrderItemToShipOrderActionTestKit;
import io.mystore.shipping.entity.ShipOrderItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItemToShipOrderActionTest {

  @Test
  public void exampleTest() {
    ShipOrderItemToShipOrderActionTestKit testKit = ShipOrderItemToShipOrderActionTestKit.of(ShipOrderItemToShipOrderAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onSkuItemAddedToOrderTest() {
    ShipOrderItemToShipOrderActionTestKit testKit = ShipOrderItemToShipOrderActionTestKit.of(ShipOrderItemToShipOrderAction::new);
    // ActionResult<Empty> result = testKit.onSkuItemAddedToOrder(ShipOrderItemEntity.SkuItemAddedToOrder.newBuilder()...build());
  }

}
