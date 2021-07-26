package fr.axa.formation.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CurrencyUtils")
class CurrencyUtilsTest {

    @DisplayName("toUnit: doit retourner null quand input null")
    @Test
    void toUnit_shouldReturnNull_whenInputNull() {
        // Arrange
        final BigInteger from = null;

        // Act
        final BigDecimal result = CurrencyUtils.toUnit(from);

        // Assert
        assertThat(result).isNull();
    }

    @DisplayName("toUnit: doit convertir une valeur en centimes en décimal")
    @Test
    void toUnit_shouldConvertCentsToUnit() {
        // Arrange
        final BigInteger from = new BigInteger("10000");

        // Act
        final BigDecimal result = CurrencyUtils.toUnit(from);

        // Assert
        assertThat(result).isEqualTo(new BigDecimal("100.00"));
    }

    @DisplayName("toCents: doit retourner null quand input null")
    @Test
    void toCents_shouldReturnNull_whenInputNull() {
        // Arrange
        final BigDecimal from = null;

        // Act
        final BigInteger result = CurrencyUtils.toCents(from);

        // Assert
        assertThat(result).isNull();
    }

    @DisplayName("toCents: doit convertir valeur décimale en centimes")
    @Test
    void toCents_shoudlConvertUnitToCents() {
        // Arrange
        final BigDecimal from = new BigDecimal("100.00");

        // Act
        final BigInteger result = CurrencyUtils.toCents(from);

        // Assert
        assertThat(result).isEqualTo(new BigInteger("10000"));
    }
}
