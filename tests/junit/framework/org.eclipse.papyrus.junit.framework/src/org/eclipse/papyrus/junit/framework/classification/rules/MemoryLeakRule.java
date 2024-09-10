/*
 * Copyright (c) 2014, 2015 CEA, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 451013
 *   Christian W. Damus - bug 483721
 *
 */
package org.eclipse.papyrus.junit.framework.classification.rules;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


/**
 * A simple JUnit rule for tracking memory leaks. Simply {@linkplain #add(Object) add objects} during your test execution, make assertions if desired,
 * and on successful completion of the body of the test, this rule verifies that none of the tracked objects have leaked.
 * Tests that are sensitive to references being retained temporarily via {@link SoftReference}s should be annotated as {@link SoftReferenceSensitive
 * &#x40;SoftReferenceSensitive} so that the rule may employ extra measures to ensure that soft references are cleared.
 * 
 * @see SoftReferenceSensitive
 */
public class MemoryLeakRule extends TestWatcher {
	private static final boolean DEBUG = Boolean.getBoolean("MemoryLeakRule.debug");

	private static final int DEQUEUE_REF_ITERATIONS = 3;

	private static final int DEQUEUE_REF_TIMEOUT = 1000; // Millis

	private static final int GC_ITERATIONS = 10;

	private static final int CLEAR_SOFT_REFS_ITERATIONS = 3;

	private static final Map<Class<?>, Boolean> WARMED_UP_SUITES = new WeakHashMap<Class<?>, Boolean>();

	private static boolean warmingUp;

	private ReferenceQueue<Object> queue;

	private List<WeakReference<Object>> tracker;

	private String testName;

	private Class<?> testClass;

	private boolean isSoftReferenceSensitive;

	public MemoryLeakRule() {
		super();
	}

	public void add(Object leak) {
		assertThat("Cannot track null references for memory leaks.", leak, notNullValue());

		if (queue == null) {
			queue = new ReferenceQueue<Object>();
			tracker = Lists.newArrayList();
		}

		tracker.add(new WeakReference<Object>(leak, queue));
	}

	public String getTestName() {
		return testName;
	}

	@Override
	protected void starting(Description description) {
		testName = description.getMethodName();
		testClass = description.getTestClass();

		isSoftReferenceSensitive = description.getAnnotation(SoftReferenceSensitive.class) != null;

		if (isSoftReferenceSensitive && !isWarmedUp() && !warmingUp) {
			// Warm up the soft-reference sensitive tests by running this one up-front, first,
			// because the first such test to execute always results in a spurious failure
			// (at least, such is the case on the Mac build of JRE 1.6)
			warmingUp = true;
			try {
				warmUp();
			} finally {
				warmingUp = false;
			}
		}
	}

	@Override
	protected void succeeded(Description description) {
		// If the test's assertions (if any) all succeeded, then check for leaks on the way out
		if (tracker == null) {
			// No leaks to assert
			return;
		}

		// Assert that our tracked objects are now all unreachable
		while (!tracker.isEmpty()) {
			Reference<?> ref = dequeueTracker();

			for (int i = 0; ((ref == null) && isSoftReferenceSensitive) && (i < CLEAR_SOFT_REFS_ITERATIONS); i++) {
				// Maybe there are soft references retaining our objects? Desperation move.
				// On some platforms, our simulated OOME doesn't actually purge all soft
				// references (contrary to Java spec!), so we have to repeat
				forceClearSoftReferenceCaches();

				// Try once more
				ref = dequeueTracker();
			}

			if (!tracker.remove(ref) && !tracker.isEmpty()) {
				// The remaining tracked elements are leaked
				final String leaks = Joiner.on('\n').join(Iterables.transform(tracker, label()));
				if (warmingUp) {
					debug("Warm-up detected leaks: %s%n", leaks.replace('\n', ' '));
				}
				fail("One or more objects leaked:\n" + leaks);
				break; // Unreachable
			}
		}
	}

	@Override
	protected void finished(Description description) {
		// Clean up
		tracker = null;
		queue = null;
	}

	Reference<?> dequeueTracker() {
		Reference<?> result = null;

		try {
			for (int i = 0; (result == null) && (i < DEQUEUE_REF_ITERATIONS); i++) {
				// Try to force GC
				collectGarbage();

				result = queue.remove(DEQUEUE_REF_TIMEOUT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("JUnit was interrupted");
		}

		return result;
	}

	Function<WeakReference<?>, String> label() {
		return new Function<WeakReference<?>, String>() {

			@Override
			public String apply(WeakReference<?> input) {
				return label(input.get());
			}
		};
	}

	String label(Object input) {
		String result = null;

		if (!(input instanceof EObject)) {
			result = String.valueOf(input);
		} else {
			EObject object = (EObject) input;
			EClass eclass = object.eClass();
			String label = null;

			EStructuralFeature nameFeature = eclass.getEStructuralFeature("name"); //$NON-NLS-1$
			if (nameFeature != null) {
				label = String.valueOf(object.eGet(nameFeature));
			} else {
				// Look for anything label-like
				for (EAttribute next : eclass.getEAllAttributes()) {
					if (!next.isMany() && next.getEAttributeType().getInstanceClass() == String.class) {
						label = (String) object.eGet(next);
						if ((label != null) && !label.isEmpty()) {
							break;
						}
					}
				}
			}

			result = String.format("<%s> %s", eclass.getName(), label);
		}

		return result;
	}

	void collectGarbage() {
		// Try a few times to decrease the amount of used heap space
		final Runtime rt = Runtime.getRuntime();

		Long usedMem = rt.totalMemory() - rt.freeMemory();
		Long prevUsedMem = usedMem;

		for (int i = 0; (prevUsedMem <= usedMem) && (i < GC_ITERATIONS); i++) {
			rt.gc();
			Thread.yield();

			prevUsedMem = usedMem;
			usedMem = rt.totalMemory() - rt.freeMemory();
		}
	}

	void forceClearSoftReferenceCaches() {
		// There are components in the Eclipse workbench that maintain soft references to objects for
		// performance caches. For example, the the Common Navigator Framework used by Model Explorer
		// caches mappings of elements in the tree to the content extensions that provided them using
		// EvalutationReferences [sic] that are SoftReferences

		// This is a really gross HACK and runs the risk that some other thread(s) also may see OOMEs!
		try {
			List<Object[]> hog = Lists.newLinkedList();
			for (;;) {
				hog.add(new Object[getLargeMemorySize()]);
			}
		} catch (OutOfMemoryError e) {
			// Good! The JVM guarantees that all soft references are cleared before throwing OOME,
			// so we can be assured that they are now cleared
		} finally {
			if (warmingUp) {
				// We have successfully warmed up the soft-references hack
				WARMED_UP_SUITES.put(testClass, true);
			}
		}
	}

	private static int getLargeMemorySize() {
		// These 64 megs are multiplied by the size of a pointer!
		return 64 * 1024 * 1024;
	}

	private boolean isWarmedUp() {
		return Boolean.TRUE.equals(WARMED_UP_SUITES.get(testClass));
	}

	private void warmUp() {
		// The first test that relies on the soft-reference clearing hack will
		// always fail, so run such a test once up-front. Call this a metahack

		try {
			debug("Warming up test suite: %s (%s)%n", testClass.getName(), testName);
			new JUnitCore().run(Request.method(testClass, testName));
		} catch (Exception e) {
			// Fine, so the warm-up didn't work
			e.printStackTrace();
		}
	}

	private static void debug(String format, Object... args) {
		if (DEBUG) {
			System.err.printf("[MEM] " + format, args);
		}
	}

	//
	// Nested types
	//

	/**
	 * Annotates a test that is sensitive to references being cached by {@link SoftReference}s.
	 * Such tests will take additional drastic measures to try to force the JVM to clear soft
	 * reference caches in order to release all possible references to objects tracked for leaks.
	 * Because the first such test is expected always to result in a spurious failure (at least,
	 * such is the case on the Mac OS X build of J2SE 1.6), the rule "warms up" the test suite
	 * by running one such test in isolation before running any others.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface SoftReferenceSensitive {
		// Empty annotation
	}
}
