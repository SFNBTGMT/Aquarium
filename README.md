# Aquarium Project

## Description

Ce projet est une simulation d'un aquarium en Java. Il permet de visualiser des poissons, des pastilles comestibles, des insectes et des décorations dans un environnement interactif. Les utilisateurs peuvent interagir avec l'aquarium en utilisant des commandes clavier pour changer la température, ajouter des éléments, ou réinitialiser l'aquarium.
![Image](https://github.com/user-attachments/assets/67992d79-5926-41e6-a96b-4d911f602ed4)

## l'enonce du projet
[Projet_Java_III_2223_-v1.pdf](https://github.com/user-attachments/files/19398579/Projet_Java_III_2223_-v1.pdf)

## Screencast du projet
![Image](https://github.com/user-attachments/assets/70b5bf4c-bac8-4500-a3f3-19cf467faa91)

## Fonctionnalités

- **Poissons** : Quatre types de poissons (orange, mauve, bleu, rouge) se déplacent dans l'aquarium.
- **Pastilles comestibles** : Les poissons peuvent interagir avec des pastilles comestibles pour augmenter leur score.
- **Insectes** : Les insectes ajoutent du réalisme à l'aquarium.
- **Décorations** : Des éléments décoratifs sont placés dans l'aquarium.
- **Commandes clavier** :
  - `0` : Réinitialiser l'aquarium.
  - `1, 2, 3` : Changer la température (froid, tiède, chaud).
  - `4` : Ajouter un insecte.
  - `5` : Ajouter une pastille comestible.
  - `9` : Ajouter un poisson aléatoire.

## Prérequis

- **Java Development Kit (JDK)** : Version 11 ou supérieure.
- **Bash** : Pour exécuter le script `run.sh` sous Linux.
  ```bash
  #!/bin/bash
  SRC_DIR="src"
  BIN_DIR="bin"
  # Vérifier si le répertoire bin existe, sinon le créer
  if [ ! -d "$BIN_DIR" ]; then
  mkdir -p "$BIN_DIR"
  fi
  # Compiler tous les fichiers Java dans src et placer les .class dans bin
  javac -d "$BIN_DIR" "$SRC_DIR"/*.java
  # Vérifier si la compilation a réussi
  if [ $? -eq 0 ]; then
  echo "Compilation réussie."

  # Exécuter l'application
  java -cp "$BIN_DIR" Main
  else
  echo "Error."
  fi

## Installation

1. Clonez le dépôt GitHub :

   ```bash
   git clone https://github.com/SFNBTGMT/Aquarium.git
   cd Aquarium
   
