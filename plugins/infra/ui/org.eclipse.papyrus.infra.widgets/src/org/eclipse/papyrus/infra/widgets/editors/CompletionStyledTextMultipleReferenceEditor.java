/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.papyrus.infra.widgets.databinding.CompletionStyledTextMultiReferenceDialogObservableValue;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * 
 * An editor for multivalued references, with a string editor in addition. This editor should be used when
 * there is enough vertical space available. If the vertical space is limited,
 * CompactMultipleReferenceEditor should be used instead.
 *
 * @author Vincent Lorenzo
 *
 */
public class CompletionStyledTextMultipleReferenceEditor extends MultipleReferenceEditor implements ISetPapyrusConverter {

	/**
	 * the embedded string editor
	 */
	private CompletionStyledTextStringEditor editor;

	/**
	 * the converter to use
	 */
	private IPapyrusConverter converter;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 * @param ordered
	 * @param unique
	 * @param label
	 */
	public CompletionStyledTextMultipleReferenceEditor(Composite parent, int style, boolean ordered, boolean unique, String label) {
		super(parent, style, ordered, unique, label);
		addStyledTextSection(parent, style);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 * @param label
	 */
	public CompletionStyledTextMultipleReferenceEditor(Composite parent, int style, String label) {
		super(parent, style, label);
		addStyledTextSection(parent, style);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public CompletionStyledTextMultipleReferenceEditor(Composite parent, int style) {
		super(parent, style);
		addStyledTextSection(parent, style);
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 */
	protected void addStyledTextSection(Composite parent, int style) {
		editor = new CompletionStyledTextStringEditor(this, style | SWT.BORDER);
		GridData treeData = new GridData(SWT.FILL, SWT.FILL, true, true);
		treeData.horizontalSpan = 2;
		editor.setLayoutData(treeData);
	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultipleValueEditor#setModelObservable(org.eclipse.core.databinding.observable.list.IObservableList)
	 *
	 * @param modelProperty
	 */
	@Override
	public void setModelObservable(IObservableList modelProperty) {
		super.setModelObservable(modelProperty);
		CompletionStyledTextMultiReferenceDialogObservableValue styledTextObservable = new CompletionStyledTextMultiReferenceDialogObservableValue(editor, editor.getText(), modelProperty, SWT.FocusOut);
		styledTextObservable.setPapyrusConverter(converter);
		styledTextObservable.addChangeListener(new IChangeListener() {

			@Override
			public void handleChange(org.eclipse.core.databinding.observable.ChangeEvent event) {
				commit();
			}
		});
		editor.setValue(modelProperty);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter#setPapyrusConverter(org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter)
	 *
	 * @param converter
	 */
	@Override
	public void setPapyrusConverter(IPapyrusConverter converter) {
		this.converter = converter;
		this.editor.setPapyrusConverter(converter);
	}
}
