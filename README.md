# Projet_Banque
FISA TI 2026 CI2 - Architecture logicielles et éco-conceptions

Une application bancaire développée en Java, mettant en œuvre l’architecture hexagonale pour une séparation claire entre la logique métier et les couches techniques.

Fonctionnalités
	•	Gestion des comptes : création, consultation, suppression.
	•	Opérations bancaires : dépôts, retraits, virements.
	•	Historique des transactions : suivi des opérations effectuées.
	•	Architecture hexagonale : séparation des responsabilités entre le domaine métier et les interfaces externes.

Architecture

Le projet suit l’architecture hexagonale (ou architecture en oignon), qui permet une meilleure maintenabilité et testabilité du code. Les composants principaux sont :
	•	Domaine : contient la logique métier et les entités principales.
	•	Ports : interfaces définissant les points d’entrée et de sortie du domaine.
	•	Adaptateurs : implémentations concrètes des ports, telles que les interfaces utilisateur ou les bases de données.

Technologies utilisées
	•	Java 17 : langage principal du projet.
	•	Maven : gestionnaire de dépendances et outil de build.
	•	JUnit 5 : framework de tests unitaires.
	•	Lombok : réduction du code boilerplate.
	•	MapStruct : mapping entre obje