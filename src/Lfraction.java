import java.util.regex.PatternSyntaxException;
/**
 * This class represents fractions of form n/d where n and d are long integer
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {
   private long numerator; //lugeja
   private long denominator; //nimetaja

   /**
    * Constructor.
    *
    * @param n numerator
    * @param d denominator > 0
    */
   public Lfraction(long n, long d) {
      if (d == 0) {
         throw new ArithmeticException("Ei saa nulliga jagada! [" + n + "/" + d + "]");
      } else if (d < 0){
         n *= -1;
         d *= -1;
      }
      long gcd = greatestCommonDivisor(n, d);//suurim ühistegur
      this.numerator = n / gcd;
      this.denominator = d / gcd;
   }

   /**
    * Main method. Different tests.
    */
   public static void main(String[] param) {
   }

   /**
    * Double value f presented as a fraction with denominator d > 0.
    *
    * @param f real number
    * @param d positive denominator for the result
    * @return f as an approximate fraction of form n/d
    */
   public static Lfraction toLfraction(double f, long d) {
      return new Lfraction(Math.round(f * d), d);
   }

   /**
    * Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    *
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */
   public static Lfraction valueOf(String s) {
      try {
         String[] nd = s.split("/");
         String n = nd[0];
         String d = nd[1];
         return new Lfraction(Long.parseLong(n), Long.parseLong(d));

      } catch (PatternSyntaxException | ArrayIndexOutOfBoundsException e) {
         throw new RuntimeException("Halb operaator kohal: [" + s + "]", e);

      } catch (NumberFormatException nfe) {
         throw new RuntimeException("Halb sisend kohal: [" + s + "]", nfe);

      } catch (RuntimeException e) {
         e.printStackTrace();
      }
      return null;
   }

   private long greatestCommonDivisor(long numerator, long denominator) {
      long divisionMod = numerator % denominator;
      while ((!(divisionMod == 0))) {
         numerator = denominator;
         denominator = divisionMod;
         divisionMod = numerator % denominator;
      }
      return Math.abs(denominator);
   }

   /**
    * Public method to access the numerator field.
    *
    * @return numerator
    */
   public long getNumerator() {
       return this.numerator;
   }

   /**
    * Public method to access the denominator field.
    *
    * @return denominator
    */
   public long getDenominator() {
      return this.denominator;
   }

   /**
    * Conversion to string.
    *
    * @return string representation of the fraction
    */
   @Override
   public String toString() {
      return numerator + "/" + denominator;
   }

   /**
    * Equality test.
    *
    * @param m second fraction
    * @return true if fractions this and m are equal
    */
   @Override
   public boolean equals(Object m) {
      Lfraction fraction = ((Lfraction) m);
      return numerator == fraction.numerator && denominator == fraction.denominator;
   }

   /**
    * Hashcode has to be equal for equal fractions.
    *
    * @return hashcode
    */
   @Override
   public int hashCode() {
      return (String.valueOf(numerator) + String.valueOf(denominator)).hashCode();
   }

   /**
    * Sum of fractions.
    *
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus(Lfraction m) {
      long commonDenominator = denominator * m.denominator;
      long sumOfNumerators = (numerator * (commonDenominator / denominator)) + (m.numerator * (commonDenominator / m.denominator));

      return new Lfraction(sumOfNumerators, commonDenominator);
   }

   /**
    * Multiplication of fractions.
    *
    * @param m second factor
    * @return this*m
    */
   public Lfraction times(Lfraction m) {
      return new Lfraction(numerator * m.numerator, denominator * m.denominator);
   }

   /**
    * Inverse of the fraction. n/d becomes d/n.
    *
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
      if (numerator < 0) {
         denominator *= 1;
      } else if (numerator == 0){
         throw new ArithmeticException("Ei saa nulliga jagada! [" + numerator + "/" + denominator + "]");
      }
      return new Lfraction(denominator, numerator);
   }

   /**
    * Opposite of the fraction. n/d becomes -n/d.
    *
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      return new Lfraction(numerator * -1, denominator);
   }

   /**
    * Difference of fractions.
    *
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus(Lfraction m) {
      long commonDenominator = denominator * m.denominator;
      long sumOfNumerators = (numerator * (commonDenominator / denominator)) - (m.numerator * (commonDenominator / m.denominator));

      return new Lfraction(sumOfNumerators, commonDenominator);
   }

   /**
    * Quotient of fractions.
    *
    * @param m divisor
    * @return this/m
    */
   public Lfraction divideBy(Lfraction m) {
      m = m.inverse();

      if(denominator * m.denominator == 0){
         throw new ArithmeticException("Ei saa nulliga jagada! [" + numerator + "/" + denominator * numerator + "]");
      }
      return new Lfraction(numerator * m.numerator, denominator * m.denominator);
   }

   /**
    * Comparision of fractions.
    *
    * @param m second fraction
    * @return -1 if this < m; 0 if this==m; 1 if this > m
    */
   @Override
   public int compareTo(Lfraction m) {
      long originalFraction = numerator * m.denominator;
      long mFraction = denominator * m.numerator;

      if (originalFraction == mFraction) {
         return 0;
      } else if (originalFraction < mFraction) {
         return -1;
      } else {
         return 1;
      }
   }

   /**
    * Clone of the fraction.
    *
    * @return new fraction equal to this
    */
   @Override
   public Object clone()
           throws CloneNotSupportedException {

      return new Lfraction(numerator, denominator);
   }

   /**
    * Integer part of the fraction.
    *
    * @return integer part of this fraction
    */
   public long integerPart() {
      return numerator / denominator;
   }

   /**
    * Extract fraction part of the fraction
    * (a proper fraction without the integer part).
    *
    * @return fraction part of this fraction
    */
   public Lfraction fractionPart() {
      long intPart = integerPart();
      long newNumerator = numerator - (intPart * denominator);

      return new Lfraction(newNumerator, denominator);
   }

   /**
    * Approximate value of the fraction.
    *
    * @return numeric value of this fraction
    */
   public double toDouble() {
      return (double) numerator / denominator;
   }
}