package org.maven.kyd.frameowrk.test;

import org.testng.annotations.Test;
import org.qa.rediffmail.kyd.engine.*;
public class LoginTest {
	public KydEngine kyd;
	@Test
	public void loginTestScenario() {
		kyd=new KydEngine();
		kyd.startExecution("loginTest");
	}
	@Test
	public void signUp()
	{
		kyd=new KydEngine();
		kyd.startExecution("SignUpTest");
	}

}
