/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.architecture.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.impl.ArchitecturePlugin;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.tools.util.ClasspathHelper;
import org.eclipse.papyrus.infra.tools.util.Try;

/**
 * Utilities for working with/resolving the command classes referenced by
 * <em>Architecture Models</em>.
 *
 * @since 3.1
 */
public class ArchitectureCommandUtils {

	private static final String REQUIRED_BUNDLES = "requiredBundles"; //$NON-NLS-1$
	private static final String CLASS_CONSTRAINT = "classConstraint"; //$NON-NLS-1$

	private static final Map<EStructuralFeature, Class<?>> commandClassConstraints = new ConcurrentHashMap<>();

	/**
	 * Obtain the type constraining command classes referenced by the given feature.
	 *
	 * @param commandClassFeature
	 *            the model feature that names a command class
	 * @return the type to which command classes named by the feature must conform
	 */
	public static Optional<Class<?>> getCommandType(EStructuralFeature commandClassFeature) {
		return Optional.ofNullable(commandClassConstraints.computeIfAbsent(commandClassFeature, ArchitectureCommandUtils::loadClassAnnotation));
	}

	private static Class<?> loadClassAnnotation(EStructuralFeature feature) {
		Class<?> result = null;

		String classURI = EcoreUtil.getAnnotation(feature, ArchitecturePackage.eNS_URI, CLASS_CONSTRAINT);
		if (classURI != null) {
			URI uri = URI.createURI(classURI);
			result = ClassLoaderHelper.loadClass(uri).orElseApply(ArchitectureCommandUtils::fail);
		}

		return result;
	}

	private static String getBundleFromClassAnnotation(EObject owner, EStructuralFeature feature) {
		String result = null;

		if (owner.eIsSet(feature)) {
			String classURI = EcoreUtil.getAnnotation(feature, ArchitecturePackage.eNS_URI, CLASS_CONSTRAINT);
			URI uri = classURI == null ? null : URI.createURI(classURI);
			Try<String> bundleName = ClassLoaderHelper.getBundleName(uri);
			result = bundleName.orElse(null);
		}

		return result;
	}

	private static String getRequiredBundlesAnnotation(EObject owner, EStructuralFeature feature) {
		String result = null;

		if (owner.eIsSet(feature)) {
			result = EcoreUtil.getAnnotation(feature, ArchitecturePackage.eNS_URI, REQUIRED_BUNDLES);
		}

		return result;
	}

	private static Class<?> fail(String message) {
		return fail(new IllegalArgumentException(message));
	}

	private static Class<?> fail(Throwable exception) {
		ArchitecturePlugin.INSTANCE.log(exception);
		return Void.class; // Bomb the usage of this class-name feature
	}

	/**
	 * Get the command class referenced by the given feature of an architecture model object, if it
	 * conforms to constraints (if any) declared in the Ecore model.
	 *
	 * @param modelObject
	 *            the architecture model object for which to load a command class
	 * @param commandClassFeature
	 *            the model feature that names the command class
	 * @return
	 *         the referenced class, or {@code null} if the class doesn't exist or is invalid
	 *         (such as not conforming to its constraining type). In such a case, the
	 *         exception is logged. The result may be a Java {@link Class} or a JDT
	 *         {@code IType}, depending whether JDT is available
	 */
	public static Object getCommandClass(EObject modelObject, EStructuralFeature commandClassFeature) {
		return getCommandClass(modelObject, commandClassFeature, getCommandType(commandClassFeature));
	}

	private static Object getCommandClass(EObject modelObject, EStructuralFeature commandClassFeature, Optional<Class<?>> registeredType) {
		if (commandClassFeature.getEType().getInstanceClass() == Class.class) {
			// Easy
			Class<?> result = (Class<?>) modelObject.eGet(commandClassFeature);
			if (result != null && registeredType.isPresent() && !registeredType.get().isAssignableFrom(result)) {
				result = null;
			}
			return result;
		}

		String className = Optional.ofNullable(modelObject.eGet(commandClassFeature)).map(String::valueOf).orElse(null);
		if (className == null) {
			// Easy
			return null;
		}

		URI context = getSourceURI(modelObject, commandClassFeature).trimFragment();

		return ClasspathHelper.INSTANCE.findClass(className, context, registeredType.orElse(null));
	}

	/**
	 * Get the URI of the object that is the source (via architecture model merge) of the value of the given
	 * command class feature.
	 *
	 * @param object
	 *            an architecture model element
	 * @param commandClassFeature
	 *            the command class feature to trace to the source
	 * @return the URI of the source object
	 */
	private static URI getSourceURI(EObject object, EStructuralFeature commandClassFeature) {
		URI result;

		if (!(object instanceof ADElement)) {
			// It wasn't merged, so it is its own source object (referenced directly wherever it is)
			result = EcoreUtil.getURI(object);
		} else {
			ADElement element = (ADElement) object;
			MergeTraceAdapter traces = MergeTraceAdapter.getMergeTraces(element);

			ADElement source = traces == null ? element : traces.trace(element, commandClassFeature);

			result = source == null
					? EcoreUtil.getURI(element) // Hope for the best
					: EcoreUtil.getURI(source);
		}

		return result;
	}

	/**
	 * Get the command class referenced by the given feature of an architecture model object, not considering
	 * any constraints imposed on it by the Ecore model.
	 *
	 * @param modelObject
	 *            the architecture model object for which to load a command class
	 * @param commandClassFeature
	 *            the model feature that names the command class
	 * @return
	 *         the referenced class, or {@code null} if the class doesn't exist. The result may be a Java
	 *         {@link Class} or a JDT {@code IType}, depending whether JDT is available
	 */
	public static Object getCommandClassUnconstrained(EObject modelObject, EStructuralFeature commandClassFeature) {
		return getCommandClass(modelObject, commandClassFeature, Optional.empty());
	}

	/**
	 * Search the content tree of a model object for bundle dependency requirements implied by features referencing
	 * command classes.
	 *
	 * @param modelObject
	 *            a model tree
	 * @return the bundle dependencies implied by command class features set in the content of the tree
	 */
	public static Set<String> getRequiredCommandBundleDependencies(EObject modelObject) {
		Set<String> result = new HashSet<>();

		Map<EClass, List<EStructuralFeature>> commandClassFeatures = new HashMap<>();

		for (Iterator<EObject> iter = EcoreUtil.getAllContents(Set.of(modelObject)); iter.hasNext();) {
			EObject next = iter.next();
			for (EStructuralFeature feature : getCommandClassFeatures(next.eClass(), commandClassFeatures)) {
				String bundleDependencyByClass = getBundleFromClassAnnotation(next, feature);
				if (bundleDependencyByClass != null) {
					result.add(bundleDependencyByClass);
				}

				String otherDependencies = getRequiredBundlesAnnotation(next, feature);
				if (otherDependencies != null && !otherDependencies.isBlank()) {
					Stream.of(otherDependencies.split("\\s*,\\s*")).forEach(result::add); //$NON-NLS-1$
				}
			}
		}

		return result;
	}

	/**
	 * Get the features of a model class that are constrained command class features.
	 *
	 * @param eClass
	 *            an EClass
	 * @param commandClassFeatures
	 *            a cache of previously computed constrained command class features
	 * @return the class's constrained command-class features
	 */
	private static Iterable<EStructuralFeature> getCommandClassFeatures(EClass eClass, Map<EClass, List<EStructuralFeature>> commandClassFeatures) {
		List<EStructuralFeature> result = commandClassFeatures.get(eClass);

		if (result == null) {
			result = eClass.getEAllStructuralFeatures().stream()
					.filter(f -> EcoreUtil.getAnnotation(f, ArchitecturePackage.eNS_URI, CLASS_CONSTRAINT) != null)
					.collect(Collectors.toList());
			commandClassFeatures.put(eClass, result);
		}

		return result;
	}

}
