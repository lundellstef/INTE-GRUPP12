import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestThing {

    @ParameterizedTest
    @CsvSource(value = {"1, 33.8", "5.5, 41.9", "0, 32"})
    void test1(double celsiusTemp, double farenheitTemp) {
        Thing celsiusConverter = new Thing();
        double temperature = celsiusConverter.convertCelsiusToFahrenheit(celsiusTemp);
        assertEquals(farenheitTemp, temperature);
    }

    @Test
    void test2() {
        Thing t = new Thing();
        double fahr = t.convertCelsiusToFahrenheit(32);
        assertEquals(fahr, 89.6);
    }

    @Test
    void ThisTestWillNOTFail() {
        Thing t = new Thing();
        double fahr = t.convertCelsiusToFahrenheit(32);
        assertEquals(fahr, 89.6);
    }
}
