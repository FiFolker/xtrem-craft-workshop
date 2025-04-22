# Example Mapping

## Format de restitution
*(rappel, pour chaque US)*

```markdown
## Titre de l'US (post-it jaunes)

> Question (post-it rouge)

### Règle Métier (post-it bleu)

Exemple: (post-it vert)

- [ ] 5 USD + 10 EUR = 17 USD
```

Vous pouvez également joindre une photo du résultat obtenu en utilisant les post-its.

## Évaluation d'un portefeuille

missing exchange rate => error on evaluation

```gherkin
Given a portfolio containing an amount in a currency A
  And an amount in a currency B
  And a Bank with exchange rate between currency A and currency B
When I evaluate the portfolio to the Bank in currency Z
Error
```


evaluation = sum of amounts in portfolio converted into target currency

exemple eur usd
```gherkin
Given a portfolio containing an amount in a currency A
  And an amount in a currency B
  And a Bank with exchange rate between currency A and currency B
When I evaluate the portfolio to the Bank in currency Z
Error
```

exxemple avc kwon
```gherkin
Given a portfolio containing an amount in a currency A
  And an amount in a currency B
  And a Bank with exchange rate between currency A and currency B
When I evaluate the portfolio to the Bank in currency Z
Error
```


