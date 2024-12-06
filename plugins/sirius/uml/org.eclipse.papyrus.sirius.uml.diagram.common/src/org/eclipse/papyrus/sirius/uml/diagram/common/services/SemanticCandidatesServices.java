/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ComponentRealization;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.DeploymentTarget;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Include;
import org.eclipse.uml2.uml.InformationFlow;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.UseCase;

/**
 * Services used to retrieve semantic candidates, mainly for edges.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class SemanticCandidatesServices {

	/**
	 * Get all {@link Abstraction} found in the context.
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Abstraction}
	 * @return
	 *         {@link Abstraction} available in the context
	 */
	public Collection<Abstraction> getAbstractionCandidates(final EObject semanticContext) {
		// @formatter:off
		return this.getDependencyCandidates(semanticContext).stream()
				.filter(Abstraction.class::isInstance)
				.map(Abstraction.class::cast)
				.collect(Collectors.toList());
		// @formatter:on
	}

	/**
	 * Get all {@link Association} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Association}
	 * @return
	 *         all {@link Association} available in the context
	 */
	public Collection<Association> getAssociationCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Package) {
			final Package pack = (Package) semanticContext;
			return this.getAllAssociations(pack);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link Association} from a given {@link Package}.
	 *
	 * @param pack
	 *            a UML {@link Package}
	 * @return
	 *         all {@link Association} recursively
	 */
	private final Collection<Association> getAllAssociations(final Package pack) {
		final Collection<Association> associations = new HashSet<Association>();
		final Iterator<PackageableElement> iter = pack.getPackagedElements().iterator();
		while (iter.hasNext()) {
			final PackageableElement current = iter.next();
			if (current instanceof Package) {
				associations.addAll(this.getAllAssociations((Package) current));
			}
			if (current instanceof Association) {
				associations.add((Association) current);
			}
		}
		return associations;
	}

	/**
	 * Get all {@link Connector} found in the context.
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Connector}
	 * @return
	 *         {@link Connector} available in the context
	 */
	public Collection<Connector> getConnectorCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return getAllConnectors(namespace);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link Connector} from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Namespace}
	 * @return
	 *         all {@link Connector} recursively
	 */
	private static final Collection<Connector> getAllConnectors(final Namespace namespace) {
		final Collection<Connector> connectors = new HashSet<Connector>();
		final Iterator<NamedElement> iter = namespace.getOwnedMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Class) {
				connectors.addAll(((Class) current).getOwnedConnectors());
			}
			if (current instanceof Namespace) {
				connectors.addAll(getAllConnectors((Namespace) current));
			}
		}
		return connectors;
	}

	/**
	 * Get {@link Constraint} candidates to display in representations.
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Constraint}
	 * @return
	 *         the list of {@link Constraint} to display
	 */
	public Collection<EObject> getConstraintCandidates(final EObject semanticContext) {
		final Set<EObject> constraints = new HashSet<EObject>();

		if (semanticContext instanceof Namespace) {
			Namespace namespace = (Namespace) semanticContext;
			constraints.addAll(namespace.getOwnedRules());
		}
		return constraints;
	}

	/**
	 * Get all {@link Dependency} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Dependency}
	 * @return
	 *         all {@link Dependency} available in the context
	 */
	public Collection<Dependency> getDependencyCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return this.getAllDependency(namespace);
		}
		return List.of();
	}

	/**
	 * Get all {@link Dependency} kind from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Namespace}
	 * @return
	 *         all {@link Dependency} recursively
	 */
	private final Collection<Dependency> getAllDependency(final Namespace namespace) {
		final Collection<Dependency> dependencies = new HashSet<Dependency>();
		final Iterator<NamedElement> iter = namespace.getMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Namespace) {
				dependencies.addAll(this.getAllDependency((Namespace) current));
			} else if (current instanceof Dependency) {
				dependencies.add((Dependency) current);
			}
			// Classifier might contain CollaborationUse(s) that might contain dependency through RoleBindings feature.
			if (current instanceof Classifier) {
				((Classifier) current).getCollaborationUses().forEach(collaborationUse -> {
					dependencies.addAll(this.getAllDependency(collaborationUse));
				});
			}
			if (current instanceof Artifact artifact) {
				dependencies.addAll(artifact.getManifestations());
			}
			if (current instanceof DeploymentTarget deploymentTarget) {
				dependencies.addAll(deploymentTarget.getDeployments());
			}
		}
		return dependencies;
	}

	/**
	 * Collects all dependency contained in the given {@link CollaborationUse}.
	 *
	 * @param collaborationUse
	 *            the {@link CollaborationUse} in which look for dependencies.
	 * @return the list of dependencies.
	 */
	private final Collection<Dependency> getAllDependency(final CollaborationUse collaborationUse) {
		// @formatter:off
		return collaborationUse.getRoleBindings().stream()
				.filter(Dependency.class::isInstance)
				.map(Dependency.class::cast)
				.collect(Collectors.toList());
		// @formatter:on
	}



	/**
	 * Get all {@link Extend} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Extend}
	 * @return
	 *         all {@link Extend} available in the context
	 */
	public Collection<Extend> getExtendCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return this.getAllExtends(namespace);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link Extend} from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Namespace}
	 * @return
	 *         all {@link Extend} recursively
	 */
	private final Collection<Extend> getAllExtends(final Namespace namespace) {
		final Collection<Extend> extendsList = new HashSet<Extend>();
		final Iterator<NamedElement> iter = namespace.getMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Namespace) {
				extendsList.addAll(this.getAllExtends((Namespace) current));
			}
			if (current instanceof UseCase) {
				extendsList.addAll(((UseCase) current).getExtends());
			}
		}
		return extendsList;
	}

	/**
	 * Get all {@link Extension} found in the context.
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Extension}
	 * @return
	 *         {@link Extension}s available in the context
	 */
	public Collection<Extension> getExtensionCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Profile) {
			final Profile profile = (Profile) semanticContext;
			return this.getAllElementsByType(profile, Extension.class);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link Generalization} found in the context.
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Generalization}
	 * @return
	 *         {@link Generalization} available in the context
	 */
	public Collection<Generalization> getGeneralizationCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return getAllGeneralizations(namespace);
		}
		return Collections.emptyList();
	}



	/**
	 * Get all {@link Generalization} from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Namespace}
	 * @return
	 *         all {@link Generalization} recursively
	 */
	private static final Collection<Generalization> getAllGeneralizations(final Namespace namespace) {
		final Collection<Generalization> generalizations = new HashSet<Generalization>();
		final Iterator<NamedElement> iter = namespace.getOwnedMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Classifier) {
				generalizations.addAll(((Classifier) current).getGeneralizations());
			}
			if (current instanceof Namespace) {
				generalizations.addAll(getAllGeneralizations((Namespace) current));
			}
		}
		return generalizations;
	}

	/**
	 * Get all {@link InterfaceRealization} found in the context.
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link InterfaceRealization}
	 * @return the {@link InterfaceRealization} available in the context
	 */
	public Collection<InterfaceRealization> getInterfaceRealizationCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return getAllInterfaceRealizations(namespace);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link InterfaceRealization} from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Namespace}
	 * @return
	 *         all {@link InterfaceRealization} recursively
	 */
	private static final Collection<InterfaceRealization> getAllInterfaceRealizations(final Namespace namespace) {
		final Collection<InterfaceRealization> interfaceRealizations = new HashSet<>();
		final Iterator<NamedElement> iter = namespace.getOwnedMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof BehavioredClassifier) {
				interfaceRealizations.addAll(((BehavioredClassifier) current).getInterfaceRealizations());
			}
			if (current instanceof Namespace) {
				interfaceRealizations.addAll(getAllInterfaceRealizations((Namespace) current));
			}
		}
		return interfaceRealizations;
	}

	/**
	 * Get all {@link ComponentRealization} found in the context.
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link ComponentRealization}
	 * @return the {@link ComponentRealization} available in the context
	 */
	public Collection<ComponentRealization> getComponentRealizationCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return getAllComponentRealizations(namespace);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link ComponentRealization} from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Namespace}
	 * @return all {@link ComponentRealization} recursively
	 */
	private static final Collection<ComponentRealization> getAllComponentRealizations(final Namespace namespace) {
		final Collection<ComponentRealization> componentRealizations = new HashSet<>();
		final Iterator<NamedElement> iter = namespace.getOwnedMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Component) {
				componentRealizations.addAll(((Component) current).getRealizations());
			}
			if (current instanceof Namespace) {
				componentRealizations.addAll(getAllComponentRealizations((Namespace) current));
			}
		}
		return componentRealizations;
	}

	/**
	 * Get all {@link Include} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Include}
	 * @return
	 *         all {@link Include} available in the context
	 */
	public Collection<Include> getIncludeCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return this.getAllIncludes(namespace);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link Include} from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Package}
	 * @return
	 *         all {@link Include} recursively
	 */
	private final Collection<Include> getAllIncludes(final Namespace namespace) {
		final Collection<Include> includes = new HashSet<Include>();
		final Iterator<NamedElement> iter = namespace.getMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Namespace) {
				includes.addAll(this.getAllIncludes((Namespace) current));
			}
			if (current instanceof UseCase) {
				includes.addAll(((UseCase) current).getIncludes());
			}
		}
		return includes;
	}

	/**
	 * Get all {@link InformationFlow} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link InformationFlow}
	 * @return
	 *         all {@link InformationFlow} available in the context
	 */
	public Collection<InformationFlow> getInformationFlowCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Package) {
			final Package pack = (Package) semanticContext;
			return this.getAllElementsByType(pack, InformationFlow.class);
		}
		return List.of();
	}

	/**
	 * Get all {@link Manifestation} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Manifestation}
	 * @return
	 *         all {@link Manifestation} available in the context
	 */
	public Collection<Manifestation> getManifestationCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Package) {
			final Package pack = (Package) semanticContext;
			return this.getAllManifestations(pack);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link Connector} from a given {@link Package}.
	 *
	 * @param pack
	 *            a UML {@link Package}
	 * @return
	 *         all {@link Manifestation} recursively
	 */
	private final Collection<Manifestation> getAllManifestations(final Package pack) {
		final Collection<Manifestation> manifestations = new HashSet<Manifestation>();
		final Iterator<NamedElement> iter = pack.getMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Package) {
				manifestations.addAll(this.getAllManifestations((Package) current));
			}
			if (current instanceof Manifestation) {
				manifestations.add((Manifestation) current);
			}
		}
		return manifestations;
	}

	/**
	 * Get all {@link PackageMerge} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link PackageMerge}
	 * @return
	 *         all {@link PackageMerge} available in the context
	 */
	public Collection<PackageMerge> getPackageMergeCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Package) {
			final Package pack = (Package) semanticContext;
			return this.getAllPackagesMerge(pack);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link PackageMerge} from a given {@link Package}.
	 *
	 * @param pack
	 *            a UML {@link Package}
	 * @return
	 *         all {@link PackageMerge} recursively
	 */
	private final Collection<PackageMerge> getAllPackagesMerge(final Package pack) {
		final Collection<PackageMerge> packagesMerge = new HashSet<PackageMerge>();
		packagesMerge.addAll(pack.getPackageMerges());
		for (PackageableElement packageableElement : pack.getPackagedElements()) {
			if (packageableElement instanceof Package) {
				packagesMerge.addAll(this.getAllPackagesMerge((Package) packageableElement));
			}
		}
		return packagesMerge;
	}

	/**
	 * Get all {@link PackageImport} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link PackageImport}
	 * @return
	 *         all {@link PackageImport} available in the context
	 */
	public Collection<PackageImport> getPackageImportCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return this.getAllPackagesImport(namespace);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link PackageImport} from a given {@link Package}.
	 *
	 * @param pack
	 *            a UML {@link Package}
	 * @return
	 *         all {@link PackageImport} recursively
	 */
	private final Collection<PackageImport> getAllPackagesImport(final Namespace namespace) {
		final Collection<PackageImport> packageImports = new HashSet<PackageImport>();
		packageImports.addAll(namespace.getPackageImports());
		if (namespace instanceof Package) {
			Package pack = (Package) namespace;
			for (PackageableElement packageableElement : pack.getPackagedElements()) {
				if (packageableElement instanceof Namespace) {
					packageImports.addAll(this.getAllPackagesImport((Namespace) packageableElement));
				}
			}
		}
		return packageImports;
	}

	/**
	 * Get all {@link Realization} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Realization}
	 * @return
	 *         all {@link Realization} available in the context
	 */
	public Collection<Realization> getRealizationCandidates(final EObject semanticContext) {
		// @formatter:off
		return this.getDependencyCandidates(semanticContext).stream()
				.filter(Realization.class::isInstance)
				.map(Realization.class::cast)
				.collect(Collectors.toList());
		// @formatter:on
	}



	/**
	 * Get all {@link Substitution} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Substitution}
	 * @return
	 *         all {@link Substitution} available in the context
	 */
	public Collection<Substitution> getSubstitutionCandidates(final EObject semanticContext) {
		if (semanticContext instanceof Namespace) {
			final Namespace namespace = (Namespace) semanticContext;
			return this.getAllSubstitutions(namespace);
		}
		return Collections.emptyList();
	}

	/**
	 * Get all {@link Substitution} from a given {@link Namespace}.
	 *
	 * @param namespace
	 *            a UML {@link Namespace}
	 * @return
	 *         all {@link Substitution} recursively
	 */
	private final Collection<Substitution> getAllSubstitutions(final Namespace namespace) {
		final Collection<Substitution> substitutions = new HashSet<Substitution>();
		final Iterator<NamedElement> iter = namespace.getOwnedMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Classifier) {
				substitutions.addAll(((Classifier) current).getSubstitutions());
			}
			if (current instanceof Namespace) {
				substitutions.addAll(this.getAllSubstitutions((Namespace) current));
			}
		}
		return substitutions;
	}

	/**
	 * Get all {@link Usage} found in the context
	 *
	 * @param semanticContext
	 *            the context in which we are looking for {@link Usage}
	 * @return
	 *         all {@link Usage} available in the context
	 */
	public Collection<Usage> getUsageCandidates(final EObject semanticContext) {
		// @formatter:off
		return this.getDependencyCandidates(semanticContext).stream()
				.filter(Usage.class::isInstance)
				.map(Usage.class::cast)
				.collect(Collectors.toList());
		// @formatter:on
	}

	/**
	 * Get all {@link Message} from the given {@code interaction} except create messages.
	 * 
	 * @param interaction
	 *            the {@link Interaction} to search into
	 * @return the messages contained in the interaction
	 */
	public Collection<Message> getMessageCandidates(final Interaction interaction) {
		return interaction.getMessages().stream()
				.filter(msg -> MessageSort.CREATE_MESSAGE_LITERAL != msg.getMessageSort())
				.toList();
	}

	/**
	 * Get all {@link Message} Create from the given {@code interaction}.
	 * 
	 * @param interactionthe
	 *            {@link Interaction} to search into
	 * @return the "message creates" contained in the interaction
	 */
	public Collection<Message> getMessageCreateCandidates(final Interaction interaction) {
		return interaction.getMessages().stream()
				.filter(msg -> MessageSort.CREATE_MESSAGE_LITERAL == msg.getMessageSort())
				.toList();
	}

	/**
	 * Get all elements of type T from a given {@link Package}.
	 *
	 * @param pack
	 *            a UML {@link Package}
	 * @return
	 *         all elements of the given type recursively
	 */
	@SuppressWarnings("unchecked")
	private final <T> Collection<T> getAllElementsByType(final Package pack, java.lang.Class<T> clazz) {
		final Collection<T> elements = new HashSet<>();
		final Iterator<NamedElement> iter = pack.getMembers().iterator();
		while (iter.hasNext()) {
			final NamedElement current = iter.next();
			if (current instanceof Package) {
				elements.addAll(this.getAllElementsByType((Package) current, clazz));
			}
			if (clazz.isInstance(current)) {
				elements.add((T) current);
			}
		}
		return elements;
	}


}
