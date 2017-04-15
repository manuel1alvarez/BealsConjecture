
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

public class AnswerStructures {
	abstract_functions test1 = new abstract_functions();

	ArrayList<BigInteger>[] SummandsList(int numBits) {
		BigInteger two = new BigInteger("2");
		BigInteger maxNumber = two.pow(numBits - 1).subtract(BigInteger.ONE);
		BigInteger maxBase = floor3rdRoot(maxNumber, 3);
		ArrayList<BigInteger>[] vectorList = new ArrayList[maxBase.intValue()];
		BigInteger next;
		int power;
		vectorList[0] = new ArrayList<BigInteger>();
		vectorList[0].add(BigInteger.ONE);
		for (int i = 1; i < vectorList.length; i++) {
			vectorList[i] = new ArrayList<BigInteger>();
			next = BigInteger.ZERO;
			power = 3;
			while (maxNumber.compareTo(next) > 0) {
				BigInteger base = BigInteger.valueOf(1 + i);
				next = base.pow(power);
				if (maxNumber.compareTo(next) < 0)
					break;
				vectorList[i].add(next);
				power++;
			}

		}
		return vectorList;
	}
	
	// In the following array of linked lists we store all valid A^x Each linked
	 // list contains the same base (A) being raised to different powers until we
	 // have surpassed maxNumber. The Base is given by the array index.
	LinkedList<BigInteger>[] SummandsLinked(int numBits) {
		BigInteger two = new BigInteger("2");
		BigInteger maxNumber = two.pow(numBits - 1).subtract(BigInteger.ONE);
		BigInteger maxBase = floor3rdRoot(maxNumber, 3);
		LinkedList<BigInteger>[] vectorList = new LinkedList[maxBase.intValue()];
		BigInteger next;
		int power;
		vectorList[0] = new LinkedList<BigInteger>();
		vectorList[0].add(BigInteger.ONE);
		for (int i = 1; i < vectorList.length; i++) { // for each base 2...
														// maxBase
			next = BigInteger.ZERO;
			power = 3;
			while (maxNumber.compareTo(next) > 0) {// build linked list of a
													// single base to several
													// powers
				if (vectorList[i] == null) {// initialize each linked list
					vectorList[i] = new LinkedList<BigInteger>();
				}
				BigInteger base = BigInteger.valueOf(1 + i);
				next = base.pow(power);
				vectorList[i].addLast(next);
				power++;
			}
			vectorList[i].removeLast();// remove entry bigger than maxNumber
		}
		return vectorList;
	}

	// finding roots for BigInteger values. Subroutine by Paul Boddington
	BigInteger floor3rdRoot(BigInteger x, int n) { 
		BigInteger a;
		BigInteger bigN = BigInteger.valueOf(n);
		BigInteger bigNMinusOne = BigInteger.valueOf(n - 1);
		BigInteger b = BigInteger.ZERO.setBit(1 + x.bitLength() / n);
		do {
			a = b;
			b = a.multiply(bigNMinusOne).add(x.divide(a.pow(n - 1)))
					.divide(bigN);
		} while (b.compareTo(a) == -1);
		return a;
	}

	HashMap<BigInteger, Pairs> HashedAnswers(int numBits) { 
		// Storing C^z values less than maxNumber
		BigInteger two = new BigInteger("2");
		BigInteger maxNumber = two.pow(numBits - 1).subtract(BigInteger.ONE);
		BigInteger maxBase = floor3rdRoot(maxNumber, 3);// [4. Boddington]
		// any base larger than maxBase will be larger than maxNumber when
		// cubed.

		HashMap<BigInteger, Pairs> myHash = new HashMap<BigInteger, Pairs>();
		BigInteger next, base;
		for (int i = 2; i <= maxBase.intValue(); i++) {// raise base (i) to
														// several powers until
														// it surpasses
														// maxNumber. Add each
														// entry to hash table
			int power = 3;
			next = BigInteger.ZERO;
			while (maxNumber.compareTo(next) > 0) {
				base = BigInteger.valueOf(i);
				next = base.pow(power);
				Pairs somePair = new Pairs(base.intValue() ,(short) power);
				if (maxNumber.compareTo(next) < 0)
					break;
				//if (myHash.containsKey(next)
						//&& test1.coprime(base.intValue(), myHash.get(next)))
					//throw new IndexOutOfBoundsException(
							//"Different coprime Bases yield the same result. \nRewrite Hashtable or data structure to accept duplicate Keys.");
				myHash.put(next, somePair);
				power++;
			}
		}
		return myHash;
	}

	HashSet<BigInteger> SetAnswers(int numBits) {
		BigInteger two = new BigInteger("2");
		BigInteger maxNumber = two.pow(numBits - 1).subtract(BigInteger.ONE);
		BigInteger maxBase = floor3rdRoot(maxNumber, 3);
		HashSet<BigInteger> mySet = new HashSet<BigInteger>();
		BigInteger next, base;
		for (int i = 2; i <= maxBase.intValue(); i++) {// raise base (i) to
														// several powers until
														// it surpasses
														// maxNumber. Add each
														// entry to hash table
			int power = 3;
			next = BigInteger.ZERO;
			while (maxNumber.compareTo(next) > 0) {
				base = BigInteger.valueOf(i);
				next = base.pow(power);
				if (maxNumber.compareTo(next) < 0)
					break;
				mySet.add(next);
				power++;
			}
		}
		return mySet;
	}

	/*
	 * Some entries in the array linked list are represented more than once.
	 * This happens when bases share unique prime factorization with one being
	 * raised further (powers). For example 8 = 2^3. So any power of 8 can be
	 * represented as (2^3)^x. All Base 8 powers are contained in Base 2 linked
	 * List of ArrayLinked list. Once we identify these duplicate bases, we will
	 * store them in a red-black Tree.
	 */
	HashSet<Long> PowerSet(int numBits) {
		BigInteger two = new BigInteger("2");
		BigInteger maxNumber = two.pow(numBits - 1).subtract(BigInteger.ONE);
		BigInteger maxBase = floor3rdRoot(maxNumber, 3);
		BigInteger maxSquare = floor3rdRoot(maxBase, 2); 
		// any base larger than maxSquare will be larger than maxBase when squared.
		HashSet<Long> squareSet = new HashSet<Long>();
		for (int i = 2; i <= maxSquare.intValue(); i++) {
			long next = 0, raised = 2;
			while (next < maxBase.intValue()) { // storing numbers that have
												// integer roots
				next = (long) Math.pow(i, raised);
				if (next > maxBase.intValue())
					break;
				squareSet.add(next);
				raised++;
			}
		}
		return squareSet;
	}

	int MaxBase(int numBits) {
		BigInteger two = new BigInteger("2");
		BigInteger maxNumber = two.pow(numBits - 1).subtract(BigInteger.ONE);
		int maxBase = floor3rdRoot(maxNumber, 3).intValue();
		return maxBase;
	}

	

	void RunCounterSearch(int numBits, int start, int stop, String threadName) {

		final long startTime = System.currentTimeMillis();
		abstract_functions test1 = new abstract_functions();
		Random rand = new Random();
		HashSet<BigInteger> myHash = SetAnswers(numBits);
		ArrayList<BigInteger>[] sumList = SummandsList(numBits);
		HashSet<Long> powerSet = PowerSet(numBits);
		BigInteger sum;
		
		for (int a = start; a < stop; a++) { // for each A^x
			if (powerSet.contains((long) a + 1))
				continue; // bases with integer roots not considered, they are
							// duplicates.
			 if(rand.nextInt(50)== 49) // informs the user where the program is.
				 System.out.println("Searching Base " + a + " out of " + stop);
			for (BigInteger s1 : sumList[a]) {
				for (int b = a; b < sumList.length; b++) {
					if (test1.coprime(a + 1, b + 1) == false)
						continue; // If counter examples do exist they must
									// occur when A & B are coprime
					for (BigInteger s2 : sumList[b]) {
						sum = s1.add(s2); // A^x + B^y, A & B are coprime
						if (myHash.contains(sum)) { // Potential counter example
													// in our hashtable, Begin
													// search.
							prtCounter(a + 1, b + 1, s1, s2, sum);
						}
					}
				}
			}

		}

		final long endTime = System.currentTimeMillis();
		System.out.println("*** " +threadName + " run time: " + (endTime - startTime)
				+ " milliseconds." + "\n     counter-examples found (up to 2^"
				+ numBits + "): 0 "  + " \n");

	}

	void prtCounter(int a, int b, BigInteger s1, BigInteger s2, BigInteger sum) {
		System.out.println("Counter Example Found!!  " + a + "^x" + " + " + b
				+ "^y = " + s1 + " + " + s2 + " = " + sum);
		System.exit(0);
	}

}
class Pairs {
	 private int base;
	 private short power;
	 public Pairs(int C, short z) {
		  base = C;
		  power = z;
	 }
	 public short power(){
		 return power;
	 }
	 public int base(){
		 return base;
	 }
}

