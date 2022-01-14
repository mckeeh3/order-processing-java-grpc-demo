package io.mystore.cart.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.akkaserverless.javasdk.testkit.EventSourcedResult;
import com.google.protobuf.Empty;
import io.mystore.cart.api.CartApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCartTest {

  @Test
  public void exampleTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // use the testkit to execute a command
    // of events emitted, or a final updated state:
    // EventSourcedResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the emitted events
    // ExpectedEvent actualEvent = result.getNextEventOfType(ExpectedEvent.class);
    // assertEquals(expectedEvent, actualEvent)
    // verify the final state after applying the events
    // assertEquals(expectedState, testKit.getState());
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void addItemTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<Empty> result = testKit.addItem(AddLineItem.newBuilder()...build());
  }


  @Test
  public void changeItemTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<Empty> result = testKit.changeItem(ChangeLineItem.newBuilder()...build());
  }


  @Test
  public void removeItemTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<Empty> result = testKit.removeItem(RemoveLineItem.newBuilder()...build());
  }


  @Test
  public void checkoutCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<Empty> result = testKit.checkoutCart(CheckoutShoppingCart.newBuilder()...build());
  }


  @Test
  public void shippedCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<Empty> result = testKit.shippedCart(ShippedShoppingCart.newBuilder()...build());
  }


  @Test
  public void deliveredCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<Empty> result = testKit.deliveredCart(DeliveredShoppingCart.newBuilder()...build());
  }


  @Test
  public void deleteCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<Empty> result = testKit.deleteCart(DeleteShoppingCart.newBuilder()...build());
  }


  @Test
  public void setDatesTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<ShoppingCart> result = testKit.setDates(SetShoppingCartDates.newBuilder()...build());
  }


  @Test
  public void getCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<ShoppingCart> result = testKit.getCart(GetShoppingCart.newBuilder()...build());
  }

}
