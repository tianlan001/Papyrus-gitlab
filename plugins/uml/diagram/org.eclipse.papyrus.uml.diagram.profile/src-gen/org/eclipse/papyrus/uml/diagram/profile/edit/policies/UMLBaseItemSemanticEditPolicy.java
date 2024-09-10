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
package org.eclipse.papyrus.uml.diagram.profile.edit.policies;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractBaseItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.profile.utils.Util;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

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
		public boolean canCreateExtension_Edge(Package container, Property source, Class target) {
			return canExistExtension_Edge(container, null, source, target);
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
		public boolean canCreateProfileApplication_Edge(Package container, Package source, Profile target) {
			return canExistProfileApplication_Edge(container, null, source, target);
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
		public boolean canCreateElementImport_Edge(Namespace container, Namespace source, PackageableElement target) {
			return canExistElementImport_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreatePackageImport_Edge(Namespace container, Namespace source, Package target) {
			return canExistPackageImport_Edge(container, null, source, target);
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
		public boolean canExistExtension_Edge(Package container, Extension linkInstance, Property source, Class target) {
			try {
				// ExtensionSource
				/**
				 * we can't make a test here, because, the source must be a Property (ExtensionEnd) and it's a Stereotype
				 *
				 * @see org.eclipse.papyrus.uml.diagram.profile.custom.policies.CUMLBaseItemSemanticEditPolicy for the good test!
				 */
				// ExtensionTarget
				/**
				 * we can't make a test here, because, the source must be a Property (ExtensionEnd) and it's a Stereotype
				 *
				 * @see org.eclipse.papyrus.uml.diagram.profile.custom.policies.CUMLBaseItemSemanticEditPolicy for the good test!
				 */
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
				// AssociationSource
				if ((source instanceof Type) && Util.isMetaclass(source)) {
					return false;
				}
				if (source instanceof Extension) {
					return false;
				}
				// AssociationTarget
				if (target != null) {
					if (target instanceof Extension) {
						return false;
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
		public boolean canExistProfileApplication_Edge(Package container, ProfileApplication linkInstance, Package source, Profile target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistAssociation_BranchEdge(Package container, Association linkInstance, Type source, Type target) {
			try {
				// AssociationSource
				if ((source instanceof Type) && Util.isMetaclass(source)) {
					return false;
				}
				if (source instanceof Extension) {
					return false;
				}
				// AssociationTarget
				if (target != null) {
					if (target instanceof Extension) {
						return false;
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
		public boolean canExistGeneralization_Edge(Generalization linkInstance, Classifier source, Classifier target) {
			try {
				// GeneralizationSource
				if (!(source instanceof Classifier)) {
					return false;
				}
				if (Util.isMetaclass(source)) {
					return false;
				}
				if (source instanceof Extension) {
					return false; // meaningless
				}
				// GeneralizationTarget
				if (target != null) {
					if (!(target instanceof Classifier)) {
						return false;
					}
					if (Util.isMetaclass(target)) {
						return false;
					}
					if (target instanceof Extension) {
						return false;// meaningless
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
		public boolean canExistDependency_Edge(Package container, Dependency linkInstance, NamedElement source, NamedElement target) {
			try {
				// DependencySource
				if ((source instanceof Type) && Util.isMetaclass((Type) source)) {
					return false;
				}
				if (source instanceof Extension) {
					return false;
				}
				// DependencyTarget
				if (target != null) {
					if (target instanceof Extension) {
						return false;
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
		public boolean canExistDependency_BranchEdge(Package container, Dependency linkInstance, NamedElement source, NamedElement target) {
			try {
				// DependencySource
				if ((source instanceof Type) && Util.isMetaclass((Type) source)) {
					return false;
				}
				if (source instanceof Extension) {
					return false;
				}
				// DependencyTarget
				if (target != null) {
					if (target instanceof Extension) {
						return false;
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
		public boolean canExistElementImport_Edge(Namespace container, ElementImport linkInstance, Namespace source, PackageableElement target) {
			try {
				// ElementImportSource
				if ((source instanceof Type) && Util.isMetaclass((Type) source)) {
					return false;
				}
				if (source instanceof Extension) {
					return false;
				}
				// ElementImportTarget
				if (target != null) {
					if (target instanceof Extension) {
						return false;
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
		public boolean canExistPackageImport_Edge(Namespace container, PackageImport linkInstance, Namespace source, Package target) {
			try {
				// PackageImportSource
				if ((source instanceof Type) && Util.isMetaclass((Type) source)) {
					return false;
				}
				if (source instanceof Extension) {
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
		public boolean canExistComment_AnnotatedElementEdge(Comment source, Element target) {
			try {
				// AnnotatedElementLink target
				if ((target instanceof Type) && Util.isMetaclass((Type) target)) {
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
			try {
				// ConstraintedElementLink
				if ((target instanceof Type) && Util.isMetaclass((Type) target)) {
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
		public boolean canExistConstraint_ContextEdge(Constraint source, Namespace target) {
			return true;
		}
	}
}
