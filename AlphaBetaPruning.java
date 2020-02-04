
import java.util.List;

public class AlphaBetaPruning {

  private double returnValue;
  private int maxDepth;
  private int nodeVisted;
  private int nodeEvaluated;
  private int runDepth;
  private String brachingFactor;
  private int bestMove;

  final int MAX = 999999;
  final int MIN = -999999;

  public AlphaBetaPruning() {
  }

  /**
   * This function will print out the information to the terminal, as specified
   * in the homework description.
   */
  public void printStats() {
    // TODO Add your code here
    System.out.println("Move: " + this.getBestMove());
    System.out.println("Value: " + this.returnValue);
    System.out.println("Number of Nodes Visited: " + this.nodeVisted);
    System.out.println("Number of Nodes Evaluated: " + this.nodeEvaluated);
    System.out.println("Max Depth Reached: " + this.maxDepth);
    System.out
        .println("Avg Effective Branching Factor: " + this.brachingFactor);


  }

  /**
   * This function will start the alpha-beta search
   * 
   * @param state This is the current game state
   * @param depth This is the specified search depth
   */
  public void run(GameState state, int depth) {
    // TODO Add your code here
    double alpha = Double.NEGATIVE_INFINITY;
    double beta = Double.POSITIVE_INFINITY;
    setMaxDepth();
    setRunDepth(depth);
    setBestMove();

    this.returnValue = alphabeta(state, 0, alpha, beta, state.maxPlayer());
    this.brachingFactor =
        String.format("%.1f",(this.nodeVisted - 1.0) / (this.nodeVisted - this.nodeEvaluated));
  }

  /**
   * Helper method for initializing maximum depth
   */
  public void setMaxDepth() {
    this.maxDepth = 0;
  }

  /**
   * Helper method for getting maximum depth
   * 
   * @return maximum depth
   */
  public int getMaxDepth() {
    return this.maxDepth;
  }

  /**
   * Helper method for initializing limited run depth
   * 
   * @param depth limiting depth to do the search
   */
  private void setRunDepth(int depth) {
    this.runDepth = depth;
  }

  /**
   * Helper method for getting limited run depth
   * 
   * @return run depth
   */
  private int getRunDpeth() {
    return this.runDepth;
  }

  /**
   * Helper method for initializing next move
   */
  private void setBestMove() {
    this.bestMove = -1;
  }

  /**
   * Helper method for getting next move
   * 
   * @return next move
   */
  private int getBestMove() {
    return this.bestMove;
  }

  /**
   * This method is used to implement alpha-beta pruning for both 2 players
   * 
   * @param state     This is the current game state
   * @param depth     Current depth of search
   * @param alpha     Current Alpha value
   * @param beta      Current Beta value
   * @param maxPlayer True if player is Max Player; Otherwise, false
   * @return int This is the number indicating score of the best next move
   */
  private double alphabeta(GameState state, int depth, double alpha,
      double beta, boolean maxPlayer) {

    // If maxDepth does not exceed current depth, increase 1
    if (this.maxDepth < depth)
      this.maxDepth++;
    // Mark a visited node
    this.nodeVisted += 1;
    // Get current node's all successors
    List<GameState> successors = state.getSuccessors();

    // Base case
    if (successors.size() == 0 || depth >= this.getRunDpeth()) {
      this.nodeEvaluated ++;
      return state.evaluate();
    }
    // If it is Max's turn
    if (maxPlayer) {
      double best = MIN;
      // Check it's all successors
      for (GameState next : successors) {
        // Assign the value
        double value = alphabeta(next, depth + 1, alpha, beta, false);
        // Check if it is the best move
        if (depth == 0 && value > best) {
          this.bestMove = next.getLastMove();
        }
        // Set value and alpha
        best = Math.max(best, value);
        alpha = Math.max(alpha, best);

        // Alpha Beta Pruning
        if (beta <= alpha)
          break; 
      }
      return best;
    } else { // Otherwise it is Min's turn
      double best = MAX;
      // Check it's all successors
      for (GameState next : successors) {
        // Assign the value
        double value = alphabeta(next, depth + 1, alpha, beta, true);
        // Check if it is the best move
        if (depth == 0 && value < best) {
          this.bestMove = next.getLastMove();
        }

        best = Math.min(best, value);
        beta = Math.min(beta, best);

        // Alpha Beta Pruning
        if (beta <= alpha)
          break;
      }
      return best;
    }
  }
}
