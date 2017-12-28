package com.incture.proj.testNg;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.incture.annotations.TestInfo;
import com.incture.annotations.TestInfoValues;
import com.incture.utility.listener.CustomListeneresImplementation;

@Listeners(CustomListeneresImplementation.class)
public class AnnotationTestNGImpl {


	@BeforeMethod
	public void beforeMethod(){
		System.out.println("i am in beforeMethod() " );

	}
	@AfterMethod
	public void afterMethod(){
		System.out.println("i am in afterMethod()");
	}

	@Test
	@TestInfo(TestCaseId = "124", TestCaseName = "TestCase1")
	public void test1(){
		System.out.println(" i am in test1() TestCaseId "+TestInfoValues.TestCaseId );
	}
	@Test
	@TestInfo(TestCaseId = "125", TestCaseName = "TestCase2")
	public void test2(){
		System.out.println(" i am in test1() TestCaseId "+TestInfoValues.TestCaseId );
	}

	@TestInfo(TestCaseId = "126", TestCaseName = "TestCase3")
	public void test3(){
		System.out.println(" i am in test3() TestCaseId "+TestInfoValues.TestCaseId );

	}

}
