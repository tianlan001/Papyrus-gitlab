/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *                                                            Bug 533527
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.xtext.widget.property;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.DirectEditorsUtil;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSourceChangedEvent;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.IDataSourceListener;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrus.uml.properties.xtext.XtextLanguageEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * An abstract xtext property editor. Subclasses must override the abstract method registerChangeListeners.
 * The class wraps an XtextLanguageEditor.
 *
 * @see XtextLanguageEditor
 */
public abstract class AbstractXtextPropertyEditor extends AbstractPropertyEditor implements IDataSourceListener {

	protected EObject elementToEdit = null;

	protected String language;

	protected XtextLanguageEditor xtextEditor;

	/**
	 * Constructor.
	 *
	 * @param language
	 *            the language for which an xtext editor has been registered (as defined by the
	 *            Papyrus extension point oep.extensionpoints.editors.DirectEditor)
	 *
	 * @param parent
	 *            The composite in which the widget is created
	 * @param style
	 *            The style for the {@link XtextValueEditor}
	 */
	public AbstractXtextPropertyEditor(String language, Composite parent, int style) {
		xtextEditor = new XtextLanguageEditor();
		xtextEditor.createWidget(parent, style);
		this.language = language;
	}

	@Override
	protected void doBinding() {
		super.doBinding();
		ModelElement element = input.getModelElement(propertyPath);
		xtextEditor.setContext(element);
		if (element instanceof EMFModelElement) {
			EMFModelElement emfElement = (EMFModelElement) element;
			elementToEdit = emfElement.getSource();
			registerChangeListeners(element);
			display();
		}
	}

	/**
	 * Register a set of change listeners. This abstract method needs to be overridden by language specific
	 * subclasses. The background is that an xtext editor typically displays multiple features of a model
	 * element. Whenever these change (e.g. the name could be modified via the standard UML property tab), the
	 * editor needs to refresh its content.
	 * Subclasses could for instance contain the following code:
	 *
	 * <pre>
	 * IObservable name = element.getObservable("name");
	 * if (name != null) {
	 * 	name.addChangeListener(this);
	 * }
	 * </pre>
	 *
	 * @param element
	 */
	protected abstract void registerChangeListeners(ModelElement element);

	protected void registerObservable(ModelElement element, String observableName) {
		IObservable observable = element.getObservable(observableName);
		if (observable != null) {
			observable.addChangeListener(this);
		}
	}

	/**
	 * treat change event, update editor contents
	 *
	 * @param event
	 */
	@Override
	public void handleChange(ChangeEvent event) {
		display();
		super.handleChange(event);
	}

	/**
	 * Display the editor contents, calculate the initial text via the editor configuration.
	 */
	public void display() {
		IDirectEditorConfiguration configuration = DirectEditorsUtil.findEditorConfiguration(language, elementToEdit, elementToEdit);
		String initialText = configuration.getTextToEdit(elementToEdit);// use xtext UI editor

		xtextEditor.setInput(initialText);
	}

	@Override
	protected void applyReadOnly(boolean readOnly) {
		xtextEditor.setReadOnly(readOnly);
	}

	@Override
	protected void unhookDataSourceListener(DataSource oldInput) {
		super.unhookDataSourceListener(oldInput);
		oldInput.removeDataSourceListener(this);
	}

	@Override
	protected void hookDataSourceListener(DataSource newInput) {
		super.hookDataSourceListener(newInput);
		newInput.addDataSourceListener(this);
	}

	public void dataSourceChanged(DataSourceChangedEvent event) {
		// the data source has changed, i.e. user clicked on a different object.
		// Asynchronously refresh editor contents
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				doBinding();
			}
		});
	}
}
