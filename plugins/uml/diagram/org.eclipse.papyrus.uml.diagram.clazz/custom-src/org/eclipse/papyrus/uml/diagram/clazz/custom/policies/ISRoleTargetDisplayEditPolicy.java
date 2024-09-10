/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
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
 *  Gabriel Pascual (ALL4TEC)  gabriel.pascual@all4tec.net -  bug 382954
 */
package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import java.util.List;

import org.eclipse.papyrus.uml.diagram.clazz.custom.parsers.EndMemberKind;
import org.eclipse.uml2.uml.Property;

/**
 * this a listener to refresh the target role of the instance specification
 *
 */
public class ISRoleTargetDisplayEditPolicy extends InstanceSpecificationRoleDisplayEditPolicy {

	@Override
	protected Property getprefvalue(List<Property> array) {
		return array.get(EndMemberKind.TARGET.getIndex());
	}
}
