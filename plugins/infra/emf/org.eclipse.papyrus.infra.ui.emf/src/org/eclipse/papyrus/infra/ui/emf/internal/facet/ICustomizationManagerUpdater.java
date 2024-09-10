/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.emf.internal.facet;

import java.util.List;

import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.core.internal.CustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;

/**
 *
 * This interface provides the method to update the {@link CustomizationManager}
 */
public interface ICustomizationManagerUpdater {

	/**
	 * This method is able to define which customizations to apply and apply them
	 */
	public void applyCustomizations();

	/**
	 * This method applies the customizations in parameter to the {@link CustomizationManager}
	 *
	 * @param customizationsToApply
	 *            the list of customization to apply
	 */
	public void applyCustomizations(final List<Customization> customizationsToApply);

	/**
	 * This method reset the customization to the initial definition
	 */
	public void resetToDefaultCustomizations();

	/**
	 * This method allows to save the state of the customization after user changes
	 */
	public void saveUserCustomizationsState();

	/**
	 *
	 * @return
	 *         the customization manager
	 */
	public ICustomizationManager getCustomizationManager();

}
