/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.usecase.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Class in charge to manage the messages
 *
 * @author flefevre
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.diagram.usecase.messages.messages"; //$NON-NLS-1$
	public static String UsecaseOwnerComposite_TITLE;
	public static String UsecaseOwnerComposite_OWNER_KEEP;
	public static String UsecaseOwnerComposite_OWNER_SWITCH;
	public static String UsecaseSelectionDialog_TITLE;
	public static String UsecaseSubjectComposite_CONTEXT;
	public static String UsecaseSubjectComposite_KEEP_SUBJECT;
	public static String UsecaseSubjectComposite_SWITCH_SUBJECT;
	public static String UsecaseToSubjectinUsecaseDiagramDropStrategy_DESCRIPTION;
	public static String UsecaseToSubjectinUsecaseDiagramDropStrategy_LABEL;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
