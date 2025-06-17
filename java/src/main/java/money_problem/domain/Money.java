package money_problem.domain;

public record Money (double amount, Currency currency){
    Money add(Money money){
        double newA = this.amount();
        newA += money.amount();
        return new Money(newA, currency);
    }
}
