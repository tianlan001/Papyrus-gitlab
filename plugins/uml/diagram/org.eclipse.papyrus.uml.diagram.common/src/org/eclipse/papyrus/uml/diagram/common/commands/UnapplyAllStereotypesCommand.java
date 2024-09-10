/*******************************************************************************
 * Copyright (c) 2008, 2015 Conselleria de Infraestructuras y Transporte, Generalitat de la Comunitat Valenciana, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Francisco Javier Cano MuÃ±oz (Prodevelop) - initial API implementation
 *     Mathieu Velten (Atos Origin) - rewrite using transactional command
 *     Christian W. Damus - bug 458685
 *
 ******************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.commands;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.uml2.uml.Element;

/**
 * Unapplies all stereotypes from an Element.
 *
 * @deprecated Proper delegation to the Edit Service ensures deletion of stereotype applications when UML elements are deleted.
 */
@Deprecated
public class UnapplyAllStereotypesCommand extends CompositeTransactionalCommand {

	public UnapplyAllStereotypesCommand(TransactionalEditingDomain domain, String label, Element element) {
		super(domain, label);
		List<EObject> stereotypeApplications = element.getStereotypeApplications();
		for (ListIterator<EObject> it = stereotypeApplications.listIterator(stereotypeApplications.size()); it.hasPrevious();) {
			EObject stereotypeApplication = it.previous();
			DestroyElementRequest stereoReq = new DestroyElementRequest(domain, stereotypeApplication, false);
			add(new DestroyElementCommand(stereoReq));
		}
	}
}
