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
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * Edit Policy for Applied Stereotype Label for {@link PackageImport}.
 * <p>
 * It simply adds a tag "use" to the label. Thanks to GMF, it is not possible to call a cTor with a parameter. So it calls super cTor with the "import" parameter
 * </p>
 */
public class AppliedStereotypePackageImportLabelDisplayEditPolicy extends AppliedStereotypeLinkLabelDisplayEditPolicy {

	String importTag = Activator.ST_LEFT + "import" + Activator.ST_RIGHT; //$NON-NLS-1$

	String accessTag = Activator.ST_LEFT + "access" + Activator.ST_RIGHT; //$NON-NLS-1$

	@Override
	public void activate() {
		super.activate();
		if (hostSemanticElement instanceof PackageImport) {
			changeTag(((PackageImport) hostSemanticElement).getVisibility());
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		if (UMLPackage.Literals.PACKAGE_IMPORT__VISIBILITY.equals(notification.getFeature())) {
			changeTag((VisibilityKind) notification.getNewValue());
		}
	}

	protected void changeTag(VisibilityKind visibility) {
		if (VisibilityKind.PUBLIC_LITERAL.equals(visibility)) {
			tag = importTag;
		} else {
			tag = accessTag;
		}
		refreshDisplay();
	}
}
