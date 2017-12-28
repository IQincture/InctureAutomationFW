package others.incture.testcases;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;


//And here's a test class that uses this annotation and a listener which parses this custom annotation to show a hi message.

import org.testng.IInvokedMethodListener;
import org.testng.ITest;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE, CONSTRUCTOR})
@interface Greetings {
	public String wishUser();
}

//https://github.com/cbeust/testng/issues/469
@Listeners(WorkWithCustomAnnotations.MySimpleListener.class)
public class WorkWithCustomAnnotations {
	@Test
	@Greetings(wishUser = "Incture")
	public void simpleTestMethod(){
		System.out.println("Hello world");
	}

	public static class MySimpleListener implements IInvokedMethodListener,ITest{

		@Override
		public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
			Greetings greetings = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Greetings.class);
			if (greetings == null){
				return;
			}
			System.out.println("Hey there " + greetings.wishUser());
		}

		@Override
		public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
			System.out.println("After");
		}

		@Override
		public String getTestName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}