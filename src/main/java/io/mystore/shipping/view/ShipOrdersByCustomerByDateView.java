package io.mystore.shipping.view;

import com.akkaserverless.javasdk.view.View;
import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.entity.ShipOrderEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrdersByCustomerByDateView extends AbstractShipOrdersByCustomerByDateView {
  static final Logger log = LoggerFactory.getLogger(ShipOrdersByCustomerByDateView.class);

  public ShipOrdersByCustomerByDateView(ViewContext context) {
  }

  @Override
  public ShipOrderModel.ShipOrder emptyState() {
    return ShipOrderModel.ShipOrder.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<ShipOrderModel.ShipOrder> onShipOrderCreated(ShipOrderModel.ShipOrder state, ShipOrderEntity.ShipOrderCreated shipOrderCreated) {
    log.info("onShipOrderCreated: state: {}\nshipOrderCreated: {}", state, shipOrderCreated);

    return effects().updateState(
        ShipOrderEventHandler
            .fromState(state)
            .handle(shipOrderCreated)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShipOrderModel.ShipOrder> onOrderShipped(ShipOrderModel.ShipOrder state, ShipOrderEntity.OrderShipped orderShipped) {
    log.info("onOrderShipped: state: {}\norderShipped: {}", state, orderShipped);

    return effects().updateState(
        ShipOrderEventHandler
            .fromState(state)
            .handle(orderShipped)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShipOrderModel.ShipOrder> onOrderSkuShipped(ShipOrderModel.ShipOrder state, ShipOrderEntity.OrderSkuShipped orderSkuShipped) {
    log.info("onOrderSkuShipped: state: {}\norderSkuShipped: {}", state, orderSkuShipped);

    return effects().updateState(
        ShipOrderEventHandler
            .fromState(state)
            .handle(orderSkuShipped)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShipOrderModel.ShipOrder> onOrderItemShipped(ShipOrderModel.ShipOrder state, ShipOrderEntity.OrderItemShipped orderItemShipped) {
    log.info("onOrderItemShipped: state: {}\norderItemShipped: {}", state, orderItemShipped);

    return effects().updateState(
        ShipOrderEventHandler
            .fromState(state)
            .handle(orderItemShipped)
            .toState());
  }

  @Override
  public View.UpdateEffect<ShipOrderModel.ShipOrder> ignoreOtherEvents(ShipOrderModel.ShipOrder state, Any any) {
    return effects().ignore();
  }
}
