package org.codeexample.jefferyyuan.fb.contrib;

import org.junit.Test;

public class FindNakedNotifyTester {

	@Test
	public void ma() throws InterruptedException {
		Object a = new Object();
		a.wait(); // fb reports warning
		while (a.toString().equals("")) {
			synchronized (a) {
				if (a.toString().equals("")) {
					a.wait();
				}
			}
		}
		// synchronized (a) {
		// a.notify();
		// }
		a.notify();
	}
}