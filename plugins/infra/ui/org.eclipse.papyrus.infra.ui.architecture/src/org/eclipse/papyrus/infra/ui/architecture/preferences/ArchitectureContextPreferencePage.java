/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bug 570486
 *
 *
 */
package org.eclipse.papyrus.infra.ui.architecture.preferences;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.contentassist.BoldStylerProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainMerger;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainPreferences;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

/**
 * Represents the preference page for the architecture contexts
 *
 * @since 1.0
 */
public class ArchitectureContextPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	// The ID of this preference page used to contribute via extension
	public static final String PAGE_ID = ArchitectureContextPreferencePage.class.getName();

	// A bold style
	private BoldStylerProvider boldStylerProvider;

	// Contexts viewer
	private CheckboxTreeViewer fContextsViewer;
	// Text displaying additional information
	private ListViewer fDescription;
	// Adapter Factory
	private ComposedAdapterFactory fComposedAdapterFactory;

	// Buttons
	private Button fOtherButton;
	private Button fDefaultButton;

	// the architecture domain model merger
	ArchitectureDomainMerger fMerger;

	// Preferences
	ArchitectureDomainPreferences fPreferences;

	/**
	 * Constructor.
	 */
	public ArchitectureContextPreferencePage() {
		fComposedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		fMerger = ArchitectureDomainManager.getInstance().getMerger().clone();
		fPreferences = new ArchitectureDomainPreferences();
	}

	@Override
	public Control createContents(Composite parent) {
		boldStylerProvider = new BoldStylerProvider(parent.getFont());
		Composite container = createComposite(parent, 1, 1, GridData.FILL_BOTH, 0, 0);
		createArchitectureContextsGroup(container);
		Dialog.applyDialogFont(container);
		return container;
	}

	/**
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 *
	 */
	@Override
	public void dispose() {
		if (boldStylerProvider != null) {
			boldStylerProvider.dispose();
		}
		super.dispose();
	}

	/**
	 * Creates the Architecture Context group in the page
	 */
	private void createArchitectureContextsGroup(Composite container) {
		Composite comp = createComposite(container, 1, 1, GridData.FILL_BOTH, 0, 0);
		((GridData) comp.getLayoutData()).widthHint = 350;
		createWrapLabel(comp, "Select the architecture contexts that can be applied to Papyrus models:", 2);
		createVerticalSpacer(comp, 1);

		Composite tableComposite = createComposite(comp, 2, 1, GridData.FILL_BOTH, 0, 0);
		createLabel(tableComposite, "Architecture Contexts:", 2);

		fContextsViewer = new ContainerCheckedTreeViewer(tableComposite, SWT.MULTI | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 250;
		fContextsViewer.getControl().setLayoutData(gd);

		final IStyledLabelProvider labelProvider = new AdapterFactoryLabelProvider.StyledLabelProvider(fComposedAdapterFactory, fContextsViewer);

		fContextsViewer.setContentProvider(new ITreeContentProvider() {
			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof MergedArchitectureDomain) {
					return !((MergedArchitectureDomain) element).getContexts().isEmpty();
				}
				return false;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				Collection<MergedArchitectureDomain> domains = ((ArchitectureDomainMerger) inputElement).getDomains();
				return domains.toArray();
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof MergedArchitectureDomain) {
					Collection<MergedArchitectureContext> contexts = ((MergedArchitectureDomain) parentElement).getContexts();
					return contexts.toArray();
				}
				return null;
			}
		});
		fContextsViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(labelProvider));
		fContextsViewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				String name1 = labelProvider.getStyledText(e1).getString();
				String name2 = labelProvider.getStyledText(e2).getString();
				return getComparator().compare(name1, name2);
			}
		});
		fContextsViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
				updateDetails();
			}
		});
		fContextsViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof MergedArchitectureContext) {
					return !fPreferences.getExcludedContextIds().contains(((MergedArchitectureContext) element).getId());
				} else {
					return fContextsViewer.getChecked(element);
				}
			}
		});
		fContextsViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Collection<MergedArchitectureContext> contexts = null;
				if (event.getElement() instanceof MergedArchitectureContext) {
					contexts = Collections.singletonList((MergedArchitectureContext) event.getElement());
				} else {
					contexts = ((MergedArchitectureDomain) event.getElement()).getContexts();
				}
				for (MergedArchitectureContext context : contexts) {
					if (event.getChecked() == false) {
						fPreferences.getExcludedContextIds().add(context.getId());
					} else {
						fPreferences.getExcludedContextIds().remove(context.getId());
					}
				}
			}
		});
		fContextsViewer.setInput(fMerger);
		fContextsViewer.expandAll();

		ColumnViewerToolTipSupport.enableFor(fContextsViewer, ToolTip.NO_RECREATE);

		Composite buttonComposite = createComposite(tableComposite, 1, 1, GridData.FILL_VERTICAL | GridData.VERTICAL_ALIGN_BEGINNING, 0, 0);
		createVerticalSpacer(buttonComposite, 1);

		fOtherButton = createPushButton(buttonComposite, "Other Architecture Models...", null, SWT.PUSH);
		fOtherButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				handleOther();
			}
		});

		fDefaultButton = createPushButton(buttonComposite, "Make Default", null, SWT.PUSH);
		fDefaultButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleDefault();
			}
		});

		updateButtons();

		Composite descriptionComposite = createComposite(comp, 1, 1, GridData.FILL_HORIZONTAL, 0, 0);

		createLabel(descriptionComposite, "Description:", 1);

		fDescription = new ListViewer(descriptionComposite);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		fDescription.getControl().setLayoutData(gd);
		fDescription.setContentProvider(new IStructuredContentProvider() {
			private Object input;

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				this.input = newInput;
			}

			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return new Object[] { input };
			}
		});
		fDescription.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MergedADElement) element).getDescription();
			}
		});
	}

	/**
	 * Handles the selection of other models by merging them
	 */
	private void handleOther() {
		ArchitectureModelSelectDialog dialog = new ArchitectureModelSelectDialog(getShell(), "File Selection", fPreferences.getAddedModelURIs().toArray(new String[0]));
		dialog.open();
		List<URI> files = dialog.getURIs();
		fPreferences.getAddedModelURIs().clear();
		for (URI file : files) {
			fPreferences.getAddedModelURIs().add(file.toString());
		}
		fMerger.setPreferenceModels(files);
		fContextsViewer.refresh();
		fContextsViewer.expandAll();
	}

	/**
	 * Handles the default button by setting the selected context as default
	 */
	protected void handleDefault() {
		IStructuredSelection selection = (IStructuredSelection) fContextsViewer.getSelection();
		MergedArchitectureContext selected = (MergedArchitectureContext) selection.getFirstElement();
		fPreferences.setDefaultContextId(selected.getId());
		fContextsViewer.refresh();
	}

	/**
	 * Update the enabled state of the buttons
	 */
	protected void updateButtons() {
		IStructuredSelection selection = (IStructuredSelection) fContextsViewer.getSelection();
		if (selection.size() == 1) {
			MergedADElement selected = (MergedADElement) selection.getFirstElement();
			if (selected instanceof MergedArchitectureContext) {
				fDefaultButton.setEnabled(true);
				return;
			}
		}
		fDefaultButton.setEnabled(false);
		fDefaultButton.setSelection(false);
	}

	/**
	 * Updates the details text box with information about the currently selected target
	 */
	protected void updateDetails() {
		IStructuredSelection selection = (IStructuredSelection) fContextsViewer.getSelection();
		if (selection.size() == 1) {
			MergedADElement selected = (MergedADElement) selection.getFirstElement();
			fDescription.setInput(selected);
		} else {
			fDescription.setInput(null);
		}
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	public void performDefaults() {
		fPreferences.reset();
		fContextsViewer.refresh();
	}

	@Override
	public boolean performOk() {
		fPreferences.write();
		return true;
	}

	private static Composite createComposite(Composite parent, int columns, int hspan, int fill, int marginwidth, int marginheight) {
		Composite g = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(columns, false);
		layout.marginWidth = marginwidth;
		layout.marginHeight = marginheight;
		g.setLayout(layout);
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	private static Label createLabel(Composite parent, String text, int hspan) {
		Label l = new Label(parent, SWT.NONE);
		l.setFont(parent.getFont());
		l.setText(text);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		gd.grabExcessHorizontalSpace = false;
		l.setLayoutData(gd);
		return l;
	}

	private static Label createWrapLabel(Composite parent, String text, int hspan) {
		Label l = new Label(parent, SWT.NONE | SWT.WRAP);
		l.setFont(parent.getFont());
		l.setText(text);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		l.setLayoutData(gd);
		return l;
	}

	private static void createVerticalSpacer(Composite parent, int numlines) {
		Label lbl = new Label(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		Layout layout = parent.getLayout();
		if (layout instanceof GridLayout) {
			gd.horizontalSpan = ((GridLayout) parent.getLayout()).numColumns;
		}
		gd.heightHint = numlines;
		lbl.setLayoutData(gd);
	}

	private static Button createPushButton(Composite parent, String label, Image image, int style) {
		Button button = new Button(parent, style);
		button.setFont(parent.getFont());
		if (image != null) {
			button.setImage(image);
		}
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);
		setButtonDimensionHint(button);
		return button;
	}

	private static void setButtonDimensionHint(Button button) {
		Assert.isNotNull(button);
		Object gd = button.getLayoutData();
		if (gd instanceof GridData) {
			((GridData) gd).widthHint = getButtonWidthHint(button);
			((GridData) gd).horizontalAlignment = GridData.FILL;
		}
	}

	private static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		PixelConverter converter = new PixelConverter(button);
		int widthHint = converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}

}
