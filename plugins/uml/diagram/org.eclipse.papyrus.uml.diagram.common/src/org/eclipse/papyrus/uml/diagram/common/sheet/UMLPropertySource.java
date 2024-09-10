/*****************************************************************************
 * Copyright (c) 2009, 2017 ATOS ORIGIN.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thibault Landre (Atos Origin) - Initial API and implementation
 *		Pauline DEVILLE (CEA LIST) - Bug 521408 - [Core] The property advanced tab should use treeviewer
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.sheet;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.infra.widgets.editors.MultipleValueSelectionDialog;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.UnsetObject;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.papyrus.uml.diagram.common.messages.Messages;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.uml2.uml.Stereotype;

/**
 * A specific property source for Papyrus. It replaces the combo used to choose
 * a single reference by a {@link ElementListSelectionDialog}
 *
 */
public class UMLPropertySource extends PropertySource {

	/**
	 * @param object
	 * @param ips
	 */
	public UMLPropertySource(Object object, IItemPropertySource ips) {
		super(object, ips);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.emf.edit.ui.provider.PropertySource#createPropertyDescriptor
	 * (org.eclipse.emf.edit.provider.IItemPropertyDescriptor)
	 */
	@Override
	protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
		return new CustomPropertyDescriptor(object, itemPropertyDescriptor);
	}

	/**
	 * A custom property descriptor. It replaces the Combo by the {@link ElementListSelectionDialog}
	 */
	private class CustomPropertyDescriptor extends PropertyDescriptor {

		public CustomPropertyDescriptor(Object object, IItemPropertyDescriptor itemPropertyDescriptor) {
			super(object, itemPropertyDescriptor);
		}

		@Override
		public CellEditor createPropertyEditor(Composite composite) {
			if (!itemPropertyDescriptor.canSetProperty(object)) {
				return null;
			}

			CellEditor result = null;
			final Object genericFeature = itemPropertyDescriptor.getFeature(object);

			if (!(genericFeature instanceof EReference)) {
				return super.createPropertyEditor(composite);
			}
			// If it is a single reference
			if (!((EReference) genericFeature).isMany()) {
				Object initialSelection = ((EObject) object).eGet((EStructuralFeature) genericFeature);
				final ILabelProvider editLabelProvider = getEditLabelProvider();
				result = new ExtendedDialogCellEditor(composite, editLabelProvider) {
					@Override
					protected Object openDialogBox(Control cellEditorWindow) {
						CustomUMLContentProvider provider = new CustomUMLContentProvider((EObject) object, (EStructuralFeature) genericFeature);
						provider.addTemporaryElement(UnsetObject.instance);
						TreeSelectorDialog dialog = new TreeSelectorDialog(cellEditorWindow.getShell());
						dialog.setTitle(Messages.UMLPropertySource_ElementSelection);
						dialog.setContentProvider(provider);
						dialog.setLabelProvider(new UMLLabelProvider());
						Object[] selectedValue = { initialSelection };
						dialog.setInitialSelections(selectedValue);
						int userResponse = dialog.open();
						Object toReturn = null;
						if (userResponse == Window.OK) {
							toReturn = dialog.getResult()[0];
							if (toReturn == null) {
								toReturn = itemPropertyDescriptor.getPropertyValue(null);
							}
						} else {
							toReturn = itemPropertyDescriptor.getPropertyValue(object);
						}
						return toReturn;
					}
				};
			} else { // If it is a multiple reference
				final ILabelProvider editLabelProvider = getEditLabelProvider();
				result = new ExtendedDialogCellEditor(composite, editLabelProvider) {

					@Override
					protected Object openDialogBox(Control cellEditorWindow) {
						Object initialSelection = ((EObject) object).eGet((EStructuralFeature) genericFeature);
						UMLContentProvider provider = new UMLContentProvider((EObject) object, (EStructuralFeature) genericFeature);

						ReferenceSelector referenceSelector = new ReferenceSelector(true);
						referenceSelector.setContentProvider(provider);
						referenceSelector.setLabelProvider(new UMLLabelProvider());
						MultipleValueSelectionDialog dialog = new MultipleValueSelectionDialog(cellEditorWindow.getShell(), referenceSelector);
						dialog.setInitialElementSelections((List) initialSelection);
						dialog.setLabelProvider(new UMLLabelProvider());
						dialog.setContextElement(object);
						dialog.setTitle(Messages.UMLPropertySource_ElementSelection);

						Object toReturn = null;
						if (dialog.open() == Window.OK) {
							toReturn = Arrays.asList(dialog.getResult());

						} else {
							toReturn = itemPropertyDescriptor.getPropertyValue(object);
						}
						return toReturn;
					}
				};
			}
			return result;
		}
	}

	public class CustomUMLContentProvider extends UMLContentProvider {

		public CustomUMLContentProvider() {
			super();
		}

		public CustomUMLContentProvider(EObject source, EStructuralFeature feature, Stereotype stereotype, ResourceSet root) {
			super(source, feature, stereotype, root);
		}

		public CustomUMLContentProvider(EObject source, EStructuralFeature feature, Stereotype stereotype) {
			super(source, feature, stereotype);
		}

		public CustomUMLContentProvider(EObject source, EStructuralFeature feature) {
			super(source, feature);
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#isValidValue(java.lang.Object)
		 *
		 * @param element
		 * @return
		 */
		@Override
		public boolean isValidValue(Object element) {
			boolean result = false;
			if (element != null) {
				result = element.equals(UnsetObject.instance);
				// avoid that we can validate on an element that is not owned by the classifier itself
				if ((feature instanceof ENamedElement) && ((ENamedElement) feature).getName().equals("classifierBehavior")) { //$NON-NLS-1$
					if (element instanceof EObjectTreeElement) {
						return isOwned(object, ((EObjectTreeElement) element).getEObject());
					}
				}
			}

			return super.isValidValue(element) || result;
		}

		/**
		 * Check whether a child belongs to the given parent, i.e. is owned by it.
		 *
		 * @param parent
		 *            a parent
		 * @param child
		 *            a child
		 * @return true, if owned
		 */
		public boolean isOwned(Object parent, EObject child) {
			child = child.eContainer();
			while (child != null) {
				if (child == parent) {
					return true;
				}
				child = child.eContainer();
			}
			return false;
		}
	}
}
