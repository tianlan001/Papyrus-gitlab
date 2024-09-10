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

package org.eclipse.papyrus.toolsmiths.validation.architecture.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ProjectManagementUtils.getExtensionElements;
import static org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ProjectManagementUtils.hasAttribute;
import static org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ProjectManagementUtils.resourcePathIs;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.Concern;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.Stakeholder;
import org.eclipse.papyrus.infra.core.architecture.util.ArchitectureSwitch;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.extensionpoints.IElementTypeSetExtensionPoint;
import org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.architecture.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ArchitectureIndex;
import org.eclipse.pde.core.plugin.IPluginElement;

/**
 * Custom validation rules for <em>Architecture Domain</em> models.
 */
public class ArchitectureCustomValidator extends CustomModelChecker.SwitchValidator {

	private static final String ELEM_ELEMENT_TYPE_SET = "elementTypeSet"; //$NON-NLS-1$

	private final ArchitectureSwitch<Boolean> explicitMergeTester = new ExplicitMergeTester();

	public ArchitectureCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(ADElement element, DiagnosticChain diagnostics, Map<Object, Object> context) {
		validateImplicitMerge(element, diagnostics, context);
	}

	public void validate(RepresentationKind representation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		validateRepresentationKindUsed(representation, diagnostics, context);
	}

	public void validate(ArchitectureContext architecture, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (architecture.getId() != null) { // Missing ID is reported separately
			architecture.getElementTypes().forEach(typeSet -> validateElementTypesContextID(architecture, typeSet, diagnostics, context));
		}
		if (!architecture.isExtension()) {
			validateContextHasRepresentationsAdvice(architecture, diagnostics, context);
		}
	}

	private void validateElementTypesContextID(ArchitectureContext architecture, ElementTypeSetConfiguration typeSet, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Collection<IPluginElement> registrations = typeSet == null || typeSet.eIsProxy()
				? List.of()
				: getExtensionElements(IElementTypeSetExtensionPoint.EXTENSION_POINT_ID, ELEM_ELEMENT_TYPE_SET)
						.filter(resourcePathIs(IElementTypeSetExtensionPoint.PATH, typeSet.eResource().getURI()))
						.collect(Collectors.toList());

		if (!registrations.isEmpty() && registrations.stream().noneMatch(hasAttribute(IElementTypeSetExtensionPoint.CLIENT_CONTEXT_ID, architecture.getId()::equals))) {
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, architecture, format(Messages.ArchitectureCustomValidator_2, context, typeSet)));
		}
	}

	private void validateRepresentationKindUsed(RepresentationKind representation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!findArchitectureContextReference(representation)) {
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, representation, format(Messages.ArchitectureCustomValidator_3, context, representation)));
		}
	}

	/**
	 * Search the registered architecture context models to find any {@link ArchitectureViewpoint viewpoint} that
	 * references the given {@code representation}.
	 *
	 * @param representation
	 *            a representation kind
	 * @return whether any viewpoint references it
	 */
	protected boolean findArchitectureContextReference(RepresentationKind representation) {
		// The simplest case is a reference within the same architecture model
		boolean result = !requireCrossReferenceAdapter(representation)
				.getInverseReferences(representation, ArchitecturePackage.Literals.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS, false).isEmpty();

		if (!result) {
			// Look for references from other registered architecture models (including from the workspace)
			result = ArchitectureIndex.getInstance().isReferenced(representation, ArchitecturePackage.Literals.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS);
		}

		return result;
	}

	private ECrossReferenceAdapter requireCrossReferenceAdapter(EObject object) {
		ECrossReferenceAdapter result = ECrossReferenceAdapter.getCrossReferenceAdapter(object);

		if (result == null) {
			result = new ECrossReferenceAdapter();
			Resource resource = object.eResource();
			ResourceSet rset = (resource != null) ? resource.getResourceSet() : null;
			if (rset != null) {
				rset.eAdapters().add(result);
			} else if (resource != null) {
				resource.eAdapters().add(result);
			} else {
				object.eAdapters().add(result);
			}
		}

		return result;
	}

	private void validateImplicitMerge(ADElement element, DiagnosticChain diagnostics, Map<Object, Object> context) {
		String name = element.getQualifiedName();
		if (name != null // Otherwise, that's a different problem reported separately
				&& !isExplicitlyMerged(element)) { // We're only checking for accidental implicit merges
			Collection<ADElement> all = ArchitectureIndex.getInstance().getElementsByQualifiedName(element.eClass(), element.getQualifiedName());
			if (all.size() > 1) {
				diagnostics.add(createDiagnostic(Diagnostic.WARNING, element, format(Messages.ArchitectureCustomValidator_4, context, element)));
			}
		}
	}

	private boolean isExplicitlyMerged(ADElement element) {
		return explicitMergeTester.doSwitch(element);
	}

	boolean hasSpecializations(ArchitectureContext context) {
		return ArchitectureIndex.getInstance().isReferenced(context, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT);
	}

	boolean hasExtensions(ArchitectureContext context) {
		return ArchitectureIndex.getInstance().isReferenced(context, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS);
	}

	/**
	 * Check whether an {@code architecture} references the <em>Element Types Configurations</em> model that provides the
	 * representations advice, whether itself, or by inheritance or extension.
	 */
	void validateContextHasRepresentationsAdvice(ArchitectureContext architecture, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean hasAdvice = hasRepresentationsAdvice(architecture);
		if (!hasAdvice && architecture.getGeneralContext() != null) {
			// Does not need to be recursive because extension content is not inherited
			hasAdvice = architecture.allGeneralContexts().stream().anyMatch(this::hasRepresentationsAdvice);
		}
		if (!hasAdvice && hasExtensions(architecture)) {
			hasAdvice = ArchitectureIndex.getInstance().getAllExtensions(architecture).stream().anyMatch(this::hasOrInheritsRepresentationsAdvice);
		}

		if (!hasAdvice) {
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, architecture, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__ELEMENT_TYPES,
					format(Messages.ArchitectureCustomValidator_0, context, architecture),
					IPluginChecker2.problem(ArchitecturePluginValidationConstants.MISSING_REPRESENTATIONS_ADVICE_ID)));
		}
	}

	private boolean hasOrInheritsRepresentationsAdvice(ArchitectureContext architecture) {
		boolean result = hasRepresentationsAdvice(architecture);
		if (!result && architecture.getGeneralContext() != null) {
			result = architecture.allGeneralContexts().stream().anyMatch(this::hasRepresentationsAdvice);
		}
		return result;
	}

	private boolean hasRepresentationsAdvice(ArchitectureContext architecture) {
		URIConverter converter = EMFHelper.getResourceSet(architecture).getURIConverter();
		URI normalized = converter.normalize(ArchitecturePluginValidationConstants.REPRESENTATIONS_ADVICE_URI);

		@SuppressWarnings("unchecked")
		EList<? extends EObject> representations = (EList<? extends EObject>) architecture.eGet(ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__ELEMENT_TYPES, false);
		return representations.stream().map(EcoreUtil::getURI).map(URI::trimFragment).map(converter::normalize).anyMatch(normalized::equals);
	}

	//
	// Nested types
	//

	private final class ExplicitMergeTester extends ArchitectureSwitch<Boolean> {

		@Override
		public Boolean defaultCase(EObject object) {
			return false;
		}

		@Override
		public Boolean caseArchitectureContext(ArchitectureContext object) {
			return object.getGeneralContext() != null || object.isExtension()
					|| hasSpecializations(object) || hasExtensions(object);
		}

		@Override
		public Boolean caseArchitectureDomain(ArchitectureDomain object) {
			return object.getContexts().stream().anyMatch(this::doSwitch);
		}

		@Override
		public Boolean caseConcern(Concern object) {
			return object.getDomain() != null && doSwitch(object.getDomain());
		}

		@Override
		public Boolean caseStakeholder(Stakeholder object) {
			return object.getDomain() != null && doSwitch(object.getDomain());
		}

		@Override
		public Boolean caseArchitectureViewpoint(ArchitectureViewpoint object) {
			return object.getContext() != null && doSwitch(object.getContext());
		}

	}

}
