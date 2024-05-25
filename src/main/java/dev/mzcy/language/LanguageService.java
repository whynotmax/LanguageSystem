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

    /**
     * Get a translation for a key in a specific language.
     * @param key                       the key
     * @param languageCode              the language code
     * @param replacements              the replacements
     * @return                          the translated string, or the key if the translation is not found
     * @throws IllegalArgumentException if the language with the specified language code does not exist
     */
    public String getTranslation(String key, String languageCode, String... replacements) {
        return languageSystem.getTranslation(key, languageCode, replacements);
    }

    /**
     * Get a translation for a key in a specific language.
     * @param language  the language code
     */
    public void create(Language language) {
        languageSystem.create(language);
    }


}
