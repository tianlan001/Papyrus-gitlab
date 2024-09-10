/**
 */
package profile;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see profile.ProfilePackage
 * @generated
 */
public interface ProfileFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProfileFactory eINSTANCE = profile.impl.ProfileFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Always Active</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Always Active</em>'.
	 * @generated
	 */
	AlwaysActive createAlwaysActive();

	/**
	 * Returns a new object of class '<em>Tst</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tst</em>'.
	 * @generated
	 */
	Tst createTst();

	/**
	 * Returns a new object of class '<em>Always Active Malformed</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Always Active Malformed</em>'.
	 * @generated
	 */
	AlwaysActiveMalformed createAlwaysActiveMalformed();

	/**
	 * Returns a new object of class '<em>Always Passive Java</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Always Passive Java</em>'.
	 * @generated
	 */
	AlwaysPassiveJava createAlwaysPassiveJava();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProfilePackage getProfilePackage();

} //ProfileFactory
