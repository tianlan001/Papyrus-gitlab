/*
 * Copyright (c) 2006 Borland Software Corporation
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Michael Golubev (Borland) - initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.common.parser.assist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class FixedSetCompletionProcessor extends EObjectCompletionProcessor {

	private final List<String> myProposals;

	public FixedSetCompletionProcessor(List<String> proposals) {
		myProposals = Collections.unmodifiableList(new ArrayList<>(proposals));
	}

	// @unused
	public FixedSetCompletionProcessor(String... proposals) {
		this(Arrays.asList(proposals));
	}

	@Override
	protected Iterable<String> computeContextProposals(EObject context) {
		return myProposals;
	}

}
