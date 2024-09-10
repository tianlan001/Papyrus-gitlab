/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.customization.properties.generation.generators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.m2m.qvt.oml.blackbox.java.Module;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;
import org.eclipse.papyrus.infra.properties.contexts.Annotatable;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextAnnotations;

/**
 * Black-box operations for context model transformations in QVTo.
 */
@Module(packageURIs = { ContextsPackage.eNS_URI })
public class ContextsBlackBox {

	public ContextsBlackBox() {
		super();
	}

	@Operation(contextual = true, kind = Kind.HELPER)
	public void setSourceModel(Annotatable self, Object sourceModel) {
		if (sourceModel instanceof EObject) {
			ContextAnnotations.setSourceModel(self, (EObject) sourceModel);
		}
	}

}
