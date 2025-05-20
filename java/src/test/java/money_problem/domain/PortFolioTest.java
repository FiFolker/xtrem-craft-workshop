package money_problem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class PortFolio{

    private double amount;
    private HashMap<Currency, Double> amounts = new HashMap<>();

    void add(double amount, Currency currency) {
        // Implementation for adding money to the portfolio
        this.amount += amount;
        if(this.amounts.get(currency) == null){
            this.amounts.put(currency, amount);
        }else{
            this.amounts.put(currency, this.amounts.get(currency) + amount);
        }
        

    }

    double evaluate(Bank bank, Currency currency){
        // Implementation for evaluating the portfolio

        double totalAmountConverted = 0;

        for (Map.Entry<Currency, Double> entry : this.amounts.entrySet()) {
            try {
                double newAmount = bank.convert(entry.getValue(), entry.getKey(), currency);
                totalAmountConverted += newAmount;
            } catch (MissingExchangeRateException e) {
                e.printStackTrace();
            }
        }

        return totalAmountConverted;
    }
}

public class PortFolioTest {

    @Test
    void shouldAddOneValueInSameCurency() {
        
        // Arange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        
        // Act
        double evaluation = portfolio.evaluate(bank, Currency.USD);
        
        //Assert
        assertEquals(evaluation, 5);
    }

    @Test
    void shouldAddInSameCurency() {
        
        // Arange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.USD);
        
        // Act
        double evaluation = portfolio.evaluate(bank, Currency.USD);
        
        //Assert
        assertEquals(evaluation, 15);
    }

    @Test
    // add 5 usd + 10 EUR qhould be 17 USD
    void shouldAddInUsd() {
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.EUR);

        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);


        double evaluation = portfolio.evaluate(bank, Currency.USD);

        assertEquals(evaluation, 17);       
    }

    @Test
    void shouldThrowErrorWhenNotExistingCurrency(){
        //Arrange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);

        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.KRW);

        //Act
        double evaluation = portfolio.evaluate(bank, Currency.KRW);

        //Assert
        assertThatThrownBy(() -> portfolio.evaluate(bank, Currency.KRW)).isInstanceOf(MissingExchangeRateException.class);
    }
}