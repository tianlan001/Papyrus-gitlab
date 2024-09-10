/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.views.references.constants.ReferencesViewConstants;
import org.eclipse.papyrus.views.references.utils.ReferencesViewUtil;

public class ReferencesResultContentProvider implements ITreeContentProvider {

	/**
	 * The Collection of the References found.
	 */
	private Collection<?> referencesSetting = null;

	/**
	 * Constructor.
	 *
	 * @param adapterFactory
	 */
	public ReferencesResultContentProvider() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren(final Object object) {
		return 0 < getChildren(object).length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements(final Object element) {
		if (element instanceof Collection<?>) {
			final List<Object> rootElements = new ArrayList<>();
			referencesSetting = (Collection<?>) element;
			// For each input of the TreeViewer, add this in a container.
			for (final Object reference : referencesSetting) {
				if (reference instanceof Setting) {
					final EStructuralFeature eStructuralFeature = ((Setting) reference).getEStructuralFeature();

					// If the EStructuralFeature is Element get the container
					if (ReferencesViewConstants.NAME_ELEMENT.equals(eStructuralFeature.getName())) {
						final EObject eObject = ((Setting) reference).getEObject();
						final EObject container = ReferencesViewUtil.getContainer(eObject);
						if (!rootElements.contains(container)) {
							rootElements.add(container);
						}
					} else {
						if (!rootElements.contains(eStructuralFeature)) {
							rootElements.add(eStructuralFeature);
						}
					}
				}
			}

			if (rootElements.isEmpty()) {
				return ((Collection<?>) element).toArray();
			} else {
				return rootElements.toArray();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getChildren(final Object objectContainer) {
		final List<Object> settings = new ArrayList<>();
		for (final Object reference : referencesSetting) {
			if (reference instanceof Setting) {
				final EStructuralFeature eStructuralFeature = ((Setting) reference).getEStructuralFeature();
				if (ReferencesViewConstants.NAME_ELEMENT.equals(eStructuralFeature.getName())) {
					final EObject eObject = ((Setting) reference).getEObject();
					final EObject container = ReferencesViewUtil.getContainer(eObject);
					if (objectContainer.equals(container)) {
						settings.add(reference);
					}
				} else {
					if (objectContainer.equals(eStructuralFeature)) {
						settings.add(reference);
					}
				}
			}
		}
		return settings.toArray();
	}

	@Override
	public Object getParent(Object element) {
		
		return null;
	}

}
