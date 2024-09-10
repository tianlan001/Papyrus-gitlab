/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.restrictions;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.EditPolicyProviderTester;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.SequenceDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.symbols.provider.ShapeEditPolicyProvider;
import org.osgi.service.component.annotations.Component;

@Component
public class SequenceEditPolicyProviderTester implements EditPolicyProviderTester {

	@Override
	public boolean isEnabled(IEditPolicyProvider provider, EditPart editPart) {
		if (isSequenceDiagram(editPart)) {
			if (provider instanceof ShapeEditPolicyProvider) {
				return false;
			}
			return true;
		}
		return true;
	}

	private boolean isSequenceDiagram(EditPart editPart) {
		RootEditPart root = editPart.getRoot();
		return root != null && root.getContents() instanceof SequenceDiagramEditPart;
	}

}
