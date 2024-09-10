/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST.
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
 *  Pierre GAUTIER (CEA LIST) - bug 521857
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.selectors;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.papyrus.infra.widgets.editors.IElementSelectionListener;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A selector for String values, or values that can be represented
 * as text in general.
 * Displays a field where the user can enter the new values.
 * The field can be multiline or single line
 *
 * @author Camille Letavernier
 *
 */
public class StringSelector implements IElementSelector {

	public static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * The text box used to enter a value for this selector
	 */
	protected Text text;

	/**
	 * Indicates if this StringSelector is multiline
	 */
	protected boolean multiline;

	/**
	 * The registered IElementSelectionListener
	 */
	protected Set<IElementSelectionListener> elementSelectionListeners = new HashSet<IElementSelectionListener>();

	/**
	 * Constructs a single-line String Selector
	 */
	public StringSelector() {
		this(false);
	}

	/**
	 * Constructs a String Selector
	 *
	 * @param multiline
	 *            True if the string values can contain more than one line
	 */
	public StringSelector(final boolean multiline) {
		this.multiline = multiline;
	}

	
	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#getSelectedElements()
	 *
	 * @return
	 */
	@Override
	public Object[] getSelectedElements() {
		final String[] result = new String[] { text.getText() };
		text.setText(""); //$NON-NLS-1$
		return result;
	}
	
	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#setSelectedElements(java.lang.Object[])
	 *
	 * @param elements
	 */
	@Override
	public void setSelectedElements(final Object[] elements) {
		// Nothing
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#createControls(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createControls(final Composite parent) {
		text = new Text(parent, SWT.MULTI | SWT.BORDER);
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(final KeyEvent e) {
				// Nothing
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				if ((e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) && (e.stateMask == SWT.NONE && !multiline || (e.stateMask & SWT.CTRL) != 0 && multiline)) {
					if (!elementSelectionListeners.isEmpty()) {
						final Object[] elements = getSelectedElements();
						if (elements == null || elements.length == 0) {
							return;
						}
						String str = (String) elements[0];
						if (str.endsWith(StringSelector.LINE_SEPARATOR)) {
							str = str.substring(0, str.length() - StringSelector.LINE_SEPARATOR.length());
						}
						if (!"".equals(str)) { //$NON-NLS-1$
							for (final IElementSelectionListener listener : elementSelectionListeners) {
								listener.addElements(new Object[] { str });
							}
						}
					}
				}
			}

		});
	}
	
	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#getAllElements()
	 *
	 * @return
	 */
	@Override
	public Object[] getAllElements() {
		return getSelectedElements();
	}
	
	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#newObjectCreated(java.lang.Object)
	 *
	 * @param newObject
	 */
	@Override
	public void newObjectCreated(final Object newObject) {
		// Ignored
	}
	
	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#clearTemporaryElements()
	 *
	 */
	@Override
	public void clearTemporaryElements() {
		// Ignored
	}



	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#addElementSelectionListener(org.eclipse.papyrus.infra.widgets.editors.IElementSelectionListener)
	 *
	 * @param listener
	 */
	@Override
	public void addElementSelectionListener(final IElementSelectionListener listener) {
		elementSelectionListeners.add(listener);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.IElementSelector#removeElementSelectionListener(org.eclipse.papyrus.infra.widgets.editors.IElementSelectionListener)
	 *
	 * @param listener
	 */
	@Override
	public void removeElementSelectionListener(final IElementSelectionListener listener) {
		elementSelectionListeners.remove(listener);
	}


}
