/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 451230, 485220, 488965
 *  Shuai Li - Modifications for navigation menu integration
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.AbstractPopupBarTool;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.Activator;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.messages.Messages;
import org.eclipse.papyrus.infra.gmfdiag.navigation.editpolicy.NavigationEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.button.HyperlinkButton;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;
import org.eclipse.papyrus.infra.hyperlink.helper.AbstractHyperLinkHelper;
import org.eclipse.papyrus.infra.hyperlink.helper.HyperLinkHelperFactory;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkDocument;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkSpecificObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkWeb;
import org.eclipse.papyrus.infra.hyperlink.ui.HyperLinkManagerShell;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkException;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkHelpersRegistrationUtil;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistry;
import org.eclipse.papyrus.infra.ui.editorsfactory.PageIconsRegistry;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * The Class HyperlinkNavigationMenuEditPolicy can be applied on edit part to display
 * shortcuts on sub-diagrams or to associate hyper-link of files, in addition to the
 * tools provided by the Navigation menu.
 * 
 * @since 2.0
 */
public class HyperlinkNavigationMenuEditPolicy extends NavigationEditPolicy {

	/** Icon for new Table. */
	private static final String ICONS_NEW_TABLE = "icons/NewTable.gif"; //$NON-NLS-1$

	/** Icon for new Diagram. */
	private static final String ICONS_NEW_DIAGRAM = "icons/NewDiagram.gif"; //$NON-NLS-1$

	/**
	 * Action to open the popup of creation of new View.
	 */
	public class AddViewPopupAction extends Action {

		/** The list of available view prototype. */
		private List<ViewPrototype> viewPrototypes;

		/** the dialog title. */
		private String title;

		/** The dialog message. */
		private String message;

		/**
		 * The label Provider for ViewPrototype.
		 */
		LabelProvider labelProvider = new LabelProvider() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(final Object element) {
				String text = null;
				if (element instanceof ViewPrototype) {
					ViewPrototype prototype = (ViewPrototype) element;
					text = prototype.getLabel();
				} else {
					text = super.getText(element);
				}
				return text;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
			 */
			@Override
			public Image getImage(final Object element) {
				Image image = null;
				if (element instanceof ViewPrototype) {
					image = ((ViewPrototype) element).getIconDescriptor().createImage();
				} else {
					image = super.getImage(element);
				}
				return image;
			}
		};

		/**
		 * Constructor.
		 *
		 * @param diagramPrototypes
		 */
		public AddViewPopupAction(List<ViewPrototype> diagramPrototypes, String Title, String message) {
			this.viewPrototypes = diagramPrototypes;
			this.title = Title;
			this.message = message;
		}

		/**
		 * Open the dialog.
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {

			ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(EditorHelper.getActiveShell(), labelProvider, new EncapsulatedContentProvider(ArrayContentProvider.getInstance()));
			dialog.setAllowMultiple(false);
			dialog.setTitle(title);
			dialog.setMessage(message);

			dialog.setInput(viewPrototypes);
			dialog.setSize(SWT.DEFAULT, 10);
			dialog.open();

			if (Window.OK == dialog.getReturnCode()) {
				// Gets the selected view prototype
				Object firstResult = dialog.getFirstResult();
				if (firstResult instanceof ViewPrototype) {
					ViewPrototype prototype = (ViewPrototype) firstResult;
					// Create the View(Diagram or Table) and add hyperlink as default.
					new CreateViewAndHyperlinkAction(prototype).run();
				}
			}
		}
	}

	/** The editor registry. */
	private IPageIconsRegistry editorRegistry;

	/** The hyper link manager shell. */
	private HyperLinkManagerShell hyperLinkManagerShell;

	protected LinkedList<HyperLinkObject> hyperLinkObjectList;

	protected HyperLinkHelperFactory hyperlinkHelperFactory;

	public HyperlinkNavigationMenuEditPolicy() {
		super();

		ArrayList<AbstractHyperLinkHelper> hyperLinkHelpers = new ArrayList<AbstractHyperLinkHelper>();
		// TODO
		// hyperLinkHelpers.add(new DiagramHyperLinkHelper());
		// hyperLinkHelpers.add(new DocumentHyperLinkHelper());
		// hyperLinkHelpers.add(new WebHyperLinkHelper());
		hyperLinkHelpers.addAll(HyperLinkHelpersRegistrationUtil.INSTANCE.getAllRegisteredHyperLinkHelper());
		hyperlinkHelperFactory = new HyperLinkHelperFactory(hyperLinkHelpers);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void appendNavigationMenuItem() {
		clearAppendObjects();

		// add all subdiagrams
		try {
			hyperLinkObjectList = new LinkedList<>();
			ArrayList<HyperLinkObject> unfilteredHyperlinkObjects = (ArrayList<HyperLinkObject>) hyperlinkHelperFactory.getAllreferenced(((IGraphicalEditPart) getHost()).getNotationView());

			for (AbstractHyperLinkHelper hyperlinkHelper : hyperlinkHelperFactory.getHyperLinkHelpers()) {
				hyperLinkObjectList.addAll(hyperlinkHelper.getFilteredObject(unfilteredHyperlinkObjects));
			}

			if (hyperLinkObjectList.isEmpty()) {
				hyperLinkObjectList.addAll(unfilteredHyperlinkObjects);
			} else if (hyperLinkObjectList.size() != unfilteredHyperlinkObjects.size()) {
				hyperLinkObjectList.clear();
				hyperLinkObjectList.addAll(unfilteredHyperlinkObjects);
			}

			addHyperlinks();
		} catch (HyperLinkException e) {
			Activator.log.error(e);
		}

		// Add View(Diagram/Table) with associated hyperlink creation
		addHyperlinkedViewCreations();

		// Add the New Hyperlink tool
		addNavigationMenuHyperlinkDescriptor(Activator.getDefault().getIcon(Activator.IMG_HYPERLINK), new AddHyperlinkAction(), Messages.HyperlinkNavigationMenuEditPolicy_EditHyperLinkTooltip, Messages.HyperlinkNavigationMenuEditPolicy_EditHyperLinkTooltipLabel);
	}

	/**
	 * Add View Navigation hyperlink.
	 */
	private void addHyperlinkedViewCreations() {
		EObject selection = null;
		Object model = ((IGraphicalEditPart) getHost()).getModel();
		if (model instanceof View) {
			View view = (View) model;
			selection = EMFHelper.getEObject(view.getElement());

		}
		if (null != selection) {

			// Gets View Prototype for Table and for Diagram
			List<ViewPrototype> diagramPrototypes = new ArrayList<>();
			List<ViewPrototype> tablePrototypes = new ArrayList<>();

			for (final ViewPrototype proto : PolicyChecker.getFor(selection).getPrototypesFor(selection)) {
				if (proto.getRepresentationKind() instanceof PapyrusDiagram) {
					diagramPrototypes.add(proto);
				} else if (proto.getRepresentationKind() instanceof PapyrusTable) {
					tablePrototypes.add(proto);
				}
			}

			// Diagram case
			if (0 < diagramPrototypes.size()) {
				// Sort list
				Collections.sort(diagramPrototypes, new ViewPrototype.Comp());
				// Add popup action
				Image addDiagramIcon = Activator.imageDescriptorFromPlugin(org.eclipse.papyrus.infra.viewpoints.policy.Activator.PLUGIN_ID, ICONS_NEW_DIAGRAM).createImage();
				AddViewPopupAction addDiagramAction = new AddViewPopupAction(diagramPrototypes, Messages.HyperlinkNavigationMenuEditPolicy_CreateDiagramDialogLabel, Messages.HyperlinkNavigationMenuEditPolicy_CreateDiagramDialogMessage);
				addNavigationMenuHyperlinkDescriptor(addDiagramIcon, addDiagramAction, Messages.HyperlinkNavigationMenuEditPolicy_CreateDiagramhyperlinkTooltip, Messages.HyperlinkNavigationMenuEditPolicy_CreateDiagramhyperlinkLabel);
			}
			// Table case
			if (0 < tablePrototypes.size()) {
				// sort list
				Collections.sort(tablePrototypes, new ViewPrototype.Comp());
				// The image
				Image addTableIcon = Activator.imageDescriptorFromPlugin(org.eclipse.papyrus.infra.viewpoints.policy.Activator.PLUGIN_ID, ICONS_NEW_TABLE).createImage();
				// The action
				AddViewPopupAction addTableAction = new AddViewPopupAction(tablePrototypes, Messages.HyperlinkNavigationMenuEditPolicy_CreateTableDialogTitle, Messages.HyperlinkNavigationMenuEditPolicy_CreateTableDialogMessage);
				// Add Popup action
				addNavigationMenuHyperlinkDescriptor(addTableIcon, addTableAction, Messages.HyperlinkNavigationMenuEditPolicy_CreateTableHyperLinkTooltip, Messages.HyperlinkNavigationMenuEditPolicy_CreateTableHyperLinkLabel);
			}
		}
	}

	/**
	 * Create View and the hyperlink set as default from a ViewPrototype.
	 */
	protected class CreateViewAndHyperlinkAction extends Action {
		private ViewPrototype prototype;

		/**
		 * Constructor.
		 */
		public CreateViewAndHyperlinkAction(final ViewPrototype prototype) {
			this.prototype = prototype;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			try {
				Object model = ((IGraphicalEditPart) getHost()).getModel();
				if (model instanceof View) {
					View view = (View) model;

					EObject eObject = EMFHelper.getEObject(view.getElement());

					if (null != eObject) {

						// Get the usage of object before action
						Set<EObject> usagesBefore = new HashSet<EObject>();
						Collection<Setting> usages = EMFHelper.getUsages(eObject);
						if (null != usages) {
							for (Setting setting : usages) {
								usagesBefore.add(setting.getEObject());
							}
						}
						prototype.instantiateOn(eObject);

						// Get the usage of object after action
						Set<EObject> usagesAfter = new HashSet<EObject>();
						usages = EMFHelper.getUsages(eObject);
						if (usages != null) {
							for (Setting setting : usages) {
								usagesAfter.add(setting.getEObject());
							}
						}

						// remove the before usage to get the new created Diagram, Table, ...
						// In Diagram case and Table, it should be only one element.
						usagesAfter.removeAll(usagesBefore);

						// Gets the container
						EObject container = null;
						for (EObject createdObject : usagesAfter) {
							if (createdObject instanceof PapyrusDiagramStyle) {
								// Add it to hyperLink
								PapyrusDiagramStyle viewStyle = (PapyrusDiagramStyle) createdObject;
								container = viewStyle.eContainer();
							} else if (null == container) {
								container = createdObject;
							}
						}

						// Set hyperLink
						if (null != container) {
							HyperLinkSpecificObject hyperLink = new HyperLinkSpecificObject(container);
							// Set it to default to be open by double click
							hyperLink.setIsDefault(true);

							List<HyperLinkObject> hyperLinkList = new ArrayList<>();
							hyperLinkList.add(hyperLink);

							TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(eObject);
							Command addHyperLinkCommand = hyperlinkHelperFactory.getAddHyperLinkCommand(domain, (EModelElement) model, hyperLinkList);

							if (addHyperLinkCommand.canExecute()) {
								domain.getCommandStack().execute(addHyperLinkCommand);
							}
						}
					}

				}
			} catch (HyperLinkException ex) {
				Activator.log.error(ex);
			}
		}
	}


	private void addHyperlinks() {
		ILabelProvider labelProvider = null;
		boolean localLabelProvider = false;
		try {
			EObject contextElement = EMFHelper.getEObject(getHost());
			labelProvider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, contextElement).getLabelProvider();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}

		if (labelProvider == null) {
			labelProvider = new LabelProvider();
			localLabelProvider = true;
		}

		for (HyperLinkObject hyperlink : hyperLinkObjectList) {
			String tooltip;
			String text;
			if (labelProvider instanceof CellLabelProvider) {
				tooltip = ((CellLabelProvider) labelProvider).getToolTipText(hyperlink);
				text = tooltip;
			} else {
				if (hyperlink instanceof HyperLinkDocument || hyperlink instanceof HyperLinkWeb) {
					text = hyperlink.getTooltipText();
					tooltip = labelProvider.getText(hyperlink);
				} else {
					text = labelProvider.getText(hyperlink);
					tooltip = hyperlink.getTooltipText();
				}

				if (tooltip == null) {
					tooltip = text;
				}

				if (text == null) {
					text = tooltip;
				}
			}

			addNavigationMenuHyperlinkDescriptor(labelProvider.getImage(hyperlink), new NavigateHyperlinkAction(hyperlink), tooltip, text);
		}

		if (localLabelProvider) {
			labelProvider.dispose();
		}
	}

	protected void addNavigationMenuHyperlinkDescriptor(Image theImage, IAction theAction, String theTip, String theText) {
		if (!(theAction instanceof AbstractPopupBarTool) || ((AbstractPopupBarTool) theAction).isCommandEnabled()) {
			// We only add pop-up bar tools whose commands are actually executable in this context
			HyperlinkButton desc = new HyperlinkButton(theText, theTip, theImage, theAction);
			this.navigationMenu.getAppendObjects().add(desc);
		}
	}

	//
	// Nested types
	//

	protected abstract class AbstractHyperlinkAction extends Action {

	}

	public class AddHyperlinkAction extends AbstractHyperlinkAction {
		@Override
		public void run() {
			Shell parentShell = EditorHelper.getActiveShell();
			hyperLinkManagerShell = new HyperLinkManagerShell(parentShell, getEditorRegistry(), ((IGraphicalEditPart) getHost()).getEditingDomain(), (EModelElement) ((IGraphicalEditPart) getHost()).getNotationView().getElement(),
					((IGraphicalEditPart) getHost()).getNotationView(), hyperlinkHelperFactory);
			hyperLinkManagerShell.setInput(hyperLinkObjectList);

			// Do this asynchronously because the dialog is modal and we need the menu to disappear
			parentShell.getDisplay().asyncExec(new Runnable() {

				public void run() {
					hyperLinkManagerShell.open();
				}
			});
		}
	}

	protected class NavigateHyperlinkAction extends AbstractHyperlinkAction {
		private final HyperLinkObject hyperlink;

		public NavigateHyperlinkAction(HyperLinkObject hyperlink) {
			super();

			this.hyperlink = hyperlink;
		}

		@Override
		public void run() {
			if (hyperlink.needsOpenCommand()) {
				try {
					TransactionalEditingDomain editingDomain = ServiceUtilsForEditPart.getInstance().getTransactionalEditingDomain(getHost());
					editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain, hyperlink.getTooltipText()) {

						@Override
						protected void doExecute() {
							hyperlink.openLink();
						}
					});
				} catch (ServiceException ex) {

				}
			} else {
				hyperlink.openLink();
			}
		}
	}

	/**
	 * Return the EditorRegistry for nested editor descriptors. Subclass should
	 * implements this method in order to return the registry associated to the
	 * extension point namespace.
	 *
	 * @return the EditorRegistry for nested editor descriptors
	 *
	 * @generated NOT
	 */
	protected IPageIconsRegistry createEditorRegistry() {
		try {
			return ServiceUtilsForEditPart.getInstance().getService(IPageIconsRegistry.class, getHost());
		} catch (ServiceException e) {
			// Return an empty registry always providing null;
			return new PageIconsRegistry();
		}
	}

	/**
	 * Gets the editor registry.
	 *
	 * @return the singleton eINSTANCE of editor registry
	 *
	 * @generated NOT Get the EditorRegistry used to create editor instances.
	 *            This default implementation return the singleton eINSTANCE.
	 *            This method can be subclassed to return another registry.
	 */
	protected IPageIconsRegistry getEditorRegistry() {
		if (editorRegistry == null) {
			editorRegistry = createEditorRegistry();
		}
		return editorRegistry;
	}
}
