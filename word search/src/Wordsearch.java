import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter; 
public class Wordsearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[][] square= new char[0][0];
		String[] wordbank = {};
		try{
			File file = new File("./src/word search");
			Scanner reader = new Scanner(file);
			int x = reader.nextInt();
			int y = reader.nextInt();
			System.out.println(x+" "+y);
			square = new char[x][y];
			reader.nextLine();
			for (int i = 0; i<y; i++){
				square[i] = reader.nextLine().toCharArray();
				System.out.println(square[i]);
			}
			//System.out.println(reader.nextLine());
			int words = reader.nextInt();
			wordbank = new String[words];
			for (int i = 0; i < words; i++){
				wordbank[i] = reader.next();
			}
		}catch(IOException e){
			
		}
		/*
		char[][] a = new char[10][10];
		a[0] = "dskkzqbluh".toCharArray();
		a[1] = "onqkitstbv".toCharArray();
		a[2] = "iozitddbyr".toCharArray();
		a[3] = "gwhiclsozi".toCharArray();
		a[4] = "lfbkoexepo".toCharArray();
		a[5] = "olccqhajdq".toCharArray();
		a[6] = "vaiwhusebg".toCharArray();
		a[7] = "ekhatslnlt".toCharArray();
		a[8] = "secarsrraw".toCharArray();
		a[9] = "snowmanzqb".toCharArray();
		String[] b = {"ski", "ice","sled","kids","hats","cold","gloves","snowman","snowflake"};
		*/
		search(square,wordbank);
		
	}
	public static void search(char[][]square, String[]words){
		for (int i = 0;  i < square.length; i++){
			for (int j = 0;  j < square[0].length; j++){
				for (String word : words){
					for (int x =-1; x<2;x++){
						for(int y = -1; y<2;y++){
							if (x!=0||y!=0){
								for (int k = 0; k<word.length();k++){
									//System.out.println(word+" word "+x+" x "+y+" y "+k+" k "+i+" i "+j+" j ");
									//System.out.println(word.substring(k, k+1)+" "+square[i+x*k][j+y*k]);
									//System.out.println(k);
									if ((i+x*k<0)||(i+x*k>square.length-1)||(j+y*k<0)||(j+y*k>square[0].length-1)){
										break;
									}
									//System.out.println(x+"x"+y+"y"+k+"k"+square[i+x*k][j+y*k]+word.substring(k, k+1));
									if(word.charAt(k)!=square[i+x*k][j+y*k]){
										break;
									}
									if (k == word.length()-1){
										System.out.println(word+" at position ["+(j+1)+" "+(i+1)+"] and direction ["+x+" "+y+"]");
									}
								}
							}
						}
					}
				}
			}	
		}
		/*
		System.out.println("-1-1,0-1,1-1");
		System.out.println("-10, 00, 10");
		System.out.println("-11, 01, 11");
		*/	
	}
}
//2
//jacksos