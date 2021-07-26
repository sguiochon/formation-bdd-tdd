# Formation TDD-BDD

## Objectifs

1. Implémenter le code lié à la feature [Virement](src/test/resources/Features/Virement.feature) en appliquant la double boucle 
BDD-TDD dans le fichier [`VirementCommand`](src/main/java/fr/axa/formation/domain/virements/VirementCommand.java).
2. Refactorer les scénarios pour utiliser un scénario outline

## Comment démarrer

**Prérequis**

- [Java 8](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)
- [IntelliJ](https://www.jetbrains.com/idea/download) (la version Community suffit)

**Configuration IntelliJ**
Si vous êtes derrière un proxy, il faut le configurer au niveau des préférences IntelliJ : `File > Settings`. Il faut avoir un système de proxy local type `px`. Dans l'exemple ci après, on utilise px avec une configuration par défaut (localhost:5000/127.0.0.1:5000).
Dans le menu : `Build,Execution, Deployment > Build Tools > Maven > Importing`, définissez les paramètres de proxy dans l'option `VM options for importer` à l'aide de : `-DproxySet=true -DproxyHost=127.0.0.1 -DproxyPort=5000`
Dans le menu : `Build,Execution, Deployment > Build Tools > Maven > Runner`, définissez les paramètres de proxy dans l'option `VM options` à l'aide de : `-DproxySet=true -DproxyHost=127.0.0.1 -DproxyPort=5000`

**Plugins IntelliJ**
Pour la pleine intégration avec IntelliJ, les plugins suivants sont important :
- Cucumber jor Java (by JetBrains)
- Gherkin (by JetBrains)

**Démarrer le projet**
- Avec IntelliJ, ouvrir le dossier `Java`
- Lancer [`FormationApplication`](src/main/java/fr/axa/formation/FormationApplication.java) (clic droit > Run 'FormationApplication')
- Le site se lance sur l'URL : `http://localhost:8080` vous pouvez utiliser Postman pour y accéder
- Console H2 (base de donnée embarquée) : `http://localhost:8080/h2-console` 
    - Driver Class : `org.h2.Driver` 
    - JDBC URL : `jdbc:h2:mem:banque`
    - User Name : `sa`

**Lancer les tests**

- Lancer la commande `mvn test` ou lancer les tests avec IntelliJ (clic droit sur /test/java > 'Run All Tests')

### Lister les comptes

```
GET http://localhost:8080/api/comptes
```

**Response**
```
{
    "status": "SUCCESS",
    "message": null,
    "data": [
        {
            "id": "0000145577",
            "label": "Compte de chèque",
            "montant": 900.00,
            "operations": [
                {
                    "id": "1",
                    "label": "Ouverture compte",
                    "montant": 900.00,
                    "date": "2020-02-17T15:47:52.690"
                }
            ]
        },
        {
            "id": "0000770286",
            "label": "Livret A",
            "montant": 12556.31,
            "operations": [
                {
                    "id": "2",
                    "label": "Ouverture compte",
                    "montant": 12556.31,
                    "date": "2019-12-17T15:47:52.690"
                }
            ]
        },
        {
            "id": "0000933153",
            "label": "Livret de développement durable et solidaire (LDDS)",
            "montant": 12000.00,
            "operations": [
                {
                    "id": "3",
                    "label": "Ouverture compte",
                    "montant": 12000.00,
                    "date": "2020-02-17T15:47:52.690"
                }
            ]
        }
    ]
}
```

### Créer un virement

```
POST http://localhost:8080/api/virements
```

**Body**
```
{
    "depuisCompte": "0000145577",
    "versCompte": "0000770286",
    "montant": 100
}
```

Réponse
```
{
    "status": "SUCCESS",
    "message": null,
    "data": "CONFIRME"
}
```


## Librairies utilisées

- [AssertJ](https://assertj.github.io/doc/)
- [Cucumber](https://cucumber.io/docs/installation/java/)
- [jqwik](https://jqwik.net/)
- [JUnit](https://junit.org/junit5/docs/current/user-guide/)
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
