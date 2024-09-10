/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.expansion.tests.project.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.view.factories.ViewFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Dummy view factory class for testing.
 */
public class TestViewFactory implements ViewFactory {

	@Override
	public View createView(IAdaptable arg0, View arg1, String arg2, int arg3, boolean arg4, PreferencesHint arg5) {
		// This class just needs to exist
		return null;
	}

}
