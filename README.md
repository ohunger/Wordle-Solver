# Wordle-Solver
(Project for LMU Algorithms class 2022 FALL)-Owen Hunger

This version of wordle is a spin-off in that the word can be any length and the set of words that the computer can choose from is much larger, depending on the tests upwards of ten thousand. 
The feedback the algorithm recieves after each guess is edit-Distance, a dynamic programming method that displays the least amount of edits needed to change one word to another, and which edits are needed.

The main parts of the algorithm that had to be implemented were the edit distance and edit distance table that had to be generated given an input of two different strings.
The other part was actually using the edit distance to shrink the possible set of words to guess from each time a guess is made, thus getting rid of impossible guesses. A set amount of guesses is given each round, tests count as passed if a certain threshold of correct rounds is met.

Essentially the goal of the program is to be able to consistently guess words correctly given ten guesses, however the computer can choose words from a very large dictionary.


