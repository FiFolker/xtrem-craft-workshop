package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class Bank {
    private final Map<String, Double> EXCHANGE_RATES;
    private final String EXCHANGE_SYMBOL = "->";  

    private Bank(Map<String, Double> exchangeRates) {
        this.EXCHANGE_RATES = exchangeRates;
    }

    public static Bank withExchangeRate(Currency start, Currency end, double rate)throws SameCurrencyException {
        var bank = new Bank(new HashMap<>());
        if(start == end){
            throw new SameCurrencyException(start, end);
        }
        bank.addExchangeRate(start, end, rate);

        return bank;
    }

    public void addExchangeRate(Currency start, Currency end, double rate) {
        EXCHANGE_RATES.put(start + EXCHANGE_SYMBOL + end, rate);
    }

    public double convert(double amount, Currency from, Currency to) throws MissingExchangeRateException, SameCurrencyException{
        if(from == to){
            throw new SameCurrencyException(from, to);
        }
        else if (!(EXCHANGE_RATES.containsKey(from + EXCHANGE_SYMBOL + to))) {
            throw new MissingExchangeRateException(from, to);
        }
        return from == to
                ? amount
                : amount * EXCHANGE_RATES.get(from + EXCHANGE_SYMBOL + to);
    }

}