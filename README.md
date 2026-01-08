# README Generator - Professional Edition

<div align="center">

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.1-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)

**Générez des README.md professionnels pour GitHub en quelques secondes**

[Installation](#installation) • [Guide Complet](SETUP_GUIDE.md) • [Documentation](#documentation) • [Tests](#tests)

</div>

---

##  À propos

**README Generator** est une application Spring Boot qui permet de créer des fichiers README.md bien structurés pour vos projets GitHub en quelques clics.

###  Fonctionnalités

-  **5 Templates prédéfinis** - API, Library, Frontend, CLI, Fullstack
-  **Génération automatique de badges** - GitHub stats, technologies, license
-  **Sections personnalisables** - Features, installation, usage, etc.
-  **Export direct vers GitHub** - Via GitHub API
-  **Téléchargement** - README.md prêt à utiliser
-  **Interface moderne** - Design inspiré des éditeurs de code
-  **Code professionnel** - Tests JUnit, Checkstyle Google, Javadoc

---

##  Structure du Projet

```
readme-generator/
├── src/
│   ├── main/
│   │   ├── java/com/readme/
│   │   │   ├── controller/       # Contrôleurs REST et Web
│   │   │   ├── service/          # Logique métier
│   │   │   ├── dto/              # Objets de transfert
│   │   │   ├── model/            # Modèles de données
│   │   │   ├── exception/        # Exceptions personnalisées
│   │   │   ├── util/             # Classes utilitaires
│   │   │   └── ReadmeGeneratorApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/        # Templates Thymeleaf
│   │       └── static/           # CSS, JS, images
│   └── test/
│       └── java/com/readme/      # Tests JUnit
├── pom.xml                       # Configuration Maven
├── SETUP_GUIDE.md               # Guide complet d'installation
└── README.md                     # Ce fichier
```

---

##  Technologies

### Backend
- **Java 17** - Langage de programmation objet
- **Spring Boot 3.2.1** - Framework web
- **Maven** - Gestion des dépendances du projet
- **GitHub API (kohsuke)** - Intégration GitHub
- **CommonMark** - Parser Markdown

### Frontend
- **Thymeleaf** - Moteur de templates
- **HTML5 / CSS3** - Interface utilisateur
- **JavaScript (Vanilla)** - Interactions client

### Qualité du Code
- **JUnit 5** - Tests unitaires
- **Mockito** - Mocking pour tests
- **AssertJ** - Assertions fluides
- **Checkstyle** - Google Java Style Guide
- **JaCoCo** - Couverture de code
- **Lombok** - Réduction du boilerplate

---

##  Installation

### Prérequis

- Java JDK 17+
- Maven 3.6+
- Eclipse IDE (2023-12 ou plus récent)

### Installation Rapide

```bash
# Cloner le repository
git clone https://github.com/votre-username/readme-generator.git
cd readme-generator

# Build avec Maven
mvn clean install

# Lancer l'application
mvn spring-boot:run

# Accéder à l'interface
open http://localhost:8080
```

### Installation Complète Eclipse

**Voir le guide détaillé : [SETUP_GUIDE.md](SETUP_GUIDE.md)**

Le guide couvre:
-  Configuration Eclipse sur macBook Pro Intel
-  Installation et configuration de Lombok
-  Configuration de Checkstyle Google
-  Exécution des tests JUnit
-  Génération de la Javadoc
-  Build et déploiement
-  Troubleshooting complet

---

##  Utilisation

### Interface Web

1. **Accéder à l'application**
   ```
   http://localhost:8080
   ```

2. **Remplir le formulaire**
   - Nom du projet
   - Description
   - Type de template
   - Technologies utilisées
   - Features, installation, usage...

3. **Générer le README**
   - Cliquer sur "Generate README"
   - Visualiser en temps réel

4. **Exporter**
   - Copier le contenu
   - Télécharger le fichier
   - Ou exporter directement vers GitHub

### API REST

#### Générer un README

```bash
POST /api/generate
Content-Type: application/json

{
  "projectName": "Mon Projet",
  "description": "Description du projet",
  "templateType": "API",
  "technologies": ["Java", "Spring Boot"],
  "features": "Feature 1\nFeature 2",
  "installation": "mvn install",
  "usage": "java -jar app.jar",
  "includeBadges": true,
  "includeContributing": true,
  "includeLicense": true
}
```

#### Exporter vers GitHub

```bash
POST /api/export
Content-Type: application/json

{
  "repositoryUrl": "https://github.com/user/repo",
  "readmeContent": "# README content...",
  "githubToken": "ghp_xxxxxxxxxxxxx"
}
```

---

##  Tests

### Exécuter tous les tests

```bash
mvn test
```

### Tests avec couverture

```bash
mvn clean test jacoco:report
open target/site/jacoco/index.html
```

### Tests dans Eclipse

1. Clic droit sur `src/test/java`
2. Run As > JUnit Test

### Classes de Test

- `BadgeGeneratorTest` - Tests génération de badges
- `MarkdownFormatterTest` - Tests formatage Markdown
- `ReadmeGeneratorServiceTest` - Tests logique métier

**Couverture actuelle: ~85%**

---

##  Documentation

### Génération de la Javadoc

```bash
# Via Maven
mvn javadoc:javadoc
open target/site/apidocs/index.html

# Via Eclipse
Project > Generate Javadoc...
```

### Checkstyle

```bash
# Vérifier les violations
mvn checkstyle:check

# Générer un rapport
mvn checkstyle:checkstyle
open target/checkstyle-result.xml
```

### Architecture

#### Couche Controller
- `ReadmeApiController` - Endpoints REST API
- `MainController` - Pages web

#### Couche Service
- `ReadmeGeneratorService` - Génération de README
- `GitHubService` - Intégration GitHub API

#### Couche Util
- `BadgeGenerator` - Création de badges shields.io
- `MarkdownFormatter` - Formatage Markdown

#### Modèles
- `TemplateType` - Types de templates (enum)
- `ReadmeRequest` - DTO requête
- `ReadmeResponse` - DTO réponse

---

##  Configuration

### application.properties

```properties
# Server
server.port=8080

# GitHub (optionnel)
github.token=votre_token_ici

# Logging
logging.level.com.readme=INFO

# Actuator
management.endpoints.web.exposure.include=health,info
```

### Variables d'Environnement

```bash
# Token GitHub (optionnel)
export GITHUB_TOKEN=ghp_xxxxxxxxxxxxx

# Port personnalisé
export SERVER_PORT=8081
```

---

##  Métriques de Qualité

| Métrique | Valeur |
|----------|--------|
| Couverture de code | 85%+ |
| Tests unitaires | 30+ |
| Classes Java | 16 |
| Lignes de code | ~2000 |
| Violations Checkstyle | 0 |
| Javadoc complète | OK |

---

##  Contribution

Les contributions sont les bienvenues !

1. Fork le projet
2. Créer une branche (`git checkout -b feature/MonFeature`)
3. Commit (`git commit -m 'Add MonFeature'`)
4. Push (`git push origin feature/MonFeature`)
5. Ouvrir une Pull Request

### Guidelines

- Respecter Google Java Style Guide
- Ajouter des tests pour les nouvelles fonctionnalités
- Documenter avec Javadoc
- Vérifier Checkstyle avant commit

---

##  License

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

##  Roadmap

- [ ] Support de templates supplémentaires
- [ ] Éditeur Markdown WYSIWYG
- [ ] Sauvegarde de templates personnalisés
- [ ] Support multi-langues
- [ ] CLI pour génération en ligne de commande
- [ ] Plugin VS Code
- [ ] Export vers GitLab et Bitbucket

---

##  Support

- **Issues GitHub**: [Ouvrir une issue](https://github.com/user/readme-generator/issues)
- **Email**: sanndobatengue@gmail.com
- **Documentation**: [SETUP_GUIDE.md](SETUP_GUIDE.md)

---

<div align="center">

**Made by Sanndo BATENGUE**

⭐ Star ce repo si vous le trouvez utile !

[Documentation](SETUP_GUIDE.md) • [Report Bug](https://github.com/user/readme-generator/issues) • [Request Feature](https://github.com/user/readme-generator/issues)

</div>
