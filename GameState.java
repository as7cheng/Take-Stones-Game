import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
  private int size; // The number of stones
  private boolean[] stones; // Game state: true for available stones, false for
                            // taken ones
  private int lastMove; // The last move

  /**
   * Class constructor specifying the number of stones.
   */
  public GameState(int size) {

    this.size = size;

    // For convenience, we use 1-based index, and set 0 to be unavailable
    this.stones = new boolean[this.size + 1];
    this.stones[0] = false;

    // Set default state of stones to available
    for (int i = 1; i <= this.size; ++i) {
      this.stones[i] = true;
    }

    // Set the last move be -1
    this.lastMove = -1;
  }

  /**
   * Copy constructor
   */
  public GameState(GameState other) {
    this.size = other.size;
    this.stones = Arrays.copyOf(other.stones, other.stones.length);
    this.lastMove = other.lastMove;
  }


  /**
   * This method is used to compute a list of legal moves
   *
   * @return This is the list of state's moves
   */
  public List<Integer> getMoves() {
    // TODO Add your code here

    List<Integer> temp = new ArrayList<Integer>();
    // If this is the first move
    if (this.lastMove == -1) {
      // Add all odds < size/2
      for (int i = 1; i < (this.getSize() + 1) / 2; i += 2) {
        if (this.getStone(i))
          temp.add(i);
      }
      return temp;
    } else { // If this not the very first check the current state by last move
      // If last move is a prime
      for (int i = 1; i < this.getSize() + 1; i++) {
        if (this.getStone(i)) {
          if (i % this.getLastMove() == 0 || this.getLastMove() % i == 0)
            temp.add(i);
        }
      }
      return temp;
    }
  }


  /**
   * This method is used to generate a list of successors using the getMoves()
   * method
   *
   * @return This is the list of state's successors
   */
  public List<GameState> getSuccessors() {
    return this.getMoves().stream().map(move -> {
      var state = new GameState(this);
      state.removeStone(move);
      return state;
    }).collect(Collectors.toList());
  }


  /**
   * This method is used to evaluate a game state based on the given heuristic
   * function
   *
   * @return int This is the static score of given state
   */
  public double evaluate() {

    
    // If stone 1 is not taken, return 0
    if (this.getStone(1))
      return 0;

    // If it is Max's turn
    if (maxPlayer()) {
      // If max has no next move, min wins
      if (this.getSuccessors().size() == 0)
        return -1.0;

      // If last move is 1, count if the amount of successors is odd or even
      if (this.lastMove == 1) {
        // If the amount is even, return -0.5
        if (this.getMoves().size() % 2 == 0)
          return -0.5;
        else // Otherwise, return 0.5
          return 0.5;
      }

      if (Helper.isPrime(this.getLastMove())) {
        int count = 0;
        int k = 2;
        int mult = 2 * this.getLastMove();
        while (mult < this.getSize()) {
          if (getStone(mult)) {
            count += 1;
          }
          k++;
          mult = this.getLastMove() * k;
        }
        if (count % 2 == 0)
          return -0.7;
        else
          return 0.7;
      } else {
        int prime = Helper.getLargestPrimeFactor(this.getLastMove());
        int count = 0;
        int k = 1;
        int mult = 1 * prime;
        while (mult < this.getSize()) {
          if (getStone(mult)) {
            count += 1;
          }
          k++;
          mult = prime * k;
        }

        if (count % 2 == 0)
          return -0.6;
        else
          return 0.6;
      }
    } else { // If it is Min's turn
      // If Min has no next move, max wins
      if (this.getSuccessors().size() == 0)
        return 1.0;

      // If last move is 1, count if the amount of successors is odd or even
      if (this.lastMove == 1) {
        // If the amount is even, return -0.5
        if (this.getSuccessors().size() % 2 == 0)
          return 0.5;
        else // Otherwise, return 0.5
          return -0.5;
      }

      if (Helper.isPrime(this.getLastMove())) {
        int count = 0;
        int k = 2;
        int mult = 2 * this.getLastMove();
        while (mult < this.getSize()) {
          if (this.getStone(mult)) {
            count++;
          }
          k++;
          mult = this.lastMove * k;
        }
        if (count % 2 == 0)
          return 0.7;
        else
          return -0.7;
      } else {
        int prime = Helper.getLargestPrimeFactor(this.getLastMove());
        int count = 0;
        int k = 1;
        int mult = k * prime;
        while (mult < this.getSize()) {
          if (this.getStone(mult)) {
            count++;
          }
          k++;
          mult = this.lastMove * k;
        }
        if (count % 2 == 0)
          return 0.6;
        else
          return -0.6;
      }

    }
  }


  /**
   * This method is used to take a stone out
   *
   * @param idx Index of the taken stone
   */
  public void removeStone(int idx) {
    this.stones[idx] = false;
    this.lastMove = idx;
  }

  /**
   * These are get/set methods for a stone
   *
   * @param idx Index of the taken stone
   */
  public void setStone(int idx) {
    this.stones[idx] = true;
  }

  public boolean getStone(int idx) {
    return this.stones[idx];
  }

  /**
   * These are get/set methods for lastMove variable
   *
   * @param move Index of the taken stone
   */
  public void setLastMove(int move) {
    this.lastMove = move;
  }

  public int getLastMove() {
    return this.lastMove;
  }

  /**
   * This is get method for game size
   *
   * @return int the number of stones
   */
  public int getSize() {
    return this.size;
  }

  /**
   * 
   */
  public boolean maxPlayer() {
    int taken = 0;
    for (int i = 1; i < this.getSize() + 1; i++) {
      if (!this.getStone(i))
        taken++;
    }


    if (taken % 2 == 0)
      return true;

    return false;

  }
}
