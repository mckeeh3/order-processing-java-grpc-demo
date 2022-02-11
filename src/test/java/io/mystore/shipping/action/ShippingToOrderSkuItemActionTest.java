package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.shipping.action.ShippingToOrderSkuItemAction;
import io.mystore.shipping.entity.ShippingEntity;
import io.mystore.shipping.action.ShippingToOrderSkuItemActionTestKit;

import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingToOrderSkuItemActionTest {

  @Test
  public void exampleTest() {
    ShippingToOrderSkuItemActionTestKit testKit = ShippingToOrderSkuItemActionTestKit.of(ShippingToOrderSkuItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onOrderCreatedTest() {
    ShippingToOrderSkuItemActionTestKit testKit = ShippingToOrderSkuItemActionTestKit.of(ShippingToOrderSkuItemAction::new);
    // ActionResult<Empty> result = testKit.onOrderCreated(ShippingEntity.OrderCreated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    ShippingToOrderSkuItemActionTestKit testKit = ShippingToOrderSkuItemActionTestKit.of(ShippingToOrderSkuItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
