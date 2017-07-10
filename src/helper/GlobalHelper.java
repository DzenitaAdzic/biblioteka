package helper;

public class GlobalHelper {
	public static boolean isNumber(String string){
		int broj;
        try{
            broj=Integer.parseInt(string);
            return true;
        } catch(Exception ex){            
            return false;
        }
	}
}
