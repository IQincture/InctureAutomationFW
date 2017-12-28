package com.incture.utility.listener;


import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.incture.annotations.TestInfo;
import com.incture.annotations.TestInfoValues;


@SuppressWarnings({"unchecked"})
public class AnnotationListener implements IInvokedMethodListener  {




	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		//method.isConfigurationMethod() &&
		if(annotationPresent(method, TestInfo.class) ) {
			TestInfoValues.TestCaseId = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TestInfo.class).TestCaseId();
			TestInfoValues.TestCaseName = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TestInfo.class).TestCaseName();
			System.out.println("TestCaseId: " + TestInfoValues.TestCaseId + " TestCaseName: " + TestInfoValues.TestCaseName);
		}
	}



	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if(method.isConfigurationMethod() && annotationPresent(method, TestInfo.class) ) {
			TestInfoValues.TestCaseId = "";
			TestInfoValues.TestCaseName = "";
		}

	}

	@SuppressWarnings("rawtypes")
	private boolean annotationPresent(IInvokedMethod method, Class clazz) {
		boolean retVal = method.getTestMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(clazz) ? true : false;
		return retVal;
	}

	///result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(MyTestNGAnnotation.class).name();

	/*
		 for(ITestNGMethod m1 : context.getAllTestMethods()) {
	       if(m1.getConstructorOrMethod().getMethod().isAnnotationPresent(MyTestNGAnnotation.class)) {
	           //capture metadata information.
	           name = m1.getConstructorOrMethod().getMethod().getAnnotation(MyTestNGAnnotation.class).name();
	          }
	   }
	 */

}