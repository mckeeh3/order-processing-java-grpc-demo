package io.mystore.cart.entity;

import io.mystore.cart.api.CartApi;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCartTest {
  static final Logger log = LoggerFactory.getLogger(ShoppingCartTest.class);

  @Test
  public void exampleTest() {
    // ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);
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

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    var reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId("cart-1")
        .build());

    assertEquals(1, reply.getReply().getLineItemsCount());
    assertEquals(1, reply.getReply().getLineItems(0).getQuantity());

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId("cart-1")
        .build());

    log.info("reply: {}", reply.getReply());

    assertEquals(1, reply.getReply().getLineItemsCount());
    assertEquals(2, reply.getReply().getLineItems(0).getQuantity());
  }

  @Test
  public void changeItemTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    testKit.changeItem(CartApi.ChangeLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setSkuId("sku-1")
        .setQuantity(12)
        .build());

    var reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId("cart-1")
        .build());

    log.info("reply: {}", reply.getReply());

    assertEquals(1, reply.getReply().getLineItemsCount());
    assertEquals(12, reply.getReply().getLineItems(0).getQuantity());
  }

  @Test
  public void removeItemTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-2")
        .setSkuName("sku name 2")
        .setQuantity(2)
        .build());

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-3")
        .setSkuName("sku name 3")
        .setQuantity(3)
        .build());

    var reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId("cart-1")
        .build());

    log.info("reply: {}", reply.getReply());

    assertEquals(3, reply.getReply().getLineItemsCount());
    assertEquals(1, reply.getReply().getLineItems(0).getQuantity());
    assertEquals(2, reply.getReply().getLineItems(1).getQuantity());
    assertEquals(3, reply.getReply().getLineItems(2).getQuantity());

    testKit.removeItem(CartApi.RemoveLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setSkuId("sku-2")
        .build());

    reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId("cart-1")
        .build());

    log.info("reply: {}", reply.getReply());

    assertEquals(2, reply.getReply().getLineItemsCount());
    assertEquals(1, reply.getReply().getLineItems(0).getQuantity());
    assertEquals(3, reply.getReply().getLineItems(1).getQuantity());
  }

  @Test
  public void checkoutCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);

    var response = testKit.checkoutCart(CartApi.CheckoutShoppingCartCommand
        .newBuilder()
        .setCartId("cart-1")
        .build());

    log.info("{}", response.getError());

    assertTrue(!response.getError().isEmpty()); // cannot checkout empty cart

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    testKit.checkoutCart(CartApi.CheckoutShoppingCartCommand
        .newBuilder()
        .setCartId("cart-1")
        .build());

    var reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId("cart-1")
        .build());

    assertTrue(reply.getReply().getCheckedOutUtc().getSeconds() > 0);
  }

  @Test
  public void deleteCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);

    var cartId = "cart-1";

    var response = testKit.deleteCart(CartApi.DeleteShoppingCartCommand
        .newBuilder()
        .setCartId(cartId)
        .build());

    log.info("{}", response.getError());

    assertTrue(!response.getError().isEmpty()); // cannot delete empty cart

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId(cartId)
        .setCustomerId("customer-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    testKit.checkoutCart(CartApi.CheckoutShoppingCartCommand
        .newBuilder()
        .setCartId(cartId)
        .build());

    response = testKit.deleteCart(CartApi.DeleteShoppingCartCommand
        .newBuilder()
        .setCartId(cartId)
        .build());

    log.info("{}", response.getError());

    assertTrue(!response.getError().isEmpty()); // cannot delete checked out cart

    var reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId(cartId)
        .build());

    assertTrue(reply.getReply().getCheckedOutUtc().getSeconds() > 0);

    testKit = ShoppingCartTestKit.of(ShoppingCart::new);
    cartId = "cart-2";

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId(cartId)
        .setCustomerId("customer-2")
        .setSkuId("sku-2")
        .setSkuName("sku name 2")
        .setQuantity(2)
        .build());

    reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId(cartId)
        .build());

    log.info("reply: {}", reply.getReply());

    reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId(cartId)
        .build());
    testKit.deleteCart(CartApi.DeleteShoppingCartCommand
        .newBuilder()
        .setCartId(cartId)
        .build());

    reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId(cartId)
        .build());

    assertTrue(reply.getReply().getDeletedUtc().getSeconds() > 0);
  }

  @Test
  public void getCartTest() {
    ShoppingCartTestKit testKit = ShoppingCartTestKit.of(ShoppingCart::new);

    testKit.addItem(CartApi.AddLineItemCommand
        .newBuilder()
        .setCartId("cart-1")
        .setCustomerId("customer-1")
        .setSkuId("sku-1")
        .setSkuName("sku name 1")
        .setQuantity(1)
        .build());

    var reply = testKit.getCart(CartApi.GetShoppingCartRequest
        .newBuilder()
        .setCartId("cart-1")
        .build());

    assertEquals("cart-1", reply.getReply().getCartId());
    assertEquals("customer-1", reply.getReply().getCustomerId());
    assertEquals(1, reply.getReply().getLineItemsCount());

    var lineItem = reply.getReply().getLineItems(0);

    assertEquals("sku-1", lineItem.getSkuId());
    assertEquals("sku name 1", lineItem.getSkuName());
    assertEquals(1, lineItem.getQuantity());
  }
}
