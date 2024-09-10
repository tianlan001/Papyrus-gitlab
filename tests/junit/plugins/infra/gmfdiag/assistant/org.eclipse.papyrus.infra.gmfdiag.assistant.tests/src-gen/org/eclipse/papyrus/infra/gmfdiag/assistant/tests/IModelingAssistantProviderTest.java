/**
 * Copyright (c) 2014 Christian W. Damus and others.
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

import junit.framework.TestCase;

import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IModeling Assistant Provider</b></em>'.
 * <!-- end-user-doc -->
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
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class IModelingAssistantProviderTest extends TestCase
{

	/**
	 * The fixture for this IModeling Assistant Provider test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IModelingAssistantProvider fixture = null;

	/**
	 * Constructs a new IModeling Assistant Provider test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IModelingAssistantProviderTest(String name)
	{
		super(name);
	}

	/**
	 * Sets the fixture for this IModeling Assistant Provider test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(IModelingAssistantProvider fixture)
	{
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this IModeling Assistant Provider test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IModelingAssistantProvider getFixture()
	{
		return fixture;
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation) <em>Provides</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
	 * @generated NOT
	 */
	public void testProvides__IOperation()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Add Provider Change Listener</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
	 * @generated NOT
	 */
	public void testAddProviderChangeListener__IProviderChangeListener()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Remove Provider Change Listener</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
	 * @generated NOT
	 */
	public void testRemoveProviderChangeListener__IProviderChangeListener()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypes(java.lang.String, org.eclipse.core.runtime.IAdaptable) <em>Get Types</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypes(java.lang.String, org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetTypes__String_IAdaptable()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnSource__IAdaptable()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesOnTarget__IAdaptable()
	{
		fail("This test should never be executed");
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
	public void testGetRelTypesOnSourceAndTarget__IAdaptable_IAdaptable()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesForSREOnTarget__IAdaptable()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnSource(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetRelTypesForSREOnSource__IAdaptable()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated NOT
	 */
	public void testGetTypesForSource__IAdaptable_IElementType()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated NOT
	 */
	public void testGetTypesForTarget__IAdaptable_IElementType()
	{
		fail("This test should never be executed");
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
	public void testSelectExistingElementForSource__IAdaptable_IElementType()
	{
		fail("This test should never be executed");
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
	public void testSelectExistingElementForTarget__IAdaptable_IElementType()
	{
		fail("This test should never be executed");
	}

	/**
	 * Tests the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable) <em>Get Types For Popup Bar</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable)
	 * @generated NOT
	 */
	public void testGetTypesForPopupBar__IAdaptable()
	{
		fail("This test should never be executed");
	}

} // IModelingAssistantProviderTest
