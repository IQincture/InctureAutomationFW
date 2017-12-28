package others.unde.listeners1;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class ISuiteListenerImp implements ISuiteListener{

	@Override
	public void onStart(ISuite suite) {
		System.out.println("ISuiteListener--> onStart");
		
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("ISuiteListener--> onFinish");
		
	}

}
