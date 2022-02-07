package io.mystore.stock.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.TimeTo;
import io.mystore.stock.api.ShippableSkuItemsTimerApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShippableSkuItemsTimer extends AbstractShippableSkuItemsTimer {
  static final Logger log = LoggerFactory.getLogger(ShippableSkuItemsTimer.class);

  public ShippableSkuItemsTimer(ValueEntityContext context) {
  }

  @Override
  public ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState emptyState() {
    return ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createShippableSkuItemsTimer(
      ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState state, ShippableSkuItemsTimerApi.CreateShippableSkuItemsTimerCommand command) {
    log.info("state: {}\nCreateShippableSkuItemsTimerCommand:{}", state, command);

    return effects()
        .updateState(
            state.toBuilder()
                .setSkuId(command.getSkuId())
                .setStarted(TimeTo.now())
                .setStopAfter(timestampPlus(5, ChronoUnit.MINUTES)) // todo: make configurable
                .build())
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> pingShippableSkuItemsTimer(
      ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState state, ShippableSkuItemsTimerApi.PingShippableSkuItemsTimerCommand command) {
    log.info("pingShippableSkuItemsTimer: stop in {}\nstate: {}", stopAfter(state), state);

    if (TimeTo.now().getSeconds() > state.getStopAfter().getSeconds()) {
      return effects().reply(Empty.getDefaultInstance());
    } else {
      return effects()
          .updateState(
              state.toBuilder()
                  .setLastPinged(TimeTo.now())
                  .build())
          .thenReply(Empty.getDefaultInstance());
    }
  }

  @Override
  public Effect<ShippableSkuItemsTimerApi.GetShippableSkuItemsTimerResponse> getShippableSkuItemsTimer(
      ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState state, ShippableSkuItemsTimerApi.GetShippableSkuItemsTimerRequest request) {
    return effects().error("The command handler for `GetShippableSkuItemsTimer` is not implemented, yet");
  }

  static Timestamp timestampPlus(long amount, TemporalUnit unit) {
    var now = Instant.now();
    var plus = now.plus(amount, unit);
    return Timestamp
        .newBuilder()
        .setSeconds(plus.getEpochSecond())
        .setNanos(plus.getNano())
        .build();
  }

  static String stopAfter(ShippableSkuItemsTimerEntity.ShippableSkuItemsTimerState state) {
    var seconds = state.getStopAfter().getSeconds() - TimeTo.now().getSeconds();
    var minutes = seconds / 60;
    return String.format("%dm%ds", minutes, seconds % 60);
  }
}
