/**
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.types.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;
import org.eclipse.papyrus.infra.types.util.ElementTypesConfigurationsValidator;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Abstract Advice Binding Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet() <em>Element Type Set</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningTarget() <em>Owning Target</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public abstract class AbstractAdviceBindingConfigurationTest extends AdviceConfigurationTest {

	/**
	 * Constructs a new Abstract Advice Binding Configuration test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractAdviceBindingConfigurationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Abstract Advice Binding Configuration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected AbstractAdviceBindingConfiguration getFixture() {
		return (AbstractAdviceBindingConfiguration)fixture;
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet() <em>Element Type Set</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet()
	 * @generated NOT
	 */
	public void testGetElementTypeSet() {
		assertThat(getFixture().getElementTypeSet(), nullValue());
		
		ElementTypeSetConfiguration set = ElementTypesConfigurationsFactory.eINSTANCE.createElementTypeSetConfiguration();
		set.getAdviceBindingsConfigurations().add(getFixture());
		
		assertThat(getFixture().getElementTypeSet(), is(set));
		
		ElementTypeConfiguration type1 = ElementTypesConfigurationsFactory.eINSTANCE.createMetamodelTypeConfiguration();
		set.getElementTypeConfigurations().add(type1);
		type1.getOwnedAdvice().add(getFixture());
		
		assertThat(getFixture().getElementTypeSet(), is(set));
	}
	
	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningTarget() <em>Owning Target</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningTarget()
	 * @generated NOT
	 */
	public void testGetOwningTarget() {
		ElementTypeSetConfiguration set = ElementTypesConfigurationsFactory.eINSTANCE.createElementTypeSetConfiguration();
		ElementTypeConfiguration type = ElementTypesConfigurationsFactory.eINSTANCE.createSpecializationTypeConfiguration();
		set.getElementTypeConfigurations().add(type);
		
		assertThat(getFixture().getOwningTarget(), nullValue());
		
		type.getOwnedConfigurations().add(getFixture());
		
		assertThat(getFixture().getOwningTarget(), is(type));
		assertThat(getFixture().getTarget(), is(type)); // The superset must get the subset value
		
		set.getAdviceBindingsConfigurations().add(getFixture());
		
		assertThat(getFixture().getOwningTarget(), nullValue());
		assertThat(getFixture().getTarget(), is(type)); // The superset is not affected by the subset being cleared
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#setOwningTarget(org.eclipse.papyrus.infra.types.ElementTypeConfiguration) <em>Owning Target</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#setOwningTarget(org.eclipse.papyrus.infra.types.ElementTypeConfiguration)
	 * @generated NOT
	 */
	public void testSetOwningTarget() {
		ElementTypeSetConfiguration set = ElementTypesConfigurationsFactory.eINSTANCE.createElementTypeSetConfiguration();
		ElementTypeConfiguration type = ElementTypesConfigurationsFactory.eINSTANCE.createSpecializationTypeConfiguration();
		set.getElementTypeConfigurations().add(type);
		
		assertThat(getFixture().getOwningTarget(), nullValue());
		
		getFixture().setOwningTarget(type);
		
		assertThat(getFixture().getOwningTarget(), is(type));
		assertThat(getFixture().getTarget(), is(type)); // The superset must get the subset value
		
		getFixture().setOwningTarget(null);
		
		assertThat(getFixture().getOwningTarget(), nullValue());
		assertThat(getFixture().getTarget(), is(type)); // The superset is not affected by the subset being cleared
	}
	
	public void testValidateAbstractAdviceBindingConfiguration_apply_to_all_types() {
		ElementTypesConfigurationsValidator validator = new ElementTypesConfigurationsValidator();
		ElementTypeConfiguration type1 = ElementTypesConfigurationsFactory.eINSTANCE.createMetamodelTypeConfiguration();
		
		BasicDiagnostic diagnostics = new BasicDiagnostic();
		assertFalse(validator.validateAbstractAdviceBindingConfiguration_apply_to_all_types(getFixture(), diagnostics, new HashMap<>()));
		assertThat(diagnostics, isError());
		
		getFixture().setApplyToAllTypes(true);
		
		assertTrue(validator.validateAbstractAdviceBindingConfiguration_apply_to_all_types(getFixture(), null, null));
		
		getFixture().setTarget(type1);
		
		diagnostics = new BasicDiagnostic();
		assertFalse(validator.validateAbstractAdviceBindingConfiguration_apply_to_all_types(getFixture(), diagnostics, new HashMap<>()));
		assertThat(diagnostics, isError());
		
		getFixture().setApplyToAllTypes(false);
		
		assertTrue(validator.validateAbstractAdviceBindingConfiguration_apply_to_all_types(getFixture(), null, null));
	}

} //AbstractAdviceBindingConfigurationTest
