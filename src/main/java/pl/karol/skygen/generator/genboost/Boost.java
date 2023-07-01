package pl.karol.skygen.generator.genboost;

import java.time.Duration;

public final class Boost {
    private final float multiplier;
    private final Duration duration;

    public Boost(float multiplier, Duration duration) {
        this.multiplier = multiplier;
        this.duration = duration;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public Duration getDuration() {
        return duration;
    }
}
