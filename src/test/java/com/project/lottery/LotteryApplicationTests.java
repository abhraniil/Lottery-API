package com.project.lottery;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Ticket-api Test Suite")
@SelectPackages("com.project.lottery")
@IncludeClassNamePatterns(".*Test")
class LotteryApplicationTests {

}
