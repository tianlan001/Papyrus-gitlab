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

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.ProgressMonitorWrapper;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * A progress monitor that prints progress to standard output or some other
 * {@link PrintStream}, optionally wrapping some other monitor.
 */
public class PrintingProgressMonitor extends ProgressMonitorWrapper {
	private final PrintStream printTo;

	private boolean first;

	private Predicate<String> filter = Predicates.alwaysTrue();

	/**
	 * Initializes me to print to standard output.
	 */
	public PrintingProgressMonitor() {
		this(System.out, new NullProgressMonitor());
	}

	/**
	 * Initializes me to print to some stream.
	 */
	public PrintingProgressMonitor(PrintStream printTo) {
		this(printTo, new NullProgressMonitor());
	}

	/**
	 * Initializes me to print to some stream and wrap another {@code monitor).
	 */
	public PrintingProgressMonitor(PrintStream printTo, IProgressMonitor monitor) {
		super(monitor);

		this.printTo = printTo;
	}

	/**
	 * Adds a filter regular expression that matches task messages to exclude from
	 * the output (to promote quieter progress when appropriate).
	 * 
	 * @param pattern
	 *            a regular expression pattern for task messages to suppress
	 * 
	 * @return myself, for the convenience of call chaining
	 */
	public PrintingProgressMonitor filter(String pattern) {
		Pattern regex = Pattern.compile(pattern);
		final Matcher m = regex.matcher(""); //$NON-NLS-1$

		Predicate<String> filter = new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				m.reset(input);
				return !m.find();
			}
		};

		this.filter = Predicates.and(filter, this.filter);

		return this;
	}

	private void echo(boolean dashN, String text) {
		echo(true, false, text);
	}

	private void echo(boolean initialNewline, boolean terminalNewline, String text) {
		if (filter.apply(text)) {
			if (first) {
				first = false;
			} else if (initialNewline) {
				printTo.println();
			}

			printTo.print(text);

			if (terminalNewline) {
				printTo.println();
			}
		}
	}

	@Override
	public void beginTask(String name, int totalWork) {
		echo(true, name);
		super.beginTask(name, totalWork);
	}

	@Override
	public void setTaskName(String name) {
		echo(true, name);
		super.setTaskName(name);
	}

	@Override
	public void subTask(String name) {
		echo(true, name);
		super.subTask(name);
	}

	@Override
	public void worked(int work) {
		echo(false, false, ".");
		super.worked(work);
	}

	@Override
	public void done() {
		echo(false, true, " Done.");
		super.done();
	}

}
