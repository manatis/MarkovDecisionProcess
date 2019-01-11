package mdp;

import java.util.Random;

/**
 * This main is for testing purposes (and to show you how to use the MDP class).
 *
 * @author David Leeftink and Mantas Makelis
 */
public class Main {

    final static boolean CREATE_10_X_10 = true; // false creates 3x4 original map
    final static boolean CREATE_HARD_MAP = true;   // (10x10 map must be true) if true - randomly creates a hard map, false creates easy map
    public final static boolean RUN_Q = true;   // false run value iteration algorithm

    public static void main(String[] args) {
        MarkovDecisionProblem mdp;
        Random rand = new Random();
        if (CREATE_10_X_10) {
            mdp = new MarkovDecisionProblem(10, 10);
            if (CREATE_HARD_MAP) {
                int random = rand.nextInt(2);
                switch (random) {
                    case 0:
                        createMantasMap(mdp);
                        break;
                    case 1:
                        createDavidsMap(mdp);
                        break;
                }
            } else {
                createStanfordMap(mdp);
            }
        } else {
            mdp = new MarkovDecisionProblem();
        }

        if (RUN_Q) {
            mdp.setPosReward(1000);
            mdp.setNegReward(-100);
            mdp.setNoReward(-1);
            mdp.setWaittime(25);
            runQLearning(mdp);
        } else {
            mdp.setPosReward(100);
            mdp.setNegReward(-1000);
            mdp.setNoReward(-0.1);
            mdp.setWaittime(50);
            runValueIteration(mdp);
        }
    }

    /**
     * Start the Q-Leaning algorithm on a given grid world.
     *
     * @param mdp the initialised markov decision problem class.
     */
    private static void runQLearning(MarkovDecisionProblem mdp) {
        QLearning q = new QLearning(mdp);
        q.learn();
    }

    /**
     * Creates Value Iteration class which pre-calculates an optimal policy and then executes it.
     *
     * @param mdp the initialised markov decision problem class.
     */
    private static void runValueIteration(MarkovDecisionProblem mdp) {
        ValueIteration vi = new ValueIteration(mdp);
        boolean isComplexityPrinted = false;
        while (true) {
            do {
                mdp.performAction(vi.getAction(mdp.getStateXPosition(), mdp.getStateYPostion()));
            } while (!mdp.isTerminated());
            mdp.restart();
            if (!isComplexityPrinted) {
                vi.printComplexity();
                isComplexityPrinted = true;
            }
        }
    }

    /**
     * Creates a replica of a map used by Stanford University.
     *
     * @param mdp the initialised markov decision problem class.
     */
    private static void createStanfordMap(MarkovDecisionProblem mdp) {
        mdp.setInitialState(0, 9);
        mdp.setField(3, 2, Field.NEGREWARD);
        mdp.setField(3, 6, Field.NEGREWARD);
        mdp.setField(5, 5, Field.NEGREWARD);
        mdp.setField(6, 5, Field.NEGREWARD);
        mdp.setField(6, 4, Field.NEGREWARD);
        mdp.setField(8, 4, Field.NEGREWARD);
        mdp.setField(8, 3, Field.NEGREWARD);
        mdp.setField(5, 2, Field.NEGREWARD);
        mdp.setField(6, 2, Field.NEGREWARD);
        mdp.setField(5, 4, Field.REWARD);

        mdp.setField(1, 7, Field.OBSTACLE);
        mdp.setField(2, 7, Field.OBSTACLE);
        mdp.setField(3, 7, Field.OBSTACLE);
        mdp.setField(4, 7, Field.OBSTACLE);
        mdp.setField(4, 6, Field.OBSTACLE);
        mdp.setField(4, 5, Field.OBSTACLE);
        mdp.setField(4, 4, Field.OBSTACLE);
        mdp.setField(4, 3, Field.OBSTACLE);
        mdp.setField(4, 2, Field.OBSTACLE);
        mdp.setField(6, 7, Field.OBSTACLE);
        mdp.setField(7, 7, Field.OBSTACLE);
        mdp.setField(8, 7, Field.OBSTACLE);
    }

    /**
     * Creates a map made by Mantas Makelis.
     *
     * @param mdp the initialised markov decision problem class.
     */
    private static void createMantasMap(MarkovDecisionProblem mdp) {
        mdp.setField(5, 5, Field.REWARD);

        mdp.setField(4, 5, Field.NEGREWARD);
        for (int i = 1; i < 9; i++) {
            mdp.setField(i, 6, Field.NEGREWARD);
        }
        for (int i = 0; i < 9; i++) {
            mdp.setField(4, i, Field.NEGREWARD);
        }
        for (int i = 1; i < 7; i++) {
            mdp.setField(8, i, Field.NEGREWARD);
        }
    }

    /**
     * Creates a map made by David Leeftink.
     *
     * @param mdp the initialised markov decision problem class.
     */
    private static void createDavidsMap(MarkovDecisionProblem mdp) {
        mdp.setField(5, 4, Field.REWARD);
        mdp.setField(1, 0, Field.NEGREWARD);
        mdp.setField(1, 1, Field.NEGREWARD);
        mdp.setField(2, 2, Field.NEGREWARD);
        mdp.setField(3, 3, Field.NEGREWARD);
        mdp.setField(4, 4, Field.NEGREWARD);
        mdp.setField(5, 5, Field.NEGREWARD);
        mdp.setField(6, 6, Field.NEGREWARD);
        mdp.setField(7, 5, Field.NEGREWARD);
        mdp.setField(8, 4, Field.NEGREWARD);
        mdp.setField(8, 3, Field.NEGREWARD);
        mdp.setField(7, 3, Field.NEGREWARD);
        mdp.setField(6, 3, Field.NEGREWARD);
    }
}
