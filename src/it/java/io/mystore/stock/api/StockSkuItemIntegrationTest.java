package io.mystore.stock.api;

import com.google.protobuf.Empty;
import io.mystore.Main;
import io.mystore.stock.entity.StockSkuItemEntity;
import kalix.javasdk.testkit.junit.KalixTestKitResource;
import org.junit.ClassRule;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Kalix proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class StockSkuItemIntegrationTest {

  /**
   * The test kit starts both the service container and the Kalix proxy.
   */
  @ClassRule
  public static final KalixTestKitResource testKit =
    new KalixTestKitResource(Main.createKalix());

  /**
   * Use the generated gRPC client to call the service through the Kalix proxy.
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
  public void orderRequestsJoinToStockOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.orderRequestsJoinToStock(StockSkuItemApi.OrderRequestsJoinToStockCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void orderRequestsJoinToStockRejectedOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.orderRequestsJoinToStockRejected(StockSkuItemApi.OrderRequestsJoinToStockRejectedCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void stockRequestedJoinToOrderAcceptedOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.stockRequestedJoinToOrderAccepted(StockSkuItemApi.StockRequestedJoinToOrderAcceptedCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void stockRequestedJoinToOrderRejectedOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.stockRequestedJoinToOrderRejected(StockSkuItemApi.StockRequestedJoinToOrderRejectedCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void getStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.getStockSkuItem(StockSkuItemApi.GetStockSKuItemRequest.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }
}
