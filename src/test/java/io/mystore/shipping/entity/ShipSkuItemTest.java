package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.akkaserverless.javasdk.testkit.EventSourcedResult;
import com.google.protobuf.Empty;
import io.mystore.shipping.api.ShipSkuItemApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItemTest {

  @Test
  public void exampleTest() {
    ShipSkuItemTestKit testKit = ShipSkuItemTestKit.of(ShipSkuItem::new);
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
  public void createItemTest() {
    ShipSkuItemTestKit testKit = ShipSkuItemTestKit.of(ShipSkuItem::new);
    // EventSourcedResult<Empty> result = testKit.createItem(CreateSkuItem.newBuilder()...build());
  }


  @Test
  public void addShipOrderItemTest() {
    ShipSkuItemTestKit testKit = ShipSkuItemTestKit.of(ShipSkuItem::new);
    // EventSourcedResult<Empty> result = testKit.addShipOrderItem(AddShipOrderItemToSku.newBuilder()...build());
  }

}
