/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant.tests;

import static org.eclipse.papyrus.infra.gmfdiag.assistant.tests.ElementTypesUtil.canonicalize;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.common.core.service.ProviderChangeEvent;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.GetTypesForPopupBarOperation;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.GetTypesOperation;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.UMLFactory;
import org.hamcrest.CoreMatchers;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Modeling Assistant Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes() <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext() <em>Client Context</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes() <em>Excluded Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes() <em>Relationship Type</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation) <em>Provides</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Add Provider Change Listener</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Remove Provider Change Listener</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypes(java.lang.String, org.eclipse.core.runtime.IAdaptable) <em>Get Types</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Source</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Target</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Source And Target</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Target</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Source</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For Source</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For Target</em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Select Existing Element For Source
 * </em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Select Existing Element For Target
 * </em>}</li>
 * <li>{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable) <em>Get Types For Popup Bar</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementType(java.lang.String) <em>Get Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#isRelationshipType(org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Is Relationship Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelingAssistantProviderTest extends TestCase {

	/**
	 * The fixture for this Modeling Assistant Provider test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ModelingAssistantProvider fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ModelingAssistantProviderTest.class);
	}

	/**
	 * Constructs a new Modeling Assistant Provider test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ModelingAssistantProviderTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Modeling Assistant Provider test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(ModelingAssistantProvider fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Modeling Assistant Provider test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ModelingAssistantProvider getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		ResourceSet rset = new ResourceSetImpl();
		rset.eAdapters().add(new CacheAdapter()); // bug 541590 [CDO] - change is not required here
		Resource res = rset.getResource(URI.createPlatformPluginURI("org.eclipse.papyrus.infra.gmfdiag.assistant.tests/resources/test.assistants", true), true);

		setFixture((ModelingAssistantProvider) res.getContents().get(0));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated NOT
	 */
	@Override
	protected void tearDown() throws Exception {
		EMFHelper.unload(EMFHelper.getResourceSet(getFixture()));
		setFixture(null);
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes() <em>Element Type</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes()
	 * @generated NOT
	 */
	public void testGetElementTypes() {
		assertThat(getFixture().getElementTypes(), hasItem(canonicalize(UMLElementTypes.USAGE)));
		assertThat(getFixture().getElementTypes(), hasItem(canonicalize(UMLElementTypes.CLASS)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext() <em>Client Context</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext()
	 * @generated NOT
	 */
	public void testGetClientContext() {
		assertThat(getFixture().getClientContext(), is(ElementTypeUtils.getDefaultClientContext()));

		getFixture().setClientContextID("org.eclipse.papyrus.uml.diagram.clazz.TypeContext");
		IClientContext classDiagramContext = ClientContextManager.getInstance().getClientContext("org.eclipse.papyrus.uml.diagram.clazz.TypeContext");
		if (classDiagramContext == null) {
			assertThat(getFixture().getClientContext(), is(ElementTypeUtils.getDefaultClientContext()));
		} else {
			assertThat(getFixture().getClientContext(), is(classDiagramContext));
		}

		// This one always exists
		getFixture().setClientContextID("org.eclipse.gmf.runtime.emf.type.core.defaultContext");
		IClientContext gmfContext = ClientContextManager.getInstance().getClientContext("org.eclipse.gmf.runtime.emf.type.core.defaultContext");
		assertThat(getFixture().getClientContext(), is(gmfContext));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes() <em>Excluded Element Type</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes()
	 * @generated NOT
	 */
	public void testGetExcludedElementTypes() {
		assertThat(getFixture().getExcludedElementTypes(), hasItem(canonicalize(UMLElementTypes.ASSOCIATION_CLASS)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes() <em>Relationship Type</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes()
	 * @generated NOT
	 */
	public void testGetRelationshipTypes() {
		assertThat(getFixture().getRelationshipTypes(), hasItem(canonicalize(UMLElementTypes.USAGE)));
		assertThat(getFixture().getRelationshipTypes(), hasItem(canonicalize(UMLElementTypes.GENERALIZATION)));
		assertThat(getFixture().getRelationshipTypes(), not(hasItem(canonicalize(UMLElementTypes.CLASS))));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation) <em>Provides</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
	 * @generated NOT
	 */
	public void testProvides__IOperation() {
		assertThat(getFixture().provides(new GetTypesForPopupBarOperation(new EObjectAdapter(UMLFactory.eINSTANCE.createClass())) {
		}), is(true));
		assertThat(getFixture().provides(new GetTypesOperation("hint", new EObjectAdapter(UMLFactory.eINSTANCE.createClass())) {
		}), is(false));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Add Provider Change Listener</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
	 * @generated NOT
	 */
	public void testAddProviderChangeListener__IProviderChangeListener() {
		IProviderChangeListener listener = new IProviderChangeListener() {

			@Override
			public void providerChanged(ProviderChangeEvent event) {
				// Pass
			}
		};
		@SuppressWarnings("unchecked")
		Collection<IProviderChangeListener> listeners = (Collection<IProviderChangeListener>) getFixture().eGet(AssistantPackage.Literals.IPROVIDER__LISTENER);

		getFixture().addProviderChangeListener(listener);
		assertThat(listeners, hasItem(listener));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Remove Provider Change Listener</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
	 * @generated NOT
	 */
	public void testRemoveProviderChangeListener__IProviderChangeListener() {
		IProviderChangeListener listener = new IProviderChangeListener() {

			@Override
			public void providerChanged(ProviderChangeEvent event) {
				// Pass
			}
		};
		@SuppressWarnings("unchecked")
		Collection<IProviderChangeListener> listeners = (Collection<IProviderChangeListener>) getFixture().eGet(AssistantPackage.Literals.IPROVIDER__LISTENER);
		listeners.add(listener);

		getFixture().removeProviderChangeListener(listener);
		assertThat(listeners, not(hasItem(listener)));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypes(java.lang.String, org.eclipse.core.runtime.IAdaptable) <em>Get Types</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypes(java.lang.String, org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetTypes__String_IAdaptable() {
		try {
			getFixture().getTypes("hint", new EObjectAdapter(UMLFactory.eINSTANCE.createClass()));
			fail("Should have thrown UnsupportedOperationException");
		} catch (UnsupportedOperationException e) {
			// Pass
		}
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnSource__IAdaptable() {
		@SuppressWarnings("unchecked")
		Collection<IElementType> types = getFixture().getRelTypesOnSource(new EObjectAdapter(UMLFactory.eINSTANCE.createClass()));

		assertThat(types, hasItem(canonicalize(UMLElementTypes.USAGE)));
		assertThat(types, not(hasItem(canonicalize(UMLElementTypes.GENERALIZATION))));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnTarget__IAdaptable() {
		@SuppressWarnings("unchecked")
		Collection<IElementType> types = getFixture().getRelTypesOnTarget(new EObjectAdapter(UMLFactory.eINSTANCE.createDataType()));

		assertThat(types, hasItem(canonicalize(UMLElementTypes.USAGE)));
		assertThat(types, not(hasItem(canonicalize(UMLElementTypes.GENERALIZATION))));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Source And Target</em>}'
	 * operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnSourceAndTarget__IAdaptable_IAdaptable() {
		@SuppressWarnings("unchecked")
		Collection<IElementType> types = getFixture().getRelTypesOnSourceAndTarget(new EObjectAdapter(UMLFactory.eINSTANCE.createClass()), new EObjectAdapter(UMLFactory.eINSTANCE.createDataType()));

		assertThat(types, hasItem(canonicalize(UMLElementTypes.USAGE)));
		assertThat(types, not(hasItem(canonicalize(UMLElementTypes.GENERALIZATION))));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesForSREOnTarget__IAdaptable() {
		try {
			getFixture().getRelTypesForSREOnTarget(new EObjectAdapter(UMLFactory.eINSTANCE.createClass()));
			fail("Should have thrown UnsupportedOperationException");
		} catch (UnsupportedOperationException e) {
			// Pass
		}
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnSource(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesForSREOnSource__IAdaptable() {
		try {
			getFixture().getRelTypesForSREOnSource(new EObjectAdapter(UMLFactory.eINSTANCE.createClass()));
			fail("Should have thrown UnsupportedOperationException");
		} catch (UnsupportedOperationException e) {
			// Pass
		}
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated NOT
	 */
	public void testGetTypesForSource__IAdaptable_IElementType() {
		@SuppressWarnings("unchecked")
		Collection<IElementType> types = getFixture().getTypesForSource(new EObjectAdapter(UMLFactory.eINSTANCE.createDataType()), canonicalize(UMLElementTypes.USAGE));

		assertThat(types, hasItem(canonicalize(UMLElementTypes.CLASS)));
		assertThat(types, not(hasItem(canonicalize(UMLElementTypes.DATA_TYPE))));
		assertThat(types, not(hasItem(canonicalize(UMLElementTypes.PACKAGE))));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated NOT
	 */
	public void testGetTypesForTarget__IAdaptable_IElementType() {
		@SuppressWarnings("unchecked")
		Collection<IElementType> types = getFixture().getTypesForTarget(new EObjectAdapter(UMLFactory.eINSTANCE.createClass()), canonicalize(UMLElementTypes.USAGE));

		assertThat(types, hasItem(canonicalize(UMLElementTypes.DATA_TYPE)));
		assertThat(types, hasItem(canonicalize(UMLElementTypes.CLASS)));
		assertThat(types, not(hasItem(canonicalize(UMLElementTypes.PACKAGE))));
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * <em>Select Existing Element For Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated NOT
	 */
	public void testSelectExistingElementForSource__IAdaptable_IElementType() {
		try {
			getFixture().selectExistingElementForSource(new EObjectAdapter(UMLFactory.eINSTANCE.createClass()), canonicalize(UMLElementTypes.USAGE));
			fail("Should have thrown UnsupportedOperationException");
		} catch (UnsupportedOperationException e) {
			// Pass
		}
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * <em>Select Existing Element For Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated NOT
	 */
	public void testSelectExistingElementForTarget__IAdaptable_IElementType() {
		try {
			getFixture().selectExistingElementForTarget(new EObjectAdapter(UMLFactory.eINSTANCE.createClass()), canonicalize(UMLElementTypes.USAGE));
			fail("Should have thrown UnsupportedOperationException");
		} catch (UnsupportedOperationException e) {
			// Pass
		}
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable) <em>Get Types For Popup Bar</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetTypesForPopupBar__IAdaptable() {
		@SuppressWarnings("unchecked")
		Collection<IElementType> types = getFixture().getTypesForPopupBar(new EObjectAdapter(UMLFactory.eINSTANCE.createPackage()));

		assertThat(types, hasItem(canonicalize(UMLElementTypes.CLASS)));
		assertThat(types, hasItem(canonicalize(UMLElementTypes.DATA_TYPE)));

		@SuppressWarnings("unchecked")
		Collection<IElementType> types2 = getFixture().getTypesForPopupBar(new EObjectAdapter(UMLFactory.eINSTANCE.createDataType()));
		assertThat(types2, not(CoreMatchers.<IElementType> hasItem(CoreMatchers.anything())));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementType(java.lang.String) <em>Get Element Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementType(java.lang.String)
	 * @generated NOT
	 */
	public void testGetElementType__String() {
		assertThat(canonicalize(UMLElementTypes.CLASS), is(getFixture().getElementType("org.eclipse.papyrus.uml.Class")));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#isRelationshipType(org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Is Relationship Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#isRelationshipType(org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated NOT
	 */
	public void testIsRelationshipType__IElementType() {
		assertThat(getFixture().isRelationshipType(canonicalize(UMLElementTypes.USAGE)), is(true));
		assertThat(getFixture().isRelationshipType(canonicalize(UMLElementTypes.GENERALIZATION)), is(true));
		assertThat(getFixture().isRelationshipType(canonicalize(UMLElementTypes.CLASS)), is(false));
	}

} // ModelingAssistantProviderTest
