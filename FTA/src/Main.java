import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner inp = new Scanner(System.in);
		int first = 0;
		int second = 0;
		while(true){
			try{
				System.out.println("enter -999 to quit");
				System.out.println("what is your first number?");
				first = inp.nextInt();
				System.out.println("what is your second number?");
				second = inp.nextInt();
				if (first == -999 || second == -999){
					break;
				}
				int check = fta(first,second);
				System.out.println(check);
			}catch(Exception e){
				first = 0;
				second = 0;
				System.out.println("there was an error in type, please try agian");
				System.exit(1);
			}
		}
	}
	public static int fta(int larger, int smaller){
		if (smaller>larger){
			int temp = larger;
			larger = smaller;
			smaller = temp;
		}
		int counter = 0;
		while (-larger+(smaller*counter)<=0){
			counter++;
		}
		int plus = -larger+(smaller*counter);
		int minus;
		if (plus - smaller >= 0){
			minus = plus - 2*smaller; 
		}else{
			minus = plus - smaller;
		}
		if (plus == -minus){
			return plus;
		}
		return fta(plus, -minus);
	}		
}