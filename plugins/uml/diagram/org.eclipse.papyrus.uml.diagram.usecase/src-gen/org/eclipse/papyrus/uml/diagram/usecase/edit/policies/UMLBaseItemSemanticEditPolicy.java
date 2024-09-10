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
package org.eclipse.papyrus.uml.diagram.usecase.edit.policies;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractBaseItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.usecase.expressions.UMLOCLFactory;
import org.eclipse.papyrus.uml.diagram.usecase.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.usecase.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Include;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
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
		public boolean canCreateInclude_Edge(UseCase container, UseCase source, UseCase target) {
			return canExistInclude_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateExtend_Edge(UseCase container, UseCase source, UseCase target) {
			return canExistExtend_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateGeneralization_Edge(Classifier container, Classifier source, Classifier target) {
			return canExistGeneralization_Edge(container, null, source, target);
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
		public boolean canCreateDependency_Edge(Package container, NamedElement source, NamedElement target) {
			return canExistDependency_Edge(container, null, source, target);
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
		public boolean canCreateRealization_Edge(Package container, NamedElement source, NamedElement target) {
			return canExistRealization_Edge(container, null, source, target);
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
		public boolean canCreatePackageImport_Edge(Namespace source, Package target) {
			return canExistPackageImport_Edge(null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canExistInclude_Edge(UseCase container, Include linkInstance, UseCase source, UseCase target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistExtend_Edge(UseCase container, Extend linkInstance, UseCase source, UseCase target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistGeneralization_Edge(Classifier container, Generalization linkInstance, Classifier source, Classifier target) {
			try {
				if (source == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections.<String, EClassifier> singletonMap("oppositeEnd", UMLPackage.eINSTANCE.getClassifier()); //$NON-NLS-1$
					Object sourceVal = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getClassifier(), env).evaluate(source, Collections.singletonMap("oppositeEnd", target)); //$NON-NLS-1$
					if (false == sourceVal instanceof Boolean || !((Boolean) sourceVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				if (target == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections.<String, EClassifier> singletonMap("oppositeEnd", UMLPackage.eINSTANCE.getClassifier()); //$NON-NLS-1$
					Object targetVal = UMLOCLFactory.getExpression(1, UMLPackage.eINSTANCE.getClassifier(), env).evaluate(target, Collections.singletonMap("oppositeEnd", source)); //$NON-NLS-1$
					if (false == targetVal instanceof Boolean || !((Boolean) targetVal).booleanValue()) {
						return false;
					} // else fall-through
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
		public boolean canExistAssociation_Edge(Package container, Association linkInstance, Type source, Type target) {
			try {
				if ((source instanceof Class) || (source instanceof Component) || (source instanceof Actor) || (source instanceof UseCase)) {
					if ((target instanceof Class) || (target instanceof Component) || (target instanceof Actor) || (target instanceof UseCase)) {
						if ((source instanceof UseCase) && (target instanceof UseCase)) {
							return (Collections.disjoint(((UseCase) source).getSubjects(), ((UseCase) target).getSubjects()));

						}
						return true;

					}
				} else {
					return false;
				}
				if ((source instanceof Class) || (source instanceof Component) || (source instanceof Actor) || (source instanceof UseCase)) {
					if ((target instanceof Class) || (target instanceof Component) || (target instanceof Actor) || (target instanceof UseCase)) {
						if ((source instanceof UseCase) && (target instanceof UseCase)) {
							return (Collections.disjoint(((UseCase) source).getSubjects(), ((UseCase) target).getSubjects()));

						}
						return true;

					}
				} else {
					return false;
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
		public boolean canExistConstraint_ConstrainedElementEdge(Constraint source, Element target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistDependency_Edge(Package container, Dependency linkInstance, NamedElement source, NamedElement target) {
			try {
				if (target == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections.<String, EClassifier> singletonMap("oppositeEnd", UMLPackage.eINSTANCE.getNamedElement()); //$NON-NLS-1$
					Object targetVal = UMLOCLFactory.getExpression(7, UMLPackage.eINSTANCE.getNamedElement(), env).evaluate(target, Collections.singletonMap("oppositeEnd", source)); //$NON-NLS-1$
					if (false == targetVal instanceof Boolean || !((Boolean) targetVal).booleanValue()) {
						return false;
					} // else fall-through
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
		public boolean canExistComment_AnnotatedElementEdge(Comment source, Element target) {
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
		public boolean canExistRealization_Edge(Package container, Realization linkInstance, NamedElement source, NamedElement target) {
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
		public boolean canExistPackageImport_Edge(PackageImport linkInstance, Namespace source, Package target) {
			return true;
		}
	}
}
