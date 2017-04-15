

public class abstract_functions{
	
	 long gcd(long intOne, long intTwo){ // euclidean algorithm
        if((intOne == 0 || intTwo == 0))
            return Math.max(intOne, intTwo);
        
        long tempMax; long tempMin;
        while(intOne != 0){
            tempMax =  Math.max(intOne, intTwo); 
            tempMin =  Math.min(intOne, intTwo);
            intTwo =  tempMin;    //next Iteration this will be the higher one.
            intOne =   tempMax % tempMin ;    // low number in next iteration.
        }
        return intTwo; // Previous remainder when we reach remainder = 0 is answer.
    }
	 
	 long gcdTriple(long one, long two, long three){
		return gcd( gcd(one,two),three );
	 }
	 
	 boolean coprimeTriple(long one, long two, long three){
		return gcd(one,two) == 1 && gcd(two,three) == 1 && gcd(one,three) == 1; 
	 }
	
	 boolean coprime(long intOne, long intTwo){ 
		 return gcd(intOne,intTwo) == 1;
}
	 
}