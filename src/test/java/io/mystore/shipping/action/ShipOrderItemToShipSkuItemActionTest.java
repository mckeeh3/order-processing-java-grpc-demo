package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.ShipOrderItemToShipSkuItemAction;
import io.mystore.shipping.action.ShipOrderItemToShipSkuItemActionTestKit;
import io.mystore.shipping.entity.ShipOrderItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItemToShipSkuItemActionTest {

  @Test
  public void exampleTest() {
    ShipOrderItemToShipSkuItemActionTestKit testKit = ShipOrderItemToShipSkuItemActionTestKit.of(ShipOrderItemToShipSkuItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onShipSkuItemRequiredTest() {
    ShipOrderItemToShipSkuItemActionTestKit testKit = ShipOrderItemToShipSkuItemActionTestKit.of(ShipOrderItemToShipSkuItemAction::new);
    // ActionResult<Empty> result = testKit.onShipSkuItemRequired(ShipOrderItemEntity.ShipSkuItemRequired.newBuilder()...build());
  }

  @Test
  public void onSkuItemReleasedFromOrderTest() {
    ShipOrderItemToShipSkuItemActionTestKit testKit = ShipOrderItemToShipSkuItemActionTestKit.of(ShipOrderItemToShipSkuItemAction::new);
    // ActionResult<Empty> result = testKit.onSkuItemReleasedFromOrder(ShipOrderItemEntity.SkuItemReleasedFromOrder.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShipOrderItemToShipSkuItemActionTestKit testKit = ShipOrderItemToShipSkuItemActionTestKit.of(ShipOrderItemToShipSkuItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
