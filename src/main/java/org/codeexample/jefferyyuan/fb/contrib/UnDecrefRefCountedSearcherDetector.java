package org.codeexample.jefferyyuan.fb.contrib;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;

import edu.umd.cs.findbugs.BugAccumulator;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.SourceLineAnnotation;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

public class UnDecrefRefCountedSearcherDetector extends OpcodeStackDetector {

	public static final String UN_DECREF_REFCOUNTED_SEARCHER = "UN_DECREF_REFCOUNTED_SEARCHER";

	private final BugReporter bugReporter;
	private final BugAccumulator bugAccumulator;

	private int increfCount = 0;
	private List<Integer> increfPcs = new ArrayList<Integer>();

	public UnDecrefRefCountedSearcherDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
		bugAccumulator = new BugAccumulator(bugReporter);
	}

	@Override
	public void visit(Code obj) {
		increfCount = 0;
		super.visit(obj);
		if (increfCount > 0) {
			for (Integer pc : increfPcs) {
				bugAccumulator.accumulateBug(new BugInstance(this,
						UN_DECREF_REFCOUNTED_SEARCHER, HIGH_PRIORITY)
						.addClassAndMethod(this), SourceLineAnnotation
						.fromVisitedInstruction(getClassContext(), this, pc));
			}
			bugAccumulator.reportAccumulatedBugs();
		}
	}

	@Override
	public void sawOpcode(int seen) {
		try {
			if (seen == INVOKEVIRTUAL) {
				String className = getClassConstantOperand();
				if (className == null)
					return;
				if (className.equals("org/apache/solr/core/SolrCore")) {
					boolean mustDecrefMethods = (getNameConstantOperand()
							.equals("getSearcher") && getSigConstantOperand()
							.equals("()Lorg/apache/solr/util/RefCounted;"))
							|| (getNameConstantOperand().equals("getNewestSearcher") && getSigConstantOperand()
									.equals("(Z)Lorg/apache/solr/util/RefCounted;"));
					if (mustDecrefMethods) {
						increfCount++;
						increfPcs.add(getPC());
					}

				} else {
					boolean isClassRefCounted = isSubClassOfRefCounted(className);

					if (isClassRefCounted) {
						if (getNameConstantOperand().equals("decref")
								&& getSigConstantOperand().equals("()V")) {
							increfCount--;
						}
					}
				}
			}
		} catch (ClassNotFoundException cnfe) {
			bugReporter.reportMissingClass(cnfe);
		}
	}

	private boolean isSubClassOfRefCounted(String className)
			throws ClassNotFoundException {
		JavaClass classClass = Repository.lookupClass(className);
		JavaClass requestClass = Repository
				.lookupClass("org.apache.solr.util.RefCounted");
		return classClass.instanceOf(requestClass);
	}

}