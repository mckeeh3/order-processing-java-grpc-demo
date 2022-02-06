package io.mystore.shipping2.api;

import com.akkaserverless.javasdk.testkit.junit.AkkaServerlessTestKitResource;
import com.google.protobuf.Empty;
import io.mystore.Main;
import io.mystore.shipping2.entity.OrderSkuItemEntity;
import org.junit.ClassRule;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Akka Serverless proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class OrderSkuItemIntegrationTest {

  /**
   * The test kit starts both the service container and the Akka Serverless proxy.
   */
  @ClassRule
  public static final AkkaServerlessTestKitResource testKit =
    new AkkaServerlessTestKitResource(Main.createAkkaServerless());

  /**
   * Use the generated gRPC client to call the service through the Akka Serverless proxy.
   */
  private final OrderSkuItemService client;

  public OrderSkuItemIntegrationTest() {
    client = testKit.getGrpcClient(OrderSkuItemService.class);
  }

  @Test
  public void createOrderSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.createOrderSkuItem(OrderSkuItemApi.CreateOrderSkuItemCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void joinToStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.joinToStockSkuItem(OrderSkuItemApi.JoinToStockSkuItemCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void backOrderOrderSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.backOrderOrderSkuItem(OrderSkuItemApi.BackOrderOrderSkuItemCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void getOrderSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.getOrderSkuItem(OrderSkuItemApi.GetOrderSkuItemRequest.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }
}
