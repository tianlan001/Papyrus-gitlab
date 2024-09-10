/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - bug 569174, 570944
 *****************************************************************************/

package org.eclipse.papyrus.dev.assistants.codegen.internal.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.gmf.codegen.gmfgen.CustomBehaviour;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenFactory;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Adds the edit policy for the Papyrus Popup Bar diagram assistant to a compartment in a GMFGen model (top-level shapes get it for free).
 */
public class AddPopupBarEditPolicyHandler extends AbstractHandler {
	static final String EDIT_POLICY_QNAME = "org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusPopupBarEditPolicy"; //$NON-NLS-1$
	static final String EDIT_POLICY_KEY = EditPolicyRoles.class.getName() + ".POPUPBAR_ROLE"; //$NON-NLS-1$

	public AddPopupBarEditPolicyHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<Command> commands = Lists.newArrayList();
		IStructuredSelection sel = AdapterUtils.adapt(HandlerUtil.getCurrentSelection(event), IStructuredSelection.class, null);
		if (sel != null) {
			EditingDomain domain = null;
			for (GenCompartment next : Iterables.filter(sel.toList(), GenCompartment.class)) {
				if (domain == null) {
					domain = AdapterFactoryEditingDomain.getEditingDomainFor(next);
				}
				if (domain != null) {
					CustomBehaviour behaviour = getPopupBarBehaviour(next);
					if (behaviour != null) {
						// Just set the edit policy class
						if (!EDIT_POLICY_QNAME.equals(behaviour.getEditPolicyQualifiedClassName())) {
							commands.add(SetCommand.create(domain, behaviour, GMFGenPackage.eINSTANCE.getCustomBehaviour_EditPolicyQualifiedClassName(), EDIT_POLICY_QNAME));
						}
					} else {
						// Need to create the custom behaviour
						behaviour = GMFGenFactory.eINSTANCE.createCustomBehaviour();
						behaviour.setKey(EDIT_POLICY_KEY);
						behaviour.setEditPolicyQualifiedClassName(EDIT_POLICY_QNAME);
						commands.add(AddCommand.create(domain, next, GMFGenPackage.eINSTANCE.getGenCommonBase_Behaviour(), behaviour));
					}
				}
			}

			if (!commands.isEmpty()) {
				domain.getCommandStack().execute(new CompoundCommand("Add Popup Bar Behavior", commands));
			}
		}
		return null;
	}

	protected CustomBehaviour getPopupBarBehaviour(GenCommonBase genBase) {
		CustomBehaviour result = null;

		for (CustomBehaviour next : Iterables.filter(genBase.getBehaviour(), CustomBehaviour.class)) {
			if (EDIT_POLICY_KEY.equals(next.getKey())) {
				result = next;
				break;
			}
		}

		return result;
	}
}
