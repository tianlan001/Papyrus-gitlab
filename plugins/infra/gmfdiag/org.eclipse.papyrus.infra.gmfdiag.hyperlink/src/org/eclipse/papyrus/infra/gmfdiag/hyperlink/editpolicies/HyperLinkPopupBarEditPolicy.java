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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusPopupBarEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.helper.AbstractHyperLinkHelper;
import org.eclipse.papyrus.infra.hyperlink.helper.HyperLinkHelperFactory;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.ui.HyperLinkManagerShell;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkException;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkHelpersRegistrationUtil;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistry;
import org.eclipse.papyrus.infra.ui.editorsfactory.PageIconsRegistry;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class HyperLinkPopupBarEditPolicy can be applied on edit part to display
 * shortcuts on sub-diagrams or to associate hyper-link of files, in addition to the
 * tools provided by the Modeling Assistant Service.
 */
public class HyperLinkPopupBarEditPolicy extends PapyrusPopupBarEditPolicy {

	/** The editor registry. */
	private IPageIconsRegistry editorRegistry;

	/** The hyper link manager shell. */
	private HyperLinkManagerShell hyperLinkManagerShell;

	protected ArrayList<HyperLinkObject> hyperLinkObjectList;

	protected HyperLinkHelperFactory hyperlinkHelperFactory;

	public HyperLinkPopupBarEditPolicy() {
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
	protected void appendPopupBarDescriptors() {
		// add all subdiagrams
		try {
			hyperLinkObjectList = (ArrayList<HyperLinkObject>) hyperlinkHelperFactory.getAllreferenced(((IGraphicalEditPart) getHost()).getNotationView());
			addHyperlinks();
		} catch (HyperLinkException e) {
			Activator.log.error(e);
		}

		// Add the New Hyperlink tool
		addPopupBarDescriptor(Activator.getDefault().getIcon(Activator.IMG_HYPERLINK), new AddHyperlinkTool(), "Add hyperlink");
	}

	//
	// Nested types
	//

	protected abstract class AbstractHyperlinkTool extends AbstractTool implements DragTracker {
		@Override
		protected boolean handleButtonDown(int button) {
			setCurrentCommand(getCommand());
			return true;
		}

		@Override
		protected boolean handleButtonUp(int button) {
			perform();
			return true;
		}

		private void perform() {
			Command command = getCurrentCommand();

			// Don't execute the command on the stack because it isn't that kind of command
			if ((command != null) && command.canExecute()) {
				command.execute();
			}
		}
	}

	protected class AddHyperlinkTool extends AbstractHyperlinkTool {

		@Override
		protected String getCommandName() {
			return "add hyperlink";
		}

		@Override
		protected Command getCommand() {
			return new Command("Add Hyperlink") {
				@Override
				public void execute() {
					Shell parentShell = EditorHelper.getActiveShell();
					hyperLinkManagerShell = new HyperLinkManagerShell(parentShell, getEditorRegistry(), ((IGraphicalEditPart) getHost()).getEditingDomain(), (EModelElement) ((IGraphicalEditPart) getHost()).getNotationView().getElement(),
							((IGraphicalEditPart) getHost()).getNotationView(), hyperlinkHelperFactory);
					hyperLinkManagerShell.setInput(hyperLinkObjectList);

					// Hide the popup bar now because the shell is modal
					hideDiagramAssistant();
					hyperLinkManagerShell.open();
				}

			};
		}
	}

	protected class NavigateHyperlinkTool extends AbstractHyperlinkTool {
		private final HyperLinkObject hyperlink;

		public NavigateHyperlinkTool(HyperLinkObject hyperlink) {
			super();

			this.hyperlink = hyperlink;
		}

		@Override
		protected String getCommandName() {
			return "open hyperlink";
		}

		@Override
		protected Command getCommand() {
			return new Command("Open Hyperlink") {
				@Override
				public void execute() {
					if (hyperlink.needsOpenCommand()) {
						try {
							// FIXME: Sometimes, it is possible to
							// automatically determine whether the editing
							// domain should be dirty or not
							// We should use standard GMF/GEF commands with
							// the DiagramCommandStack to have the same
							// behavior than NavigationEditPolicy
							TransactionalEditingDomain editingDomain = ServiceUtilsForEditPart.getInstance().getTransactionalEditingDomain(getHost());
							editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain, getLabel()) {

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
			};
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
			if (labelProvider instanceof CellLabelProvider) {
				tooltip = ((CellLabelProvider) labelProvider).getToolTipText(hyperlink);
			} else {
				tooltip = labelProvider.getText(hyperlink);
			}

			addPopupBarDescriptor(labelProvider.getImage(hyperlink), new NavigateHyperlinkTool(hyperlink), tooltip);
		}

		if (localLabelProvider) {
			labelProvider.dispose();
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
