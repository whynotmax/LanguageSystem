package dev.mzcy.language.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.util.Properties;
import java.util.TimeZone;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class Language {

    @Id
    String languageName;
    String languageCode;

    TimeZone timeZone;
    String languageFilePath;

    @Transient
    File languageFile;

    @Transient
    Properties properties;

}
