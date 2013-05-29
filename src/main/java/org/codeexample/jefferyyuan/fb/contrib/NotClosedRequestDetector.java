package org.codeexample.jefferyyuan.fb.contrib;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;

import edu.umd.cs.findbugs.BugAccumulator;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.SourceLineAnnotation;

public class NotClosedRequestDetector extends BytecodeScanningDetector {

	public static final String NOT_CLOSE_SOLR_REQUEST = "NOT_CLOSE_SOLR_REQUEST";

	private final BugReporter bugReporter;
	private final BugAccumulator bugAccumulator;

	private int solrQueryInitCount = 0;
	// private int solrQueryInitPC = 0;
	private List<Integer> solrQueryInitPCList = new ArrayList<Integer>();

	public NotClosedRequestDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
		bugAccumulator = new BugAccumulator(bugReporter);
	}

	@Override
	public void visit(Code obj) {
		solrQueryInitCount = 0;
		super.visit(obj);
		if (solrQueryInitCount > 0) {
			// bugReporter.reportBug(new BugInstance(this,
			// NOT_CLOSE_SOLR_REQUEST,
			// NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
			// this, solrQueryInitPC));
			for (Integer pc : solrQueryInitPCList) {
				bugAccumulator.accumulateBug(new BugInstance(this,
						NOT_CLOSE_SOLR_REQUEST, HIGH_PRIORITY)
						.addClassAndMethod(this), SourceLineAnnotation
						.fromVisitedInstruction(getClassContext(), this, pc));
			}
			bugAccumulator.reportAccumulatedBugs();
		}
	}

	@Override
	public void sawOpcode(int seen) {
		try {
			if (seen == INVOKESPECIAL) {
				boolean isSolrRequest = isSolrRequest();
				if (isSolrRequest) {
					if (getNameConstantOperand().equals("<init>")) {
						solrQueryInitCount++;
						// solrQueryInitPC = getPC();
						solrQueryInitPCList.add(getPC());
					}
				}
			} else if (seen == INVOKEVIRTUAL || seen == INVOKEINTERFACE
					|| seen == INVOKEINTERFACE_QUICK) {
				boolean isSolrRequest = isSolrRequest();

				if (isSolrRequest) {
					if (getNameConstantOperand().equals("close")
							&& getSigConstantOperand().equals("()V")) {
						solrQueryInitCount--;
					}
				}
			}
		} catch (ClassNotFoundException cnfe) {
			bugReporter.reportMissingClass(cnfe);
		}
	}

	private boolean isSolrRequest() throws ClassNotFoundException {
		String className = getClassConstantOperand();
		if (className == null)
			return false;
		JavaClass classClass = Repository.lookupClass(className);
		JavaClass requestClass = Repository
				.lookupClass("org.apache.solr.request.SolrQueryRequest");

		return classClass.instanceOf(requestClass);
	}
}