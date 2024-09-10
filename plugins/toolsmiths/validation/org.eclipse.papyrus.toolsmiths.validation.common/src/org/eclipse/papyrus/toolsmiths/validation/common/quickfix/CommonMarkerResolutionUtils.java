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
 *   Christian W. Damus - bugs 570097, 573788, 573986
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import static java.util.function.Predicate.not;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.tools.util.Try;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Helper class with utility methods used by {@linkplain IMarkerResolution marker resolutions}.
 */
public final class CommonMarkerResolutionUtils {

	/** Returns the model name stored in the marker attribute {@link CommonProblemConstants#MODEL_NAME}. */
	public static Optional<String> getModelName(IMarker marker) {
		return Optional.ofNullable(marker.getAttribute(CommonProblemConstants.MODEL_NAME, null));
	}

	/** Returns the model path stored in the marker attribute {@link CommonProblemConstants#MODEL_PATH}. */
	public static Optional<IPath> getModelPath(IMarker marker) {
		return Optional.ofNullable(marker.getAttribute(CommonProblemConstants.MODEL_PATH, null)).map(Path::new);
	}

	/** Get the object of the given {@code type} from an editing {@code domain}, identified by the URI in the {@code marker}. */
	public static <T extends EObject> Optional<T> getModelObject(IMarker marker, Class<T> type, EditingDomain domain) {
		return getModelObject(marker, EValidator.URI_ATTRIBUTE, type, domain);
	}

	/** Get the object of the given {@code type} from an editing {@code domain}, identified by an URI {@code attribute} in the {@code marker}. */
	public static <T extends EObject> Optional<T> getModelObject(IMarker marker, String attribute, Class<T> type, EditingDomain domain) {
		return getURIs(marker, attribute)
				.map(uri -> domain.getResourceSet().getEObject(uri, true))
				.filter(type::isInstance)
				.findAny()
				.map(type::cast);
	}

	private static Stream<URI> getURIs(IMarker marker, String attribute) {
		Stream<URI> result = Stream.of(marker.getAttribute(attribute, "")) //$NON-NLS-1$
				.filter(not(String::isBlank))
				.map(uri -> URI.createURI(uri, true));

		if (EValidator.URI_ATTRIBUTE.equals(attribute)) {
			// Also get relateds, if any
			String related = marker.getAttribute(EValidator.RELATED_URIS_ATTRIBUTE, ""); //$NON-NLS-1$
			if (!related.isBlank()) {
				result = Stream.concat(result, Stream.of(related.split("\\s+")) //$NON-NLS-1$
						.filter(not(String::isBlank))
						.map(uri -> URI.decode(uri)) // Multiple URIs are fragment-encoded per EMF
						.map(uri -> URI.createURI(uri, true)));
			}
		}

		return result;
	}

	public static Optional<EAttribute> getModelAttribute(IMarker marker) {
		return getModelFeature(marker, EPackage.Registry.INSTANCE).filter(EAttribute.class::isInstance).map(EAttribute.class::cast);
	}

	public static Optional<EReference> getModelReference(IMarker marker) {
		return getModelFeature(marker, EPackage.Registry.INSTANCE).filter(EReference.class::isInstance).map(EReference.class::cast);
	}

	public static Optional<EStructuralFeature> getModelFeature(IMarker marker) {
		return getModelFeature(marker, EPackage.Registry.INSTANCE);
	}

	public static Optional<EStructuralFeature> getModelFeature(IMarker marker, EditingDomain domain) {
		return getModelFeature(marker, domain.getResourceSet().getPackageRegistry());
	}

	public static Optional<EStructuralFeature> getModelFeature(IMarker marker, EPackage.Registry packageRegistry) {
		return getURIs(marker, EValidator.URI_ATTRIBUTE)
				.map(uri -> {
					EPackage ePackage = packageRegistry.getEPackage(uri.trimFragment().toString());
					return ePackage == null ? null : ePackage.eResource().getEObject(uri.fragment());
				})
				.filter(EStructuralFeature.class::isInstance)
				.findAny()
				.map(EStructuralFeature.class::cast);
	}

	/**
	 * Get a value encoded in a marker attribute.
	 *
	 * @param <V>
	 *            the value type
	 * @param marker
	 *            the marker describing the problem
	 * @param attribute
	 *            the marker attribute that encodes the value
	 * @param type
	 *            the value type
	 * @param dataType
	 *            the EMF representation of the value type, used to restore non-primitive values. May be {@code null}
	 *            for boolean, integer, or string values
	 * @return the value encoded in the marker
	 */
	public static <V> Optional<V> getValue(IMarker marker, String attribute, Class<V> type, EDataType dataType) {
		if (type == boolean.class || type == Boolean.class) {
			@SuppressWarnings("unchecked") // No reasonable way to handle primitive wrappers
			V safeCast = (V) Boolean.valueOf(marker.getAttribute(attribute, false));
			return Optional.ofNullable(safeCast);
		} else if (type == int.class || type == Integer.class) {
			@SuppressWarnings("unchecked") // No reasonable way to handle primitive wrappers
			V safeCast = (V) Integer.valueOf(marker.getAttribute(attribute, 0));
			return Optional.ofNullable(safeCast);
		} else if (type == String.class) {
			return Optional.ofNullable(type.cast(marker.getAttribute(attribute, (String) null)));
		} else {
			return Optional.ofNullable(marker.getAttribute(attribute, (String) null))
					.map(value -> EcoreUtil.createFromString(dataType, value))
					.map(type::cast);
		}
	}

	/**
	 * Get the editing domain from an editor that is currently open on the resource of a {@code marker}, if any.
	 *
	 * @param marker
	 *            a problem marker
	 * @return the editing domain from an existing open EMF-based editor, which may be a {@linkplain Try#isSuccess() <tt>null</tt> success}
	 *         if there is no open editor or {@linkplain Try#isFailure() a failure} if there is an open editor but it is dirty and the
	 *         user elected not to save it when prompted
	 */
	public static Try<EditingDomain> getOpenEditingDomain(IMarker marker) {
		IResource resource = marker.getResource();
		if (!(resource instanceof IFile) || !PlatformUI.isWorkbenchRunning()) {
			return Try.success(null);
		}

		IEditorInput editorInput = new FileEditorInput((IFile) resource);

		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		return Stream.of(windows).map(IWorkbenchWindow::getActivePage)
				.flatMap(page -> Stream.of(page.findEditors(editorInput, null, IWorkbenchPage.MATCH_INPUT)))
				.filter(Objects::nonNull)
				.map(CommonMarkerResolutionUtils::getEditingDomain)
				.filter(Objects::nonNull)
				.findAny().orElseGet(Try::empty);
	}

	private static Try<EditingDomain> getEditingDomain(IEditorReference editorRef) {
		Try<EditingDomain> result = Try.empty();

		IEditorPart editor = editorRef.getEditor(true);
		if (editor instanceof IEditingDomainProvider) {
			if (!ensureClean(editor)) {
				result = Try.failure("dirty editor"); //$NON-NLS-1$
			} else {
				result = Try.success(((IEditingDomainProvider) editor).getEditingDomain());
			}
		}
		if (result.isEmpty() && editor != null) {
			result = Try.success(editor.getAdapter(EditingDomain.class));
		}

		return result;
	}

	private static boolean ensureClean(IEditorPart editor) {
		boolean result = !editor.isDirty();

		if (!result) {
			IWorkbenchPage page = editor.getSite().getPage();
			if (page.getActiveEditor() != editor) {
				page.activate(editor);
			}

			if (MessageDialog.openQuestion(editor.getSite().getShell(), Messages.CommonMarkerResolutionUtils_0, NLS.bind(Messages.CommonMarkerResolutionUtils_1, editor.getTitle()))) {
				result = page.saveEditor(editor, false);
			}
		}

		return result;
	}

	/**
	 * Find the open editor that has the given editing domain.
	 *
	 * @param editingDomain
	 *            an editing domain
	 * @return the option editor using that domain
	 */
	public static Optional<IEditorPart> getEditor(EditingDomain editingDomain) {
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		return Stream.of(windows).map(IWorkbenchWindow::getActivePage)
				.map(IWorkbenchPage::getEditorReferences).flatMap(Stream::of)
				.map(ref -> ref.getEditor(false))
				.filter(Objects::nonNull)
				.filter(hasEditingDomain(editingDomain))
				.findAny();
	}

	private static Predicate<IEditorPart> hasEditingDomain(EditingDomain editingDomain) {
		return editor -> {
			EditingDomain myDomain = null;

			if (editor instanceof IEditingDomainProvider) {
				myDomain = ((IEditingDomainProvider) editor).getEditingDomain();
			}
			if (myDomain == null) {
				myDomain = editor.getAdapter(EditingDomain.class);
			}

			return myDomain == editingDomain;
		};
	}
}
