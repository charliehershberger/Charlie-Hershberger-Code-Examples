import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class main {

	public static void main(String[] args) throws Exception  {
		// TODO Auto-generated method stub
		HashMap a = new HashMap<String, String>();
		a.put("Hello", "there");
		a.put("Hell", "thee");
		a.put("Hello", "thre");
		a.put("llo", "ther");
		for(String key: (Set<String>)(a.keySet())){
			System.out.println(key+" "+a.get(key));
		}
		System.out.println("what it looks like");
		HashMap b = switchMap(a);
		Set<String> c = b.keySet();
		for(String key: c){
			System.out.println(key+" "+b.get(key));
		}
	}
	public static HashMap<String, String> switchMap(Map <String, String> map) throws Exception{
		Set<String> Set = map.keySet();
		HashMap answer = new HashMap<String, String>();
		for(String key: Set){
			int test = 0;
			for(String Key: Set){
				if (map.get(key).equals(map.get(Key))){
					test++;
				}
			}
			System.out.println();
			if (test >1){
				throw new Exception();
			}
		}
		for(String key: Set){
			answer.put(map.get(key), key);
		}
		return answer;
	}
}
