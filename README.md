# Lab_5_The_Matching_Problem
 implement an algorithm that finds a solution to the following problem:  N programmers are looking for a job; N companies are looking to hire a programmer. 

# What This Program Does; 
    - This Java program implements the Gale-Shapley algorithm to find a satisfactory (stable) pairing between  programmers and  companies based on mutual ranked preferences. A pairing is satisfactory if no programmer and company would both prefer each other over their current match.

# How to Input Data
    - Test File Version ()
Preferences are hardcoded as 2D arrays:
    - : Each row is a programmer’s ranked list of companies (0-indexed).
    - : Each row is a company’s ranked list of programmers (0-indexed).
  - You can modify these arrays directly in the  method to test different scenarios.
    - File Input Version ()
    - Reads preferences from a file named .
    - The file format:

# Number of participants
5

# Programmer preferences
4 0 2 1 3
3 4 1 0 2
3 1 2 4 0
2 1 4 0 3
0 3 1 2 4

# Company preferences

1 4 0 2 3
0 1 2 3 4
4 2 1 0 3
0 2 1 3 4
1 2 4 3 0

Comment lines starting with # are ignored.
  
Test Cases
The program includes four documented test cases:
    1. The provided 5x5 example from the lab assignment 
    2. The edge case where all programmers want the same company 
    3. Reverse preferences (last preferred first)
    4. Randomized preference for both sides
Each test prints:
    - The final company-programmer matches
    - Whether the pairing is satisfactory (verified using  method)

# Why the Algorithm Is Correct
Termination Guarantee:
    Each programmer proposes to each company at most once. Since there are  programmers and  companies, the total number of proposals is bounded by . Therefore, the algorithm always terminates.
Satisfactory Guarantee:
    The Gale-Shapley algorithm ensures that no programmer-company pair would both prefer each other over their current match. This is the definition of a stable (satisfactory) pairing. The algorithm avoids instability by allowing companies to tentatively accept better proposals and programmers to keep proposing until matched.
Verification:
    The program includes a method  that checks for any unstable pairs in the final matching. All test cases pass this check.

# Efficiency in the Worst Case
Time Complexity:
Worst-case time complexity is O(N²).
    - Each of the  programmers may propose to all  companies.
    - Each proposal involves constant-time comparison and potential reassignment.
Space Complexity:
    - Also O(N²) due to the preference matrices and ranking maps.

Justification:
    - The algorithm is efficient for moderate values of  and scales predictably. It avoids backtracking or recursion and uses simple loops and arrays.

# Helpful links and resources 
[Gale–Shapley algorithm - Wikipedia](https://en.wikipedia.org/wiki/Gale%E2%80%93Shapley_algorithm)

[Gale-Shapley Algorithm Explained | Built In](https://builtin.com/articles/gale-shapley-algorithm)

[Stable Marriage Problem - GeeksforGeeks](https://www.geeksforgeeks.org/dsa/stable-marriage-problem/)