/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.uml.profile.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;


/**
 * A label provider for {@link EPackage}s that are unknown schemas (demand-created packages).
 */
public class UnknownSchemaEPackageLabelProvider extends EMFLabelProvider implements IFilteredLabelProvider {

	public boolean accept(Object element) {
		boolean result = false;

		EObject eobject = EMFHelper.getEObject(element);
		if (eobject instanceof EPackage) {
			// Typically the only EPackages that don't have names are unknown schema but they do have namespace prefixes
			EPackage epackage = (EPackage) eobject;
			result = (epackage.getName() == null) && (epackage.getNsPrefix() != null);
		}

		return result;
	}

	@Override
	protected String getText(EObject element) {
		String result;

		if (element instanceof EPackage) {
			EPackage epackage = (EPackage) element;
			result = NLS.bind("({0}) - unknown schema", epackage.getNsPrefix());
		} else {
			result = super.getText(element);
		}

		return result;
	}

}
