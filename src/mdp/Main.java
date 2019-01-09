package mdp;

/**
 * This main is for testing purposes (and to show you how to use the MDP class).
 * 
 * @author Jered Vroon
 *
 */
public class Main {

	/**
	 * @param args, not used
	 */
	public static void main(String[] args) {
		MarkovDecisionProblem mdp = new MarkovDecisionProblem();
		mdp.setInitialState(0, 0);
		for (int i = 0; i < 15; i++){
			mdp.performAction(Action.UP);
			mdp.performAction(Action.UP);
			mdp.performAction(Action.RIGHT);
			mdp.performAction(Action.RIGHT);
			mdp.performAction(Action.RIGHT);
			mdp.restart();
		}
		
		MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
		mdp2.setField(5, 5, Field.REWARD);
		for (int i = 0; i < 100; i++){
			mdp2.performAction(Action.UP);
			mdp2.performAction(Action.RIGHT);
			mdp2.performAction(Action.DOWN);
			mdp2.performAction(Action.LEFT);
		}
	}
}
