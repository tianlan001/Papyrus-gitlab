/******************************************************************************
 * Copyright (c) 2014, 2023 Obeo, CEA LIST, Artal Technologies
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - initial API and implementation
 *  Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - adaptation to integrate in Papyrus
 *  Jessy Mallet (Obeo) - jessy.mallet@obeo.fr - Bug 579695
 *****************************************************************************/

package org.eclipse.papyrus.sirius.uml.diagram.common.core.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.papyrus.uml.domain.services.create.ElementCreationChecker;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.business.api.helper.graphicalfilters.HideFilterHelper;
import org.eclipse.sirius.diagram.business.api.query.ContainerMappingQuery;
import org.eclipse.sirius.diagram.business.api.query.DDiagramQuery;
import org.eclipse.sirius.diagram.business.internal.helper.task.operations.CreateViewTask;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.tool.CreateEdgeView;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.diagram.description.tool.ToolFactory;
import org.eclipse.sirius.diagram.model.business.internal.helper.ContentHelper;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;
import org.eclipse.sirius.ecore.extender.business.api.accessor.exception.FeatureNotFoundException;
import org.eclipse.sirius.ecore.extender.business.api.accessor.exception.MetaClassNotFoundException;
import org.eclipse.sirius.tools.api.command.CommandContext;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;

/**
 * A set of services used by diagrams.
 */
@SuppressWarnings("restriction")
public abstract class AbstractDiagramServices {

	protected static final String CONTAINER_VIEW = "containerView"; //$NON-NLS-1$
	private static final String SOURCE = "source";//$NON-NLS-1$
	private static final String TARGET = "target"; //$NON-NLS-1$


	/**
	 * Compute default name.
	 *
	 * @param element
	 *            New element
	 * @return Name for the new element, he name will looks like
	 *         'ElementType'+total of existing elements of the same type.
	 */
	public String computeDefaultName(final EObject element) {
		return LabelServices.INSTANCE.computeDefaultName(element);
	}

	/**
	 * Compute the tooltip.
	 *
	 * @param element
	 *            New element
	 * @return Tooltip for the new element, the tooltip will looks like
	 *         'pkg::name'.
	 */
	public String computeTooltip(final EObject element) {
		return TooltipServices.INSTANCE.computeTooltip(element);
	}

	/**
	 * Compute the label of the given element.
	 *
	 * @param element
	 *            the {@link Element} for which to retrieve a label.
	 * @return the computed label.
	 */
	public String computeUmlLabel(Element element) {
		return LabelServices.INSTANCE.computeUmlLabel(element);
	}

	/**
	 * Creates a new Sirius view.
	 *
	 * @param semanticElement
	 *            Semantic element
	 * @param containerView
	 *            Container view
	 * @param containerViewExpression
	 *            The aql expression to retrieve the container view.
	 */
	public void createView(final EObject semanticElement, final DSemanticDecorator containerView, final String containerViewExpression) {
		Session session = SessionManager.INSTANCE.getSession(semanticElement);
		this.createView(semanticElement, containerView, session, containerViewExpression);
	}

	/**
	 * Create view.
	 *
	 * @param semanticElement
	 *            Semantic element
	 * @param containerView
	 *            Container view
	 * @param session
	 *            Session
	 * @param containerViewVariable
	 *            Name of the container view variable
	 */
	protected void createView(final EObject semanticElement, final DSemanticDecorator containerView, final Session session, final String containerViewExpression) {
		// Get all available mappings applicable for the semantic element in the
		// current container
		final List<DiagramElementMapping> semanticElementMappings = this.getMappings(semanticElement, containerView, session);

		// Build a createView tool
		for (final DiagramElementMapping semanticElementMapping : semanticElementMappings) {
			final CreateView createViewOp = ToolFactory.eINSTANCE.createCreateView();
			final DiagramElementMapping tmpSemanticElementMapping = semanticElementMapping;
			createViewOp.setMapping(tmpSemanticElementMapping);
			createViewOp.setContainerViewExpression(containerViewExpression);

			this.executeCreateViewOperation(semanticElement, session, containerView, createViewOp);
		}
	}

	/**
	 * Check if the given {@link DSemanticDecorator} is a compartment view.
	 *
	 * @param view
	 *            the view to check,
	 * @return <code>true</code> if the view is a compartment view, <code>false</code> otherwise.
	 */
	protected boolean isCompartment(final DSemanticDecorator view) {
		// @formatter:off
		return Optional.ofNullable(view)
				.filter(DDiagramElement.class::isInstance)
				.map(DDiagramElement.class::cast)
				.map(DDiagramElement::getDiagramElementMapping)
				.filter(ContainerMapping.class::isInstance)
				.map(ContainerMapping.class::cast)
				.map(ContainerMappingQuery::new)
				.filter(ContainerMappingQuery::isRegion)
				.isPresent();
		// @formatter:on
	}

	/**
	 * Check if a given element is represented by a BorderNode in a given container view.
	 *
	 * @param semanticElement
	 *            the semanticElement to display in the container view,
	 * @param targetView
	 *            the container view that will contain the mapping of the semanticElement,
	 * @return <code>true</code> if the semanticElement will be display with a BorderNode, <code>false</code> otherwise.
	 */
	protected boolean isBorderNode(EObject semanticElement, DSemanticDecorator targetView) {
		Session session = SessionManager.INSTANCE.getSession(semanticElement);
		List<DiagramElementMapping> semanticElementMappings = this.getMappings(semanticElement, targetView, session);
		if (!semanticElementMappings.isEmpty()) {
			DiagramElementMapping diagramElementMapping = semanticElementMappings.get(0);
			EObject eContainer = diagramElementMapping.eContainer();
			if (eContainer instanceof AbstractNodeMapping) {
				return ((AbstractNodeMapping) eContainer).getBorderedNodeMappings().contains(semanticElementMappings.get(0));
			}
		}
		return false;
	}

	/**
	 * Create Edge view by DnD.
	 *
	 * @param semanticElement
	 *            Semantic element
	 * @param dDiagram
	 *            the diagram in which the edge view will be created.
	 * @param session
	 *            Session
	 * @param containerViewExpression
	 *            expression used to get the container view.
	 * @param isDiagramWithRoot
	 *            indicates if the root semantic element is also displayed as a root container
	 */
	protected void createDnDEdgeView(final EObject semanticElement, final DDiagram dDiagram, final Session session, boolean isDiagramWithRoot) {
		// Get all available mappings applicable for the semantic element in the
		// current container
		final List<DiagramElementMapping> semanticElementMappings = this.getMappings(semanticElement, (DSemanticDecorator) dDiagram, session);
		this.createSourceAndTargetView(semanticElement, (DSemanticDiagram) dDiagram, session, isDiagramWithRoot);
		// Build a createEdgeView tool
		for (final DiagramElementMapping semanticElementMapping : semanticElementMappings) {
			if (semanticElementMapping instanceof EdgeMapping) {
				this.createEdgeViewFromMapping(semanticElement, session, dDiagram, semanticElementMapping, "var:newContainerView", "aql:self.getSource()", "aql:self.getTargets()->first()"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			}
		}
	}

	/**
	 * Creates the source and target view needed to create the given semanticElement.
	 *
	 * @param semanticElement
	 *            the semantic element on which the domain based edge is based on.
	 * @param dSemanticDiagram
	 *            the semantic diagram.
	 * @param session
	 *            the Sirius session.
	 * @param isDiagramWithRoot
	 *            indicates if the root semantic element is also displayed as a root container
	 */
	protected void createSourceAndTargetView(EObject semanticElement, DSemanticDiagram dSemanticDiagram, Session session, boolean isDiagramWithRoot) {
		DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
		Optional<EObject> optionalSemanticSource = Optional.ofNullable(domainBasedEdgeServices.getSource(semanticElement));
		Optional<? extends EObject> optionalSemanticTarget = domainBasedEdgeServices.getTargets(semanticElement).stream().findFirst();

		if (optionalSemanticSource.isPresent() && optionalSemanticTarget.isPresent()) {
			EObject semanticSource = optionalSemanticSource.get();
			EObject semanticTarget = optionalSemanticTarget.get();
			Collection<DDiagramElement> allDiagramElements = new DDiagramQuery(dSemanticDiagram).getAllDiagramElements();
			boolean sourceViewExist = false;
			boolean targetViewExist = false;
			Iterator<DDiagramElement> iterator = allDiagramElements.iterator();
			while (!(sourceViewExist && targetViewExist) && iterator.hasNext()) {
				DDiagramElement diagramElement = iterator.next();
				if (semanticSource.equals(diagramElement.getTarget())) {
					sourceViewExist = true;
				}
				if (semanticTarget.equals(diagramElement.getTarget())) {
					targetViewExist = true;
				}
			}
			if (!sourceViewExist) {
				Optional<DSemanticDecorator> optionalTargetView = this.getContainerTargetView(semanticSource, dSemanticDiagram, allDiagramElements, isDiagramWithRoot);
				if (optionalTargetView.isPresent()) {
					IInterpreter interpreter = session.getInterpreter();
					interpreter.setVariable(CONTAINER_VIEW, optionalTargetView.get());
					this.createView(semanticSource, optionalTargetView.get(), session, "aql:" + CONTAINER_VIEW); //$NON-NLS-1$
					interpreter.unSetVariable(CONTAINER_VIEW);
				}
			}
			if (!targetViewExist) {
				Optional<DSemanticDecorator> optionalTargetView = this.getContainerTargetView(semanticTarget, dSemanticDiagram, allDiagramElements, isDiagramWithRoot);
				if (optionalTargetView.isPresent()) {
					IInterpreter interpreter = session.getInterpreter();
					interpreter.setVariable(CONTAINER_VIEW, optionalTargetView.get());
					this.createView(semanticTarget, optionalTargetView.get(), session, "aql:" + CONTAINER_VIEW); //$NON-NLS-1$
					interpreter.unSetVariable(CONTAINER_VIEW);
				}
			}
		}

	}

	/**
	 * Provides the containing view for the current semantic element.
	 *
	 * @param semanticElement
	 *            the current semantic element we need to find the view.
	 * @param dSemanticDiagram
	 *            the root diagram
	 * @param allDiagramElements
	 *            all diagram elements.
	 * @param isDiagramWithRoot
	 *            indicates if the root semantic element is also displayed as a root container
	 * @return the potential container view if found in the parent diagram.
	 */
	protected Optional<DSemanticDecorator> getContainerTargetView(EObject semanticElement, DSemanticDiagram dSemanticDiagram, Collection<DDiagramElement> allDiagramElements, boolean isDiagramWithRoot) {
		Optional<DSemanticDecorator> optionalContainer = Optional.empty();
		EObject semanticContainer = semanticElement.eContainer();
		if (semanticContainer != null) {
			// When the semantic root element is displayed in the diagram, the diagram is ignored.
			if (semanticContainer.equals(dSemanticDiagram.getTarget()) && !isDiagramWithRoot) {
				optionalContainer = Optional.of(dSemanticDiagram);
			} else {
				List<DDiagramElement> allDNodeElements = allDiagramElements.stream().filter(AbstractDNode.class::isInstance).collect(Collectors.toList());
				for (DDiagramElement diagramElement : allDNodeElements) {
					DiagramElementMapping diagramElementMapping = diagramElement.getDiagramElementMapping();
					boolean isRegionContainer = this.isRegionContainer(diagramElementMapping);
					// The sub node and container are contained in the internal structure (verticalStack region) so we don't want to return the region container.
					if (semanticContainer.equals(diagramElement.getTarget()) && !isRegionContainer) {
						DSemanticDecorator targetView = diagramElement;
						// The border node is owned by the region container.
						if (this.isCompartment(targetView)) {
							if (this.isBorderNode(semanticContainer, (DSemanticDecorator) targetView.eContainer())) {
								targetView = (DSemanticDecorator) targetView.eContainer();
							}
						}
						optionalContainer = Optional.of(targetView);
						break;
					}
				}
			}
		}
		return optionalContainer;
	}

	private boolean isRegionContainer(DiagramElementMapping diagramElementMapping) {
		if (diagramElementMapping instanceof ContainerMapping) {
			ContainerMappingQuery containerMappingQuery = new ContainerMappingQuery((ContainerMapping) diagramElementMapping);
			return containerMappingQuery.isRegionContainer();
		}
		return false;
	}


	/**
	 * Create a new domain based Edge view matching the given semanticElement.
	 *
	 * @param semanticElement
	 *            the edge Semantic element
	 * @param sourceView
	 *            Source view (the current container) used to retrieve the parent diagram
	 * @param session
	 *            the current Sirius Session
	 */
	protected void createEdgeView(final EObject semanticElement, final DDiagramElement sourceView, final Session session) {
		Optional<DSemanticDiagram> optionalContainerView = Optional.ofNullable(sourceView.getParentDiagram())
				.filter(DSemanticDiagram.class::isInstance)
				.map(DSemanticDiagram.class::cast);
		if (optionalContainerView.isPresent()) {
			// Get all available mappings applicable for the semantic element in the
			// current container
			final List<DiagramElementMapping> semanticElementMappings = this.getMappings(semanticElement, optionalContainerView.get(), session);

			DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();

			// We retrieve the effective source and target in case they are different from the graphical selected ones.
			Optional<EObject> optionalSource = Optional.ofNullable(domainBasedEdgeServices.getSource(semanticElement));
			Optional<? extends EObject> optionalTarget = domainBasedEdgeServices.getTargets(semanticElement).stream().findFirst();
			IInterpreter interpreter = session.getInterpreter();
			if (interpreter != null) {
				if (optionalSource.isPresent()) {
					interpreter.setVariable(SOURCE, optionalSource.get());
				}
				if (optionalTarget.isPresent()) {
					interpreter.setVariable(TARGET, optionalTarget.get());
				}
				try {
					// Build a createEdgeView tool
					for (final DiagramElementMapping semanticElementMapping : semanticElementMappings) {
						if (semanticElementMapping instanceof EdgeMapping) {
							this.createEdgeViewFromMapping(semanticElement, session, optionalContainerView.get(), semanticElementMapping, null, null, null);
						}
					}
				} finally {
					if (optionalSource.isPresent()) {
						interpreter.unSetVariable(SOURCE);
					}
					if (optionalTarget.isPresent()) {
						interpreter.unSetVariable(TARGET);
					}
				}
			}
		}
	}

	/**
	 * Create a new edge view based on a relation based edge mapping.
	 *
	 * @param semanticElement
	 *            The context element (self). Most of the time, the source of the edge.
	 * @param sourceView
	 *            the source view.
	 * @param session
	 *            The Sirius session
	 * @param mappingName
	 *            The relation based edge mapping name. Must be unique, otherwise, it will use the first found.
	 */
	protected void createRelationBasedEdgeViewFromCreationTool(final EObject semanticElement, final DSemanticDecorator sourceView, final Session session, String mappingName) {
		this.createRelationBasedEdgeView(semanticElement, sourceView, session, mappingName, null, null);
	}

	/**
	 * Create a new edge view following a target reconnection, based on a relation based edge mapping.
	 *
	 * @param semanticElement
	 *            The context element (self). Most of the time, the source of the edge.
	 * @param sourceView
	 *            the source view.
	 * @param session
	 *            The Sirius session
	 * @param mappingName
	 *            The relation based edge mapping name. Must be unique, otherwise, it will use the first found.
	 */
	protected void createReconnectTargetRelationBasedEdgeView(final EObject semanticElement, final DSemanticDecorator sourceView, final Session session, String mappingName) {
		this.createRelationBasedEdgeView(semanticElement, sourceView, session, mappingName, "aql:element", "aql:target"); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Create a new edge view based on a relation based edge mapping.
	 *
	 * @param semanticElement
	 *            The source semantic element.
	 * @param sourceView
	 *            the edge source view.
	 * @param session
	 *            the Sirius session.
	 * @param mappingName
	 *            the edge mapping.
	 * @param sourceExpression
	 *            the source expression (depends on the tool).
	 * @param targetExpression
	 *            (depends on the tool).
	 */
	private void createRelationBasedEdgeView(final EObject semanticElement, final DSemanticDecorator sourceView, final Session session, String mappingName, String sourceExpression, String targetExpression) {
		// @formatter:off
		Optional<DDiagram> optionalDDiagram = Optional.of(sourceView)
				.filter(DDiagramElement.class::isInstance)
				.map(DDiagramElement.class::cast)
				.map(DDiagramElement::getParentDiagram);
		// @formatter:on
		if (optionalDDiagram.isPresent()) {
			Optional<EdgeMapping> optionalEdgeMapping = ContentHelper.getAllEdgeMappings(optionalDDiagram.get().getDescription(), false).stream()
					.filter(mapping -> mappingName.equals(mapping.getName()))
					.findFirst();
			if (optionalEdgeMapping.isPresent()) {
				this.createEdgeViewFromMapping(semanticElement, session, optionalDDiagram.get(), optionalEdgeMapping.get(), null, sourceExpression, targetExpression);
			}
		}
	}

	/**
	 * Create an Edge view from the given mapping.
	 *
	 * @param semanticElement
	 *            the target semantic element.
	 * @param session
	 *            the Sirius session.
	 * @param dDiagram
	 *            the {@link DDiagram}
	 * @param semanticElementMapping
	 *            the mapping from which we want to create the edge view.
	 * @param sourceExpression
	 *            the source expression (Optional). Default: var:source
	 * @param targetExpression
	 *            the target expression (Optional). Default: var:target
	 */
	private void createEdgeViewFromMapping(final EObject semanticElement, final Session session, DDiagram dDiagram, final DiagramElementMapping semanticElementMapping, String containerViewExpression, String sourceExpression, String targetExpression) {
		final CreateEdgeView createEdgeViewOp = ToolFactory.eINSTANCE.createCreateEdgeView();
		final DiagramElementMapping tmpSemanticElementMapping = semanticElementMapping;
		createEdgeViewOp.setMapping(tmpSemanticElementMapping);
		String safeContainerViewExpression = Optional.ofNullable(containerViewExpression).orElse("aql:sourceView.eContainer(diagram::DDiagram)");//$NON-NLS-1$
		createEdgeViewOp.setContainerViewExpression(safeContainerViewExpression);
		String safeSourceExpression = Optional.ofNullable(sourceExpression).orElse("var:" + SOURCE); //$NON-NLS-1$
		createEdgeViewOp.setSourceExpression(safeSourceExpression);
		String safeTargetExpression = Optional.ofNullable(targetExpression).orElse("var:" + TARGET);//$NON-NLS-1$
		createEdgeViewOp.setTargetExpression(safeTargetExpression);

		this.executeCreateViewOperation(semanticElement, session, (DSemanticDecorator) dDiagram, createEdgeViewOp);
	}

	/**
	 * Execute the create view operation.
	 *
	 * @param semanticElement
	 *            the semantic element to represent with the view,
	 * @param session
	 *            the session with the {@link TransactionalEditingDomain} used to execute command,
	 * @param containerView
	 *            the container view which will contain the new view,
	 * @param createViewOp
	 *            the create view operation to execute
	 */
	protected void executeCreateViewOperation(final EObject semanticElement, final Session session, DSemanticDecorator containerView, final CreateView createViewOp) {
		try {
			// Get the command context
			DRepresentation representation = null;
			if (containerView instanceof DRepresentation) {
				representation = (DRepresentation) containerView;
			} else if (containerView instanceof DDiagramElement) {
				representation = ((DDiagramElement) containerView).getParentDiagram();
			}

			final CommandContext context = new CommandContext(semanticElement, representation);

			// Execute the create view task
			final CreateViewTask task = new CreateViewTask(context, session.getModelAccessor(), createViewOp, session.getInterpreter());
			task.execute();

			final Object createdView = session.getInterpreter().getVariable(createViewOp.getVariableName());
			if (createdView instanceof DDiagramElement) {
				final DDiagramElement element = (DDiagramElement) createdView;
				HideFilterHelper.INSTANCE.reveal(element);
			}
		} catch (final MetaClassNotFoundException e) {
			Activator.log.error("Unexpected Error",e); //$NON-NLS-1$
		} catch (final FeatureNotFoundException e) {
			Activator.log.error("Unexpected Error",e); //$NON-NLS-1$
		}
	}

	/**
	 * Default height.
	 *
	 * @param any
	 *            Any
	 * @return The default height.
	 */
	public int defaultHeight(EObject any) {
		return 10/* UIServices.INSTANCE.defaultHeight() */;
	}

	/**
	 * Default width.
	 *
	 * @param any
	 *            Any
	 * @return The default width.
	 */
	public int defaultWidth(EObject any) {
		return 10/* UIServices.INSTANCE.defaultWidth() */;
	}

	/**
	 * Get mappings available for a semantic element and a given container view.
	 *
	 * @param semanticElement
	 *            Semantic element
	 * @param containerView
	 *            Container view
	 * @param session
	 *            Session
	 * @return List of mappings which could not be null
	 */
	protected List<DiagramElementMapping> getMappings(final EObject semanticElement, final DSemanticDecorator containerView, Session session) {
		final ModelAccessor modelAccessor = session.getModelAccessor();
		final List<DiagramElementMapping> mappings = new ArrayList<DiagramElementMapping>();

		if (containerView instanceof DSemanticDiagram) {
			mappings.addAll(AddElementToDiagramServices.INSTANCE.getValidMappingsForDiagram(semanticElement, (DSemanticDiagram) containerView, session));
		} else {
			AbstractNodeMapping abstractNodeMapping = null;
			if (containerView instanceof DDiagramElementContainer) {
				abstractNodeMapping = ((DDiagramElementContainer) containerView).getActualMapping();
			} else if (containerView instanceof DNode) {
				abstractNodeMapping = ((DNode) containerView).getActualMapping();
			}
			if (abstractNodeMapping != null) {
				for (final DiagramElementMapping mapping : this.getAllMappings(abstractNodeMapping)) {
					final String domainClass = ((AbstractNodeMapping) mapping).getDomainClass();
					if (modelAccessor.eInstanceOf(semanticElement, domainClass)) {
						mappings.add(mapping);
					}
				}
			}
		}
		return mappings;
	}

	/**
	 * Get all mappings available for a given {@link AbstractNodeMapping}.
	 *
	 * @param abstractNodeMapping
	 *            the mapping with mappings children
	 * @return all mappings available for a given {@link AbstractNodeMapping}.
	 */
	private List<DiagramElementMapping> getAllMappings(final AbstractNodeMapping abstractNodeMapping) {
		List<DiagramElementMapping> allMappings = new ArrayList<>();
		allMappings.addAll(abstractNodeMapping.getReusedBorderedNodeMappings());
		allMappings.addAll(abstractNodeMapping.getBorderedNodeMappings());
		if (abstractNodeMapping instanceof ContainerMapping) {
			ContainerMapping containerMapping = (ContainerMapping) abstractNodeMapping;
			allMappings.addAll(containerMapping.getSubContainerMappings());
			allMappings.addAll(containerMapping.getSubNodeMappings());
			allMappings.addAll(containerMapping.getReusedContainerMappings());
			allMappings.addAll(containerMapping.getReusedNodeMappings());
		}
		return allMappings;
	}

	/**
	 * return the list of semantic elements we should bind with the given
	 * element in the property view.
	 *
	 * @param e
	 *            a semantic element.
	 * @return the list of semantic elements we should bind with the given
	 *         element in the property view.
	 */
	public Collection<EObject> getSemanticElements(EObject e) {
		return new SemanticElementsSwitch().getSemanticElements(e);
	}


	/**
	 * Get {@link ECrossReferenceAdapter} from given object to mainly use inverse reference.
	 *
	 * @param source
	 *            the {@link EObject} from resource which contain {@link ECrossReferenceAdapter}
	 * @return {@link ECrossReferenceAdapter} from given object to mainly use inverse reference.
	 */
	protected ECrossReferenceAdapter getECrossReferenceAdapter(EObject source) {
		return source.eResource().getResourceSet().eAdapters().stream()//
				.filter(a -> a instanceof ECrossReferenceAdapter)//
				.map(a -> (ECrossReferenceAdapter) a)//
				.findFirst().orElse(null);
	}

	/**
	 * Service used to check if an object can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create.
	 * @param containmentReference
	 *            the containment reference to use to attach the new element to the model
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreate(EObject container, EClass objectToCreate, EStructuralFeature containmentReference) {
		boolean canCreate = false;
		if (containmentReference != null) {
			canCreate = this.canCreate(container, objectToCreate, containmentReference.getName());
		}
		return canCreate;
	}

	/**
	 * Service used to check if an object can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create.
	 * @param containmentReferenceName
	 *            the name ofthe containment reference to use to attach the new element to the model
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreate(EObject container, EClass objectToCreate, String containmentReferenceName) {
		return new ElementCreationChecker().canCreate(container, objectToCreate.getName(), containmentReferenceName).isValid();
	}
}
