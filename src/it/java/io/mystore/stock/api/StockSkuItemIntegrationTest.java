package io.mystore.stock.api;

import com.akkaserverless.javasdk.testkit.junit.AkkaServerlessTestKitResource;
import com.google.protobuf.Empty;
import io.mystore.Main;
import io.mystore.stock.entity.StockSkuItemEntity;
import org.junit.ClassRule;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Akka Serverless proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class StockSkuItemIntegrationTest {

  /**
   * The test kit starts both the service container and the Akka Serverless proxy.
   */
  @ClassRule
  public static final AkkaServerlessTestKitResource testKit =
    new AkkaServerlessTestKitResource(Main.createAkkaServerless());

  /**
   * Use the generated gRPC client to call the service through the Akka Serverless proxy.
   */
  private final StockSkuItemService client;

  public StockSkuItemIntegrationTest() {
    client = testKit.getGrpcClient(StockSkuItemService.class);
  }

  @Test
  public void createStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.createStockSkuItem(StockSkuItemApi.CreateStockSkuItemCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void joinStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.joinStockSkuItem(StockSkuItemApi.JoinStockSkuItemCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void releaseStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.releaseStockSkuItem(StockSkuItemApi.ReleaseStockSkuItemCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void getStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.getStockSkuItem(StockSkuItemApi.GetStockSKuItemRequest.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }
}
