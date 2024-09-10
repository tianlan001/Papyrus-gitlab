/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.junit.Assert;

public abstract class AbstractPapyrusSemanticTestCase extends AbstractPapyrusTestCase {

	/**
	 * create childNodeEditPart in parentEditPart
	 */
	protected IGraphicalEditPart createChild(String childVID, IGraphicalEditPart container) {
		final CreateViewRequest requestcreation = createRequest(childVID, container);
		Command cmd = container.getCommand(requestcreation);
		executeOnUIThread(cmd);
		return findChildBySemanticHint(container, childVID);
	}

	protected CreateViewRequest createRequest(String childVID, IGraphicalEditPart container) {
		final IElementType childType = getTypeByID(childVID);
		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childType, container.getDiagramPreferencesHint());
		requestcreation.setSize(new Dimension(1, 1));
		requestcreation.setLocation(new Point(10, 10));
		return requestcreation;
	}

	protected abstract IElementType getTypeByID(String vid);

	protected IGraphicalEditPart findChildBySemanticHint(IGraphicalEditPart parent, String vid) {
		IGraphicalEditPart childEP = parent.getChildBySemanticHint(vid);
		Assert.assertNotNull("Parent " + parent + ", type " + parent.getNotationView() + " looking for: " + vid, childEP);
		return childEP;
	}

	public void checkListElementReferenceSemantic(IGraphicalEditPart childEP, IGraphicalEditPart parentEP, EReference... expectedFeature) {
		EObject child = getSemanticElement(childEP);
		EObject parent = getSemanticElement(parentEP);
		for (EReference feature: expectedFeature) {
			Object objectList = parent.eGet(feature);
			Assert.assertTrue("Feature result should be list", objectList instanceof List<?>);
			List<?> children = (List<?>) objectList;
			Assert.assertTrue(getAssertMasageForContainChild(parent, child, feature), children.contains(child));
		}
	}

	public void checkOneElementReferenceSemantic(IGraphicalEditPart childEP, IGraphicalEditPart parentEP, EReference feature) {
		EObject child = getSemanticElement(childEP);
		EObject parent = getSemanticElement(parentEP);

		Object objectChildElement = parent.eGet(feature);
		Assert.assertEquals(getAssertMasageForContainChild(child, parent, feature), child, objectChildElement);
	}

	protected EObject getSemanticElement(IGraphicalEditPart ep) {
		EObject activityNode = ep.resolveSemanticElement();
		assertNotNull("Primary view of " + ep.getNotationView() + " must have EObject element", activityNode);
		return activityNode;
	}

	protected String getAssertMasageForContainChild(EObject child, EObject parent, EReference feature) {
		return "Element:" + parent.getClass().getSimpleName() +  "should contein child:" + child.getClass().getSimpleName()  + " in feature: " + feature.getName();
	}
}
