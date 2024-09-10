/**
 */
package org.eclipse.papyrus.infra.emf.types.ui.advices.values;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage
 * @generated
 */
public interface RuntimeValuesAdviceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuntimeValuesAdviceFactory eINSTANCE = org.eclipse.papyrus.infra.emf.types.ui.advices.values.impl.RuntimeValuesAdviceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configuration</em>'.
	 * @generated
	 */
	RuntimeValuesAdviceConfiguration createRuntimeValuesAdviceConfiguration();

	/**
	 * Returns a new object of class '<em>View To Display</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>View To Display</em>'.
	 * @generated
	 */
	ViewToDisplay createViewToDisplay();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RuntimeValuesAdvicePackage getRuntimeValuesAdvicePackage();

} //RuntimeValuesAdviceFactory
