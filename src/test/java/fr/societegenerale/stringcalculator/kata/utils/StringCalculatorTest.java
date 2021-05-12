package fr.societegenerale.stringcalculator.kata.utils;

import fr.societegenerale.stringcalculator.kata.utils.exception.StringCalculatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * class StringCalculatorTest.
 *
 * @author awi
 * @since 12 mai. 2021 - 00:36
 */
class StringCalculatorTest {

    /**
     * Method should_return_zero_empty_string.
     * Step 1: Handle zero on empty string.
     *
     * @author awi
     * @since 12/05/2021 - 00:54
     */
    @Test
    void should_return_zero_empty_string() throws StringCalculatorException {
        assertEquals(0, StringCalculator.add(""));
    }

    /**
     * Method should_return_result_on_unknown_amount_of_numbers.
     * Step 2: Handle an unknown amount of numbers.
     *
     * @throws StringCalculatorException
     * @author awi
     * @since 12/05/2021 - 04:07
     */
    @Test
    void should_return_result_on_unknown_amount_of_numbers() throws StringCalculatorException {
        assertEquals(3, StringCalculator.add("1,2"));
    }

    /**
     * Method should_handle_new_line_between_numbers.
     * Step 3 : Handle new line between numbers.
     *
     * @throws StringCalculatorException
     * @author awi
     * @since 12/05/2021 - 04:15
     */
    @Test
    void should_handle_new_line_between_numbers() throws StringCalculatorException {
        assertEquals(6, StringCalculator.add("1\n2,3"));
    }

    /**
     * Method should_handle_optional_delimiter_specification.
     * Step 4 : Support optional delimiter.
     *
     * @throws StringCalculatorException
     * @author awi
     * @since 12/05/2021 - 04:17
     */
    @Test
    void should_support_optional_delimiter_specification() throws StringCalculatorException {
        assertEquals(3, StringCalculator.add("//;\n1;2"));
    }

    /**
     * Method should_throw_exception_on_forcing_delimiter_without_respecting_pattern.
     * Step 4 : Throw exception when not respecting pattern with delimiter.
     *
     * @throws StringCalculatorException
     * @author awi
     * @since 12/05/2021 - 04:17
     */
    @Test
    void should_throw_exception_on_forcing_delimiter_without_respecting_pattern() throws StringCalculatorException {
        // given
        Exception exception = assertThrows(StringCalculatorException.class, () -> {
            StringCalculator.add("//;1;2");
        });
        // assertion
        String expected = "the input starts with delimiter // but does not respect the standard pattern '//(.)\n(.*)'";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Method should_throw_exception_on_negative_numbers.
     * Step 5: Handle exception on negative numbers.
     *
     * @throws StringCalculatorException
     * @author awi
     * @since 12/05/2021 - 04:40
     */
    @Test
    void should_throw_exception_on_negative_numbers() throws StringCalculatorException {
        // given
        Exception exception = assertThrows(StringCalculatorException.class, () -> {
            StringCalculator.add("-1,-2");
        });
        // assertion
        String expected = "negatives not allowed -1,-2";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

}