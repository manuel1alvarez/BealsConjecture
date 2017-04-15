
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.math.BigInteger;
//Search for solutions adhering to Beal's Conjecture: A^x +B^y = C^z
public class BealsSolver{
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		abstract_functions test1 = new abstract_functions();
		AnswerStructures datagetter = new AnswerStructures();
		
		int numBits = 40; // find solutions up to number of bits, range 10 to 91
		HashMap<BigInteger, Pairs> myHash = datagetter.HashedAnswers(numBits);
		LinkedList<BigInteger>[] vectorList = datagetter.SummandsLinked(numBits);

		int numSoln = 0, powerB, powerA;
		BigInteger  num_A, sum;
		for (int a = 0; a < vectorList.length; a++) {
			ListIterator<BigInteger> listIter_A = vectorList[a].listIterator();
			powerA = 2;
			while (listIter_A.hasNext()) {
				powerA++;
				num_A = listIter_A.next();

				for (int b = a; b < vectorList.length; b++) {
					if (!test1.coprime(a + 1, b + 1) == false)// Only non-coprime Bases. 
						continue;

					ListIterator<BigInteger> listIter_B = vectorList[b].listIterator();
					powerB = 2;
					while (listIter_B.hasNext()) {
						powerB++;
						sum = num_A.add(listIter_B.next());
						if (myHash.get(sum) != null) {
							Pairs somePair  = myHash.get(sum);
							int gcd =  (int) test1.gcdTriple(a+1,b+1, somePair.base());
							numSoln++;
							prtAnswer(numSoln,a+1,powerA,b+1,powerB,sum,somePair.base(),gcd,somePair.power());
						}

					}

				}

			}
		}

		final long endTime = System.currentTimeMillis();
		System.out.println("Total program run time: " + (endTime - startTime) + " milliseconds");

	}

	private static void prtAnswer(int numSoln, int i, int powerA, int j,
			int powerB, BigInteger sum, int base, int gcd, short powerZ) {
		System.out.println("solution # " + numSoln + " | "
				+ i + "^" + powerA + " + " + j
				+ "^" + powerB + " = " + base + "^" + powerZ + " = " + sum + ",  GCD: "+ gcd);
		
	}

}