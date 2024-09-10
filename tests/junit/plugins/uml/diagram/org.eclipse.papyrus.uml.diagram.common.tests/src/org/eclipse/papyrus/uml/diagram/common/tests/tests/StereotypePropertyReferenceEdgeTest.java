/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.EDGE_LABEL_ANNOTATION_KEY;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.FEATURE_TO_SET_ANNOTATION_KEY;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeConnectionTool;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.LayoutUtils;
import org.eclipse.papyrus.infra.gmfdiag.menu.utils.DeleteActionUtil;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.commands.UnapplyStereotypeCommand;
import org.eclipse.papyrus.uml.tools.utils.ElementUtil;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrus.uml.types.core.commands.UnapplyProfileCommand;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyProfileRequest;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for Stereotype Property Reference Edge.
 * 
 * @since 1.3
 */
@SuppressWarnings("nls")
@PluginResource("/resources/518645/StereotypePropertyReferenceEdgeTest.di")
public class StereotypePropertyReferenceEdgeTest extends AbstractPapyrusTest {

	/** The Constant SINGLE_E_CLASS_REF_LABEL. */
	private static final String SINGLE_E_CLASS_REF_LABEL = "SingleEClassRefLabel";

	/** The Constant SINGLE_E_CLASS_REF. */
	private static final String SINGLE_E_CLASS_REF = "SingleEClassRef";

	/** The Constant MULTI_STEREO_REF_LABEL. */
	private static final String MULTI_STEREO_REF_LABEL = "MultiStereoRefLabel";

	/** The Constant MULTI_STEREO_REF. */
	private static final String MULTI_STEREO_REF = "MultiStereoRef";

	/** The Constant MULTI_E_CLASS_REF_LABEL. */
	private static final String MULTI_E_CLASS_REF_LABEL = "MultiEClassRefLabel";

	/** The Constant MULTI_E_CLASS_REF. */
	private static final String MULTI_E_CLASS_REF = "MultiEClassRef";

	/** The Constant SINGLE_STEREO_REF_LABEL. */
	private static final String SINGLE_STEREO_REF_LABEL = "SingleStereoRefLabel";

	/** The Constant SINGLE_STEREO_REF. */
	private static final String SINGLE_STEREO_REF = "SingleStereoRef";

	/** The Constant SOURCE_STEREOTYPE_QN. */
	private static final String SOURCE_STEREOTYPE_QN = "StereotypePropertyReferenceEdgeTest::SourceStereotype";

	/** The element type id for single stereotype references. */
	private static final String ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF = "org.eclipse.papyrus.stereotypepropertyreferenceedgetest.singlestereotyperef";

	/** The element type id for multiple stereotype references. */
	private static final String ELEMENT_TYPE_ID_MULTISTEREOTYPEREF = "org.eclipse.papyrus.stereotypepropertyreferenceedgetest.multistereotyperef";

	/** The element type id for single eclass references. */
	private static final String ELEMENT_TYPE_ID_SINGLEECLASSREF = "org.eclipse.papyrus.stereotypepropertyreferenceedgetest.singleeclassref";

	/** The element type id for multiple eclass references. */
	private static final String ELEMENT_TYPE_ID_MULTIECLASSREF = "org.eclipse.papyrus.stereotypepropertyreferenceedgetest.multieclassref";

	/** The model set fixture. */
	@Rule
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();

	/** The diagram. */
	private Diagram diagram = null;

	/** The test source 1 edit part. */
	private EditPart testSource1EditPart;

	/** The test source 2 edit part. */
	private EditPart testSource2EditPart;

	/** The test target stereo 1 edit part. */
	private EditPart testTargetStereo1EditPart;

	/** The test target stereo 2 edit part. */
	private EditPart testTargetStereo2EditPart;

	/** The test target E class 1 edit part. */
	private EditPart testTargetEClass1EditPart;

	/** The test target E class 2 edit part. */
	private EditPart testTargetEClass2EditPart;

	/** The editing domain. */
	private TransactionalEditingDomain editingDomain;

	/**
	 * Check creation stereotype property reference edge result.
	 *
	 * @param sourceView
	 *            the source view
	 * @param targetView
	 *            the target view
	 * @param expectedStereotypeQN
	 *            the expected stereotype QN
	 * @param expectedFeatureToSet
	 *            the expected feature to set
	 * @param expectedEdgeLabel
	 *            the expected edge label
	 */
	@SuppressWarnings("rawtypes")
	protected void checkStereotypePropertyReferenceEdge(final View sourceView, final View targetView, final String expectedStereotypeQN, final String expectedFeatureToSet, final String expectedEdgeLabel, final boolean edgeMustBeCreated) {
		// Get source connection from source
		if (edgeMustBeCreated) {
			assertTrue("Source connections from source must not be empty", !ViewUtil.getSourceConnections(sourceView).isEmpty());
		}
		// find the expected created edge
		Boolean edgePresent = !getEdges(sourceView, targetView, expectedStereotypeQN, expectedFeatureToSet, expectedEdgeLabel).isEmpty();
		if (edgeMustBeCreated) {
			assertTrue("The edge must exist for " + expectedStereotypeQN + "::" + expectedFeatureToSet + "with label " + expectedEdgeLabel, edgePresent);
		} else {
			assertTrue("The edge must not exist for " + expectedStereotypeQN + "::" + expectedFeatureToSet + "with label " + expectedEdgeLabel, !edgePresent);
		}

		// Test the semantic set in stereotype application
		Element sourceElement = (Element) sourceView.getElement();
		Element targetElement = (Element) targetView.getElement();

		Stereotype sourceStereotype = UMLUtil.getApplicableStereotype(sourceElement, expectedStereotypeQN, true);
		Stereotype appliedSubstereotype = UMLUtil.getAppliedSubstereotype(sourceElement, sourceStereotype);
		Object sourceFeatureValue = sourceElement.getValue(appliedSubstereotype, expectedFeatureToSet);

		// Get The expected target value
		Property attribute = sourceStereotype.getAttribute(expectedFeatureToSet, null);
		assertNotNull("Attribute must exist", attribute);
		Type targetType = attribute.getType();
		EObject expectedTargetValue = null;
		if (targetType instanceof Stereotype) {
			// feature as stereotype reference
			if (ElementUtil.hasStereotypeApplied(targetElement, targetType.getQualifiedName())) {
				// The edge target stereotype application must be equals to the source feature value stereotype application
				EObject targetStereotypeApplication = targetElement.getStereotypeApplication(UMLUtil.getAppliedSubstereotype(targetElement, (Stereotype) targetType));
				expectedTargetValue = targetStereotypeApplication;
			}
		} else {
			expectedTargetValue = targetElement;
		}

		if (sourceFeatureValue instanceof List) {
			// multi ref case
			if (edgeMustBeCreated) {
				assertTrue("The list of references of stereotype property must contain new set value", ((List) sourceFeatureValue).contains(expectedTargetValue));
			} else {
				assertTrue("The list of references of stereotype property must not contain new set value", !((List) sourceFeatureValue).contains(expectedTargetValue));
			}
		} else {
			// single ref case
			if (edgeMustBeCreated) {
				assertEquals("The references of stereotype property must be the correct value", expectedTargetValue, sourceFeatureValue);
			} else {
				assertNotEquals("The references of stereotype property must be the correct value", expectedTargetValue, sourceFeatureValue);
			}
		}
	}

	/**
	 * Gets the creation stereotype property reference edge command.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param elementTypeId
	 *            the element type id
	 * @return the creation stereotype property reference edge command
	 */
	private void createStereotypePropertyReferenceEdge(final IGraphicalEditPart sourceEditPart, final IGraphicalEditPart targetEditPart, final String elementTypeId) {
		final IElementType elementType = ElementTypeRegistry.getInstance().getType(elementTypeId);

		CreateConnectionRequest connectionRequest = createTargetRequest(elementType);
		// get the anchors locations
		Point[] newLocation = LayoutUtils.getLinkAnchor((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart);
		connectionRequest.setTargetEditPart(sourceEditPart);
		connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_START);
		connectionRequest.setLocation(newLocation[0]);

		Command startCommand = sourceEditPart.getCommand(connectionRequest);
		Assert.assertNotNull(startCommand);
		connectionRequest.setStartCommand(startCommand);

		connectionRequest.setSourceEditPart(sourceEditPart);
		connectionRequest.setTargetEditPart(targetEditPart);
		connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_END);
		connectionRequest.setLocation(newLocation[1]);

		Command command = targetEditPart.getCommand(connectionRequest);

		// Execute command
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());
		editingDomain.getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
	}

	/**
	 * Create the target request.
	 *
	 * @param elementType
	 *            the element type
	 * @return the create the connection request
	 */
	protected CreateConnectionRequest createTargetRequest(final IElementType elementType) {
		return new AspectUnspecifiedTypeConnectionTool(Collections.singletonList(elementType)).new CreateAspectUnspecifiedTypeConnectionRequest(Collections.singletonList(elementType), false, new PreferencesHint("org.eclipse.papyrus.uml.diagram.clazz"));
	}

	/**
	 * Delete stereotype property reference edge.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param elementTypeId
	 *            the element type id
	 * @param sourceStereotypeQn
	 *            the source stereotype qn
	 * @param featureName
	 *            the feature name
	 * @param edgeLabel
	 *            the edge label
	 */
	protected void deleteStereotypePropertyReferenceEdge(final IGraphicalEditPart sourceEditPart, final IGraphicalEditPart targetEditPart, final String elementTypeId, final String sourceStereotypeQn, final String featureName, final String edgeLabel) {
		// Create edge
		createStereotypePropertyReferenceEdge(sourceEditPart, targetEditPart, elementTypeId);
		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);

		// Create another edge whitch have to be deleted too
		createStereotypePropertyReferenceEdge(sourceEditPart, targetEditPart, elementTypeId);

		IGraphicalEditPart editPart = getStereotypePropertyReferenceEdgeEditPart(sourceEditPart, targetEditPart, sourceStereotypeQn, featureName, edgeLabel);

		// delete it
		Command deleteCommand = DeleteActionUtil.getDeleteFromModelCommand(editPart, editingDomain);
		assertNotNull("The command must not be null", deleteCommand);
		assertTrue("The command must be executable", deleteCommand.canExecute());
		editingDomain.getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(deleteCommand));

		// check deletion
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		// undo test
		editingDomain.getCommandStack().undo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// redo test
		editingDomain.getCommandStack().redo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);

	}

	/**
	 * Gets the edges.
	 *
	 * @param sourceView
	 *            the source view
	 * @param targetView
	 *            the target view
	 * @param expectedStereotypeQN
	 *            the expected stereotype QN
	 * @param expectedFeatureToSet
	 *            the expected feature to set
	 * @param expectedEdgeLabel
	 *            the expected edge label
	 * @return the edges
	 */
	@SuppressWarnings("unchecked")
	protected List<Edge> getEdges(final View sourceView, final View targetView, final String expectedStereotypeQN, final String expectedFeatureToSet, final String expectedEdgeLabel) {
		return (List<Edge>) ViewUtil.getSourceConnections(sourceView).stream()
				.filter(p -> ((View) p).getType().equals(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT))
				.filter(Edge.class::isInstance)
				.map(Edge.class::cast)
				.filter(p -> ((Edge) p).getTarget().equals(targetView))
				.filter(p -> null != ((EModelElement) p).getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT))
				.filter(p -> expectedStereotypeQN.equals(((EModelElement) p).getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT).getDetails().get(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY)))
				.filter(p -> expectedFeatureToSet.equals(((EModelElement) p).getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT).getDetails().get(FEATURE_TO_SET_ANNOTATION_KEY)))
				.filter(p -> expectedEdgeLabel.equals(((EModelElement) p).getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT).getDetails().get(EDGE_LABEL_ANNOTATION_KEY)))
				.collect(Collectors.toCollection((Supplier<List<Edge>>) ArrayList::new));
	}

	/**
	 * Gets the edit part.
	 *
	 * @param semanticElement
	 *            the semantic element
	 * @return the edits the part
	 */
	private EditPart getEditPart(final String semanticElement) {

		modelSetFixture.getPageManager().openPage(diagram);

		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (null == diagramView) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (null == diagramView) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (null == diagramView) {
						diagramView = DiagramUtils.findEdge((View) object, semanticElement);
					}
				}
			}
		}

		assertNotNull(diagramView);

		IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(modelSetFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		assertNotNull(semanticEP);

		return semanticEP;
	}

	/**
	 * Gets the stereotype property reference edge edit part.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param sourceStereotypeQn
	 *            the source stereotype qn
	 * @param featureName
	 *            the feature name
	 * @param edgeLabel
	 *            the edge label
	 * @return the stereotype property reference edge edit part
	 */
	private IGraphicalEditPart getStereotypePropertyReferenceEdgeEditPart(final IGraphicalEditPart sourceEditPart, final IGraphicalEditPart targetEditPart, final String sourceStereotypeQn, final String featureName, final String edgeLabel) {
		// get the created edge
		List<Edge> edges = getEdges((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel);
		assertTrue("It must only be at least one edge", !edges.isEmpty());

		// Get the created edge edit part
		IGraphicalEditPart editPart = DiagramUtils.findEditPartforView(modelSetFixture.getEditor(), edges.get(0), IGraphicalEditPart.class);
		assertNotNull(editPart);
		return editPart;
	}

	/**
	 * Initialize test.
	 */
	@Before
	public void initialize() {

		diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "Class Diagram");
		assertNotNull(diagram);

		editingDomain = modelSetFixture.getEditingDomain();
		assertNotNull("Editing domain must not be null.", editingDomain);

		testSource1EditPart = getEditPart("Class1");
		assertTrue("The Edit Part must be a IGraphicalEditPart", testSource1EditPart instanceof IGraphicalEditPart);

		testSource2EditPart = getEditPart("Class2");
		assertTrue("The Edit Part must be a IGraphicalEditPart", testSource2EditPart instanceof IGraphicalEditPart);

		testTargetStereo1EditPart = getEditPart("Component1");
		assertTrue("The Edit Part must be a IGraphicalEditPart", testTargetStereo1EditPart instanceof IGraphicalEditPart);

		testTargetStereo2EditPart = getEditPart("Component2");
		assertTrue("The Edit Part must be a IGraphicalEditPart", testTargetStereo2EditPart instanceof IGraphicalEditPart);

		testTargetEClass1EditPart = getEditPart("DataType1");
		assertTrue("The Edit Part must be a IGraphicalEditPart", testTargetEClass1EditPart instanceof IGraphicalEditPart);

		testTargetEClass2EditPart = getEditPart("DataType2");
		assertTrue("The Edit Part must be a IGraphicalEditPart", testTargetEClass2EditPart instanceof IGraphicalEditPart);

	}

	/**
	 * Reorient source of stereotype property reference edge.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param newSourceEditPart
	 *            the new source edit part
	 * @param elementTypeId
	 *            the element type id
	 * @param sourceStereotypeQn
	 *            the source stereotype qn
	 * @param featureName
	 *            the feature name
	 * @param edgeLabel
	 *            the edge label
	 */
	private void reorientSourceOfStereotypePropertyReferenceEdge(final EditPart sourceEditPart, final EditPart targetEditPart, final EditPart newSourceEditPart, final String elementTypeId, final String sourceStereotypeQn, final String featureName,
			final String edgeLabel) {
		// create edge
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, elementTypeId);
		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);

		// Gets the edge edit part
		IGraphicalEditPart edgeEditPart = getStereotypePropertyReferenceEdgeEditPart((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, sourceStereotypeQn, featureName, edgeLabel);
		assertTrue("The edit part must be instance of ConnectionEditPart", edgeEditPart instanceof ConnectionEditPart);

		// create reconnect request
		ReconnectRequest reconnectRequest = new ReconnectRequest(RequestConstants.REQ_RECONNECT_SOURCE);
		reconnectRequest.setConnectionEditPart((ConnectionEditPart) edgeEditPart);

		Point newLocation = LayoutUtils.getLinkAnchor((IGraphicalEditPart) newSourceEditPart, (IGraphicalEditPart) targetEditPart)[0];
		reconnectRequest.setLocation(newLocation);
		reconnectRequest.setTargetEditPart(newSourceEditPart);

		// Get the command
		Command command = newSourceEditPart.getCommand(reconnectRequest);
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());

		// Execute command
		editingDomain.getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		// Check result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		checkStereotypePropertyReferenceEdge((View) newSourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// undo test
		editingDomain.getCommandStack().undo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		checkStereotypePropertyReferenceEdge((View) newSourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		// redo
		editingDomain.getCommandStack().redo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		checkStereotypePropertyReferenceEdge((View) newSourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
	}

	/**
	 * Reorient source of stereotype property reference edge with other same edge.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param newSourceEditPart
	 *            the new source edit part
	 * @param elementTypeId
	 *            the element type id
	 * @param sourceStereotypeQn
	 *            the source stereotype qn
	 * @param featureName
	 *            the feature name
	 * @param edgeLabel
	 *            the edge label
	 */
	private void reorientSourceOfStereotypePropertyReferenceEdgeWithOtherSameEdge(final EditPart sourceEditPart, final EditPart targetEditPart, final EditPart newSourceEditPart, final String elementTypeId, final String sourceStereotypeQn,
			final String featureName,
			final String edgeLabel) {
		// create edge 1
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, elementTypeId);
		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// create edge 2
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, elementTypeId);
		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);

		// Gets the edge edit part
		IGraphicalEditPart edgeEditPart = getStereotypePropertyReferenceEdgeEditPart((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, sourceStereotypeQn, featureName, edgeLabel);
		assertTrue("The edit part must be instance of ConnectionEditPart", edgeEditPart instanceof ConnectionEditPart);

		// create reconnect request
		ReconnectRequest reconnectRequest = new ReconnectRequest(RequestConstants.REQ_RECONNECT_SOURCE);
		reconnectRequest.setConnectionEditPart((ConnectionEditPart) edgeEditPart);

		Point newLocation = LayoutUtils.getLinkAnchor((IGraphicalEditPart) newSourceEditPart, (IGraphicalEditPart) targetEditPart)[0];
		reconnectRequest.setLocation(newLocation);
		reconnectRequest.setTargetEditPart(newSourceEditPart);

		// Get the command
		Command command = newSourceEditPart.getCommand(reconnectRequest);
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());

		// Execute command
		editingDomain.getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		// Check result
		checkStereotypePropertyReferenceEdge((View) newSourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// undo test
		editingDomain.getCommandStack().undo();
		checkStereotypePropertyReferenceEdge((View) newSourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// redo
		editingDomain.getCommandStack().redo();
		checkStereotypePropertyReferenceEdge((View) newSourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);

	}

	/**
	 * Reorient target of stereotype property reference edge.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param newTargetEditPart
	 *            the new target edit part
	 * @param elementTypeId
	 *            the element type id
	 * @param sourceStereotypeQn
	 *            the source stereotype qn
	 * @param featureName
	 *            the feature name
	 * @param edgeLabel
	 *            the edge label
	 */
	private void reorientTargetOfStereotypePropertyReferenceEdge(final EditPart sourceEditPart, final EditPart targetEditPart, final EditPart newTargetEditPart, final String elementTypeId, final String sourceStereotypeQn, final String featureName,
			final String edgeLabel) {
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, elementTypeId);

		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);

		// Gets the edge edit part
		IGraphicalEditPart edgeEditPart = getStereotypePropertyReferenceEdgeEditPart((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, sourceStereotypeQn, featureName, edgeLabel);
		assertTrue("The edit part must be instance of ConnectionEditPart", edgeEditPart instanceof ConnectionEditPart);

		// create reconnect request
		ReconnectRequest reconnectRequest = new ReconnectRequest(RequestConstants.REQ_RECONNECT_TARGET);
		reconnectRequest.setConnectionEditPart((ConnectionEditPart) edgeEditPart);

		Point newLocation = LayoutUtils.getLinkAnchor((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) newTargetEditPart)[1];
		reconnectRequest.setLocation(newLocation);
		reconnectRequest.setTargetEditPart(newTargetEditPart);

		// Get the command
		Command command = newTargetEditPart.getCommand(reconnectRequest);
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());

		// Execute command
		editingDomain.getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		// Check result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) newTargetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// undo test
		editingDomain.getCommandStack().undo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) newTargetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		// redo
		editingDomain.getCommandStack().redo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) newTargetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
	}

	/**
	 * Reorient target of stereotype property reference edge with other same edge.If not multiple the same edge must be deleted
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param newTargetEditPart
	 *            the new target edit part
	 * @param elementTypeId
	 *            the element type id
	 * @param sourceStereotypeQn
	 *            the source stereotype qn
	 * @param featureName
	 *            the feature name
	 * @param edgeLabel
	 *            the edge label
	 * @param multiple
	 *            the multiple
	 */
	private void reorientTargetOfStereotypePropertyReferenceEdgeWithOtherSameEdge(final EditPart sourceEditPart, final EditPart targetEditPart, final EditPart newTargetEditPart, final String elementTypeId, final String sourceStereotypeQn,
			final String featureName,
			final String edgeLabel,
			final boolean multiple) {
		// create edge 1
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, elementTypeId);
		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// create edge 2
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, elementTypeId);
		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);

		// Gets the edge edit part
		IGraphicalEditPart edgeEditPart = getStereotypePropertyReferenceEdgeEditPart((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, sourceStereotypeQn, featureName, edgeLabel);
		assertTrue("The edit part must be instance of ConnectionEditPart", edgeEditPart instanceof ConnectionEditPart);

		// create reconnect request
		ReconnectRequest reconnectRequest = new ReconnectRequest(RequestConstants.REQ_RECONNECT_TARGET);
		reconnectRequest.setConnectionEditPart((ConnectionEditPart) edgeEditPart);

		Point newLocation = LayoutUtils.getLinkAnchor((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) newTargetEditPart)[1];
		reconnectRequest.setLocation(newLocation);
		reconnectRequest.setTargetEditPart(newTargetEditPart);

		// Get the command
		Command command = newTargetEditPart.getCommand(reconnectRequest);
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());

		// Execute command
		editingDomain.getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));

		// Check result: if not multiple the same edge must be deleted
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, multiple);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) newTargetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		// undo test
		editingDomain.getCommandStack().undo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) newTargetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		// redo
		editingDomain.getCommandStack().redo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, multiple);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) newTargetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
	}

	/**
	 * Test stereotype property reference edge creation multi EClass reference.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws RollbackException
	 *             the rollback exception
	 * @throws ServiceException
	 *             the service exception
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Creation_MultiEClassRef() throws InterruptedException, RollbackException, ServiceException {
		createSourcePropertyStereotypeEdge(testSource1EditPart, testTargetEClass1EditPart, testTargetEClass2EditPart, ELEMENT_TYPE_ID_MULTIECLASSREF, SOURCE_STEREOTYPE_QN, MULTI_E_CLASS_REF, MULTI_E_CLASS_REF_LABEL, true);
	}

	/**
	 * Create the source property stereotype edge.
	 *
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @param targetEditPart2
	 *            the target edit part 2
	 * @param elementTypeId
	 *            the element type id
	 * @param sourceStereotypeQn
	 *            the source stereotype qn
	 * @param featureName
	 *            the feature name
	 * @param edgeLabel
	 *            the edge label
	 * @param multiple
	 *            if the feature muliplicity is > 1
	 */
	private void createSourcePropertyStereotypeEdge(final EditPart sourceEditPart, final EditPart targetEditPart, final EditPart targetEditPart2, final String elementTypeId, final String sourceStereotypeQn, final String featureName, final String edgeLabel,
			final boolean multiple) {
		// Create edge
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart, elementTypeId);

		// Check the result
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		editingDomain.getCommandStack().undo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
		editingDomain.getCommandStack().redo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);

		// Test multiple creation in another target
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) sourceEditPart, (IGraphicalEditPart) targetEditPart2, elementTypeId);
		// check it
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, multiple);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart2.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		editingDomain.getCommandStack().undo();
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart.getModel(), sourceStereotypeQn, featureName, edgeLabel, true);
		checkStereotypePropertyReferenceEdge((View) sourceEditPart.getModel(), (View) targetEditPart2.getModel(), sourceStereotypeQn, featureName, edgeLabel, false);
	}

	/**
	 * Test stereotype property reference edge creation multi stereotype reference.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws RollbackException
	 *             the rollback exception
	 * @throws ServiceException
	 *             the service exception
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Creation_MultiStereoRef() throws InterruptedException, RollbackException, ServiceException {
		createSourcePropertyStereotypeEdge(testSource1EditPart, testTargetStereo1EditPart, testTargetStereo2EditPart, ELEMENT_TYPE_ID_MULTISTEREOTYPEREF, SOURCE_STEREOTYPE_QN, MULTI_STEREO_REF, MULTI_STEREO_REF_LABEL, true);
	}

	/**
	 * Test stereotype property reference edge creation single EClass reference.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws RollbackException
	 *             the rollback exception
	 * @throws ServiceException
	 *             the service exception
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Creation_SingleEClassRef() throws InterruptedException, RollbackException, ServiceException {
		createSourcePropertyStereotypeEdge(testSource1EditPart, testTargetEClass1EditPart, testTargetEClass2EditPart, ELEMENT_TYPE_ID_SINGLEECLASSREF, SOURCE_STEREOTYPE_QN, SINGLE_E_CLASS_REF, SINGLE_E_CLASS_REF_LABEL, false);
	}

	/**
	 * Test stereotype property reference edge creation for single stereotype reference.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws RollbackException
	 *             the rollback exception
	 * @throws ServiceException
	 *             the service exception
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Creation_SingleStereoRef() throws InterruptedException, RollbackException, ServiceException {
		createSourcePropertyStereotypeEdge(testSource1EditPart, testTargetStereo1EditPart, testTargetStereo2EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF, SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL, false);
	}

	/**
	 * Test stereotype property reference edge deletion multi E class ref.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Deletion_MultiEClassRef() {
		deleteStereotypePropertyReferenceEdge((IGraphicalEditPart) testSource1EditPart, (IGraphicalEditPart) testTargetEClass1EditPart, ELEMENT_TYPE_ID_MULTIECLASSREF, SOURCE_STEREOTYPE_QN, MULTI_E_CLASS_REF, MULTI_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge deletion multi stereo ref.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Deletion_MultiStereoRef() {
		deleteStereotypePropertyReferenceEdge((IGraphicalEditPart) testSource1EditPart, (IGraphicalEditPart) testTargetStereo1EditPart, ELEMENT_TYPE_ID_MULTISTEREOTYPEREF, SOURCE_STEREOTYPE_QN, MULTI_STEREO_REF, MULTI_STEREO_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge deletion single E class ref.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Deletion_SingleEClassRef() {
		deleteStereotypePropertyReferenceEdge((IGraphicalEditPart) testSource1EditPart, (IGraphicalEditPart) testTargetEClass1EditPart, ELEMENT_TYPE_ID_SINGLEECLASSREF, SOURCE_STEREOTYPE_QN, SINGLE_E_CLASS_REF, SINGLE_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge deletion single stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_Deletion_SingleStereoRef() {
		deleteStereotypePropertyReferenceEdge((IGraphicalEditPart) testSource1EditPart, (IGraphicalEditPart) testTargetStereo1EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF, SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient source multiple EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSource_MultiEClassRef() {
		reorientSourceOfStereotypePropertyReferenceEdge(testSource1EditPart, testTargetEClass1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_MULTIECLASSREF, SOURCE_STEREOTYPE_QN, MULTI_E_CLASS_REF, MULTI_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient source multiple stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSource_MultiStereoRef() {
		reorientSourceOfStereotypePropertyReferenceEdge(testSource1EditPart, testTargetStereo1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_MULTISTEREOTYPEREF, SOURCE_STEREOTYPE_QN, MULTI_STEREO_REF, MULTI_STEREO_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient source single EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSource_SingleEClassRef() {
		reorientSourceOfStereotypePropertyReferenceEdge(testSource1EditPart, testTargetEClass1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_SINGLEECLASSREF, SOURCE_STEREOTYPE_QN, SINGLE_E_CLASS_REF, SINGLE_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient source single stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSource_SingleStereoRef() {
		reorientSourceOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetStereo1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF, SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL);
	}


	/**
	 * Test stereotype property reference edge reorient source with an other same edge for multiple EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSourceWithOtherSameEdge_MultiEClassRef() {
		reorientSourceOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetEClass1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_MULTIECLASSREF, SOURCE_STEREOTYPE_QN, MULTI_E_CLASS_REF, MULTI_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient source with an other same edge for multiple stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSourceWithOtherSameLink_MultiStereoRef() {
		reorientSourceOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetStereo1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_MULTISTEREOTYPEREF, SOURCE_STEREOTYPE_QN, MULTI_STEREO_REF, MULTI_STEREO_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient source with an other same edge for single EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSourceWithOtherSameLink_SingleEClassRef() {
		reorientSourceOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetEClass1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_SINGLEECLASSREF, SOURCE_STEREOTYPE_QN, SINGLE_E_CLASS_REF, SINGLE_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient source with an other same edge for single stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientSourceWithOtherSameLink_SingleStereoRef() {
		reorientSourceOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetStereo1EditPart, testSource2EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF, SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient target multiple EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTarget_MultiEClassRef() {
		reorientTargetOfStereotypePropertyReferenceEdge(testSource1EditPart, testTargetEClass1EditPart, testTargetEClass2EditPart, ELEMENT_TYPE_ID_MULTIECLASSREF, SOURCE_STEREOTYPE_QN, MULTI_E_CLASS_REF, MULTI_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient target multiple stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTarget_MultiStereoRef() {
		reorientTargetOfStereotypePropertyReferenceEdge(testSource1EditPart, testTargetStereo1EditPart, testTargetStereo2EditPart, ELEMENT_TYPE_ID_MULTISTEREOTYPEREF, SOURCE_STEREOTYPE_QN, MULTI_STEREO_REF, MULTI_STEREO_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient target single EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTarget_SingleEClassRef() {
		reorientTargetOfStereotypePropertyReferenceEdge(testSource1EditPart, testTargetEClass1EditPart, testTargetEClass2EditPart, ELEMENT_TYPE_ID_SINGLEECLASSREF, SOURCE_STEREOTYPE_QN, SINGLE_E_CLASS_REF, SINGLE_E_CLASS_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient target single stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTarget_SingleStereoRef() {
		reorientTargetOfStereotypePropertyReferenceEdge(testSource1EditPart, testTargetStereo1EditPart, testTargetStereo2EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF, SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL);
	}

	/**
	 * Test stereotype property reference edge reorient target with an other same edge for multiple EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTargetWithOtherSameEdge_MultiEClassRef() {
		reorientTargetOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetEClass1EditPart, testTargetEClass2EditPart, ELEMENT_TYPE_ID_MULTIECLASSREF, SOURCE_STEREOTYPE_QN, MULTI_E_CLASS_REF, MULTI_E_CLASS_REF_LABEL, true);
	}

	/**
	 * Test stereotype property reference edge reorient target multiple stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTargetWithOtherSameEdge_MultiStereoRef() {
		reorientTargetOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetStereo1EditPart, testTargetStereo2EditPart, ELEMENT_TYPE_ID_MULTISTEREOTYPEREF, SOURCE_STEREOTYPE_QN, MULTI_STEREO_REF, MULTI_STEREO_REF_LABEL, true);
	}

	/**
	 * Test stereotype property reference edge reorient target single EClass reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTargetWithOtherSameEdge_SingleEClassRef() {
		reorientTargetOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetEClass1EditPart, testTargetEClass2EditPart, ELEMENT_TYPE_ID_SINGLEECLASSREF, SOURCE_STEREOTYPE_QN, SINGLE_E_CLASS_REF, SINGLE_E_CLASS_REF_LABEL, false);
	}

	/**
	 * Test stereotype property reference edge reorient target single stereotype reference.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_ReorientTargetWithOtherSameEdge_SingleStereoRef() {
		reorientTargetOfStereotypePropertyReferenceEdgeWithOtherSameEdge(testSource1EditPart, testTargetStereo1EditPart, testTargetStereo2EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF, SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL, false);
	}

	/**
	 * Test stereotype property reference edge unapply profile.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_UnapplyProfile() {
		// Create edge
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) testSource1EditPart, (IGraphicalEditPart) testTargetStereo1EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF);
		checkStereotypePropertyReferenceEdge((View) testSource1EditPart.getModel(), (View) testTargetStereo1EditPart.getModel(), SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL, true);

		// Unapply Profile
		org.eclipse.uml2.uml.Package modelPackage = (Package) ((View) testSource1EditPart.getParent().getModel()).getElement();
		Profile profile = modelPackage.getAppliedProfile("StereotypePropertyReferenceEdgeTest");
		UnapplyProfileRequest request = new UnapplyProfileRequest(modelPackage, profile, editingDomain);
		UnapplyProfileCommand command = new UnapplyProfileCommand(request, editingDomain, "UnapplyProfile");

		// Execute command
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());
		editingDomain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(command));

		// Test the deletion of the edge
		Boolean edgePresent = !getEdges((View) testSource1EditPart.getModel(), (View) testTargetStereo1EditPart.getModel(), SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL).isEmpty();
		assertTrue("The edge must not exist after unapply profile", !edgePresent);
	}

	/**
	 * Test stereotype property reference edge when unapply stereotype on source.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_UnapplyStereotypeOnSource() {
		// Create edge
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) testSource1EditPart, (IGraphicalEditPart) testTargetStereo1EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF);
		checkStereotypePropertyReferenceEdge((View) testSource1EditPart.getModel(), (View) testTargetStereo1EditPart.getModel(), SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL, true);

		// Unapply stereotype
		Element sourceElement = (Element) ((View) testSource1EditPart.getModel()).getElement();
		Stereotype stereotype = sourceElement.getAppliedStereotype("StereotypePropertyReferenceEdgeTest::SubSourceStereotype");
		UnapplyStereotypeCommand command = new UnapplyStereotypeCommand(sourceElement, stereotype, editingDomain);

		// Execute command
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());
		editingDomain.getCommandStack().execute(command);

		// Test the deletion of the edge
		Boolean edgePresent = !getEdges((View) testSource1EditPart.getModel(), (View) testTargetStereo1EditPart.getModel(), SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL).isEmpty();
		assertTrue("The edge must not exist after unapply stereotype", !edgePresent);
	}

	/**
	 * Test stereotype property reference edge when unapply stereotype on target.
	 */
	@Test
	public void testStereotypePropertyReferenceEdge_UnapplyStereotypeOnTarget() {
		// Create edge
		createStereotypePropertyReferenceEdge((IGraphicalEditPart) testSource1EditPart, (IGraphicalEditPart) testTargetStereo1EditPart, ELEMENT_TYPE_ID_SINGLESTEREOTYPEREF);
		checkStereotypePropertyReferenceEdge((View) testSource1EditPart.getModel(), (View) testTargetStereo1EditPart.getModel(), SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL, true);

		// Unapply Profile
		Element sourceElement = (Element) ((View) testTargetStereo1EditPart.getModel()).getElement();
		Stereotype stereotype = sourceElement.getAppliedStereotype("StereotypePropertyReferenceEdgeTest::SubTargetStereotype");
		UnapplyStereotypeCommand command = new UnapplyStereotypeCommand(sourceElement, stereotype, editingDomain);

		// Execute command
		assertNotNull("The command must not be null", command);
		assertTrue("The command must be executable", command.canExecute());
		editingDomain.getCommandStack().execute(command);

		// Test the deletion of the edge
		Boolean edgePresent = !getEdges((View) testSource1EditPart.getModel(), (View) testTargetStereo1EditPart.getModel(), SOURCE_STEREOTYPE_QN, SINGLE_STEREO_REF, SINGLE_STEREO_REF_LABEL).isEmpty();
		assertTrue("The edge must not exist after unapply stereotype", !edgePresent);
	}
}

