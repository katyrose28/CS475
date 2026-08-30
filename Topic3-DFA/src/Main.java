import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// Handles file selection, DFA file input, user input, and program output.
public class Main {

    // Reads a DFA definition from the selected input file.
    //
    // Big-O: O(t^2) in the worst case, where t is the number of transitions.
    // Each transition is read once. Adding states and alphabet symbols uses
    // ArrayList.contains(), which performs a linear search, making the overall
    // worst-case running time quadratic as the DFA grows.
    public static FiniteAutomata fileInput(File file) throws FileNotFoundException {

        FiniteAutomata dfa = new FiniteAutomata();

        // Opens the selected file for reading.
        try (Scanner scanner = new Scanner(file)) {

            // The first line of the file contains the DFA's start state.
            if (scanner.hasNextLine()) {
                String startState = scanner.nextLine().trim();
                dfa.setStartState(startState);
            }

            // The second line contains one or more accepting states separated by spaces.
            if (scanner.hasNextLine()) {
                String acceptLine = scanner.nextLine().trim();

                if (!acceptLine.isEmpty()) {
                    String[] acceptStateNames = acceptLine.split("\\s+");

                    for (String state : acceptStateNames) {
                        dfa.addAcceptState(state);
                    }
                }
            }

            // Each remaining line defines one transition:
            // fromState label toState
            while (scanner.hasNextLine()) {
                String transitionLine = scanner.nextLine().trim();

                if (!transitionLine.isEmpty()) {
                    String[] parts = transitionLine.split("\\s+");

                    String fromState = parts[0];
                    char label = parts[1].charAt(0);
                    String toState = parts[2];

                    Transition transition =
                            new Transition(fromState, label, toState);

                    dfa.addTransition(transition);
                }
            }
        }

        return dfa;
    }

    // Starts the program and allows the user to select and test a DFA.
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        // Opens the file chooser in the project's root-level input directory.
        JFileChooser fileChooser = new JFileChooser(new File("input"));

        int choice = fileChooser.showOpenDialog(frame);

        // Ends the program if the user cancels the file selection.
        if (choice != JFileChooser.APPROVE_OPTION) {
            frame.dispose();
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();

        try {
            FiniteAutomata dfa = fileInput(selectedFile);

            // Displays the alphabet associated with the selected DFA.
            JOptionPane.showMessageDialog(
                    frame,
                    "DFA Alphabet: " + dfa.alphabet());

            // Prompts the user for a string to test.
            String testString = JOptionPane.showInputDialog(
                    frame,
                    "Enter a string to test");

            // Ends the program if the user cancels the input dialog.
            if (testString == null) {
                frame.dispose();
                return;
            }

            // Runs the DFA using the user's input string.
            boolean accepted = dfa.run(testString);

            // Displays whether the DFA accepted or rejected the input.
            if (accepted) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Yes, your input string is accepted by the DFA.");
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Your input string is not accepted by this DFA.");
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "The selected file could not be found.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        frame.dispose();
    }
}
