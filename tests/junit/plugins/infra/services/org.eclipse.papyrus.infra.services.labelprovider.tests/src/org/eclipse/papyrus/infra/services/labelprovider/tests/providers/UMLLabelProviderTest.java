/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.labelprovider.tests.providers;

import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.uml2.uml.Comment;


public class UMLLabelProviderTest extends UMLFilteredLabelProvider {

	@Override
	public boolean accept(Object element) {
		return super.accept(element);
	}

	@Override
	public String getText(Object element) {
		if(element instanceof Comment) {
			return getText((Comment)element);
		}
		return super.getText(element);
	}

	public String getText(Comment comment) {
		return "Test_" + comment.getBody();
	}

}
