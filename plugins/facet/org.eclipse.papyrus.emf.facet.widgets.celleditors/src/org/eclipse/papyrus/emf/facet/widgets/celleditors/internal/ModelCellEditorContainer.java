/*******************************************************************************
 * Copyright (c) 2011 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Guyomar (Mia-Software) - Bug 339554 - org.eclipse.papyrus.emf.facet.widgets.celleditors API cleaning
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.widgets.celleditors.internal;

import org.eclipse.papyrus.emf.facet.widgets.celleditors.IModelCellEditorContainer;
import org.eclipse.papyrus.emf.facet.widgets.celleditors.modelCellEditor.AbstractModelCellEditor;

public class ModelCellEditorContainer<T extends AbstractModelCellEditor> implements
		IModelCellEditorContainer<AbstractModelCellEditor> {

	private AbstractModelCellEditor modelCellEditor;
	private String bundleName;

	public String getBundleName() {
		return this.bundleName;
	}

	public AbstractModelCellEditor getModelCellEditor() {
		return this.modelCellEditor;
	}

	public void setBundleName(final String bundleName) {
		this.bundleName = bundleName;
	}

	public void setModelCellEditor(final AbstractModelCellEditor modelCellEditor) {
		this.modelCellEditor = modelCellEditor;

	}
}