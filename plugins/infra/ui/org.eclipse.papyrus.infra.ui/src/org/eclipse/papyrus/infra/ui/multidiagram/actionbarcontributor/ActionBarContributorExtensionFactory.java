/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.papyrus.infra.core.extension.BadNameExtensionException;
import org.eclipse.papyrus.infra.core.extension.ExtensionException;
import org.eclipse.papyrus.infra.core.extension.ExtensionUtils;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * A factory used to create ActionBarContributor object from Eclipse extensions
 * points elements.
 *
 * @author Cedric Dumoulin
 * @auhtor Patrick Tessier
 * @since 1.2
 */
public class ActionBarContributorExtensionFactory extends ExtensionUtils {

	/** singleton eINSTANCE of this class */
	public static final ActionBarContributorExtensionFactory eINSTANCE = new ActionBarContributorExtensionFactory();

	/** constant for the editor diagram **/
	public static final String EDITOR_ACTIONBARCONTRIBUTOR_EXTENSIONPOINT = "" + "actionBarContributor";

	/** constant for the attribute factoryClass **/
	public static final String CONTEXTCLASS_ATTRIBUTE = "implementingClass";

	/** constant for the attribute contextId **/
	public static final String ID_ATTRIBUTE = "id";

	/**
	 * @return the eINSTANCE
	 */
	public static ActionBarContributorExtensionFactory getInstance() {
		return eINSTANCE;
	}

	/**
	 * Create a ContextDescriptor instance corresponding to the
	 * ConfigurationElement.
	 *
	 * @param element
	 *            an {@link IConfigurationElement} see eclipse extension point
	 * @return a ContextDescriptor structure that contains information to the
	 *         diagram context
	 * @throws BadNameExtensionException
	 **/
	public ActionBarContributorDescriptor createActionBarContributorDescriptor(IConfigurationElement element) throws ExtensionException {
		ActionBarContributorDescriptor res;

		checkTagName(element, EDITOR_ACTIONBARCONTRIBUTOR_EXTENSIONPOINT);

		res = new ActionBarContributorDescriptor();
		res.contextClass = (Class<EditorActionBarContributor>) parseClass(element, CONTEXTCLASS_ATTRIBUTE, EDITOR_ACTIONBARCONTRIBUTOR_EXTENSIONPOINT);
		res.contextId = element.getAttribute(ID_ATTRIBUTE);

		return res;
	}

}
