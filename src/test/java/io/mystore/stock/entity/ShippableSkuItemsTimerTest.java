package io.mystore.stock.entity;

import com.akkaserverless.javasdk.testkit.ValueEntityResult;
import com.akkaserverless.javasdk.valueentity.ValueEntity;
import com.google.protobuf.Empty;
import io.mystore.stock.api.ShippableSkuItemsTimerApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippableSkuItemsTimerTest {

  @Test
  public void exampleTest() {
    ShippableSkuItemsTimerTestKit testKit = ShippableSkuItemsTimerTestKit.of(ShippableSkuItemsTimer::new);
    // use the testkit to execute a command
    // of events emitted, or a final updated state:
    // ValueEntityResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
    // verify the final state after the command
    // assertEquals(expectedState, testKit.getState());
  }

  @Test
  public void createShippableSkuItemsTimerTest() {
    ShippableSkuItemsTimerTestKit testKit = ShippableSkuItemsTimerTestKit.of(ShippableSkuItemsTimer::new);
    // ValueEntityResult<Empty> result = testKit.createShippableSkuItemsTimer(CreateShippableSkuItemsTimerCommand.newBuilder()...build());
  }


  @Test
  public void pingShippableSkuItemsTimerTest() {
    ShippableSkuItemsTimerTestKit testKit = ShippableSkuItemsTimerTestKit.of(ShippableSkuItemsTimer::new);
    // ValueEntityResult<Empty> result = testKit.pingShippableSkuItemsTimer(PingShippableSkuItemsTimerCommand.newBuilder()...build());
  }


  @Test
  public void getShippableSkuItemsTimerTest() {
    ShippableSkuItemsTimerTestKit testKit = ShippableSkuItemsTimerTestKit.of(ShippableSkuItemsTimer::new);
    // ValueEntityResult<GetShippableSkuItemsTimerResponse> result = testKit.getShippableSkuItemsTimer(GetShippableSkuItemsTimerRequest.newBuilder()...build());
  }

}
