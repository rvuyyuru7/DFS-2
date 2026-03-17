import java.util.LinkedList;
import java.util.Queue;

// Approach 1: BFS
/**
 * We scan the entire grid for any unvisited '1' which marks the start of an island.
 * Upon finding one, we use BFS to visit all connected land cells, marking them as '0'.
 * This prevents revisits and lets us count each island exactly once.
 */
// TC: O(m * n); m = number of rows, n = number of columns
// SC: O(Math.min(m, n)) for queue = max number of elements at each level of BFS (consider all the diagonals of the matrix)
class Solution {
    private static final int[][] DIRECTIONS = new int[][] {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    private static final char LAND = '1';
    private static final char WATER = '0';
    public int numIslands(char[][] grid) {
        int islandsCount = 0;
        Queue<int[]> indexQueue = new LinkedList<>();
        // Iterate through each index one by one
        for (int row = 0; row < grid.length; row ++) {
            for (int column = 0; column < grid[0].length; column ++) {
                // If land is found then find the entire island.
                if (grid[row][column] == LAND) {
                    islandsCount ++; // increase island count
                    // Find neighbors of the land
                    findNeighbors(grid, indexQueue, row, column);
                }
            }
        }
        return islandsCount;
    }

    // BFS to find neighbors
    private void findNeighbors(char[][] grid, Queue<int[]> indexQueue, int row, int column) {
        indexQueue.offer(new int[] {row, column});
        grid[row][column] = WATER; // update to water to avoid revisiting
        while (!indexQueue.isEmpty()) {
            System.out.println(indexQueue.size());
            int[] index = indexQueue.poll();
            // Check all neighbors of the index.
            for (int[] direction: DIRECTIONS) {
                int newRow = index[0] + direction[0];
                int newColumn = index[1] + direction[1];
                
                // If index is valid and is land add to the indexQueue.
                if (newRow >= 0 && newColumn >= 0 && newRow < grid.length && newColumn < grid[0].length
                    && grid[newRow][newColumn] == LAND
                ) {
                    indexQueue.offer(new int[] {newRow, newColumn});
                    grid[newRow][newColumn] = WATER; // update to water to avoid revisiting
                }
            }
        }
    }
}


// Approach 2: DFS
/**
 * We loop over each cell and start DFS if we hit an unvisited land cell '1'.
 * During DFS, we mark connected land as '0' so we don’t revisit.
 * Each DFS call handles one complete island, so we increase the count per call.
 */
// TC: O(m * n); m = number of rows, n = number of columns
// SC: O(m * n) for recursive stack. If entire matrix of size m * n has all elements as '1' then DFS will go through entire matrix before returning.
class Solution {
    private static final int[][] DIRECTIONS = new int[][] {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    private static final char LAND = '1';
    private static final char WATER = '0';
    public int numIslands(char[][] grid) {
        int islandsCount = 0;
        // Iterate through each index one by one
        for (int row = 0; row < grid.length; row ++) {
            for (int column = 0; column < grid[0].length; column ++) {
                // If land is found then find the entire island.
                if (grid[row][column] == LAND) {
                    islandsCount ++; // increase island count
                    // Find neighbors of the land
                    findNeighbors(grid, row, column);
                }
            }
        }
        return islandsCount;
    }

    // DFS to find neighbors
    private void findNeighbors(char[][] grid, int row, int column) {
        // base
        if (row < 0 || column < 0 || row >= grid.length || column >= grid[0].length || grid[row][column] == WATER) {
            return;
        }
        // logic
        grid[row][column] = WATER; // update to water to avoid revisiting
        // Check all neighbors of the index.
        for (int[] direction: DIRECTIONS) {
            int newRow = row + direction[0];
            int newColumn = column + direction[1];
            findNeighbors(grid, newRow, newColumn);
        }
    }
}