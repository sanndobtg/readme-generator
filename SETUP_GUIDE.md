#  Guide Complet - README Generator

##  Table des Matières

1. [Prérequis](#prérequis)
2. [Installation sur macBook Pro Intel](#installation-sur-macbook-pro-intel)
3. [Configuration Eclipse](#configuration-eclipse)
4. [Configuration Checkstyle Google](#configuration-checkstyle-google)
5. [Configuration Lombok](#configuration-lombok)
6. [Lancement de l'Application](#lancement-de-lapplication)
7. [Exécution des Tests JUnit](#exécution-des-tests-junit)
8. [Génération de la Javadoc](#génération-de-la-javadoc)
9. [Build et Déploiement](#build-et-déploiement)
10. [Troubleshooting](#troubleshooting)

---

##  Prérequis

### Logiciels Requis

- **Java JDK 17** ou supérieur
- **Maven 3.6+**
- **Eclipse IDE for Java EE Developers** (2023-12 ou plus récent)
- **Git** (pour le versioning)

### Vérification des Prérequis

```bash
# Vérifier Java
java -version
# Doit afficher: java version "17.x.x" ou supérieur

# Vérifier Maven
mvn -version
# Doit afficher: Apache Maven 3.6.x ou supérieur

# Vérifier Git
git --version
```

---

##  Installation sur macBook Pro Intel

### 1. Installation de Java 17

```bash
# Via Homebrew (recommandé)
brew install openjdk@17

# Ajouter Java au PATH
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Vérifier l'installation
java -version
```

### 2. Installation de Maven

```bash
# Via Homebrew
brew install maven

# Vérifier l'installation
mvn -version
```

### 3. Installation d'Eclipse

1. Télécharger Eclipse IDE for Java EE Developers depuis:
   https://www.eclipse.org/downloads/

2. Extraire l'archive et déplacer Eclipse.app vers `/Applications`

3. Lancer Eclipse et choisir un workspace (ex: `~/eclipse-workspace`)

---

##  Configuration Eclipse

### Étape 1: Importer le Projet Maven

1. **Ouvrir Eclipse**

2. **File > Import > Maven > Existing Maven Projects**

3. **Sélectionner le dossier du projet**
   - Browse vers: `/chemin/vers/readme-generator`
   - Cocher le projet `readme-generator/pom.xml`
   - Cliquer sur **Finish**

4. **Attendre l'indexation Maven**
   - Eclipse va télécharger toutes les dépendances
   - Vérifier dans la Console qu'il n'y a pas d'erreurs

### Étape 2: Configuration du JDK

1. **Eclipse > Preferences** (ou **Cmd + ,**)

2. **Java > Installed JREs**
   - Cliquer sur **Add...**
   - Sélectionner **Standard VM**
   - JRE home: `/usr/local/opt/openjdk@17`
   - Cliquer sur **Finish**
   - Cocher la case pour Java 17

3. **Java > Compiler**
   - Compiler compliance level: **17**
   - Cliquer sur **Apply and Close**

### Étape 3: Configuration Maven dans Eclipse

1. **Eclipse > Preferences > Maven**

2. **Installations**
   - Cliquer sur **Add...**
   - Sélectionner `/usr/local/Cellar/maven/x.x.x/libexec`
   - Cocher la nouvelle installation
   - Cliquer sur **Apply**

3. **User Settings**
   - Vérifier que le fichier `settings.xml` est correct

### Étape 4: Update Project

1. **Clic droit sur le projet** dans Project Explorer

2. **Maven > Update Project...**

3. **Cocher "Force Update of Snapshots/Releases"**

4. **Cliquer sur OK**

---

##  Configuration Checkstyle Google

### Installation du Plugin Checkstyle

1. **Help > Eclipse Marketplace**

2. **Rechercher "Checkstyle"**

3. **Installer "Checkstyle Plug-in"** (version 10.x.x)

4. **Redémarrer Eclipse**

### Configuration Checkstyle

1. **Eclipse > Preferences > Checkstyle**

2. **Cliquer sur "New..." (Configuration)**

3. **Paramètres:**
   - Type: **Remote Configuration**
   - Name: **Google Checks**
   - Location: `https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml`

4. **Cliquer sur OK**

5. **Sélectionner "Google Checks" comme Default**

### Activer Checkstyle sur le Projet

1. **Clic droit sur le projet > Properties**

2. **Checkstyle**

3. **Cocher "Checkstyle active for this project"**

4. **Sélectionner "Google Checks" dans la liste**

5. **Cliquer sur Apply and Close**

### Visualiser les Violations Checkstyle

1. **Window > Show View > Other...**

2. **Checkstyle > Checkstyle violations**

3. **Les violations apparaîtront dans la vue**

### Formatter compatible avec Google Checkstyle

1. **Télécharger le formatter:**
   ```bash
   curl -o ~/Downloads/eclipse-java-google-style.xml https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml
   ```

2. **Eclipse > Preferences > Java > Code Style > Formatter**

3. **Cliquer sur "Import..."**

4. **Sélectionner `eclipse-java-google-style.xml`**

5. **Active profile: GoogleStyle**

6. **Apply and Close**

### Auto-format sur Save

1. **Eclipse > Preferences > Java > Editor > Save Actions**

2. **Cocher "Perform the selected actions on save"**

3. **Cocher "Format source code"**

4. **Apply and Close**

---

##  Configuration Lombok

### Installation Lombok dans Eclipse

1. **Télécharger Lombok**
   ```bash
   curl -o ~/Downloads/lombok.jar https://projectlombok.org/downloads/lombok.jar
   ```

2. **Lancer l'installateur Lombok**
   ```bash
   java -jar ~/Downloads/lombok.jar
   ```

3. **L'installateur va détecter Eclipse automatiquement**
   - Sélectionner votre Eclipse
   - Cliquer sur **Install/Update**
   - Cliquer sur **Quit Installer**

4. **Redémarrer Eclipse**

### Vérification Lombok

1. **Ouvrir un fichier avec `@Data`, `@Builder`, etc.**

2. **Vérifier qu'il n'y a pas d'erreurs de compilation**

3. **Tester l'autocomplétion sur les getters/setters générés**

---

##  Lancement de l'Application

### Méthode 1: Via Eclipse (Recommandé pour Dev)

1. **Localiser `ReadmeGeneratorApplication.java`**
   - `src/main/java/com/readme/ReadmeGeneratorApplication.java`

2. **Clic droit sur le fichier**

3. **Run As > Java Application**

4. **Vérifier la Console:**
   ```
   Started ReadmeGeneratorApplication in X.XXX seconds
   ```

5. **Ouvrir le navigateur:**
   - URL: `http://localhost:8080`

### Méthode 2: Via Maven (Terminal)

```bash
# Depuis la racine du projet
cd readme-generator-pro

# Lancer l'application
mvn spring-boot:run

# L'application démarre sur http://localhost:8080
```

### Méthode 3: Via JAR Exécutable

```bash
# Build du JAR
mvn clean package

# Lancer le JAR
java -jar target/readme-generator-1.0.0-SNAPSHOT.jar
```

### Vérification du Lancement

1. **Console Eclipse/Terminal:**
   ```
   Tomcat started on port(s): 8080 (http)
   Started ReadmeGeneratorApplication
   ```

2. **Browser:**
   - Ouvrir `http://localhost:8080`
   - La page d'accueil doit s'afficher

3. **Actuator Health:**
   - URL: `http://localhost:8080/actuator/health`
   - Réponse: `{"status":"UP"}`

---

##  Exécution des Tests JUnit

### Via Eclipse

#### Lancer Tous les Tests

1. **Clic droit sur `src/test/java`**

2. **Run As > JUnit Test**

3. **Voir les résultats dans la vue JUnit**

#### Lancer un Test Spécifique

1. **Ouvrir un fichier de test (ex: `BadgeGeneratorTest.java`)**

2. **Clic droit dans l'éditeur**

3. **Run As > JUnit Test**

#### Lancer une Méthode de Test

1. **Cliquer sur une méthode `@Test`**

2. **Clic droit > Run As > JUnit Test**

### Via Maven (Terminal)

```bash
# Lancer tous les tests
mvn test

# Lancer un test spécifique
mvn test -Dtest=BadgeGeneratorTest

# Lancer une méthode spécifique
mvn test -Dtest=BadgeGeneratorTest#testGenerateStarsBadge

# Tests avec couverture de code (JaCoCo)
mvn clean test jacoco:report
```

### Consulter le Rapport de Couverture JaCoCo

```bash
# Après mvn test jacoco:report
open target/site/jacoco/index.html
```

Le rapport montre:
- % de lignes couvertes
- % de branches couvertes
- Code non testé en rouge

### Configuration de JUnit dans Eclipse

1. **Window > Preferences > Java > JUnit**

2. **Configurer les paramètres par défaut**

---

##  Génération de la Javadoc

### Via Maven (Recommandé)

```bash
# Générer la Javadoc
mvn javadoc:javadoc

# La Javadoc est générée dans: target/site/apidocs/
open target/site/apidocs/index.html
```

### Via Eclipse

1. **Project > Generate Javadoc...**

2. **Configuration:**
   - Javadoc command: `/usr/local/opt/openjdk@17/bin/javadoc`
   - Sélectionner le projet `readme-generator`
   - Destination: `target/site/apidocs`

3. **Next > Next > Finish**

4. **Vérifier:**
   ```bash
   open target/site/apidocs/index.html
   ```

### Personnalisation de la Javadoc

Ajouter dans `pom.xml` (déjà configuré):

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <configuration>
        <show>private</show>
        <nohelp>true</nohelp>
        <additionalOptions>
            <additionalOption>-Xdoclint:none</additionalOption>
        </additionalOptions>
    </configuration>
</plugin>
```

### Package Javadoc pour Distribution

```bash
# Créer le JAR Javadoc
mvn javadoc:jar

# Le JAR est dans: target/readme-generator-1.0.0-SNAPSHOT-javadoc.jar
```

---

##  Build et Déploiement

### Build Complet avec Vérifications

```bash
# Clean + Compile + Test + Package
mvn clean install

# Vérifier Checkstyle
mvn checkstyle:check

# Build avec skip tests (plus rapide)
mvn clean package -DskipTests
```

### Fichiers Générés

Après `mvn clean install`:

```
target/
├── readme-generator-1.0.0-SNAPSHOT.jar            # JAR exécutable
├── readme-generator-1.0.0-SNAPSHOT-javadoc.jar    # Javadoc
├── classes/                                       # Fichiers compilés
├── test-classes/                                  # Tests compilés
├── site/
│   ├── apidocs/                                   # Javadoc HTML
│   └── jacoco/                                    # Rapport couverture
└── checkstyle-result.xml                          # Rapport Checkstyle
```

### Créer un JAR Exécutable

Le JAR est déjà configuré comme exécutable Spring Boot:

```bash
java -jar target/readme-generator-1.0.0-SNAPSHOT.jar
```

### Déploiement en Production

#### Option 1: Heroku

```bash
# Créer Procfile
echo "web: java -jar target/readme-generator-1.0.0-SNAPSHOT.jar" > Procfile

# Déployer
heroku create readme-generator-app
git push heroku main
```

#### Option 2: Docker

```bash
# Créer Dockerfile
cat > Dockerfile << 'EOF'
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/readme-generator-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
EOF

# Build image
docker build -t readme-generator .

# Run container
docker run -p 8080:8080 readme-generator
```

#### Option 3: Serveur Linux (VPS)

```bash
# Build du JAR
mvn clean package

# Upload vers serveur
scp target/readme-generator-1.0.0-SNAPSHOT.jar user@server:/opt/readme/

# Sur le serveur
ssh user@server
cd /opt/readme
nohup java -jar readme-generator-1.0.0-SNAPSHOT.jar &
```

---

##  Troubleshooting

### Problème 1: Lombok ne fonctionne pas

**Symptômes:**
- Erreurs "cannot find symbol" sur getters/setters
- Annotations Lombok en rouge

**Solutions:**

1. **Vérifier l'installation Lombok:**
   ```bash
   grep -i lombok /Applications/Eclipse.app/Contents/Eclipse/eclipse.ini
   ```
   Doit contenir: `-javaagent:lombok.jar`

2. **Réinstaller Lombok:**
   ```bash
   java -jar ~/Downloads/lombok.jar
   ```

3. **Clean + Rebuild:**
   ```bash
   mvn clean install
   ```

### Problème 2: Tests ne se lancent pas

**Symptômes:**
- "No tests found"
- JUnit not in classpath

**Solutions:**

1. **Update Maven Project:**
   - Clic droit projet > Maven > Update Project

2. **Vérifier Build Path:**
   - Clic droit projet > Build Path > Configure Build Path
   - Onglet Libraries: JUnit 5 doit être présent

3. **Forcer téléchargement dépendances:**
   ```bash
   mvn dependency:purge-local-repository
   mvn clean install
   ```

### Problème 3: Checkstyle erreurs

**Symptômes:**
- Trop de violations Checkstyle
- Build fail sur checkstyle:check

**Solutions:**

1. **Auto-format tout le code:**
   - Sélectionner `src/main/java`
   - Clic droit > Source > Format

2. **Désactiver temporairement:**
   Dans `pom.xml`:
   ```xml
   <failsOnError>false</failsOnError>
   ```

3. **Fix violations courantes:**
   ```bash
   # Trailing whitespace
   find src -name "*.java" -exec sed -i '' 's/[[:space:]]*$//' {} +
   
   # Imports
   # Eclipse: Ctrl+Shift+O
   ```

### Problème 4: Port 8080 déjà utilisé

**Symptômes:**
- "Port 8080 is already in use"

**Solutions:**

1. **Trouver le processus:**
   ```bash
   lsof -i :8080
   ```

2. **Tuer le processus:**
   ```bash
   kill -9 <PID>
   ```

3. **Changer le port dans `application.properties`:**
   ```properties
   server.port=8081
   ```

### Problème 5: Maven dependency errors

**Solutions:**

1. **Force update:**
   ```bash
   mvn clean install -U
   ```

2. **Vider le cache Maven:**
   ```bash
   rm -rf ~/.m2/repository/com/readme
   mvn clean install
   ```

3. **Vérifier proxy/réseau:**
   ```bash
   mvn dependency:resolve
   ```

---

##  Commandes Maven Utiles

```bash
# Build rapide (skip tests)
mvn clean package -DskipTests

# Tests uniquement
mvn test

# Tests avec couverture
mvn clean test jacoco:report

# Checkstyle
mvn checkstyle:check

# Javadoc
mvn javadoc:javadoc

# Build complet
mvn clean install

# Arbre de dépendances
mvn dependency:tree

# Vérifier updates
mvn versions:display-dependency-updates

# Run application
mvn spring-boot:run
```

---

##  Best Practices

### Développement

1. **Toujours formatter avant commit:**
   - Eclipse: Ctrl+Shift+F (Cmd+Shift+F sur Mac)

2. **Lancer les tests régulièrement:**
   ```bash
   mvn test
   ```

3. **Vérifier Checkstyle:**
   ```bash
   mvn checkstyle:check
   ```

4. **Documenter le code:**
   - Ajouter Javadoc sur toutes les classes/méthodes publiques

### Git Workflow

```bash
# Branch pour feature
git checkout -b feature/nouvelle-fonctionnalite

# Commit réguliers
git add .
git commit -m "feat: ajout de la fonctionnalité X"

# Test avant push
mvn clean install

# Push
git push origin feature/nouvelle-fonctionnalite
```

---

##  Support

Pour toute question:
- Issues GitHub: [repo]/issues
- Email: support@readmegenerator.com
- Documentation: https://docs.readmegenerator.com

---

**Projet prêt à déployer !**
