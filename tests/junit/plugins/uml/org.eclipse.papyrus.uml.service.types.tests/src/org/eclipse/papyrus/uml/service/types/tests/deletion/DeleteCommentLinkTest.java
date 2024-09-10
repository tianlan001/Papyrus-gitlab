/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.tests.deletion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EReference;
//import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.infra.gmfdiag.menu.utils.DeleteActionUtil;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.types.core.utils.ElementTypeRegistryUtils;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;



/**
 * This tests check the deletion of the comment link between Class1 and Comment1 from :
 * <ul>
 * <li>a class diagram</li>
 * <li>a composite diagram</li>
 * <li>a component diagram</li>
 * <li>a set request</li>
 * </ul>
 * 
 * This test has been developped to fix the bug 492522
 * 
 * TODO : write the tests for others diagrams
 *
 */
@PluginResource("resource/bug492522/model.di")
public class DeleteCommentLinkTest extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	private static final String COMMENT1_BODY_START = "Comment1"; //$NON-NLS-1$

	private static final String COMMENT2_BODY_START = "Comment2"; //$NON-NLS-1$

	private static final int NB_EDGES_BEFORE_DELETION_IN_CLASS_DIAGRAM = 10;

	private static final int NB_EDGES_DESTROYED_IN_CLASS_DIAGRAM = 2;

	private static final int NB_NODES_IN_CLASS_DIAGRAM = 6;

	private static final int NB_EDGES_BEFORE_DELETION_IN_COMPOSITE_DIAGRAM = 9;

	private static final int NB_EDGES_DESTROYED_IN_COMPOSITE_DIAGRAM = 2;

	private static final int NB_NODES_IN_COMPOSITE_DIAGRAM = 6;

	private static final int NB_EDGES_BEFORE_DELETION_IN_COMPONENT_DIAGRAM = 9;

	private static final int NB_EDGES_DESTROYED_IN_COMPONENT_DIAGRAM = 2;

	private static final int NB_NODES_IN_COMPONENT_DIAGRAM = 6;


	private Class class1;

	private Class class2;

	private Constraint constraint1;

	private Comment comment1;

	private Comment comment2;

	private static final String CLASS_DIAGRAM_NAME = "ClassDiagram"; //$NON-NLS-1$

	private static final String COMPOSITE_DIAGRAM_NAME = "CompositeDiagram"; //$NON-NLS-1$

	private static final String COMPONENT_DIAGRAM_NAME = "ComponentDiagram"; //$NON-NLS-1$

	private Diagram classDiagram;

	private Diagram compositeDiagram;

	private Diagram componentDiagram;

	@Before
	public void initClass() {
		// get the elements of the model
		this.class1 = (Class) fixture.getModel().getOwnedMember("Class1"); //$NON-NLS-1$
		this.class2 = (Class) fixture.getModel().getOwnedMember("Class2"); //$NON-NLS-1$
		this.constraint1 = (Constraint) this.class1.getMember("Constraint1"); //$NON-NLS-1$

		for (Comment current : fixture.getModel().getOwnedComments()) {
			if (current.getBody().contains(COMMENT1_BODY_START)) {
				this.comment1 = current;
				continue;
			}
			if (current.getBody().contains(COMMENT2_BODY_START)) {
				this.comment2 = current;
				continue;
			}
		}
		Assert.assertNotNull(class1);
		Assert.assertNotNull(class2);
		Assert.assertNotNull(comment1);
		Assert.assertNotNull(comment2);
		Assert.assertNotNull(constraint1);
		checkSemanticElementsBeforeCommentLinkDeletion();

		List<Object> pages = this.fixture.getPageManager().allPages();
		for (Object current : pages) {
			if (current instanceof Diagram) {
				String name = ((Diagram) current).getName();
				if (CLASS_DIAGRAM_NAME.equals(name)) {
					classDiagram = (Diagram) current;
				} else if (COMPOSITE_DIAGRAM_NAME.equals(name)) {
					compositeDiagram = (Diagram) current;
				} else if (COMPONENT_DIAGRAM_NAME.equals(name)) {
					componentDiagram = (Diagram) current;
				}
			}
		}
		Assert.assertNotNull(classDiagram);
		Assert.assertNotNull(compositeDiagram);
		Assert.assertNotNull(componentDiagram);

	}

	/**
	 * This test does the deletion from a Comment Link Edit Part displayed in the class diagram and check all diagrams and the semantic model
	 */
	@ActiveDiagram("ClassDiagram")
	@Test
	public void testDeletionInClassDiagram() {
		testDeletionInDiagrams();
	}

	/**
	 * This test does the deletion from a Comment Link Edit Part displayed in the composite diagram and check all diagrams and the semantic model
	 */
	@ActiveDiagram("CompositeDiagram")
	@Test
	public void testDeletionInCompositeDiagram() {
		testDeletionInDiagrams();
	}

	/**
	 * This test does the deletion from a Comment Link Edit Part displayed in the component diagram and check all diagrams and the semantic model
	 */
	@ActiveDiagram("ComponentDiagram")
	@Test
	public void testDeletionInComponentDiagram() {
		testDeletionInDiagrams();
	}

	/**
	 * In some case, like from the Property View, we use a set request to set directly the new values to the feature
	 * 
	 * This test doesn't work, because the clean of the diagram using a set request is not good!
	 */
	@ActiveDiagram("ClassDiagram")
	@Test
	@InvalidTest
	public void testDeletionWithSetRequest() {
		checkModelAndDiagramBeforeLinkDeletion();
		Collection<Element> newValues = new ArrayList<Element>();
		newValues.add(class2);
		newValues.add(constraint1);
		SetRequest request = new SetRequest(fixture.getEditingDomain(), this.comment1, UMLPackage.eINSTANCE.getComment_AnnotatedElement(), newValues);
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(comment1);
		ICommand command = provider.getEditCommand(request);
		Assert.assertNotNull("The command must not be null.", command); //$NON-NLS-1$
		Assert.assertTrue("The command must be executable", command.canExecute()); //$NON-NLS-1$
		this.fixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
		checkModelAndDiagramsAfterLinkDeletion();

		this.fixture.getEditingDomain().getCommandStack().undo();
		checkModelAndDiagramBeforeLinkDeletion();
		this.fixture.getEditingDomain().getCommandStack().redo();
		checkModelAndDiagramsAfterLinkDeletion();
	}

	/**
	 * This method checks the features values for the tested elements, before the deletion
	 */
	protected void checkSemanticElementsBeforeCommentLinkDeletion() {
		Assert.assertTrue("The initial model is invalid.", comment1.getAnnotatedElements().contains(class1)); //$NON-NLS-1$
		Assert.assertTrue("The initial model is invalid.", comment1.getAnnotatedElements().contains(class2)); //$NON-NLS-1$
		Assert.assertTrue("The initial model is invalid.", comment1.getAnnotatedElements().contains(constraint1)); //$NON-NLS-1$
		Assert.assertTrue("The initial model is invalid.", constraint1.getConstrainedElements().contains(comment1)); //$NON-NLS-1$
	}

	/**
	 * This method checks the features values for the tested elements, after the deletion
	 */
	protected void checkSemanticElementsAfterCommentLinkDeletion() {
		Assert.assertTrue("Class1 has not been removed from the feature.", !comment1.getAnnotatedElements().contains(class1)); //$NON-NLS-1$
		Assert.assertTrue("Class2 has been removed of the feature -> not wanted.", comment1.getAnnotatedElements().contains(class2)); //$NON-NLS-1$
		Assert.assertTrue("Constraint1 has been removed of the feature -> not wanted.", comment1.getAnnotatedElements().contains(constraint1)); //$NON-NLS-1$
		Assert.assertTrue("Comment1 has been removed of the contrainedElement in Constraint1 -> not wanted.", constraint1.getConstrainedElements().contains(comment1)); //$NON-NLS-1$
	}

	/**
	 * This method removes a comment link between Class1 and Comment1 in the current Diagram and checks the result in the model and in all diagrams
	 */
	protected void testDeletionInDiagrams() {
		// 1. we get the current diagram
		final DiagramEditPart diagramEP = fixture.getActiveDiagram();

		// 2. we check the contents of all diagram before deletion
		checkModelAndDiagramBeforeLinkDeletion();

		// 3. we look for 1 connections between Comment and Class1
		IGraphicalEditPart epToDestroy = null;
		List<?> connections = diagramEP.getConnections();
		Iterator<?> iter = connections.iterator();
		while (iter.hasNext() && null == epToDestroy) {
			Object current = iter.next();
			if (current instanceof ConnectionEditPart) {
				ConnectionEditPart cEP = (ConnectionEditPart) current;
				Object model = cEP.getModel();
				if (model instanceof Connector) {
					String type = ((Connector) model).getType();
					EReference feature = getFeatureToRemove(type);
					if (UMLPackage.eINSTANCE.getComment_AnnotatedElement().equals(feature)) {
						EditPart source = cEP.getSource();
						EditPart target = cEP.getTarget();
						View sourceModel = source.getAdapter(View.class);
						View targetModel = target.getAdapter(View.class);
						if (comment1 == sourceModel.getElement() && class1 == targetModel.getElement()) {
							epToDestroy = (ConnectionEditPart) current;
						}
					}
				}
			}
		}

		Assert.assertNotNull("The link to destroy has not been found.", epToDestroy); //$NON-NLS-1$

		// 4. we build the delete command and we execute it
		org.eclipse.gef.commands.Command curCommand = DeleteActionUtil.getDeleteFromModelCommand(epToDestroy, fixture.getEditingDomain());

		Assert.assertNotNull("the command must not be null.", curCommand); //$NON-NLS-1$
		Assert.assertTrue("The command must be executable.", curCommand.canExecute()); //$NON-NLS-1$

		fixture.getEditingDomain().getCommandStack().execute(new GEFtoEMFCommandWrapper(curCommand));

		// 5. we check all diagrams after the deletion
		checkModelAndDiagramsAfterLinkDeletion();

		// 6. we undo and check all diagrams after redo
		fixture.getEditingDomain().getCommandStack().undo();
		checkModelAndDiagramBeforeLinkDeletion();

		// 7. we redo and check all diagrams after redo
		fixture.getEditingDomain().getCommandStack().redo();
		checkModelAndDiagramsAfterLinkDeletion();
	}

	/**
	 * 
	 */
	protected void checkModelAndDiagramBeforeLinkDeletion() {
		checkSemanticElementsBeforeCommentLinkDeletion();

		// 1. check the class diagram
		Assert.assertEquals(NB_NODES_IN_CLASS_DIAGRAM, classDiagram.getChildren().size());
		Assert.assertEquals(NB_EDGES_BEFORE_DELETION_IN_CLASS_DIAGRAM, classDiagram.getEdges().size());

		// 2. check the composite diagram
		Assert.assertEquals(NB_NODES_IN_COMPOSITE_DIAGRAM, compositeDiagram.getChildren().size());
		Assert.assertEquals(NB_EDGES_BEFORE_DELETION_IN_COMPOSITE_DIAGRAM, compositeDiagram.getEdges().size());


		// 3. check the component diagram
		Assert.assertEquals(NB_NODES_IN_COMPONENT_DIAGRAM, componentDiagram.getChildren().size());
		Assert.assertEquals(NB_EDGES_BEFORE_DELETION_IN_COMPONENT_DIAGRAM, componentDiagram.getEdges().size());

	}


	/**
	 * This methods check the model and all diagrams after the deletion of the comment link
	 */
	protected void checkModelAndDiagramsAfterLinkDeletion() {
		checkSemanticElementsAfterCommentLinkDeletion();

		// check for class diagram
		checkDiagramAfterLinkDeletion(classDiagram, NB_EDGES_BEFORE_DELETION_IN_CLASS_DIAGRAM - NB_EDGES_DESTROYED_IN_CLASS_DIAGRAM, NB_NODES_IN_CLASS_DIAGRAM);

		// check for composite diagram
		checkDiagramAfterLinkDeletion(compositeDiagram, NB_EDGES_BEFORE_DELETION_IN_COMPOSITE_DIAGRAM - NB_EDGES_DESTROYED_IN_COMPOSITE_DIAGRAM, NB_NODES_IN_COMPOSITE_DIAGRAM);

		// checl for component diagram
		checkDiagramAfterLinkDeletion(componentDiagram, NB_EDGES_BEFORE_DELETION_IN_COMPONENT_DIAGRAM - NB_EDGES_DESTROYED_IN_COMPONENT_DIAGRAM, NB_NODES_IN_COMPONENT_DIAGRAM);

	}

	/**
	 * 
	 * @param diagram
	 *            a diagram
	 * @param nbEdgeAfterDeletion
	 *            the number of link displayed in the diagram after the deletion
	 * @param nbNodeInDiagram
	 *            the number of node displayed in the diagram after the deletion
	 */
	protected void checkDiagramAfterLinkDeletion(Diagram diagram, int nbEdgeAfterDeletion, int nbNodeInDiagram) {
		// Diagram diagram = fixture.getDiagram(classDiagram2).getDiagramView();
		Assert.assertEquals(nbEdgeAfterDeletion, diagram.getEdges().size());
		Assert.assertEquals(nbNodeInDiagram, diagram.getChildren().size());


		// now we check all connections in the class diagram
		List<?> currentConnections = diagram.getEdges();
		for (Object current : currentConnections) {
			if (current instanceof Connector) {
				Connector conn = (Connector) current;
				String type = conn.getType();

				// we get a feature only in the case of the comment link
				EReference ref = getFeatureToRemove(type);
				if (null != ref) {
					View sourceModel = conn.getSource();
					View targetModel = conn.getTarget();
					if (sourceModel == comment1) {
						Assert.assertTrue(targetModel.getElement() != class1);
					}
				}
			}
		}
	}


	/**
	 * 
	 * @param visualId
	 *            the visual id
	 * @return
	 * 		the EReference represented by the view with this visual id
	 */
	protected EReference getFeatureToRemove(String visualId) {

		try {
			IClientContext context = TypeContext.getContext(fixture.getModel());
			List<IElementType> elementTypes = ElementTypeRegistryUtils.getElementTypesBySemanticHint(visualId, context.getId());

			for (IElementType iElementType : elementTypes) {
				Map<String, EReference> featureElementTypeToEReferenceMap = getFeatureElementTypeToEReferenceMap();
				for (String featureElementType : featureElementTypeToEReferenceMap.keySet()) {
					List<ISpecializationType> subs = Arrays.asList(ElementTypeRegistry.getInstance().getSpecializationsOf(featureElementType));
					if (subs.contains(iElementType)) {
						return featureElementTypeToEReferenceMap.get(featureElementType);
					}
				}
			}
		} catch (ServiceException e) {
			org.eclipse.papyrus.uml.service.types.Activator.log.error(e);
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#getFeatureElementTypeToEReferenceMap()
	 *
	 * @return
	 */

	protected Map<String, EReference> getFeatureElementTypeToEReferenceMap() {
		return Collections.singletonMap(UMLElementTypes.COMMENT_ANNOTATEDELEMENTS.getId(), UMLPackage.eINSTANCE.getComment_AnnotatedElement());
	}
}
