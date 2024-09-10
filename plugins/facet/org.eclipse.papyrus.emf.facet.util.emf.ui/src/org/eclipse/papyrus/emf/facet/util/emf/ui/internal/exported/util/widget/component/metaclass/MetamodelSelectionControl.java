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
 */
package org.eclipse.papyrus.emf.facet.util.emf.ui.internal.exported.util.widget.component.metaclass;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.emf.facet.util.ui.internal.exported.util.composite.FilteredElementSelectionComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * A control to select a metamodel from a list of metamodels, that can be
 * filtered using the associated text field.
 *
 * @since 0.3
 */
public class MetamodelSelectionControl extends
		FilteredElementSelectionComposite {

	public MetamodelSelectionControl(final Composite parent) {
		super(parent, true, false);
		final Set<String> uris = getUris();
		setElements(uris.toArray());
	}

	private static Set<String> getUris() {
		final Set<String> uris = new TreeSet<String>();
		for (final Object name : ((Map<?, ?>) EPackage.Registry.INSTANCE)
				.keySet()) {
			uris.add(name.toString());
		}
		return uris;
	}
}
