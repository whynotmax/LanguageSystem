# LanguageSystem

## 1. Introduction
This is a simple language system that can be used to create a multi-language system for your minecraft plugin. It is very simple to use and can be easily integrated into your application.
This project is built using Java 16 and uses MongoDB as the database to store the language data.

### 1.1. Features
- Create a multi-language system for your application
- Store language data in a MongoDB database
- Easy to use and integrate into your application
- Built using Java 16
- Uses Lombok to reduce boilerplate code

### 1.2. Dependencies
- Java 16
- MongoDB Database
- Lombok
- Spigot API (to use in Minecraft plugins)
- en2do ([Entity-To-Document made by Koboo](https://github.com/Koboo/))

## 2. Installation
You can install the package via maven and gradle.

### 2.1. Maven
```xml
<dependency>
    <groupId>com.github.whynotmax</groupId>
    <artifactId>LanguageSystem</artifactId>
    <version>1.0.1</version>
</dependency>
```

### 2.1. Gradle
```gradle
implementation 'com.github.whynotmax:LanguageSystem:1.0.1'
```

## 3. Usage
### 3.1. Create a new language system
To create a new language system, you need to create a new instance of the `LanguageSystem` class. You can do this by using the `LanguageSystemBuilder` class.
The `LanguageSystemBuilder` class has a method called `withMongoCredentials` that takes a `Credentials` object as a parameter. The `Credentials` object has two fields: `uri` and `database`. The `uri` field is the connection string to the MongoDB database and the `database` field is the name of the database that you want to use.
<br>

```java
package dev.whynotmax.languagesystem.example;

import dev.whynotmax.languagesystem.LanguageSystemBuilder;
import dev.whynotmax.languagesystem.LanguageSystem;
import lombok.Getter;

@Getter
public class Main extends JavaPlugin {
    private LanguageSystem languageSystem;
    
    @Override
    public void onEnable() {
        languageSystem = new LanguageSystemBuilder(this)
            .withMongoCredentials(Credentials.of("mongodb://localhost:27017", "languageSystem"))
            .build();
    }
    
}
```

**Remember**: You need to have a MongoDB database running in order to use this library. Also, you need to shade en2do and lombok in your project. You can do this by adding the following lines to your `pom.xml` or to your `build.gradle` file:

#### Maven:
```xml
<repositories>
    <repository>
        <id>koboo-reposilite-releases</id>
        <name>Koboo's Reposilite</name>
        <url>https://reposilite.koboo.eu/releases</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.koboo</groupId>
        <artifactId>en2do</artifactId>
        <version>2.3.9</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.22</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

#### Gradle:
```gradle
repositories {
    maven {
        name "kobooReposiliteReleases"
        url "https://reposilite.koboo.eu/releases"
    }
}

dependencies {
    implementation 'com.github.koboo:en2do:2.3.9'
    compileOnly 'org.projectlombok:lombok:1.18.22'
    annotationProcessor 'org.projectlombok:lombok:1.18.22'
}
```

### 3.2. Initializing the LanguageService
After creating a new instance of the `LanguageSystem` class, you need to initialize the `LanguageService` class. The `LanguageService` class is used to interact with the language data in the database. You can do this by simply creating a new instance of the `LanguageService` class and passing the `JavaPlugin` and the `LanguageSystem` instance we've created in the previous step as a parameter. This should look like this:

```java
package dev.whynotmax.languagesystem.example;

import dev.whynotmax.languagesystem.LanguageSystemBuilder;
import dev.whynotmax.languagesystem.LanguageSystem;
import dev.whynotmax.languagesystem.service.LanguageService;
import lombok.Getter;

@Getter
public class Main extends JavaPlugin {
    private LanguageSystem languageSystem;
    private LanguageService languageService;
    
    @Override
    public void onEnable() {
        languageSystem = new LanguageSystemBuilder(this)
            .withMongoCredentials(Credentials.of("mongodb://localhost:27017", "languageSystem"))
            .build();
        
        languageService = new LanguageService(this, languageSystem);
    }
}
```

### 3.3. Creating a new language
To create a new language, you can use the `LanguageService` class. The `LanguageService` class has a method called `createLanguage` that takes a `Language` object as a parameter. The `Language` object has two fields: `name` and `code`. The `name` field is the name of the language and the `code` field is the language code (e.g. en, de, fr, etc.). Here is an example of how you can create a new language:

```java
package dev.whynotmax.languagesystem.example;

import dev.whynotmax.languagesystem.LanguageSystemBuilder;
import dev.whynotmax.languagesystem.LanguageSystem;
import dev.whynotmax.languagesystem.service.LanguageService;
import dev.whynotmax.languagesystem.model.Language;
import lombok.Getter;

@Getter
public class Main extends JavaPlugin {
    private LanguageSystem languageSystem;
    private LanguageService languageService;
    
    @Override
    public void onEnable() {
        languageSystem = new LanguageSystemBuilder(this)
            .withMongoCredentials(Credentials.of("mongodb://localhost:27017", "languageSystem"))
            .build();
        
        languageService = new LanguageService(this, languageSystem);
        
        Language englishLanguage = Language.builder()
                .name("English")
                .code("en")
                .build();
        languageService.createLanguage(english);
    }
}
```

### 3.4. Getting a language by code
To get a language by its code, you can use the `LanguageService` class. The `LanguageService` class has a method called `getLanguageByCode` that takes a `String` as a parameter. This method will return a `Language` object if the language with the given code exists in the database. Here is an example of how you can get a language by its code:

```java
package dev.whynotmax.languagesystem.example;

import dev.whynotmax.languagesystem.LanguageSystemBuilder;
import dev.whynotmax.languagesystem.LanguageSystem;
import dev.whynotmax.languagesystem.service.LanguageService;
import dev.whynotmax.languagesystem.model.Language;
import lombok.Getter;

@Getter
public class Main extends JavaPlugin {
    private LanguageSystem languageSystem;
    private LanguageService languageService;
    
    @Override
    public void onEnable() {
        languageSystem = new LanguageSystemBuilder(this)
            .withMongoCredentials(Credentials.of("mongodb://localhost:27017", "languageSystem"))
            .build();
        
        languageService = new LanguageService(this, languageSystem);
        
        Language english = languageService.getLanguageByCode("en");
    }
}
```

### 3.5. Translating a message
To translate a message, you can use the `LanguageService` class. The `LanguageService` class has a method called `getTranslation` that takes a `String key`, a `String languageCode` object, and `String... replacements` as parameters. The first parameter is the message that you want to translate, the second parameter is the language that you want to translate the message to, and the third... parameters are the replacements. This method will return the translated message if the translation is found in the database, otherwise it will return the key. Here is an example of how you can translate a message:

```java
package dev.whynotmax.languagesystem.example;

import dev.whynotmax.languagesystem.LanguageSystemBuilder;
import dev.whynotmax.languagesystem.LanguageSystem;
import dev.whynotmax.languagesystem.service.LanguageService;
import dev.whynotmax.languagesystem.model.Language;
import lombok.Getter;

@Getter
public class Main extends JavaPlugin {
    private LanguageSystem languageSystem;
    private LanguageService languageService;
    
    @Override
    public void onEnable() {
        languageSystem = new LanguageSystemBuilder(this)
            .withMongoCredentials(Credentials.of("mongodb://localhost:27017", "languageSystem"))
            .build();
        
        languageService = new LanguageService(this, languageSystem);
        
        String translatedMessage = languageService.getTranslation("text.hello", "en");
        getLogger().info(translatedMessage); // Output: Hello! | Original: Hello!
    }
}
```

You can also use placeholders in your messages. To use placeholders, you need to add the placeholders to your message using curly braces with an index, like this: `{0}`. Here is an example of how you can use placeholders:

```java
package dev.whynotmax.languagesystem.example;

import dev.whynotmax.languagesystem.LanguageSystemBuilder;
import dev.whynotmax.languagesystem.LanguageSystem;
import dev.whynotmax.languagesystem.service.LanguageService;
import dev.whynotmax.languagesystem.model.Language;
import lombok.Getter;

@Getter
public class Main extends JavaPlugin {
    private LanguageSystem languageSystem;
    private LanguageService languageService;
    
    @Override
    public void onEnable() {
        languageSystem = new LanguageSystemBuilder(this)
            .withMongoCredentials(Credentials.of("mongodb://localhost:27017", "languageSystem"))
            .build();
        
        languageService = new LanguageService(this, languageSystem);
        
        String translatedMessage = languageService.getTranslation("text.hello", "en", "Max");
        getLogger().info(translatedMessage); // Output: Hello Max! | Original: Hello {0}!
    }
}
```

### 3.6. Editing translations
To edit a translation, you need at least:
- One already created language
- A key that you want to edit
- Start the server at least once

After you have all of these, you can edit the translations in the `lang_{code}.properties` files in the `languages` folder in your plugin's data folder. The `lang_{code}.properties` files are automatically generated when you start the server for the first time. Here is an example of how you can edit a translation:

```properties
# lang_en.properties
text.hello=Hello {0}!
```

In this example, we are editing the translation for the key `text.hello` in the `lang_en.properties` file. We are changing the value from `Hello!` to `Hello {0}!`. This will change the translation for the key `text.hello` in the English language to `Hello {0}!`.
It is important to note that you need to restart the server after editing the translations in order for the changes to take effect. Maybe in the future, I will add a feature to reload the translations without restarting the server.

### 3.7. Deleting a language
To delete a language, you can use the `LanguageService` class. The `LanguageService` class has a method called `deleteLanguage` that takes a `String languageCode` as a parameter. This method will delete the language with the given code from the database. Here is an example of how you can delete a language:

```java
package dev.whynotmax.languagesystem.example;

import dev.whynotmax.languagesystem.LanguageSystemBuilder;
import dev.whynotmax.languagesystem.LanguageSystem;
import dev.whynotmax.languagesystem.service.LanguageService;
import dev.whynotmax.languagesystem.model.Language;
import lombok.Getter;

@Getter
public class Main extends JavaPlugin {
    private LanguageSystem languageSystem;
    private LanguageService languageService;
    
    @Override
    public void onEnable() {
        languageSystem = new LanguageSystemBuilder(this)
            .withMongoCredentials(Credentials.of("mongodb://localhost:27017", "languageSystem"))
            .build();
        
        languageService = new LanguageService(this, languageSystem);
        
        languageService.deleteLanguage("en");
        //or
        Language english = languageService.getLanguageByCode("en");
        languageService.deleteLanguage(english);
    }
}
```

### That's it!
You have successfully created a multi-language system for your application using the LanguageSystem library. You can now use this library to create a multi-language system for your Minecraft plugin or any other application. If you have any questions or need help, feel free to ask in the comments below. I hope you found this tutorial helpful and that you learned something new. Thank you for reading!

## 4. License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
