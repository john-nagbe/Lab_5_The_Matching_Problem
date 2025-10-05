import java.util.*;

/*
 * Goal: Find a satisfactory paring such that no programmer and company would both prefer each other over their
 * current match.
 * 
    Initialization:
    • All programmers and companies start unmatched.
    • Each programmer has a list of companies ranked by preference.
    • Each company has a list of programmers ranked by preference. 
    Proposal Phase:
    • While there are unmatched programmers:
    • Each unmatched programmer proposes to their highest-ranked company they haven’t yet proposed to.
    • Each company reviews all proposals and tentatively accepts the one they prefer most (based on their ranking), rejecting the rest.
    Repeat:
    • Rejected programmers propose to their next preferred company.
    • Companies may drop their current match if a better one proposes.
    Termination:
    • The process continues until all programmers are matched.
    • The result is a satisfactory pairing. 
*/

public class Matching_Problem {
    
    public static void main(String[] args) {
        int N = 5;
        // Programmer preferences: programmerPrefs[programmer][rank] = company
        int[][] programmerPrefs = {
            {4, 0, 2, 1, 3}, // Programmer 0
            {3, 4, 1, 0, 2}, // Programmer 1
            {3, 1, 2, 4, 0}, // Programmer 2
            {2, 1, 4, 0, 3}, // Programmer 3
            {0, 3, 1, 2, 4}  // Programmer 4
        };

        // Company preferences: companyPrefs[company][rank] = programmer
        int[][] companyPrefs = {
            {1, 4, 0, 2, 3}, // Company 0
            {0, 1, 2, 3, 4}, // Company 1
            {4, 2, 1, 0, 3}, // Company 2
            {0, 2, 1, 3, 4}, // Company 3
            {1, 2, 4, 3, 0}  // company 4
        };

        int[] matches = galeShapley(N, programmerPrefs, companyPrefs);
        // Output results 
        for (int i = 0; i < N; i++) {
            char company = (char) ('A' + i);
            System.out.println("Company " + company + "matched with Programmer " + (matches[i] + 1));
        }
    }
    public static int[] galeShapley(int N, int[][] programmerPrefs, int[][] companyPrefs) {
        int[] companyMatches = new int[N]; // companyMatches[company] = programmer
        Arrays.fill(companyMatches, -1);

        int[] proposals = new int[N]; // proposals[programmer] = next company to propose to
        boolean[] freeProgrammers = new boolean[N];
        Arrays.fill(freeProgrammers, true);

        // Build company ranking map: companyRank[company][programmer] = rank
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
}

