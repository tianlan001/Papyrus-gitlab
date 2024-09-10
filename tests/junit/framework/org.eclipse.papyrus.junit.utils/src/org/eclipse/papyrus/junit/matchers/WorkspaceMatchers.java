/*****************************************************************************
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.junit.matchers;

import static org.hamcrest.CoreMatchers.is;

import org.eclipse.core.resources.IMarker;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

/**
 * Hamcrest matchers for the <em>Eclipse Workspace</em> API.
 *
 * @since 3.0
 */
public class WorkspaceMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private WorkspaceMatchers() {
		super();
	}

	/**
	 * Create a matcher for markers by severity.
	 *
	 * @param severity
	 *            the marker severity to match
	 * @return the marker matcher
	 */
	public static Matcher<IMarker> isMarkerSeverity(final int severity) {
		return new FeatureMatcher<>(is(severity), "marker severity", "severity") { //$NON-NLS-1$//$NON-NLS-2$
			@Override
			protected Integer featureValueOf(IMarker actual) {
				return actual.getAttribute(IMarker.SEVERITY, -1);
			}
		};
	}

	/**
	 * Create a matcher for markers by message.
	 *
	 * @param messageMatcher
	 *            a message matcher to match markers
	 * @return the marker matcher
	 */
	public static Matcher<IMarker> isMarkerMessage(Matcher<? super String> messageMatcher) {
		return new FeatureMatcher<IMarker, String>(messageMatcher, "marker message", "message") { //$NON-NLS-1$//$NON-NLS-2$
			@Override
			protected String featureValueOf(IMarker actual) {
				return actual.getAttribute(IMarker.MESSAGE, "");
			}
		};
	}

}
