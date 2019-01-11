package mdp;

/**
 * A class representing a state and all possible actions in that state pair.
 * Contains the update of the value formula.
 *
 * @author David Leeftink and Mantas Makelis
 */
public class QField {

    private Action[] actions;
    private double[] values;

    /**
     * Constructor of the state-actions representation.
     */
    public QField() {
        this.actions = new Action[]{Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT};
        this.values = new double[actions.length];

    }

    /**
     * Getter for the highest valued action in the state.
     *
     * @return most valuable action (policy).
     */
    public Action getHighestAction() {
        int index = Utilities.getHighestIndex(values);
        return actions[index];
    }

    /**
     * Updates the value of the action that was performed in this state (previous state).
     *
     * @param action the action taken in this state (previous state).
     * @param reward the reward received after taken action.
     * @param next the resulting state after performed action (current state).
     */
    public void updateValue(Action action, double reward, QField next) {
        int index = getActionIndex(action);
        double nextHighestAction = next.getValue(next.getHighestAction());
        values[index] = values[index] + QLearning.LEARN_RATE * (reward + QLearning.DISCOUNT * nextHighestAction - values[index]);
    }

    /**
     * Getter for index of an action.
     *
     * @param action the action of which index is to be retrieved.
     * @return integer index.
     */
    private int getActionIndex(Action action) {
        switch (action) {
            case UP:
                return 0;
            case DOWN:
                return 1;
            case LEFT:
                return 2;
            case RIGHT:
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Getter for the value of an action.
     *
     * @param action the action for which the value to be return.
     * @return double of the value.
     */
    public double getValue(Action action) {
        if (action == Action.UP) {
            return values[0];
        }
        if (action == Action.DOWN) {
            return values[1];
        }
        if (action == Action.LEFT) {
            return values[2];
        } else {
            return values[3];
        }
    }
}
