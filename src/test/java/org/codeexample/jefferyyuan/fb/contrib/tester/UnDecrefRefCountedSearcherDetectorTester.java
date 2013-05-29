package org.codeexample.jefferyyuan.fb.contrib.tester;

import org.codeexample.jefferyyuan.fb.contrib.DemoDecrefRefCountedSearcher;
import org.codeexample.jefferyyuan.fb.contrib.DemoUnDecrefRefCountedSearcher;
import org.codeexample.jefferyyuan.fb.contrib.DemoUnDecrefRefCountedSearcherGetNewSearcher;
import org.codeexample.jefferyyuan.fb.contrib.UnDecrefRefCountedSearcherDetector;
import org.junit.Test;

import com.youdevise.fbplugins.tdd4fb.DetectorAssert;

import edu.umd.cs.findbugs.BugReporter;

public class UnDecrefRefCountedSearcherDetectorTester extends BaseTestCase {

	@Test
	public void demoDecrefRefCountedSearcher() throws Exception {
		BugReporter bugReporter = DetectorAssert.bugReporterForTesting();
		UnDecrefRefCountedSearcherDetector detector = new UnDecrefRefCountedSearcherDetector(
				bugReporter);

		DetectorAssert.assertNoBugsReported(DemoDecrefRefCountedSearcher.class,
				detector, bugReporter);
	}

	@Test
	public void demoUnDecrefRefCountedSearcher() throws Exception {
		BugReporter bugReporter = DetectorAssert.bugReporterForTesting();

		UnDecrefRefCountedSearcherDetector detector = new UnDecrefRefCountedSearcherDetector(
				bugReporter);

		DetectorAssert.assertBugReported(DemoUnDecrefRefCountedSearcher.class,
				detector, bugReporter);
		printBugReporter(detector, bugReporter);

		assertFindbug(
				bugReporter,
				UnDecrefRefCountedSearcherDetector.UN_DECREF_REFCOUNTED_SEARCHER);
	}

	@Test
	public void demoUnDecrefRefCountedSearcherGetNewSearcher() throws Exception {
		BugReporter bugReporter = DetectorAssert.bugReporterForTesting();

		UnDecrefRefCountedSearcherDetector detector = new UnDecrefRefCountedSearcherDetector(
				bugReporter);

		DetectorAssert.assertBugReported(
				DemoUnDecrefRefCountedSearcherGetNewSearcher.class, detector,
				bugReporter);
		printBugReporter(detector, bugReporter);

		assertFindbug(
				bugReporter,
				UnDecrefRefCountedSearcherDetector.UN_DECREF_REFCOUNTED_SEARCHER);
	}
}
