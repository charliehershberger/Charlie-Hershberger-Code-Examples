import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		char[] full = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		Scanner inp = new Scanner(System.in);
		System.out.println("what is your number?");
		String num = inp.nextLine();
		System.out.println("what is your base?");
		int base = inp.nextInt();
		System.out.println("what is your new base?");
		int nbase = inp.nextInt();
		int number = 0;
		int counter = -1;
		while(num.length()>0){
			counter++;
			char digit = num.charAt(num.length()-1);
			num = num.substring(0, num.length()-1);
			number += find(full, digit)*Math.pow(base, counter);
		}
		String ans = "";
		int r = 0;
		int d = 0;
		while (number>0){
			r = number%nbase;
			number/=nbase;
			ans=full[r]+ans;
		}
		System.out.println(ans);
	}
	public static int find(char[] full, char digit){
		int i = 0;
		while(full[i] != digit){
			i++;
		}
		return i;
	}
}
