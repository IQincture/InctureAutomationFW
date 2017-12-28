package others.unde.listeners1;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class IInvokedMethodListener2Impl implements IInvokedMethodListener2{

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
System.out.println("beforeInvocation()");
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		System.out.println("afterInvocation()");
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("beforeInvocation()");
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("afterInvocation()");
	}

}
