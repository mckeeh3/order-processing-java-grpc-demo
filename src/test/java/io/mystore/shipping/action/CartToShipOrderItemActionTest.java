package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.cart.entity.CartEntity;
import io.mystore.shipping.action.CartToShipOrderItemAction;
import io.mystore.shipping.action.CartToShipOrderItemActionTestKit;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToShipOrderItemActionTest {

  @Test
  public void exampleTest() {
    CartToShipOrderItemActionTestKit testKit = CartToShipOrderItemActionTestKit.of(CartToShipOrderItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onCartCheckedOutTest() {
    CartToShipOrderItemActionTestKit testKit = CartToShipOrderItemActionTestKit.of(CartToShipOrderItemAction::new);
    // ActionResult<Empty> result = testKit.onCartCheckedOut(CartEntity.CartCheckedOut.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    CartToShipOrderItemActionTestKit testKit = CartToShipOrderItemActionTestKit.of(CartToShipOrderItemAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
