package money_problem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static money_problem.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BankBuilder{
    private Currency pivotCurrency;
    private final Map<Currency, Double> exchangeRates = new HashMap<>();

    public static BankBuilder abank() {
        return new BankBuilder();
    }

    public BankBuilder withPivot(Currency pivotCurrency) {
        this.pivotCurrency = pivotCurrency;
        return this;
    }

    public BankBuilder withExchangeRate(double rate, Currency targetCurrency) {
        this.exchangeRates.put(targetCurrency, rate);
        return this;
    }

    public build(){
        Currency currency = this.exchangeRates.keys()[0];
        Bank bank = Bank.withExchangeRate(this.pivotCurrency, currency, this.exchangeRates.get(currency));
        for (Map.Entry<Currency, Double> entry : exchangeRates.entrySet()) {
            if(entry.getKey() != currency){
                bank.addExchangeRate(this.pivotCurrency, entry.getKey(), entry.getValue());
            }
        }
        return bank;
    }
}


public class PortFolioTest {

    @Test
    void shouldAddOneValueInSameCurency() throws MissingExchangeRateException{
        
        // Arange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);

        Bank bank2 = BankBuilder.abank().withPivot(Currency.EUR).withExchangeRate(1.2, Currency.USD).build();
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        
        // Act
        Money evaluation = portfolio.evaluate(bank, Currency.USD);
        Money evaluation2 = portfolio.evaluate(bank2, Currency.USD)
        
        //Assert
        assertEquals(evaluation, new Money(5, USD));
        assertEquals(evaluation2, new Money(5, USD));
    }

    @Test
    void shouldAddInSameCurency() throws MissingExchangeRateException{
        
        // Arange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.USD);
        
        // Act
        Money evaluation = portfolio.evaluate(bank, Currency.USD);
        
        //Assert
        assertEquals(evaluation, new Money(15, USD));
    }

    @Test
    // add 5 usd + 10 EUR qhould be 17 USD
    void shouldAddInUsd() throws MissingExchangeRateException{
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.EUR);

        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);


        Money evaluation = portfolio.evaluate(bank, Currency.USD);

        assertEquals(evaluation, new Money(17, USD));       
    }

    @Test
    void shouldThrowErrorWhenNotExistingCurrency() throws MissingExchangeRateException{
        //Arrange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);

        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.KRW);

        //Assert
        assertThatThrownBy(() -> portfolio.evaluate(bank, Currency.USD)).isInstanceOf(MissingExchangeRateException.class);
    }
}