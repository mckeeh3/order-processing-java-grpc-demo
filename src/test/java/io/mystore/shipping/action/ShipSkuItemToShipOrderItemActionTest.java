package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.ShipSkuItemToShipOrderItemAction;
import io.mystore.shipping.action.ShipSkuItemToShipOrderItemActionTestKit;
import io.mystore.shipping.entity.ShipSkuItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItemToShipOrderItemActionTest {

  @Test
  public void exampleTest() {
    ShipSkuItemToShipOrderItemActionTestKit testKit = ShipSkuItemToShipOrderItemActionTestKit.of(ShipSkuItemToShipOrderItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onSkuItemCreatedTest() {
    ShipSkuItemToShipOrderItemActionTestKit testKit = ShipSkuItemToShipOrderItemActionTestKit.of(ShipSkuItemToShipOrderItemAction::new);
    // ActionResult<Empty> result = testKit.onSkuItemCreated(ShipSkuItemEntity.SkuItemCreated.newBuilder()...build());
  }

  @Test
  public void onShipOrderItemAddedTest() {
    ShipSkuItemToShipOrderItemActionTestKit testKit = ShipSkuItemToShipOrderItemActionTestKit.of(ShipSkuItemToShipOrderItemAction::new);
    // ActionResult<Empty> result = testKit.onShipOrderItemAdded(ShipSkuItemEntity.ShipOrderItemAdded.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShipSkuItemToShipOrderItemActionTestKit testKit = ShipSkuItemToShipOrderItemActionTestKit.of(ShipSkuItemToShipOrderItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
