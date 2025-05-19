# Library_final – Manuel d’utilisation

## 1. Prérequis

- **Java JDK 17** ou supérieur
- **MySQL**  pour la base de données
- **IDE recommandé** : NetBeans, IntelliJ IDEA, ou Visual Studio Code
- **JavaFX** (inclus dans ZuluFX ou OpenJFX)
- **Librairies externes (JAR) à ajouter au classpath** :
  - JDBC MySQL (ex: `mysql-connector-java-x.x.x.jar`) pour la connection à la bd
  - Jakarta Mail (ex: `jakarta.mail-x.x.x.jar`) pour l'envoie des mail
  - Jakarta BCrypt (ex: `jakarta.bcrypt-x.x.x.jar`) pour hasher le mot de passe

**Où trouver ces JAR ?**
- Dans le dossier ziper `fichier jar.zip` à la racine du projet .
- Place-les dans un dossier `lib/` à la racine du projet.

---

## 2. Installation

### a. Cloner ou copier le projet

Place le dossier du projet sur ta machine, par exemple :
```
C:\Users\TSEFACK\Documents\NetBeansProjects
```

### b. Ajouter les librairies JAR

1. Crée un dossier `lib` à la racine du projet si besoin.
2. Place tous les fichiers `.jar` nécessaires dans ce dossier.
3. **Dans NetBeans** : clic droit sur le projet > Propriétés > Bibliothèques > Ajouter JAR/Dossier > sélectionne tous les JAR du dossier `lib`.
4. **En ligne de commande** : ajoute `-cp "lib/*"` à tes commandes `javac` et `java`.

### c. Préparer la base de données

1. Ouvre MySQL et exécute le script :
   - `src/library_final/config/library_final.sql`
2. Vérifie/modifie la configuration de connexion dans :
   - `src/library_final/config/config.properties`
   - Exemple :
     ```
     db.url=jdbc:mysql://localhost:3306/library
     db.user=VOTRE_UTILISATEUR 
     db.password=VOTRE_MOT_DE_PASSE
     ```

### d. Configuration email (pour la réinitialisation de mot de passe)

Dans `src/library_final/config/config.properties`, configure :
```
mail.username=VOTRE_EMAIL
mail.password=VOTRE_MDP
mail.smtp.host=smtp.votre-fournisseur.com
mail.smtp.port=587
mail.smtp.auth=true
mail.smtp.starttls.enable=true
```

---

## 3. Compilation et exécution

- **en interface graphique** : clic droit sur le fichier Library_final.java > Run.
- **En console** : dans le dossier `test` faite un clique droit sur `GeneralTest.java`

---

## 4. Fonctionnalités principales

- **Authentification** : connexion via email/mot de passe (voir table `User`).
- **Gestion des livres** : ajouter, modifier, suppression logique (statut `Inactive`).
- **Gestion des exemplaires** : ajouter, modifier, suppression logique.
- **Gestion des emprunts** : ajouter un prêt, enregistrer un retour, statistiques.
- **Réinitialisation du mot de passe** : envoi d’un code par email.

---

## 5. Structure du projet

- `src/library_final/model/entity/` : Entités métiers (Book, Copy, Loan, User, etc.)
- `src/library_final/model/DAO/` : Accès aux données (CRUD)
- `src/library_final/controller/` : Contrôleurs JavaFX
- `src/library_final/view/` : Fichiers FXML (interfaces graphiques)
- `src/library_final/config/` : Fichiers de configuration et SQL
- `src/library_final/Mail/` : Gestion de l’envoi d’emails
- `test/` : Classes de test

---

## 6. Tests unitaires

- Exécute les classes de test dans le dossier `test/`.
- Exemple :
  ```sh
  java -cp "build/classes;build/test/classes;lib/*" BookDAOTest
  ```

---

## 7. Conseils

- **Connecter vous avec les informations** : email `klein@gmail.com` et motDePasse `23721`.


---

## 8. Problèmes fréquents

- **Erreur de connexion BDD** : vérifie `config.properties` et la présence du JAR JDBC.
- **Erreur JavaFX** : vérifie que JavaFX est bien dans le classpath.
- **Erreur d’email** : vérifie la configuration SMTP et le JAR Jakarta Mail.

---
