/*****************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 535653
 *****************************************************************************/
package org.eclipse.papyrus.uml.ui.editors;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.emf.utils.TextReferencesHelper;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.widgets.editors.richtext.GenericRichTextEditor;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.tools.namereferences.NameReferencesHelper;
import org.eclipse.papyrus.uml.tools.providers.SemanticUMLContentProvider;
import org.eclipse.papyrus.uml.tools.util.UMLProviderHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * @author Vincent Lorenzo
 * 
 *         This editor provides an additional button to be able to link UML references in the html text
 *
 */
public class UMLRichtextEditorWithReferences extends GenericRichTextEditor {

	/**
	 * the reference helper to use, created when we set the editedElement
	 */
	private TextReferencesHelper textReferencesHelper;

	/**
	 * the content provider to use for the reference dialog
	 */
	private IStaticContentProvider referenceContentProvider;

	/**
	 * the label provider to use for the dialog with reference
	 */
	private ILabelProvider labelProvider;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public UMLRichtextEditorWithReferences(Composite parent, int style) {
		this(parent, new UMLToolbarConfiguration(), style);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param toolbarConfig
	 * @param style
	 */
	public UMLRichtextEditorWithReferences(Composite parent, UMLToolbarConfiguration toolbarConfig, int style) {
		super(parent, toolbarConfig, style);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param toolbarConfig
	 */
	public UMLRichtextEditorWithReferences(Composite parent, UMLToolbarConfiguration toolbarConfig) {
		this(parent, toolbarConfig, SWT.NONE);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 */
	public UMLRichtextEditorWithReferences(Composite parent) {
		this(parent, SWT.NONE);
	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.editors.richtext.GenericRichTextEditor#configureEdition(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 *
	 * @param eobject
	 * @param feature
	 */
	@Override
	public void configureEdition(EObject editedElement, EStructuralFeature editedFeature) {
		super.configureEdition(editedElement, editedFeature);
		if (null != editedElement) {
			setContentProvider(createContentProvider(editedElement, editedFeature));
			setTextReferencesHelper(createTextReferencesHelper(editedElement));
			setLabelProvider(createLabelProvider(editedElement));
		}
	}



	/**
	 * We override the setText to replace all "{@link #1}" with "<ref src="#1">Name</ref>" in order to display properly the UML reference in the ckeditor
	 * 
	 * @see org.eclipse.nebula.widgets.richtext.RichTextEditor#setText(java.lang.String)
	 *
	 * @param value
	 */
	@Override
	public void setText(String value) {
		enableTags();
		String text = null;
		if (isTagsEnabled() && textReferencesHelper != null) {
			// Replace all "{@link #1}" with "<ref src="#1">Name</ref>"
			try {
				Pattern regex = Pattern.compile("(\\{\\@link)(\\s*?)(.*?)(\\s*?)(\\})", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
				Matcher matcher = regex.matcher((String) value);

				StringBuffer buffer = new StringBuffer();
				while (matcher.find()) {
					String reference = matcher.group(3).replaceAll("\\s", ""); //$NON-NLS-1$ //$NON-NLS-2$
					String name = textReferencesHelper.replaceReferences("{@link " + reference + "}"); //$NON-NLS-1$ //$NON-NLS-2$
					String realName = name.replaceAll("\\<u\\>", "").replaceAll("\\<\\/u\\>", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					matcher.appendReplacement(buffer, "<a class=\"internal_ref\" href=\"" + reference + "\">" + realName + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				matcher.appendTail(buffer);
				String result = buffer.toString();
				text = result;
			} catch (Exception e) {
				Activator.log.error(e);
				text = value;
			}
		} else {
			text = value;
		}
		super.setText(text);
	}

	/**
	 * We undo the replacement done in the {@link #setText(String)} method to store the wanted value : Replace all "<ref src="#1">Name</ref>" with "{@link #1}"
	 * 
	 * @see org.eclipse.nebula.widgets.richtext.RichTextEditor#getText()
	 *
	 * @return
	 */
	@Override
	public String getText() {
		String returnedText = super.getText();
		if (isTagsEnabled()) {
			try {
				String source = returnedText;
				// Replace all "<ref src="#1">Name</ref>" with "{@link #1}"
				if (source != null && source.length() > 0) {
					Pattern regex = Pattern.compile("(\\<a)((\\s*)|(.*?)(\\s+))(class=\\\")((\\s*)|(.*?)(\\s+))(internal_ref)((\\s*)|(\\s+)(.*?))(\\\")((\\s*)|(.*?)(\\s+))(href=\\\")(.*?)(\\\")(.*?)(\\>)(.*?)(\\<\\/a\\>)", //$NON-NLS-1$
							Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					Matcher matcher = regex.matcher(source);

					StringBuffer buffer = new StringBuffer();
					while (matcher.find()) {
						matcher.appendReplacement(buffer, "{@link " + matcher.group(22) + "}"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					matcher.appendTail(buffer);
					String result = buffer.toString();

					if (result != null && result.length() > 0) {
						regex = Pattern.compile("(\\<a)((\\s*)|(.*?)(\\s+))(href=\\\")(.*?)(\\\")((\\s*)|(.*?)(\\s+))(class=\\\")((\\s*)|(.*?)(\\s+))(internal_ref)((\\s*)|(\\s+)(.*?))(\\\")(.*?)(\\>)(.*?)(\\<\\/a\\>)", //$NON-NLS-1$
								Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
						matcher = regex.matcher(result);

						buffer = new StringBuffer();
						while (matcher.find()) {
							matcher.appendReplacement(buffer, "{@link " + matcher.group(7) + "}"); //$NON-NLS-1$ //$NON-NLS-2$
						}
						matcher.appendTail(buffer);
						result = buffer.toString();
					}

					returnedText = result;
				}
			} catch (Exception e) {
				Activator.log.error(e);
			}
		}
		return returnedText;
	}


	/**
	 * 
	 * @param editedElement
	 *            the edited element
	 * @param editedFeature
	 *            the edited feature
	 * @return
	 * 		the content provider to use for the richtext editor with reference
	 */
	private IStaticContentProvider createContentProvider(final EObject editedElement, final EStructuralFeature editedFeature) {
		IStaticContentProvider provider = null;
		if (editedElement != null && editedFeature != null) {
			final Resource baseResource = editedElement.eResource();
			ResourceSet resourceSet = null;
			if (baseResource != null) {
				resourceSet = baseResource.getResourceSet();
			}

			if (resourceSet != null) {
				// we create the contents provider
				final SemanticUMLContentProvider semanticProvider = new SemanticUMLContentProvider(editedElement, editedFeature, resourceSet);
				semanticProvider.setWantedMetaclasses(Collections.singletonList(UMLPackage.eINSTANCE.getNamedElement()));
				provider = UMLProviderHelper.encapsulateProvider(semanticProvider, editedElement, editedFeature, resourceSet);
			}
		}
		return provider;
	}

	/**
	 * 
	 * @param editedElement
	 *            the edited element
	 * @return
	 * 		the {@link TextReferencesHelper} to use
	 */
	private TextReferencesHelper createTextReferencesHelper(final EObject editedElement) {
		TextReferencesHelper helper = null;
		if (editedElement != null && editedElement.eResource() != null) {
			helper = new NameReferencesHelper(editedElement.eResource());
		}
		return helper;
	}

	/**
	 * 
	 * @param editedElement
	 *            the edited element
	 * @return
	 * 		the label provider to use for this element
	 */
	private ILabelProvider createLabelProvider(final EObject editedElement) {
		ILabelProvider labelProvider = null;
		if (editedElement != null) {
			LabelProviderService lpSvc = null;
			try {
				lpSvc = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, editedElement);
				labelProvider = lpSvc.getLabelProvider();
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
		}
		return labelProvider;
	}


	/**
	 * 
	 * @param textReferencesHelper
	 *            the text references helper to use
	 */
	public void setTextReferencesHelper(final TextReferencesHelper textReferencesHelper) {
		this.textReferencesHelper = textReferencesHelper;
	}

	/**
	 * 
	 * @return
	 * 		the text reference helper to use
	 */

	public TextReferencesHelper getTextReferencesHelper() {
		return textReferencesHelper;
	}

	/**
	 * 
	 * @param labelProvider
	 *            the label provider to use
	 */
	public void setLabelProvider(final ILabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * 
	 * @return
	 * 		the label provider to use
	 */
	public ILabelProvider getLabelProvider() {
		return this.labelProvider;
	}

	/**
	 * 
	 * @param referenceContentProvider
	 *            the reference content provider to use
	 */
	public void setContentProvider(final IStaticContentProvider referenceContentProvider) {
		this.referenceContentProvider = referenceContentProvider;
	}

	/**
	 * 
	 * @return
	 * 		the content provider to use
	 */
	public IStaticContentProvider getContentProvider() {
		return this.referenceContentProvider;
	}

}
