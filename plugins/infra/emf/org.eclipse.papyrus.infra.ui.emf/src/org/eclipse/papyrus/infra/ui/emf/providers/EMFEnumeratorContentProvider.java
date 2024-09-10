/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.emf.providers;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.widgets.providers.AbstractStaticContentProvider;

/**
 * An IStaticContentProvider for EMF enumerators.
 *
 * @author Camille Letavernier
 */
public class EMFEnumeratorContentProvider extends AbstractStaticContentProvider {

	/**
	 * The feature representing the Enumerator for this ContentProvider
	 */
	protected EStructuralFeature feature;

	/**
	 * Constructs an EcoreEnumerator for the given Structural Feature
	 *
	 * @param feature
	 */
	public EMFEnumeratorContentProvider(EStructuralFeature feature) {
		this.feature = feature;
	}

	public Object[] getElements() {
		EClassifier type = feature.getEType();
		EEnum enumerated = (EEnum) type;
		EEnumLiteral[] literals = enumerated.getELiterals().toArray(new EEnumLiteral[0]);
		Enumerator[] values = new Enumerator[literals.length];

		int i = 0;
		for (EEnumLiteral literal : literals) {
			Enumerator value = literal.getInstance();
			values[i++] = value;
		}

		return values;
	}

}
