/**
 * Copyright (c) 2012 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  	Alban Ménager (Soft-Maint) - Bug 387470 - [EFacet][Custom] Editors
 *  	Grégoire Dupé (Mia-Software) - Bug 387470 - [EFacet][Custom] Editors
 */
package org.eclipse.papyrus.emf.facet.efacet.sdk.ui.internal.exported.widget;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.util.emf.ui.internal.exported.wizard.ISelectETypeWizard;

/**
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IETypedElementWidget<C extends EObject, CW extends Object>
		extends
		IENamedElementWidget<C, CW> {

	boolean isOrdered();

	void setOrdered(boolean value);

	boolean isUnique();

	void setUnique(boolean value);

	int getLowerBound();

	void setLowerBound(int value);

	int getUpperBound();

	void setUpperBound(int value);

	EClassifier getEType();

	void setEType(EClassifier value);

	/**
	 * Press the button to select the type.
	 */
	ISelectETypeWizard<EClassifier> pressTypeButton();

	/**
	 * @return the type of the element.
	 */
	String getElementTypeName();
}
