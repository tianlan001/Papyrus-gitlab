/*****************************************************************************
 * Copyright (c) 2014, 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.ne - Bug 455060
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 579399
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.factory.IAxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.StringComparator;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.nattable.wizard.pages.ConfigurePastePage;
import org.eclipse.papyrus.infra.nattable.wizard.pages.SelectCategoriesWizardPage;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.infra.widgets.providers.FlattenableRestrictedFilteredContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * This wizard allows to configure the categories to listen in the table And the paste action for each categories
 */
public class ConfigureTableCategoriesWizard extends AbstractTableWizard implements IPageChangedListener {

	/**
	 * the page used to choose the categories
	 */
	private SelectCategoriesWizardPage categoriesPage;

	/**
	 * the page used to configure the paste
	 */
	private ConfigurePastePage pastePage;

	/**
	 * the nattable model manager
	 */
	private INattableModelManager manager;

	/**
	 * the nattable widget
	 */
	private final NatTable natTable;

	/**
	 * the initial selection
	 */
	private List<Object> initialSelection;

	/**
	 * Constructor.
	 *
	 * @param manager
	 *            The nattable model manager.
	 */
	public ConfigureTableCategoriesWizard(final INattableModelManager manager) {
		this.manager = manager;
		this.natTable = (NatTable) ((IAdaptable) manager).getAdapter(NatTable.class);
		setWindowTitle(Messages.ConfigureTableCategoriesWizard_ConfigureCategoriesAndPaste);
		this.initialSelection = createInitialSelection();
	}

	/**
	 * Create the selector content provider.
	 * 
	 * @param selector
	 *            the reference selector
	 * @return
	 * 		the content provider to use for the selector
	 */
	protected IStaticContentProvider createSelectorContentProvider(final ReferenceSelector selector) {
		final IAxisManager editedAxisManager = this.manager.getColumnAxisManager();
		IStaticContentProvider provider = editedAxisManager.createPossibleAxisContentProvider(true);
		Assert.isNotNull(provider);

		return new FlattenableRestrictedFilteredContentProvider((IRestrictedContentProvider) provider, selector) {

			/**
			 * @see org.eclipse.papyrus.infra.widgets.providers.FlattenableRestrictedFilteredContentProvider#isValidValue(java.lang.Object)
			 *
			 * @param element
			 * @return
			 */
			@Override
			public boolean isValidValue(Object element) {
				// EMF dependency, must not be done here, it should be better with a new content provider service
				return element instanceof EReference && element != EcorePackage.eINSTANCE.getEModelElement_EAnnotations();
			}
		};

	}

	/**
	 * Create the reference selector.
	 * 
	 * @return
	 * 		the created and initialized reference selector
	 */
	protected ReferenceSelector createReferenceSelector() {
		ReferenceSelector selector = new ReferenceSelector(false) {


			/**
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector#createControls(org.eclipse.swt.widgets.Composite)
			 *
			 * @param parent
			 */
			@Override
			public void createControls(Composite parent) {
				super.createControls(parent);
				this.treeViewer.setComparator(new ViewerComparator(new StringComparator()));// should always be string element
			}
		};

		final LabelProviderService serv = this.natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		final ILabelProvider labelProvider = serv.getLabelProvider();
		selector.setLabelProvider(labelProvider);
		selector.setContentProvider(createSelectorContentProvider(selector));
		return selector;

	}

	/**
	 * This allows to create the first page corresponding to the categories selection.
	 * 
	 * @return
	 * 		the page to use to select the categories to listen in the table
	 */
	protected SelectCategoriesWizardPage createSelectCategoriesPage() {
		this.categoriesPage = new SelectCategoriesWizardPage(createReferenceSelector());
		final LabelProviderService serv = this.natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		this.categoriesPage.setLabelProvider(new ITreeItemWrappedObjectLabelProvider(serv.getLabelProvider(), this.natTable));
		this.categoriesPage.setInitialElementSelections(this.initialSelection);
		return this.categoriesPage;
	}

	/**
	 * This allows to create the second page corresponding to the paste configurations.
	 * 
	 * @return
	 * 		the paste page used to configure the paste
	 */
	protected ConfigurePastePage createConfigurePastePage() {
		final LabelProviderService serv = this.natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		this.pastePage = new ConfigurePastePage("Paste Configuration", manager, new ITreeItemWrappedObjectLabelProvider(serv.getLabelProvider(), this.natTable), serv.getLabelProvider(), createSelectorContentProvider(null)); //$NON-NLS-1$
		return this.pastePage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		addPage(createSelectCategoriesPage());
		addPage(createConfigurePastePage());

		// Add a listener to manage the page changing
		final WizardDialog dialog = (WizardDialog) getContainer();
		dialog.addPageChangedListener(this);
	}

	/**
	 * The label provider to use for the selected elements viewer
	 */
	private static class ITreeItemWrappedObjectLabelProvider implements ILabelProvider {

		/**
		 * default label provider
		 */
		private ILabelProvider wrappedLabelprovider;

		/**
		 * The nattable to manage.
		 */
		private NatTable natTable;

		/**
		 * The wrapper label provider context.
		 */
		private LabelProviderContextElementWrapper wrapper;

		/**
		 * Constructor.
		 *
		 * @param wrappedProvider
		 *            The wrapped provider.
		 * @param natTable
		 *            The nattable to manage.
		 */
		public ITreeItemWrappedObjectLabelProvider(final ILabelProvider wrappedProvider, final NatTable natTable) {
			this.wrappedLabelprovider = wrappedProvider;
			this.natTable = natTable;
			wrapper = new LabelProviderContextElementWrapper();
			wrapper.setConfigRegistry(natTable.getConfigRegistry());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 */
		@Override
		public void addListener(final ILabelProviderListener arg0) {
			// nothing to do
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 */
		@Override
		public void dispose() {
			// nothing to do
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
		 */
		@Override
		public boolean isLabelProperty(final Object arg0, final String arg1) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 */
		@Override
		public void removeListener(final ILabelProviderListener arg0) {
			// nothing to do
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(final Object arg0) {
			Object current = arg0;
			if (arg0 instanceof ITreeItemAxis) {
				current = ((ITreeItemAxis) arg0).getElement();
			}
			return wrappedLabelprovider.getImage(current);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(final Object arg0) {
			Assert.isTrue(arg0 instanceof ITreeItemAxis);
			ITreeItemAxis axis = (ITreeItemAxis) arg0;
			Object element = axis.getElement();
			if (element instanceof String) {
				if (TypeUtils.isNaturalValue((String) element)) {
					Integer value = Integer.parseInt((String) element);
					int depth = value;
					if (depth == 0 && axis.getChildren().isEmpty()) {
						return NLS.bind(Messages.ConfigureTableCategoriesWizard_DepthFilledByUser, depth);
					} else {
						return NLS.bind(Messages.ConfigureTableCategoriesWizard_Depth, depth);
					}
				}
			}
			wrapper.setObject(axis);
			final LabelProviderService serv = this.natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
			ILabelProvider p = serv.getLabelProvider(wrapper);
			p = serv.getLabelProvider(Constants.HEADER_LABEL_PROVIDER_CONTEXT);
			return p.getText(wrapper);
			// return wrappedLabelprovider.getText(wrapper);
		}
	}

	/**
	 * This allows to create the initial selection corresponding to the table to edit.
	 * 
	 * @return
	 * 		the initial selection to use in the wizard page
	 */
	protected List<Object> createInitialSelection() {
		Table table = this.manager.getTable();
		EObject tableContext = table.getContext();

		// the number of elements in initial selection is the number of AxisManagerRepresentation for rows
		List<Object> initialSelection = new ArrayList<Object>();

		// 0. we iterate on the row axis manager representation
		for (AxisManagerRepresentation rep : getRowAxisManagerRepresentations(table)) {// this iteration has not been tested
			Map<Object, ITreeItemAxis> map = new HashMap<Object, ITreeItemAxis>();
			ITreeItemAxis rootAxis = IAxisFactory.createITreeItemAxis(null, tableContext, rep, null);
			map.put(tableContext, rootAxis);
			initialSelection.add(rootAxis);

			// 1. we cross the existing tree filling configuration
			for (TreeFillingConfiguration fillingConf : FillingConfigurationUtils.getTreeFillingConfiguration(table, rep)) {
				Integer depth = Integer.valueOf(fillingConf.getDepth());
				ITreeItemAxis axis = map.get(depth);
				// 2. we create an idAxis if required for the depth of the current filling configuration
				if (axis == null) {
					axis = IAxisFactory.createITreeItemAxis(rootAxis, depth, null, null);
					map.put(depth, axis);
				}

				// 3. we create an axis to represent the element represented by the TreeFillingConfiguration
				Object representedElement = fillingConf.getAxisUsedAsAxisProvider().getElement();
				for (ITreeItemAxis curr : axis.getChildren()) {
					if (curr.getElement() == representedElement) {
						continue;
					}
				}
				ITreeItemAxis categoryAxis = IAxisFactory.createITreeItemAxis(axis, representedElement, null, fillingConf.getAxisUsedAsAxisProvider().getAlias());
				if (fillingConf.getPasteConfiguration() != null) {
					// create an axis to configure the paste in the table
					IAxisFactory.createITreeItemAxis(categoryAxis, fillingConf.getPasteConfiguration(), null, null);
				}
			}
			// the 0 depth must always be here
			if (map.get(Integer.valueOf(0)) == null) {
				ITreeItemAxis axis = IAxisFactory.createITreeItemAxis(rootAxis, 0, null, null);
				map.put(Integer.valueOf(0), axis);
				rootAxis.getChildren().move(0, axis);
			}
		}

		return initialSelection;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		Table table = manager.getTable();
		final List<ITreeItemAxis> configureCategoriesResult = new ArrayList<ITreeItemAxis>();
		final Map<ITreeItemAxis, PasteEObjectConfiguration> pasteConfigurations = pastePage.getPasteConfigurations();
		for (Object curr : initialSelection) {
			configureCategoriesResult.add((ITreeItemAxis) curr);
		}
		final Command cmd = getConfigureCategoriesCommand(configureCategoriesResult, pasteConfigurations);
		final EditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(table);
		domain.getCommandStack().execute(cmd);
		return true;
	}

	/**
	 * Get the label provider configuration for the tree filling configuration.
	 * 
	 * @param table
	 *            The table.
	 * @return The label provider configuration.
	 */
	private ILabelProviderConfiguration getLabelConfigurationForTreeFillingConfiguration(final Table table) {
		TableHeaderAxisConfiguration conf = (TableHeaderAxisConfiguration) HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTableConfiguration(table);
		for (IAxisConfiguration tmp : conf.getOwnedAxisConfigurations()) {
			if (tmp instanceof TreeFillingConfiguration) {
				if (((TreeFillingConfiguration) tmp).getLabelProvider() != null) {
					return ((TreeFillingConfiguration) tmp).getLabelProvider();
				}
			}
		}
		return null;
	}

	/**
	 * Get the label provider context for the tree filling configuration.
	 * 
	 * @param table
	 *            The table.
	 * @return The label provider context as string.
	 */
	private String getLabelProviderContextForTreeFillingConfiguration(final Table table) {
		return Constants.HEADER_LABEL_PROVIDER_TREE_FILLING_FEATURE_CONFIGURATION_CONTEXT;
	}

	/**
	 * This allows to get the command that configure the categories.
	 * 
	 * @param userSelection
	 *            The list of tree item axis choosen by the user.
	 * @param pasteConfigurations
	 *            The paste configurations by tree item axis modified by user.
	 * @return The command.
	 */
	protected Command getConfigureCategoriesCommand(final List<ITreeItemAxis> userSelection, final Map<ITreeItemAxis, PasteEObjectConfiguration> pasteConfigurations) {
		return new RecordingCommand(TableEditingDomainUtils.getTableEditingDomain(manager.getTable())) {

			@Override
			protected void doExecute() {
				Table table = manager.getTable();

				for (Object tmp : userSelection) {
					ITreeItemAxis root = (ITreeItemAxis) tmp;
					AxisManagerRepresentation representation = root.getManager();
					List<TreeFillingConfiguration> createdFillingConfiguration = new ArrayList<TreeFillingConfiguration>();
					List<PasteEObjectConfiguration> createdPasteEObjectConfiguration = new ArrayList<PasteEObjectConfiguration>();
					
					for (ITreeItemAxis depthItem : root.getChildren()) {
						Assert.isTrue(CategoriesWizardUtils.isDepthItem(depthItem));
						int wantedDepth = Integer.valueOf((String) depthItem.getElement());
						if (depthItem.getChildren().isEmpty()) {
							// we do nothing if there is no child, in standard usecase, it is only possible when wantedDepth==0;
							
							if (null != pasteConfigurations.get(depthItem)) {
								createdPasteEObjectConfiguration.add(pasteConfigurations.get(depthItem));
							}
							continue;
						}


						for (ITreeItemAxis categoryItem : depthItem.getChildren()) {

							// 1. try to find existing conf
							TreeFillingConfiguration newConf = findExistingTreeFillingConfiguration(table, representation, wantedDepth, categoryItem.getElement());
							if (null == newConf || EMFHelper.isReadOnly(newConf) || null != pasteConfigurations.get(categoryItem)) {
								PasteEObjectConfiguration copiedEObjectConfiguration = null;
								if (null != pasteConfigurations.get(categoryItem)) {
									copiedEObjectConfiguration = pasteConfigurations.get(categoryItem);
								} else if (null != newConf) {
									PasteEObjectConfiguration existingPasteConfiguration = newConf.getPasteConfiguration();
									copiedEObjectConfiguration = EcoreUtil.copy(existingPasteConfiguration);
								}
								if (null != copiedEObjectConfiguration) {
									createdPasteEObjectConfiguration.add(copiedEObjectConfiguration);
								}

								// we create new TreeFillingConfiguration
								newConf = NattableaxisconfigurationFactory.eINSTANCE.createTreeFillingConfiguration();
								newConf.setDepth(wantedDepth);
								IAxis axis = IAxisFactory.createAxisForFeature(categoryItem.getElement(), representation, categoryItem.getAlias());
								newConf.setAxisUsedAsAxisProvider(axis);
								newConf.setLabelProvider(getLabelConfigurationForTreeFillingConfiguration(table));
								newConf.setLabelProviderContext(getLabelProviderContextForTreeFillingConfiguration(table));
								// Manage the paste configuration
								newConf.setPasteConfiguration(copiedEObjectConfiguration);
							} else {

								if (null != newConf) {
									// update the alias if required
									String oldAlias = newConf.getAxisUsedAsAxisProvider().getAlias();
									String newAlias = categoryItem.getAlias();
									if ((oldAlias != null && !oldAlias.equals(newAlias)) || (newAlias != null && !newAlias.equals(oldAlias))) {
										newConf.getAxisUsedAsAxisProvider().setAlias(newAlias);
									}

									if (null != newConf.getPasteConfiguration()) {
										createdPasteEObjectConfiguration.add(newConf.getPasteConfiguration());
									}
								}
							}


							createdFillingConfiguration.add(newConf);
						}
					}

					LocalTableHeaderAxisConfiguration local = (LocalTableHeaderAxisConfiguration) HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTable(table);
					if (local == null) {
						local = HeaderAxisConfigurationManagementUtils.transformToLocalHeaderConfiguration((TableHeaderAxisConfiguration) HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTableConfiguration(table));
						table.setLocalRowHeaderAxisConfiguration(local);
					}
					List<AxisManagerConfiguration> axisManagerConfigurations = local.getAxisManagerConfigurations();
					AxisManagerConfiguration wantedAxisManagerConfiguration = null;
					for (AxisManagerConfiguration curr : axisManagerConfigurations) {
						if (curr.getAxisManager() == representation) {
							wantedAxisManagerConfiguration = curr;
						}
					}
					if (wantedAxisManagerConfiguration == null) {
						wantedAxisManagerConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createAxisManagerConfiguration();
						wantedAxisManagerConfiguration.setAxisManager(representation);
						// TODO
						// /wantedAxisManagerConfiguration.setLocalHeaderLabelConfiguration(value);
						local.getAxisManagerConfigurations().add(wantedAxisManagerConfiguration);
					}
					local.getOwnedAxisConfigurations().clear();
					local.getOwnedAxisConfigurations().addAll(createdFillingConfiguration);
					local.getOwnedAxisConfigurations().addAll(createdPasteEObjectConfiguration);
					wantedAxisManagerConfiguration.getLocalSpecificConfigurations().clear();
					wantedAxisManagerConfiguration.getLocalSpecificConfigurations().addAll(createdFillingConfiguration);
				}
			}
		};
	}

	/**
	 * This allows to find the exiting tree filling configuration corresponding to the properties in parameters.
	 * 
	 * @param table
	 *            The table.
	 * @param representedAxisManager
	 *            The axis manager.
	 * @param depth
	 *            The wanted depth.
	 * @param representedObject
	 *            The represented object to search.
	 * @return The corresponding tree filling configuration.
	 */
	// TODO : move me
	public static final TreeFillingConfiguration findExistingTreeFillingConfiguration(final Table table, final AxisManagerRepresentation representedAxisManager, final int depth, final Object representedObject) {
		List<TreeFillingConfiguration> existingConf = FillingConfigurationUtils.getTreeFillingConfigurationForDepth(table, representedAxisManager, depth);
		for (TreeFillingConfiguration treeFillingConfiguration : existingConf) {
			if (treeFillingConfiguration.getAxisUsedAsAxisProvider().getElement().equals(representedObject)) {
				return treeFillingConfiguration;
			}
		}
		return null;
	}

	/**
	 * Get the axis manager representation for rows.
	 * 
	 * @param table
	 *            the table
	 * @return
	 * 		the axis manager representation for rows
	 */
	private static final List<AxisManagerRepresentation> getRowAxisManagerRepresentations(Table table) {
		AbstractHeaderAxisConfiguration tmp = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTableConfiguration(table);
		Assert.isTrue(tmp instanceof TableHeaderAxisConfiguration);
		TableHeaderAxisConfiguration conf = (TableHeaderAxisConfiguration) tmp;
		return conf.getAxisManagers();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IPageChangedListener#pageChanged(org.eclipse.jface.dialogs.PageChangedEvent)
	 */
	@Override
	public void pageChanged(final PageChangedEvent event) {
		if (event.getSelectedPage().equals(pastePage)) {
			// If the changed page is the paste configurations page, set it the selection potentially modified in the previous page
			this.pastePage.setInitialElementSelections(this.initialSelection);
		}
	}

}
