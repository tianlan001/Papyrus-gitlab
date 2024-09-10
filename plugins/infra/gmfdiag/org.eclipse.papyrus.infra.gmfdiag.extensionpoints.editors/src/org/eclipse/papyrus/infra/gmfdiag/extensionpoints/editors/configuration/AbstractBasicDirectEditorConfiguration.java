/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Basic Direct Editor configuration to rename elements.
 */
public abstract class AbstractBasicDirectEditorConfiguration extends DefaultDirectEditorConfiguration implements ICustomDirectEditorConfiguration {

	/**
	 * {@inheritDoc}
	 */
	public DirectEditManager createDirectEditManager(final ITextAwareEditPart host) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public IParser createParser(final EObject host) {
		if (null == objectToEdit) {
			objectToEdit = host;
		}
		return createDirectEditorParser();
	}

	/**
	 * {@inheritDoc}
	 */
	public CellEditor createCellEditor(final Composite parent, final EObject object) {
		TextCellEditor cellEditor = new TextCellEditor(parent, SWT.BORDER);
		return cellEditor;
	}

	/**
	 * Creation of the parser used to renamed the element.
	 * 
	 * @return The IParser.
	 */
	public abstract IParser createDirectEditorParser();

}
