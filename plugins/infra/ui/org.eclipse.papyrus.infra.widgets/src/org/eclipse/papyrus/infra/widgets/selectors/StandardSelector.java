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
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 475369
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.selectors;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.AbstractEditor;
import org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor;
import org.eclipse.papyrus.infra.widgets.editors.ICommitListener;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelectionListener;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.util.EditorFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Creates an element selector from an AbstractValueEditor. This class is a
 * generic implementation for element selectors.
 *
 * @author Camille Letavernier
 *
 */
public class StandardSelector implements IElementSelector {

	/**
	 * The AbstractValueEditor class used for instantiating this selector.
	 *
	 * @deprecated since 3.3
	 */
	@Deprecated
	protected Class<? extends AbstractValueEditor> editorClass;

	/**
	 * The editor factory to create the correct editor.
	 *
	 * @since 3.3
	 */
	private EditorFactory editorFactory;

	/**
	 * The AbstractValueEditor used by this selector
	 */
	protected AbstractValueEditor editor;

	protected Set<IElementSelectionListener> elementSelectionListeners = new HashSet<>();

	/**
	 * Instantiates this selector, using the specified editor class
	 *
	 * @param editorClass
	 *            The AbstractValueEditor Class used to instantiate this selector
	 *
	 * @deprecated since 3.3, use {@link #StandardSelector(EditorFactory)} instead.
	 */
	@Deprecated
	public StandardSelector(Class<? extends AbstractValueEditor> editorClass) {
		this(reflexiveEditorFactory(editorClass));
	}

	/**
	 * Create an {@link EditorFactory} from an {@link AbstractValueEditor} class.
	 * This is only here to support legacy behavior; {@link EditorFactory} is the preferred
	 * way to create new editor instances.
	 *
	 * @deprecated
	 */
	@Deprecated
	private static EditorFactory reflexiveEditorFactory(Class<? extends AbstractValueEditor> editorClass) {
		return (parent, style, label, commitOnFocusLost) -> {
			try {
				Constructor<? extends AbstractValueEditor> construct = editorClass.getDeclaredConstructor(Composite.class, Integer.TYPE, String.class, Boolean.TYPE);
				return construct.newInstance(parent, SWT.BORDER, null, false);
			} catch (Exception ex) {
				Activator.log.error(ex);
				return null;
			}
		};
	}

	/**
	 * Instantiates this selector, using the specified editor class
	 *
	 * @param editorFactory
	 *            The editor factory to create the correct editor.
	 *
	 * @since 3.3
	 */
	public StandardSelector(final EditorFactory editorFactory) {
		Assert.isNotNull(editorFactory, "The StandardSelector editor class should not be null"); //$NON-NLS-1$
		this.editorFactory = editorFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getSelectedElements() {
		Object value = editor.getValue();
		if (value == null) {
			return new Object[] {};
		}

		return new Object[] { value };
	}

	/**
	 * Ignored. The generic selectors can't be filtered.
	 */
	@Override
	public void setSelectedElements(Object[] elements) {
		// Ignored
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getAllElements() {
		return getSelectedElements();
	}

	/**
	 * {@inheritDoc}
	 *
	 * The control for this selector is obtained by instantiating the generic class with a parent
	 * composite and a default style
	 */
	@Override
	public void createControls(Composite parent) {
		try {
			editor = editorFactory.create(parent, SWT.BORDER, null, false);
			editor.addCommitListener(new ICommitListener() {

				@Override
				public void commit(AbstractEditor editor) {
					if (!elementSelectionListeners.isEmpty()) {
						Object value = StandardSelector.this.editor.getValue();
						for (IElementSelectionListener listener : elementSelectionListeners) {
							listener.addElements(new Object[] { value });
						}
					}
				}

			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void newObjectCreated(Object newObject) {
		// Ignored
	}

	@Override
	public void clearTemporaryElements() {
		// Ignored
	}

	@Override
	public void addElementSelectionListener(IElementSelectionListener listener) {
		elementSelectionListeners.add(listener);
	}

	@Override
	public void removeElementSelectionListener(IElementSelectionListener listener) {
		elementSelectionListeners.remove(listener);
	}

	/**
	 * Return the editor factory.
	 *
	 * @return the editorFactory The editor factory.
	 * @since 3.3
	 */
	public EditorFactory getEditorFactory() {
		return editorFactory;
	}

}
