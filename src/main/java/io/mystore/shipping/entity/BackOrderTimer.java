package io.mystore.shipping.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.google.protobuf.Duration;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mystore.shipping.api.BackOrderTimerApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class BackOrderTimer extends AbstractBackOrderTimer {
  static final Logger log = LoggerFactory.getLogger(BackOrderTimer.class);

  public BackOrderTimer(ValueEntityContext context) {
  }

  @Override
  public BackOrderTimerEntity.BackOrderTimerState emptyState() {
    return BackOrderTimerEntity.BackOrderTimerState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createBackOrderTimer(BackOrderTimerEntity.BackOrderTimerState state, BackOrderTimerApi.CreateBackOrderTimerCommand command) {
    log.info("state: {}\nCreateBackOrderTimerCommand:{}", state, command);
    return effects()
        .updateState(
            state.toBuilder()
                .setSkuId(command.getSkuId())
                .setStarted(timestampNow())
                .setStopAfter(timestampPlus(5, ChronoUnit.MINUTES)) // todo: make configurable
                .setPingInterval(duration(2, ChronoUnit.SECONDS)) // todo: make configurable
                .build())
        .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> pingBackOrderTimer(BackOrderTimerEntity.BackOrderTimerState state, BackOrderTimerApi.PingBackOrderTimerCommand command) {
    log.info("pingBackOrderTimer: stop in {}\nstate: {}", stopAfter(state), state);

    if (timestampNow().getSeconds() > state.getStopAfter().getSeconds()) {
      return effects().reply(Empty.getDefaultInstance());
    } else {
      // ===============================================================================================================
      waitInterval(state.getPingInterval()); // this is the ping interval delay - todo: is there a better way to do this?
      // ===============================================================================================================

      return effects()
          .updateState(
              state.toBuilder()
                  .setLastPinged(timestampNow())
                  .build())
          .thenReply(Empty.getDefaultInstance());
    }
  }

  @Override
  public Effect<BackOrderTimerApi.GetBackOrderTimerResponse> getBackOrderTimer(BackOrderTimerEntity.BackOrderTimerState state, BackOrderTimerApi.GetBackOrderTimerRequest command) {
    return effects().reply(toApi(state));
  }

  static BackOrderTimerApi.GetBackOrderTimerResponse toApi(BackOrderTimerEntity.BackOrderTimerState state) {
    return BackOrderTimerApi.GetBackOrderTimerResponse
        .newBuilder()
        .setSkuId(state.getSkuId())
        .setStarted(state.getStarted())
        .setStopAfter(timestampPlus(state.getStopAfter().getSeconds(), ChronoUnit.SECONDS))
        .setPingInterval(state.getPingInterval())
        .setLastPinged(state.getLastPinged())
        .build();
  }

  static Timestamp timestampNow() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
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

  static Duration duration(long amount, TemporalUnit unit) {
    var duration = java.time.Duration.of(amount, unit);
    return Duration
        .newBuilder()
        .setSeconds(duration.getSeconds())
        .setNanos(duration.getNano())
        .build();
  }

  static void waitInterval(Duration pingInterval) {
    var millis = pingInterval.getSeconds() * 1000 + pingInterval.getNanos() / 1000000;
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      log.error("Interrupted while waiting for ping interval", e);
    }
  }

  static String stopAfter(BackOrderTimerEntity.BackOrderTimerState state) {
    var seconds = state.getStopAfter().getSeconds() - timestampNow().getSeconds();
    var minutes = seconds / 60;
    return String.format("%dm%ds", minutes, seconds % 60);
  }
}
