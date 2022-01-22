package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.StockItemToShipSkuItemAction;
import io.mystore.shipping.action.StockItemToShipSkuItemActionTestKit;
import io.mystore.shipping.entity.StockEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockItemToShipSkuItemActionTest {

  @Test
  public void exampleTest() {
    StockItemToShipSkuItemActionTestKit testKit = StockItemToShipSkuItemActionTestKit.of(StockItemToShipSkuItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onCreateStockItemTest() {
    StockItemToShipSkuItemActionTestKit testKit = StockItemToShipSkuItemActionTestKit.of(StockItemToShipSkuItemAction::new);
    // ActionResult<Empty> result = testKit.onCreateStockItem(StockEntity.StockItemCreated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    StockItemToShipSkuItemActionTestKit testKit = StockItemToShipSkuItemActionTestKit.of(StockItemToShipSkuItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
