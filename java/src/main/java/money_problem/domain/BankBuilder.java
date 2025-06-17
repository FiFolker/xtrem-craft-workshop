package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public class BankBuilder{
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

    public Bank build(){
        Currency currency = this.exchangeRates.keySet().iterator().next();
        Bank bank = Bank.withExchangeRate(this.pivotCurrency, currency, this.exchangeRates.get(currency));
        for (Map.Entry<Currency, Double> entry : exchangeRates.entrySet()) {
            if(entry.getKey() != currency){
                bank.addExchangeRate(this.pivotCurrency, entry.getKey(), entry.getValue());
            }
        }
        return bank;
    }
}
