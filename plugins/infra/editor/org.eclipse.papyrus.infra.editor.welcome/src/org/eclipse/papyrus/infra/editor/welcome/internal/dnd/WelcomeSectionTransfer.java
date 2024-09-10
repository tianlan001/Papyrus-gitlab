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

package org.eclipse.papyrus.infra.editor.welcome.internal.dnd;

import org.eclipse.papyrus.infra.ui.dnd.PapyrusTransfer;
import org.eclipse.ui.forms.widgets.Section;

/**
 * A transfer type for sections dragged around in the welcome page (to rearrange them).
 */
public class WelcomeSectionTransfer extends PapyrusTransfer<Section> {
	private static final WelcomeSectionTransfer INSTANCE = new WelcomeSectionTransfer();

	private WelcomeSectionTransfer() {
		super(Section.class);
	}

	public static WelcomeSectionTransfer getInstance() {
		return INSTANCE;
	}
}
