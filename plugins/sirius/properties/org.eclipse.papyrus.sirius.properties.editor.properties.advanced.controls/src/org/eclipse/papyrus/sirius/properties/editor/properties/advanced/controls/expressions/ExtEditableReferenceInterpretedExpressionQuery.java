/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.utils.PapyrusReferenceUtils;
import org.eclipse.sirius.business.api.dialect.description.AbstractInterpretedExpressionQuery;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQuery;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.internal.dialect.description.ToolInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.common.tools.api.interpreter.VariableType;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;
import org.eclipse.sirius.properties.GroupDescription;
import org.eclipse.sirius.properties.PageDescription;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.sirius.properties.core.internal.SiriusToolServices;
import org.eclipse.sirius.properties.core.internal.expressions.DomainClassSwitch;
import org.eclipse.sirius.properties.core.internal.expressions.VSMNavigation;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Extension;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.tool.ToolPackage;
import org.eclipse.sirius.viewpoint.description.validation.ValidationPackage;

/**
 * An {@code IInterpretedExpressionQuery} for expressions occuring inside Ext
 * Editable reference descriptions.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@SuppressWarnings({ "restriction", "removal", "deprecation" })
public final class ExtEditableReferenceInterpretedExpressionQuery extends AbstractInterpretedExpressionQuery
		implements IInterpretedExpressionQuery {
	private Collection<EPackage> packagesToImport;

	/**
	 * Constructor.
	 *
	 * @param target
	 *            the VSM element on which the expression appears.
	 * @param expressionAttribute
	 *            the attribute of the VSM element which defines the
	 *            expression (assumed to be an InterpredExpression).
	 */
	public ExtEditableReferenceInterpretedExpressionQuery(EObject target, EStructuralFeature expressionAttribute) {
		super(target, expressionAttribute);
	}

	@Override
	protected void initializeTargetSwitch() {
		this.targetSwitch = new PropertiesExpressionsGlobalTargetSwitch(feature);
	}

	@Override
	public Collection<EPackage> getPackagesToImport() {
		/*
		 * We can't rely on the default implementation here, as it assumes we are inside
		 * a RepresentationDescription, which is not the case for properties
		 * definitions.
		 */
		if (packagesToImport == null) {
			packagesToImport = getEPackagesInScope(target);
		}
		return packagesToImport;
	}

	@Override
	public Collection<String> getDependencies() {
		/*
		 * We can't rely on the default implementation here, as it assumes we are inside
		 * a Viewpoint, which is not the case for properties definitions.
		 */
		if (dependencies == null) {
			Collection<String> result = new ArrayList<>();
			result.addAll(VSMNavigation.getJavaExtensionsInVSM(target));
			// Make sure the implicitly registered SiriusToolServices class is
			// also visible.
			result.add(SiriusToolServices.class.getName());
			dependencies = result;
		}
		return dependencies;
	}

	private static Collection<EPackage> getEPackagesInScope(EObject target) {
		Collection<EPackage> result = new LinkedHashSet<>();

		boolean needsGlobalPackages = false;
		for (RepresentationDescription desc : VSMNavigation.getRepresentationDescriptionsInVSM(target)) {
			EList<EPackage> configured = desc.getMetamodel();
			result.addAll(configured);
			if (configured.isEmpty()) {
				/*
				 * If at least one of the possible source representations has no explicitly
				 * configured metamodel, we must include the globally registered packages.
				 */
				needsGlobalPackages = true;
			}
		}

		if (needsGlobalPackages) {
			result.addAll(getAllRegisteredEPackages(EPackage.Registry.INSTANCE));
		}

		// Also add metamodels explicitly added to the ViewExtensionDescription,
		// if any.
		Option<EObject> viewDescriptionOpt = new EObjectQuery(target)
				.getFirstAncestorOfType(PropertiesPackage.Literals.VIEW_EXTENSION_DESCRIPTION);
		if (viewDescriptionOpt.some()) {
			ViewExtensionDescription ved = (ViewExtensionDescription) viewDescriptionOpt.get();
			result.addAll(ved.getMetamodels());
		}

		// In all cases, we make sure the core Sirius metamodels are present...
		result.add(EcorePackage.eINSTANCE);
		result.add(ViewpointPackage.eINSTANCE);
		result.add(DescriptionPackage.eINSTANCE);
		result.add(ToolPackage.eINSTANCE);
		result.add(ValidationPackage.eINSTANCE);
		// ... and the properties view one too.
		result.add(PropertiesPackage.eINSTANCE);

		return result;
	}

	private static Collection<EPackage> getAllRegisteredEPackages(Registry source) {
		Collection<EPackage> result = new LinkedHashSet<>();
		Set<String> nsURIs = new LinkedHashSet<>();
		nsURIs.addAll(source.keySet());
		for (String nsURI : nsURIs) {
			try {
				EPackage ePackage = source.getEPackage(nsURI);
				if (ePackage != null) {
					result.add(ePackage);
				}
				// CHECKSTYLE:OFF
			} catch (Throwable e) {
				/*
				 * anything might happen here depending on the other Eclipse tools, and we've
				 * seen many time tools breaking all the others.
				 */
				// CHECKSTYLE:ON
			}
		}
		return result;
	}

	@Override
	public Map<String, VariableType> getAvailableVariables() {
		if (availableVariables == null) {
			availableVariables = new LinkedHashMap<>();
		}

		availableVariables.put(EEFExpressionUtils.INPUT, VariableType.fromJavaClass(SiriusInputDescriptor.class));

		availableVariables.put(EEFExpressionUtils.EEFList.SELECTION, VariableType.ANY_EOBJECT);
		availableVariables.put(PapyrusReferenceUtils.NEW_ELEMENT_TYPE_NAME, VariableType.fromJavaClass(String.class));
		availableVariables.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER, VariableType.ANY_EOBJECT);
		availableVariables.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER_FEATURE_NAME, VariableType.fromJavaClass(String.class));
		return availableVariables;
	}

	/**
	 * The switch used to compute domainClasses for expressions used in properties
	 * definitions.
	 *
	 * @author pcdavid
	 */
	private static class PropertiesExpressionsGlobalTargetSwitch implements IInterpretedExpressionTargetSwitch {
		/**
		 * The switch for properties-specific expressions.
		 */
		private final DomainClassSwitch propertiesSwitch;

		/**
		 * The switch we delegate to for Model Operations.
		 */
		private final ToolInterpretedExpressionTargetSwitch delegateSwitch;

		/**
		 * By default ToolInterpretedExpressionTargetSwitch assumes operations will
		 * appear inside representations or mappings, so we override
		 * getFirstContextChangingContainer() to locate the parent GroupDescription
		 * instead.
		 */
		private static class CustomToolInterpretedExpressionTargetSwitch extends ToolInterpretedExpressionTargetSwitch {
			CustomToolInterpretedExpressionTargetSwitch(EStructuralFeature feature,
					IInterpretedExpressionTargetSwitch defaultSwitch) {
				super(feature, defaultSwitch);
			}

			@Override
			protected EObject getFirstContextChangingContainer(EObject element) {
				EObject defaultResult = super.getFirstContextChangingContainer(element);
				if (defaultResult instanceof Extension) {
					/*
					 * The generic algorithm in the super-class does not know anything about the
					 * properties metamodel but will stop at the top-level ViewExtensionDescription
					 * as it is an Extension.
					 */
					return VSMNavigation.findClosestGroupDescription(element);
				} else {
					return defaultResult;
				}
			}
		}

		PropertiesExpressionsGlobalTargetSwitch(EStructuralFeature feature) {
			this.propertiesSwitch = new DomainClassSwitch(feature);
			this.delegateSwitch = new CustomToolInterpretedExpressionTargetSwitch(feature, this);
		}

		@Override
		public Option<Collection<String>> doSwitch(EObject target, boolean considerFeature) {
			Collection<String> targetTypes = new LinkedHashSet<>();
			Option<Collection<String>> expressionTarget = Options.newSome(targetTypes);
			if (target != null) {
				if (expressionTarget.some() && expressionTarget.get().isEmpty()) {
					propertiesSwitch.setConsiderFeature(considerFeature);
					expressionTarget = propertiesSwitch.doSwitch(target);
				}
				if (expressionTarget.some() && expressionTarget.get().isEmpty()) {
					delegateSwitch.setConsiderFeature(considerFeature);
					expressionTarget = delegateSwitch.doSwitch(target);
				}
			}
			return expressionTarget;
		}

		@Override
		public EObject getFirstRelevantContainer(EObject obj) {
			if (obj != null) {
				EObject container = obj.eContainer();
				while (container != null && !isRelevant(container)) {
					container = container.eContainer();
				}
				return container;
			} else {
				return null;
			}
		}

		/**
		 * In this context, relevant containers are top-level pages and groups only. The
		 * root ViewExtensionDescriptions do not define anything relevant for domain
		 * class computation.
		 */
		private boolean isRelevant(EObject container) {
			return container instanceof PageDescription || container instanceof GroupDescription;
		}
	}
}
