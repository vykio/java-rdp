# java-rdp

<p align="center">
	Implémentation d'un simulateur de réseaux de Pétri en Java en utilisant Swing principalement
	<br><br>
  <img src="https://i.imgur.com/Sbbn9KK.png">
</p>

## Comment on organise les branches ?

<p align="center">
  <img src="https://i.imgur.com/0eeVHRb.png" />
</p>

## Calendrier organisation 

<ul>
	<li> Semaine 07 (fin février) : réunion 1 pour la définition du projet, </li>
	<li>Semaine 10 (début mars) : réunion 2 avec remise du cahier des charges et avancement,</li>
	<li>Semaine 13 (début avril) : réunion 3 (état d’avancement),</li>
	<li>Semaine 16 (fin avril) : réunion 4 (état d’avancement),</li>
	<li>Semaine 19 (début mai) : remise des livrables,</li>
	<li>Semaine 20 (fin mai) : soutenance du projet.</li>
</ul>

## Comment utiliser le répertoire ?

- IDE : Intellij IDEA (Ultimate ou Community) de préférence!
- Version JAVA du projet : 11

Il faut <span style="font-weight: bold">TOUJOURS</span> travailler sur la branche <span style="font-weight: bold">develop</span> !!

Si vous travaillez sur une feature importante, veillez à créer une branche "<< Nom feature >>" depuis la branche "develop". 
Toutes les modifications de la feature devront être réalisées sur la branche "<< Nom feature >>".

Lorsque vous avez terminé de coder votre feature, vous devrez créer une Pull Request (de la branche Feature à la branche develop), celle-ci sera validée
si tout est bon et si tout fonctionne.

## Et la branche main dans tout ça ?

A la fin de la création de chaque feature importante ajoutée et fonctionnelle, nous ferons un check-up du code
et des fonctionnalités de la branche develop, en veillant à ce qu'il y ait le moins de bug possibles.
Puis nous ferons une pull-request de la branche develop vers la branche main.

La branche main représente un livrable fonctionnel avec le moins de bug possible. C'est une sorte de release.

