package io.mystore.stock.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.entity.OrderSkuItemEntity;
import io.mystore.stock.action.ShippableSkuItemsTimerAction;
import io.mystore.stock.action.ShippableSkuItemsTimerActionTestKit;
import io.mystore.stock.entity.ShippableSkuItemsTimerEntity;
import io.mystore.stock.entity.StockSkuItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippableSkuItemsTimerActionTest {

  @Test
  public void exampleTest() {
    ShippableSkuItemsTimerActionTestKit testKit = ShippableSkuItemsTimerActionTestKit.of(ShippableSkuItemsTimerAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onBackOrderedOrderSkuItemTest() {
    ShippableSkuItemsTimerActionTestKit testKit = ShippableSkuItemsTimerActionTestKit.of(ShippableSkuItemsTimerAction::new);
    // ActionResult<Empty> result = testKit.onBackOrderedOrderSkuItem(OrderSkuItemEntity.BackOrderedOrderSkuItem.newBuilder()...build());
  }

  @Test
  public void ignoreOtherOrderSkuItemEventsTest() {
    ShippableSkuItemsTimerActionTestKit testKit = ShippableSkuItemsTimerActionTestKit.of(ShippableSkuItemsTimerAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherOrderSkuItemEvents(Any.newBuilder()...build());
  }

  @Test
  public void onReleasedFromStockSkuItemTest() {
    ShippableSkuItemsTimerActionTestKit testKit = ShippableSkuItemsTimerActionTestKit.of(ShippableSkuItemsTimerAction::new);
    // ActionResult<Empty> result = testKit.onReleasedFromStockSkuItem(StockSkuItemEntity.ReleasedFromStockSkuItem.newBuilder()...build());
  }

  @Test
  public void ignoreOtherStockSkuItemEventsTest() {
    ShippableSkuItemsTimerActionTestKit testKit = ShippableSkuItemsTimerActionTestKit.of(ShippableSkuItemsTimerAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherStockSkuItemEvents(Any.newBuilder()...build());
  }

  @Test
  public void onPingShippableSkuItemsTimerTest() {
    ShippableSkuItemsTimerActionTestKit testKit = ShippableSkuItemsTimerActionTestKit.of(ShippableSkuItemsTimerAction::new);
    // ActionResult<Empty> result = testKit.onPingShippableSkuItemsTimer(ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState.newBuilder()...build());
  }

}
