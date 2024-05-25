package dev.mzcy;

import eu.koboo.en2do.Credentials;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LanguageSystemBuilder {

    final JavaPlugin javaPlugin;
    Credentials mongoCredentials;

    private LanguageSystemBuilder(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public LanguageSystemBuilder withMongoCredentials(Credentials mongoCredentials) {
        this.mongoCredentials = mongoCredentials;
        return this;
    }

    public LanguageSystem build() {
        return new LanguageSystem(javaPlugin, mongoCredentials);
    }



    public static LanguageSystemBuilder create(JavaPlugin javaPlugin) {
        return new LanguageSystemBuilder(javaPlugin);
    }

}
