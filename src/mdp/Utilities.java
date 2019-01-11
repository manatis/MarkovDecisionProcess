package mdp;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class containing the static methods used though out the Value Iteration and Q-Learning algorithms.
 *
 * @author David Leeftink and Mantas Makelis
 */
public class Utilities {

    /**
     * Finder for the maximum value in the supplied array.
     *
     * @param values an array of double values.
     * @return highest value in the array.
     */
    public static double max(double[] values) {
        double max = 0;
        for (double value : values) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

    /**
     * Finder for the maximum value index in the supplied array.
     *
     * @param values an array of double values.
     * @return an index of the highest value in the array.
     */
    public static int getHighestIndex(double[] values) {
        int indexOfMax = 0;
        double max = values[0];
        for (int i = 0; i < values.length; i++) {
            if (max < values[i]) {
                max = values[i];
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }

    /**
     * Dumps the supplied information of the cumulative reward to the .csv file.
     * The information can then be loaded into R, Python, etc and plotted.
     *
     * @param timedCumulativeReward the array list of doubles from the Q-Learning algorithm.
     */
    public static void dumpQInfo(ArrayList<Double> timedCumulativeReward) {
        try {
            Random rand = new Random();
            PrintWriter pw = new PrintWriter(new File("cumulative_rewards" + rand.nextInt(100) + ".csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("id");
            sb.append(',');
            sb.append("Reward");
            sb.append('\n');

            int i = 1;
            for (double reward : timedCumulativeReward) {
                sb.append(i);
                sb.append(',');
                sb.append(reward);
                sb.append('\n');
                i++;
            }
            pw.write(sb.toString());
            pw.close();
        } catch (Exception ignored) {
        }
    }
}