package money_problem.domain;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.KRW;
import static money_problem.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import org.junit.jupiter.api.Test;

class BankTest {

    @Test
    void convert_currencies() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);

        double amount = bank.convert(10, EUR, USD);

        assertThat(amount).isEqualTo(12);
    }

    @Test
    void NewConvert_currencies() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);

        Money amount = bank.convert(new Money(10, EUR), USD);

        assertThat(amount).isEqualTo(new Money(12, USD));
    }


    @Test
    void convert_same_currency() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        
        double amount = bank.convert(10, EUR, EUR);

        assertThat(amount).isEqualTo(10);
    }

    @Test
    void NewConvert_same_currency() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        
        Money amount = bank.convert(new Money(10, EUR), EUR);

        assertThat(amount).isEqualTo(new Money(10, EUR));
    }

    @Test
    void convert_throws_exception_on_missing_exchange_rate() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        assertThatThrownBy(() -> bank.convert(10, EUR, KRW)).hasMessage("EUR->KRW");
    }

    @Test
    void NewConvert_throws_exception_on_missing_exchange_rate() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        assertThatThrownBy(() -> bank.convert(new Money(10, EUR), KRW)).hasMessage("EUR->KRW");
    }

    @Test
    void convert_with_different_exchange_rates_returns_different_floats() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);

        double amount1 = bank.convert(10, EUR, USD);
        
        assertThat(amount1).isEqualTo(12);
        

        Bank bank2 = Bank.withExchangeRate(EUR, USD, 1.3);

        double amount2 = bank2.convert(10, EUR, USD);

        assertThat(amount2).isEqualTo(13);
    }

    @Test
    void NewConvert_with_different_exchange_rates_returns_different_floats() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);

        Money amount1 = bank.convert(new Money(10, EUR), USD);
        
        assertThat(amount1.amount()).isEqualTo(12);

        Bank bank2 = Bank.withExchangeRate(EUR, USD, 1.3);

        Money amount2 = bank2.convert(new Money(10, EUR), USD);

        assertThat(amount2.amount()).isEqualTo(13);
    }

    @Test
    void convert_with_round_tripping_and_simple_rate() throws MissingExchangeRateException {
        Bank bank = Bank.withExchangeRate(EUR, USD, 2);

        double amount = bank.convert(10, EUR, USD);
        assertThat(amount).isBetween((double) 20 - (2 * 2 / 100), (double) 20 + (2 * 2 / 100));

        amount = bank.convert(20, USD, EUR);
        assertThat(amount).isBetween((double) 10 - (2 * 2 / 100), (double) 10 + (2 * 2 / 100));
    }

    @Test
    void convert_with_round_tripping_and_complex_rate() throws MissingExchangeRateException { 
        Bank bank = Bank.withExchangeRate(EUR, KRW, 1344);

        double amount = bank.convert(100, EUR, KRW);
        assertThat(amount).isBetween((double) 134400 - (2 * 1344 / 100), (double) 134400 + (2 * 1344 / 100));

        amount = bank.convert(134400, KRW, EUR);
        assertThat(amount).isBetween((double) 100 - (2 * 1344 / 100), (double) 100 + (2 * 1344 / 100));
    }

    @Test
    void test_round_when_convert() throws MissingExchangeRateException { 
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.5);

        double amount = bank.convert(123.93, EUR, USD);
        assertThat(amount).isBetween(185.895 - (2 * 1.5 / 100), 185.895 + (2 * 1.5 / 100));
        assertThat(amount).isEqualTo(185.88);
    }

}