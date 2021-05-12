package fr.societegenerale.stringcalculator.kata.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.societegenerale.stringcalculator.kata.utils.exception.StringCalculatorException;

/**
 * class StringCalculator.
 *
 * @author awi
 * @since 12 mai. 2021 - 00:24
 */
public class StringCalculator {

    private static final String COMMA = ",";
    private static final String REGEX_OR_OPERATOR = "|";
    private static final String NEW_LINE_CHARACTER = "\n";
    public static final String SPECIFICATION_REGEX = "//(.)\n(.*)";
    public static final String PREFIX_DELIMITER = "//";

    /**
     * Method add.
     *
     * @param raw
     * @return
     * @author awi
     * @since 12/05/2021 - 00:26
     */
    static int add(String raw) throws StringCalculatorException {
        if (raw.isEmpty()) {
            return 0;
        } else {
            String[] numbers = normalizeRaw(raw);
            // check if input contains not allowed numbers
            String[] negativeNumbers = getNegativeNumbers(numbers);
            if (negativeNumbers.length > 0) {
                // input contains negative numbers which are not allowed
                throw new StringCalculatorException("negatives not allowed " + Arrays.stream(negativeNumbers).collect(Collectors.joining(COMMA)));
            } else {
                // calculate sum
                return Arrays.stream(numbers).mapToInt(Integer::parseInt).sum();
            }
        }
    }

    /**
     * Method getNegativeNumbers.
     *
     * @param numbers
     * @return
     * @author awi
     * @since 12/05/2021 - 06:10
     */
    private static String[] getNegativeNumbers(String[] numbers) {
        // format double in order to have a clean output log message
        NumberFormat formatter = new DecimalFormat();
        String[] negativeNumbers = Arrays.stream(numbers)
                .mapToDouble(Double::parseDouble)
                .filter(i -> i < 0)
                .mapToObj(i -> formatter.format(i))
                .toArray(String[]::new);
        return negativeNumbers;
    }

    /**
     * Method normalizeRaw.
     *
     * @param input
     * @return
     * @throws StringCalculatorException
     * @author awi
     * @since 12/05/2021 - 05:06
     */
    private static String[] normalizeRaw(String input) throws StringCalculatorException {
        if (input.startsWith(PREFIX_DELIMITER)) {
            // normalize using a specific character
            Matcher matcher = Pattern.compile(SPECIFICATION_REGEX).matcher(input);
            if (matcher.matches()) {
                String specification = matcher.group(1);
                String numbers = matcher.group(2);
                return numbers.split(specification);
            } else {
                // raw data starts with delimiter but does not respect pattern
                throw new StringCalculatorException("the input starts with delimiter // but does not respect the standard pattern '" + SPECIFICATION_REGEX + "'");
            }
        } else {
            // default normalizing using comma or new line delimiters
            return input.split(COMMA + REGEX_OR_OPERATOR + NEW_LINE_CHARACTER);
        }
    }
}
