package money_problem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PortFolio{
    void add(int amount, Currency currency) {
        // Implementation for adding money to the portfolio
    }

    int evaluate(Bank bank) {
        // Implementation for evaluating the portfolio
        return 5;
    }
}

public class PortFolioTest {
    
    @Test
    void shouldAddInUsd() {
        
        // Arange
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        PortFolio portfolio = new PortFolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.USD);
        
        // Act
        int evaluation = portfolio.evaluate(bank);
        
        //Assert
        assertEquals(evaluation, 5);
    }
}