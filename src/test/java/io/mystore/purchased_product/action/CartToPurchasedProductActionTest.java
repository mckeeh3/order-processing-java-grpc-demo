package io.mystore.purchased_product.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.cart.entity.CartEntity;
import io.mystore.purchased_product.action.CartToPurchasedProductAction;
import io.mystore.purchased_product.action.CartToPurchasedProductActionTestKit;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToPurchasedProductActionTest {

  @Test
  public void exampleTest() {
    CartToPurchasedProductActionTestKit testKit = CartToPurchasedProductActionTestKit.of(CartToPurchasedProductAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onCartCheckedOutTest() {
    CartToPurchasedProductActionTestKit testKit = CartToPurchasedProductActionTestKit.of(CartToPurchasedProductAction::new);
    // ActionResult<Empty> result = testKit.onCartCheckedOut(CartEntity.CartCheckedOut.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    CartToPurchasedProductActionTestKit testKit = CartToPurchasedProductActionTestKit.of(CartToPurchasedProductAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
