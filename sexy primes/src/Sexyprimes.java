import java.util.ArrayList;
public class Sexyprimes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("here are some sexy primes");
		SexyPrimes();
	}
	public static void SexyPrimes(){
		ArrayList<Integer> primes = new ArrayList<Integer>();
		int counter = 2;
		int primecounter = 0;
		int maxprime = 1000;
		primes.add(2);
		boolean primenum = true;
		// should you keep going?
		while (primecounter < maxprime){
			// move up the prime
			counter++;
			primenum = true;
			for (Integer prime: primes){
				// is it prime
				if (counter%prime.intValue()==0){
					primenum = false;
					break;
				}
				// break for effiecenty if it can't be correct
				if (Math.sqrt(counter) < prime.intValue()){
					break;
				}
			}
			if (primenum){
				//add if prime is less needed amount of primes
				//if (primes.size() < Math.sqrt(maxprime)){
					primes.add(counter);
				//}
				//add primes
				primecounter++;
				//check run
				if (primecounter%100000==0){
					System.out.printf("%d %,d",primecounter,counter);
					System.out.println();
				}
				//sexy primes
				for(Integer prime:primes){
					if (counter-6==prime){
						System.out.println(counter +" "+prime);
					}
					if (counter-6<prime){
						break;
					}
				}
			}
		}
	}
} 