# Diagnostic SOLID

Les fichiers cités se trouvent dans `src/main/java/fr/efrei/residence/`.

| Défaut | Emplacement | Principe SOLID violé |
| --- | --- | --- |
| Une seule classe gère les inscriptions, les baux, les notifications et les rapports. | `GestionResidence.java` | S |
| Ajouter un type de logement oblige à modifier les conditions du calcul du loyer. | `CalculateurLoyer.java`, méthode `calculerLoyer()` | O |
| Une colocation refuse `loger()` en lançant une exception, alors que cette méthode fonctionne pour un logement classique disponible. | `Colocation.java`, méthode `loger()` | L |
| L'interface impose les méthodes de colocation aux logements individuels qui n'en ont pas besoin. | `ILogement.java`, méthode `ajouterColocataire()` | I |
| Le service dépend directement de la classe `Database` et de son singleton. | `GestionResidence.java`, champ `db` | D |
