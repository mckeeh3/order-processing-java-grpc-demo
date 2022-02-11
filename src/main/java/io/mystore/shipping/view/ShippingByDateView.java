package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.shipping.entity.ShippingEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippingByDateView extends AbstractShippingByDateView {

  public ShippingByDateView(ViewContext context) {
  }

  @Override
  public ShippingModel.Shipping emptyState() {
    return ShippingModel.Shipping.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> onOrderCreated(ShippingModel.Shipping state, ShippingEntity.OrderCreated orderCreated) {
    return effects().updateState(
        ShippingEventHandler
            .fromState(state)
            .handle(orderCreated)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> onOrderShipped(ShippingModel.Shipping state, ShippingEntity.OrderShipped orderShipped) {
    return effects().updateState(
        ShippingEventHandler
            .fromState(state)
            .handle(orderShipped)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> onOrderItemShipped(ShippingModel.Shipping state, ShippingEntity.OrderItemShipped orderItemShipped) {
    return effects().updateState(
        ShippingEventHandler
            .fromState(state)
            .handle(orderItemShipped)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> onOrderSkuItemShipped(ShippingModel.Shipping state, ShippingEntity.OrderSkuItemShipped orderSkuItemShipped) {
    return effects().updateState(
        ShippingEventHandler
            .fromState(state)
            .handle(orderSkuItemShipped)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> ignoreOtherEvents(ShippingModel.Shipping state, Any any) {
    return effects().ignore();
  }
}
