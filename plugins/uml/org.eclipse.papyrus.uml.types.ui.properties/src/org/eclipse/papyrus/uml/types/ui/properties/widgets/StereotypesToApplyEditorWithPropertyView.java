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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.ui.properties.widgets;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView;
import org.eclipse.papyrus.infra.widgets.editors.MultipleReferenceEditor;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;
import org.eclipse.papyrus.uml.types.ui.properties.providers.StereotypeToApplyContentProvider;
import org.eclipse.papyrus.uml.types.ui.properties.providers.StereotypeToApplyLabelProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * Multiple value editor for {@link StereotypeToApply}.
 */
public class StereotypesToApplyEditorWithPropertyView extends MultiReferenceEditorWithPropertyView {

	/** The change listener on {@link StereotypeToApply}. */
	private AdapterImpl changeListener;

	/** The previous selected Element. */
	private EObject previousSelectedElement;


	/**
	 * Constructor.
	 */
	public StereotypesToApplyEditorWithPropertyView(final Composite parent, final int style) {
		super(parent, style);

		changeListener = new AdapterImpl() {
			/**
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(final Notification msg) {
				if (!multiReferenceEditor.isDisposed()) {
					multiReferenceEditor.getViewer().refresh();
					layout();
				}
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView#setModelObservable(org.eclipse.core.databinding.observable.list.IObservableList)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setModelObservable(final IObservableList modelObservable) {
		super.setModelObservable(modelObservable);
		updateProviders();

		// Select the first element
		if (!modelObservable.isEmpty()) {
			multiReferenceEditor.getViewer().setSelection(new StructuredSelection(modelObservable.get(0)));
		}

	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView#createMultipleReferenceEditor(int)
	 */
	@Override
	protected MultipleReferenceEditor createMultipleReferenceEditor(final int style) {
		return new StereotypesToApplyEditor(this, style);

	}

	/**
	 * {@inheritDoc}
	 * <br>
	 * Manage listener in selection change.
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		super.selectionChanged(event);
		// Add listener in semantic element
		Object selection = event.getSelection();

		if (selection instanceof TreeSelection && null != ((TreeSelection) selection).getFirstElement()) {

			EObject selectedElement = (EObject) ((TreeSelection) selection).getFirstElement();
			if (!selectedElement.equals(previousSelectedElement)) {
				if (null != previousSelectedElement) {
					previousSelectedElement.eAdapters().remove(changeListener);
				}
				previousSelectedElement = selectedElement;
				selectedElement.eAdapters().add(changeListener);
			}
		}
	}

	/**
	 * Update providers.
	 */
	protected void updateProviders() {
		multiReferenceEditor.getViewer().setContentProvider(new StereotypeToApplyContentProvider());
		multiReferenceEditor.getViewer().setLabelProvider(new StereotypeToApplyLabelProvider());
	}

	/**
	 * {@inheritDoc}
	 * <br>
	 * Remove listener in selection.
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView#dispose()
	 */
	@Override
	public void dispose() {
		// Remove the listener.
		if (null != previousSelectedElement) {
			previousSelectedElement.eAdapters().remove(changeListener);
		}
		super.dispose();
	}

}
