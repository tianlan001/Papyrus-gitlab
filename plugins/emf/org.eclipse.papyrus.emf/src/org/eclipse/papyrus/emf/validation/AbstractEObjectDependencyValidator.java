/*****************************************************************************
 * Copyright (c) 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 568782
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.validation;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.papyrus.emf.helpers.BundleResourceURIHelper;
import org.eclipse.papyrus.emf.helpers.ProjectDependencyHelper;


/**
 * Abstract validator for dependencies of an Ecore model
 */
public abstract class AbstractEObjectDependencyValidator extends EObjectValidator {

	/**
	 * helper used to get bundle name from a {@link Resource}
	 */
	protected static final BundleResourceURIHelper RESOURCE_URI_HELPER = BundleResourceURIHelper.INSTANCE;

	/**
	 * helper used to know if a project is dependant of another one
	 */
	protected static final ProjectDependencyHelper PROJECT_DEPENDENCY_HELPER = ProjectDependencyHelper.INSTANCE;

	/**
	 * The package's own registered validator, if any.
	 */
	private EValidator delegate;

	/**
	 * Initializes me without a delegate.
	 */
	public AbstractEObjectDependencyValidator() {
		this((EValidator) null);
	}

	/**
	 * Initializes me with a delegate for validation of intrinsic model constraints.
	 *
	 * @param delegate
	 *            my delegate
	 */
	public AbstractEObjectDependencyValidator(EValidator delegate) {
		super();

		this.delegate = delegate;
	}

	public AbstractEObjectDependencyValidator(EPackage ePackage) {
		this(EValidator.Registry.INSTANCE.getEValidator(ePackage));
	}

	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (delegate == null) {
			return super.validate(eClass, eObject, diagnostics, context);
		}

		boolean result = delegate.validate(eClass, eObject, diagnostics, context);

		if ((result || diagnostics != null) && !eObject.eIsProxy() && (eClass.eContainer() == getEPackage())) {
			// In case a subclass overrides this method. Dynamic EObjects and intrinsic model constraints were handled by the delegate
			result = validate(eClass.getClassifierID(), eObject, diagnostics, context) && result;
		}

		return result;
	}

	@Override
	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (delegate == null) {
			return super.validate(eDataType, value, diagnostics, context);
		}

		boolean result = delegate.validate(eDataType, value, diagnostics, context);

		if ((result || diagnostics != null) && eDataType.isInstance(value) && (eDataType.eContainer() == getEPackage())) {
			// In case a subclass overrides this method. Dynamic values and intrinsic model constraints were handled by the delegate
			result = validate(eDataType.getClassifierID(), value, diagnostics, context) && result;
		}

		return result;
	}

	@Override
	public boolean validate_EveryDefaultConstraint(EObject object, DiagnosticChain theDiagnostics, Map<Object, Object> context) {
		// If we delegated validation, then that took care of the default constraints
		return delegate != null || super.validate_EveryDefaultConstraint(object, theDiagnostics, context);
	}

	/**
	 *
	 * @param eobjectInError
	 *            the eobject in error
	 * @param feature
	 *            the tested feature
	 * @param missingDependencies
	 *            the list of missing dependencies
	 * @return
	 *         the created diagnostic
	 */
	protected final Diagnostic createMissingDependenciesDiagnostic(final EObject eobjectInError, final EStructuralFeature feature, final Collection<String> missingDependencies) {
		return DependencyValidationUtils.createMissingDependenciesDiagnostic(eobjectInError, feature, missingDependencies);
	}

}
