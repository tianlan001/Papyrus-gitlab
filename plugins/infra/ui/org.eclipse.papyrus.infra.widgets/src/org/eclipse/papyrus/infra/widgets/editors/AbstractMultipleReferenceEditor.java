/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 516526
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.providers.EmptyContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.WrappedLabelProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.swt.widgets.Composite;

/**
 * A partial implementation of an editor for multivalued references. This editor should be used when
 * there is enough vertical space available. If the vertical space is limited,
 * CompactMultipleReferenceEditor should be used instead.
 *
 * @author Camille Letavernier
 * @since 3.0
 */
public abstract class AbstractMultipleReferenceEditor extends AbstractMultipleValueEditor<ReferenceSelector> implements IMultipleReferenceEditor {

	protected IStaticContentProvider contentProvider;

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor is created
	 * @param ordered
	 *            True if the list should be ordered
	 * @param unique
	 *            True if the list values should be unique
	 * @param label
	 *            The label for this editor
	 * @param contentControlCreator
	 *            a function that creates the content control
	 */
	protected AbstractMultipleReferenceEditor(Composite parent,
			boolean ordered, boolean unique, String label) {

		super(parent, new ReferenceSelector(unique), ordered, unique, label);

		// Default providers
		setProviders(EmptyContentProvider.instance, new WrappedLabelProvider());
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor is created
	 * @param label
	 *            The label for this editor
	 * @param contentControlCreator
	 *            a function that creates the content control
	 */
	protected AbstractMultipleReferenceEditor(Composite parent, String label) {
		this(parent, false, false, label);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor is created
	 * @param contentControlCreator
	 *            a function that creates the content control
	 */
	protected AbstractMultipleReferenceEditor(Composite parent) {
		this(parent, false, false, null);
	}

	@Override
	public void setProviders(IStaticContentProvider contentProvider, ILabelProvider labelProvider) {
		Assert.isNotNull(contentProvider, "The content provider should be defined"); //$NON-NLS-1$

		this.contentProvider = contentProvider;

		if (selector instanceof ReferenceSelector) {
			selector.setContentProvider(contentProvider);
		}

		if (labelProvider != null) {
			selector.setLabelProvider(labelProvider);

			setLabelProvider(labelProvider);
		}
	}

	@Override
	public void setUnique(boolean unique) {
		selector.setUnique(unique);

		super.setUnique(unique);
	}
}
