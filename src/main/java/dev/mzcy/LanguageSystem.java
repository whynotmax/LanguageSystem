package dev.mzcy;

import dev.mzcy.language.LanguageService;
import dev.mzcy.language.model.Language;
import dev.mzcy.language.repository.LanguageRepository;
import dev.mzcy.mongo.TimeZoneCodec;
import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.java.Log;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ApiStatus.Internal
@Log
public class LanguageSystem {

    JavaPlugin javaPlugin;
    Credentials mongoCredentials;
    @NonFinal LanguageRepository languageRepository;
    List<Language> loadedLanguages;

    /**
     * Create a new LanguageSystem instance.
     * @param javaPlugin        the JavaPlugin instance
     * @param mongoCredentials  the MongoDB credentials
     * <br>
     * This method is for internal use only, and it can be changed or removed at any time.
     */
    @ApiStatus.Internal
    public LanguageSystem(JavaPlugin javaPlugin, Credentials mongoCredentials) {
        this.javaPlugin = javaPlugin;
        this.mongoCredentials = mongoCredentials;
        this.loadedLanguages = new CopyOnWriteArrayList<>();

        this.onEnable();
    }

    /**
     * Load all languages from the database.
     * <br>
     * This method is for internal use only, and it can be changed or removed at any time.
     */
    @ApiStatus.Internal
    private void onEnable() {
        MongoManager mongoManager = new MongoManager(mongoCredentials)
                .registerCodec(new TimeZoneCodec());
        this.languageRepository = mongoManager.create(LanguageRepository.class);
        this.loadedLanguages.addAll(languageRepository.findAll());

        this.loadedLanguages.forEach(language -> {
            File languageFile = new File("plugins/" + javaPlugin.getName() + "/languages/" + language.getLanguageCode() + ".yml");
            if (!languageFile.exists()) {
                languageFile.getParentFile().mkdirs();
                try {
                    languageFile.createNewFile();
                } catch (Exception e) {
                    log.warning("An error occurred while creating language file " + languageFile.getName());
                }
            }
            language.setLanguageFile(languageFile);
            Properties properties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream(languageFile)) {
                properties.load(fileInputStream);
                language.setProperties(properties);
            } catch (Exception e) {
                log.warning("An error occurred while loading language file " + languageFile.getName());
            }
        });
    }

    /**
     * Get a translation for a key in a specific language.
     * @param key                       the key
     * @param languageCode              the language code
     * @param replacements              the replacements
     * @return                          the translated string, or the key if the translation is not found
     * @throws IllegalArgumentException if the language with the specified language code does not exist
     * <br>
     * This method is for internal use only, and it can be changed or removed at any time.
     */
    @ApiStatus.Internal
    public String getTranslation(String key, String languageCode, String... replacements) {
        for (Language language : loadedLanguages) {
            if (language.getLanguageCode().equals(languageCode)) {
                String translation = language.getProperties().getProperty(key);
                if (translation == null) {
                    log.warning("Translation for key " + key + " not found in language " + languageCode + ". Returning key.");
                    return key + " (translation not found)";
                }
                for (int i = 0; i < replacements.length; i++) {
                    translation = translation.replace("{" + i + "}", replacements[i]);
                }
                return translation;
            }
        }
        throw new IllegalArgumentException("Language with code " + languageCode + " not found");
    }

    /**
     * Create a new language.
     * @param newLanguage the new language
     * <br>
     * This method is for internal use only, and it can be changed or removed at any time.
     */
    @ApiStatus.Internal
    public void create(Language newLanguage) {
        newLanguage.setLanguageFile(new File("plugins/" + javaPlugin.getName() + "/languages/lang_" + newLanguage.getLanguageCode() + ".properties"));
        //Check if the language already exists
        for (Language language : loadedLanguages) {
            if (language.getLanguageCode().equals(newLanguage.getLanguageCode())) {
                throw new IllegalArgumentException("Language with code " + newLanguage.getLanguageCode() + " already exists");
            }
            if (language.getLanguageName().equals(newLanguage.getLanguageName())) {
                throw new IllegalArgumentException("Language with name " + newLanguage.getLanguageName() + " already exists");
            }
            if (language.getLanguageFile().equals(newLanguage.getLanguageFile())) {
                throw new IllegalArgumentException("Language with file " + newLanguage.getLanguageFile() + " already exists");
            }
        }
        languageRepository.save(newLanguage);
        loadedLanguages.add(newLanguage);
    }
}
