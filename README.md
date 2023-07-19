# CoastGuard Search Project 

## Overview

This project implements different search algorithms to find an optimal path for a coast guard ship to rescue people stranded on sinking ships and deliver them to safety stations.

A grid representing the map is randomly generated with the following elements:

- Coast guard ship with random starting position and passenger capacity
- Sinking ships at random locations containing random number of passengers  
- Safety stations at random locations

The goal is to find a path where the coast guard picks up all passengers from sinking ships and drops them off at safety stations in the minimum number of steps.

## Search Algorithms

The following search algorithms are implemented:

- Breadth-First Search (BFS)
- Depth-First Search (DFS)   
- Iterative Deepening Search (IDS)
- Greedy Search (GR1 and GR2)
- A* Search (AS1 and AS2)

Each algorithm attempts to find an optimal path from the starting state to the goal state based on different traversal strategies.

## Usage

The CoastGuard class provides static methods to:

- Generate a random grid string representing the map
- Encode a grid from a tree node state for search
- Run the search algorithms on a grid string and return the solution path
- Check if a solution path is valid by simulating it on the grid

Example usage:

```java
String grid = CoastGuard.genGrid();
String solution = CoastGuard.solve(grid, "AS1", false);
boolean isValid = CoastGuard.applyPlan(grid, solution); 
```

The solve() method takes the grid, search algorithm name, and boolean to visualize the search. It returns the solution path string.

The applyPlan() method validates if the path satisfies the goal conditions on the grid.

## Implementation

The GenericSearch class contains the common bfs, dfs, ids algorithm implementations. CoastGuard extends this and provides the problem-specific methods.

TreeNode represents each search node with state information. The Checker class is used to simulate and validate the solution path on a grid.

Overall, this project provides a template for implementing informed search algorithms on grid-based problems.
