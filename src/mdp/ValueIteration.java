package mdp;

import java.util.ArrayList;

/**
 * @author David Leeftink and Mantas Makelis
 */
public class ValueIteration {

    private MarkovDecisionProblem mdp;
    private double[] probDistribution; // always the same format: pPerform, pSidestep/2, pSidestep/2, pBackStep, pNoStep
    private double discount;
    private double[][] valueLandscape;
    private double[][] rewardLandscape;
    private Action[][] policyLandscape;
    private int iterations;
    private int complexity;

    public ValueIteration(MarkovDecisionProblem mdp) {
        this.mdp = mdp;
        this.probDistribution = mdp.getProbs();
        this.valueLandscape = new double[mdp.getWidth()][mdp.getHeight()];
        this.rewardLandscape = createRewardLandscape();
        this.discount = 0.86;
        this.iterations = 1000;
        this.complexity = 0;
        this.policyLandscape = Calculate();
        mdp.setValueLandscape(valueLandscape);
    }

    private Action[][] Calculate() {
        Action[][] policyLandscape = new Action[mdp.getWidth()][mdp.getHeight()];
        for (int k = 0; k < iterations; k++) {
            for (int i = 0; i < mdp.getWidth(); i++) {
                for (int j = 0; j < mdp.getHeight(); j++) {
                    V(i, j);
                }
            }
        }
        for (int xpos = 0; xpos < mdp.getWidth(); xpos++) {
            for (int ypos = 0; ypos < mdp.getHeight(); ypos++) {
                Action[] moves = getPossibleActions(xpos, ypos);
                double[] values = new double[moves.length];
                for (int a = 0; a < moves.length; a++) {
                    switch (moves[a]) {
                        case UP:
                            if (ypos < (mdp.getHeight() - 1)) {
                                values[a] = valueLandscape[xpos][ypos + 1];
                            } else {
                                // out of range
                                values[a] = -1;
                            }
                            break;
                        case DOWN:
                            if (ypos > 0) {
                                values[a] = valueLandscape[xpos][ypos - 1];
                            } else {
                                // out of range
                                values[a] = -1;
                            }
                            break;
                        case LEFT:
                            if (xpos > 0) {
                                values[a] = valueLandscape[xpos - 1][ypos];
                            } else {
                                // out of range
                                values[a] = -1;
                            }
                            break;
                        case RIGHT:
                            if (xpos < mdp.getWidth() - 1) {
                                values[a] = valueLandscape[xpos + 1][ypos];
                            } else {
                                // out of range
                                values[a] = -1;
                            }
                            break;
                    }
                }
                int index = Utilities.getHighestIndex(values);
                if (moves.length != 0){
                    // no policy for obstacles
                    policyLandscape[xpos][ypos] = moves[index];
                }
            }
        }
        return policyLandscape;
    }

    private void V(int xpos, int ypos) {
        Action[] moves = getPossibleActions(xpos, ypos);
        double[] values = new double[moves.length];
        for (int i = 0; i < moves.length; i++) {
            values[i] = QValue(xpos, ypos, moves[i]);
        }
        valueLandscape[xpos][ypos] = Utilities.max(values);
    }

    private double QValue(int xpos, int ypos, Action action) {
        double expectedValue = 0;
        Action[] actions = new Action[]{action, Action.previousAction(action), Action.nextAction(action), Action.backAction(action),
            Action.NOTHING};
        for (int i = 0; i < actions.length; i++) {
            Action act = actions[i];
            switch (act) {
                case UP:
                    if (ypos < (mdp.getHeight() - 1) && mdp.getField(xpos, ypos + 1) != Field.OBSTACLE) {
                        expectedValue += probDistribution[i] * valueLandscape[xpos][ypos + 1];
                    } else if (ypos > (mdp.getHeight() - 1)) {
                        // if out of range
                        expectedValue += probDistribution[i] * -1;
                    }
                    break;
                case DOWN:
                    if (ypos > 0 && mdp.getField(xpos, ypos - 1) != Field.OBSTACLE) {
                        expectedValue += probDistribution[i] * valueLandscape[xpos][ypos - 1];
                    } else if (ypos - 1 < 0) {
                        // if out of range
                        expectedValue += probDistribution[i] * -1;
                    }
                    break;
                case LEFT:
                    if (xpos > 0 && mdp.getField(xpos - 1, ypos) != Field.OBSTACLE) {
                        expectedValue += probDistribution[i] * valueLandscape[xpos - 1][ypos];
                    } else if (xpos - 1 < 0) {
                        // if out of range
                        expectedValue += probDistribution[i] * -1;
                    }
                    break;
                case RIGHT:
                    if (xpos < mdp.getWidth() - 1 && mdp.getField(xpos + 1, ypos) != Field.OBSTACLE) {
                        expectedValue += probDistribution[i] * valueLandscape[xpos + 1][ypos];
                        break;
                    } else if (xpos > mdp.getWidth() - 1) {
                        // if out of range
                        expectedValue += probDistribution[i] * -1;
                    }
                default:
                    expectedValue += probDistribution[i] * valueLandscape[xpos][ypos];
                    break;
            }
            complexity++;
        }
        double reward = rewardLandscape[xpos][ypos];
        return reward + (expectedValue * discount);
    }

    private double[][] createRewardLandscape() {
        double[][] rewardLandscape = new double[mdp.getWidth()][mdp.getHeight()];
        for (int i = 0; i < mdp.getWidth(); i++) {
            for (int j = 0; j < mdp.getHeight(); j++) {
                Field field = mdp.getField(i, j);
                if (field == Field.REWARD) {
                    rewardLandscape[i][j] = mdp.getPosReward();
                } else if (field == Field.EMPTY) {
                    rewardLandscape[i][j] = mdp.getNoReward();
                } else if (field == Field.NEGREWARD) {
                    rewardLandscape[i][j] = mdp.getNegReward();
                }
                // otherwise its an obstacle, keeps 0
            }
        }
        return rewardLandscape;
    }

    public Action[] getPossibleActions(int xpos, int ypos) {
        ArrayList<Action> actions = new ArrayList<>();
        if (ypos < (mdp.getHeight() - 1) && mdp.getField(xpos, ypos + 1) != Field.OBSTACLE) {
            actions.add(Action.UP);
        }
        if (ypos > 0 && mdp.getField(xpos, ypos - 1) != Field.OBSTACLE) {
            actions.add(Action.DOWN);
        }
        if (xpos > 0 && mdp.getField(xpos - 1, ypos) != Field.OBSTACLE) {
            actions.add(Action.LEFT);
        }
        if (xpos < mdp.getWidth() - 1 && mdp.getField(xpos + 1, ypos) != Field.OBSTACLE) {
            actions.add(Action.RIGHT);
        }
        return actions.toArray(new Action[actions.size()]);
    }

    public Action getAction(int xpos, int ypos) {
        return policyLandscape[xpos][ypos];
    }

    public void printComplexity(){
        System.out.println(complexity);
    }
}
