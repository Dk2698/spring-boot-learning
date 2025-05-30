package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.OffsetTime;

/**
 * Holder class for time slot of a day,
 * <p>
 * Refer <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html">DateTimeFormatter patterns</a>
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TimeSlot implements Serializable {
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mmZ")
    @JsonFormat(pattern = "HH:mmZ")
//    @NotNull
    private OffsetTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mmZ")
    @JsonFormat(pattern = "HH:mmZ")
//    @NotNull
    private OffsetTime endTime;
}
