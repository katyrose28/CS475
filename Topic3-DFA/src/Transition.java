public class Transition {

    // Stores the starting state, input symbol, and destination state
    // for a single DFA transition.
    private String fromState;
    private char label;
    private String toState;

    // Creates a transition such as: q0 --a--> q1
    public Transition(String fromState, char label, String toState) {
        this.fromState = fromState;
        this.label = label;
        this.toState = toState;
    }

    // Returns the state where this transition begins.
    public String getFromState() {
        return fromState;
    }

    // Returns the alphabet symbol that causes this transition.
    public char getLabel() {
        return label;
    }

    // Returns the state reached after taking this transition.
    public String getToState() {
        return toState;
    }
}
