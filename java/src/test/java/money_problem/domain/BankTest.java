package money_problem.domain;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.KRW;
import static money_problem.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BankTest {

    @Test
    void convert_currencies() throws MissingExchangeRateException, SameCurrencyException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);

        double amount = bank.convert(10, EUR, USD);

        assertThat(amount).isEqualTo(12);
    }

    @Test
    void convert_same_currency() throws MissingExchangeRateException, SameCurrencyException {
        assertThatThrownBy(() -> Bank.withExchangeRate(EUR, USD, 1.2).convert(10, EUR, EUR))
                .isInstanceOf(SameCurrencyException.class)
                .hasMessage("Impossible de convertir deux monnaies identiques EUR->");
    }

    @Test
    void convert_throws_exception_on_missing_exchange_rate() {
        assertThatThrownBy(() -> Bank.withExchangeRate(EUR, USD, 1.2).convert(10, EUR, KRW))
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    @Test
    void convert_with_different_exchange_rates_returns_different_floats() throws MissingExchangeRateException, SameCurrencyException {
        assertThat(Bank.withExchangeRate(EUR, USD, 1.2).convert(10, EUR, USD))
                .isEqualTo(12);

        assertThat(Bank.withExchangeRate(EUR, USD, 1.3).convert(10, EUR, USD))
                .isEqualTo(13);
    }
}