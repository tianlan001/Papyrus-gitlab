/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
package org.eclipse.papyrus.infra.widgets.providers;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.StringWithClearEditor;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;


/**
 * A generic implementation for a IGraphicalContentProvider.
 * This class doesn't provide any element, and should be extended.
 *
 * It implements a filter for List or Tree elements, based on the label
 * provided by the viewer's label provider (Or Object#toString() if the viewer
 * doesn't have a label provider).
 *
 * A Text widget is added before the display control to insert the filter
 * pattern. An element is matched if at least one of these conditions is
 * matched :
 * - The element's name matches the pattern
 * - One of the element's children matches the pattern
 * - One of the element's parent matches the pattern
 *
 * The elements' hierarchy is obtained via the viewer's ContentProvider.
 *
 * @author Camille Letavernier
 */
// TODO : Encapsulate a IStructuredContentProvider and make this class concrete
public abstract class AbstractFilteredContentProvider implements IGraphicalContentProvider {

	/** The current viewer. */
	protected StructuredViewer viewer;

	/** The base pattern . */
	public static final String BASE_PATTERN = "*"; //$NON-NLS-1$

	/** true if must be show if has visible parent. */
	protected boolean showIfHasVisibleParent = false;

	/** The pattern viewer filter. */
	private PatternViewerFilter patternFilter;

	/** The current filter pattern as String. */
	private String currentFilterPattern;

	@Override
	public void dispose() {
		// Nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (viewer instanceof StructuredViewer) {
			this.viewer = (StructuredViewer) viewer;
		}
	}

	@Override
	public void createBefore(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

		// Create filter composite
		createPatternFilter(composite);
		createCaseSensitiveButton(composite);
	}

	/**
	 * create the Case sensitive checkBox.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	protected void createCaseSensitiveButton(final Composite parent) {
		// Create the checkbox button
		Button checkBoxCaseSensitive = new Button(parent, SWT.CHECK);

		checkBoxCaseSensitive.setText(Messages.AbstractFilteredContentProvider_CaseSensitiveLabel);
		checkBoxCaseSensitive.setToolTipText(Messages.AbstractFilteredContentProvider_CaseSensitiveTooltip);
		checkBoxCaseSensitive.addSelectionListener(new SelectionAdapter() {


			@Override
			public void widgetSelected(SelectionEvent event) {
				if (patternFilter instanceof PatternViewerFilter) {
					((PatternViewerFilter) patternFilter).setIgnoreCase(!checkBoxCaseSensitive.getSelection());
				}
				viewer.refresh();
			}
		});
	}

	/**
	 * Create the pattern filter.
	 * 
	 * @param parent
	 */
	protected void createPatternFilter(final Composite parent) {
		StringWithClearEditor filterField = new StringWithClearEditor(parent, SWT.BORDER);
		filterField.setToolTipText(Messages.AbstractFilteredContentProvider_FilterFieldTooltip);
		filterField.setValidateOnDelay(Activator.getValidationDelay());
		filterField.setValidateOnDelay(Activator.isFilterValidateOnDelay());

		// Set replacement of stereotype delimiters
		if (Activator.isStereotypeDelimitersReplaced()) {
			filterField.addStringToReplace(Activator.ST_LEFT_BEFORE, Activator.ST_LEFT);
			filterField.addStringToReplace(Activator.ST_RIGHT_BEFORE, Activator.ST_RIGHT);
		} else {
			filterField.clearStringToReplace();
		}

		GridLayoutFactory.fillDefaults().applyTo(filterField);

		filterField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		patternFilter = getViewerFilter();
		currentFilterPattern = BASE_PATTERN;
		((PatternViewerFilter) patternFilter).setPattern(BASE_PATTERN);

		filterField.addCommitListener(editor -> {
			String filterPattern = (String) ((StringWithClearEditor) editor).getValue();
			((PatternViewerFilter) patternFilter).setPattern(filterPattern);

			List<ViewerFilter> filtersAsList = Arrays.asList(viewer.getFilters());
			if (!filtersAsList.contains(patternFilter)) {
				viewer.addFilter(patternFilter);
			}
			viewer.refresh();

			if (!("".equals(filterPattern) || currentFilterPattern.equals(filterPattern))) { //$NON-NLS-1$
				currentFilterPattern = filterPattern;
			}
		});

		// Focus on viewer when press the down arrow
		filterField.getText().addKeyListener(new KeyAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					viewer.getControl().setFocus();
				}
			}
		});

	}

	@Override
	public void createAfter(Composite parent) {
		// Nothing
	}

	protected PatternViewerFilter getViewerFilter() {
		PatternViewerFilter filter = new PatternViewerFilter();
		filter.setStrict(false);
		currentFilterPattern = BASE_PATTERN;
		filter.setPattern(currentFilterPattern);
		filter.setShowIfHasVisibleParent(showIfHasVisibleParent);
		return filter;
	}

}
