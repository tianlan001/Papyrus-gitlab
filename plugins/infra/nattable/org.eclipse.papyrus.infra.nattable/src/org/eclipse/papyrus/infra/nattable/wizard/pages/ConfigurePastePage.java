/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.wizard.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.nattable.contentprovider.RowContainmentFeatureContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.RowElementTypeIdContentProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.StringComparator;
import org.eclipse.papyrus.infra.nattable.wizard.CategoriesWizardUtils;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.strategy.ProviderBasedBrowseStrategy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * The page to manage the paste configurations.
 */
public class ConfigurePastePage extends WizardPage {

	/**
	 * The checked image.
	 */
	private static final Image CHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.infra.nattable.common", "icons/checked.gif"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The unchecked image.
	 */
	private static final Image UNCHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.infra.nattable.common", "icons/unchecked.gif"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The undefined value.
	 */
	private static String UNDEFINED = "<Undefined>"; //$NON-NLS-1$


	/**
	 * The nattable manager.
	 */
	protected INattableModelManager nattableManager = null;

	/**
	 * The tree viewer.
	 */
	protected TreeViewer treeViewer;

	/**
	 * the initial selection
	 */
	protected List<Object> initialSelection;

	/**
	 * The label provider for the initial selection.
	 */
	protected ILabelProvider treeItemWrappedLabelProvider;

	/**
	 * The label provider for the containmentFeature.
	 */
	protected ILabelProvider containmentFeatureLabelProvider;

	/**
	 * The content provider for the containmentFeature.
	 */
	protected IStaticContentProvider containmentFeatureContentProvider;

	/**
	 * The modified paste configurations by tree item axis.
	 */
	protected Map<ITreeItemAxis, PasteEObjectConfiguration> pasteConfigurations;

	/**
	 * Constructor.
	 *
	 * @param pageName
	 *            The page name.
	 * @param nattableManager
	 *            The nattable manager.
	 * @param treeItemWrappedLabelProvider
	 *            The label provider for the initial selection.
	 * @param containmentFeatureLabelProvider
	 *            The label provider for the containment feature tree viewer.
	 * @param containmentFeatureContentProvider
	 *            The content provider for the containment feature tree viewer.
	 */
	public ConfigurePastePage(final String pageName, final INattableModelManager nattableManager, final ILabelProvider treeItemWrappedLabelProvider, final ILabelProvider containmentFeatureLabelProvider,
			final IStaticContentProvider containmentFeatureContentProvider) {
		super(pageName);
		setTitle(pageName);
		this.nattableManager = nattableManager;
		this.pasteConfigurations = new HashMap<ITreeItemAxis, PasteEObjectConfiguration>();
		this.treeItemWrappedLabelProvider = treeItemWrappedLabelProvider;
		this.containmentFeatureLabelProvider = containmentFeatureLabelProvider;
		this.containmentFeatureContentProvider = containmentFeatureContentProvider;
	}

	/**
	 * This allows to set the initial element selected and update the treeviewer if already done.
	 * 
	 * @param arrayList
	 *            The initial element selected.
	 */
	public void setInitialElementSelections(final List<Object> arrayList) {
		this.initialSelection = arrayList;
		if (null != treeViewer) {
			treeViewer.setInput(this.initialSelection);
			treeViewer.expandAll();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		// Create a parent composite for paste axis (for column and row)
		final Composite treeViewerComposite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, true);
		treeViewerComposite.setLayout(gridLayout);

		final Tree tree = new Tree(treeViewerComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		final TreeColumnLayout columnLayout = new TreeColumnLayout();
		treeViewerComposite.setLayout(columnLayout);

		treeViewer = new TreeViewer(tree);

		// Create the first column and manage its display by the label provider of the initial selection
		final TreeViewerColumn firstViewerColumn = createTreeViewerColumn(treeViewer, Messages.ConfigurePastePage_firstColumnTitle);
		firstViewerColumn.getColumn().setAlignment(SWT.CENTER);
		columnLayout.setColumnData(firstViewerColumn.getColumn(), new ColumnPixelData(225));
		firstViewerColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return treeItemWrappedLabelProvider.getText(element);
			}

			@Override
			public Image getImage(final Object element) {
				return treeItemWrappedLabelProvider.getImage(element);
			}
		});

		// Create the second viewer column and manage its display and edition
		final TreeViewerColumn secondViewerColumn = createTreeViewerColumn(treeViewer, Messages.ConfigurePastePage_secondColumnTitle);
		secondViewerColumn.getColumn().setAlignment(SWT.CENTER);
		columnLayout.setColumnData(secondViewerColumn.getColumn(), new ColumnPixelData(75));
		secondViewerColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return null;
			}

		});
		secondViewerColumn.setEditingSupport(new EditingSupport(treeViewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (canEditPasteConfiguration(element)) {
					final PasteEObjectConfiguration pasteConfiguration = getPasteConfigurationFromTreeItemAxis((ITreeItemAxis) element);

					if (null != pasteConfiguration) {
						if (null == pasteConfigurations.get(element)) {
							pasteConfigurations.put((ITreeItemAxis) element, EcoreUtil.copy(pasteConfiguration));
						}
						pasteConfigurations.get(element).setDetachedMode((Boolean) value);
					}
					treeViewer.refresh();
				}
			}

			@Override
			protected Object getValue(final Object element) {
				return getDetachedMode(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new CheckboxCellEditor(tree, SWT.CHECK | SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(final Object element) {
				return canEditPasteConfiguration(element);
			}
		});

		// Create the third viewer column and manage its display and edition
		final TreeViewerColumn thirdViewerColumn = createTreeViewerColumn(treeViewer, Messages.ConfigurePastePage_thirdColumnTitle);
		thirdViewerColumn.getColumn().setAlignment(SWT.CENTER);
		columnLayout.setColumnData(thirdViewerColumn.getColumn(), new ColumnPixelData(230));
		thirdViewerColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				// Must the managed in the paint item listener of the table (because we don't want to center values)
				return null;
			}
		});
		thirdViewerColumn.setEditingSupport(new EditingSupport(treeViewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (canEditPasteConfiguration(element)) {
					final PasteEObjectConfiguration pasteConfiguration = getPasteConfigurationFromTreeItemAxis((ITreeItemAxis) element);

					if (null != pasteConfiguration) {
						if (null == pasteConfigurations.get(element)) {
							pasteConfigurations.put((ITreeItemAxis) element, EcoreUtil.copy(pasteConfiguration));
						}
						pasteConfigurations.get(element).setPastedElementId((String) value);
					}
					treeViewer.refresh();
				}
			}

			@Override
			protected Object getValue(final Object element) {
				return getPastedElementId(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new ExtendedComboBoxCellEditor(tree, Arrays.asList(new RowElementTypeIdContentProvider(nattableManager).getElements()), new LabelProvider(), SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(final Object element) {
				return canEditPasteConfiguration(element);
			}
		});

		// Create the fourth viewer column and manage its display and edition
		final TreeViewerColumn fourthViewerColumn = createTreeViewerColumn(treeViewer, Messages.ConfigurePastePage_fourthColumnTitle);
		fourthViewerColumn.getColumn().setAlignment(SWT.CENTER);
		columnLayout.setColumnData(fourthViewerColumn.getColumn(), new ColumnPixelData(200));
		fourthViewerColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				// Must the managed in the paint item listener of the table (because we don't want to center values)
				return null;
			}

			@Override
			public Image getImage(final Object element) {
				EStructuralFeature containmentFeature = getContainmentFeature(element);
				if (null != containmentFeature) {
					return containmentFeatureLabelProvider.getImage(containmentFeature);
				}
				return null;
			}
		});
		fourthViewerColumn.setEditingSupport(new EditingSupport(treeViewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (value instanceof EStructuralFeature) {
					if (canEditPasteConfiguration(element)) {
						final PasteEObjectConfiguration pasteConfiguration = getPasteConfigurationFromTreeItemAxis((ITreeItemAxis) element);

						if (null != pasteConfiguration) {
							if (null == pasteConfigurations.get(element)) {
								pasteConfigurations.put((ITreeItemAxis) element, EcoreUtil.copy(pasteConfiguration));
							}
							pasteConfigurations.get(element).setPasteElementContainementFeature((EStructuralFeature) value);
						}
						treeViewer.refresh();
					}
				}
			}

			@Override
			protected Object getValue(final Object element) {
				return getContainmentFeatureAsString(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				// Create a dialog for the containment feature selection
				return new ExtendedDialogCellEditor(tree, new LabelProvider()) {
					/**
					 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
					 *
					 * @param cellEditorWindow
					 * @return
					 */
					@Override
					protected Object openDialogBox(final Control cellEditorWindow) {
						final TreeSelectorDialog dialog = new TreeSelectorDialog(cellEditorWindow.getShell()) {
							@Override
							protected void initViewerAndProvider() {
								super.initViewerAndProvider();
								// Set a comparator to sort the tree viewer
								getViewer().setComparator(new ViewerComparator(new StringComparator()));// should always be string element
							}
						};
						dialog.setTitle(Messages.ConfigurePastePage_fourthColumnTitle);

						ITreeContentProvider treeContentProvider = null;
						if ((element instanceof IdTreeItemAxis && ((IdTreeItemAxis) element).getChildren().isEmpty() && 0 == Integer.parseInt(((IdTreeItemAxis) element).getElement()))
								|| (element instanceof ITreeItemAxis && ((ITreeItemAxis) element).getParent() instanceof IdTreeItemAxis && 0 == Integer.parseInt(((IdTreeItemAxis) ((ITreeItemAxis) element).getParent()).getElement()))) {
							treeContentProvider = new EncapsulatedContentProvider(new RowContainmentFeatureContentProvider(nattableManager.getTable(), nattableManager.getTable().getContext().eClass()));
						} else {
							treeContentProvider = new ProviderBasedBrowseStrategy(new EncapsulatedContentProvider(containmentFeatureContentProvider));
						}

						dialog.setContentProvider(treeContentProvider);
						dialog.setLabelProvider(containmentFeatureLabelProvider);

						if (dialog.open() == Window.OK) {
							Object[] result = dialog.getResult();
							if (result.length == 0) {
								return null;
							}

							if (result[0] instanceof EObject) {
								return result[0];
							}
						}
						return null;
					}
				};
			}

			@Override
			protected boolean canEdit(final Object element) {
				return canEditPasteConfiguration(element);
			}
		});

		// The content provider of the initial selection
		treeViewer.setContentProvider(new ITreeItemContentProvider() {

			@Override
			public boolean hasChildren(final Object parent) {
				if (parent instanceof ITreeItemAxis && CategoriesWizardUtils.isCategoryItem((ITreeItemAxis) parent)) {
					return false;
				}
				return super.hasChildren(parent);
			}
		});

		// Redefine the paint item listener to manage custom display in tree table
		tree.addListener(SWT.PaintItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem) event.item;

				if (event.index == 1) {
					// Manage the CHECKED and UNCHECKED images and center it in the column
					Image trailingImage = null;

					if (item.getData() instanceof ITreeItemAxis) {
						if (canEditPasteConfiguration(item.getData())) {
							final PasteEObjectConfiguration pasteConfiguration = getPasteConfigurationFromTreeItemAxis((ITreeItemAxis) item.getData());

							if (null != pasteConfiguration) {
								trailingImage = pasteConfiguration.isDetachedMode() ? CHECKED : UNCHECKED;
							}
						}
					}

					if (trailingImage != null) {
						// Calculate the center position
						int itemWidth = tree.getColumn(event.index).getWidth();
						int imageWidth = trailingImage.getBounds().width;
						int x = event.x + (itemWidth - imageWidth) / 2;
						int itemHeight = tree.getItemHeight();
						int imageHeight = trailingImage.getBounds().height;
						int y = event.y + (itemHeight - imageHeight) / 2;

						// Draw the image
						event.gc.drawImage(trailingImage, x, y);
					}
				} else if (event.index == 2 || event.index == 3) {
					// Manage the non-center text of the third and fourth columns
					String text = null;

					// Get the needed value to display
					if (2 == event.index) {
						Object result = getPastedElementId(item.getData());
						text = null != result ? (String) result : ""; //$NON-NLS-1$
					} else if (3 == event.index) {
						text = getContainmentFeatureAsString(item.getData());
					}

					final Image image = item.getImage(event.index);

					// Calculate the neede position of the text
					int x = event.x + 2;
					int yOffset = 0;
					if (event.index == 1) {
						Point size = event.gc.textExtent(text);
						yOffset = Math.max(0, (event.height - size.y) / 2);
					}
					int y = event.y + yOffset;

					if (null != image) {
						x += image.getBounds().width + 2;
					}

					// Draw the text
					event.gc.drawText(text, x, y, true);
				}
			}
		});

		setControl(treeViewerComposite);
	}

	/**
	 * This allows to check the element to display and modify. This must be a structural feature tree axis or the id tree axis when the depath is 0 and filled by user;
	 * 
	 * @param element
	 *            The element to check.
	 * @return <code>true</code> if the element can be displayed and modified, <code>false</code> otherwise
	 */
	protected boolean canEditPasteConfiguration(final Object element) {
		return (element instanceof IdTreeItemAxis && ((IdTreeItemAxis) element).getChildren().isEmpty() && 0 == Integer.parseInt(((IdTreeItemAxis) element).getElement()))
				|| (element instanceof EStructuralFeatureAxis && element instanceof ITreeItemAxis);
	}

	/**
	 * Get the detached mode value.
	 * 
	 * @param element
	 *            The element.
	 * @return The boolean value or <code>null</code>.
	 */
	protected Object getDetachedMode(final Object element) {
		Object result = null;
		if (canEditPasteConfiguration(element)) {
			final PasteEObjectConfiguration pasteConfiguration = getPasteConfigurationFromTreeItemAxis((ITreeItemAxis) element);

			if (null != pasteConfiguration) {
				result = pasteConfiguration.isDetachedMode();
			}
		}
		return result;
	}

	/**
	 * Get the pasted element id value.
	 * 
	 * @param element
	 *            The element.
	 * @return The string value or <code>null</code>.
	 */
	protected Object getPastedElementId(final Object element) {
		Object result = null;
		if (canEditPasteConfiguration(element)) {
			final PasteEObjectConfiguration pasteConfiguration = getPasteConfigurationFromTreeItemAxis((ITreeItemAxis) element);

			if (null != pasteConfiguration) {
				String pastedElementId = pasteConfiguration.getPastedElementId();
				if (null == pastedElementId || pastedElementId.isEmpty()) {
					pastedElementId = UNDEFINED;
				}
				result = pastedElementId;
			}
		}
		return result;
	}

	/**
	 * Get the containment feature value.
	 * 
	 * @param element
	 *            The element.
	 * @return The string value of structural feature or <code>null</code>.
	 */
	protected EStructuralFeature getContainmentFeature(final Object element) {
		EStructuralFeature result = null;
		if (canEditPasteConfiguration(element)) {
			final PasteEObjectConfiguration pasteConfiguration = getPasteConfigurationFromTreeItemAxis((ITreeItemAxis) element);

			if (null != pasteConfiguration) {
				final EStructuralFeature pasteElementContainementFeature = pasteConfiguration.getPasteElementContainementFeature();
				if (null != pasteElementContainementFeature) {
					result = pasteElementContainementFeature;
				}
			}
		}
		return result;
	}

	/**
	 * Get the containment feature value.
	 * 
	 * @param element
	 *            The element.
	 * @return The string value of structural feature or <code>null</code>.
	 */
	protected String getContainmentFeatureAsString(final Object element) {
		String result = ""; //$NON-NLS-1$
		if (canEditPasteConfiguration(element)) {
			final EStructuralFeature containmentFeature = getContainmentFeature(element);
			if (null != containmentFeature) {
				result = containmentFeature.getName();
			} else {
				result = UNDEFINED;
			}
		}
		return result;
	}

	/**
	 * This allows to create a tree viewer column in the tree viewer.
	 * 
	 * @param viewer
	 *            the tree viewer.
	 * @param title
	 *            The title of the column.
	 * @return The created tree viewer column.
	 */
	protected TreeViewerColumn createTreeViewerColumn(final TreeViewer viewer, final String title) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	/**
	 * This allows to get the paste configuration corresponding to a tree item axis.
	 * 
	 * @param treeItemAxis
	 *            The tree item axis
	 * @return The paste configuration corresponding to the tree item axis.
	 */
	protected PasteEObjectConfiguration getPasteConfigurationFromTreeItemAxis(final ITreeItemAxis treeItemAxis) {
		PasteEObjectConfiguration pasteConfiguration = null;
		// Check if the paste configuration of the tree item to modify was already modified and use it
		if (null != pasteConfigurations.get(treeItemAxis)) {
			pasteConfiguration = pasteConfigurations.get(treeItemAxis);
		} else {
			// The paste configuration was never used for this moment, get it from the tree item axis
			pasteConfiguration = getChildrenPasteConfiguration(treeItemAxis);
			if (null == pasteConfiguration) {
				// The tree item axis doesn't contain the paste configuration, get it from the nattable configuration
				int depth = -1;
				boolean hasNoTreeFillingFirstDepth = false;
				String category = null;

				// Check if the first depth contains a structural feature, else manage the paste configuration for the first level
				if (treeItemAxis instanceof EStructuralFeatureAxis && treeItemAxis.getParent() instanceof IdTreeItemAxis) {
					depth = Integer.parseInt(((IdTreeItemAxis) treeItemAxis.getParent()).getElement());
					category = ((EStructuralFeatureAxis) treeItemAxis).getElement().getName();
				} else if (treeItemAxis instanceof IdTreeItemAxis) {
					depth = Integer.parseInt(((IdTreeItemAxis) treeItemAxis).getElement());
					hasNoTreeFillingFirstDepth = true;
				}

				if (-1 != depth) {
					pasteConfiguration = getPasteConfigurationsFor(nattableManager.getTable(), depth, category, hasNoTreeFillingFirstDepth);
				}
			}
		}

		if (null == pasteConfiguration) {
			pasteConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createPasteEObjectConfiguration();
		}
		return pasteConfiguration;
	}

	/**
	 * Get the child tree item axis of a tree item axis corresponding to a paste configuration.
	 * 
	 * @param treeItemAxis
	 *            the tree item axis.
	 * @return The paste configuration or <code>null</code>.
	 */
	protected PasteEObjectConfiguration getChildrenPasteConfiguration(final ITreeItemAxis treeItemAxis) {
		PasteEObjectConfiguration result = null;

		if (!treeItemAxis.getChildren().isEmpty()) {
			for (ITreeItemAxis childItemAxis : treeItemAxis.getChildren()) {
				if (childItemAxis instanceof EObjectTreeItemAxis && childItemAxis.getElement() instanceof PasteEObjectConfiguration) {
					result = (PasteEObjectConfiguration) childItemAxis.getElement();
				}
			}
		}

		return result;
	}

	/**
	 * Get the paste configuration from the table.
	 * 
	 * @param table
	 *            The table.
	 * @param depth
	 *            The depath to search.
	 * @param categoryName
	 *            The category name is necessary (can be <code>nulll</code>).
	 * @param hasNoTreeFillingFirstDepth
	 *            <code>true</code> if no tree filling configuration for depath 0, <code>false</code> otherwise.
	 * @return The found paste configuration.
	 */
	protected PasteEObjectConfiguration getPasteConfigurationsFor(final Table table, final int depth, final String categoryName, final boolean hasNoTreeFillingFirstDepth) {
		PasteEObjectConfiguration result = null;
		// The first depth has no tree filling configuration
		if (hasNoTreeFillingFirstDepth) {
			AbstractHeaderAxisConfiguration conf = table.getLocalRowHeaderAxisConfiguration();
			if (conf == null) {
				conf = table.getTableConfiguration().getRowHeaderAxisConfiguration();
			}
			final List<TreeFillingConfiguration> filling = FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(table, depth);
			final List<IAxisConfiguration> referencedPasteConf = new ArrayList<IAxisConfiguration>();
			for (final TreeFillingConfiguration tmp : filling) {
				if (tmp.getPasteConfiguration() != null) {
					referencedPasteConf.add(tmp.getPasteConfiguration());
				}
			}
			final Iterator<IAxisConfiguration> axisConfIterator = conf.getOwnedAxisConfigurations().iterator();
			while (null == result && axisConfIterator.hasNext()) {
				final IAxisConfiguration axisConf = axisConfIterator.next();
				if (axisConf instanceof PasteEObjectConfiguration && !referencedPasteConf.contains(axisConf)) {
					result = (PasteEObjectConfiguration) axisConf;
				}
			}
		} else {
			// Iterate on existing tree filling to find the correct one
			final Iterator<TreeFillingConfiguration> treeFillingConfIterator = FillingConfigurationUtils.getAllTreeFillingConfiguration(table).iterator();
			while (null == result && treeFillingConfIterator.hasNext()) {
				final TreeFillingConfiguration curr = treeFillingConfIterator.next();
				if (curr.getDepth() == depth) {
					if (null == categoryName || categoryName.isEmpty()) {
						result = curr.getPasteConfiguration();
					} else {
						String featureName = curr.getAxisUsedAsAxisProvider().getAlias();
						if (featureName == null || featureName.isEmpty()) {
							final Object element = curr.getAxisUsedAsAxisProvider().getElement();
							if (element instanceof EStructuralFeature) {
								featureName = ((EStructuralFeature) element).getName();
							}
						}
						if (categoryName.equals(featureName)) {
							result = curr.getPasteConfiguration();
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Get the paste configurations modified by tree item axis.
	 * 
	 * @return The map of paste configurations modified by tree item axis.
	 */
	public Map<ITreeItemAxis, PasteEObjectConfiguration> getPasteConfigurations() {
		return pasteConfigurations;
	}
}
