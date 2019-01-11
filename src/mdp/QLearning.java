package mdp;

import java.util.ArrayList;

public class QLearning {

    public final static double LEARN_RATE = 0.2;
    public final static double EPSILON = 0.04;
    public final static double DISCOUNT = 1;

    private int totalActionsMade;
    private double cumulativeReward;
    private ArrayList<Double> thousandthCumulativeReward;
    private int learningIterations;

    private MarkovDecisionProblem mdp;
    private QField[][] qFields;

    public QLearning(MarkovDecisionProblem mdp){
        this.mdp = mdp;
        mdp.changeProbabilities(EPSILON);
        this.qFields = initializeQ();
        this.learningIterations = 1;
        thousandthCumulativeReward = new ArrayList<>();
    }

    private QField[][] initializeQ() {
        QField[][] qFields = new QField[mdp.getWidth()][mdp.getHeight()];
        for (int i = 0; i < mdp.getWidth(); i++) {
            for (int j = 0; j < mdp.getHeight(); j++) {
                qFields[i][j] = new QField();
            }
        }
        return qFields;
    }

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
                if (totalActionsMade % 100 == 0){
                    thousandthCumulativeReward.add(cumulativeReward);
                }
            } while (!mdp.isTerminated());
            mdp.restart();
            mdp.setLearningIterations(learningIterations++);
            mdp.setCumulativeReward(cumulativeReward);
        }
        Utilities.dumpQInfo(thousandthCumulativeReward);
    }
}
