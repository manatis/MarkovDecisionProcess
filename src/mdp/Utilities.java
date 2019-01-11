package mdp;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Utilities {

    public static double max(double[] values) {
        double max = 0;
        for (double value : values) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

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

    public static void dumpQInfo(ArrayList<Double> thousandthCumulativeReward) {
        try {
            Random rand = new Random();
            PrintWriter pw = new PrintWriter(new File("cumulative_rewards" + rand.nextInt(100) + ".csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("id");
            sb.append(',');
            sb.append("Reward");
            sb.append('\n');

            int i = 1;
            for (double reward : thousandthCumulativeReward) {
                sb.append(i);
                sb.append(',');
                sb.append(reward);
                sb.append('\n');
                i++;
            }
            pw.write(sb.toString());
            pw.close();
        } catch (Exception ignored){}
    }
}