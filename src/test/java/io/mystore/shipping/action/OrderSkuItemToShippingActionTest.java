package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import kalix.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.action.OrderSkuItemToShippingAction;
import io.mystore.shipping.entity.OrderSkuItemEntity;
import io.mystore.shipping.action.OrderSkuItemToShippingActionTestKit;

import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemToShippingActionTest {

  @Test
  public void exampleTest() {
    OrderSkuItemToShippingActionTestKit testKit = OrderSkuItemToShippingActionTestKit
        .of(OrderSkuItemToShippingAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onJoinedToStockSkuItemTest() {
    OrderSkuItemToShippingActionTestKit testKit = OrderSkuItemToShippingActionTestKit
        .of(OrderSkuItemToShippingAction::new);
    // ActionResult<Empty> result =
    // testKit.onJoinedToStockSkuItem(OrderSkuItemEntity.JoinedToStockSkuItem.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    OrderSkuItemToShippingActionTestKit testKit = OrderSkuItemToShippingActionTestKit
        .of(OrderSkuItemToShippingAction::new);
    // ActionResult<Empty> result =
    // testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
