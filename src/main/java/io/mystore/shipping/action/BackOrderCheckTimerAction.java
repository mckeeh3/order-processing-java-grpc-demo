package io.mystore.shipping.action;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.BackOrderTimerApi;
import io.mystore.shipping.api.ShipSkuItemApi;
import io.mystore.shipping.entity.BackOrderTimerEntity;
import io.mystore.shipping.entity.ShipOrderItemEntity;
import io.mystore.shipping.entity.ShipSkuItemEntity;
import io.mystore.shipping.view.AvailableShipSkuItemsModel;
import io.mystore.shipping.view.AvailableShipSkuItemsModel.GetAvailableShipSkuItemsResponse;
import io.mystore.shipping.view.BackOrderedShipOrderItemsBySkuModel;
import io.mystore.shipping.view.BackOrderedShipOrderItemsBySkuModel.GetBackOrderedOrderItemsBySkuResponse;
import io.mystore.shipping.view.ShipOrderItemModel;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class BackOrderCheckTimerAction extends AbstractBackOrderCheckTimerAction {
  static final Random random = new Random();
  static final Logger log = LoggerFactory.getLogger(BackOrderCheckTimerAction.class);

  public BackOrderCheckTimerAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onOrderItemBackOrdered(ShipOrderItemEntity.OrderItemBackOrdered orderItemBackOrdered) {
    log.info("onOrderItemBackOrdered: {}", orderItemBackOrdered);
    return effects().forward(components().backOrderTimer().createBackOrderTimer(toBackOrderTimer(orderItemBackOrdered)));
  }

  @Override
  public Effect<Empty> ignoreOtherOrderItemEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> onReleasedFromOrderItem(ShipSkuItemEntity.ReleasedFromOrderItem releasedFromOrderItem) {
    log.info("onReleasedFromOrderItem: {}", releasedFromOrderItem);
    return effects().forward(components().backOrderTimer().createBackOrderTimer(toBackOrderTimer(releasedFromOrderItem)));
  }

  @Override
  public Effect<Empty> ignoreOtherSkuItemEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> onBackOrderTimerPing(BackOrderTimerEntity.BackOrderTimerState backOrderTimerState) {
    log.info("onBackOrderTimerPing: {}", backOrderTimerState);

    return effects().asyncReply(queryAvailableSkuItems(backOrderTimerState.getSkuId()));
  }

  private BackOrderTimerApi.CreateBackOrderTimerCommand toBackOrderTimer(ShipOrderItemEntity.OrderItemBackOrdered orderItemBackOrdered) {
    return BackOrderTimerApi.CreateBackOrderTimerCommand
        .newBuilder()
        .setSkuId(orderItemBackOrdered.getSkuId())
        .build();
  }

  private BackOrderTimerApi.CreateBackOrderTimerCommand toBackOrderTimer(ShipSkuItemEntity.ReleasedFromOrderItem releasedFromOrderItem) {
    return BackOrderTimerApi.CreateBackOrderTimerCommand
        .newBuilder()
        .setSkuId(releasedFromOrderItem.getSkuId())
        .build();
  }

  private CompletionStage<Empty> queryAvailableSkuItems(String skuId) {
    return components().availableShipSkuItemsView().getAvailableShipSkuItems(
        AvailableShipSkuItemsModel.GetAvailableShipSkuItemsRequest
            .newBuilder()
            .setSkuId(skuId)
            .build())
        .execute()
        .thenCompose(response -> queryBackOrderedOrderItems(skuId, response));
  }

  private CompletionStage<Empty> queryBackOrderedOrderItems(String skuId, GetAvailableShipSkuItemsResponse availableSkuItems) {
    return components().backOrderedShipOrderItemsBySkuView().getBackOrderedShipOrderItemsBySku(
        BackOrderedShipOrderItemsBySkuModel.GetBackOrderedOrderItemsBySkuRequest
            .newBuilder()
            .setSkuId(skuId)
            .build())
        .execute()
        .thenCompose(response -> joinBackOrderedItemsToSkuItems(skuId, availableSkuItems, response));
  }

  private CompletionStage<Empty> joinBackOrderedItemsToSkuItems(String skuId, GetAvailableShipSkuItemsResponse availableSkuItems, GetBackOrderedOrderItemsBySkuResponse backOrderedOrderItems) {
    var countAvailableSkuItems = availableSkuItems.getShipSkuItemsCount();
    var countBackOrderedOrderItems = backOrderedOrderItems.getShipOrderItemsCount();

    log.info("skuId: {}, available SKU items: {}, back ordered order items: {}", skuId, countAvailableSkuItems, countBackOrderedOrderItems);

    var results = IntStream.range(0, Math.min(countAvailableSkuItems, countBackOrderedOrderItems))
        .mapToObj(i -> new SkuItemOrderItem(availableSkuItems.getShipSkuItems(i), backOrderedOrderItems.getShipOrderItems(i)))
        .map(skuItemOrderItem -> joinBackOrderedToSkuItemX(skuItemOrderItem.skuItem, skuItemOrderItem.orderItem))
        .collect(Collectors.toList());

    return CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .completeOnTimeout(null, 2, TimeUnit.SECONDS) // todo: make configurable
        .thenCompose(v -> pingBackOrderTimer(skuId));
  }

  private CompletionStage<Empty> joinBackOrderedToSkuItemX(AvailableShipSkuItemsModel.ShipSkuItem shipSkuItem, ShipOrderItemModel.ShipOrderItem shipOrderItem) {
    log.info("join SKU item: {}\nback ordered order item: {}", shipSkuItem, shipOrderItem);

    return components().shipSkuItem().joinToOrderItem(
        ShipSkuItemApi.JoinToOrderItemCommand
            .newBuilder()
            .setOrderId(shipOrderItem.getOrderId())
            .setOrderItemId(shipOrderItem.getOrderItemId())
            .setSkuId(shipSkuItem.getSkuId())
            .setSkuItemId(shipSkuItem.getSkuItemId())
            .build())
        .execute();
  }

  private CompletionStage<Empty> pingBackOrderTimer(String skuId) {
    return components().backOrderTimer().pingBackOrderTimer(
        BackOrderTimerApi.PingBackOrderTimerCommand
            .newBuilder()
            .setSkuId(skuId)
            .build())
        .execute();
  }

  static class SkuItemOrderItem {
    final AvailableShipSkuItemsModel.ShipSkuItem skuItem;
    final ShipOrderItemModel.ShipOrderItem orderItem;

    public SkuItemOrderItem(AvailableShipSkuItemsModel.ShipSkuItem availableSkuItem, ShipOrderItemModel.ShipOrderItem backOrderedOrderItem) {
      this.skuItem = availableSkuItem;
      this.orderItem = backOrderedOrderItem;
    }
  }
}
