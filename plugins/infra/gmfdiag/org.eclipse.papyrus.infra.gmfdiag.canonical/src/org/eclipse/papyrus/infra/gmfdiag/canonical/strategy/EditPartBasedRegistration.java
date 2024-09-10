/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.canonical.internal.Activator;
import org.osgi.framework.Bundle;

import com.google.common.base.Strings;

/**
 * Abstraction of an {@link EditPart}-based registration on the extension point.
 */
abstract class EditPartBasedRegistration<T, R extends EditPartBasedRegistration<? extends T, R>> extends Registration<T, R> {

	private static final String EDITPART = "editPart"; //$NON-NLS-1$

	private final String editPartClassName;

	private Class<?> editPartType;

	public EditPartBasedRegistration(IConfigurationElement config, Class<T> extensionType) throws CoreException {
		super(config, extensionType);

		this.editPartClassName = Strings.emptyToNull(config.getAttribute(EDITPART));
	}

	private Class<?> getEditPartType() {
		if ((editPartType == null) && (editPartClassName != null)) {
			IConfigurationElement config = getConfigurationElement();
			Bundle bundle = Platform.getBundle(config.getContributor().getName());
			if ((bundle != null) && (bundle.getState() == Bundle.ACTIVE)) {
				try {
					editPartType = bundle.loadClass(editPartClassName).asSubclass(EditPart.class);
				} catch (Exception e) {
					editPartType = Void.class; // Forget matching anything, ever
					Activator.log.error("Failed to load edit-part type " + editPartClassName, e); //$NON-NLS-1$
				}
			}
		}

		return editPartType;
	}

	boolean matchesEditPartType(Class<?> editPartType) {
		boolean result = (editPartClassName == null);

		if (!result) {
			// The declaring bundle may not be loaded, in which case we don't want its strategy
			Class<?> myEditPartType = getEditPartType();
			result = (myEditPartType != null) && myEditPartType.isAssignableFrom(editPartType);
		}

		return result;
	}

	@Override
	public boolean isApplicableTo(EditPart editPart) {
		return matchesEditPartType(editPart.getClass()) && super.isApplicableTo(editPart);
	}
}
