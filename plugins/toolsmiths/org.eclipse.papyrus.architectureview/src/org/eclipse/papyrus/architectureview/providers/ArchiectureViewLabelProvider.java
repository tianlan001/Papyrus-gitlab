/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 570486
 *
 *****************************************************************************/

package org.eclipse.papyrus.architectureview.providers;

import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.papyrus.architectureview.Activator;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.swt.graphics.Image;

/**
 * This is the label provider to display architecture elements of tree viewer.
 */
public class ArchiectureViewLabelProvider implements ITableLabelProvider {

	/**
	 * This map allows to save diagnostics of validation to avoid re-validation.
	 */
	private Map<MergedArchitectureDomain, Integer> diagnosticByArchitecture;

	private ComposedAdapterFactory adapterFactory;

	/**
	 * The default constructor.
	 */
	public ArchiectureViewLabelProvider() {
		super();

		diagnosticByArchitecture = new WeakHashMap<>();
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void dispose() {
		adapterFactory.dispose();
		adapterFactory = null;
		diagnosticByArchitecture.clear();
		diagnosticByArchitecture = null;
	}

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// Do nothing here.
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof MergedArchitectureDomain) {
			MergedArchitectureDomain architectureDomain = (MergedArchitectureDomain) element;
			ADElement adElement = architectureDomain.getAdapter(ADElement.class);

			switch (columnIndex) {
			case 0:
				return null != architectureDomain ? architectureDomain.getName() : "Unknown name"; //$NON-NLS-1$
			case 1:
				return null != architectureDomain ? architectureDomain.getId() : "Unknown identifier"; //$NON-NLS-1$
			case 2:
				return Boolean.toString(architectureDomain.isMerged());
			case 3:
				return adElement.eResource().getURI().lastSegment();
			case 4:
				// The second segment should be the plug-in (the 'platform' is managed as scheme)
				final URI uri = adElement.eResource().getURI();
				return uri.isPlatform() ? uri.segment(1) : uri.toString();
			case 5:
				if (null != architectureDomain && null == diagnosticByArchitecture.get(architectureDomain)) {
					diagnosticByArchitecture.put(architectureDomain, Diagnostician.INSTANCE.validate(adElement).getSeverity());
				}
				return String.valueOf(Diagnostic.OK == diagnosticByArchitecture.get(architectureDomain));
			}
		} else if (element instanceof MergedADElement) {
			if (0 == columnIndex) {
				return ((MergedADElement) element).getName();
			}
			return ""; //$NON-NLS-1$
		}
		return "cannot display it: " + element; //$NON-NLS-1$
	}

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		if (!(element instanceof MergedADElement)) {
			return null;
		}
		MergedADElement merged = (MergedADElement) element;
		ADElement adElement = merged.getAdapter(ADElement.class);

		if (0 == columnIndex) {
			IItemLabelProvider itemProvider = (IItemLabelProvider) adapterFactory.adapt(merged, IItemLabelProvider.class);

			if (null != itemProvider) {
				return ExtendedImageRegistry.getInstance().getImage(itemProvider.getImage(merged));
			}
		}

		if (2 == columnIndex && element instanceof MergedArchitectureDomain) {
			return merged.isMerged() ? Activator.CHECKED_IMAGE : Activator.UNCHECKED_IMAGE;
		}

		if (5 == columnIndex && element instanceof MergedArchitectureDomain) {
			if (null == diagnosticByArchitecture.get(element)) {
				diagnosticByArchitecture.put((MergedArchitectureDomain) element, Diagnostician.INSTANCE.validate(adElement).getSeverity());
			}

			return Diagnostic.OK == diagnosticByArchitecture.get(element) ? Activator.VALID_IMAGE : Activator.INVALID_IMAGE;
		}

		return null;
	}
}
