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

package org.eclipse.papyrus.infra.editor.welcome.tests;

import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeSection;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Hamcrest matchers for the Welcome model.
 */
public class WelcomeMatchers {

	private WelcomeMatchers() {
		super();
	}

	public static Matcher<WelcomeSection> identifiedBy(String id) {
		return new BaseMatcher<WelcomeSection>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("WelcomeSection identified by ").appendText(id);
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof WelcomeSection)
						&& ((WelcomeSection) item).getIdentifiers().contains(id);
			}
		};
	}

	public static Matcher<Object> isPageVisible(ISashWindowsContainer sashContainer) {
		return new BaseMatcher<Object>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("open in a visible editor page");
			}

			@Override
			public boolean matches(Object item) {
				return sashContainer.getVisiblePages().stream()
						.map(IPageUtils::getRawModel)
						.anyMatch(o -> o == item);
			}
		};
	}
}
