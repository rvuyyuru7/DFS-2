// Approach 1: Using two Stacks. One for numbers and one for strings. Note that the initially an empty string will be pushed when open bracket is found. Ex: 2[a3[bc]]
// TC: O(n * k); n = s.length(), k = maximum repeat count
// SC: Number of opening brackets. Could be max of n/2 ~= O(n)
class Solution {
    public String decodeString(String s) {
        Stack<Integer> numberStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        int currentNumber = 0;
        StringBuilder currentString = new StringBuilder();
        for (int i = 0; i < s.length(); i ++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                // append digit to current number
                currentNumber = (currentNumber * 10) + (c - '0');
            } else if (c == '[') {
                // push to stacks
                numberStack.push(currentNumber);
                stringStack.push(currentString);

                // reset current number and string
                currentNumber = 0;
                currentString = new StringBuilder();
            } else if (c == ']') {
                // pop the current string's count
                int count = numberStack.pop();
                // pop the previous string
                StringBuilder previousString = stringStack.pop();
                // append current string count number of times to the previous string
                while (count -- != 0) {
                    previousString.append(currentString);
                }
                // previous string will become the new current string after closing the bracket
                currentString = previousString;
            } else {
                // append character to current string
                currentString.append(c);
            }
        }
        return currentString.toString();
    }
}

/**
 * Approach 2: Recursive DFS with a global index pointer
 * We traverse the string once, and whenever we see a '[',
 * we recursively decode the substring inside it.
 */
// TC: O(n * k)
// - n = length of string
// - k = max repetition count (due to repeated string appends)
//
// SC: O(n)
// - recursion stack can go as deep as number of nested brackets (worst case ~ n/2)
class Solution {
    int i = 0; // store index
    public String decodeString(String s) {
        int currentNumber = 0;
        StringBuilder currentString = new StringBuilder();
        while (i < s.length()) {
            char c = s.charAt(i);
            i ++; // increase index before sending recursion call (pre-order style traversal)
            if (Character.isDigit(c)) {
                // append digit to current number
                currentNumber = (currentNumber * 10) + (c - '0');
            } else if (c == '[') {
                // Start of a nested encoded substring
                // Recursively decode substring inside brackets
                String childString = decodeString(s);
                // Repeat the decoded substring 'currentNumber' times
                for (int i = 0; i < currentNumber; i ++) {
                    currentString.append(childString);
                }
                // Reset number after using it
                currentNumber = 0;
            } else if (c == ']') {
                // End of current recursive segment
                // Return the decoded string to previous level
                return currentString.toString();
            } else {
                // Regular character, just append to current result
                currentString.append(c);
            }
        }
        // Final result for top-level call
        return currentString.toString();
    }
}