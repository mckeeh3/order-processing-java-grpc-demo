package io.shopping.cart.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Empty;
import io.shopping.cart.action.CartToPurchasedProductAction;
import io.shopping.cart.action.CartToPurchasedProductActionTestKit;
import io.shopping.cart.entity.CartEntity;
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
  public void onItemCheckedOutTest() {
    CartToPurchasedProductActionTestKit testKit = CartToPurchasedProductActionTestKit.of(CartToPurchasedProductAction::new);
    // ActionResult<Empty> result = testKit.onItemCheckedOut(CartEntity.ItemCheckedOut.newBuilder()...build());
  }

}
