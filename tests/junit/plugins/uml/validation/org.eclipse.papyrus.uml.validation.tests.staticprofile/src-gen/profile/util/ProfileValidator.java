/**
 */
package profile.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import profile.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see profile.ProfilePackage
 * @generated
 */
public class ProfileValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ProfileValidator INSTANCE = new ProfileValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "profile";

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Constraint Always Active' of 'Always Active'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ALWAYS_ACTIVE__CONSTRAINT_ALWAYS_ACTIVE = 1;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Constraint Always Active MF' of 'Always Active Malformed'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ALWAYS_ACTIVE_MALFORMED__CONSTRAINT_ALWAYS_ACTIVE_MF = 2;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Always Passive' of 'Always Passive Java'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ALWAYS_PASSIVE_JAVA__ALWAYS_PASSIVE = 3;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 3;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProfileValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return ProfilePackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case ProfilePackage.ALWAYS_ACTIVE:
				return validateAlwaysActive((AlwaysActive)value, diagnostics, context);
			case ProfilePackage.TST:
				return validateTst((Tst)value, diagnostics, context);
			case ProfilePackage.ALWAYS_ACTIVE_MALFORMED:
				return validateAlwaysActiveMalformed((AlwaysActiveMalformed)value, diagnostics, context);
			case ProfilePackage.ALWAYS_PASSIVE_JAVA:
				return validateAlwaysPassiveJava((AlwaysPassiveJava)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlwaysActive(AlwaysActive alwaysActive, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(alwaysActive, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(alwaysActive, diagnostics, context);
		if (result || diagnostics != null) result &= validateAlwaysActive_ConstraintAlwaysActive(alwaysActive, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ConstraintAlwaysActive constraint of '<em>Always Active</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlwaysActive_ConstraintAlwaysActive(AlwaysActive alwaysActive, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return alwaysActive.ConstraintAlwaysActive(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTst(Tst tst, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(tst, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlwaysActiveMalformed(AlwaysActiveMalformed alwaysActiveMalformed, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(alwaysActiveMalformed, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(alwaysActiveMalformed, diagnostics, context);
		if (result || diagnostics != null) result &= validateAlwaysActiveMalformed_ConstraintAlwaysActiveMF(alwaysActiveMalformed, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ConstraintAlwaysActiveMF constraint of '<em>Always Active Malformed</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlwaysActiveMalformed_ConstraintAlwaysActiveMF(AlwaysActiveMalformed alwaysActiveMalformed, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return alwaysActiveMalformed.ConstraintAlwaysActiveMF(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlwaysPassiveJava(AlwaysPassiveJava alwaysPassiveJava, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(alwaysPassiveJava, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(alwaysPassiveJava, diagnostics, context);
		if (result || diagnostics != null) result &= validateAlwaysPassiveJava_AlwaysPassive(alwaysPassiveJava, diagnostics, context);
		return result;
	}

	/**
	 * Validates the AlwaysPassive constraint of '<em>Always Passive Java</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlwaysPassiveJava_AlwaysPassive(AlwaysPassiveJava alwaysPassiveJava, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return alwaysPassiveJava.AlwaysPassive(diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //ProfileValidator
