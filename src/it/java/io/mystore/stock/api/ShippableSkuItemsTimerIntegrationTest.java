package io.mystore.stock.api;

import com.akkaserverless.javasdk.testkit.junit.AkkaServerlessTestKitResource;
import com.google.protobuf.Empty;
import io.mystore.Main;
import io.mystore.stock.entity.ShippableSkuItemsTimerEntity;
import org.junit.ClassRule;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Akka Serverless proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class ShippableSkuItemsTimerIntegrationTest {

  /**
   * The test kit starts both the service container and the Akka Serverless proxy.
   */
  @ClassRule
  public static final AkkaServerlessTestKitResource testKit =
    new AkkaServerlessTestKitResource(Main.createAkkaServerless());

  /**
   * Use the generated gRPC client to call the service through the Akka Serverless proxy.
   */
  private final ShippableSkuItemsTimerService client;

  public ShippableSkuItemsTimerIntegrationTest() {
    client = testKit.getGrpcClient(ShippableSkuItemsTimerService.class);
  }

  @Test
  public void createShippableSkuItemsTimerOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.createShippableSkuItemsTimer(ShippableSkuItemsTimerApi.CreateShippableSkuItemsTimerCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void pingShippableSkuItemsTimerOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.pingShippableSkuItemsTimer(ShippableSkuItemsTimerApi.PingShippableSkuItemsTimerCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void getShippableSkuItemsTimerOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.getShippableSkuItemsTimer(ShippableSkuItemsTimerApi.GetShippableSkuItemsTimerRequest.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }
}
