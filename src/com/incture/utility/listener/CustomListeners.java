package com.incture.utility.listener;
//
import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.incture.proj.suite.parameters.XmlParameters;

@SuppressWarnings("unused")
public interface CustomListeners extends ISuiteListener,ITestListener{//ITestNGListener

	void onStart(ISuite suite);
	public void onFinish(ISuite suite);

	void onTestStart(ITestResult result);

	void onTestSuccess(ITestResult result);

	void onTestFailure(ITestResult result);

	void onTestSkipped(ITestResult result);

	void onTestFailedButWithinSuccessPercentage(ITestResult result);

	void onStart(ITestContext context);

	void onFinish(ITestContext context);

}
