package pl.karol.skygen.config;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import pl.karol.skygen.config.serializer.GeneratorSerializer;

public final class SkyGeneratorsSerdes implements OkaeriSerdesPack {

    @Override
    public void register(SerdesRegistry registry) {
        registry.register(new GeneratorSerializer());
    }

}