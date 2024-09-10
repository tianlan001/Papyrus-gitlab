/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.properties.modelelements;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.papyrus.uml.nattable.properties.Activator;
import org.eclipse.papyrus.uml.nattable.properties.utils.MatrixHelper;
import org.eclipse.ui.IEditorPart;

/**
 * 
 * The factory for the tab Matrix when we manage Relationship
 */
public class GenericUMLRelationshipMatrixFactory extends EMFModelElementFactory {


	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractEMFModelElementFactory#updateModelElement(org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement, java.lang.Object)
	 *
	 * @param modelElement
	 * @param newSourceElement
	 */
	@Override
	protected void updateModelElement(EMFModelElement modelElement, Object newSourceElement) {
		final NatTableEditor nattableEditor = getCurrentNatTableEditor();
		final IMatrixTableWidgetManager tableManager = (IMatrixTableWidgetManager) nattableEditor.getAdapter(IMatrixTableWidgetManager.class);
		if (null != nattableEditor && null != tableManager) {
			if (modelElement instanceof GenericUMLRelationshipMatrixModelElement) {
				updateTableModelElement((GenericUMLRelationshipMatrixModelElement) modelElement, tableManager);
			}
			updateEMFModelElement(modelElement, nattableEditor.getTable());
		} else {
			super.updateModelElement(modelElement, newSourceElement);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory#doCreateFromSource(java.lang.Object, org.eclipse.papyrus.infra.properties.contexts.DataContextElement)
	 *
	 * @param sourceElement
	 * @param context
	 * @return
	 */
	@Override
	protected EMFModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		final EObject source = EMFHelper.getEObject(sourceElement);
		if (null == source) {
			Activator.log.warn("Unable to resolve the selected element to a Table"); //$NON-NLS-1$
			return null;
		}
		// this manager is the manager of the current table editor. It already has been checked by the constraint {@link IsGenericUMLRelationshipMatrix}
		final IMatrixTableWidgetManager manager = MatrixHelper.getMatrixTableWidgetModelManagerFromCurrentEditor();
		if (null != manager) {
			final TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(manager.getTable());
			if (null != domain) {
				return new GenericUMLRelationshipMatrixModelElement(manager, domain);
			}
		}

		return super.doCreateFromSource(sourceElement, context);
	}

	/**
	 * This allows to get the current nattable editor when this is available.
	 * 
	 * @return The current nattable editor if available or <code>null</code>.
	 */
	protected NatTableEditor getCurrentNatTableEditor() {
		NatTableEditor result = null;

		final IEditorPart currentEditor = EditorHelper.getCurrentEditor();
		if (currentEditor instanceof IMultiPageEditorPart) {
			result = ((IMultiPageEditorPart) currentEditor).getActiveEditor().getAdapter(NatTableEditor.class);
		} else if (currentEditor instanceof IAdaptable) {
			result = ((IAdaptable) currentEditor).getAdapter(NatTableEditor.class);
		}

		return result;
	}

	/**
	 * Modify the table property of the nattable model element.
	 * 
	 * @param nattableModelElement
	 *            The nattable model element.
	 * @param table
	 *            The table.
	 */
	protected void updateTableModelElement(final GenericUMLRelationshipMatrixModelElement nattableModelElement, final IMatrixTableWidgetManager tableModelManager) {
		nattableModelElement.updateTableWidgetMatrixManager(tableModelManager);
	}
}
