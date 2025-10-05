import java.util.*;
import java.io.*;

public class Matching_Problem_fromFile {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("preferences.txt"));

            // Skip comment lines and blank lines until we find the number of participants
            int N = -1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                N = Integer.parseInt(line);
                break;
            }

            if (N <= 0) throw new IllegalArgumentException("Invalid number of participants.");

            int[][] programmerPrefs = new int[N][N];
            int[][] companyPrefs = new int[N][N];

            // Read programmer preferences
            int pCount = 0;
            while (pCount < N && sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] tokens = line.split("\\s+");
                for (int j = 0; j < N; j++) {
                    programmerPrefs[pCount][j] = Integer.parseInt(tokens[j]);
                }
                pCount++;
            }

            // Read company preferences
            int cCount = 0;
            while (cCount < N && sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] tokens = line.split("\\s+");
                for (int j = 0; j < N; j++) {
                    companyPrefs[cCount][j] = Integer.parseInt(tokens[j]);
                }
                cCount++;
            }

            int[] matches = galeShapley(N, programmerPrefs, companyPrefs);

            System.out.println("=== Matching Results ===");
            for (int i = 0; i < N; i++) {
                char company = (char) ('A' + i);
                System.out.println("Company " + company + " matched with Programmer " + (matches[i] + 1));
            }

            boolean stable = isSatisfactory(matches, programmerPrefs, companyPrefs);
            System.out.println("Satisfactory pairing? " + stable);

        } catch (Exception e) {
            System.out.println("Error reading preferences file: " + e.getMessage());
        }
    }

    public static int[] galeShapley(int N, int[][] programmerPrefs, int[][] companyPrefs) {
        int[] companyMatches = new int[N];
        Arrays.fill(companyMatches, -1);

        int[] proposals = new int[N];
        boolean[] freeProgrammers = new boolean[N];
        Arrays.fill(freeProgrammers, true);

        int[][] companyRank = new int[N][N];
        for (int c = 0; c < N; c++) {
            for (int rank = 0; rank < N; rank++) {
                companyRank[c][companyPrefs[c][rank]] = rank;
            }
        }

        int freeCount = N;
        while (freeCount > 0) {
            for (int p = 0; p < N; p++) {
                if (!freeProgrammers[p]) continue;

                int c = programmerPrefs[p][proposals[p]];
                proposals[p]++;

                if (companyMatches[c] == -1) {
                    companyMatches[c] = p;
                    freeProgrammers[p] = false;
                    freeCount--;
                } else {
                    int current = companyMatches[c];
                    if (companyRank[c][p] < companyRank[c][current]) {
                        companyMatches[c] = p;
                        freeProgrammers[p] = false;
                        freeProgrammers[current] = true;
                    }
                }
            }
        }

        return companyMatches;
    }

    public static boolean isSatisfactory(int[] matches, int[][] programmerPrefs, int[][] companyPrefs) {
        int N = matches.length;

        int[][] progRank = new int[N][N];
        int[][] compRank = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int r = 0; r < N; r++) {
                progRank[i][programmerPrefs[i][r]] = r;
                compRank[i][companyPrefs[i][r]] = r;
            }
        }

        for (int c1 = 0; c1 < N; c1++) {
            int p1 = matches[c1];
            for (int c2 = 0; c2 < N; c2++) {
                if (c1 == c2) continue;
                int p2 = matches[c2];

                if (progRank[p1][c2] < progRank[p1][c1] && compRank[c2][p1] < compRank[c2][p2]) {
                    return false;
                }
            }
        }
        return true;
    }
}