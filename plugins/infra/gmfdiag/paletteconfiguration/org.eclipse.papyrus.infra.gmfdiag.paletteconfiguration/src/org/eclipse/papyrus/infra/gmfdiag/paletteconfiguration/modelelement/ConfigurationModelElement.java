/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.modelelement;

import java.text.MessageFormat;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Configuration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.messages.Messages;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.ElementDescriptorLabelProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.widgets.Activator;

/**
 * {@link EMFModelElement} for {@link Configuration}.
 */
public class ConfigurationModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 *
	 * @param sourceElement
	 *            the palette configuration where to edit the icon descriptor
	 * @param domain
	 *            the editing domain
	 */
	public ConfigurationModelElement(final Configuration sourceElement, final EditingDomain domain) {
		super(sourceElement, domain);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#isFeatureEditable(java.lang.String)
	 */
	@Override
	protected boolean isFeatureEditable(final String propertyPath) {
		boolean featureEditable = false;
		if ("icon".equals(propertyPath)) { //$NON-NLS-1$
			featureEditable = true;
		} else if ("elementDescriptors".equals(propertyPath)) { //$NON-NLS-1$
			featureEditable = true;
		} else {
			featureEditable = super.isFeatureEditable(propertyPath);
		}
		return featureEditable;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#getValidator(java.lang.String)
	 */
	@Override
	public IValidator getValidator(final String propertyPath) {
		IValidator validator = null;
		switch (propertyPath) {
		case "label"://$NON-NLS-1$
		case "id"://$NON-NLS-1$
			validator = new IValidator() {
				@Override
				public IStatus validate(Object value) {
					Status status = null;
					if (value instanceof String) {
						if (!((String) value).isEmpty()) {
							status = new Status(IStatus.OK, Activator.PLUGIN_ID, "");//$NON-NLS-1$
						} else {
							status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, MessageFormat.format(Messages.ConfigurationModelElement_WarningNotSet, propertyPath));
						}
					} else {
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, MessageFormat.format(Messages.ConfigurationModelElement_ErrorNotString, propertyPath));
					}
					return status;
				}
			};
			break;
		case "icon"://$NON-NLS-1$
			validator = super.getValidator(propertyPath);
			break;
		default:
			validator = new IValidator() {

				@Override
				public IStatus validate(Object value) {
					return new Status(IStatus.OK, Activator.PLUGIN_ID, "");//$NON-NLS-1$
				}

			};
			break;
		}
		return validator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IObservable doGetObservable(final String propertyPath) {
		IObservable observable = null;
		if ("icon".equals(propertyPath)) { //$NON-NLS-1$
			observable = new IconDescriptorObservableValue(source, PaletteconfigurationPackage.eINSTANCE.getConfiguration_Icon(), getDomain());
		} else {
			observable = super.doGetObservable(propertyPath);
		}
		return observable;
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 */
	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		ILabelProvider labelProvider = null;
		if ("elementDescriptors".equals(propertyPath)) {//$NON-NLS-1$
			labelProvider = new ElementDescriptorLabelProvider();
		} else {
			labelProvider = super.getLabelProvider(propertyPath);
		}
		return labelProvider;
	}


}
