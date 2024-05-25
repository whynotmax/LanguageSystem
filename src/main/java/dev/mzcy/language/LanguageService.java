package dev.mzcy.language;

import dev.mzcy.LanguageSystem;
import dev.mzcy.language.model.Language;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LanguageService {

    JavaPlugin javaPlugin;
    LanguageSystem languageSystem;

    public LanguageService(JavaPlugin javaPlugin, LanguageSystem languageSystem) {
        this.javaPlugin = javaPlugin;
        this.languageSystem = languageSystem;
    }

    public String getTranslation(String key, String languageCode, String... replacements) {
        return languageSystem.getTranslation(key, languageCode, replacements);
    }

    public void create(Language language) {
        languageSystem.create(language);
    }


}
