package dev.mzcy.entity;

import dev.mzcy.LanguageSystem;
import dev.mzcy.entity.repository.LanguagePlayerRepository;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LanguagePlayerManager {

    LanguageSystem languageSystem;
    LanguagePlayerRepository languagePlayerRepository;

    public LanguagePlayerManager(LanguageSystem languageSystem, LanguagePlayerRepository languagePlayerRepository) {
        this.languageSystem = languageSystem;
        this.languagePlayerRepository = languagePlayerRepository;
    }
}
