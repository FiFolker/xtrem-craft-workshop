package money_problem.domain;

import java.util.ArrayList;

class PortFolio{
    private ArrayList<Money> moneys = new ArrayList<>();

    void add(double amount, Currency currency) {
        // Implementation for adding money to the portfolio

        moneys.add(new Money(amount, currency));
    }

    Money evaluate(Bank bank, Currency currency) throws MissingExchangeRateException{
        // Implementation for evaluating the portfolio

        Money totalAmountConverted = new Money(0, currency);

        for (Money money : moneys) {
            Money newAmount = bank.convert(money, currency);
            totalAmountConverted = totalAmountConverted.add(newAmount);
        }
        
        return totalAmountConverted;
    }

}
