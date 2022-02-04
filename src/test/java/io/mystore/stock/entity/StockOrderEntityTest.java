package io.mystore.stock.entity;

import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntity;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.akkaserverless.javasdk.testkit.EventSourcedResult;
import com.google.protobuf.Empty;
import io.mystore.stock.api.StockOrderApi;
import io.mystore.stock.api.StockSkuItemEvents;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockOrderEntityTest {

  @Test
  public void exampleTest() {
    StockOrderEntityTestKit testKit = StockOrderEntityTestKit.of(StockOrderEntity::new);
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
  public void createStockOrderTest() {
    StockOrderEntityTestKit testKit = StockOrderEntityTestKit.of(StockOrderEntity::new);
    // EventSourcedResult<Empty> result = testKit.createStockOrder(CreateStockOrderCommand.newBuilder()...build());
  }


  @Test
  public void joinStockSkuItemTest() {
    StockOrderEntityTestKit testKit = StockOrderEntityTestKit.of(StockOrderEntity::new);
    // EventSourcedResult<Empty> result = testKit.joinStockSkuItem(JoinStockSkuItemCommand.newBuilder()...build());
  }


  @Test
  public void releaseStockSkuItemTest() {
    StockOrderEntityTestKit testKit = StockOrderEntityTestKit.of(StockOrderEntity::new);
    // EventSourcedResult<Empty> result = testKit.releaseStockSkuItem(ReleaseStockSkuItemCommand.newBuilder()...build());
  }


  @Test
  public void getStockOrderTest() {
    StockOrderEntityTestKit testKit = StockOrderEntityTestKit.of(StockOrderEntity::new);
    // EventSourcedResult<StockOrder> result = testKit.getStockOrder(GetStockOrderRequest.newBuilder()...build());
  }

}
