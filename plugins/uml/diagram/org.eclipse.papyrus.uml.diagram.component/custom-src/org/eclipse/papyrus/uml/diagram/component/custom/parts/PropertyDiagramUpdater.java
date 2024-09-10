/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.part.ICustomDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.part.UMLNodeDescriptor;

/**
 * <pre>
 * This class provides a custom implementation for the method
 * resolving the semantic children of a Property (as viewed in a
 * structured classifier) in {@link UMLDiagramUpdater}.
 * </pre>
 *
 * @since 3.0
 */
public class PropertyDiagramUpdater implements ICustomDiagramUpdater<UMLNodeDescriptor> {
	/**
	 * <pre>
	 * The original generated method of {@link UMLDiagramUpdater} class is kept commented below.
	 *
	 * In the CompositeStructure Diagram, Port may be shown graphically attached to
	 * a Property. This is only possible if the Property is typed by the StructuredClassifier
	 * that owns the Port.
	 *
	 * In order to implement this in GMF, the Port is declared as an affixed child for both
	 * StructuredClassifier and Property, but as the Port is not contained by Property, the
	 * Property related getSemanticChildren is not generated properly.
	 *
	 * FIXME : Not sure whether the {@link UMLDiagramUpdater} class is really needed by Papyrus
	 *
	 * {@inheritDoc}
	 * </pre>
	 */
	@Override
	public List<UMLNodeDescriptor> getSemanticChildren(View view) {
		return Collections.emptyList();
	}
}
