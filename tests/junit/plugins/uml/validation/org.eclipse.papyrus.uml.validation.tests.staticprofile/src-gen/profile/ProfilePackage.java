/**
 */
package profile;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see profile.ProfileFactory
 * @model kind="package"
 * @generated
 */
public interface ProfilePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "profile";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.papyrus/activetest/1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "profile";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProfilePackage eINSTANCE = profile.impl.ProfilePackageImpl.init();

	/**
	 * The meta object id for the '{@link profile.impl.AlwaysActiveImpl <em>Always Active</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see profile.impl.AlwaysActiveImpl
	 * @see profile.impl.ProfilePackageImpl#getAlwaysActive()
	 * @generated
	 */
	int ALWAYS_ACTIVE = 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE__BASE_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Always Active</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Constraint Always Active</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE___CONSTRAINT_ALWAYS_ACTIVE__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The number of operations of the '<em>Always Active</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link profile.impl.TstImpl <em>Tst</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see profile.impl.TstImpl
	 * @see profile.impl.ProfilePackageImpl#getTst()
	 * @generated
	 */
	int TST = 1;

	/**
	 * The feature id for the '<em><b>Base Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TST__BASE_PACKAGE = 0;

	/**
	 * The number of structural features of the '<em>Tst</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TST_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Tst</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link profile.impl.AlwaysActiveMalformedImpl <em>Always Active Malformed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see profile.impl.AlwaysActiveMalformedImpl
	 * @see profile.impl.ProfilePackageImpl#getAlwaysActiveMalformed()
	 * @generated
	 */
	int ALWAYS_ACTIVE_MALFORMED = 2;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE_MALFORMED__BASE_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Always Active Malformed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE_MALFORMED_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Constraint Always Active MF</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE_MALFORMED___CONSTRAINT_ALWAYS_ACTIVE_MF__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The number of operations of the '<em>Always Active Malformed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_ACTIVE_MALFORMED_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link profile.impl.AlwaysPassiveJavaImpl <em>Always Passive Java</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see profile.impl.AlwaysPassiveJavaImpl
	 * @see profile.impl.ProfilePackageImpl#getAlwaysPassiveJava()
	 * @generated
	 */
	int ALWAYS_PASSIVE_JAVA = 3;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_PASSIVE_JAVA__BASE_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Always Passive Java</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_PASSIVE_JAVA_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Always Passive</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_PASSIVE_JAVA___ALWAYS_PASSIVE__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The number of operations of the '<em>Always Passive Java</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALWAYS_PASSIVE_JAVA_OPERATION_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link profile.AlwaysActive <em>Always Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Always Active</em>'.
	 * @see profile.AlwaysActive
	 * @generated
	 */
	EClass getAlwaysActive();

	/**
	 * Returns the meta object for the reference '{@link profile.AlwaysActive#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see profile.AlwaysActive#getBase_Class()
	 * @see #getAlwaysActive()
	 * @generated
	 */
	EReference getAlwaysActive_Base_Class();

	/**
	 * Returns the meta object for the '{@link profile.AlwaysActive#ConstraintAlwaysActive(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Constraint Always Active</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Constraint Always Active</em>' operation.
	 * @see profile.AlwaysActive#ConstraintAlwaysActive(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getAlwaysActive__ConstraintAlwaysActive__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link profile.Tst <em>Tst</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tst</em>'.
	 * @see profile.Tst
	 * @generated
	 */
	EClass getTst();

	/**
	 * Returns the meta object for the reference '{@link profile.Tst#getBase_Package <em>Base Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Package</em>'.
	 * @see profile.Tst#getBase_Package()
	 * @see #getTst()
	 * @generated
	 */
	EReference getTst_Base_Package();

	/**
	 * Returns the meta object for class '{@link profile.AlwaysActiveMalformed <em>Always Active Malformed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Always Active Malformed</em>'.
	 * @see profile.AlwaysActiveMalformed
	 * @generated
	 */
	EClass getAlwaysActiveMalformed();

	/**
	 * Returns the meta object for the reference '{@link profile.AlwaysActiveMalformed#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see profile.AlwaysActiveMalformed#getBase_Class()
	 * @see #getAlwaysActiveMalformed()
	 * @generated
	 */
	EReference getAlwaysActiveMalformed_Base_Class();

	/**
	 * Returns the meta object for the '{@link profile.AlwaysActiveMalformed#ConstraintAlwaysActiveMF(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Constraint Always Active MF</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Constraint Always Active MF</em>' operation.
	 * @see profile.AlwaysActiveMalformed#ConstraintAlwaysActiveMF(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getAlwaysActiveMalformed__ConstraintAlwaysActiveMF__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link profile.AlwaysPassiveJava <em>Always Passive Java</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Always Passive Java</em>'.
	 * @see profile.AlwaysPassiveJava
	 * @generated
	 */
	EClass getAlwaysPassiveJava();

	/**
	 * Returns the meta object for the reference '{@link profile.AlwaysPassiveJava#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see profile.AlwaysPassiveJava#getBase_Class()
	 * @see #getAlwaysPassiveJava()
	 * @generated
	 */
	EReference getAlwaysPassiveJava_Base_Class();

	/**
	 * Returns the meta object for the '{@link profile.AlwaysPassiveJava#AlwaysPassive(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Always Passive</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Always Passive</em>' operation.
	 * @see profile.AlwaysPassiveJava#AlwaysPassive(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getAlwaysPassiveJava__AlwaysPassive__DiagnosticChain_Map();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProfileFactory getProfileFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link profile.impl.AlwaysActiveImpl <em>Always Active</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see profile.impl.AlwaysActiveImpl
		 * @see profile.impl.ProfilePackageImpl#getAlwaysActive()
		 * @generated
		 */
		EClass ALWAYS_ACTIVE = eINSTANCE.getAlwaysActive();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALWAYS_ACTIVE__BASE_CLASS = eINSTANCE.getAlwaysActive_Base_Class();

		/**
		 * The meta object literal for the '<em><b>Constraint Always Active</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALWAYS_ACTIVE___CONSTRAINT_ALWAYS_ACTIVE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getAlwaysActive__ConstraintAlwaysActive__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link profile.impl.TstImpl <em>Tst</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see profile.impl.TstImpl
		 * @see profile.impl.ProfilePackageImpl#getTst()
		 * @generated
		 */
		EClass TST = eINSTANCE.getTst();

		/**
		 * The meta object literal for the '<em><b>Base Package</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TST__BASE_PACKAGE = eINSTANCE.getTst_Base_Package();

		/**
		 * The meta object literal for the '{@link profile.impl.AlwaysActiveMalformedImpl <em>Always Active Malformed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see profile.impl.AlwaysActiveMalformedImpl
		 * @see profile.impl.ProfilePackageImpl#getAlwaysActiveMalformed()
		 * @generated
		 */
		EClass ALWAYS_ACTIVE_MALFORMED = eINSTANCE.getAlwaysActiveMalformed();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALWAYS_ACTIVE_MALFORMED__BASE_CLASS = eINSTANCE.getAlwaysActiveMalformed_Base_Class();

		/**
		 * The meta object literal for the '<em><b>Constraint Always Active MF</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALWAYS_ACTIVE_MALFORMED___CONSTRAINT_ALWAYS_ACTIVE_MF__DIAGNOSTICCHAIN_MAP = eINSTANCE.getAlwaysActiveMalformed__ConstraintAlwaysActiveMF__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link profile.impl.AlwaysPassiveJavaImpl <em>Always Passive Java</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see profile.impl.AlwaysPassiveJavaImpl
		 * @see profile.impl.ProfilePackageImpl#getAlwaysPassiveJava()
		 * @generated
		 */
		EClass ALWAYS_PASSIVE_JAVA = eINSTANCE.getAlwaysPassiveJava();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALWAYS_PASSIVE_JAVA__BASE_CLASS = eINSTANCE.getAlwaysPassiveJava_Base_Class();

		/**
		 * The meta object literal for the '<em><b>Always Passive</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALWAYS_PASSIVE_JAVA___ALWAYS_PASSIVE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getAlwaysPassiveJava__AlwaysPassive__DiagnosticChain_Map();

	}

} //ProfilePackage
