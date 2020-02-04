public class Helper {

  /**
   * Class constructor.
   */
  private Helper() {
  }

  /**
   * This method is used to check if a number is prime or not
   * 
   * @param x A positive integer number
   * @return boolean True if x is prime; Otherwise, false
   */
  public static boolean isPrime(int x) {

    // TODO Add your code here
    for (int i = 2; i < x / 2; i++) {
      if (x % i == 0)
        return true;
    }
    return false;
  }

  /**
   * This method is used to get the largest prime factor
   * 
   * @param x A positive integer number
   * @return int The largest prime factor of x
   */
  public static int getLargestPrimeFactor(int x) {
    
 // TODO Add your code here
    int max = 0;
    for (int i = x / 2; i > 2; i--) {
      if (x % i == 0) {
        max = i;
        break;
      }
    }
    return max;
  }
}
