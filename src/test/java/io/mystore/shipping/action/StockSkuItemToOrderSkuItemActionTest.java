package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.action.StockSkuItemToOrderSkuItemAction;
import io.mystore.shipping.action.StockSkuItemToOrderSkuItemActionTestKit;
import io.mystore.stock.entity.StockSkuItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockSkuItemToOrderSkuItemActionTest {

  @Test
  public void exampleTest() {
    StockSkuItemToOrderSkuItemActionTestKit testKit = StockSkuItemToOrderSkuItemActionTestKit.of(StockSkuItemToOrderSkuItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onStockSkuItemCreatedTest() {
    StockSkuItemToOrderSkuItemActionTestKit testKit = StockSkuItemToOrderSkuItemActionTestKit.of(StockSkuItemToOrderSkuItemAction::new);
    // ActionResult<Empty> result =
    // testKit.onStockSkuItemCreated(StockSkuItemEntity.StockSkuItemCreated.newBuilder()...build());
  }

  @Test
  public void onJoinedToStockSkuItemTest() {
    StockSkuItemToOrderSkuItemActionTestKit testKit = StockSkuItemToOrderSkuItemActionTestKit.of(StockSkuItemToOrderSkuItemAction::new);
    // ActionResult<Empty> result =
    // testKit.onJoinedToStockSkuItem(StockSkuItemEntity.JoinedToStockSkuItem.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    StockSkuItemToOrderSkuItemActionTestKit testKit = StockSkuItemToOrderSkuItemActionTestKit.of(StockSkuItemToOrderSkuItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
