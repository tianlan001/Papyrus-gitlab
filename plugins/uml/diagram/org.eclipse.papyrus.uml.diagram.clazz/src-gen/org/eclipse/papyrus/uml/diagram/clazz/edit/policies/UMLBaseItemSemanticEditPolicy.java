/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.clazz.edit.policies;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractBaseItemSemanticEditPolicy;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.InformationFlow;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.TemplateableElement;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.UseCase;

/**
 * @generated
 */
public class UMLBaseItemSemanticEditPolicy extends AbstractBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected UMLBaseItemSemanticEditPolicy(IElementType elementType) {
		super(elementType);
	}

	/**
	 * @generated
	 */
	@Override
	protected String getVisualIdFromView(View view) {
		return UMLVisualIDRegistry.getVisualID(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected IElementType getContextElementType(IEditCommandRequest request) {
		IElementType requestContextElementType = UMLElementTypes.getElementType(getVisualID(request));
		return requestContextElementType != null ? requestContextElementType : getBaseElementType();
	}

	/**
	 * @generated
	 */
	public static LinkConstraints getLinkConstraints() {
		LinkConstraints cached = UMLDiagramEditorPlugin.getInstance().getLinkConstraints();
		if (cached == null) {
			UMLDiagramEditorPlugin.getInstance().setLinkConstraints(cached = new LinkConstraints());
		}
		return cached;
	}

	/**
	 * @generated
	 */
	public static class LinkConstraints {

		/**
		 * @generated
		 */
		public LinkConstraints() {
			// use static method #getLinkConstraints() to access instance
		}

		/**
		 * @generated
		 */
		public boolean canCreateAssociationClass_TetherEdge() {
			return canExistAssociationClass_TetherEdge();
		}

		/**
		 * @generated
		 */
		public boolean canCreateAssociationClass_Edge(Package container, Type source, Type target) {
			return canExistAssociationClass_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateAssociation_Edge(Package container, Type source, Type target) {
			return canExistAssociation_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateAssociation_BranchEdge(Package container, Type source, Type target) {
			return canExistAssociation_BranchEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateGeneralization_Edge(Classifier source, Classifier target) {
			return canExistGeneralization_Edge(null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateInterfaceRealization_Edge(BehavioredClassifier source, Interface target) {
			return canExistInterfaceRealization_Edge(null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateSubstitution_Edge(Classifier container, Classifier source, Classifier target) {
			return canExistSubstitution_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateRealization_Edge(Package container, NamedElement source, NamedElement target) {
			return canExistRealization_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateAbstraction_Edge(Package container, NamedElement source, NamedElement target) {
			return canExistAbstraction_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateUsage_Edge(Package container, NamedElement source, NamedElement target) {
			return canExistUsage_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateDependency_Edge(Package container, NamedElement source, NamedElement target) {
			return canExistDependency_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateDependency_BranchEdge(Package container, NamedElement source, NamedElement target) {
			return canExistDependency_BranchEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateElementImport_Edge(Namespace source, PackageableElement target) {
			return canExistElementImport_Edge(null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreatePackageImport_Edge(Namespace source, Package target) {
			return canExistPackageImport_Edge(null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreatePackageMerge_Edge(Package container, Package source, Package target) {
			return canExistPackageMerge_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateProfileApplication_Edge(Package source, Profile target) {
			return canExistProfileApplication_Edge(null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateComment_AnnotatedElementEdge(Comment source, Element target) {
			if (source != null) {
				if (source.getAnnotatedElements()
						.contains(target)) {
					return false;
				}
			}
			return canExistComment_AnnotatedElementEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateConstraint_ConstrainedElementEdge(Constraint source, Element target) {
			if (source != null) {
				if (source.getConstrainedElements()
						.contains(target)) {
					return false;
				}
			}
			return canExistConstraint_ConstrainedElementEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateTemplateBinding_Edge(TemplateableElement container, TemplateableElement source, TemplateableElement target) {
			return canExistTemplateBinding_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateGeneralizationSet_Edge(Package container, Generalization source, Generalization target) {
			return canExistGeneralizationSet_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateInstanceSpecification_Edge(Package container, Slot source, Slot target) {
			return canExistInstanceSpecification_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateElement_ContainmentEdge(Element source, Element target) {
			if (source != null) {
				if (source.getOwnedElements()
						.contains(target)) {
					return false;
				}
			}
			if (target != null && (target.getOwner() != null)) {
				return false;
			}
			return canExistElement_ContainmentEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateTimeObservation_EventEdge(TimeObservation source, NamedElement target) {
			if (source != null) {
				if (source.getEvent() != null) {
					return false;
				}
			}
			return canExistTimeObservation_EventEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateDurationObservation_EventEdge(DurationObservation source, NamedElement target) {
			if (source != null) {
				if (source.getEvents()
						.size() >= 2
						||
						source.getEvents()
								.contains(target)) {
					return false;
				}
			}
			return canExistDurationObservation_EventEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateInformationFlow_Edge(Package container, NamedElement source, NamedElement target) {
			return canExistInformationFlow_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateConstraint_ContextEdge(Constraint source, Namespace target) {
			if (source != null) {
				if (source.getContext() != null) {
					return false;
				}
			}
			if (target != null && (target.getOwnedRules()
					.contains(source))) {
				return false;
			}
			return canExistConstraint_ContextEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canExistAssociationClass_TetherEdge() {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistAssociationClass_Edge(Package container, AssociationClass linkInstance, Type source, Type target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistAssociation_Edge(Package container, Association linkInstance, Type source, Type target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistAssociation_BranchEdge(Package container, Association linkInstance, Type source, Type target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistGeneralization_Edge(Generalization linkInstance, Classifier source, Classifier target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistInterfaceRealization_Edge(InterfaceRealization linkInstance, BehavioredClassifier source, Interface target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistSubstitution_Edge(Classifier container, Substitution linkInstance, Classifier source, Classifier target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistRealization_Edge(Package container, Realization linkInstance, NamedElement source, NamedElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistAbstraction_Edge(Package container, Abstraction linkInstance, NamedElement source, NamedElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistUsage_Edge(Package container, Usage linkInstance, NamedElement source, NamedElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistDependency_Edge(Package container, Dependency linkInstance, NamedElement source, NamedElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistDependency_BranchEdge(Package container, Dependency linkInstance, NamedElement source, NamedElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistElementImport_Edge(ElementImport linkInstance, Namespace source, PackageableElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistPackageImport_Edge(PackageImport linkInstance, Namespace source, Package target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistPackageMerge_Edge(Package container, PackageMerge linkInstance, Package source, Package target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistProfileApplication_Edge(ProfileApplication linkInstance, Package source, Profile target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistComment_AnnotatedElementEdge(Comment source, Element target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistConstraint_ConstrainedElementEdge(Constraint source, Element target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistTemplateBinding_Edge(TemplateableElement container, TemplateBinding linkInstance, TemplateableElement source, TemplateableElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistGeneralizationSet_Edge(Package container, GeneralizationSet linkInstance, Generalization source, Generalization target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistInstanceSpecification_Edge(Package container, InstanceSpecification linkInstance, Slot source, Slot target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistElement_ContainmentEdge(Element source, Element target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistTimeObservation_EventEdge(TimeObservation source, NamedElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistDurationObservation_EventEdge(DurationObservation source, NamedElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistInformationFlow_Edge(Package container, InformationFlow linkInstance, NamedElement source, NamedElement target) {
			try {
				// Information Flow source constraint
				if (source != null) {
					if (!((source instanceof Actor)
							|| (source instanceof Node)
							|| (source instanceof UseCase)
							|| (source instanceof Artifact)
							|| (source instanceof Class)
							|| (source instanceof Component)
							|| (source instanceof Port)
							|| (source instanceof Property)
							|| (source instanceof Interface)
							|| (source instanceof Package)
							|| (source instanceof ActivityNode)
							|| (source instanceof ActivityPartition) || (source instanceof InstanceSpecification))) {

						return false;

					}
					if (source instanceof InstanceSpecification) {
						EList<Classifier> classes = ((InstanceSpecification) source).getClassifiers();
						for (int i = 0; i < classes.size(); i++) {
							if (classes.get(i) instanceof Relationship) {
								return false;
							}
						}
					}
				}
				// Information Flow target constraint
				if (target != null) {
					if (!((target instanceof Actor)
							|| (target instanceof Node)
							|| (target instanceof UseCase)
							|| (target instanceof Artifact)
							|| (target instanceof Class)
							|| (target instanceof Component)
							|| (target instanceof Port)
							|| (target instanceof Property)
							|| (target instanceof Interface)
							|| (target instanceof Package)
							|| (target instanceof ActivityNode)
							|| (target instanceof ActivityPartition) || (target instanceof InstanceSpecification))) {

						return false;

					}
					if (target instanceof InstanceSpecification) {
						EList<Classifier> classes = ((InstanceSpecification) target).getClassifiers();
						for (int i = 0; i < classes.size(); i++) {
							if (classes.get(i) instanceof Relationship) {
								return false;
							}
						}
					}
				}
				return true;
			} catch (Exception e) {
				UMLDiagramEditorPlugin.getInstance().logError("Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}

		/**
		 * @generated
		 */
		public boolean canExistConstraint_ContextEdge(Constraint source, Namespace target) {
			return true;
		}
	}
}
