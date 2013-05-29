package org.codeexample.jefferyyuan.fb.contrib.tester;

import org.codeexample.jefferyyuan.fb.contrib.DemoDecrefRefCountedSearcher;
import org.codeexample.jefferyyuan.fb.contrib.DemoUnClosedSolrRequest;
import org.codeexample.jefferyyuan.fb.contrib.NotClosedRequestDetector;
import org.junit.Test;

import com.youdevise.fbplugins.tdd4fb.DetectorAssert;

import edu.umd.cs.findbugs.BugReporter;

public class NotClosedRequestDetectorTester extends BaseTestCase {

	@Test
	public void demoDecrefRefCountedSearcher() throws Exception {
		BugReporter bugReporter = DetectorAssert.bugReporterForTesting();
		NotClosedRequestDetector detector = new NotClosedRequestDetector(
				bugReporter);

		DetectorAssert.assertNoBugsReported(DemoDecrefRefCountedSearcher.class,
				detector, bugReporter);
	}

	@Test
	public void demoUnClosedSolrRequest() throws Exception {
		BugReporter bugReporter = DetectorAssert.bugReporterForTesting();
		NotClosedRequestDetector detector = new NotClosedRequestDetector(
				bugReporter);

		DetectorAssert.assertBugReported(DemoUnClosedSolrRequest.class,
				detector, bugReporter);
		printBugReporter(detector, bugReporter);

		assertFindbug(bugReporter,
				NotClosedRequestDetector.NOT_CLOSE_SOLR_REQUEST);
	}
}
