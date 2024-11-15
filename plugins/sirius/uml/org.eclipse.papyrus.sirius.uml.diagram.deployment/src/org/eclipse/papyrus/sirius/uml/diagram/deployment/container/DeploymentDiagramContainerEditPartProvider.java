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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.container;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.business.api.query.ContainerMappingQuery;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeContainer2EditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeContainerEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.SiriusEditPartFactory;
import org.eclipse.sirius.diagram.ui.internal.providers.SiriusEditPartProvider;
import org.eclipse.sirius.diagram.ui.part.SiriusVisualIDRegistry;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Node;

/**
 * Provides a specific factory to represent Deployment diagram elements as a 3D box.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@SuppressWarnings("restriction")
public class DeploymentDiagramContainerEditPartProvider extends SiriusEditPartProvider {

	private static final String DEPLOYMENT_DIAGRAM_NAME = "DeploymentDiagram"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 */
	public DeploymentDiagramContainerEditPartProvider() {
		this.setFactory(new SiriusEditPartFactory() {
			@Override
			public EditPart createEditPart(EditPart context, Object model) {
				if (model instanceof View view) {
					if (this.isRepresentedByDeploymentContainer(view)) {
						int id = SiriusVisualIDRegistry.getVisualID(view);
						if (id == DNodeContainerEditPart.VISUAL_ID) {
							return new CuboidDNodeContainerEditPart(view);
						} else if (id == DNodeContainer2EditPart.VISUAL_ID && !this.isRegion(view)) {
							// We do not provide the custom edit part for the internal compartment.
							return new CuboidDNodeContainerInContainerEditPart(view);
						}
					}
				}
				return super.createEditPart(context, model);
			}

			private boolean isRepresentedByDeploymentContainer(View view) {
				return Optional.ofNullable(view.getElement())//
						.filter(DSemanticDecorator.class::isInstance)//
						.map(DSemanticDecorator.class::cast)//
						.map(DSemanticDecorator::getTarget)//
						.filter(Node.class::isInstance)//
						.isPresent();
			}

			private boolean isRegion(final View view) {
				boolean isRegion = Optional.ofNullable(view.getElement())
						.filter(DDiagramElement.class::isInstance)
						.map(DDiagramElement.class::cast)
						.map(DDiagramElement::getDiagramElementMapping)
						.filter(ContainerMapping.class::isInstance)
						.filter(containerMapping -> {
							return new ContainerMappingQuery((ContainerMapping) containerMapping).isRegion();
						})
						.isPresent();
				return isRegion;
			}
		});
	}

	@Override
	public synchronized boolean provides(IOperation operation) {
		if(operation instanceof IEditPartOperation editPartOperation) {
			if (editPartOperation.getView() != null
					&& editPartOperation.getView().getDiagram() != null
					&& editPartOperation.getView().getDiagram().getElement() instanceof DDiagram diagram) {
				if(Objects.equals(diagram.getDescription().getName(), DEPLOYMENT_DIAGRAM_NAME)) {
					return super.provides(editPartOperation);
				} else {
					return false;
				}
			}
		}
		return super.provides(operation);
	}
}
