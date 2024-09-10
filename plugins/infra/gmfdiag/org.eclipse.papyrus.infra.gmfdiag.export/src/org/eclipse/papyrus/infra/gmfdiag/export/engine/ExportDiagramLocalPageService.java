/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.export.engine;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.AbstractLocalPageService;

/**
 * Definition of local page for Export All Diagrams feature.
 *
 * @author Gabriel Pascual
 *
 */
public class ExportDiagramLocalPageService extends AbstractLocalPageService {

	/**
	 * Constructor.
	 *
	 * @param rootElement
	 */
	public ExportDiagramLocalPageService(Object rootElement) {
		super(rootElement);
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.service.ILocalPageService#isLocalPage(java.lang.Object)
	 *
	 * @param content
	 * @return
	 */
	@Override
	public boolean isLocalPage(Object content) {

		boolean localPage = false;

		// Page must be a diagram
		if (content instanceof Diagram) {

			EObject semanticElement = ((Diagram) content).getElement();
			EObject semanticContainer = EcoreUtil.getRootContainer(semanticElement);

			// Diagram must have same root
			return rootElement.equals(semanticContainer);
		}

		return localPage;


	}
}
