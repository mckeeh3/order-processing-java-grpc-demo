package io.mystore.stock.action;

import akka.stream.javadsl.Source;
import kalix.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.stock.action.StockSkuItemToStockOrderAction;
import io.mystore.stock.action.StockSkuItemToStockOrderActionTestKit;
import io.mystore.stock.entity.StockSkuItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItemToStockOrderActionTest {

  @Test
  public void exampleTest() {
    StockSkuItemToStockOrderActionTestKit testKit = StockSkuItemToStockOrderActionTestKit
        .of(StockSkuItemToStockOrderAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onJoinedToStockSkuItemTest() {
    StockSkuItemToStockOrderActionTestKit testKit = StockSkuItemToStockOrderActionTestKit
        .of(StockSkuItemToStockOrderAction::new);
    // ActionResult<Empty> result =
    // testKit.onJoinedToStockSkuItem(StockSkuItemEntity.JoinedToStockSkuItem.newBuilder()...build());
  }

  @Test
  public void onReleasedFromStockSkuItemTest() {
    StockSkuItemToStockOrderActionTestKit testKit = StockSkuItemToStockOrderActionTestKit
        .of(StockSkuItemToStockOrderAction::new);
    // ActionResult<Empty> result =
    // testKit.onReleasedFromStockSkuItem(StockSkuItemEntity.ReleasedFromStockSkuItem.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    StockSkuItemToStockOrderActionTestKit testKit = StockSkuItemToStockOrderActionTestKit
        .of(StockSkuItemToStockOrderAction::new);
    // ActionResult<Empty> result =
    // testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
