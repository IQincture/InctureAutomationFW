package com.incture.proj.testNg;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.incture.utility.listener.*;

import others.unde.listeners1.IExecutionListenerImpl;
import others.unde.listeners1.ISuiteListenerImp;
import others.unde.listeners1.ITestListenerImp;

//@Listeners(ITestListenerImp.class)
@Listeners({IExecutionListenerImpl.class,ITestListenerImp.class,ISuiteListenerImp.class})
public class Trin {

	public void customMethod(){
		System.out.println("this is custom method");
	}
	
	@BeforeMethod
	public void beforeMethod(){
		System.out.println("This is beforeMethod()");
		customMethod();
	}
	@Test
	public void test1(){
		System.out.println("test1()");
	}

	@Test
	public void test2(){
		System.out.println("test2()");
	}

	@AfterMethod
	public void aftermethod(){
		System.out.println("afterMethod()");
	}

}
