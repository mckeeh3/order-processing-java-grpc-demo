package io.mystore.stock.action;

import akka.stream.javadsl.Source;
import kalix.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.stock.action.StockOrderToStockSkuItemAction;
import io.mystore.stock.action.StockOrderToStockSkuItemActionTestKit;
import io.mystore.stock.entity.StockOrderEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockOrderToStockSkuItemActionTest {

  @Test
  public void exampleTest() {
    StockOrderToStockSkuItemActionTestKit testKit = StockOrderToStockSkuItemActionTestKit
        .of(StockOrderToStockSkuItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onStockOrderCreatedTest() {
    StockOrderToStockSkuItemActionTestKit testKit = StockOrderToStockSkuItemActionTestKit
        .of(StockOrderToStockSkuItemAction::new);
    // ActionResult<Empty> result =
    // testKit.onStockOrderCreated(StockOrderEntity.StockOrderCreated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    StockOrderToStockSkuItemActionTestKit testKit = StockOrderToStockSkuItemActionTestKit
        .of(StockOrderToStockSkuItemAction::new);
    // ActionResult<Empty> result =
    // testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
