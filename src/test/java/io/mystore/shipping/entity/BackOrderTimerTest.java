package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.testkit.ValueEntityResult;
import com.akkaserverless.javasdk.valueentity.ValueEntity;
import com.google.protobuf.Empty;
import io.mystore.shipping.api.BackOrderTimerApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class BackOrderTimerTest {

  @Test
  public void exampleTest() {
    BackOrderTimerTestKit testKit = BackOrderTimerTestKit.of(BackOrderTimer::new);
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
  public void createBackOrderTimerTest() {
    BackOrderTimerTestKit testKit = BackOrderTimerTestKit.of(BackOrderTimer::new);
    // ValueEntityResult<Empty> result = testKit.createBackOrderTimer(CreateBackOrderTimerCommand.newBuilder()...build());
  }


  @Test
  public void pingBackOrderTimerTest() {
    BackOrderTimerTestKit testKit = BackOrderTimerTestKit.of(BackOrderTimer::new);
    // ValueEntityResult<Empty> result = testKit.pingBackOrderTimer(PingBackOrderTimerCommand.newBuilder()...build());
  }


  @Test
  public void getBackOrderTimerTest() {
    BackOrderTimerTestKit testKit = BackOrderTimerTestKit.of(BackOrderTimer::new);
    // ValueEntityResult<GetBackOrderTimerResponse> result = testKit.getBackOrderTimer(GetBackOrderTimerRequest.newBuilder()...build());
  }

}