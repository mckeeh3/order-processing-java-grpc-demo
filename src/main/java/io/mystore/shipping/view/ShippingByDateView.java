package io.mystore.shipping.view;

import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.mystore.shipping.entity.ShippingEntity;

// This class was initially generated based on the .proto definition by Kalix tooling.
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
  public View.UpdateEffect<ShippingModel.Shipping> onOrderCreated(ShippingModel.Shipping state,
      ShippingEntity.OrderCreated event) {
    return effects().updateState(ShippingEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> onOrderShipped(ShippingModel.Shipping state,
      ShippingEntity.OrderShipped event) {
    return effects().updateState(ShippingEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> onOrderItemShipped(ShippingModel.Shipping state,
      ShippingEntity.OrderItemShipped event) {
    return effects().updateState(ShippingEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> onOrderSkuItemShipped(ShippingModel.Shipping state,
      ShippingEntity.OrderSkuItemShipped event) {
    return effects().updateState(ShippingEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<ShippingModel.Shipping> ignoreOtherEvents(ShippingModel.Shipping state, Any any) {
    return effects().ignore();
  }
}
