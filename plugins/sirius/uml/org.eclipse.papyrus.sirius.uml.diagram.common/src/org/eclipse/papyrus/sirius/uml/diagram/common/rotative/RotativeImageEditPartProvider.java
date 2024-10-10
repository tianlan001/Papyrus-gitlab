/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.rotative;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.CustomStyle;
import org.eclipse.sirius.diagram.ui.part.SiriusVisualIDRegistry;
import org.eclipse.sirius.diagram.ui.tools.api.figure.WorkspaceImageFigure;

/**
 * This EditPartProvider is used to provide a RotativeImageEditPart when identifying CustomStyle that describes
 * an image that should rotate according to its graphic position.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class RotativeImageEditPartProvider extends AbstractEditPartProvider {

	/**
	 * The name the type of Sirius diagrams. This String is defined in
	 * {@link org.eclipse.sirius.diagram.business.api.refresh.DiagramCreationUtil} when a Sirius diagram is created.
	 */
	private static final String SIRIUS_MODEL_ID = "Sirius"; //$NON-NLS-1$

	@Override
	protected Class<?> getNodeEditPartClass(final View view) {
		final EObject resolvedSemanticElement = ViewUtil.resolveSemanticElement(view);
		if (resolvedSemanticElement instanceof CustomStyle && this.isRotativeImage((CustomStyle) resolvedSemanticElement)) {
			return RotativeImageEditPart.class;
		}
		return super.getNodeEditPartClass(view);
	}

	/**
	 * Check if the specified CustomStyle describe a rotative image. Note that SVG images are not supported.
	 *
	 * @param customStyle
	 *            the style containing the id to check.
	 * @return <code>true</code> if the id describe a non-SVG rotative image; <code>false</code> otherwise
	 */
	private boolean isRotativeImage(CustomStyle customStyle) {
		String id = customStyle.getId();
		return id != null
				&& id.startsWith(RotativeImageHelper.ROTATIVE_PREFIX)
				&& !WorkspaceImageFigure.isSvgImage(id.substring(RotativeImageHelper.ROTATIVE_PREFIX.length()));
	}

	/**
	 * Overridden to provides this EditPart in Papyrus-Sirius diagrams only. We should only provide this EditPart for
	 * Sirius diagrams. The Sirius Model ID used to determine the diagram type is defined in
	 * {@link org.eclipse.sirius.diagram.business.api.refresh.DiagramCreationUtil} when a Sirius diagram is created.
	 *
	 * @param operation
	 *            an instance of a <code>CreateGraphicEditPartOperation</code>
	 * @return <code>true</code> if the EditPart should be provided; <code>false</code> otherwise.
	 */
	@Override
	public boolean provides(IOperation operation) {
		boolean shouldProvides = false;
		if (operation instanceof CreateGraphicEditPartOperation) {
			View view = ((IEditPartOperation) operation).getView();
			shouldProvides = super.provides(operation) && SIRIUS_MODEL_ID.equals(SiriusVisualIDRegistry.getModelID(view));
		}
		return shouldProvides;
	}
}
