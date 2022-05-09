package io.mystore.order.action;

import akka.stream.javadsl.Source;
import kalix.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.order.action.OrderToOrderItemAction;
import io.mystore.order.action.OrderToOrderItemActionTestKit;
import io.mystore.order.entity.OrderEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class OrderToOrderItemActionTest {

  @Test
  public void exampleTest() {
    OrderToOrderItemActionTestKit testKit = OrderToOrderItemActionTestKit.of(OrderToOrderItemAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onOrderCreatedTest() {
    OrderToOrderItemActionTestKit testKit = OrderToOrderItemActionTestKit.of(OrderToOrderItemAction::new);
    // ActionResult<Empty> result =
    // testKit.onOrderCreated(OrderEntity.OrderCreated.newBuilder()...build());
  }

  @Test
  public void onOrderItemShippedTest() {
    OrderToOrderItemActionTestKit testKit = OrderToOrderItemActionTestKit.of(OrderToOrderItemAction::new);
    // ActionResult<Empty> result =
    // testKit.onOrderItemShipped(OrderEntity.OrderItemShipped.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    OrderToOrderItemActionTestKit testKit = OrderToOrderItemActionTestKit.of(OrderToOrderItemAction::new);
    // ActionResult<Empty> result =
    // testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
