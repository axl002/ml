import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by user on 3/17/17.
 * fun classification of cancer
 * using kuh nu nu classifier
 * data is cancer.txt
 * k is 5 by default;
 * rejects rows of data where index 6 has missing data
 */

public class KNN {

    private static final int HOW_MANY_K = 5;
    private static final String FILE_NAME = "cancer.txt";
    private static int[][] cells = new int[699][];
    private static HashMap<Integer, Cell> cellMap = new HashMap<>();

    public static void main(String[] args) {
        long davai = System.currentTimeMillis();
        read();
        kek();
        topKek(HOW_MANY_K);
        score();
        long total = System.currentTimeMillis() - davai;
        System.out.println("\nruntime: " + total + " momoseconds (1 second = 1000 momoseconds)");
    }


    // compute classification results and print to console
    private static void score() {
        int correct = 0;
        int fp = 0;
        int miss = 0;
        for (int key : cellMap.keySet()) {
            if (cellMap.get(key).correct) correct++;
            else {
                // false positives
                if (cellMap.get(key).CANCER == 2 && cellMap.get(key).guess == 4) fp++;
                // miss
                if (cellMap.get(key).CANCER == 4 && cellMap.get(key).guess == 2) miss++;
            }
        }

        System.out.println("Kuh nu nu classifier results: "
                + "\ncorrect: " + correct + " out of : " + cellMap.size()
                + "\n" + "accuracy: " + (correct * 1.0) / (cellMap.size() * 1.0)
                + "\n" + "precision: " + correct * 1.0 / (correct + fp) * 1.0
                + "\n" + "recall: " + correct * 1.0 / (miss + correct));
    }


    // for each cell in hashmap
    // get cancer from k nearest cells
    // vote on guess using mode
    private static void topKek(int k) {
        for (int key : cellMap.keySet()) {
            int[] guesses = new int[k];
            for (int j = 0; j < k; j++) {
                int currentKey = cellMap.get(key).distances.get(j).ID;

                guesses[j] = cellMap.get(currentKey).CANCER;
            }
            cellMap.get(key).guess = mode(guesses);
            // check correctness
            if (cellMap.get(key).guess == cellMap.get(key).CANCER) cellMap.get(key).correct = true;
        }
    }


    // for each point compute euclidian distances with every other point
    // store distances in list
    // sort list
    // construct cell object to store cell info and distances list
    // put cell with distances list into hashmap
    private static void kek() {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] != null) {
                ArrayList<Pair> dank = new ArrayList<>();
                for (int j = 0; j < cells.length; j++) {
                    if (cells[j] != null && j != i) {
                        dank.add(distance(cells[i], cells[j], j));
                    }
                }
                // intellij doesn't like this for some reason, suppress it
                //noinspection Since15
                dank.sort(new Comparator<Pair>() {
                    @Override
                    public int compare(Pair p1, Pair p2) {
                        if (p1.DISTANCE > p2.DISTANCE) {
                            return 1;
                        } else if (p1.DISTANCE < p2.DISTANCE) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                cellMap.put(i, new Cell(i, cells[i][10], dank));
            }

        }
    }

    // find mode and return it
    // possible values are 2 and 4
    private static int mode(int[] foo) {
        int twos = 0;
        int fours = 0;

        for (int num : foo) {
            if (num == 2) twos++;
            if (num == 4) fours++;
        }

        return twos >= fours ? 2 : 4;
    }

    // compute euclidian distances between start and end
    // return tuple of endID and distances
    private static Pair distance(int[] start, int[] ending, int endID) {
        return new Pair(endID, Math.sqrt(Math.pow(start[1] - ending[1], 2)
                + Math.pow(start[2] - ending[2], 2)
                + Math.pow(start[3] - ending[3], 2)
                + Math.pow(start[4] - ending[4], 2)
                + Math.pow(start[5] - ending[5], 2)
                + Math.pow(start[6] - ending[6], 2)
                + Math.pow(start[7] - ending[7], 2)
                + Math.pow(start[8] - ending[8], 2)
                + Math.pow(start[9] - ending[9], 2)));
    }

    // read data in
    private static void read() {
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader(new File(FILE_NAME)));

            String foo = bf.readLine();
            int i = 0;
            while (foo != null) {
                String[] bar = foo.split(",");
                // remove cells with missing data in position 6
                if (bar[6].equals("?")) {
                    foo = bf.readLine();
                    continue;
                }
                cells[i] = fill(bar);
                // advance
                i++;
                foo = bf.readLine();
            }
        } catch (IOException ioE) {
            ioE.printStackTrace();
            System.exit(666);
        }
    }

    // fill out array of features
    private static int[] fill(String[] bar) {
        int[] cell = new int[11];

        for (int i = 0; i < cell.length; i++) {
            cell[i] = Integer.parseInt(bar[i]);
        }

        return cell;
    }


    // class for cell id and distances tuple
    public static class Pair {
        public final int ID;
        public final double DISTANCE;

        public Pair(int id, double dis) {
            this.ID = id;
            this.DISTANCE = dis;
        }
    }


    // class for storing cell info
    public static class Cell {
        public final int ID;
        public final int CANCER;
        ArrayList<Pair> distances;
        public int guess;
        public boolean correct;

        public Cell(int id, int cancer, ArrayList<Pair> d) {
            this.ID = id;
            this.CANCER = cancer;
            this.distances = d;
        }
    }

}
