/*******************************************************************************
 * Copyright (c) 2008 Conselleria de Infraestructuras y Transporte,
 * Generalitat de la Comunitat Valenciana .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Francisco Javier Cano MuÃ±oz (Prodevelop) - initial API implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.common.preferences.UMLPreferencesConstants;
import org.eclipse.papyrus.uml.internationalization.edit.providers.InternationalizationUMLItemProviderAdapterFactory;
import org.eclipse.papyrus.uml.internationalization.edit.utils.InternationalizationElementItemProviderUtils;
import org.eclipse.uml2.uml.edit.providers.*;
import org.osgi.framework.Bundle;

public class AlternativeUMLItemProviderAdapterFactory extends InternationalizationUMLItemProviderAdapterFactory {

	public AlternativeUMLItemProviderAdapterFactory(IPreferenceStore preferenceStore) {
		myPreferenceStore = preferenceStore;
	}

	@Override
	public Adapter createCommentAdapter() {
		if (commentItemProvider == null) {
			commentItemProvider = new CommentItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Comment.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return commentItemProvider;
	}

	@Override
	public Adapter createDependencyAdapter() {
		if (dependencyItemProvider == null) {
			dependencyItemProvider = new DependencyItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Dependency.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return dependencyItemProvider;
	}

	@Override
	public Adapter createTemplateParameterAdapter() {
		if (templateParameterItemProvider == null) {
			templateParameterItemProvider = new TemplateParameterItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TemplateParameter.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return templateParameterItemProvider;
	}

	@Override
	public Adapter createTemplateSignatureAdapter() {
		if (templateSignatureItemProvider == null) {
			templateSignatureItemProvider = new TemplateSignatureItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TemplateSignature.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return templateSignatureItemProvider;
	}

	@Override
	public Adapter createTemplateBindingAdapter() {
		if (templateBindingItemProvider == null) {
			templateBindingItemProvider = new TemplateBindingItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TemplateBinding.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return templateBindingItemProvider;
	}

	@Override
	public Adapter createTemplateParameterSubstitutionAdapter() {
		if (templateParameterSubstitutionItemProvider == null) {
			templateParameterSubstitutionItemProvider = new TemplateParameterSubstitutionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TemplateParameterSubstitution.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return templateParameterSubstitutionItemProvider;
	}

	@Override
	public Adapter createElementImportAdapter() {
		if (elementImportItemProvider == null) {
			elementImportItemProvider = new ElementImportItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ElementImport.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return elementImportItemProvider;
	}

	@Override
	public Adapter createPackageImportAdapter() {
		if (packageImportItemProvider == null) {
			packageImportItemProvider = new PackageImportItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/PackageImport.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return packageImportItemProvider;
	}

	@Override
	public Adapter createPackageAdapter() {
		if (packageItemProvider == null) {
			packageItemProvider = new PackageItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Package.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return packageItemProvider;
	}

	@Override
	public Adapter createPackageMergeAdapter() {
		if (packageMergeItemProvider == null) {
			packageMergeItemProvider = new PackageMergeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/PackageMerge.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return packageMergeItemProvider;
	}

	@Override
	public Adapter createProfileApplicationAdapter() {
		if (profileApplicationItemProvider == null) {
			profileApplicationItemProvider = new ProfileApplicationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ProfileApplication.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return profileApplicationItemProvider;
	}

	@Override
	public Adapter createProfileAdapter() {
		if (profileItemProvider == null) {
			profileItemProvider = new ProfileItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Profile.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return profileItemProvider;
	}

	@Override
	public Adapter createStereotypeAdapter() {
		if (stereotypeItemProvider == null) {
			stereotypeItemProvider = new StereotypeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Stereotype.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stereotypeItemProvider;
	}

	@Override
	public Adapter createImageAdapter() {
		if (imageItemProvider == null) {
			imageItemProvider = new ImageItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Image.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return imageItemProvider;
	}

	@Override
	public Adapter createClassAdapter() {
		if (classItemProvider == null) {
			classItemProvider = new ClassItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Class.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return classItemProvider;
	}

	@Override
	public Adapter createGeneralizationAdapter() {
		if (generalizationItemProvider == null) {
			generalizationItemProvider = new GeneralizationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Generalization.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return generalizationItemProvider;
	}

	@Override
	public Adapter createGeneralizationSetAdapter() {
		if (generalizationSetItemProvider == null) {
			generalizationSetItemProvider = new GeneralizationSetItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/GeneralizationSet.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return generalizationSetItemProvider;
	}

	@Override
	public Adapter createUseCaseAdapter() {
		if (useCaseItemProvider == null) {
			useCaseItemProvider = new UseCaseItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/UseCase.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return useCaseItemProvider;
	}

	@Override
	public Adapter createIncludeAdapter() {
		if (includeItemProvider == null) {
			includeItemProvider = new IncludeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Include.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return includeItemProvider;
	}

	@Override
	public Adapter createExtendAdapter() {
		if (extendItemProvider == null) {
			extendItemProvider = new ExtendItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Extend.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extendItemProvider;
	}

	@Override
	public Adapter createConstraintAdapter() {
		if (constraintItemProvider == null) {
			constraintItemProvider = new ConstraintItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Constraint.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return constraintItemProvider;
	}

	@Override
	public Adapter createExtensionPointAdapter() {
		if (extensionPointItemProvider == null) {
			extensionPointItemProvider = new ExtensionPointItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ExtensionPoint.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extensionPointItemProvider;
	}

	@Override
	public Adapter createSubstitutionAdapter() {
		if (substitutionItemProvider == null) {
			substitutionItemProvider = new SubstitutionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Substitution.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return substitutionItemProvider;
	}

	@Override
	public Adapter createRealizationAdapter() {
		if (realizationItemProvider == null) {
			realizationItemProvider = new RealizationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Realization.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return realizationItemProvider;
	}

	@Override
	public Adapter createAbstractionAdapter() {
		if (abstractionItemProvider == null) {
			abstractionItemProvider = new AbstractionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Abstraction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return abstractionItemProvider;
	}

	@Override
	public Adapter createOpaqueExpressionAdapter() {
		if (opaqueExpressionItemProvider == null) {
			opaqueExpressionItemProvider = new OpaqueExpressionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/OpaqueExpression.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return opaqueExpressionItemProvider;
	}

	@Override
	public Adapter createParameterAdapter() {
		if (parameterItemProvider == null) {
			parameterItemProvider = new ParameterItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Parameter.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return parameterItemProvider;
	}

	@Override
	public Adapter createConnectorEndAdapter() {
		if (connectorEndItemProvider == null) {
			connectorEndItemProvider = new ConnectorEndItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ConnectorEnd.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return connectorEndItemProvider;
	}

	@Override
	public Adapter createPropertyAdapter() {
		if (propertyItemProvider == null) {
			propertyItemProvider = new PropertyItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Property.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return propertyItemProvider;
	}

	@Override
	public Adapter createDeploymentAdapter() {
		if (deploymentItemProvider == null) {
			deploymentItemProvider = new DeploymentItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Deployment.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return deploymentItemProvider;
	}

	@Override
	public Adapter createDeploymentSpecificationAdapter() {
		if (deploymentSpecificationItemProvider == null) {
			deploymentSpecificationItemProvider = new DeploymentSpecificationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DeploymentSpecification.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return deploymentSpecificationItemProvider;
	}

	@Override
	public Adapter createArtifactAdapter() {
		if (artifactItemProvider == null) {
			artifactItemProvider = new ArtifactItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Artifact.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return artifactItemProvider;
	}

	@Override
	public Adapter createManifestationAdapter() {
		if (manifestationItemProvider == null) {
			manifestationItemProvider = new ManifestationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Manifestation.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return manifestationItemProvider;
	}

	@Override
	public Adapter createOperationAdapter() {
		if (operationItemProvider == null) {
			operationItemProvider = new OperationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Operation.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return operationItemProvider;
	}

	@Override
	public Adapter createParameterSetAdapter() {
		if (parameterSetItemProvider == null) {
			parameterSetItemProvider = new ParameterSetItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ParameterSet.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return parameterSetItemProvider;
	}

	@Override
	public Adapter createDataTypeAdapter() {
		if (dataTypeItemProvider == null) {
			dataTypeItemProvider = new DataTypeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DataType.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return dataTypeItemProvider;
	}

	@Override
	public Adapter createInterfaceAdapter() {
		if (interfaceItemProvider == null) {
			interfaceItemProvider = new InterfaceItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Interface.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interfaceItemProvider;
	}

	@Override
	public Adapter createReceptionAdapter() {
		if (receptionItemProvider == null) {
			receptionItemProvider = new ReceptionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Reception.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return receptionItemProvider;
	}

	@Override
	public Adapter createSignalAdapter() {
		if (signalItemProvider == null) {
			signalItemProvider = new SignalItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Signal.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return signalItemProvider;
	}

	@Override
	public Adapter createProtocolStateMachineAdapter() {
		if (protocolStateMachineItemProvider == null) {
			protocolStateMachineItemProvider = new ProtocolStateMachineItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ProtocolStateMachine.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return protocolStateMachineItemProvider;
	}

	@Override
	public Adapter createStateMachineAdapter() {
		if (stateMachineItemProvider == null) {
			stateMachineItemProvider = new StateMachineItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/StateMachine.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stateMachineItemProvider;
	}

	@Override
	public Adapter createRegionAdapter() {
		if (regionItemProvider == null) {
			regionItemProvider = new RegionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Region.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return regionItemProvider;
	}

	@Override
	public Adapter createTransitionAdapter() {
		if (transitionItemProvider == null) {
			transitionItemProvider = new TransitionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Transition.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return transitionItemProvider;
	}

	@Override
	public Adapter createTriggerAdapter() {
		if (triggerItemProvider == null) {
			triggerItemProvider = new TriggerItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Trigger.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return triggerItemProvider;
	}

	@Override
	public Adapter createPortAdapter() {
		if (portItemProvider == null) {
			portItemProvider = new PortItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Port.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return portItemProvider;
	}

	@Override
	public Adapter createStateAdapter() {
		if (stateItemProvider == null) {
			stateItemProvider = new StateItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/State.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stateItemProvider;
	}

	@Override
	public Adapter createConnectionPointReferenceAdapter() {
		if (connectionPointReferenceItemProvider == null) {
			connectionPointReferenceItemProvider = new ConnectionPointReferenceItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ConnectionPointReference.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return connectionPointReferenceItemProvider;
	}

	@Override
	public Adapter createPseudostateAdapter() {
		if (pseudostateItemProvider == null) {
			pseudostateItemProvider = new PseudostateItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Pseudostate.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return pseudostateItemProvider;
	}

	@Override
	public Adapter createProtocolConformanceAdapter() {
		if (protocolConformanceItemProvider == null) {
			protocolConformanceItemProvider = new ProtocolConformanceItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ProtocolConformance.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return protocolConformanceItemProvider;
	}

	@Override
	public Adapter createOperationTemplateParameterAdapter() {
		if (operationTemplateParameterItemProvider == null) {
			operationTemplateParameterItemProvider = new OperationTemplateParameterItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/OperationTemplateParameter.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return operationTemplateParameterItemProvider;
	}

	@Override
	public Adapter createAssociationAdapter() {
		if (associationItemProvider == null) {
			associationItemProvider = new AssociationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Association.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return associationItemProvider;
	}

	@Override
	public Adapter createConnectableElementTemplateParameterAdapter() {
		if (connectableElementTemplateParameterItemProvider == null) {
			connectableElementTemplateParameterItemProvider = new ConnectableElementTemplateParameterItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ConnectableElementTemplateParameter.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return connectableElementTemplateParameterItemProvider;
	}

	@Override
	public Adapter createCollaborationUseAdapter() {
		if (collaborationUseItemProvider == null) {
			collaborationUseItemProvider = new CollaborationUseItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CollaborationUse.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return collaborationUseItemProvider;
	}

	@Override
	public Adapter createCollaborationAdapter() {
		if (collaborationItemProvider == null) {
			collaborationItemProvider = new CollaborationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Collaboration.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return collaborationItemProvider;
	}

	@Override
	public Adapter createConnectorAdapter() {
		if (connectorItemProvider == null) {
			connectorItemProvider = new ConnectorItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Connector.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return connectorItemProvider;
	}

	@Override
	public Adapter createRedefinableTemplateSignatureAdapter() {
		if (redefinableTemplateSignatureItemProvider == null) {
			redefinableTemplateSignatureItemProvider = new RedefinableTemplateSignatureItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/RedefinableTemplateSignature.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return redefinableTemplateSignatureItemProvider;
	}

	@Override
	public Adapter createClassifierTemplateParameterAdapter() {
		if (classifierTemplateParameterItemProvider == null) {
			classifierTemplateParameterItemProvider = new ClassifierTemplateParameterItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ClassifierTemplateParameter.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return classifierTemplateParameterItemProvider;
	}

	@Override
	public Adapter createInterfaceRealizationAdapter() {
		if (interfaceRealizationItemProvider == null) {
			interfaceRealizationItemProvider = new InterfaceRealizationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InterfaceRealization.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interfaceRealizationItemProvider;
	}

	@Override
	public Adapter createExtensionAdapter() {
		if (extensionItemProvider == null) {
			extensionItemProvider = new ExtensionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Extension.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extensionItemProvider;
	}

	@Override
	public Adapter createExtensionEndAdapter() {
		if (extensionEndItemProvider == null) {
			extensionEndItemProvider = new ExtensionEndItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ExtensionEnd.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return extensionEndItemProvider;
	}

	@Override
	public Adapter createStringExpressionAdapter() {
		if (stringExpressionItemProvider == null) {
			stringExpressionItemProvider = new StringExpressionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/StringExpression.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stringExpressionItemProvider;
	}

	@Override
	public Adapter createExpressionAdapter() {
		if (expressionItemProvider == null) {
			expressionItemProvider = new ExpressionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Expression.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return expressionItemProvider;
	}

	@Override
	public Adapter createLiteralIntegerAdapter() {
		if (literalIntegerItemProvider == null) {
			literalIntegerItemProvider = new LiteralIntegerItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LiteralInteger.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalIntegerItemProvider;
	}

	@Override
	public Adapter createLiteralStringAdapter() {
		if (literalStringItemProvider == null) {
			literalStringItemProvider = new LiteralStringItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LiteralString.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalStringItemProvider;
	}

	@Override
	public Adapter createLiteralBooleanAdapter() {
		if (literalBooleanItemProvider == null) {
			literalBooleanItemProvider = new LiteralBooleanItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LiteralBoolean.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalBooleanItemProvider;
	}

	@Override
	public Adapter createLiteralNullAdapter() {
		if (literalNullItemProvider == null) {
			literalNullItemProvider = new LiteralNullItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LiteralNull.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalNullItemProvider;
	}

	@Override
	public Adapter createSlotAdapter() {
		if (slotItemProvider == null) {
			slotItemProvider = new SlotItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Slot.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return slotItemProvider;
	}

	@Override
	public Adapter createInstanceSpecificationAdapter() {
		if (instanceSpecificationItemProvider == null) {
			instanceSpecificationItemProvider = new InstanceSpecificationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InstanceSpecification.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return instanceSpecificationItemProvider;
	}

	@Override
	public Adapter createEnumerationAdapter() {
		if (enumerationItemProvider == null) {
			enumerationItemProvider = new EnumerationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Enumeration.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return enumerationItemProvider;
	}

	@Override
	public Adapter createEnumerationLiteralAdapter() {
		if (enumerationLiteralItemProvider == null) {
			enumerationLiteralItemProvider = new EnumerationLiteralItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/EnumerationLiteral.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return enumerationLiteralItemProvider;
	}

	@Override
	public Adapter createPrimitiveTypeAdapter() {
		if (primitiveTypeItemProvider == null) {
			primitiveTypeItemProvider = new PrimitiveTypeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/PrimitiveType.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return primitiveTypeItemProvider;
	}

	@Override
	public Adapter createInstanceValueAdapter() {
		if (instanceValueItemProvider == null) {
			instanceValueItemProvider = new InstanceValueItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InstanceValue.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return instanceValueItemProvider;
	}

	@Override
	public Adapter createLiteralUnlimitedNaturalAdapter() {
		if (literalUnlimitedNaturalItemProvider == null) {
			literalUnlimitedNaturalItemProvider = new LiteralUnlimitedNaturalItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LiteralUnlimitedNatural.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return literalUnlimitedNaturalItemProvider;
	}

	@Override
	public Adapter createOpaqueBehaviorAdapter() {
		if (opaqueBehaviorItemProvider == null) {
			opaqueBehaviorItemProvider = new OpaqueBehaviorItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/OpaqueBehavior.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return opaqueBehaviorItemProvider;
	}

	@Override
	public Adapter createFunctionBehaviorAdapter() {
		if (functionBehaviorItemProvider == null) {
			functionBehaviorItemProvider = new FunctionBehaviorItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/FunctionBehavior.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return functionBehaviorItemProvider;
	}

	@Override
	public Adapter createActorAdapter() {
		if (actorItemProvider == null) {
			actorItemProvider = new ActorItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Actor.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return actorItemProvider;
	}

	@Override
	public Adapter createUsageAdapter() {
		if (usageItemProvider == null) {
			usageItemProvider = new UsageItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Usage.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return usageItemProvider;
	}

	@Override
	public Adapter createMessageAdapter() {
		if (messageItemProvider == null) {
			messageItemProvider = new MessageItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Message.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return messageItemProvider;
	}

	@Override
	public Adapter createInteractionAdapter() {
		if (interactionItemProvider == null) {
			interactionItemProvider = new InteractionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Interaction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionItemProvider;
	}

	@Override
	public Adapter createLifelineAdapter() {
		if (lifelineItemProvider == null) {
			lifelineItemProvider = new LifelineItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Lifeline.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return lifelineItemProvider;
	}

	@Override
	public Adapter createPartDecompositionAdapter() {
		if (partDecompositionItemProvider == null) {
			partDecompositionItemProvider = new PartDecompositionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/PartDecomposition.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return partDecompositionItemProvider;
	}

	@Override
	public Adapter createInteractionUseAdapter() {
		if (interactionUseItemProvider == null) {
			interactionUseItemProvider = new InteractionUseItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InteractionUse.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionUseItemProvider;
	}

	@Override
	public Adapter createGateAdapter() {
		if (gateItemProvider == null) {
			gateItemProvider = new GateItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Gate.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return gateItemProvider;
	}

	@Override
	public Adapter createActivityAdapter() {
		if (activityItemProvider == null) {
			activityItemProvider = new ActivityItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Activity.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityItemProvider;
	}

	@Override
	public Adapter createActivityPartitionAdapter() {
		if (activityPartitionItemProvider == null) {
			activityPartitionItemProvider = new ActivityPartitionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ActivityPartition.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityPartitionItemProvider;
	}

	@Override
	public Adapter createStructuredActivityNodeAdapter() {
		if (structuredActivityNodeItemProvider == null) {
			structuredActivityNodeItemProvider = new StructuredActivityNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/StructuredActivityNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return structuredActivityNodeItemProvider;
	}

	@Override
	public Adapter createVariableAdapter() {
		if (variableItemProvider == null) {
			variableItemProvider = new VariableItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Variable.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return variableItemProvider;
	}

	@Override
	public Adapter createInterruptibleActivityRegionAdapter() {
		if (interruptibleActivityRegionItemProvider == null) {
			interruptibleActivityRegionItemProvider = new InterruptibleActivityRegionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InterruptibleActivityRegion.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interruptibleActivityRegionItemProvider;
	}

	@Override
	public Adapter createExceptionHandlerAdapter() {
		if (exceptionHandlerItemProvider == null) {
			exceptionHandlerItemProvider = new ExceptionHandlerItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ExceptionHandler.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return exceptionHandlerItemProvider;
	}

	@Override
	public Adapter createOutputPinAdapter() {
		if (outputPinItemProvider == null) {
			outputPinItemProvider = new OutputPinItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/OutputPin.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return outputPinItemProvider;
	}



	@Override
	public Adapter createInputPinAdapter() {
		if (inputPinItemProvider == null) {
			inputPinItemProvider = new InputPinItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InputPin.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return inputPinItemProvider;
	}

	@Override
	public Adapter createGeneralOrderingAdapter() {
		if (generalOrderingItemProvider == null) {
			generalOrderingItemProvider = new GeneralOrderingItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/GeneralOrdering.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return generalOrderingItemProvider;
	}

	@Override
	public Adapter createOccurrenceSpecificationAdapter() {
		if (occurrenceSpecificationItemProvider == null) {
			occurrenceSpecificationItemProvider = new OccurrenceSpecificationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/OccurrenceSpecification.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return occurrenceSpecificationItemProvider;
	}

	@Override
	public Adapter createInteractionOperandAdapter() {
		if (interactionOperandItemProvider == null) {
			interactionOperandItemProvider = new InteractionOperandItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InteractionOperand.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionOperandItemProvider;
	}

	@Override
	public Adapter createInteractionConstraintAdapter() {
		if (interactionConstraintItemProvider == null) {
			interactionConstraintItemProvider = new InteractionConstraintItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InteractionConstraint.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return interactionConstraintItemProvider;
	}

	@Override
	public Adapter createExecutionOccurrenceSpecificationAdapter() {
		if (executionOccurrenceSpecificationItemProvider == null) {
			executionOccurrenceSpecificationItemProvider = new ExecutionOccurrenceSpecificationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ExecutionOccurrenceSpecification.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return executionOccurrenceSpecificationItemProvider;
	}




	@Override
	public Adapter createStateInvariantAdapter() {
		if (stateInvariantItemProvider == null) {
			stateInvariantItemProvider = new StateInvariantItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/StateInvariant.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return stateInvariantItemProvider;
	}

	@Override
	public Adapter createActionExecutionSpecificationAdapter() {
		if (actionExecutionSpecificationItemProvider == null) {
			actionExecutionSpecificationItemProvider = new ActionExecutionSpecificationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ActionExecutionSpecification.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return actionExecutionSpecificationItemProvider;
	}

	@Override
	public Adapter createBehaviorExecutionSpecificationAdapter() {
		if (behaviorExecutionSpecificationItemProvider == null) {
			behaviorExecutionSpecificationItemProvider = new BehaviorExecutionSpecificationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/BehaviorExecutionSpecification.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return behaviorExecutionSpecificationItemProvider;
	}





	@Override
	public Adapter createMessageOccurrenceSpecificationAdapter() {
		if (messageOccurrenceSpecificationItemProvider == null) {
			messageOccurrenceSpecificationItemProvider = new MessageOccurrenceSpecificationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/MessageOccurrenceSpecification.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return messageOccurrenceSpecificationItemProvider;
	}


	@Override
	public Adapter createCombinedFragmentAdapter() {
		if (combinedFragmentItemProvider == null) {
			combinedFragmentItemProvider = new CombinedFragmentItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CombinedFragment.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return combinedFragmentItemProvider;
	}

	@Override
	public Adapter createContinuationAdapter() {
		if (continuationItemProvider == null) {
			continuationItemProvider = new ContinuationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Continuation.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return continuationItemProvider;
	}

	@Override
	public Adapter createConsiderIgnoreFragmentAdapter() {
		if (considerIgnoreFragmentItemProvider == null) {
			considerIgnoreFragmentItemProvider = new ConsiderIgnoreFragmentItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ConsiderIgnoreFragment.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return considerIgnoreFragmentItemProvider;
	}

	@Override
	public Adapter createCallEventAdapter() {
		if (callEventItemProvider == null) {
			callEventItemProvider = new CallEventItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CallEvent.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return callEventItemProvider;
	}

	@Override
	public Adapter createChangeEventAdapter() {
		if (changeEventItemProvider == null) {
			changeEventItemProvider = new ChangeEventItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ChangeEvent.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return changeEventItemProvider;
	}

	@Override
	public Adapter createSignalEventAdapter() {
		if (signalEventItemProvider == null) {
			signalEventItemProvider = new SignalEventItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/SignalEvent.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return signalEventItemProvider;
	}

	@Override
	public Adapter createAnyReceiveEventAdapter() {
		if (anyReceiveEventItemProvider == null) {
			anyReceiveEventItemProvider = new AnyReceiveEventItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/AnyReceiveEvent.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return anyReceiveEventItemProvider;
	}

	@Override
	public Adapter createCreateObjectActionAdapter() {
		if (createObjectActionItemProvider == null) {
			createObjectActionItemProvider = new CreateObjectActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CreateObjectAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return createObjectActionItemProvider;
	}

	@Override
	public Adapter createDestroyObjectActionAdapter() {
		if (destroyObjectActionItemProvider == null) {
			destroyObjectActionItemProvider = new DestroyObjectActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DestroyObjectAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return destroyObjectActionItemProvider;
	}

	@Override
	public Adapter createTestIdentityActionAdapter() {
		if (testIdentityActionItemProvider == null) {
			testIdentityActionItemProvider = new TestIdentityActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TestIdentityAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return testIdentityActionItemProvider;
	}

	@Override
	public Adapter createReadSelfActionAdapter() {
		if (readSelfActionItemProvider == null) {
			readSelfActionItemProvider = new ReadSelfActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadSelfAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readSelfActionItemProvider;
	}

	@Override
	public Adapter createReadStructuralFeatureActionAdapter() {
		if (readStructuralFeatureActionItemProvider == null) {
			readStructuralFeatureActionItemProvider = new ReadStructuralFeatureActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadStructuralFeatureAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readStructuralFeatureActionItemProvider;
	}

	@Override
	public Adapter createClearStructuralFeatureActionAdapter() {
		if (clearStructuralFeatureActionItemProvider == null) {
			clearStructuralFeatureActionItemProvider = new ClearStructuralFeatureActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ClearStructuralFeatureAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clearStructuralFeatureActionItemProvider;
	}

	@Override
	public Adapter createRemoveStructuralFeatureValueActionAdapter() {
		if (removeStructuralFeatureValueActionItemProvider == null) {
			removeStructuralFeatureValueActionItemProvider = new RemoveStructuralFeatureValueActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/RemoveStructuralFeatureValueAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return removeStructuralFeatureValueActionItemProvider;
	}

	@Override
	public Adapter createAddStructuralFeatureValueActionAdapter() {
		if (addStructuralFeatureValueActionItemProvider == null) {
			addStructuralFeatureValueActionItemProvider = new AddStructuralFeatureValueActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/AddStructuralFeatureValueAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return addStructuralFeatureValueActionItemProvider;
	}

	@Override
	public Adapter createLinkEndDataAdapter() {
		if (linkEndDataItemProvider == null) {
			linkEndDataItemProvider = new LinkEndDataItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LinkEndData.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return linkEndDataItemProvider;
	}

	@Override
	public Adapter createQualifierValueAdapter() {
		if (qualifierValueItemProvider == null) {
			qualifierValueItemProvider = new QualifierValueItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/QualifierValue.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return qualifierValueItemProvider;
	}

	@Override
	public Adapter createReadLinkActionAdapter() {
		if (readLinkActionItemProvider == null) {
			readLinkActionItemProvider = new ReadLinkActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadLinkAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readLinkActionItemProvider;
	}

	@Override
	public Adapter createLinkEndCreationDataAdapter() {
		if (linkEndCreationDataItemProvider == null) {
			linkEndCreationDataItemProvider = new LinkEndCreationDataItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LinkEndCreationData.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return linkEndCreationDataItemProvider;
	}

	@Override
	public Adapter createCreateLinkActionAdapter() {
		if (createLinkActionItemProvider == null) {
			createLinkActionItemProvider = new CreateLinkActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CreateLinkAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return createLinkActionItemProvider;
	}

	@Override
	public Adapter createDestroyLinkActionAdapter() {
		if (destroyLinkActionItemProvider == null) {
			destroyLinkActionItemProvider = new DestroyLinkActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DestroyLinkAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return destroyLinkActionItemProvider;
	}

	@Override
	public Adapter createLinkEndDestructionDataAdapter() {
		if (linkEndDestructionDataItemProvider == null) {
			linkEndDestructionDataItemProvider = new LinkEndDestructionDataItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LinkEndDestructionData.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return linkEndDestructionDataItemProvider;
	}

	@Override
	public Adapter createClearAssociationActionAdapter() {
		if (clearAssociationActionItemProvider == null) {
			clearAssociationActionItemProvider = new ClearAssociationActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ClearAssociationAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clearAssociationActionItemProvider;
	}

	@Override
	public Adapter createBroadcastSignalActionAdapter() {
		if (broadcastSignalActionItemProvider == null) {
			broadcastSignalActionItemProvider = new BroadcastSignalActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/BroadcastSignalAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return broadcastSignalActionItemProvider;
	}

	@Override
	public Adapter createSendObjectActionAdapter() {
		if (sendObjectActionItemProvider == null) {
			sendObjectActionItemProvider = new SendObjectActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/SendObjectAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return sendObjectActionItemProvider;
	}

	@Override
	public Adapter createValueSpecificationActionAdapter() {
		if (valueSpecificationActionItemProvider == null) {
			valueSpecificationActionItemProvider = new ValueSpecificationActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ValueSpecificationAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return valueSpecificationActionItemProvider;
	}

	@Override
	public Adapter createTimeExpressionAdapter() {
		if (timeExpressionItemProvider == null) {
			timeExpressionItemProvider = new TimeExpressionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TimeExpression.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeExpressionItemProvider;
	}

	@Override
	public Adapter createDurationAdapter() {
		if (durationItemProvider == null) {
			durationItemProvider = new DurationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Duration.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationItemProvider;
	}

	@Override
	public Adapter createValuePinAdapter() {
		if (valuePinItemProvider == null) {
			valuePinItemProvider = new ValuePinItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ValuePin.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return valuePinItemProvider;
	}

	@Override
	public Adapter createDurationIntervalAdapter() {
		if (durationIntervalItemProvider == null) {
			durationIntervalItemProvider = new DurationIntervalItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DurationInterval.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationIntervalItemProvider;
	}

	@Override
	public Adapter createIntervalAdapter() {
		if (intervalItemProvider == null) {
			intervalItemProvider = new IntervalItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Interval.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return intervalItemProvider;
	}

	@Override
	public Adapter createTimeConstraintAdapter() {
		if (timeConstraintItemProvider == null) {
			timeConstraintItemProvider = new TimeConstraintItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TimeConstraint.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeConstraintItemProvider;
	}

	@Override
	public Adapter createIntervalConstraintAdapter() {
		if (intervalConstraintItemProvider == null) {
			intervalConstraintItemProvider = new IntervalConstraintItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/IntervalConstraint.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return intervalConstraintItemProvider;
	}

	@Override
	public Adapter createTimeIntervalAdapter() {
		if (timeIntervalItemProvider == null) {
			timeIntervalItemProvider = new TimeIntervalItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TimeInterval.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeIntervalItemProvider;
	}

	@Override
	public Adapter createDurationConstraintAdapter() {
		if (durationConstraintItemProvider == null) {
			durationConstraintItemProvider = new DurationConstraintItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DurationConstraint.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationConstraintItemProvider;
	}

	@Override
	public Adapter createTimeObservationAdapter() {
		if (timeObservationItemProvider == null) {
			timeObservationItemProvider = new TimeObservationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TimeObservation.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeObservationItemProvider;
	}

	@Override
	public Adapter createDurationObservationAdapter() {
		if (durationObservationItemProvider == null) {
			durationObservationItemProvider = new DurationObservationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DurationObservation.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return durationObservationItemProvider;
	}

	@Override
	public Adapter createOpaqueActionAdapter() {
		if (opaqueActionItemProvider == null) {
			opaqueActionItemProvider = new OpaqueActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/OpaqueAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return opaqueActionItemProvider;
	}

	@Override
	public Adapter createSendSignalActionAdapter() {
		if (sendSignalActionItemProvider == null) {
			sendSignalActionItemProvider = new SendSignalActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/SendSignalAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return sendSignalActionItemProvider;
	}

	@Override
	public Adapter createCallOperationActionAdapter() {
		if (callOperationActionItemProvider == null) {
			callOperationActionItemProvider = new CallOperationActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CallOperationAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return callOperationActionItemProvider;
	}

	@Override
	public Adapter createCallBehaviorActionAdapter() {
		if (callBehaviorActionItemProvider == null) {
			callBehaviorActionItemProvider = new CallBehaviorActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CallBehaviorAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return callBehaviorActionItemProvider;
	}

	@Override
	public Adapter createInformationItemAdapter() {
		if (informationItemItemProvider == null) {
			informationItemItemProvider = new InformationItemItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InformationItem.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return informationItemItemProvider;
	}

	@Override
	public Adapter createInformationFlowAdapter() {
		if (informationFlowItemProvider == null) {
			informationFlowItemProvider = new InformationFlowItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InformationFlow.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return informationFlowItemProvider;
	}

	@Override
	public Adapter createModelAdapter() {
		if (modelItemProvider == null) {
			modelItemProvider = new ModelItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Model.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return modelItemProvider;
	}

	@Override
	public Adapter createReadVariableActionAdapter() {
		if (readVariableActionItemProvider == null) {
			readVariableActionItemProvider = new ReadVariableActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadVariableAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readVariableActionItemProvider;
	}

	@Override
	public Adapter createClearVariableActionAdapter() {
		if (clearVariableActionItemProvider == null) {
			clearVariableActionItemProvider = new ClearVariableActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ClearVariableAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clearVariableActionItemProvider;
	}

	@Override
	public Adapter createAddVariableValueActionAdapter() {
		if (addVariableValueActionItemProvider == null) {
			addVariableValueActionItemProvider = new AddVariableValueActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/AddVariableValueAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return addVariableValueActionItemProvider;
	}

	@Override
	public Adapter createRemoveVariableValueActionAdapter() {
		if (removeVariableValueActionItemProvider == null) {
			removeVariableValueActionItemProvider = new RemoveVariableValueActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/RemoveVariableValueAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return removeVariableValueActionItemProvider;
	}

	@Override
	public Adapter createRaiseExceptionActionAdapter() {
		if (raiseExceptionActionItemProvider == null) {
			raiseExceptionActionItemProvider = new RaiseExceptionActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/RaiseExceptionAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return raiseExceptionActionItemProvider;
	}

	@Override
	public Adapter createActionInputPinAdapter() {
		if (actionInputPinItemProvider == null) {
			actionInputPinItemProvider = new ActionInputPinItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ActionInputPin.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return actionInputPinItemProvider;
	}

	@Override
	public Adapter createReadExtentActionAdapter() {
		if (readExtentActionItemProvider == null) {
			readExtentActionItemProvider = new ReadExtentActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadExtentAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readExtentActionItemProvider;
	}

	@Override
	public Adapter createReclassifyObjectActionAdapter() {
		if (reclassifyObjectActionItemProvider == null) {
			reclassifyObjectActionItemProvider = new ReclassifyObjectActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReclassifyObjectAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return reclassifyObjectActionItemProvider;
	}

	@Override
	public Adapter createReadIsClassifiedObjectActionAdapter() {
		if (readIsClassifiedObjectActionItemProvider == null) {
			readIsClassifiedObjectActionItemProvider = new ReadIsClassifiedObjectActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadIsClassifiedObjectAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readIsClassifiedObjectActionItemProvider;
	}

	@Override
	public Adapter createStartClassifierBehaviorActionAdapter() {
		if (startClassifierBehaviorActionItemProvider == null) {
			startClassifierBehaviorActionItemProvider = new StartClassifierBehaviorActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/StartClassifierBehaviorAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return startClassifierBehaviorActionItemProvider;
	}

	@Override
	public Adapter createReadLinkObjectEndActionAdapter() {
		if (readLinkObjectEndActionItemProvider == null) {
			readLinkObjectEndActionItemProvider = new ReadLinkObjectEndActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadLinkObjectEndAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readLinkObjectEndActionItemProvider;
	}

	@Override
	public Adapter createReadLinkObjectEndQualifierActionAdapter() {
		if (readLinkObjectEndQualifierActionItemProvider == null) {
			readLinkObjectEndQualifierActionItemProvider = new ReadLinkObjectEndQualifierActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReadLinkObjectEndQualifierAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return readLinkObjectEndQualifierActionItemProvider;
	}

	@Override
	public Adapter createCreateLinkObjectActionAdapter() {
		if (createLinkObjectActionItemProvider == null) {
			createLinkObjectActionItemProvider = new CreateLinkObjectActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CreateLinkObjectAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return createLinkObjectActionItemProvider;
	}

	@Override
	public Adapter createAcceptEventActionAdapter() {
		if (acceptEventActionItemProvider == null) {
			acceptEventActionItemProvider = new AcceptEventActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/AcceptEventAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return acceptEventActionItemProvider;
	}

	@Override
	public Adapter createAcceptCallActionAdapter() {
		if (acceptCallActionItemProvider == null) {
			acceptCallActionItemProvider = new AcceptCallActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/AcceptCallAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return acceptCallActionItemProvider;
	}

	@Override
	public Adapter createReplyActionAdapter() {
		if (replyActionItemProvider == null) {
			replyActionItemProvider = new ReplyActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReplyAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return replyActionItemProvider;
	}

	@Override
	public Adapter createUnmarshallActionAdapter() {
		if (unmarshallActionItemProvider == null) {
			unmarshallActionItemProvider = new UnmarshallActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/UnmarshallAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return unmarshallActionItemProvider;
	}

	@Override
	public Adapter createReduceActionAdapter() {
		if (reduceActionItemProvider == null) {
			reduceActionItemProvider = new ReduceActionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ReduceAction.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return reduceActionItemProvider;
	}

	@Override
	public Adapter createControlFlowAdapter() {
		if (controlFlowItemProvider == null) {
			controlFlowItemProvider = new ControlFlowItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ControlFlow.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return controlFlowItemProvider;
	}

	@Override
	public Adapter createInitialNodeAdapter() {
		if (initialNodeItemProvider == null) {
			initialNodeItemProvider = new InitialNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/InitialNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return initialNodeItemProvider;
	}

	@Override
	public Adapter createActivityParameterNodeAdapter() {
		if (activityParameterNodeItemProvider == null) {
			activityParameterNodeItemProvider = new ActivityParameterNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ActivityParameterNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityParameterNodeItemProvider;
	}

	@Override
	public Adapter createForkNodeAdapter() {
		if (forkNodeItemProvider == null) {
			forkNodeItemProvider = new ForkNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ForkNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return forkNodeItemProvider;
	}

	@Override
	public Adapter createFlowFinalNodeAdapter() {
		if (flowFinalNodeItemProvider == null) {
			flowFinalNodeItemProvider = new FlowFinalNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/FlowFinalNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return flowFinalNodeItemProvider;
	}

	@Override
	public Adapter createCentralBufferNodeAdapter() {
		if (centralBufferNodeItemProvider == null) {
			centralBufferNodeItemProvider = new CentralBufferNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CentralBufferNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return centralBufferNodeItemProvider;
	}

	@Override
	public Adapter createMergeNodeAdapter() {
		if (mergeNodeItemProvider == null) {
			mergeNodeItemProvider = new MergeNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/MergeNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return mergeNodeItemProvider;
	}

	@Override
	public Adapter createDecisionNodeAdapter() {
		if (decisionNodeItemProvider == null) {
			decisionNodeItemProvider = new DecisionNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DecisionNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return decisionNodeItemProvider;
	}

	@Override
	public Adapter createActivityFinalNodeAdapter() {
		if (activityFinalNodeItemProvider == null) {
			activityFinalNodeItemProvider = new ActivityFinalNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ActivityFinalNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return activityFinalNodeItemProvider;
	}

	@Override
	public Adapter createJoinNodeAdapter() {
		if (joinNodeItemProvider == null) {
			joinNodeItemProvider = new JoinNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/JoinNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return joinNodeItemProvider;
	}

	@Override
	public Adapter createDataStoreNodeAdapter() {
		if (dataStoreNodeItemProvider == null) {
			dataStoreNodeItemProvider = new DataStoreNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/DataStoreNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return dataStoreNodeItemProvider;
	}

	@Override
	public Adapter createObjectFlowAdapter() {
		if (objectFlowItemProvider == null) {
			objectFlowItemProvider = new ObjectFlowItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ObjectFlow.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return objectFlowItemProvider;
	}

	@Override
	public Adapter createSequenceNodeAdapter() {
		if (sequenceNodeItemProvider == null) {
			sequenceNodeItemProvider = new SequenceNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/SequenceNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return sequenceNodeItemProvider;
	}

	@Override
	public Adapter createConditionalNodeAdapter() {
		if (conditionalNodeItemProvider == null) {
			conditionalNodeItemProvider = new ConditionalNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ConditionalNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return conditionalNodeItemProvider;
	}

	@Override
	public Adapter createClauseAdapter() {
		if (clauseItemProvider == null) {
			clauseItemProvider = new ClauseItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Clause.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return clauseItemProvider;
	}

	@Override
	public Adapter createLoopNodeAdapter() {
		if (loopNodeItemProvider == null) {
			loopNodeItemProvider = new LoopNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/LoopNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return loopNodeItemProvider;
	}

	@Override
	public Adapter createExpansionNodeAdapter() {
		if (expansionNodeItemProvider == null) {
			expansionNodeItemProvider = new ExpansionNodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ExpansionNode.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return expansionNodeItemProvider;
	}

	@Override
	public Adapter createExpansionRegionAdapter() {
		if (expansionRegionItemProvider == null) {
			expansionRegionItemProvider = new ExpansionRegionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ExpansionRegion.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return expansionRegionItemProvider;
	}

	@Override
	public Adapter createComponentRealizationAdapter() {
		if (componentRealizationItemProvider == null) {
			componentRealizationItemProvider = new ComponentRealizationItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ComponentRealization.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return componentRealizationItemProvider;
	}

	@Override
	public Adapter createComponentAdapter() {
		if (componentItemProvider == null) {
			componentItemProvider = new ComponentItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Component.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return componentItemProvider;
	}

	@Override
	public Adapter createNodeAdapter() {
		if (nodeItemProvider == null) {
			nodeItemProvider = new NodeItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Node.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return nodeItemProvider;
	}

	@Override
	public Adapter createDeviceAdapter() {
		if (deviceItemProvider == null) {
			deviceItemProvider = new DeviceItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/Device.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return deviceItemProvider;
	}

	@Override
	public Adapter createExecutionEnvironmentAdapter() {
		if (executionEnvironmentItemProvider == null) {
			executionEnvironmentItemProvider = new ExecutionEnvironmentItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ExecutionEnvironment.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return executionEnvironmentItemProvider;
	}

	@Override
	public Adapter createCommunicationPathAdapter() {
		if (communicationPathItemProvider == null) {
			communicationPathItemProvider = new CommunicationPathItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/CommunicationPath.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return communicationPathItemProvider;
	}

	@Override
	public Adapter createFinalStateAdapter() {
		if (finalStateItemProvider == null) {
			finalStateItemProvider = new FinalStateItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/FinalState.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return finalStateItemProvider;
	}

	@Override
	public Adapter createTimeEventAdapter() {
		if (timeEventItemProvider == null) {
			timeEventItemProvider = new TimeEventItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/TimeEvent.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return timeEventItemProvider;
	}

	@Override
	public Adapter createProtocolTransitionAdapter() {
		if (protocolTransitionItemProvider == null) {
			protocolTransitionItemProvider = new ProtocolTransitionItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/ProtocolTransition.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return protocolTransitionItemProvider;
	}

	@Override
	public Adapter createAssociationClassAdapter() {
		if (associationClassItemProvider == null) {
			associationClassItemProvider = new AssociationClassItemProvider(this) {

				@Override
				public Object getImage(Object object) {
					if (useAlternativeIcons(object)) {
						return overlayImage(object, FileLocator.find(UML_BUNDLE, new Path("icons/obj16/AssociationClass.gif"), null)); //$NON-NLS-1$
					}
					return super.getImage(object);
				}

				@Override
				protected StringBuffer appendLabel(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendLabel(text, object, shouldTranslate());
				}

				@Override
				protected StringBuffer appendKeywords(final StringBuffer text, final Object object) {
					return InternationalizationElementItemProviderUtils.appendKeywords(text, object, shouldTranslate());
				}
			};
		}
		return associationClassItemProvider;
	}

	private boolean useAlternativeIcons(Object object) {
		return UMLPreferencesConstants.PREF_ICON_STYLE_CHEERFUL.equals(myPreferenceStore.getString(UMLPreferencesConstants.PREF_ICON_STYLE));
	}

	private static final Bundle UML_BUNDLE = Platform.getBundle("org.eclipse.papyrus.uml.diagram.common.common"); //$NON-NLS-1$

	private IPreferenceStore myPreferenceStore;
}
