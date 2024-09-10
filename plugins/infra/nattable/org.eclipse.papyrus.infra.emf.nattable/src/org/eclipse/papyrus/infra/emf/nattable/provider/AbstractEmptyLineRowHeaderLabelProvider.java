/*****************************************************************************
 * Copyright (c) 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.nattable.provider;

import org.eclipse.core.runtime.Assert;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Abstract label provider allows to get a label in the row header for empty line
 *
 * @since 5.2
 */
public abstract class AbstractEmptyLineRowHeaderLabelProvider extends EMFEObjectHeaderLabelProvider {

	/**
	 * the kindId of the table on which this label provider must be activated
	 */
	private final String kindId;

	/**
	 * the kindId of the table on which this label provider must be activated
	 */
	public AbstractEmptyLineRowHeaderLabelProvider(final String tableKind) {
		Assert.isNotNull(tableKind);
		Assert.isTrue(!tableKind.isEmpty());
		this.kindId = tableKind;
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFEObjectHeaderLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean accept(Object element) {
		String currentTableKindId = ""; //$NON-NLS-1$
		if (element instanceof LabelProviderCellContextElementWrapper) {
			LabelProviderCellContextElementWrapper wrapper = (LabelProviderCellContextElementWrapper) element;
			INattableModelManager manager = wrapper.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL,
					NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			currentTableKindId = manager.getTable().getTableKindId();

			element = ((LabelProviderCellContextElementWrapper) element).getObject();

		}
		return this.kindId.equals(currentTableKindId) && element instanceof ITreeItemAxis && ((ITreeItemAxis) element).getElement() == null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFEObjectHeaderLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof LabelProviderCellContextElementWrapper) {
			element = ((LabelProviderCellContextElementWrapper) element).getObject();
		}
		if (element instanceof ITreeItemAxis) {
			final ITreeItemAxis axis = (ITreeItemAxis) element;
			ITreeItemAxis parentAxis = axis.getParent();
			final Object parentElement = parentAxis.getElement();
			if (parentElement instanceof TreeFillingConfiguration) {
				final TreeFillingConfiguration conf = (TreeFillingConfiguration) parentElement;
				return getCreationHeaderMessage(conf);
			}
		}

		return "";//$NON-NLS-1$
	}

	/**
	 *
	 * @param configuration
	 *            the tree filling configuration for which we display an empty line
	 * @return
	 *         the label to display for the row header cell, can't be <code>null</code>
	 */
	protected abstract String getCreationHeaderMessage(final TreeFillingConfiguration configuration);

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFEObjectHeaderLabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object element) {
		return Activator.getDefault().getImage("/icons/Add_12x12.gif"); //$NON-NLS-1$
	}

}
