package io.mystore.stock.api;

import com.google.protobuf.Empty;
import io.mystore.Main;
import io.mystore.stock.entity.StockOrderEntity;
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
public class StockOrderIntegrationTest {

  /**
   * The test kit starts both the service container and the Kalix proxy.
   */
  @ClassRule
  public static final KalixTestKitResource testKit =
    new KalixTestKitResource(Main.createKalix());

  /**
   * Use the generated gRPC client to call the service through the Kalix proxy.
   */
  private final StockOrderService client;

  public StockOrderIntegrationTest() {
    client = testKit.getGrpcClient(StockOrderService.class);
  }

  @Test
  public void createStockOrderOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.createStockOrder(StockOrderApi.CreateStockOrderCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void shippedStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.shippedStockSkuItem(StockOrderApi.ShippedStockSkuItemToStockOrderCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void releaseStockSkuItemOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.releaseStockSkuItem(StockOrderApi.ReleaseStockSkuItemFromStockOrderCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void getStockOrderOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.getStockOrder(StockOrderApi.GetStockOrderRequest.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }
}
