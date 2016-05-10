package squaresquares;
import java.util.ArrayList;
public class SquareSquares {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int top = 3000;
		int counter = 0;
		double num;
		ArrayList<Integer> occur = new ArrayList<Integer>();
		for (int i = 0; i<top; i++){
			occur.clear();
			
			for (int j = 1; j<top-1; j++){
				if ((j*j)> i*i){
					break;
				}
				num = (Math.sqrt(i*i-j*j));
				if (num%1 < 0.00000001 && !occur.contains((int)(num))&& (int)(num)!=0){
					occur.add((int)(num));
					occur.add(j);
					counter++;
					System.out.print(j+"^2 "+(int)(num)+"^2 = "+ i+"     ");
				}
				if (counter%7==0){
					counter++;
					System.out.println();
				}
				/*
				for (int k = j; k<top; k++){
					if((k*k)+(j*j)>i*i){
						break;
					}
					if((k*k)+(j*j)==i*i){
						System.out.println(j+"^2 "+k+"^2 = "+ i);
					}
				}
				*/
			}	
		}
	}

}
