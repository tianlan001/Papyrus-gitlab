/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.junit.utils;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.junit.matchers.MoreMatchers;
import org.hamcrest.Matcher;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

/**
 * A configurable log listener to help tests to make assertions about the
 * messages that are logged.
 */
public class LogTracker implements ILogListener {

	private String bundle;

	private Predicate<? super IStatus> filter;

	private ILog log;

	private final List<IStatus> messages = Lists.newArrayList();

	public LogTracker() {
		super();
	}

	/**
	 * Start tracking the specified {@code bundle}'s log.
	 * 
	 * @param bundle
	 *            the symbolic name of the bundle whose log is to be tracked
	 */
	public void start(String bundle) {
		start(bundle, null);
	}

	/**
	 * Start tracking the specified {@code bundle}'s log for particular messages.
	 * 
	 * @param bundle
	 *            the symbolic name of the bundle whose log is to be tracked
	 * @param filter
	 *            a filter matching messages that should be recorded, or {@code null} to record all messages
	 */
	public void start(String bundle, Predicate<? super IStatus> filter) {
		if (filter == null) {
			filter = Predicates.alwaysTrue();
		}

		this.bundle = bundle;
		this.filter = filter;
		this.log = Platform.getLog(Platform.getBundle(bundle));

		// Individual ILog instances don't notify listeners
		Platform.addLogListener(this);
	}

	public void dispose() {
		if (log != null) {
			Platform.removeLogListener(this);
			log = null;

			clear();
			bundle = null;
			filter = null;
		}
	}

	public void clear() {
		messages.clear();
	}

	@Override
	public void logging(IStatus status, String plugin) {
		if ((plugin.equals(bundle) || status.getPlugin().equals(bundle)) && filter.apply(status)) {
			messages.add(status);
		}
	}

	/**
	 * Assert that either there were no messages recorded, or they all satisfy an {@code assertion}.
	 */
	public void assertAll(Matcher<? super IStatus> assertion) {
		@SuppressWarnings("unchecked")
		Matcher<IStatus> hamcrestSignatureWorkaround = (Matcher<IStatus>) assertion;
		assertThat(messages, either(MoreMatchers.<IStatus> emptyIterable()).or(everyItem(hamcrestSignatureWorkaround)));
	}

	/**
	 * Assert at least one message was recorded, and all recorded messages satisfy an {@code assertion}.
	 */
	public void assertExistAll(Matcher<? super IStatus> assertion) {
		@SuppressWarnings("unchecked")
		Matcher<IStatus> hamcrestSignatureWorkaround = (Matcher<IStatus>) assertion;
		assertThat(messages, both(not(MoreMatchers.<IStatus> emptyIterable())).and(everyItem(hamcrestSignatureWorkaround)));
	}

	/**
	 * Assert that either there were no messages recorded, or they all satisfy an {@code assertion}.
	 */
	public void assertNone(Matcher<? super IStatus> assertion) {
		@SuppressWarnings("unchecked")
		Matcher<IStatus> hamcrestSignatureWorkaround = (Matcher<IStatus>) assertion;
		assertThat(messages, everyItem(not(hamcrestSignatureWorkaround)));
	}
}
