package mdp;

import java.util.ArrayList;

/**
 * The class which represents the Q-Learning algorithm containing all parameters and main algorithm part.
 *
 * @author David Leeftink and Mantas Makelis
 */
public class QLearning {

    public final static double LEARN_RATE = 0.2;
    public final static double EPSILON = 0.04;
    public final static double DISCOUNT = 1;

    private int totalActionsMade;
    private double cumulativeReward;
    private ArrayList<Double> timedCumulativeReward;
    private int learningIterations;

    private MarkovDecisionProblem mdp;
    private QField[][] qFields;

    /**
     * The constructor of the Q-Learning algorithm. It initialised the starting values.
     *
     * @param mdp the initialised markov decision problem class which contains grid world.
     */
    public QLearning(MarkovDecisionProblem mdp) {
        this.mdp = mdp;
        mdp.changeProbabilities(EPSILON);
        this.qFields = initializeQ();
        this.learningIterations = 1;
        timedCumulativeReward = new ArrayList<>();
    }

    /**
     * Initialises starting state-actions pairs representation.
     *
     * @return the two-dimensional array of state-actions representations.
     */
    private QField[][] initializeQ() {
        QField[][] qFields = new QField[mdp.getWidth()][mdp.getHeight()];
        for (int i = 0; i < mdp.getWidth(); i++) {
            for (int j = 0; j < mdp.getHeight(); j++) {
                qFields[i][j] = new QField();
            }
        }
        return qFields;
    }

    /**
     * The Q-Leaning algorithm.
     */
    public void learn() {
        while (cumulativeReward < 100000) {
            do {
                QField current = qFields[mdp.getStateXPosition()][mdp.getStateYPostion()];
                Action action = current.getHighestAction();
                double reward = mdp.performAction(action);
                QField next = qFields[mdp.getStateXPosition()][mdp.getStateYPostion()];
                current.updateValue(action, reward, next);
                cumulativeReward += reward;
                totalActionsMade++;
                if (totalActionsMade % 100 == 0) {
                    timedCumulativeReward.add(cumulativeReward);
                }
            } while (!mdp.isTerminated());
            mdp.restart();
            mdp.setLearningIterations(learningIterations++);
            mdp.setCumulativeReward(cumulativeReward);
        }
        Utilities.dumpQInfo(timedCumulativeReward);
    }
}
