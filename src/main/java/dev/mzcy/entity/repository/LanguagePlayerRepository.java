package dev.mzcy.entity.repository;

import dev.mzcy.entity.model.LanguagePlayer;
import eu.koboo.en2do.repository.AsyncRepository;
import eu.koboo.en2do.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface LanguagePlayerRepository extends Repository<LanguagePlayer, UUID>, AsyncRepository<LanguagePlayer, UUID>{

    List<LanguagePlayer> findManyByLanguageCode(String languageCode);

}
