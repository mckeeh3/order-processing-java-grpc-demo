package io.mystore.shipping.action;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.ShipOrderItemApi;
import io.mystore.shipping.api.ShipSkuItemApi;
import io.mystore.shipping.entity.ShipSkuItemEntity;
import io.mystore.shipping.view.BackOrderedShipOrderItemsBySkuModel;
import io.mystore.shipping.view.ShipOrderItemModel;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipSkuItemToShipOrderItemAction extends AbstractShipSkuItemToShipOrderItemAction {
  static final Random random = new Random();
  static final Logger log = LoggerFactory.getLogger(ShipSkuItemToShipOrderItemAction.class);

  public ShipSkuItemToShipOrderItemAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onSkuItemCreated(ShipSkuItemEntity.SkuItemCreated skuItemCreated) {
    log.info("onSkuItemCreated: {}", skuItemCreated);
    return notifySkuItemOfBackOrderedOrderItem(skuItemCreated.getSkuId(), skuItemCreated.getSkuItemId());
  }

  @Override
  public Effect<Empty> onJoinedToOrderItem(ShipSkuItemEntity.JoinedToOrderItem joinedToOrderItem) {
    return effects().forward(components().shipOrderItem().joinToSkuItem(toJoinToSkuItem(joinedToOrderItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  private Effect<Empty> notifySkuItemOfBackOrderedOrderItem(String skuId, String skuItemId) {
    return effects().asyncReply(
        components().backOrderedShipOrderItemsBySkuView().getBackOrderedShipOrderItemsBySku(
            BackOrderedShipOrderItemsBySkuModel.GetBackOrderedOrderItemsBySkuRequest
                .newBuilder()
                .setSkuId(skuId)
                .build())
            .execute()
            .thenCompose(response -> joinBackOrderedToSkuItem(skuId, skuItemId, response)));
  }

  private CompletionStage<Empty> joinBackOrderedToSkuItem(String skuId, String skuItemId, BackOrderedShipOrderItemsBySkuModel.GetBackOrderedOrderItemsBySkuResponse response) {
    var count = response.getShipOrderItemsCount();
    log.info("joinBackOrderedToSkuItem: skuId: {}, count: {}", skuId, count);

    if (count > 0) {
      return joinBackOrderedToSkuItem(skuItemId, response.getShipOrderItemsList().get(random.nextInt(count)));
    } else {
      return CompletableFuture.completedFuture(Empty.getDefaultInstance());
    }
  }

  private CompletionStage<Empty> joinBackOrderedToSkuItem(String skuItemId, ShipOrderItemModel.ShipOrderItem shipOrderItem) {
    return components().shipSkuItem().joinToOrderItem(
        ShipSkuItemApi.JoinToOrderItemCommand
            .newBuilder()
            .setOrderId(shipOrderItem.getOrderId())
            .setOrderItemId(shipOrderItem.getOrderItemId())
            .setSkuId(shipOrderItem.getSkuId())
            .setSkuItemId(skuItemId)
            .build())
        .execute();
  }

  static ShipOrderItemApi.JoinToSkuItemCommand toJoinToSkuItem(ShipSkuItemEntity.JoinedToOrderItem joinedToOrderItem) {
    return ShipOrderItemApi.JoinToSkuItemCommand
        .newBuilder()
        .setOrderId(joinedToOrderItem.getOrderId())
        .setOrderItemId(joinedToOrderItem.getOrderItemId())
        .setSkuId(joinedToOrderItem.getSkuId())
        .setSkuItemId(joinedToOrderItem.getSkuItemId())
        .setShippedUtc(joinedToOrderItem.getShippedUtc())
        .build();
  }
}
