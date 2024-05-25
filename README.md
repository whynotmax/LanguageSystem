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
- en2do (Entity-To-Document made by @koboo)

## 2. Installation
You can install the package via maven and gradle.

### 2.1. Maven
```xml
<dependency>
    <groupId>com.github.whynotmax</groupId>
    <artifactId>LanguageSystem</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2.1. Gradle
```gradle
implementation 'com.github.whynotmax:LanguageSystem:1.0.0'
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

### To be continued...

<svg xmlns="http://www.w3.org/2000/svg" height="200px" width="200px" viewBox="0 0 200 200" class="pencil">
	<defs>
		<clipPath id="pencil-eraser">
			<rect height="30" width="30" ry="5" rx="5"></rect>
		</clipPath>
	</defs>
	<circle transform="rotate(-113,100,100)" stroke-linecap="round" stroke-dashoffset="439.82" stroke-dasharray="439.82 439.82" stroke-width="2" stroke="currentColor" fill="none" r="70" class="pencil__stroke"></circle>
	<g transform="translate(100,100)" class="pencil__rotate">
		<g fill="none">
			<circle transform="rotate(-90)" stroke-dashoffset="402" stroke-dasharray="402.12 402.12" stroke-width="30" stroke="hsl(223,90%,50%)" r="64" class="pencil__body1"></circle>
			<circle transform="rotate(-90)" stroke-dashoffset="465" stroke-dasharray="464.96 464.96" stroke-width="10" stroke="hsl(223,90%,60%)" r="74" class="pencil__body2"></circle>
			<circle transform="rotate(-90)" stroke-dashoffset="339" stroke-dasharray="339.29 339.29" stroke-width="10" stroke="hsl(223,90%,40%)" r="54" class="pencil__body3"></circle>
		</g>
		<g transform="rotate(-90) translate(49,0)" class="pencil__eraser">
			<g class="pencil__eraser-skew">
				<rect height="30" width="30" ry="5" rx="5" fill="hsl(223,90%,70%)"></rect>
				<rect clip-path="url(#pencil-eraser)" height="30" width="5" fill="hsl(223,90%,60%)"></rect>
				<rect height="20" width="30" fill="hsl(223,10%,90%)"></rect>
				<rect height="20" width="15" fill="hsl(223,10%,70%)"></rect>
				<rect height="20" width="5" fill="hsl(223,10%,80%)"></rect>
				<rect height="2" width="30" y="6" fill="hsla(223,10%,10%,0.2)"></rect>
				<rect height="2" width="30" y="13" fill="hsla(223,10%,10%,0.2)"></rect>
			</g>
		</g>
		<g transform="rotate(-90) translate(49,-30)" class="pencil__point">
			<polygon points="15 0,30 30,0 30" fill="hsl(33,90%,70%)"></polygon>
			<polygon points="15 0,6 30,0 30" fill="hsl(33,90%,50%)"></polygon>
			<polygon points="15 0,20 10,10 10" fill="hsl(223,10%,10%)"></polygon>
		</g>
	</g>
</svg>
<style>
.pencil {
  display: block;
  width: 10em;
  height: 10em;
}

.pencil__body1,
.pencil__body2,
.pencil__body3,
.pencil__eraser,
.pencil__eraser-skew,
.pencil__point,
.pencil__rotate,
.pencil__stroke {
animation-duration: 3s;
animation-timing-function: linear;
animation-iteration-count: infinite;
}

.pencil__body1,
.pencil__body2,
.pencil__body3 {
transform: rotate(-90deg);
}

.pencil__body1 {
animation-name: pencilBody1;
}

.pencil__body2 {
animation-name: pencilBody2;
}

.pencil__body3 {
animation-name: pencilBody3;
}

.pencil__eraser {
animation-name: pencilEraser;
transform: rotate(-90deg) translate(49px,0);
}

.pencil__eraser-skew {
animation-name: pencilEraserSkew;
animation-timing-function: ease-in-out;
}

.pencil__point {
animation-name: pencilPoint;
transform: rotate(-90deg) translate(49px,-30px);
}

.pencil__rotate {
animation-name: pencilRotate;
}

.pencil__stroke {
animation-name: pencilStroke;
transform: translate(100px,100px) rotate(-113deg);
}

/* Animations */
@keyframes pencilBody1 {
from,
to {
stroke-dashoffset: 351.86;
transform: rotate(-90deg);
}

50% {
stroke-dashoffset: 150.8;
/* 3/8 of diameter */
transform: rotate(-225deg);
}
}

@keyframes pencilBody2 {
from,
to {
stroke-dashoffset: 406.84;
transform: rotate(-90deg);
}

50% {
stroke-dashoffset: 174.36;
transform: rotate(-225deg);
}
}

@keyframes pencilBody3 {
from,
to {
stroke-dashoffset: 296.88;
transform: rotate(-90deg);
}

50% {
stroke-dashoffset: 127.23;
transform: rotate(-225deg);
}
}

@keyframes pencilEraser {
from,
to {
transform: rotate(-45deg) translate(49px,0);
}

50% {
transform: rotate(0deg) translate(49px,0);
}
}

@keyframes pencilEraserSkew {
from,
32.5%,
67.5%,
to {
transform: skewX(0);
}

35%,
65% {
transform: skewX(-4deg);
}

37.5%,
62.5% {
transform: skewX(8deg);
}

40%,
45%,
50%,
55%,
60% {
transform: skewX(-15deg);
}

42.5%,
47.5%,
52.5%,
57.5% {
transform: skewX(15deg);
}
}

@keyframes pencilPoint {
from,
to {
transform: rotate(-90deg) translate(49px,-30px);
}

50% {
transform: rotate(-225deg) translate(49px,-30px);
}
}

@keyframes pencilRotate {
from {
transform: translate(100px,100px) rotate(0);
}

to {
transform: translate(100px,100px) rotate(720deg);
}
}

@keyframes pencilStroke {
from {
stroke-dashoffset: 439.82;
transform: translate(100px,100px) rotate(-113deg);
}

50% {
stroke-dashoffset: 164.93;
transform: translate(100px,100px) rotate(-113deg);
}

75%,
to {
stroke-dashoffset: 439.82;
transform: translate(100px,100px) rotate(112deg);
}
}</style>


