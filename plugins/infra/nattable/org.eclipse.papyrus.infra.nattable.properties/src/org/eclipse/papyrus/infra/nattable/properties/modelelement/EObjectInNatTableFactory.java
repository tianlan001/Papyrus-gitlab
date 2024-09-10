/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 520188
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.modelelement;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.properties.utils.NatTableEditorUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;

/**
 * The model factory to create {@link NatTableModelElement}s from the current nattable editor.
 *
 * @since 2.1
 */
public class EObjectInNatTableFactory extends NatTableFactory {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory#doCreateFromSource(java.lang.Object, org.eclipse.papyrus.infra.properties.contexts.DataContextElement)
	 */
	@Override
	protected EMFModelElement doCreateFromSource(final Object sourceElement, final DataContextElement context) {

		final INattableModelManager nattableManager = NatTableEditorUtils.getCurrentNatTableModelManager();

		if (null != nattableManager) {
			final TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(nattableManager.getTable());
			if (null != domain) {
				return new NatTableModelElement(nattableManager, domain);
			}
		}

		return super.doCreateFromSource(sourceElement, context);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractEMFModelElementFactory#updateModelElement(org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement, java.lang.Object)
	 */
	@Override
	protected void updateModelElement(final EMFModelElement modelElement, final Object newSourceElement) {
		final NatTableEditor nattableEditor = NatTableEditorUtils.getCurrentNatTableEditor();
		if (null != nattableEditor) {
			if (modelElement instanceof NatTableModelElement) {
				updateTableModelElement((NatTableModelElement) modelElement, nattableEditor.getTable());
			}
			updateEMFModelElement(modelElement, nattableEditor.getTable());
		} else {
			super.updateModelElement(modelElement, newSourceElement);
		}
	}
	
	/**
	 * This allows to get the current nattable editor when this is available.
	 * 
	 * @return The current nattable editor if available or <code>null</code>.
	 * @deprecated since 4.0
	 */
	protected NatTableEditor getCurrentNatTableEditor() {
		return NatTableEditorUtils.getCurrentNatTableEditor();
	}
}
