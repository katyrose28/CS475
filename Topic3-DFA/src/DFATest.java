import java.io.File;
import java.io.FileNotFoundException;

// Simple test harness used to verify the DFA implementation
// against multiple DFA definitions and input strings.
public class DFATest {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("          DFA AUTOMATED TESTS");
        System.out.println("========================================");

        // Tests the DFA provided in the assignment directions.
        runTests(
                "input/assignmentDFA.txt",
                new String[] {
                    "a",
                    "b",
                    "ab",
                    "aba",
                    "aa",
                    "bb"
                },
                new boolean[] {
                    true,
                    false,
                    false,
                    true,
                    true,
                    false
                });

        // Tests a DFA that accepts strings containing
        // an even number of 1s.
        runTests(
                "input/evenOnesDFA.txt",
                new String[] {
                    "",
                    "0",
                    "1",
                    "11",
                    "101",
                    "111",
                    "1011"
                },
                new boolean[] {
                    true,
                    true,
                    false,
                    true,
                    true,
                    false,
                    false
                });

        // Tests a DFA with a three-symbol alphabet and
        // multiple accepting states. It accepts strings
        // ending in either a or c.
        runTests(
                "input/endingDFA.txt",
                new String[] {
                    "",
                    "a",
                    "b",
                    "c",
                    "abc",
                    "abba",
                    "abbb",
                    "cccb"
                },
                new boolean[] {
                    false,
                    true,
                    false,
                    true,
                    true,
                    true,
                    false,
                    false
                });

        System.out.println();
        System.out.println("========================================");
        System.out.println("             TESTING COMPLETE");
        System.out.println("========================================");
    }

    // Loads one DFA and compares its actual result against the
    // expected result for each supplied test string.
    private static void runTests(
            String fileName,
            String[] testStrings,
            boolean[] expectedResults) {

        System.out.println();
        System.out.println("Testing: " + fileName);
        System.out.println("----------------------------------------");

        try {
            FiniteAutomata dfa = Main.fileInput(new File(fileName));

            System.out.println("States:   " + dfa.states());
            System.out.println("Alphabet: " + dfa.alphabet());
            System.out.println();

            for (int i = 0; i < testStrings.length; i++) {

                String input = testStrings[i];
                boolean expected = expectedResults[i];
                boolean actual = dfa.run(input);

                String result;

                if (actual == expected) {
                    result = "PASS";
                } else {
                    result = "FAIL";
                }

                // Displays <empty> so an empty-string test
                // is easy to recognize in the output.
                String displayInput =
                        input.isEmpty() ? "<empty>" : input;

                System.out.printf(
                        "%-10s Expected: %-5s Actual: %-5s %s%n",
                        displayInput,
                        expected,
                        actual,
                        result);
            }

        } catch (FileNotFoundException e) {
            System.out.println(
                    "ERROR: Could not find test file: " + fileName);
        }
    }
}
