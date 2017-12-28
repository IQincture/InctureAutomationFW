package others.unde.listeners1;

import org.testng.IExecutionListener;

public class IExecutionListenerImpl implements IExecutionListener {

	@Override
	public void onExecutionStart() {
	
		System.out.println(" IExecutionListener --> onExecutionStart()");
		
	}

	@Override
	public void onExecutionFinish() {
		System.out.println(" IExecutionListener --> onExecutionFinish()");
		
	}

}
