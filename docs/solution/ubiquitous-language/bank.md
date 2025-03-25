# Concept

> Définition : Convertir des monnaies entre 2 devises différentes avec un taux de change.

## Properties

Une map entre exchangeRates entre le un string qui signifie la devise de départe et celle d'arrivé associé au taux de change

-

## Responsibilities

Ca doit retourner les valeurs demandées après avoir changer la monnaie dans une autre devise.

-

## Invariants

Il faut forcément un taux de change sinon on renvoie un erreur.
Un invariant pour ne pas pouvoir changer une devise dans la même devise

-

## Collaborators

MissingExchangeRateException pour lever une exception si il manque un taux de change.

-
