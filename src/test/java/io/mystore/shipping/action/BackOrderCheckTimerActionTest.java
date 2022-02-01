package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.BackOrderCheckTimerAction;
import io.mystore.shipping.action.BackOrderCheckTimerActionTestKit;
import io.mystore.shipping.entity.BackOrderTimerEntity;
import io.mystore.shipping.entity.ShipOrderItemEntity;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class BackOrderCheckTimerActionTest {

  @Test
  public void exampleTest() {
    BackOrderCheckTimerActionTestKit testKit = BackOrderCheckTimerActionTestKit.of(BackOrderCheckTimerAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onOrderItemBackOrderedTest() {
    BackOrderCheckTimerActionTestKit testKit = BackOrderCheckTimerActionTestKit.of(BackOrderCheckTimerAction::new);
    // ActionResult<Empty> result = testKit.onOrderItemBackOrdered(ShipOrderItemEntity.OrderItemBackOrdered.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    BackOrderCheckTimerActionTestKit testKit = BackOrderCheckTimerActionTestKit.of(BackOrderCheckTimerAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

  @Test
  public void onBackOrderTimePingTest() {
    BackOrderCheckTimerActionTestKit testKit = BackOrderCheckTimerActionTestKit.of(BackOrderCheckTimerAction::new);
    // ActionResult<Empty> result = testKit.onBackOrderTimePing(BackOrderTimerEntity.BackOrderTimerState.newBuilder()...build());
  }

}
