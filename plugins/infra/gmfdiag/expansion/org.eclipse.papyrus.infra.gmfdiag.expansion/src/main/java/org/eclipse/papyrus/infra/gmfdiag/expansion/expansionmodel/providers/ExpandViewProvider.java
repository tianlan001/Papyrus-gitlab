/**
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.providers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateChildViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionSingleton;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionsRegistry;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.IdentityGraphicalElementType;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 *
 * This class is a generic ViewProvider that is enable to create notation element by reading an expansion model
 * See Requirement #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_010
 * #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_040
 *
 */
public class ExpandViewProvider extends CustomAbstractViewProvider implements IViewProvider {

	private static final String DEBUG_PREFIX = "[EXPANSION_DIAGRAM]";
	/** Map containing node view types supported by this provider */
	protected Map<String, Class<?>> nodeMap = new HashMap<String, Class<?>>();

	/** Map containing edge view types supported by this provider */
	protected Map<String, Class<?>> edgeMap = new HashMap<String, Class<?>>();

	protected DiagramExpansionsRegistry diagramExpansionRegistry;

	/** Default constructor */
	public ExpandViewProvider() {
		super();
		initDiagramType();
		initGraphicalTypeRegistry();
		diagramExpansionRegistry = DiagramExpansionSingleton.getInstance().getDiagramExpansionRegistry();
	}


	/**
	 * Initialize the graphical type registry. This should be set in the constructor, and not called again after that.
	 */
	/**
	 * {@inheritDoc}
	 */
	protected void initGraphicalTypeRegistry() {
		this.registry = new IdentityGraphicalElementType();
	}




	@Override
	protected boolean provides(CreateNodeViewOperation operation) {
		if (!ProviderServiceUtil.isEnabled(this, operation.getContainerView())) {
			return false;
		}
		return providesFromExpansionModel(operation);
	}

	@Override
	protected boolean provides(CreateEdgeViewOperation operation) {
		if (!ProviderServiceUtil.isEnabled(this, operation.getContainerView())) {
			return false;
		}
		return providesFromExpansionModel(operation);
	}

	/**
	 * this method consults the expansion model to know if the view can be created in the current container.
	 * 
	 * @param operation
	 *                      the proposition of creation
	 * @return true if the expansion model declare it, else false
	 */
	protected boolean providesFromExpansionModel(CreateChildViewOperation operation) {
		String currentDiagramType = getDiagramType(operation.getContainerView());

		// this diagram is known as extended?
		if (diagramExpansionRegistry.mapChildreen.get(currentDiagramType) == null) {
			Activator.log.trace(Activator.EXPANSION_TRACE, this.getClass().getName() + " " + currentDiagramType + " not supported by loaded expansion model");

			// this diagram is unknown
			return false;
		}
		String graphicalType = operation.getSemanticHint();
		String containerType;
		if (operation.getContainerView() instanceof Diagram) {
			containerType = currentDiagramType;
		} else {
			containerType = operation.getContainerView().getType();
		}
		Activator.log.trace(Activator.EXPANSION_TRACE, this.getClass().getName() + " try to create view in the container " + containerType + " the view " + operation.getSemanticHint()); //$NON-NLS-1$ //$NON-NLS-2$
		// get the list of childreen Id from a parent ID
		List<String> possibleChildreenIDs = diagramExpansionRegistry.mapChildreen.get(currentDiagramType).parentChildrenRelation.get(containerType);
		if (possibleChildreenIDs == null) {
			return false;
		}
		// the child ID is known?
		if (possibleChildreenIDs.contains(graphicalType)) {
			return true;
		}
		return false;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?> getNodeViewClass(IAdaptable semanticAdapter, View containerView, String graphicalType) {
		return getViewFactory(containerView, graphicalType);
	}


	protected Class<?> getViewFactory(View containerView, String graphicalType) {
		String currentDiagramType = getDiagramType(containerView);

		// the map from the diagram ID
		if (diagramExpansionRegistry.mapChildreen.get(currentDiagramType) != null) {
			EObject eObject = diagramExpansionRegistry.mapChildreen.get(currentDiagramType).IDMap.get(graphicalType);

			// look for the representation and the view factory path
			if (eObject instanceof AbstractRepresentation) {
				String viewFactoryPath = ((AbstractRepresentation) eObject).getViewFactory();
				if (viewFactoryPath != null && (!"".equals(viewFactoryPath.trim()))) {
					return ClassLoaderHelper.loadClass(viewFactoryPath);
				} else {
					RepresentationKind representationKind = ((AbstractRepresentation) eObject).getKind();
					if (representationKind != null) {
						viewFactoryPath = representationKind.getViewFactory();
						if (viewFactoryPath != null) {
							return ClassLoaderHelper.loadClass(viewFactoryPath);
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * get the diagram type from a view.
	 * 
	 * @param currentView
	 *                        the current view
	 * @return the diagram type it can be also a view point
	 */
	protected String getDiagramType(View currentView) {
		Diagram diagram = currentView.getDiagram();
		String currentDiagramType = null;
		ViewPrototype viewPrototype = org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils.getPrototype(diagram);
		if (viewPrototype != null) {
			currentDiagramType = viewPrototype.getLabel();
		} else {
			currentDiagramType = diagram.getType();
		}
		diagramType = currentDiagramType;
		return currentDiagramType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?> getEdgeViewClass(IAdaptable semanticAdapter, View containerView, String graphicalType) {
		return getViewFactory(containerView, graphicalType);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void initDiagramType() {
		diagramType = null;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider#provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation)
	 *
	 * @param op
	 * @return
	 */
	@Override
	protected boolean provides(CreateViewForKindOperation op) {
		return false;
	}
}
