package others.unde.listeners1;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class IInvokedMethodListenerImp implements IInvokedMethodListener,IMethodInterceptor {

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		System.out.println(method.getTestMethod());
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		System.out.println(method.getTestMethod());
		
	}

	 @Override
	    public List<IMethodInstance> intercept(List<IMethodInstance> arg0, ITestContext arg1)
	    {
		 System.out.println(arg0.get(0).getMethod());
	        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
	        return arg0;
	    }

}
