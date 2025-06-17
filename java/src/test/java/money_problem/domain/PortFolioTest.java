package money_problem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class PortFolio{

    private double amount;
    private HashMap<Currency, Double> amounts = new HashMap<>();

    private Money money;
    private HashMap<Currency, Double> moneys = new HashMap<>();

    void add(double amount, Currency currency) {
        // Implementation for adding money to the portfolio
        this.amount += amount;
        if(this.amounts.get(currency) == null){
            this.amounts.put(currency, amount);
        }else{
            this.amounts.put(currency, this.amounts.get(currency) + amount);
        }
    }

    double evaluate(Bank bank, Currency currency) throws MissingExchangeRateException{
        // Implementation for evaluating the portfolio

        double totalAmountConverted = 0;

        for (Map.Entry<Currency, Double> entry : this.amounts.entrySet()) {
                System.out.println(entry.getValue().toString() + " " +  entry.getKey().toString() + " " + currency.toString());
                double newAmount = bank.convert(entry.getValue(), entry.getKey(), currency);
                System.out.println(newAmount);
                totalAmountConverted += newAmount;
        }
        
        return totalAmountConverted;
    }

    Money newEvaluate(Bank bank, Currency currency) throws MissingExchangeRateException{
        double totalAmountConverted = 0;

        for (Map.Entry<Currency, Double> entry : this.moneys.entrySet()) {
                System.out.println(entry.getValue().toString() + " " +  entry.getKey().toString() + " " + currency.toString());
                Money newAmount = bank.convert(new Money(entry.getValue(), entry.getKey()), currency);
                System.out.println(newAmount.amount());
                totalAmountConverted += newAmount.amount();
        }
        
        return totalAmountConverted;
    }
}

public class PortFolioTest {

    @Test
    void shouldAddOneValueInSameCurency() throws MissingExchangeRateException{
        
        // Arange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        
        // Act
        double evaluation = portfolio.newEvaluate(bank, Currency.USD);
        
        //Assert
        assertEquals(evaluation, 5);
    }

    @Test
    void shouldAddInSameCurency() throws MissingExchangeRateException{
        
        // Arange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.USD);
        
        // Act
        double evaluation = portfolio.newEvaluate(bank, Currency.USD);
        
        //Assert
        assertEquals(evaluation, 15);
    }

    @Test
    // add 5 usd + 10 EUR qhould be 17 USD
    void shouldAddInUsd() throws MissingExchangeRateException{
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.EUR);

        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);


        double evaluation = portfolio.newEvaluate(bank, Currency.USD);

        assertEquals(evaluation, 17);       
    }

    @Test
    void shouldThrowErrorWhenNotExistingCurrency() throws MissingExchangeRateException{
        //Arrange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);

        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.KRW);

        //Assert
        assertThatThrownBy(() -> portfolio.newEvaluate(bank, Currency.USD)).isInstanceOf(MissingExchangeRateException.class);
    }
}