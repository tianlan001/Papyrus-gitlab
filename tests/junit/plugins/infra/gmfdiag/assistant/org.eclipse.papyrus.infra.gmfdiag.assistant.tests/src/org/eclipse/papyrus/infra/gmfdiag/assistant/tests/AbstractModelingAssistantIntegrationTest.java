/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.assistant.tests;

import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.find;
import static org.eclipse.papyrus.infra.emf.utils.EMFFunctions.getFeature;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.AbstractBaseModel;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * Integration-type tests for the whole diagram assistant stack, from assistant models to the model-based assistant provider via the
 * GMF Modeling Assistant Service, for generically defined assistants (not specific to a particular diagram).
 */
@PluginResource("resources/model.di")
public class AbstractModelingAssistantIntegrationTest {

	@ClassRule
	public static final ModelSetFixture model = new ServiceRegistryModelSetFixture();

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private Diagram useCaseDiagram;

	public AbstractModelingAssistantIntegrationTest() {
		super();
	}

	//
	// Test fixture
	//

	@Before
	public void getUseCaseDiagram() {
		useCaseDiagram = getDiagram("Use Cases");
	}

	UseCase getUseCase(String name) {
		return (UseCase) model.getModel().getOwnedType(name, false, UMLPackage.Literals.USE_CASE, false);
	}

	Actor getActor(String name) {
		return (Actor) model.getModel().getOwnedType(name, false, UMLPackage.Literals.ACTOR, false);
	}

	private Resource getNotationResource() {
		return ((AbstractBaseModel) model.getResourceSet().getModel(NotationModel.MODEL_ID)).getResource();
	}

	Diagram getDiagram(String name) {
		return find(filter(getNotationResource().getContents(), Diagram.class), compose(equalTo(name), getFeature(NotationPackage.Literals.DIAGRAM__NAME, String.class)));
	}

	IAdaptable getEditPartSurrogate(EObject semanticElement) {
		View view = find(filter(useCaseDiagram.getChildren(), View.class), compose(equalTo(semanticElement), getFeature(NotationPackage.Literals.VIEW__ELEMENT, EObject.class)));

		assertThat("No such view", view, notNullValue());

		return new EditPartSurrogate(semanticElement, view);
	}

	IAdaptable getDiagramEditPartSurrogate() {
		return new EditPartSurrogate(useCaseDiagram.getElement(), useCaseDiagram);
	}

	@SuppressWarnings("restriction")
	IElementType getProxyType(String semanticTypeID, String visualTypeID) {
		IElementType semanticType = ElementTypesUtil.requireType(semanticTypeID);
		IElementType visualType = ElementTypesUtil.requireType(visualTypeID);
		return org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.util.ProxyElementType.create(semanticType, (IHintedType) visualType, true);
	}

	@SuppressWarnings("unchecked")
	List<IElementType> getTypesForPopupBar(IAdaptable host) {
		return ModelingAssistantService.getInstance().getTypesForPopupBar(host);
	}

	@SuppressWarnings("unchecked")
	List<IElementType> getRelTypesOnSource(IAdaptable source) {
		return ModelingAssistantService.getInstance().getRelTypesOnSource(source);
	}

	@SuppressWarnings("unchecked")
	List<IElementType> getRelTypesOnTarget(IAdaptable target) {
		return ModelingAssistantService.getInstance().getRelTypesOnTarget(target);
	}

	@SuppressWarnings("unchecked")
	List<IElementType> getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		return ModelingAssistantService.getInstance().getRelTypesOnSourceAndTarget(source, target);
	}

	@SuppressWarnings("unchecked")
	List<IElementType> getTypesForTarget(IAdaptable source, IElementType connectionType) {
		return ModelingAssistantService.getInstance().getTypesForTarget(source, connectionType);
	}

	@SuppressWarnings("unchecked")
	List<IElementType> getTypesForSource(IAdaptable target, IElementType connectionType) {
		return ModelingAssistantService.getInstance().getTypesForSource(target, connectionType);
	}

	//
	// Nested types
	//

	/**
	 * A surrogate for GEF edit parts from a visible diagram, which provides all that the modeling assistant framework needs from an edit
	 * part: adapters for the semantic and notation model elements.
	 */
	private static class EditPartSurrogate implements IAdaptable {
		private final EObject semanticElement;
		private final View notationElement;

		EditPartSurrogate(EObject semanticElement, View notationElement) {
			this.semanticElement = semanticElement;
			this.notationElement = notationElement;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <A> A getAdapter(Class<A> adapter) {
			return (A) ((adapter == EObject.class) ? semanticElement //
					: (adapter == View.class) ? notationElement //
							: null);
		}
	}
}
