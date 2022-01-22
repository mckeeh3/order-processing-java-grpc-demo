package io.mystore.shipping.action;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.google.protobuf.Empty;
import io.mystore.shipping.action.StockFrontendService;
import io.mystore.shipping.action.StockItemFrontendAction;
import io.mystore.shipping.action.StockItemFrontendActionTestKit;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StockItemFrontendActionTest {

  @Test
  public void exampleTest() {
    StockItemFrontendActionTestKit testKit = StockItemFrontendActionTestKit.of(StockItemFrontendAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void createStockItemsTest() {
    StockItemFrontendActionTestKit testKit = StockItemFrontendActionTestKit.of(StockItemFrontendAction::new);
    // ActionResult<Empty> result = testKit.createStockItems(StockFrontendService.CreateStockRequest.newBuilder()...build());
  }

}
