/*****************************************************************************
 * Copyright (c) 2013, 2021, 2018, 2023 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 422257
 *  Christian W. Damus - bugs 463156, 493030, 573886
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 519409
 *  Benoit Maggi (CEA) - Bug 536581
 *  Vincent Lorenzo (CEA-LIST) <vincent.lorenzo@cea.fr> - bug 582023
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate;
import org.eclipse.papyrus.infra.architecture.representation.ModelRule;
import org.eclipse.papyrus.infra.architecture.representation.OwningRule;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule;
import org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule;
import org.eclipse.papyrus.infra.gmfdiag.representation.PaletteRule;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;

/**
 * The <code>PolicyChecker</code> enforces the viewpoints description as a policy in the user interface
 *
 * @author Laurent Wouters
 */
public class PolicyChecker {
	/**
	 * Default result when the current policy cannot determine whether an element can be added to a view
	 */
	private static final boolean DEFAULT_POLICY_UNKNOWN_CHILD = false;
	/**
	 * Default result when the current policy cannot determine whether a palette item should be exposed
	 */
	private static final boolean DEFAULT_POLICY_UNKNWON_PALETTE = true;
	/**
	 * Policy check result allowing an action
	 */
	private static final int RESULT_PERMIT = 1;
	/**
	 * Policy check result neither allowing not denying an action
	 */
	private static final int RESULT_UNKNOWN = 0;
	/**
	 * Policy check result denying an action
	 */
	private static final int RESULT_DENY = -1;

	/**
	 * @since 2.0
	 */
	public static PolicyChecker getFor(EObject object) {
		if (object != null && object.eResource() != null) {
			return getFor(object.eResource());
		} else {
			return getFor(ArchitectureDomainManager.getInstance().getDefaultArchitectureContext());
		}
	}

	/**
	 * @since 2.0
	 */
	public static PolicyChecker getFor(Resource resource) {
		if (resource != null && resource.getResourceSet() != null) {
			return getFor(resource.getResourceSet());
		} else {
			return getFor(ArchitectureDomainManager.getInstance().getDefaultArchitectureContext());
		}
	}

	/**
	 * @since 2.0
	 */
	public static PolicyChecker getFor(ResourceSet resourceSet) {
		if (resourceSet instanceof ModelSet) {
			return getFor((ModelSet) resourceSet);
		} else {
			return getFor(ArchitectureDomainManager.getInstance().getDefaultArchitectureContext());
		}
	}

	/**
	 * @since 2.0
	 */
	public static PolicyChecker getFor(ModelSet modelSet) {
		Collection<MergedArchitectureViewpoint> viewpoints = new ArchitectureDescriptionUtils(modelSet).getArchitectureViewpoints();
		return getFor(viewpoints);
	}

	/**
	 * @since 2.0
	 */
	public static PolicyChecker getFor(MergedArchitectureContext context) {
		if (context == null) {
			return new PolicyChecker(Collections.emptyList());
		}
		return new PolicyChecker(context.getViewpoints());
	}

	/**
	 * @since 2.0
	 */
	public static PolicyChecker getFor(Collection<MergedArchitectureViewpoint> viewpoints) {
		return new PolicyChecker(viewpoints);
	}

	/**
	 * The current profile helper
	 */
	private IProfileHelper profileHelper;

	/**
	 * The architecture viewpoints
	 */
	private Collection<MergedArchitectureViewpoint> viewpoints;

	/**
	 * Gets the viewpoint enforced by this object
	 *
	 * @return The enforced viewpoint
	 * @since 2.0
	 */
	public Collection<MergedArchitectureViewpoint> getViewpoints() {
		return viewpoints;
	}

	/**
	 * Initializes this instance from the current preferences
	 */
	private PolicyChecker(Collection<MergedArchitectureViewpoint> viewpoints) {
		this.profileHelper = ProfileUtils.getProfileHelper();
		this.viewpoints = viewpoints;
	}

	/**
	 * Determines whether the given element can be the root of a view owned by the given object
	 *
	 * @param element
	 *            The possible root element
	 * @param owner
	 *            The possible owner
	 * @param prototype
	 *            The view prototype
	 * @return <code>true</code> if the element can be the root
	 */
	public boolean canHaveNewView(EObject element, EObject owner, ViewPrototype prototype) {
		if (prototype == null) {
			return false;
		}
		if (!matchesProfiles(prototype.representationKind, owner, profileHelper.getAppliedProfiles(owner))) {
			return false;
		}
		if (!matchesProfiles(prototype.representationKind, owner, profileHelper.getAppliedProfiles(element))) {
			return false;
		}
		if (!matchesCreationRoot(prototype.representationKind, element, profileHelper.getAppliedStereotypes(element), () -> prototype.getViewCountOn(element))) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the insertion data of the given element in the given diagram
	 *
	 * @param diagram
	 *            The diagram
	 * @param parent
	 *            The element to be edited
	 * @param child
	 *            The element to be added through the diagram
	 * @return The insertion data
	 */
	public ModelAddData getChildAddData(Diagram diagram, EObject parent, EObject child) {
		ViewPrototype prototype = ViewPrototype.get(diagram);
		if (prototype == null || !(prototype.representationKind instanceof PapyrusDiagram)) {
			// This diagram is not in the current policy
			return new ModelAddData(false);
		}

		PapyrusDiagram config = (PapyrusDiagram) prototype.representationKind;
		Collection<EClass> stereotypes = profileHelper.getAppliedStereotypes(child);
		while (config != null) {
			for (ChildRule rule : config.getChildRules()) {
				int result = allows(rule, parent.eClass(), child.eClass(), stereotypes);
				if (result != RESULT_UNKNOWN) {
					return new ModelAddData((result == RESULT_PERMIT), rule.getInsertionPath());
				}
			}
			config = (PapyrusDiagram) config.getParent();
		}
		return new ModelAddData(DEFAULT_POLICY_UNKNOWN_CHILD);
	}

	/**
	 * Gets the insertion data of the given element in the given diagram
	 *
	 * @param diagram
	 *            The diagram
	 * @param parentType
	 *            The type of the element to be edited
	 * @param childType
	 *            The type of element to be added through the diagram
	 * @return The insertion data
	 */
	public ModelAddData getChildAddData(Diagram diagram, EClass parentType, EClass childType) {
		ViewPrototype prototype = ViewPrototype.get(diagram);
		if (prototype == null || !(prototype.representationKind instanceof PapyrusDiagram)) {
			// This diagram is not in the current policy
			return new ModelAddData(false);
		}

		PapyrusDiagram config = (PapyrusDiagram) prototype.representationKind;
		while (config != null) {
			for (ChildRule rule : config.getChildRules()) {
				int result = allows(rule, parentType, childType, new ArrayList<EClass>(0));
				if (result != RESULT_UNKNOWN) {
					return new ModelAddData((result == RESULT_PERMIT), rule.getInsertionPath());
				}
			}
			config = (PapyrusDiagram) config.getParent();
		}
		return new ModelAddData(DEFAULT_POLICY_UNKNOWN_CHILD);
	}

	/**
	 * Determines whether the given diagram can have the palette element with the given entry ID
	 *
	 * @param diagram
	 *            The diagram
	 * @param entryID
	 *            A palette element entry ID
	 * @return <code>true</code> if the palette element is allowed
	 */
	public boolean isInPalette(Diagram diagram, String entryID) {
		ViewPrototype prototype = ViewPrototype.get(diagram);
		if (prototype == null || !(prototype.representationKind instanceof PapyrusDiagram)) {
			// This diagram is not in the current policy
			return false;
		}

		PapyrusDiagram config = (PapyrusDiagram) prototype.representationKind;
		while (config != null) {
			for (PaletteRule rule : config.getPaletteRules()) {
				int result = allows(rule, entryID);
				if (result != RESULT_UNKNOWN) {
					return (result == RESULT_PERMIT);
				}
			}
			config = (PapyrusDiagram) config.getParent();
		}
		return DEFAULT_POLICY_UNKNWON_PALETTE;
	}

	/**
	 * Determines whether the given diagram can have a modeling assistant creating the specified element type.
	 *
	 * @param diagram
	 *            The diagram
	 * @param elementType
	 *            A modeling assistant element type
	 * @return whether the modeling assistant is allowed
	 */
	public boolean isInModelingAssistants(Diagram diagram, IElementType elementType) {
		ViewPrototype prototype = ViewPrototype.get(diagram);
		if (prototype == null || !(prototype.representationKind instanceof PapyrusDiagram)) {
			// This diagram is not in the current policy
			return false;
		}

		PapyrusDiagram config = (PapyrusDiagram) prototype.representationKind;
		while (config != null) {
			for (AssistantRule rule : config.getAssistantRules()) {
				int result = allows(rule, elementType);
				if (result != RESULT_UNKNOWN) {
					return (result == RESULT_PERMIT);
				}
			}
			config = (PapyrusDiagram) config.getParent();
		}
		return DEFAULT_POLICY_UNKNWON_PALETTE;
	}

	/**
	 * Determines whether the given view description element is part of the current viewpoint
	 *
	 * @param config
	 *            A view description element
	 * @return <code>true</code> if the element is part of the current viewpoint
	 * @since 2.0
	 */
	public boolean isInViewpoint(PapyrusRepresentationKind kind) {
		return kind != null && getViewpoints().stream()
				.flatMap(viewpoint -> viewpoint.getRepresentationKinds().stream())
				.anyMatch(representationKinds -> representationKinds.getQualifiedName().equals(kind.getQualifiedName()));
	}

	/**
	 * Gets a collection of all the view prototypes allowed by the active policy
	 *
	 * @return A collection of view prototypes
	 */
	public Collection<ViewPrototype> getAllPrototypes() {
		Collection<ViewPrototype> result = new ArrayList<>();
		for (MergedArchitectureViewpoint viewpoint : getViewpoints()) {
			for (RepresentationKind kind : viewpoint.getRepresentationKinds()) {
				PapyrusRepresentationKind view = (PapyrusRepresentationKind) kind;
				ViewPrototype proto = ViewPrototype.get(view);
				if (null != proto && !result.contains(proto)) {
					result.add(proto);
				}
			}
		}
		return result;
	}

	/**
	 * Gets a list of the prototypes that can be instantiated with the given element as owner according to the policy
	 *
	 * @param element
	 *            The element to test
	 * @return A list of the prototypes that can be instantiated
	 */
	public Collection<ViewPrototype> getPrototypesFor(EObject element) {
		Collection<ViewPrototype> result = new LinkedHashSet<>();
		Collection<EPackage> profiles = profileHelper.getAppliedProfiles(element);
		Collection<EClass> stereotypes = profileHelper.getAppliedStereotypes(element);
		for (MergedArchitectureViewpoint viewpoint : getViewpoints()) {
			for (RepresentationKind kind : viewpoint.getRepresentationKinds()) {
				PapyrusRepresentationKind view = (PapyrusRepresentationKind) kind;
				if (!matchesProfiles(view, element, profiles)) {
					continue;
				}
				ViewPrototype proto = ViewPrototype.get(view);
				if (proto == null) {
					continue;
				}
				Supplier<Integer> count = () -> proto.getOwnedViewCount(element);
				OwningRule rule = matchesCreationOwner(view, element, stereotypes, count);
				if (rule == null) {
					continue;
				}
				if (rule.getNewModelPath() != null && !rule.getNewModelPath().isEmpty()) {
					// Auto-created root => always OK
					result.add(proto);
				} else if (rule.getSelectDiagramRoot() != null && !rule.getSelectDiagramRoot().isEmpty()) {
					result.add(proto);
				} else {
					// We have to check if the owner can also be a root
					count = () -> proto.getViewCountOn(element);
					if (matchesCreationRoot(view, element, stereotypes, count)) {
						// The owner can also be the root => OK
						result.add(proto);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Gets the owning rule relevant for the given view prototype and owner
	 *
	 * @param prototype
	 *            The view prototype
	 * @param owner
	 *            The view's owner
	 * @return The owning rule
	 * @since 2.0
	 */
	public OwningRule getOwningRuleFor(ViewPrototype prototype, EObject owner) {
		Collection<EClass> stereotypes = profileHelper.getAppliedStereotypes(owner);
		Supplier<Integer> count = () -> prototype.getOwnedViewCount(owner);
		return matchesCreationOwner(prototype.representationKind, owner, stereotypes, count);
	}

	/**
	 * Tries to match a view description from the given info
	 *
	 * @param implem
	 *            The implementation ID
	 * @param owner
	 *            The owner
	 * @param root
	 *            The root element
	 * @return The matching view, or <code>null</code> if none was found
	 * @since 2.0
	 */
	protected PapyrusRepresentationKind getRepresentationKindFrom(String implem, EObject owner, EObject root) {
		for (MergedArchitectureViewpoint viewpoint : getViewpoints()) {
			for (RepresentationKind kind : viewpoint.getRepresentationKinds()) {
				PapyrusRepresentationKind view = (PapyrusRepresentationKind) kind;
				if (matches(view, implem, owner, root)) {
					return view;
				}
			}
		}
		return null;
	}

	/**
	 * Tries to match a view description with the given info
	 *
	 * @param view
	 *            A view description
	 * @param implem
	 *            The implementation ID
	 * @param owner
	 *            The owner
	 * @param root
	 *            The root element
	 * @return <code>true</code> if the description matches
	 */
	private boolean matches(PapyrusRepresentationKind view, String implem, EObject owner, EObject root) {
		if (!view.getImplementationID().equals(implem)) {
			return false;
		}
		if (owner != null) {
			if (!matchesProfiles(view, owner, profileHelper.getAppliedProfiles(owner))) {
				return false;
			}
			if (!matchesExistingOwner(view, owner, profileHelper.getAppliedStereotypes(owner))) {
				return false;
			}
		}
		if (root != null) {
			if (!matchesProfiles(view, root, profileHelper.getAppliedProfiles(root))) {
				return false;
			}
			if (!matchesExistingRoot(view, root, profileHelper.getAppliedStereotypes(root))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the given set of profiles matches the requirements of the given view
	 *
	 * @param view
	 *            The view to check against
	 * @param context
	 *            The object in which context we are checking the {@code view}'s profile association
	 * @param profiles
	 *            The applied profiles
	 * @return <code>true</code> if the prototype is matching
	 */
	private boolean matchesProfiles(PapyrusRepresentationKind view, EObject context, Collection<EPackage> profiles) {
		PapyrusRepresentationKind current = view;
		while (current != null) {
			for (EPackage profile : view.getLanguage().getProfiles()) {
				// The profile may be a dynamic definition, so compare them in the same resource set context
				EPackage localProfile = resolveDynamicProfile(context, profile);
				if (!profiles.contains(localProfile)) {
					return false;
				}
			}
			current = current.getParent();
		}
		return true;
	}

	/**
	 * Resolve a possibly dynamic {@link profile} definition in the resource set
	 * of the {@code context} element.
	 *
	 * @param context
	 *            a model element in which context policy is being checked
	 * @param profile
	 *            a profile associated with the policy being checked
	 * @return the corresponding {@code profile} definition on the {@code context}'s resource set
	 */
	private EPackage resolveDynamicProfile(EObject context, EPackage profile) {
		return Optional.ofNullable(EMFHelper.getResourceSet(context))
				.map(rset -> rset.getPackageRegistry().getEPackage(profile.getNsURI()))
				.orElse(profile);
	}

	/**
	 * Checks whether the given type of owning element with applied stereotypes is allowed for the given view
	 *
	 * @param view
	 *            The view to check against
	 * @param type
	 *            The owning element's type
	 * @param stereotypes
	 *            The stereotypes applied on the owning element
	 * @return <code>true</code> if the prototype is matching
	 */
	private boolean matchesExistingOwner(PapyrusRepresentationKind view, EObject owner, Collection<EClass> stereotypes) {
		PapyrusRepresentationKind current = view;
		while (current != null) {
			for (OwningRule rule : current.getOwningRules()) {
				int result = allows(rule, owner, stereotypes);
				if (result == RESULT_DENY) {
					return false;
				}
				if (result == RESULT_PERMIT) {
					return true;
				}
			}
			current = current.getParent();
		}
		return false;
	}

	/**
	 * Checks whether the given view can be owned by an element of the given type, applied with the given stereotypes if the cardinality is already the given amount
	 *
	 * @param view
	 *            The view to check against
	 * @param owner
	 *            The owning element
	 * @param stereotypes
	 *            The stereotypes applied on the owning element
	 * @param count
	 *            The current cardinality for the owning element
	 * @return The matching rule that allows the owner
	 */
	private OwningRule matchesCreationOwner(PapyrusRepresentationKind view, EObject owner, Collection<EClass> stereotypes, Supplier<Integer> count) {
		PapyrusRepresentationKind current = view;
		while (current != null) {
			for (OwningRule rule : current.getOwningRules()) {
				int allow = allows(rule, owner, stereotypes);
				if (allow == RESULT_DENY) {
					return null;
				}
				if (allow == RESULT_UNKNOWN) {
					continue;
				}
				int multiplicity = rule.getMultiplicity();
				if (multiplicity == -1 || count.get() < multiplicity) {
					if (allows(rule, owner)) {
						return rule;
					}
				}
			}
			current = current.getParent();
		}
		return null;
	}

	/**
	 * Checks whether the given type of root element with applied stereotypes is allowed for the given view
	 *
	 * @param view
	 *            The view to check against
	 * @param root
	 *            The root element
	 * @param stereotypes
	 *            The stereotypes applied on the root element
	 * @return <code>true</code> if the prototype is matching
	 */
	private boolean matchesExistingRoot(PapyrusRepresentationKind view, EObject root, Collection<EClass> stereotypes) {
		PapyrusRepresentationKind current = view;
		while (current != null) {
			for (ModelRule rule : current.getModelRules()) {
				int result = allows(rule, root, stereotypes);
				if (result == RESULT_DENY) {
					return false;
				}
				if (result == RESULT_PERMIT) {
					return true;
				}
			}
			current = current.getParent();
		}
		return false;
	}

	/**
	 * Checks whether the given view can have the given root element of the given type, applied with the given stereotypes if the cardinality is already the given amount
	 *
	 * @param view
	 *            The view to check against
	 * @param root
	 *            The root element
	 * @param stereotypes
	 *            The stereotypes applied on the root element
	 * @param count
	 *            The current cardinality for the root element
	 * @return <code>true</code> if the prototype is matching
	 */
	private boolean matchesCreationRoot(PapyrusRepresentationKind view, EObject root, Collection<EClass> stereotypes, Supplier<Integer> count) {
		PapyrusRepresentationKind current = view;
		while (current != null) {
			for (ModelRule rule : current.getModelRules()) {
				int allow = allows(rule, root, stereotypes);
				if (allow == RESULT_DENY) {
					return false;
				}
				if (allow == RESULT_UNKNOWN) {
					continue;
				}
				int multiplicity = rule.getMultiplicity();
				if (multiplicity == -1 || count.get() < multiplicity) {
					return true;
				}
			}
			current = current.getParent();
		}
		return false;
	}

	/**
	 * Checks an owner's type against a rule
	 *
	 * @param rule
	 *            The owning rule
	 * @param owner
	 *            The owner's type
	 * @param stereotypes
	 *            The stereotypes applied on <code>owner</code>
	 * @return The check result
	 */
	private int allows(OwningRule rule, EObject owner, Collection<EClass> stereotypes) {
		EClass c = rule.getElement();
		if (c == null || c.isSuperTypeOf(owner.eClass())) {
			// matching type => check the application of the required stereotypes
			for (EClass stereotype : rule.getStereotypes()) {
				// The profile may be a dynamic definition, so compare stereotypes in the same resource set context
				EClass localStereotype = resolveDynamicStereotype(owner, stereotype);
				if (!stereotypes.contains(localStereotype)) {
					return RESULT_UNKNOWN;
				}
			}
			return rule.isPermit() ? RESULT_PERMIT : RESULT_DENY;
		} else {
			// type is not matching => unknown
			return RESULT_UNKNOWN;
		}
	}

	/**
	 * Resolve a possibly dynamic {@link stereotype} definition in the resource set
	 * of the {@code context} element.
	 *
	 * @param context
	 *            a model element in which context policy is being checked
	 * @param stereotype
	 *            a stereotype associated with the policy being checked
	 * @return the corresponding {@code stereotype} definition on the {@code context}'s resource set
	 */
	private EClass resolveDynamicStereotype(EObject context, EClass stereotype) {
		return Optional.ofNullable(resolveDynamicProfile(context, stereotype.getEPackage())
				.getEClassifier(stereotype.getName()))
				.filter(EClass.class::isInstance)
				.map(EClass.class::cast)
				.orElse(stereotype);
	}

	/**
	 * Checks whether the given owning rule will allow to derive an auto-created root
	 *
	 * @param rule
	 *            The owning rule
	 * @param owner
	 *            The owner
	 * @return <code>true</code> if it is possible
	 */
	private boolean allows(OwningRule rule, EObject owner) {
		List<ModelAutoCreate> modelAutoCreateList = rule.getNewModelPath();
		if (modelAutoCreateList == null || modelAutoCreateList.isEmpty()) {
			return true;
		}
		EObject current = owner;
		for (ModelAutoCreate elem : modelAutoCreateList) {
			EReference ref = elem.getFeature();
			if (ref.isMany()) {
				return true;
			}
			Object e = current.eGet(ref);
			if (e == null) {
				return true;
			}
			current = (EObject) e;
		}
		return false; // by default a rule is not allowed
	}

	/**
	 * Checks a root element's type against a rule
	 *
	 * @param rule
	 *            The modeling rule
	 * @param element
	 *            The root element's type
	 * @param stereotypes
	 *            The stereotypes applied on <code>element</code>
	 * @return The check result
	 */
	private int allows(ModelRule rule, EObject element, Collection<EClass> stereotypes) {
		EClass c = rule.getElement();
		if (c == null || c.isSuperTypeOf(element.eClass())) {
			// matching type => check the application of the required stereotypes
			// each stereotype required to match the model rule should be applied on the element
			for (EClass stereotype : rule.getStereotypes()) {
				// The profile may be a dynamic definition, so compare stereotypes in the same resource set context
				EClass localStereotype = resolveDynamicStereotype(element, stereotype);
				if (!stereotypes.contains(localStereotype)) {
					return RESULT_UNKNOWN;
				}
			}
			// check the rule
			boolean ruleMatches = RuleConstraintManager.getInstance().matchRule(rule, element);
			if (!ruleMatches) {
				return RESULT_UNKNOWN;
			}
			return rule.isPermit() ? RESULT_PERMIT : RESULT_DENY;
		} else {
			// type is not matching => unknown
			return RESULT_UNKNOWN;
		}
	}

	/**
	 * Checks a child insertion against a rule
	 *
	 * @param rule
	 *            The insertion rule
	 * @param origin
	 *            The root element's type
	 * @param element
	 *            The child's type
	 * @param stereotypes
	 *            The stereotypes applied on <code>element</code>
	 * @return The check result
	 */
	private int allows(ChildRule rule, EClass origin, EClass element, Collection<EClass> stereotypes) {
		EClass ce = rule.getElement();
		EClass co = rule.getOrigin();
		if ((ce == null || ce.isSuperTypeOf(element))
				&& (co == null || co.isSuperTypeOf(origin))) {
			// matching type => check the application of the required stereotypes
			for (EClass stereotype : rule.getStereotypes()) {
				// The profile may be a dynamic definition, so compare stereotypes in the same resource set context
				EClass localStereotype = resolveDynamicStereotype(element, stereotype);
				if (!stereotypes.contains(localStereotype)) {
					return RESULT_UNKNOWN;
				}
			}
			return rule.isPermit() ? RESULT_PERMIT : RESULT_DENY;
		} else {
			// type is not matching => unknown
			return RESULT_UNKNOWN;
		}
	}

	/**
	 * Checks a palette element against a rule
	 *
	 * @param rule
	 *            The palette rule
	 * @param toolID
	 *            The palette element's ID
	 * @return The check result
	 */
	private int allows(PaletteRule rule, String toolID) {
		String elem = rule.getElement();
		boolean applies = (elem == null);
		applies = applies || (elem.length() == 0);
		applies = applies || (elem != null && elem.equals(toolID));
		applies = applies || (elem != null && elem.endsWith("*") && toolID.startsWith(elem.substring(0, elem.length() - 1)));
		if (applies) {
			return rule.isPermit() ? RESULT_PERMIT : RESULT_DENY;
		}
		return RESULT_UNKNOWN;
	}

	/**
	 * Checks a modeling assistant element against a rule.
	 *
	 * @param rule
	 *            The assistant rule
	 * @param elementType
	 *            The modeling assistant element type
	 * @return The check result
	 */
	private int allows(AssistantRule rule, IElementType elementType) {
		return rule.matches(elementType)
				? (rule.isPermit() ? RESULT_PERMIT : RESULT_DENY)
				: RESULT_UNKNOWN;
	}
}
