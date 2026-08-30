import java.util.ArrayList;

// Represents a deterministic finite automaton (DFA), including its
// states, accepting states, transitions, alphabet, and start state.
public class FiniteAutomata {

    // The single state where the DFA begins processing input.
    private String startState;

    // Stores all accepting (final) states for the DFA.
    private ArrayList<String> acceptStates;

    // Stores every state found in the DFA.
    private ArrayList<String> states;

    // Stores all transition rules used by the DFA.
    private ArrayList<Transition> transitions;

    // Stores each unique symbol found in the DFA transitions.
    private ArrayList<Character> alphabet;

    // Creates an empty DFA that will be populated from an input file.
    public FiniteAutomata() {
        acceptStates = new ArrayList<>();
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        alphabet = new ArrayList<>();
    }

    // Sets the DFA's start state after it is read from the input file.
    public void setStartState(String startState) {
        this.startState = startState;
        addState(startState);
    }

    // Adds an accepting (final) state to the DFA.
    public void addAcceptState(String state) {
        acceptStates.add(state);
        addState(state);
    }

    // Adds a state to the DFA if it has not already been recorded.
    public void addState(String state) {
        if (!states.contains(state)) {
            states.add(state);
        }
    }

    // Returns all unique alphabet symbols used by the DFA.
    public ArrayList<Character> alphabet() {
        return alphabet;
    }

    // Adds an alphabet symbol if it has not already been recorded.
    public void addAlphabetSymbol(char symbol) {
        if (!alphabet.contains(symbol)) {
            alphabet.add(symbol);
        }
    }

    // Adds a transition and records any new states or alphabet symbol it contains.
    public void addTransition(Transition transition) {
        transitions.add(transition);

        addState(transition.getFromState());
        addState(transition.getToState());
        addAlphabetSymbol(transition.getLabel());
    }

    // Returns all states currently defined in the DFA.
    public ArrayList<String> states() {
        return states;
    }

    // Big-O: O(n * t), where n is the length of the input string
    // and t is the number of transitions. For each input symbol,
    // the method may scan the full transition list.
    public boolean run(String input) {
        String currentState = startState;

        // Processes the input string one symbol at a time.
        for (int i = 0; i < input.length(); i++) {
            char symbol = input.charAt(i);
            boolean transitionFound = false;

            // Searches for the transition that matches the current state
            // and current input symbol.
            for (Transition transition : transitions) {
                if (transition.getFromState().equals(currentState)
                        && transition.getLabel() == symbol) {

                    currentState = transition.getToState();
                    transitionFound = true;
                    break;
                }
            }

            // Rejects the input if no valid transition exists.
            if (!transitionFound) {
                return false;
            }
        }

        // Accepts the input only if processing ends in an accepting state.
        return acceptStates.contains(currentState);
    }
}
