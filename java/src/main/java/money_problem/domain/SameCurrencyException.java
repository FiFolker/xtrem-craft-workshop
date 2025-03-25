package money_problem.domain;

public class SameCurrencyException extends Exception {
    public SameCurrencyException(Currency start, Currency end) {
        super(String.format("Impossible de convertir deux monnaies identiques %s->%s", start, end));
    }
}
