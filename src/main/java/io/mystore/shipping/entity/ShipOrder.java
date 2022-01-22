package io.mystore.shipping.entity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import io.mystore.shipping.api.ShipOrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** A value entity. */
public class ShipOrder extends AbstractShipOrder {

  public ShipOrder(ValueEntityContext context) {
  }

  @Override
  public ShipOrderEntity.ShipOrderState emptyState() {
    return ShipOrderEntity.ShipOrderState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addShipOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShipOrder command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> shippedOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> deliveredOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.DeliveredOrderRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> returnedOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ReturnedOrderRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<Empty> canceledOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.CanceledOrderRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  @Override
  public Effect<ShipOrderApi.ShipOrder> getShipOrder(ShipOrderEntity.ShipOrderState state, ShipOrderApi.GetShipOrderRequest command) {
    return reject(state, command).orElseGet(() -> handle(state, command));
  }

  private ShipOrderEntity.ShipOrderState updateState(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShipOrder command) {
    return ShipOrderEntity.ShipOrderState
        .newBuilder()
        .setCustomerId(command.getCustomerId())
        .setOrderId(command.getOrderId())
        .setOrderedUtc(command.getOrderedUtc())
        .addAllLineItems(toEntity(command.getLineItemsList()))
        .build();
  }

  private Optional<Effect<Empty>> reject(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderRequest command) {
    if (state.getCanceledUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order has been canceled"));
    }
    if (state.getShippedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order already shipped"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(ShipOrderEntity.ShipOrderState state, ShipOrderApi.DeliveredOrderRequest command) {
    if (state.getCanceledUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order has been canceled"));
    }
    if (state.getShippedUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Order has not been shipped"));
    }
    if (state.getDeliveredUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order already delivered"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ReturnedOrderRequest command) {
    if (state.getCanceledUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order has been canceled"));
    }
    if (state.getDeliveredUtc().getSeconds() == 0) {
      return Optional.of(effects().error("Order has not been delivered"));
    }
    if (state.getReturnedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order already returned"));
    }
    return Optional.empty();
  }

  private Optional<Effect<Empty>> reject(ShipOrderEntity.ShipOrderState state, ShipOrderApi.CanceledOrderRequest command) {
    if (state.getCanceledUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order has been canceled"));
    }
    if (state.getDeliveredUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order has been delivered"));
    }
    if (state.getReturnedUtc().getSeconds() > 0) {
      return Optional.of(effects().error("Order has been returned"));
    }
    return Optional.empty();
  }

  private Optional<Effect<ShipOrderApi.ShipOrder>> reject(ShipOrderEntity.ShipOrderState state, ShipOrderApi.GetShipOrderRequest command) {
    if (state.getOrderId().isEmpty()) {
      return Optional.of(effects().error("Order not found"));
    }
    return Optional.empty();
  }

  private Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.DeliveredOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ReturnedOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.CanceledOrderRequest command) {
    return effects()
        .updateState(updateState(state, command))
        .thenReply(Empty.getDefaultInstance());
  }

  private Effect<ShipOrderApi.ShipOrder> handle(ShipOrderEntity.ShipOrderState state, ShipOrderApi.GetShipOrderRequest command) {
    return effects().reply(toApi(state));
  }

  private ShipOrderEntity.ShipOrderState updateState(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ShippedOrderRequest command) {
    return state
        .toBuilder()
        .setShippedUtc(timestampNow())
        .build();
  }

  private ShipOrderEntity.ShipOrderState updateState(ShipOrderEntity.ShipOrderState state, ShipOrderApi.DeliveredOrderRequest command) {
    return state
        .toBuilder()
        .setDeliveredUtc(timestampNow())
        .build();
  }

  private ShipOrderEntity.ShipOrderState updateState(ShipOrderEntity.ShipOrderState state, ShipOrderApi.ReturnedOrderRequest command) {
    return state
        .toBuilder()
        .setReturnedUtc(timestampNow())
        .build();
  }

  private ShipOrderEntity.ShipOrderState updateState(ShipOrderEntity.ShipOrderState state, ShipOrderApi.CanceledOrderRequest command) {
    return state
        .toBuilder()
        .setCanceledUtc(timestampNow())
        .build();
  }

  private Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }

  private ShipOrderApi.ShipOrder toApi(ShipOrderEntity.ShipOrderState state) {
    return ShipOrderApi.ShipOrder
        .newBuilder()
        .setCustomerId(state.getCustomerId())
        .setOrderId(state.getOrderId())
        .setOrderedUtc(state.getOrderedUtc())
        .setShippedUtc(state.getShippedUtc())
        .setDeliveredUtc(state.getDeliveredUtc())
        .setReturnedUtc(state.getReturnedUtc())
        .setCanceledUtc(state.getCanceledUtc())
        .addAllLineItems(toApi(state.getLineItemsList()))
        .build();
  }

  private List<ShipOrderApi.LineItem> toApi(List<ShipOrderEntity.LineItem> lineItems) {
    return lineItems.stream()
        .map(lineItem -> ShipOrderApi.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  private List<ShipOrderEntity.LineItem> toEntity(List<ShipOrderApi.LineItem> lineItems) {
    return lineItems.stream()
        .map(lineItem -> ShipOrderEntity.LineItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }
}
