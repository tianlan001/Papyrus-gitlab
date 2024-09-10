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

package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.ExternallyRegisteredType;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.types.util.ElementTypesConfigurationsSwitch;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;

/**
 * An index of {@link ElementTypeSetConfiguration} and Ecore models in the workspace.
 */
public class ElementTypeConfigurationsIndex extends AbstractIndex {

	private static final ElementTypeConfigurationsIndex INSTANCE = new ElementTypeConfigurationsIndex();

	private final IContentType elementTypesConfigurationsContentType;
	private final ElementTypesRegistry registry;
	private final Switch<EClass> baseClassSwitch;

	/** Mapping of element type to the EClass it is based on. */
	private final Computation<Map<ElementTypeConfiguration, EClass>> elementTypeBaseClasses;
	/** Mapping of role names to EClass types of those roles. */
	private final Computation<Multimap<String, EClass>> creatableRoles;

	/**
	 * Not instantiable by clients.
	 */
	private ElementTypeConfigurationsIndex() {
		super();

		elementTypesConfigurationsContentType = Platform.getContentTypeManager().getContentType(ElementTypesConfigurationsPackage.eCONTENT_TYPE);
		registry = new ElementTypesRegistry();
		baseClassSwitch = new BaseClassSwitch();
		elementTypeBaseClasses = new Computation<>(this::computeElementTypeBaseClasses);
		creatableRoles = new Computation<>(this::computeCreatableRoles);
	}

	public static ElementTypeConfigurationsIndex getInstance() {
		return INSTANCE;
	}

	private Map<ElementTypeConfiguration, EClass> computeElementTypeBaseClasses() {
		ImmutableMap.Builder<ElementTypeConfiguration, EClass> result = ImmutableMap.builder();
		Set<ElementTypeSetConfiguration> visitedSets = new HashSet<>();

		for (Map<String, ElementTypeSetConfiguration> types : registry.getElementTypeSetConfigurations().values()) {
			for (ElementTypeSetConfiguration set : types.values()) {
				if (visitedSets.add(set)) {
					set.getElementTypeConfigurations().forEach(type -> {
						EClass baseClass = baseClass(type);
						if (baseClass != null) {
							result.put(type, baseClass);
						}
					});
				}
			}
		}

		return result.build();
	}

	EClass baseClass(ElementTypeConfiguration type) {
		return baseClassSwitch.doSwitch(type);
	}

	private Multimap<String, EClass> computeCreatableRoles() {
		ImmutableSetMultimap.Builder<String, EClass> result = ImmutableSetMultimap.builder();
		Set<EClass> visitedClasses = new HashSet<>();

		for (Map<String, ElementTypeSetConfiguration> types : registry.getElementTypeSetConfigurations().values()) {
			for (ElementTypeSetConfiguration set : types.values()) {
				set.getElementTypeConfigurations().forEach(type -> {
					EClass baseClass = baseClass(type);
					if (baseClass != null && visitedClasses.add(baseClass)) {
						getContainmentRoles(baseClass).forEach(ref -> result.put(ref.getName(), ref.getEReferenceType()));
					}
				});
			}
		}

		return result.build();
	}

	private Stream<EReference> getContainmentRoles(EClass eClass) {
		return eClass.getEAllReferences().stream().filter(this::isContainment);
	}

	/**
	 * Query whether a {@code reference} is either an actual containment reference or a UML subset of
	 * a containment reference.
	 *
	 * @param reference
	 *            an Ecore reference
	 * @return whether it is some kind of containment reference
	 */
	private boolean isContainment(EReference reference) {
		boolean result = reference.isContainment();

		if (!result) {
			EAnnotation subsetAnnotation = reference.getEAnnotation("subsets"); //$NON-NLS-1$
			if (subsetAnnotation != null) {
				result = subsetAnnotation.getReferences().stream()
						.filter(EReference.class::isInstance)
						.map(EReference.class::cast)
						.anyMatch(this::isContainment);
			}
		}

		return result;
	}

	/**
	 * Asynchronously query the {@code EClass} that is the base class of an element {@code type}.
	 *
	 * @param type
	 *            an element type configuration
	 * @return the future EClass that is its base EMF class
	 */
	public CompletableFuture<EClass> getBaseClassAsync(ElementTypeConfiguration type) {
		return asyncTransform(elementTypeBaseClasses, map -> map.get(type));
	}

	/**
	 * Query the {@code EClass} that is the base class of an element {@code type}.
	 *
	 * @param type
	 *            an element type configuration
	 * @return the EClass that is its base EMF class
	 */
	public EClass getBaseClass(ElementTypeConfiguration type) {
		return transform(elementTypeBaseClasses, map -> map.get(type));
	}

	/**
	 * Asynchronously query whether an element {@code type} is creatable in any element type that features the given
	 * {@code role}.
	 *
	 * @param type
	 *            an element type configuration
	 * @param role
	 *            a role in which it may be creatable
	 * @return whether any known element type has the given {@code role} of a type compatible with the given {@code type}
	 */
	public CompletableFuture<Boolean> isCreatableInRoleAsync(ElementTypeConfiguration type, String role) {
		return asyncCombine(elementTypeBaseClasses, creatableRoles, isCreatableInRoleFunction(type, role));
	}

	private BiFunction<Map<ElementTypeConfiguration, EClass>, Multimap<String, EClass>, Boolean> isCreatableInRoleFunction(ElementTypeConfiguration type, String role) {
		return (baseClasses, roles) -> {
			// Locate the type in my registry's resource set
			ElementTypeConfiguration localType = registry.getLocalInstance(type);
			EClass baseClass = baseClasses.get(localType);
			return baseClass != null && roles.get(role).stream().anyMatch(eClass -> eClass.isSuperTypeOf(baseClass));
		};
	}

	/**
	 * Query whether an element {@code type} is creatable in any element type that features the given
	 * {@code role}.
	 *
	 * @param type
	 *            an element type configuration
	 * @param role
	 *            a role in which it may be creatable
	 * @return whether any known element type has the given {@code role} of a type compatible with the given {@code type}
	 */
	public boolean isCreatableInRole(ElementTypeConfiguration type, String role) {
		return combine(elementTypeBaseClasses, Map.of(), creatableRoles, ImmutableMultimap.of(),
				isCreatableInRoleFunction(type, role));
	}

	//
	// Nested types
	//

	/**
	 * A private variant of the modeled Element Types Configurations registry that doesn't actually register
	 * anything but just loads all of the registered configuration models and overlays them with source models
	 * from the workspace.
	 */
	private final class ElementTypesRegistry extends ElementTypeSetConfigurationRegistry {

		private final IResourceChangeListener workspaceListener = this::workspaceChanged;

		private final Computation<Map<String, Map<String, ElementTypeSetConfiguration>>> elementTypeSetConfigurationsComputation;

		ElementTypesRegistry() {
			super();

			elementTypeSetConfigurationsComputation = new Computation<>(() -> {
				init();
				return elementTypeSetConfigurations;
			});
		}

		@Override
		protected void init() {
			super.init();

			IWorkspace workspace = ResourcesPlugin.getWorkspace();

			// Overlay workspace models not already registered
			try {
				workspace.getRoot().accept(new IResourceProxyVisitor() {

					@Override
					public boolean visit(IResourceProxy proxy) throws CoreException {
						switch (proxy.getType()) {
						case IResource.FILE:
							if (isElementTypesFile(proxy)) {
								URI uri = URI.createPlatformResourceURI(proxy.requestFullPath().toPortableString(), true);
								process(uri);
							}
							return false;
						default:
							return true;
						}
					}
				}, IResource.NONE);
			} catch (CoreException e) {
				Activator.log.error("Failed to scan workspace for Element Types Configurations model resources.", e); //$NON-NLS-1$
			}

			workspace.addResourceChangeListener(workspaceListener, IResourceChangeEvent.POST_CHANGE);
		}

		@Override
		public synchronized void dispose() {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(workspaceListener);

			EMFHelper.unload(elementTypeSetConfigurationResourceSet);
			elementTypeSetConfigurations = null;

			resetComputations();
		}

		@Override
		protected ResourceSet createResourceSet() {
			ResourceSet result = new ResourceSetImpl();

			result.setPackageRegistry(ResourceUtils.createWorkspaceAwarePackageRegistry());
			result.setURIConverter(ResourceUtils.createWorkspaceAwareURIConverter());

			return result;
		}

		@Override
		public Map<String, Map<String, ElementTypeSetConfiguration>> getElementTypeSetConfigurations() {
			return get(elementTypeSetConfigurationsComputation);
		}

		@Override
		public boolean loadElementTypeSetConfigurations(String contextID, Collection<ElementTypeSetConfiguration> elementTypeSetConfigurationsToRegister) {
			if (contextID == null) {
				return false;
			}

			// Just load the types
			Map<String, ElementTypeSetConfiguration> map = elementTypeSetConfigurations.computeIfAbsent(contextID, __ -> new HashMap<>());

			elementTypeSetConfigurationsToRegister.forEach(set -> map.put(set.getIdentifier(), set));
			return true;
		}

		private boolean isElementTypesFile(IResourceProxy proxy) throws CoreException {
			boolean result = false;

			if (elementTypesConfigurationsContentType.isAssociatedWith(proxy.getName())) {
				result = isElementTypesFile(proxy.requestResource());
			}

			return result;
		}

		private boolean isElementTypesFile(IResource resource) throws CoreException {
			boolean result = false;

			if (resource.getType() == IResource.FILE && resource.isAccessible()) {
				IContentDescription desc = ((IFile) resource).getContentDescription();
				result = desc != null && desc.getContentType() != null && desc.getContentType().isKindOf(elementTypesConfigurationsContentType);
			}

			return result;
		}

		private void process(URI resourceURI) {
			Resource resource = elementTypeSetConfigurationResourceSet.getResource(resourceURI, false);

			if (resource == null) {
				// Not registered, so load it
				try {
					resource = elementTypeSetConfigurationResourceSet.getResource(resourceURI, true);
					Collection<ElementTypeSetConfiguration> sets = EcoreUtil.getObjectsByType(resource.getContents(),
							ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);
					if (!sets.isEmpty()) {
						// We do not currently use the client context IDs in this index. If and when we do,
						// we will need to get it from the host project's plugin.xml
						String clientContextID = TypeContext.getDefaultContextId();
						loadElementTypeSetConfigurations(clientContextID, sets);
					}
				} catch (Exception e) {
					Activator.log.error("Failed to load Element Types Configurations model resource.", e); //$NON-NLS-1$
				}
			}
		}

		private void workspaceChanged(IResourceChangeEvent event) {
			// Just as when the architecture domain manager changes in the superclass
			boolean[] needRebuild = { false };

			try {
				event.getDelta().accept(new IResourceDeltaVisitor() {

					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						switch (delta.getKind()) {
						case IResourceDelta.CHANGED:
							if ((delta.getFlags() & IResourceDelta.CONTENT) == 0) {
								break;
							}
							// FALL-THROUGH
						case IResourceDelta.ADDED:
						case IResourceDelta.REMOVED:
							if (!delta.getResource().isDerived() && isElementTypesFile(delta.getResource())) {
								needRebuild[0] = true;
							}
							break;
						}

						return !needRebuild[0]; // Short-circuit if we already have this answer
					}
				});
			} catch (CoreException e) {
				Activator.log.error("Failed to process workspace changes.", e); //$NON-NLS-1$
			}

			if (needRebuild[0]) {
				dispose();
			}
		}

		/**
		 * Get the object from my resource set corresponding to the given {@code object} from another resource set.
		 */
		@SuppressWarnings("unchecked")
		<E extends EObject> E getLocalInstance(E object) {
			if (object == null) {
				return null;
			}

			URI uri = EcoreUtil.getURI(object);
			EObject result = elementTypeSetConfigurationResourceSet.getEObject(uri, false);

			if (result != null && result.eClass() == object.eClass()) {
				return (E) result;
			}

			return null;
		}

	}

	/**
	 * A switch over Element Types Configurations that computes the {@link EClass} they are based on.
	 */
	private class BaseClassSwitch extends ElementTypesConfigurationsSwitch<EClass> {

		@Override
		public EClass caseMetamodelTypeConfiguration(MetamodelTypeConfiguration object) {
			return object.getEClass();
		}

		@Override
		public EClass caseSpecializationTypeConfiguration(SpecializationTypeConfiguration object) {
			EClass result = null;

			for (ElementTypeConfiguration general : object.getSpecializedTypes()) {
				result = doSwitch(general);
				if (result != null) {
					break;
				}
			}

			return result;
		}

		@Override
		public EClass caseExternallyRegisteredType(ExternallyRegisteredType object) {
			IElementType type = ElementTypeRegistry.getInstance().getType(object.getIdentifier());
			return type == null ? null : type.getEClass();
		}

	}

}
