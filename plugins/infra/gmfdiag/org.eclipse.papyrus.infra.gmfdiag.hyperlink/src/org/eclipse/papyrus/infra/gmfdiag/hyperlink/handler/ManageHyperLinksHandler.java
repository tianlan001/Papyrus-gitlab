/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Francois Le Fevre - CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.hyperlink.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.NavigationEditPolicy;
import org.eclipse.papyrus.infra.hyperlink.helper.AbstractHyperLinkHelper;
import org.eclipse.papyrus.infra.hyperlink.helper.HyperLinkHelperFactory;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.ui.HyperLinkManagerShell;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkException;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkHelpersRegistrationUtil;
import org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistry;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Dedicated to open a shell for managing the hyperlinks
 *
 */
public class ManageHyperLinksHandler extends AbstractHandler {
	
	/**
	 * {@inheritDoc}
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		//Retrieve NavigationEditPolicy
		NavigationEditPolicy navigationEditPolicy = (NavigationEditPolicy) getEditPolicy(org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.NavigationEditPolicy.NAVIGATION_POLICY);
		if (navigationEditPolicy == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		//Retrieve other variables needed to create HyperLinkManagerShell
		final IGraphicalEditPart gep ; 
		EditPart target = navigationEditPolicy.getHost();
		while (!(target instanceof IPrimaryEditPart) && target != null) {
			target = target.getParent();
		}
		
		if (!(target instanceof IGraphicalEditPart)) {
			return UnexecutableCommand.INSTANCE;
		}

		gep = (IGraphicalEditPart)target;
		

		Shell parentShell = EditorHelper.getActiveShell();
		
		try {
		IPageIconsRegistry iconsregistry = ServiceUtilsForEditPart.getInstance().getService(IPageIconsRegistry.class, navigationEditPolicy.getHost());
		
		
		List<AbstractHyperLinkHelper> hyperLinkHelpers = new ArrayList<>();
		hyperLinkHelpers.addAll(HyperLinkHelpersRegistrationUtil.INSTANCE.getAllRegisteredHyperLinkHelper());
		final HyperLinkHelperFactory hyperlinkHelperFactory = new HyperLinkHelperFactory(hyperLinkHelpers);
		
		final List<HyperLinkObject> hyperLinkObjectList = (ArrayList<HyperLinkObject>) hyperlinkHelperFactory.getAllreferenced(gep.getNotationView());
		
		//Creation of the Shell
		HyperLinkManagerShell hyperLinkManagerShell = new HyperLinkManagerShell(parentShell, iconsregistry, ((IGraphicalEditPart) navigationEditPolicy.getHost()).getEditingDomain(),
				(EModelElement) ((IGraphicalEditPart) navigationEditPolicy.getHost()).getNotationView().getElement(),
				((IGraphicalEditPart) navigationEditPolicy.getHost()).getNotationView(), hyperlinkHelperFactory);
        hyperLinkManagerShell.setInput(hyperLinkObjectList);
        hyperLinkManagerShell.open();
        
		} catch (ServiceException | HyperLinkException e) {
			Activator.log.error("Error in opening HyperLink Management Shell",e);//$NON-NLS-1$
		}

		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @return a given EditPolicy from its name
	 */
	// FIXME : there must be some API to provide directly that	
	public EditPolicy getEditPolicy(String editPolicyName) {
		EditPolicy lookupEditPolicy = null;
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow != null) {
			ISelectionService selectionService = activeWorkbenchWindow.getSelectionService();
			ISelection selectionTmp = selectionService.getSelection();
			if ((selectionTmp != null) && (!selectionTmp.isEmpty())) {
				if (selectionTmp instanceof StructuredSelection) {
					Iterator<?> it = ((StructuredSelection) selectionTmp).iterator();
					while (it.hasNext()) {
						Object current = it.next();
						if (current instanceof IGraphicalEditPart) {
							EditPolicy editpolicy = ((IGraphicalEditPart) current).getEditPolicy(editPolicyName);
							if (editpolicy != null) {
								lookupEditPolicy = editpolicy;
								break;
							}
						}
					}
				}
			}
		}
		return lookupEditPolicy;
	}
}

