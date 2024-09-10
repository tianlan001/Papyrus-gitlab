/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.providers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.AbstractEditor;
import org.eclipse.papyrus.infra.widgets.editors.ICommitListener;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * A content provider which encapsule an other permitting to see its content flat.
 * It provide button to sort content added by {@link FlattenableFilteredContentProvider#createViewerToolbar(Composite)}.
 */
public class FlatFilteredContentProvider
		implements IAdaptableContentProvider, ICommitListener, IGraphicalContentProvider, IStaticContentProvider, IHierarchicContentProvider, IFlattenableContentProvider, IRevealSemanticElement, IDependableContentProvider {

	/** set to true if the content is flat */
	protected boolean sorted = true;

	/** The encapsulate provider. */
	protected ITreeContentProvider provider;

	protected ToolItem sortButton;

	/** The dialog settings key for this class. */
	protected static final String DIALOG_SETTINGS_KEY = FlatFilteredContentProvider.class.getName();

	/** The dialogs settings key for the flat value */
	protected static final String SORT_SETTINGS_KEY = "sort"; //$NON-NLS-1$

	/** The default profile icon path. */
	protected static final String ICONS_ALPHA_SORT = "/icons/sort.gif";//$NON-NLS-1$

	/** the viewer. */
	private StructuredViewer viewer;

	/**
	 * Constructor.
	 */
	public FlatFilteredContentProvider() {
		initializeDialogsSettings();
	}

	/**
	 * Constructor.
	 *
	 * @param provider
	 *            The contained content provider.
	 */
	public FlatFilteredContentProvider(final IHierarchicContentProvider provider) {
		this.provider = provider;
		initializeDialogsSettings();
	}

	/**
	 * initialize settings.
	 */
	protected void initializeDialogsSettings() {
		setAlphaSorted(getDialogSettings().getBoolean(SORT_SETTINGS_KEY));
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.AbstractFilteredContentProvider#createAfter(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createAfter(final Composite parent) {
		if (provider instanceof IGraphicalContentProvider) {
			((IGraphicalContentProvider) provider).createAfter(parent);
		}
	}

	/**
	 * {@inheritDoc}<br>
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createViewerToolbar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createViewerToolbar(final Composite parent) {
		createSortButton(parent);
		// Don't call super to unable collapse and expand button
	}

	/**
	 * create the sort ToolItem.
	 *
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	protected void createSortButton(final Composite parent) {
		initializeDialogsSettings();

		ToolBar Toolbar = new ToolBar(parent, SWT.NONE);

		sortButton = new ToolItem(Toolbar, SWT.CHECK);
		sortButton.setImage(Activator.getDefault().getImage(ICONS_ALPHA_SORT));
		sortButton.setToolTipText(Messages.FlatFilteredContentProvider_sortButtonTooltip);
		sortButton.setSelection(sorted);

		sortButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent event) {
				setAlphaSorted(!sorted);
				getDialogSettings().put(SORT_SETTINGS_KEY, sorted);
				sortButton.setSelection(sorted);
			}
		});

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		Object[] elements = provider.getElements(inputElement);
		Set<Object> list = new HashSet<>();
		for (Object object : elements) {
			if (isValidValue(object)) {
				list.add(object);
			}
			list.addAll(Arrays.asList(getAllChildren(object, new HashSet<Object>())));
		}
		return list.toArray();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		return new Object[0];
	}

	/**
	 * Get all valid children from a parent element.
	 * 
	 * @param parentElement
	 *            The parent element.
	 * @param visitedElements
	 *            The already visited elements( cache to avoid loops).
	 * @return The array of all visited children.
	 */
	protected Object[] getAllChildren(final Object parentElement, final Set<Object> visitedElements) {
		Set<Object> list = new HashSet<Object>();
		Object[] children = provider.getChildren(parentElement);
		if (null != children && 0 < children.length) {
			for (int i = 0; i < children.length; i++) {
				boolean visited = false;

				if (visitedElements.contains(children[i])) {
					visited = true;
				} else {
					visitedElements.add(children[i]);
				}

				// get the semantic element for some case
				if (!visited) {
					EObject eObject = PlatformHelper.getAdapter(children[i], EObject.class);
					if (null != eObject && !eObject.equals(children[i]) && visitedElements.contains(eObject)) {
						visited = true;
					} else {
						visitedElements.add(eObject);
					}
				}

				if (!visited && isValidValue(children[i])) {
					list.add(children[i]);
				}
				if (!visited) {
					list.addAll(Arrays.asList(getAllChildren(children[i], visitedElements)));
				}

			}
		}
		return list.toArray();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		provider.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		if (viewer instanceof StructuredViewer) {
			this.viewer = (StructuredViewer) viewer;
		}
		provider.inputChanged(viewer, oldInput, newInput);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 */
	@Override
	public boolean isValidValue(final Object element) {
		boolean isValidValue = true;
		if (provider instanceof IHierarchicContentProvider) {
			isValidValue = ((IHierarchicContentProvider) provider).isValidValue(element);
		}
		return isValidValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(final Object element) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(final Object element) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IFlattenableContentProvider#setFlat(boolean)
	 */
	@Override
	public void setFlat(final boolean isFlat) {
		if (provider instanceof IFlattenableContentProvider) {
			((IFlattenableContentProvider) provider).setFlat(isFlat);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 */
	@Override
	public Object[] getElements() {
		if (provider instanceof IStaticContentProvider) {
			return ((IStaticContentProvider) provider).getElements();
		}
		return getElements(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement#revealSemanticElement(java.util.List)
	 */
	@Override
	public void revealSemanticElement(final List<?> elementList) {
		if (provider instanceof IRevealSemanticElement) {
			((IRevealSemanticElement) provider).revealSemanticElement(elementList);
		} else if (provider instanceof CompositeContentProvider) {
			if (null != ((CompositeContentProvider) provider).getContentProviders()) {
				for (ITreeContentProvider contentProvider : ((CompositeContentProvider) provider).getContentProviders()) {
					if (contentProvider instanceof IRevealSemanticElement) {
						((IRevealSemanticElement) contentProvider).revealSemanticElement(elementList);
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createBefore(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createBefore(final Composite parent) {
		if (provider instanceof IGraphicalContentProvider) {
			((IGraphicalContentProvider) provider).createBefore(parent);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IFlattenableContentProvider#isFlat()
	 */
	@Override
	public boolean isFlat() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.ICommitListener#commit(org.eclipse.papyrus.infra.widgets.editors.AbstractEditor)
	 */
	@Override
	public void commit(final AbstractEditor editor) {
		if (provider instanceof ICommitListener) {
			((ICommitListener) provider).commit(editor);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider#getAdaptedValue(java.lang.Object)
	 */
	@Override
	public Object getAdaptedValue(final Object containerElement) {
		if (provider instanceof IAdaptableContentProvider) {
			return ((IAdaptableContentProvider) provider).getAdaptedValue(containerElement);
		}
		return null;
	}

	/**
	 * Returns the dialog settings. Returned object can't be null.
	 *
	 * @return dialog settings for this dialog
	 */
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS_KEY);
		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS_KEY);
		}
		return settings;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IDependableContentProvider#getContentProvider()
	 *
	 * @return
	 */
	@Override
	public ITreeContentProvider getContentProvider() {
		return provider;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IDependableContentProvider#setContentProvider(org.eclipse.jface.viewers.IContentProvider)
	 *
	 * @param provider
	 */
	@Override
	public void setContentProvider(final ITreeContentProvider provider) {
		this.provider = provider;
	}

	/**
	 * Set if content should be sort alphabetically.
	 * 
	 * @param sorted
	 *            Set to true if sorted.
	 */
	public void setAlphaSorted(final boolean sorted) {
		this.sorted = sorted;
		if ((null != viewer) && (null != viewer.getControl()) && !viewer.getControl().isDisposed()) {
			if (sorted) {
				viewer.setComparator(new ViewerComparator());
			} else {
				viewer.setComparator(null);
			}
			viewer.refresh();
		}
	}

}
