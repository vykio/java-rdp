# A LIRE SI VOUS COMPILEZ LE PROJET

Le dossier "used_library" contient toutes les librairies que le projet utilise.

Il se peut que dans le futur, vous devez ajouter des fonctionnalités au GMA,
et que certains modules de la librairie JGraphT doivent être utilisés.
Pour cela, nous avons décidé de garder le dossier "lib" de la librairie
JGraphT.

Pour ajouter une dépendance au projet, il vous suffit de copier un .JAR du
dossier "jgrapht-1.5.1/lib" dans le dossier "used_library" et de re-build le
projet.

Pour résumé, mettez toutes les librairies ".JAR" que vous voulez utiliser
dans le projet dans le dossier "used_library". Le dossier "jgrapht-1.5.1/lib"
contient tout simplement les .JAR de la librairie JgraphT que nous n'avons
pas utilisé et qui ne sont pas utilisées lors de la compilation.

# Pour le BUILD de l'artifact .JAR

Dans Project Structure (sous IntelliJ), vous verrez que toutes les librairies
contenues dans le dossier "used_library" seront copiés dans le dossier "lib"
du build.

Vous aurez donc une structure :
- java-rdp.jar
- lib/
	- jlatexmath.jar
	- jgrapht-core.jar
	- ...

Le META-INF/MANIFEST.MF définit le "Class-Path" comme étant "lib/*". C'est
à-dire qu'il dit au programme d'aller chercher les dépendances dans le dossier
lib à coté de l'exécutable .JAR

# APRES avoir build le .JAR

En supposant que vous avez JDK 11 et que les .JAR sont build dans le dossier
"lib/" à côté du java-rdp.jar.

- Etape 1 : Définir la liste des dépendances du .JAR résultant du Build Artifact
Lancez la commande suivante:
```
jdeps --module-path lib/ --add-modules=ALL-MODULE-PATH --list-deps java-rdp.jar
```

- Etape 2 : Créer le JRE permettant de distribuer notre application plus facilement
Lancez la commande suivante, où LISTE_PRECEDENTE est la liste des dépendances trouvées
précédemment (Etape 1) séparée d'une virgule entre chaque dépendance.
```
jlink --no-header-files --no-man-pages --add-modules LISTE_PRECEDENT --output jre_custom/
```

- Etape 3 : Lancer le programme avec le JRE personnalisé
Lancez la commande suivante:
```
jre_custom/bin/java --module-path lib/ --add-modules=ALL-MODULE-PATH -jar java-rdp.jar
```

Voilà !!

Maintenant, vous pouvez distribuer l'application !
Pour windows, il serait intéressant d'utiliser `Launch4J` permettant
de convertir un .JAR en .EXE, et veillez à donner les bons paramètres notamment
par rapport au dossier lib/ contenant toutes les dépendances.

