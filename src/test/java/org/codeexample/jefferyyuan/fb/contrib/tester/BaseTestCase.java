package org.codeexample.jefferyyuan.fb.contrib.tester;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.AnalysisCacheToAnalysisContextAdapter;
import edu.umd.cs.findbugs.ba.AnalysisContext;

public class BaseTestCase {

	@org.junit.Before
	public void setup() {
		AnalysisCacheToAnalysisContextAdapter analysisContext = new AnalysisCacheToAnalysisContextAdapter();
		AnalysisContext.setCurrentAnalysisContext(analysisContext);
	}

	public BaseTestCase() {
		super();
	}

	protected void printBugReporter(Detector detector, BugReporter bugReporter) {
		System.err.println("After appplying " + detector);
		BugCollection collection = bugReporter.getBugCollection();
		for (BugInstance bugInstance : collection.getCollection()) {
			System.err.println(bugInstance.toString());
		}
	}

	protected void assertFindbug(BugReporter bugReporter, String type) {
		Iterator<BugInstance> it = bugReporter.getBugCollection().iterator();
		boolean found = false;
		while (it.hasNext()) {
			BugInstance bug = it.next();

			if (type.equals(bug.getType())) {
				found = true;
			}
		}
		assertEquals(true, found);
	}

}