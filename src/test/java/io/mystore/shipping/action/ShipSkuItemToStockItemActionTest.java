package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.ShipSkuItemToStockItemAction;
import io.mystore.shipping.action.ShipSkuItemToStockItemActionTestKit;
import io.mystore.shipping.entity.ShipSkuItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItemToStockItemActionTest {

  @Test
  public void exampleTest() {
    ShipSkuItemToStockItemActionTestKit testKit = ShipSkuItemToStockItemActionTestKit.of(ShipSkuItemToStockItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onShipOrderItemAddedTest() {
    ShipSkuItemToStockItemActionTestKit testKit = ShipSkuItemToStockItemActionTestKit.of(ShipSkuItemToStockItemAction::new);
    // ActionResult<Empty> result = testKit.onShipOrderItemAdded(ShipSkuItemEntity.ShipOrderItemAdded.newBuilder()...build());
  }

  @Test
  public void onReleasedSkuItemFromOrderTest() {
    ShipSkuItemToStockItemActionTestKit testKit = ShipSkuItemToStockItemActionTestKit.of(ShipSkuItemToStockItemAction::new);
    // ActionResult<Empty> result = testKit.onReleasedSkuItemFromOrder(ShipSkuItemEntity.ReleasedSkuItemFromOrder.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShipSkuItemToStockItemActionTestKit testKit = ShipSkuItemToStockItemActionTestKit.of(ShipSkuItemToStockItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
