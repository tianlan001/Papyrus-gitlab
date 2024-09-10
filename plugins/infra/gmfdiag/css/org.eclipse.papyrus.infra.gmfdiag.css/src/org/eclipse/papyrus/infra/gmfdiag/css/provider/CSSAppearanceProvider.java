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
package org.eclipse.papyrus.infra.gmfdiag.css.provider;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.papyrus.infra.emf.appearance.style.AnnotationStyleProvider;

/**
 * An appearance provider based on CSS. It reuses the AnnotationStyleProvider:
 * if a property is changed manually, a standard annotation will be used.
 *
 * @author Camille Letavernier
 *
 */
public class CSSAppearanceProvider extends AnnotationStyleProvider {

	@Override
	public boolean showElementIcon(EModelElement modelElement) {
		if (modelElement instanceof CustomStyle) {
			return ((CustomStyle) modelElement).showElementIcon();
		}
		return super.showElementIcon(modelElement);
	}

	@Override
	public int getQualifiedNameDepth(EModelElement modelElement) {
		if (modelElement instanceof CustomStyle) {
			return ((CustomStyle) modelElement).getQualifiedNameDepth();
		}
		return super.getQualifiedNameDepth(modelElement);
	}

	@Override
	public boolean showShadow(EModelElement modelElement) {
		if (modelElement instanceof CustomStyle) {
			return ((CustomStyle) modelElement).showShadow();
		}
		return super.showShadow(modelElement);
	}

}
