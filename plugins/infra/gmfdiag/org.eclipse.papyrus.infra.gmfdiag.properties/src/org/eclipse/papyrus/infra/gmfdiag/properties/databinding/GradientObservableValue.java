/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.databinding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.datatype.GradientData;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.GMFObservableValue;

/**
 * An IObservableValue for a Gradient
 *
 * @author Camille Letavernier
 *
 */
public class GradientObservableValue extends GMFObservableValue {

	private ObservableGradientData gradientData;

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 *            The EObject owning the gradient data
	 * @param feature
	 *            The Feature in which the gradient data is set
	 * @param domain
	 *            The editing domain on which the commands will be executed
	 */
	public GradientObservableValue(EObject source, EStructuralFeature feature, EditingDomain domain) {
		super(source, feature, domain);
	}

	@Override
	protected Object doGetValue() {
		if (gradientData == null) {
			GradientData data = (GradientData) eObject.eGet(eStructuralFeature);
			gradientData = new ObservableGradientData(this, data);
		}

		return gradientData;
	}

	@Override
	protected void doSetValue(Object value) {
		super.doSetValue(value);
	}
}
