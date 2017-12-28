package randD;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("static-access")
public class SettersGetters {

	//Map<String, String> synchronizedMap = Collections.synchronizedMap();

	private  static String browserName,url, nodeIp, port;
	private static final Object countLockHelper = new Object();



	static Map mapSetGet = new HashMap();

	public static synchronized  void setBrowserName(String browserName1){

		browserName=browserName1;

		/*synchronized (SettersGetters.class) { 
            //or synchronized (Class.forname("BumpTest"))
			this.browserName=browserName;
			}*/

		/*synchronized (countLockHelper) {
			this.browserName=browserName;
		}*/

		//mapSetGet.put("browserName", browserName);
	}

	public	 static synchronized String getBrowserName(){
		String retunrvalu;
		//synchronized (SettersGetters.class) { 
		retunrvalu=browserName;
		//}


		return retunrvalu;
	}
}
