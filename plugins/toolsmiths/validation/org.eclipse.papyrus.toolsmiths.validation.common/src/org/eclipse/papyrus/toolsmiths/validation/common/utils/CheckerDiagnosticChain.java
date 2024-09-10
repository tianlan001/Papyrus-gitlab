/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.DiagnosticEquivalence;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2.MessageArgument;

/**
 * A diagnostic chain implementation that supports the merging of {@link Diagnostic}s for
 * {@linkplain IPluginChecker2#dynamicMessageArgument(int, Object) dynamic message arguments}.
 *
 * @see IPluginChecker2#dynamicMessageArgument(int, Object)
 * @see IPluginChecker2#getDynamicMessage(Diagnostic)
 */
public final class CheckerDiagnosticChain implements DiagnosticChain, Iterable<Diagnostic> {

	private final DiagnosticEquivalence diagnosticEquivalence;
	private final List<Diagnostic> diagnostics = new ArrayList<>();
	private int severity;

	/**
	 * Initializes me with the specified diagnostic equivalence semantics.
	 *
	 * @param diagnosticEquivalence
	 *            my diagnostic equivalence semantics
	 */
	public CheckerDiagnosticChain(DiagnosticEquivalence diagnosticEquivalence) {
		super();

		this.diagnosticEquivalence = diagnosticEquivalence;
	}

	/**
	 * Initializes me with the default diagnostic equivalence.
	 *
	 * @see DiagnosticEquivalence#DEFAULT
	 */
	public CheckerDiagnosticChain() {
		this(DiagnosticEquivalence.DEFAULT);
	}

	@Override
	public void add(Diagnostic diagnostic) {
		// Find an existing equivalent diagnostic and merge the message arguments
		Optional<Diagnostic> existing = !IPluginChecker2.hasDynamicMessage(diagnostic)
				? Optional.empty()
				: stream().filter(diag -> diagnosticEquivalence.equals(diag, diagnostic)).findFirst();

		existing.ifPresentOrElse(target -> {
			// The diagnostics could not have been equivalent had they not the same number of arguments
			List<MessageArgument> targetArgs = MessageArgument.stream(target).collect(Collectors.toList());
			List<MessageArgument> sourceArgs = MessageArgument.stream(diagnostic).collect(Collectors.toList());
			IntStream.range(0, targetArgs.size()).forEach(i -> targetArgs.get(i).merge(sourceArgs.get(i)));
		}, () -> {
			// This is the first diagnostic on this dynamic message pattern
			diagnostics.add(diagnostic);
			severity = Math.max(severity, diagnostic.getSeverity());
		});
	}

	@Override
	public void addAll(Diagnostic diagnostic) {
		diagnostic.getChildren().forEach(this::add);
	}

	@Override
	public void merge(Diagnostic diagnostic) {
		if (diagnostic.getChildren().isEmpty()) {
			add(diagnostic);
		} else {
			addAll(diagnostic);
		}
	}

	@Override
	public Iterator<Diagnostic> iterator() {
		return Collections.unmodifiableList(diagnostics).iterator();
	}

	public Stream<Diagnostic> stream() {
		return diagnostics.stream();
	}

	public int getSeverity() {
		return severity;
	}

}
