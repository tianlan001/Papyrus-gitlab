/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.editor;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.editors.MultipleValueDialog;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The cell editor for the multi primitive types edition.
 * 
 * @since 5.0
 */
public abstract class AbstractMultiValuePrimitiveTypeCellEditor extends AbstractUMLMultiValueCellEditor {

	/**
	 * Constructor.
	 *
	 * @param axisElement
	 *            The axis element.
	 * @param elementProvider
	 *            The table axis element provider.
	 */
	protected AbstractMultiValuePrimitiveTypeCellEditor(final Object axisElement, final ITableAxisElementProvider elementProvider) {
		super(axisElement, elementProvider);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.editor.AbstractUMLMultiValueCellEditor#createDialog(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, org.eclipse.uml2.uml.Stereotype, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	@Override
	protected Object createDialog(final EObject realEditedObject, final EStructuralFeature realFeature, final Stereotype stereotype, final ResourceSet resourceSet) {
		final UMLContentProvider p = new UMLContentProvider(realEditedObject, realFeature, stereotype, resourceSet);
		final String title = realFeature.getName();
		final boolean unique = realFeature.isUnique();
		final boolean ordered = realFeature.isOrdered();
		final int upperBound = realFeature.getUpperBound();
		final Object value = realEditedObject.eGet(realFeature);
		IElementSelector selector = getElementSelector(unique, new UMLLabelProvider(), p);
		final MultipleValueDialog dialog = new MultipleValueDialog(Display.getCurrent().getActiveShell(), selector, title, unique, ordered, upperBound) {

			@Override
			protected void okPressed() {
				super.okPressed();
				Collection<Object> newValue = new ArrayList<Object>();
				Object[] result = this.getResult();
				for (Object object : result) {
					newValue.add(object);

				}
				AbstractMultiValuePrimitiveTypeCellEditor.this.returnedValue = newValue;
				AbstractMultiValuePrimitiveTypeCellEditor.this.editHandler.commit(newValue, MoveDirectionEnum.NONE);
			}

		};
		dialog.setContextElement(realEditedObject);
		dialog.setLabelProvider(new UMLLabelProvider());
		if (value != null && value instanceof Collection) {
			Collection<?> coll = (Collection<?>) value;
			if (!coll.isEmpty()) {
				dialog.setInitialSelections(coll.toArray());
			}
		}

		ReferenceValueFactory factory = getFactory();
		if (factory != null) {
			dialog.setFactory(factory);
		}

		return dialog;
	}
}
