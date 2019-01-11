package mdp;

public class QField {
    private Action[] actions;
    private double[] values;

    public QField() {
        this.actions = new Action[]{Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT};
        this.values = new double[actions.length];

    }

    public Action getHighestAction() {
        int index = Utilities.getHighestIndex(values);
        return actions[index];
    }

    public void updateValue(Action action, double reward, QField next) {
        int index = getActionIndex(action);
        double nextHighestAction = next.getValue(next.getHighestAction());
        values[index] = values[index] + QLearning.LEARN_RATE * (reward + QLearning.DISCOUNT * nextHighestAction - values[index]);
    }

    private int getActionIndex(Action action) {
        switch (action){
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

    public double getValue(Action action){
        if (action == Action.UP){
            return values[0];
        }
        if (action == Action.DOWN){
            return values[1];
        }
        if (action == Action.LEFT){
            return values[2];
        }
        else{
            return values[3];
        }
    }
}
