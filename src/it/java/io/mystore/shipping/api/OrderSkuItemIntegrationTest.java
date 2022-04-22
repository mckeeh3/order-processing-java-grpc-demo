package io.mystore.shipping.api;

import com.google.protobuf.Empty;
import io.mystore.Main;
import io.mystore.shipping.entity.OrderSkuItemEntity;
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
public class OrderSkuItemIntegrationTest {

  /**
   * The test kit starts both the service container and the Kalix proxy.
   */
  @ClassRule
  public static final KalixTestKitResource testKit =
    new KalixTestKitResource(Main.createKalix());

  /**
   * Use the generated gRPC client to call the service through the Kalix proxy.
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
  public void stockRequestsJoinToOrderOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.stockRequestsJoinToOrder(OrderSkuItemApi.StockRequestsJoinToOrderCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void stockRequestsJoinToOrderRejectedOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.stockRequestsJoinToOrderRejected(OrderSkuItemApi.StockRequestsJoinToOrderRejectedCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void orderRequestedJoinToStockAcceptedOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.orderRequestedJoinToStockAccepted(OrderSkuItemApi.OrderRequestedJoinToStockAcceptedCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  public void orderRequestedJoinToStockRejectedOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.orderRequestedJoinToStockRejected(OrderSkuItemApi.OrderRequestedJoinToStockRejectedCommand.newBuilder().build())
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
