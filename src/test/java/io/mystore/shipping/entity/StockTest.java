package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.akkaserverless.javasdk.testkit.EventSourcedResult;
import com.google.protobuf.Empty;
import io.mystore.shipping.api.StockApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockTest {

  @Test
  public void exampleTest() {
    StockTestKit testKit = StockTestKit.of(Stock::new);
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
  public void createTest() {
    StockTestKit testKit = StockTestKit.of(Stock::new);
    // EventSourcedResult<Empty> result = testKit.create(CreateStockItem.newBuilder()...build());
  }


  @Test
  public void shipTest() {
    StockTestKit testKit = StockTestKit.of(Stock::new);
    // EventSourcedResult<Empty> result = testKit.ship(ShipStockItem.newBuilder()...build());
  }


  @Test
  public void releaseTest() {
    StockTestKit testKit = StockTestKit.of(Stock::new);
    // EventSourcedResult<Empty> result = testKit.release(ReleaseStockItem.newBuilder()...build());
  }


  @Test
  public void getTest() {
    StockTestKit testKit = StockTestKit.of(Stock::new);
    // EventSourcedResult<Empty> result = testKit.get(GetStockItem.newBuilder()...build());
  }

}
