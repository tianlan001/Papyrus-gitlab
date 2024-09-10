/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 417409
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 492891
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 520188
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.properties.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.properties.Activator;
import org.eclipse.papyrus.infra.nattable.properties.utils.NatTableEditorUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;

/**
 * The model factory to create {@link NatTableModelElement}s.
 */
public class NatTableFactory extends EMFModelElementFactory {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory#doCreateFromSource(java.lang.Object, org.eclipse.papyrus.infra.properties.contexts.DataContextElement)
	 */
	@Override
	protected EMFModelElement doCreateFromSource(final Object sourceElement, final DataContextElement context) {
		EObject source = EMFHelper.getEObject(sourceElement);
		if (source == null) {
			Activator.log.warn("Unable to resolve the selected element to an EObject"); //$NON-NLS-1$
			return null;
		}

		if (source instanceof Table) {
			final INattableModelManager nattableManager = NatTableEditorUtils.getCurrentNatTableModelManager();

			if (null != nattableManager && source == nattableManager.getTable()) {
				final TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(nattableManager.getTable());
				if (null != domain) {
					return new NatTableModelElement(nattableManager, domain);
				}
			}
		}
 
		return super.doCreateFromSource(sourceElement, context);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractEMFModelElementFactory#updateModelElement(org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement, java.lang.Object)
	 * 
	 * @since 2.1
	 */
	@Override
	protected void updateModelElement(final EMFModelElement modelElement, final Object newSourceElement) {
		final EObject eObject = EMFHelper.getEObject(newSourceElement);
		if (null == eObject) {
			throw new IllegalArgumentException("Cannot resolve EObject selection: " + newSourceElement);
		}

		if (modelElement instanceof NatTableModelElement && eObject instanceof Table) {
			updateTableModelElement((NatTableModelElement) modelElement, (Table) eObject);
		}
		updateEMFModelElement(modelElement, eObject);
	}

	/**
	 * Modify the table property of the nattable model element.
	 * 
	 * @param nattableModelElement
	 *            The nattable model element.
	 * @param table
	 *            The table.
	 * 
	 * @since 2.1
	 */
	public static void updateTableModelElement(final NatTableModelElement nattableModelElement, final Table table) {
		nattableModelElement.table = table;
	}
}
