/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Mickael ADAM mickael.adam@all4tec.net - implement IFlattenableContentProvider
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.contentprovider;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.ui.emf.utils.EcoreModelContentProvider;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.infra.widgets.editors.AbstractEditor;
import org.eclipse.papyrus.infra.widgets.editors.ICommitListener;
import org.eclipse.papyrus.infra.widgets.editors.StringWithClearEditor;
import org.eclipse.papyrus.infra.widgets.providers.IFlattenableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.PatternViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Represents a simple ECore model content provider for the selection of a table's root model element
 *
 * @author Laurent Wouters
 */
public class ContextFeatureContentProvider extends EcoreModelContentProvider implements IStaticContentProvider, IHierarchicContentProvider, IFlattenableContentProvider, IGraphicalContentProvider {
	/**
	 * The table
	 */
	private Table table;

	/** true if flat. */
	private boolean flat;

	/** The current viewer. */
	private StructuredViewer viewer;

	/** The pattern viewer filter. */
	private PatternViewerFilter patternFilter;

	/** The current pattern as String. */
	private String currentFilterPattern;

	/**
	 * Initializes the provider with the given root
	 *
	 * @param diagram
	 *            The diagram for which a root model element is to be provided
	 * @param root
	 *            The root object
	 */
	public ContextFeatureContentProvider(Table table, EObject root) {
		super(root);
		this.table = table;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 */
	@Override
	public Object[] getElements() {
		return getElements(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 */
	@Override
	public boolean isValidValue(final Object element) {
		if (element == null) {
			return false;
		}
		if (!(element instanceof EObject)) {
			return false;
		}
		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		RepresentationKind repKind = manager.getRepresentationKindById(table.getTableKindId());
		ViewPrototype prototype = ViewPrototype.get((PapyrusRepresentationKind) repKind);
		if (prototype == null) {
			return false;
		}
		return PolicyChecker.getFor((EObject)element).canHaveNewView((EObject) element, table.getOwner(), prototype);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IFlattenableContentProvider#setFlat(boolean)
	 */
	@Override
	public void setFlat(final boolean isFlat) {
		if (isFlat != this.flat) {
			this.flat = isFlat;
			if (null != viewer) {
				viewer.refresh();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IFlattenableContentProvider#isFlat()
	 */
	@Override
	public boolean isFlat() {
		return flat;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.utils.EcoreModelContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		if (viewer instanceof StructuredViewer) {
			this.viewer = (StructuredViewer) viewer;
		} else {
			this.viewer = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createBefore(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createBefore(final Composite parent) {
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
	 * @since 3.0
	 */
	protected void createCaseSensitiveButton(final Composite parent) {
		// Create the checkbox button
		final Button checkBoxCaseSensitive = new Button(parent, SWT.CHECK);

		checkBoxCaseSensitive.setText(Messages.ContextFeatureContentProvider_CaseSensitiveLabel);
		checkBoxCaseSensitive.setToolTipText(Messages.ContextFeatureContentProvider_CaseSensitiveTooltip);
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
	 * @since 3.0
	 */
	protected void createPatternFilter(final Composite parent) {
		StringWithClearEditor editor = new StringWithClearEditor(parent, SWT.BORDER);
		editor.setToolTipText(Messages.ContextFeatureContentProvider_FilterTooltip);
		editor.setValidateOnDelay(org.eclipse.papyrus.infra.widgets.Activator.getValidationDelay());
		editor.setValidateOnDelay(org.eclipse.papyrus.infra.widgets.Activator.isFilterValidateOnDelay());

		GridLayoutFactory.fillDefaults().applyTo(editor);

		editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		patternFilter = new PatternViewerFilter();
		currentFilterPattern = "*"; //$NON-NLS-1$
		((PatternViewerFilter) patternFilter).setPattern(currentFilterPattern);

		editor.addCommitListener(new ICommitListener() {

			@Override
			public void commit(AbstractEditor editor) {
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
			}

		});

		// Focus on viewer when press the down arrow
		editor.getText().addKeyListener(new KeyAdapter() {
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createAfter(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createAfter(Composite parent) {
		// Do Nothing
	}
}
