package com.duguyin.id_generator.util;

import com.duguyin.id_generator.impl.bean.IdType;

public class TimeUtils {


    public static final long EPOCH = 1420041600000L;


    public static void validateTimestamp(long lastTimestamp, long timestamp) {
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException(
                    String.format(
                            "Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
                            lastTimestamp - timestamp));
        }
    }

    public static long tillNextTimeUnit(final long lastTimestamp, final IdType idType) {

        System.out.println((String
                    .format("Ids are used out during %d. Waiting till next second/milisencond.",
                            lastTimestamp)));

        long timestamp = TimeUtils.genTime(idType);
        while (timestamp <= lastTimestamp) {
            timestamp = TimeUtils.genTime(idType);
        }

        System.out.println(
           String.format("Next second/milisencond %d is up.",
                    timestamp));

        return timestamp;
    }

    public static long genTime(final IdType idType) {
        if (idType == IdType.MAX_PEAK)
            return (System.currentTimeMillis() - TimeUtils.EPOCH) / 1000;
        else if (idType == IdType.MIN_GRANULARITY)
            return (System.currentTimeMillis() - TimeUtils.EPOCH);

        return (System.currentTimeMillis() - TimeUtils.EPOCH) / 1000;
    }

}