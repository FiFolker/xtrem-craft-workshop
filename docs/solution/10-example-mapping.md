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

## Story 1: Define Pivot Currency

```gherkin
As a Foreign Exchange Expert
I want to be able to define a Pivot Currency
So that I can express exchange rates based on it
```

## Story 2: Add an exchange rate
```gherkin
As a Foreign Exchange Expert
I want to add/update exchange rates by specifying: a multiplier rate and a currency
So they can be used to evaluate client portfolios
```

## Story 3: Convert a Money

```gherkin
As a Bank Consumer
I want to convert a given amount in currency into another currency
So it can be used to evaluate client portfolios
```

## Bank can convert to same currency without exchange rate
```gherkin
Given a bank with pivot currency EUR
And 10 EUR
when I convert to EUR
Then I get 10 EUR
```

## Bank can convert from pivot to other currency
```gherkin
Given a bank with pivot currency EUR
And an exchange rate : USD, 1.2
And 10 EUR
when I convert to EUR
Then I get 12 USD
```

## Bank can convert from currency to pivot
```gherkin
Given a bank with pivot currency EUR
And an exchange rate : USD, 1.2
And 12 USD
when I convert to EUR
Then I get 10 EUR
```

## Bank can convert from one currency to other currency
```gherkin
Given a bank with pivot currency EUR
And an exchange rate : USD, 1.2
And an exchange rate : KRW, 1344
And 12 USD
When I convert to KRW
Then I get 1344 KRW
```

## Bank can limit when the result is under one cent
```gherkin
Given a bank with pivot currency EUR
And an exchange rate : KRW, 1344
And 1 KRW
When I convert to EUR
Error because the minimum is one cent EUR and the conversion is 0.00063 KRW
```

## Bank can round the result if it has more than 2 number after the comma
```gherkin
Given a bank with pivot currency EUR
And an exchange rate : USD, 1.2
And 123.9 EUR
When I convert to USD
Then I get 101.58 EUR
```

## Bank can do round-tripping with KRW and USD
```gherkin
Given a bank with pivot currency EUR
And an exchange rate : USD, 1.2
And an exchange rate : KRW, 1344
And 5 000 000 KRW
When I convert to USD
Then I get 4464.27 USD
When I convert to KRW
Then I get 4 999 982 KRW and the margin of error is 2% of the exchange rate
```

## Bank can do round-tripping with KRW and EUR
```gherkin
Given a bank with pivot currency EUR
And an exchange rate : KRW, 1344
And 12.68 EUR
When I convert to KRW
Then I get 17283 KRW
When I convert to EUR
Then I get 12.85 and the margin of error is 2% of the exchange rate
```


