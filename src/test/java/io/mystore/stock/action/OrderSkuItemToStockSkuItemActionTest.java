package io.mystore.stock.action;

import akka.stream.javadsl.Source;
import kalix.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.entity.OrderSkuItemEntity;
import io.mystore.stock.action.OrderSkuItemToStockSkuItemAction;
import io.mystore.stock.action.OrderSkuItemToStockSkuItemActionTestKit;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemToStockSkuItemActionTest {

  @Test
  public void exampleTest() {
    OrderSkuItemToStockSkuItemActionTestKit testKit = OrderSkuItemToStockSkuItemActionTestKit
        .of(OrderSkuItemToStockSkuItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onStockSkuItemRequiredTest() {
    OrderSkuItemToStockSkuItemActionTestKit testKit = OrderSkuItemToStockSkuItemActionTestKit
        .of(OrderSkuItemToStockSkuItemAction::new);
    // ActionResult<Empty> result =
    // testKit.onStockSkuItemRequired(OrderSkuItemEntity.StockSkuItemRequired.newBuilder()...build());
  }

  @Test
  public void onStockSkuItemReleasedTest() {
    OrderSkuItemToStockSkuItemActionTestKit testKit = OrderSkuItemToStockSkuItemActionTestKit
        .of(OrderSkuItemToStockSkuItemAction::new);
    // ActionResult<Empty> result =
    // testKit.onStockSkuItemReleased(OrderSkuItemEntity.ReleasedFromOrderSkuItem.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    OrderSkuItemToStockSkuItemActionTestKit testKit = OrderSkuItemToStockSkuItemActionTestKit
        .of(OrderSkuItemToStockSkuItemAction::new);
    // ActionResult<Empty> result =
    // testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
