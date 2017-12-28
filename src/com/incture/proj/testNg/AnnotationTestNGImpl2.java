package com.incture.proj.testNg;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.incture.annotations.TestInfo;
import com.incture.annotations.TestInfoValues;
import com.incture.utility.listener.CustomListeneresImplementation;

@Listeners(CustomListeneresImplementation.class)
public class AnnotationTestNGImpl2 {


	@BeforeMethod
	public void beforeMethod(){
		System.out.println("i am in beforeMethod() " );

	}
	@AfterMethod
	public void afterMethod(){
		System.out.println("i am in afterMethod()");
	}

	@Test
	@TestInfo(TestCaseId = "ABC", TestCaseName = "TestCaseABC")
	public void test1(){
		System.out.println(" i am in test1() TestCaseId "+TestInfoValues.TestCaseId );
	}
	@Test
	@TestInfo(TestCaseId = "DEF", TestCaseName = "TestCaseDEF")
	public void test2(){
		System.out.println(" i am in test1() TestCaseId "+TestInfoValues.TestCaseId );
	}

	@TestInfo(TestCaseId = "EJK", TestCaseName = "TestCaseEJK")
	public void test3(){
		System.out.println(" i am in test3() TestCaseId "+TestInfoValues.TestCaseId );

	}

}
