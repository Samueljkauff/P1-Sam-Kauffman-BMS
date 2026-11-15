
package com.sam.bank.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.sam.bank.validation.AccountValidator;

import static org.junit.jupiter.api.Assertions.*;

class AccountValidatorTest {

    @Test
    void validPasswordReturnsTrue() {
        assertTrue(AccountValidator.verifyPasswordValid(
            "PiggyWig.123", "PiggyWig.123"
        ));
    }

    @Test
    void mismatchedPasswordsReturnFalse() {
        assertFalse(AccountValidator.verifyPasswordValid(
            "PiggyWig.123", "PiggyWig.124"
        ));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "PiggyWi.",       // Missing digit
        "piggywig.123",    // Missing uppercase
        "PIGGYWIG.123",    // Missing lowercase
        "PiggyWig.abc",    // Missing digit
        "PiggyWig123",     // Missing special character
        "Pigg.1",          // Too short
        "Piggy Wig.123",   // Contains space
        "PiggyWig.123 "    // Trailing space
    })
    void invalidPasswordsReturnFalse(String password) {
    boolean result =
        AccountValidator.verifyPasswordValid(password, password);

    assertFalse(result, "Unexpectedly accepted password: " + password);

    }

    @Test
    void emptyPasswordReturnsFalse() {
        assertFalse(AccountValidator.verifyPasswordValid("", ""));
    }

    @Test
    void passwordWithDifferentCaseReturnsFalse() {
        assertFalse(AccountValidator.verifyPasswordValid(
            "PiggyWig.123", "piggywig.123"
        ));
    }

    @Test
    void validPhoneReturnsTrue() {
        assertTrue(AccountValidator.verifyPhone("5022911856"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "502291185",       // Too short
        "50229118567",     // Too long
        "50229abc56",      // Contains letters
        "502-291-1856",    // Contains hyphens
        "502 291 1856",    // Contains spaces
        "502.291.1856",    // Contains periods
        "",                // Empty
        "abcdefghij"       // All letters
    })
    void invalidPhoneReturnsFalse(String phone) {
        assertFalse(AccountValidator.verifyPhone(phone));
    }

}
