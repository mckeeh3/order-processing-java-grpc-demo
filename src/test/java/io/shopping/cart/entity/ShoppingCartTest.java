package io.shopping.cart.entity;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.google.protobuf.Timestamp;

import org.junit.Test;

import io.shopping.cart.CartApi;
import io.shopping.cart.CartApi.SetShoppingCartDates;
import io.shopping.cart.entity.CartEntity.CartState;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCartTest {

  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

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
  public void getCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    // EventSourcedResult<ShoppingCart> result = testKit.getCart(GetShoppingCart.newBuilder()...build());
  }

  @Test
  public void setDatesValidationFail() {
    var state = CartEntity.CartState.getDefaultInstance();

    var command = CartApi.SetShoppingCartDates
        .newBuilder()
        .setCartId("cartId")
        .setCheckedOutUtc("2019-01-01T00:00:00.000-0500")
        .setShippedUtc("2019-01-01T00:00:00.000-0500")
        .setDeliveredUtc("2019-01-01T00:00:00.000-0500")
        .setDeletedUtc("2019-01-01T00:00:00.000Z")
        .build();

    var resp = reject(state, command).orElseGet(() -> handle(state, command));
    assertEquals("Invalid deleted date", resp);
  }

  @Test
  public void setDatesValidationPass() {
    var state = CartEntity.CartState.getDefaultInstance();

    var command = CartApi.SetShoppingCartDates
        .newBuilder()
        .setCartId("cartId")
        .setCheckedOutUtc("")
        .setShippedUtc("2019-01-01T00:00:00.000-0500")
        .setDeliveredUtc("2019-01-01T00:00:00.000-0500")
        .setDeletedUtc("2019-01-01T00:00:00.000-0500")
        .build();

    var resp = reject(state, command).orElseGet(() -> handle(state, command));
    assertEquals("handled", resp);
  }

  private String handle(CartState state, SetShoppingCartDates command) {
    return "handled";
  }

  private Optional<String> reject(CartEntity.CartState state, CartApi.SetShoppingCartDates command) {
    return reject(command.getCheckedOutUtc(), "checked out")
        .or(() -> reject(command.getShippedUtc(), "shipped"))
        .or(() -> reject(command.getDeliveredUtc(), "delivered"))
        .or(() -> reject(command.getDeletedUtc(), "deleted"));
  }

  private Optional<String> reject(String utc, String name) {
    if (utc.trim().isEmpty()) {
      return Optional.empty();
    }
    try {
      dateFormat.parse(utc);
      return Optional.empty();
    } catch (ParseException e) {
      return Optional.of(String.format("Invalid %s date", name));
    }
  }

  @Test
  public void TimestampTestZeroTime() {
    var time = Timestamp.newBuilder()
        .setSeconds(0)
        .setNanos(0)
        .build();

    var utc = toUtc(time);
    assertEquals("", utc);
  }

  @Test
  public void TimestampTestNonZeroTime() {
    var time = Timestamp.newBuilder()
        .setSeconds(System.currentTimeMillis() / 1000)
        .setNanos(0)
        .build();

    var utc = toUtc(time);
    assertNotEquals("", utc);
  }

  private static String toUtc(Timestamp timestamp) {
    if (timestamp.getSeconds() == 0) {
      return "";
    }
    return dateFormat.format(new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000));
  }
}
