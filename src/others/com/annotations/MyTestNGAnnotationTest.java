package others.com.annotations;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.incture.annotations.TestInfo;
//@Listeners(MyTestNGAnnotationListener.class)
public class MyTestNGAnnotationTest {
	 
	@BeforeMethod
	public void loginMethod(){
		System.out.println("beforeMethod()");
	}

	@Test
	@TestInfo(TestCaseId = "123", TestCaseName = "BeforeTestCase1")
	public void testMyAnnotation() {
		System.out.println("The metadata from @MyTestNGAnnotation should have already been printed.");
		System.out.println("Starting my test method...");
	}

}
