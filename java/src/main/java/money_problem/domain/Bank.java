package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class Bank {
    private Currency pivotCurrency;
    
    private final Map<String, Double> EXCHANGE_RATES;
    private final Map<Currency, Double> NEW_EXCHANGE_RATES = new HashMap<Currency,Double>();
    private final String EXCHANGE_SYMBOL = "->";  

    private Bank(Currency pivotCurrency, Map<String, Double> exchangeRates) {
        this.pivotCurrency = pivotCurrency;
        this.EXCHANGE_RATES = exchangeRates;
    }

    public static Bank withExchangeRate(Currency start, Currency end, double rate) {
        Bank bank = new Bank(start, new HashMap<>());

        bank.addExchangeRate(end, rate);
        return bank;
    }

    public void addExchangeRate(Currency currency, double rate) {
        EXCHANGE_RATES.put(this.pivotCurrency + EXCHANGE_SYMBOL + currency, rate);
        EXCHANGE_RATES.put(currency + EXCHANGE_SYMBOL + this.pivotCurrency, (1 / rate));

        NEW_EXCHANGE_RATES.put(currency, rate);
    }

    public double convert(double amount, Currency from, Currency to) throws MissingExchangeRateException{
        double result;
        if(from == to){
            return amount;
        } else if (!(EXCHANGE_RATES.containsKey(from + EXCHANGE_SYMBOL + to))) {
            throw new MissingExchangeRateException(from, to);
        }
        
        if (from == this.pivotCurrency){
            result = amount * NEW_EXCHANGE_RATES.get(to);
        }
        else if(to == this.pivotCurrency){
            result = amount * (1 / NEW_EXCHANGE_RATES.get(to));
        }
        else{
            result = amount * EXCHANGE_RATES.get(from + EXCHANGE_SYMBOL + to);
        }
        
        if (result % 1 != 0){
            result = (Math.floor(result * 100) / 100) - 0.01; // Round down to two decimal places
        }
        return result;
    }

    public Money convert(Money money, Currency to) throws MissingExchangeRateException{
        return new Money(this.convert(money.amount(), money.currency(), to), to);
    }

}