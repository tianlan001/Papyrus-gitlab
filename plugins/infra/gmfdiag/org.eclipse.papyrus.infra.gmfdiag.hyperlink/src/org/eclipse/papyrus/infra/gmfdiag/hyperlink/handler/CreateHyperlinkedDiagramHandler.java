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
 *   Benoit Maggi - CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.hyperlink.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.HyperlinkNavigationMenuEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.messages.Messages;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for hyperlinked diagram menu
 */
public class CreateHyperlinkedDiagramHandler extends AbstractHandler {


	/**
	 * {@inheritDoc}
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		EditPolicy editPolicy = getEditPolicy(org.eclipse.papyrus.infra.gmfdiag.navigation.editpolicy.NavigationEditPolicy.EDIT_POLICY_ID);
		if (editPolicy instanceof HyperlinkNavigationMenuEditPolicy) {
			HyperlinkNavigationMenuEditPolicy hyperlinkNavigationMenuEditPolicy = (HyperlinkNavigationMenuEditPolicy) editPolicy;
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				EObject selectedElement = EMFHelper.getEObject(structuredSelection.getFirstElement());
				List<ViewPrototype> diagramPrototypes = new ArrayList<>();
				for (final ViewPrototype proto : PolicyChecker.getFor(selectedElement).getPrototypesFor(selectedElement)) {
					if (proto.getRepresentationKind() instanceof PapyrusDiagram) {
						diagramPrototypes.add(proto);
					}
				}
				if (!diagramPrototypes.isEmpty()) {
					Collections.sort(diagramPrototypes, new ViewPrototype.Comp());
					HyperlinkNavigationMenuEditPolicy.AddViewPopupAction addDiagramAction = hyperlinkNavigationMenuEditPolicy.new AddViewPopupAction(diagramPrototypes, Messages.HyperlinkNavigationMenuEditPolicy_CreateDiagramDialogLabel, Messages.HyperlinkNavigationMenuEditPolicy_CreateDiagramDialogMessage);
					addDiagramAction.run();
				}
			}
		}
		return UnexecutableCommand.INSTANCE;
	}	
	
	/**
	 * @return a given EditPolicy from its name
	 */
	// FIXME : there must be some API to provide directly that	
	private EditPolicy getEditPolicy(String editPolicyName) {
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

