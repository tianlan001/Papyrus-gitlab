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
 *     Mathieu Velten (Atos Origin) - re-written using transactional command
 *     Christian W. Damus - bug 458685
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Element;

/**
 * Unapplies all stereotypes from an Element.
 * 
 * @deprecated Proper delegation to the Edit Service ensures deletion of stereotype applications when UML elements are deleted.
 */
@Deprecated
public class UnapplyAllStereotypesCommand extends org.eclipse.papyrus.uml.diagram.common.commands.UnapplyAllStereotypesCommand {

	public UnapplyAllStereotypesCommand(TransactionalEditingDomain domain, String label, Element element) {
		super(domain, label, element);
	}
}
