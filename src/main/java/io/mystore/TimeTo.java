package io.mystore;

import java.time.Instant;

import com.google.protobuf.Timestamp;

public class TimeTo {
  public static Timestamp zero() {
    return Timestamp
        .newBuilder()
        .setSeconds(0)
        .setNanos(0)
        .build();
  }

  public static Timestamp now() {
    var now = Instant.now();
    return Timestamp
        .newBuilder()
        .setSeconds(now.getEpochSecond())
        .setNanos(now.getNano())
        .build();
  }
}
