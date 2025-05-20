package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class Bank {
    private final Map<String, Double> EXCHANGE_RATES;
    private final String EXCHANGE_SYMBOL = "->";  

    private Bank(Map<String, Double> exchangeRates) {
        this.EXCHANGE_RATES = exchangeRates;
    }

    public static Bank withExchangeRate(Currency start, Currency end, double rate) {
        var bank = new Bank(new HashMap<>());
        
        bank.addExchangeRate(start, end, rate);

        return bank;
    }

    public void addExchangeRate(Currency start, Currency end, double rate) {
        EXCHANGE_RATES.put(start + EXCHANGE_SYMBOL + end, rate);
    }

    public double convert(double amount, Currency from, Currency to) throws MissingExchangeRateException{
        if(from == to){
            return amount;
        } else if (!(EXCHANGE_RATES.containsKey(from + EXCHANGE_SYMBOL + to))) {
            throw new MissingExchangeRateException(from, to);
        }
        return amount * EXCHANGE_RATES.get(from + EXCHANGE_SYMBOL + to);
    }

    public Money convert(Money money, Currency to) throws MissingExchangeRateException{
        return new Money(this.convert(money.amount(), money.currency(), to), to);
    }

}