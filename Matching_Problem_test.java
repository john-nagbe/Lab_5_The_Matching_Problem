import java.util.Arrays;

public class Matching_Problem_test {

     public static void main(String[] args) {
        
        System.out.println("=== Test Case 1: Provided 5x5 Example ===");
        testCase(5,
            new int[][] {
                {4, 0, 2, 1, 3},
                {3, 4, 1, 0, 2},
                {3, 1, 2, 4, 0},
                {2, 1, 4, 0, 3},
                {0, 3, 1, 2, 4}
            },
            new int[][] {
                {1, 4, 0, 2, 3},
                {0, 1, 2, 3, 4},
                {4, 2, 1, 0, 3},
                {0, 2, 1, 3, 4},
                {1, 2, 4, 3, 0}
            }
        );

        System.out.println("\n=== Test Case 2: Everyone Wants the Same ===");
        testCase(3,
            new int[][] {
                {0, 1, 2},
                {0, 1, 2},
                {0, 1, 2}
            },
            new int[][] {
                {0, 1, 2},
                {0, 1, 2},
                {0, 1, 2}
            }
        );

        System.out.println("\n=== Test Case 3: Reverse Preferences ===");
        testCase(3,
            new int[][] {
                {2, 1, 0},
                {2, 1, 0},
                {2, 1, 0}
            },
            new int[][] {
                {2, 1, 0},
                {2, 1, 0},
                {2, 1, 0}
            }
        );

        System.out.println("\n=== Test Case 4: Randomized Preferences ===");
        testCase(3,
            new int[][] {
                {1, 0, 2},
                {2, 0, 1},
                {0, 2, 1}
            },
            new int[][] {
                {2, 1, 0},
                {0, 2, 1},
                {1, 0, 2}
            }
        );
}
public static void testCase(int N, int[][] programmerPrefs, int[][] companyPrefs) {
        int[] matches = galeShapley(N, programmerPrefs, companyPrefs);
        for (int i = 0; i < N; i++) {
            char company = (char) ('A' + i);
            System.out.println("Company " + company + " matched with Programmer " + (matches[i] + 1));
        }
        System.out.println("Satisfactory pairing? " + isSatisfactory(matches, programmerPrefs, companyPrefs));
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

