/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.uml.modelrepair.internal.stereotypes;

import org.eclipse.uml2.uml.util.UMLUtil;



/**
 * This is the AbstractRepairAction type. Enjoy.
 */
abstract class AbstractRepairAction extends UMLUtil implements IRepairAction {

	private final Kind kind;

	protected AbstractRepairAction(Kind kind) {
		this.kind = kind;
	}

	public Kind kind() {
		return kind;
	}

	public boolean isNull() {
		return kind() == null;
	}

	public String getLabel() {
		return kind().displayName();
	}

}
