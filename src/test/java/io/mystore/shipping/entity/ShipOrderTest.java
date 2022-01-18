package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.testkit.ValueEntityResult;
import com.akkaserverless.javasdk.valueentity.ValueEntity;
import com.google.protobuf.Empty;
import io.mystore.shipping.api.ShipOrderApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderTest {

  @Test
  public void exampleTest() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
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
  public void addShipOrderTest() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // ValueEntityResult<Empty> result = testKit.addShipOrder(ShipOrder.newBuilder()...build());
  }


  @Test
  public void shippedOrderTest() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // ValueEntityResult<Empty> result = testKit.shippedOrder(ShippedOrderRequest.newBuilder()...build());
  }


  @Test
  public void deliveredOrderTest() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // ValueEntityResult<Empty> result = testKit.deliveredOrder(DeliveredOrderRequest.newBuilder()...build());
  }


  @Test
  public void returnedOrderTest() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // ValueEntityResult<Empty> result = testKit.returnedOrder(ReturnedOrderRequest.newBuilder()...build());
  }


  @Test
  public void canceledOrderTest() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // ValueEntityResult<Empty> result = testKit.canceledOrder(CanceledOrderRequest.newBuilder()...build());
  }


  @Test
  public void getShipOrderTest() {
    ShipOrderTestKit testKit = ShipOrderTestKit.of(ShipOrder::new);
    // ValueEntityResult<ShipOrder> result = testKit.getShipOrder(GetShipOrderRequest.newBuilder()...build());
  }

}
