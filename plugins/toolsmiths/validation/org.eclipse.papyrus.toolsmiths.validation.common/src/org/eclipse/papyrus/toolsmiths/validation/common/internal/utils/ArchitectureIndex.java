/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.InternalCrossReferencer;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

/**
 * An index of references to tooling models from the {@link ArchitectureDomain} models available
 * in the current context (installation, target, and workspace).
 */
public class ArchitectureIndex extends AbstractIndex {

	private static final ArchitectureIndex INSTANCE = new ArchitectureIndex();

	private final ArchitectureDomainManager domainManager;

	private final Map<Mode, Computation<Multimap<EObject, EStructuralFeature.Setting>>> crossReferences;

	private final Map<EClass, Computation<Multimap<String, ADElement>>> elementsByQualifiedName;

	/**
	 * Not instantiable by clients.
	 */
	private ArchitectureIndex() {
		super();

		domainManager = ArchitectureDomainManager.getInstance();

		crossReferences = new EnumMap<>(Map.of(
				Mode.EXTERNAL_CROSS_REFERENCE, new Computation<>(this::computeExternalCrossReferences),
				Mode.INTERNAL_CROSS_REFERENCE, new Computation<>(this::computeInternalCrossReferences)));
		elementsByQualifiedName = ArchitecturePackage.eINSTANCE.getEClassifiers().stream()
				.filter(EClass.class::isInstance).map(EClass.class::cast)
				.filter(ArchitecturePackage.Literals.AD_ELEMENT::isSuperTypeOf)
				.collect(Collectors.toMap(Function.identity(), eClass -> new Computation<>(() -> computeQualifiedNameMap(eClass))));

		domainManager.addListener(this::domainManagerChanged);
	}

	/**
	 * Get the architecture index.
	 *
	 * @return the architecture index
	 */
	public static ArchitectureIndex getInstance() {
		return INSTANCE;
	}

	/**
	 * Obtain a calculation of all cross-references from registered architecture models to tooling models
	 * (and anything else) not contained within one of those architecture models.
	 *
	 * @param crossReferenceMode
	 *            the cross-reference mode to query
	 * @return the external cross-references of the current architecture domain models
	 */
	public CompletableFuture<Multimap<EObject, EStructuralFeature.Setting>> getCrossReferences(Mode crossReferenceMode) {
		return asyncGet(crossReference(crossReferenceMode));
	}

	private Computation<Multimap<EObject, EStructuralFeature.Setting>> crossReference(Mode mode) {
		return crossReferences.get(mode);
	}

	/**
	 * Obtain a calculation of all cross-references from registered architecture models to tooling models
	 * (and anything else) not contained within one of those architecture models.
	 *
	 * @return the external cross-references of the current architecture domain models
	 */
	public CompletableFuture<Multimap<EObject, EStructuralFeature.Setting>> getExternalCrossReferences() {
		return getCrossReferences(Mode.EXTERNAL_CROSS_REFERENCE);
	}

	private Multimap<EObject, EStructuralFeature.Setting> computeExternalCrossReferences() {
		Set<? extends ADElement> architectureDomains = ArchitectureDomainManager.getInstance().getRegisteredArchitectureDomains().stream()
				.collect(Collectors.toSet());

		ImmutableListMultimap.Builder<EObject, EStructuralFeature.Setting> result = ImmutableListMultimap.builder();
		for (Map.Entry<EObject, Collection<EStructuralFeature.Setting>> next : EcoreUtil.ExternalCrossReferencer.find(architectureDomains).entrySet()) {
			result.putAll(next.getKey(), next.getValue());
		}

		return result.build();
	}

	/**
	 * Obtain a calculation of all cross-references from objects in registered architecture models to other objects
	 * contained within one of those architecture models.
	 *
	 * @return the internal cross-references of the current architecture domain models
	 */
	public CompletableFuture<Multimap<EObject, EStructuralFeature.Setting>> getInternalCrossReferences() {
		return getCrossReferences(Mode.INTERNAL_CROSS_REFERENCE);
	}

	private Multimap<EObject, EStructuralFeature.Setting> computeInternalCrossReferences() {
		Set<? extends ADElement> architectureDomains = ArchitectureDomainManager.getInstance().getRegisteredArchitectureDomains().stream()
				.collect(Collectors.toSet());

		ImmutableListMultimap.Builder<EObject, EStructuralFeature.Setting> result = ImmutableListMultimap.builder();
		for (Map.Entry<EObject, Collection<EStructuralFeature.Setting>> next : InternalCrossReferencer.find(architectureDomains).entrySet()) {
			result.putAll(next.getKey(), next.getValue());
		}

		return result.build();
	}

	/**
	 * Cancel any pending index calculations; forget all index calculations.
	 */
	private void domainManagerChanged() {
		resetComputations();
	}

	/**
	 * Query whether any object in the given {@code resource} is referenced by some registered <em>architecture model</em>.
	 *
	 * @param crossReferenceMode
	 *            the cross-reference mode to query
	 * @param resource
	 *            a resource
	 * @return whether it is referenced by a registered architecture model
	 */
	public boolean isReferenced(Mode crossReferenceMode, Resource resource) {
		return isReferenced(crossReferenceMode, resource, null);
	}

	/**
	 * Query whether any object in the given {@code resource} is referenced by some registered <em>architecture model</em>
	 * via the given {@code reference}. Objects cross-referenced by other references are not considered.
	 *
	 * @param crossReferenceMode
	 *            the cross-reference mode to query
	 * @param resource
	 *            a resource
	 * @param reference
	 *            the reference to consider by which objects in the {@code resource} may be referenced
	 * @return whether any object in the {@code resource} is referenced by a registered architecture model via the {@code reference}
	 */
	public boolean isReferenced(Mode crossReferenceMode, Resource resource, EReference reference) {
		return Optional.ofNullable(resource).map(Resource::getResourceSet)
				.map(context -> isReferenced(crossReferenceMode, resource.getURI(), reference, context))
				.orElse(false);
	}

	/**
	 * Query whether the given {@code object} is referenced by some registered <em>architecture model</em>.
	 * The cross-reference mode is inferred from the type of {@code object}, which either is an <em>architecture
	 * model</em> element, implying internal cross-reference search, or not, implying external cross-references.
	 *
	 * @param object
	 *            a model object
	 * @return whether it is referenced by a registered architecture model
	 */
	public boolean isReferenced(EObject object) {
		return isReferenced(object, null);
	}

	/**
	 * Query whether the given {@code object} is referenced by some registered <em>architecture model</em>
	 * via the given {@code reference}. Objects cross-referenced by other references are not considered.
	 * The cross-reference mode is inferred from the type of {@code object}, which either is an <em>architecture
	 * model</em> element, implying internal cross-reference search, or not, implying external cross-references.
	 *
	 * @param object
	 *            a model object
	 * @param reference
	 *            the reference to consider by which objects may be referenced
	 * @return whether the {@code object} is referenced by a registered architecture model via the {@code reference}
	 */
	public boolean isReferenced(EObject object, EReference reference) {
		return Optional.ofNullable(object).map(EObject::eResource).map(Resource::getResourceSet)
				.map(context -> isReferenced(inferCrossReferenceMode(object), EcoreUtil.getURI(object), reference, context))
				.orElse(false);
	}

	/**
	 * Infer the cross-reference mode to query for an {@code object}. The mode will be inferred as
	 * {@linkplain Mode#INTERNAL_CROSS_REFERENCE internal} if the {@code object} is in an architecture
	 * domain; {@linkplain Mode#EXTERNAL_CROSS_REFERENCE external}, otherwise.
	 *
	 * @param object
	 *            an object for which to query cross-references
	 * @return the inferred cross-reference query mode
	 */
	private static Mode inferCrossReferenceMode(EObject object) {
		return (object instanceof ADElement)
				? Mode.INTERNAL_CROSS_REFERENCE
				: object != null && object.eContainer() != null
						? inferCrossReferenceMode(object.eContainer())
						: Mode.EXTERNAL_CROSS_REFERENCE;
	}

	/**
	 * Query whether any registered <em>architecture model</em> has an HREF matching the given {@code uri}.
	 *
	 * @param crossReferenceMode
	 *            the cross-reference mode to query
	 * @param uri
	 *            an URI, which may be a resource URI or an object URI ({@linkplain URI#hasFragment() with fragment})
	 * @param context
	 *            the resource set in which to resolve/convert URIs for stable comparison
	 * @return whether any registered architecture has an HREF matching the given URI
	 */
	public boolean isReferenced(Mode crossReferenceMode, URI uri, ResourceSet context) {
		return isReferenced(crossReferenceMode, uri, null, context);
	}

	/**
	 * Query whether any registered <em>architecture model</em> has an HREF matching the given {@code uri}
	 * in the given {@code reference}. HREFs in other references are not considered.
	 *
	 * @param crossReferenceMode
	 *            the cross-reference mode to query
	 * @param uri
	 *            an URI, which may be a resource URI or an object URI ({@linkplain URI#hasFragment() with fragment})
	 * @param reference
	 *            the reference to consider in looking for HREFs
	 * @param context
	 *            the resource set in which to resolve/convert URIs for stable comparison
	 * @return whether any registered architecture has an HREF matching the given URI in the {@code reference}
	 */
	public boolean isReferenced(Mode crossReferenceMode, URI uri, EReference reference, ResourceSet context) {
		return transform(crossReference(crossReferenceMode), ImmutableMultimap.of(),
				isReferencedFunction(crossReferenceMode, uri, reference, context));
	}

	/**
	 * Asynchronously query whether any registered <em>architecture model</em> has an HREF matching the given {@code uri}
	 * in the given {@code reference}. HREFs in other references are not considered.
	 *
	 * @param crossReferenceMode
	 *            the cross-reference mode to query
	 * @param uri
	 *            an URI, which may be a resource URI or an object URI ({@linkplain URI#hasFragment() with fragment})
	 * @param reference
	 *            the reference to consider in looking for HREFs
	 * @param context
	 *            the resource set in which to resolve/convert URIs for stable comparison
	 * @return whether any registered architecture has an HREF matching the given URI in the {@code reference}
	 */
	public CompletableFuture<Boolean> isReferencedAsync(Mode crossReferenceMode, URI uri, EReference reference, ResourceSet context) {
		return asyncTransform(crossReference(crossReferenceMode), isReferencedFunction(crossReferenceMode, uri, reference, context));
	}

	private Function<Multimap<EObject, EStructuralFeature.Setting>, Boolean> isReferencedFunction(Mode crossReferenceMode, URI uri, EReference reference, ResourceSet context) {
		URIConverter converter = context.getURIConverter();
		Function<URI, URI> uriTrimmer = uri.hasFragment() ? Function.identity() : URI::trimFragment;
		Predicate<Map.Entry<EObject, EStructuralFeature.Setting>> referenceFilter = (reference == null)
				? __ -> true
				: entry -> entry.getValue().getEStructuralFeature() == reference;

		// The architecture models are loaded in their own resource set, so look for our model by URI
		Predicate<Multimap<EObject, EStructuralFeature.Setting>> isReferenced = xrefs -> xrefs
				.entries().stream()
				.filter(referenceFilter)
				.map(Map.Entry::getKey)
				.map(EcoreUtil::getURI)
				.map(uriTrimmer)
				.map(converter::normalize)
				.anyMatch(uri::equals);

		return isReferenced::test;
	}

	/**
	 * Obtain a mapping of instances of the given {@link EClass} by name.
	 *
	 * @param <T>
	 *            the type of elements requested, according to the given {@link EClass}
	 * @param eClass
	 *            the {@link EClass} of elements for which to get the name map. It should conform to
	 *            {@link ArchitecturePackage.Literals#AD_ELEMENT ADElement} to be useful
	 * @return the name map, which may be empty, especially in the case that the requested {@link EClass} is of an inapplicable type
	 *
	 * @see #getElementsByQualifiedNameAsync(EClass, String)
	 * @see #getElementsByName(EClass, String)
	 */
	public <T extends ADElement> CompletableFuture<Multimap<String, T>> getElementsByQualifiedName(EClass eClass) {
		if (ArchitecturePackage.Literals.AD_ELEMENT.isSuperTypeOf(eClass)) {
			// The actual EClass may not be one from the Architecture Package that we have enumerated
			eClass = findArchitectureEClass(eClass);
			if (eClass != null) {
				// The maps computed are immutable, so the cast is safe because we know a priori that all
				// elements of the map conform and none can be added
				@SuppressWarnings({ "unchecked", "rawtypes" })
				CompletableFuture<Multimap<String, T>> result = (CompletableFuture) asyncGet(elementsByQualifiedName.get(eClass));
				return result;
			}
		}

		return CompletableFuture.completedFuture(ImmutableMultimap.of());
	}

	private EClass findArchitectureEClass(EClass eClass) {
		if (eClass.getEPackage() == ArchitecturePackage.eINSTANCE) {
			return eClass;
		}
		for (EClass next : eClass.getEAllSuperTypes()) {
			if (next.getEPackage() == ArchitecturePackage.eINSTANCE) {
				return next;
			}
		}
		return null;
	}

	/**
	 * Obtain the instances of the given {@link EClass} having some qualified {@code name}.
	 *
	 * @param <T>
	 *            the type of elements requested, according to the given {@link EClass}
	 * @param eClass
	 *            the {@link EClass} of elements to query. It should conform to
	 *            {@link ArchitecturePackage.Literals#AD_ELEMENT ADElement} to be useful
	 * @param name
	 *            the qualified name to search for
	 * @return the elements having the given qualified {@code name}
	 *
	 * @see #getElementsByName(EClass)
	 * @see #getElementsByQualifiedNameAsync(EClass, String)
	 */
	public <T extends ADElement> Collection<T> getElementsByQualifiedName(EClass eClass, String name) {
		Collection<T> result;

		try {
			result = this.<T> getElementsByQualifiedNameAsync(eClass, name).get();
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the architecture index? Then we didn't find anything
			Activator.log.error("Error querying Architecture Context models.", e); //$NON-NLS-1$
			result = List.of();
		}

		return result;
	}

	/**
	 * Obtain the instances of the given {@link EClass} having some qualified {@code name}.
	 *
	 * @param <T>
	 *            the type of elements requested, according to the given {@link EClass}
	 * @param eClass
	 *            the {@link EClass} of elements to query. It should conform to
	 *            {@link ArchitecturePackage.Literals#AD_ELEMENT ADElement} to be useful
	 * @param name
	 *            the qualified name to search for
	 * @return the elements having the given qualified {@code name}
	 *
	 * @see #getElementsByName(EClass)
	 * @see #getElementsByQualifiedName(EClass, String)
	 */
	public <T extends ADElement> CompletableFuture<Collection<T>> getElementsByQualifiedNameAsync(EClass eClass, String name) {
		return this.<T> getElementsByQualifiedName(eClass).thenApply(map -> map.get(name));
	}

	private Multimap<String, ADElement> computeQualifiedNameMap(EClass eClass) {
		ImmutableListMultimap.Builder<String, ADElement> result = ImmutableListMultimap.builder();

		EcoreUtil.getAllContents(ArchitectureDomainManager.getInstance().getRegisteredArchitectureDomains()).forEachRemaining(object -> {
			if (eClass.isInstance(object)) {
				ADElement element = (ADElement) object;
				result.put(element.getQualifiedName(), element);
			}
		});

		return result.build();
	}

	public Collection<ArchitectureContext> getAllExtensions(ArchitectureContext context) {
		try {
			return getAllExtensionsAsync(context).get();
		} catch (InterruptedException | ExecutionException e) {
			Activator.log.error("Error querying Architecture Context models.", e); //$NON-NLS-1$
			return List.of();
		}
	}

	public CompletableFuture<Collection<ArchitectureContext>> getAllExtensionsAsync(ArchitectureContext context) {
		return getInternalCrossReferences().thenApply(xrefs -> {
			Collection<ArchitectureContext> result = new LinkedHashSet<>();
			Queue<ArchitectureContext> queue = new ArrayDeque<>(getExtensions(context, xrefs));

			for (ArchitectureContext next = queue.poll(); next != null; next = queue.poll()) {
				if (result.add(next)) {
					queue.addAll(getExtensions(next, xrefs));
				}
			}

			return result;
		});
	}

	private Collection<ArchitectureContext> getExtensions(ArchitectureContext context, Multimap<EObject, EStructuralFeature.Setting> xrefs) {
		UserSpaceMapping mapping = new UserSpaceMapping(context);

		return xrefs.get(mapping.toIndexSpace(context)).stream()
				.filter(setting -> setting.getEStructuralFeature() == ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS)
				.map(EStructuralFeature.Setting::getEObject)
				.map(ArchitectureContext.class::cast)
				.distinct()
				.map(mapping::toUserSpace)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	//
	// Nested types
	//

	/**
	 * Cross-reference indexing/searching modes.
	 */
	public static enum Mode {
		/** Search cross-references from architecture models to other tooling models. */
		EXTERNAL_CROSS_REFERENCE,
		/** Search cross-references wihin and between architecture models only. */
		INTERNAL_CROSS_REFERENCE;
	}

	/**
	 * A bijection of objects in user space and index space, being the resource set of the client
	 * component and the resource set of the index, respectively.
	 */
	private final class UserSpaceMapping {
		private final ResourceSet userContext;
		private final ResourceSet indexContext;

		UserSpaceMapping(EObject userContext) {
			super();

			this.userContext = EMFHelper.getResourceSet(userContext);
			this.indexContext = domainManager.getRegisteredArchitectureDomains().stream()
					.map(EMFHelper::getResourceSet)
					.filter(Objects::nonNull)
					.findAny()
					.orElse(null);
		}

		@SuppressWarnings("unchecked")
		<T extends EObject> T toUserSpace(T indexObject) {
			return (T) userContext.getEObject(EcoreUtil.getURI(indexObject), true);
		}

		@SuppressWarnings("unchecked")
		<T extends EObject> T toIndexSpace(T userObject) {
			return (indexContext == null) ? null : (T) indexContext.getEObject(EcoreUtil.getURI(userObject), true);
		}

	}


}
