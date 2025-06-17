package money_problem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static money_problem.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;




public class PortFolioTest {

    @Test
    void shouldAddOneValueInSameCurency() throws MissingExchangeRateException{
                
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);

        // Arange
        // Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        Bank bank = BankBuilder.abank().withPivot(Currency.EUR).withExchangeRate(1.2, Currency.USD).build();
        
        // Act
        // Money evaluation = portfolio.evaluate(bank, Currency.USD);
        Money evaluation = portfolio.evaluate(bank, Currency.USD);
        
        //Assert
        // assertEquals(evaluation, new Money(5, USD));
        assertEquals(evaluation, new Money(5, USD));
    }

    @Test
    void shouldAddInSameCurency() throws MissingExchangeRateException{
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.USD);

        // Arange
        // Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        Bank bank = BankBuilder.abank().withPivot(Currency.EUR).withExchangeRate(1.2, Currency.USD).build();
        
        // Act
        // Money evaluation = portfolio.evaluate(bank, Currency.USD);
        Money evaluation = portfolio.evaluate(bank, Currency.USD);
        
        //Assert
        // assertEquals(evaluation, new Money(15, USD));
        assertEquals(evaluation, new Money(15, USD));
    }

    @Test
    // add 5 usd + 10 EUR qhould be 17 USD
    void shouldAddInUsd() throws MissingExchangeRateException{

        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.EUR);

        // Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        Bank bank = BankBuilder.abank().withPivot(Currency.EUR).withExchangeRate(1.2, Currency.USD).build();


        // Money evaluation = portfolio.evaluate(bank, Currency.USD);
        Money evaluation = portfolio.evaluate(bank, Currency.USD);

        // assertEquals(evaluation, new Money(17, USD));
        assertEquals(evaluation, new Money(17, USD));
    }

    @Test
    void shouldThrowErrorWhenNotExistingCurrency() throws MissingExchangeRateException{
        //Arrange
        // Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        Bank bank = BankBuilder.abank().withPivot(Currency.EUR).withExchangeRate(1.2, Currency.USD).build();

        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.KRW);

        //Assert
        // assertThatThrownBy(() -> portfolio.evaluate(bank, Currency.USD)).isInstanceOf(MissingExchangeRateException.class);
        assertThatThrownBy(() -> portfolio.evaluate(bank, Currency.USD)).isInstanceOf(MissingExchangeRateException.class);
    }
}