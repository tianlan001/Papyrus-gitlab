/*******************************************************************************
 * Copyright (c) 2009, 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - initial API and implementation
 *    Nicolas Guyomar (Mia-Software) - Bug 349546 - EMF Facet facetSet editor
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.efacet.ui.internal.widget;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.emf.facet.efacet.ui.internal.composites.FilteredElementSelectionComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * A control to select a metamodel from a list of metamodels, that can be filtered using the
 * associated text field.
 */
public class MetamodelSelectionControl extends FilteredElementSelectionComposite {

	public MetamodelSelectionControl(final Composite parent) {
		super(parent, true, false);

		final Set<String> uris = new TreeSet<String>();
		for (final Object name : ((Map<?, ?>) EPackage.Registry.INSTANCE).keySet()) {
			uris.add((name).toString());
		}
		setElements(uris.toArray());
	}
}
