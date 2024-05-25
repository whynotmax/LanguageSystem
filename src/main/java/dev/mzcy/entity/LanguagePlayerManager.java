package dev.mzcy.entity;

import com.google.common.collect.Maps;
import dev.mzcy.LanguageSystem;
import dev.mzcy.entity.model.LanguagePlayer;
import dev.mzcy.entity.repository.LanguagePlayerRepository;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LanguagePlayerManager {

    LanguageSystem languageSystem;
    LanguagePlayerRepository languagePlayerRepository;
    Map<UUID, LanguagePlayer> languagePlayerMap = Maps.newConcurrentMap();

    public LanguagePlayerManager(LanguageSystem languageSystem, LanguagePlayerRepository languagePlayerRepository) {
        this.languageSystem = languageSystem;
        this.languagePlayerRepository = languagePlayerRepository;

        loadLanguagePlayers();
    }

    /**
     * Load all language players from the database.
     *
     * @apiNote This method is asynchronous. This method is for internal use only, and it can be changed or removed at any time.
     */
    public void loadLanguagePlayers() {
        languagePlayerRepository.asyncFindAll().thenAccept(languagePlayers -> {
            for (LanguagePlayer languagePlayer : languagePlayers) {
                languagePlayerMap.put(languagePlayer.getUniqueId(), languagePlayer);
            }
        });
    }

    /**
     * Create a new language player.
     *
     * @param uniqueId the unique ID
     */
    public void createLanguagePlayer(UUID uniqueId) {
        LanguagePlayer languagePlayer = new LanguagePlayer(uniqueId, "en");
        languagePlayerMap.put(uniqueId, languagePlayer);
        saveLanguagePlayer(languagePlayer, true);
    }

    /**
     * Save a language player.
     *
     * @param languagePlayer the language player
     * @param async         whether the operation should be asynchronous
     */
    public void saveLanguagePlayer(LanguagePlayer languagePlayer, boolean async) {
        if (async) {
            languagePlayerRepository.asyncSave(languagePlayer);
            return;
        }
        languagePlayerRepository.save(languagePlayer);
    }

    /**
     * Get a language player by their unique ID.
     *
     * @param uniqueId the unique ID
     * @return the language player, or null if the language player does not exist
     */
    public LanguagePlayer getLanguagePlayer(UUID uniqueId) {
        return languagePlayerMap.get(uniqueId);
    }


    /**
     * Save all language players.
     *
     * @param async whether the operation should be asynchronous
     *
     * @apiNote This method is for internal use only, and it can be changed or removed at any time.
     */
    public void saveLanguagePlayers(boolean async) {
        for (LanguagePlayer languagePlayer : languagePlayerMap.values()) {
            saveLanguagePlayer(languagePlayer, async);
        }
    }
}
