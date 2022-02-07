package io.mystore.shipping2.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping2.action.OrderSkuItemToShippingAction;
import io.mystore.shipping2.action.OrderSkuItemToShippingActionTestKit;
import io.mystore.shipping2.entity.OrderSkuItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderSkuItemToShippingActionTest {

  @Test
  public void exampleTest() {
    OrderSkuItemToShippingActionTestKit testKit = OrderSkuItemToShippingActionTestKit.of(OrderSkuItemToShippingAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onJoinedToStockSkuItemTest() {
    OrderSkuItemToShippingActionTestKit testKit = OrderSkuItemToShippingActionTestKit.of(OrderSkuItemToShippingAction::new);
    // ActionResult<Empty> result = testKit.onJoinedToStockSkuItem(OrderSkuItemEntity.JoinedToStockSkuItem.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    OrderSkuItemToShippingActionTestKit testKit = OrderSkuItemToShippingActionTestKit.of(OrderSkuItemToShippingAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
