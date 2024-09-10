/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.profile.custom.canonical;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Specialized strategy for determining the semantic children of packages in profile diagrams.
 */
public class ProfileDiagramSemanticChildrenStrategy extends DefaultUMLSemanticChildrenStrategy {

	public ProfileDiagramSemanticChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		List<? extends EObject> result = super.getCanonicalSemanticChildren(semanticFromEditPart, viewFromEditPart);

		if (semanticFromEditPart instanceof Profile && (viewFromEditPart instanceof Diagram)) {
			// The profile diagram, itself, is where all extended metaclasses are shown
			Set<org.eclipse.uml2.uml.Class> metaclasses = Sets.newLinkedHashSet();

			for (TreeIterator<EObject> iter = UML2Util.getAllContents(semanticFromEditPart, true, false); iter.hasNext();) {
				EObject next = iter.next();
				if (!(next instanceof org.eclipse.uml2.uml.Package)) {
					iter.prune();
				} else {
					for (Stereotype stereotype : ((org.eclipse.uml2.uml.Package) next).getOwnedStereotypes()) {
						// Don't include inherited ones (they belong on some other diagram)
						metaclasses.addAll(stereotype.getExtendedMetaclasses());
					}
				}
			}

			if (!metaclasses.isEmpty()) {
				List<EObject> _result = Lists.newArrayList(result);
				result = _result;
				_result.addAll(metaclasses);
			}
		}

		return result;
	}
}
