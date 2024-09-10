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

package org.eclipse.papyrus.infra.emf.types.ui.properties.modelelements;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.infra.emf.types.ui.properties.providers.ViewContentProvider;
import org.eclipse.papyrus.infra.emf.types.ui.properties.providers.ViewLabelProvider;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Profile;

/**
 * {@link EMFModelElement} for {@link RuntimeValuesAdviceConfiguration}.
 */
public class RuntimeValuesModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 *
	 * @param sourceElement
	 *            The source element.
	 * @param domain
	 *            The editing domain.
	 */
	public RuntimeValuesModelElement(final EObject sourceElement, final EditingDomain domain) {
		super(sourceElement, domain);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 */
	@Override
	public ILabelProvider getLabelProvider(final String propertyPath) {
		ILabelProvider labelProvider = null;
		if ("view".equals(propertyPath) || "viewsToDisplay".equals(propertyPath)) {//$NON-NLS-1$ //$NON-NLS-2$
			labelProvider = new ViewLabelProvider();
		} else {
			labelProvider = super.getLabelProvider(propertyPath);
		}
		return labelProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 */
	@Override
	public IStaticContentProvider getContentProvider(final String propertyPath) {
		IStaticContentProvider contentProvider = null;
		if ("view".equals(propertyPath)) {//$NON-NLS-1$
			contentProvider = new ViewContentProvider();
		} else {
			contentProvider = super.getContentProvider(propertyPath);
		}
		return contentProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getDirectCreation(java.lang.String)
	 */
	@Override
	public boolean getDirectCreation(final String propertyPath) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getValueFactory(java.lang.String)
	 */
	@Override
	public ReferenceValueFactory getValueFactory(final String propertyPath) {

		ReferenceValueFactory valueFactory = null;
		if ("view".equals(propertyPath)) {//$NON-NLS-1$
			// Set the string edition factory for required profiles
			valueFactory = new ReferenceValueFactory() {
				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory#canCreateObject()
				 */
				@Override
				public boolean canCreateObject() {
					return false;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory#canEdit()
				 */
				@Override
				public boolean canEdit() {
					return true;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory#createObject(org.eclipse.swt.widgets.Control, java.lang.Object)
				 */
				@Override
				public Object createObject(final Control widget, final Object context) {
					return null;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory#edit(org.eclipse.swt.widgets.Control, java.lang.Object)
				 */
				@Override
				public Object edit(final Control widget, final Object object) {
					Object newValue = null;
					TreeSelectorDialog dialog = new TreeSelectorDialog(widget.getShell());

					dialog.setContentProvider(new ViewContentProvider());
					dialog.setLabelProvider(new ViewLabelProvider());

					dialog.setTitle(Messages.RuntimeValuesModelElement_browserTitle);
					dialog.setDescription(Messages.RuntimeValuesModelElement_browserDescription);

					int result = dialog.open();
					if (Window.OK == result) {
						Object[] resultValue = dialog.getResult();
						if (0 < resultValue.length) {
							Object profile = resultValue[0];
							if (profile instanceof Profile) {
								newValue = ((Profile) profile).getName();
							} else if (profile instanceof View) {
								newValue = profile;
							}
						}
					}
					return newValue;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory#validateObjects(java.util.Collection)
				 */
				@Override
				public Collection<Object> validateObjects(final Collection<Object> objectsToValidate) {
					return objectsToValidate;
				}

			};
		}

		return null != valueFactory ? valueFactory : super.getValueFactory(propertyPath);
	}

}
