package pl.karol.skygen.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import pl.karol.skygen.generator.Generator;

import java.time.Duration;

public final class GeneratorSerializer implements ObjectSerializer<Generator> {

    @Override
    public boolean supports(@NonNull Class<? super Generator> type) {
        return Generator.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Generator object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("location", object.getLocation());
        data.add("item", object.getIcon());
        data.add("generation-rate", object.getGenerationRate());
        data.add("name", object.getName());
    }

    @Override
    public Generator deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        final Location location = data.get("location", Location.class);
        final ItemStack itemStack = data.get("item", ItemStack.class);
        final Duration generationRate = data.get("generation-rate", Duration.class);
        final String name = data.get("name", String.class);

        return new Generator(name, itemStack, location, generationRate);
    }

}