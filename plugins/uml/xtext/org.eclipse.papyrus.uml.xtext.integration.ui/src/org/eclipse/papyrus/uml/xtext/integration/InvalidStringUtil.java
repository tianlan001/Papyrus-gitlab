/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.xtext.integration;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.uml.extensionpoints.Registry;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * @author Arnaud Cuccuru
 *
 */
public class InvalidStringUtil {

	/**
	 * Stereotype name for textual representation
	 */
	public static final String TEXTUAL_REPRESENTATION = "TextualRepresentation"; //$NON-NLS-1$

	/**
	 * Attribute of TextualRepresentation stereotype that indicates used language
	 */
	public static final String LANGUAGE = "language"; //$NON-NLS-1$

	public static final String ACTION_LANGUAGE_PROFILE_NAME = "ActionLanguage"; //$NON-NLS-1$

	/**
	 * The Action Language profile
	 */
	protected static Profile actionLanguageProfile;

	/**
	 * The TextualRepresentation stereotype (from the Action Language profile)
	 */
	protected static Stereotype textualRepresentationStereotype;

	/**
	 * @param element
	 * @param args
	 * @return
	 */
	public static String getTextualRepresentation(Element element) {
		Comment textualRepresentationComment = getTextualRepresentationComment(element);
		if (textualRepresentationComment == null) {
			return null;
		} else {
			return textualRepresentationComment.getBody();
		}
	}

	/**
	 * @param element
	 * @return
	 */
	public static Comment getTextualRepresentationComment(Element element) {
		Comment textualRepresentationComment = null;
		for (Comment comment : element.getOwnedComments()) {
			if (comment.getBody() != null
					&& isATextualRepresentationComment(comment)) {
				textualRepresentationComment = comment;
			}
		}
		return textualRepresentationComment;
	}

	/**
	 * @param comment
	 * @return
	 */
	public static boolean isATextualRepresentationComment(Comment comment) {
		if (textualRepresentationStereotype != null) {
			return comment.getAppliedStereotypes().contains(
					textualRepresentationStereotype);
		} else {
			// There is a chance to find it if the root model has ActionLanguage
			// profile applied. In this case, the stereotype is included in
			// comment.getApplicableStereotypes()
			List<Stereotype> applicableStereotypes = comment.getApplicableStereotypes();
			for (int i = 0; i < applicableStereotypes.size()
					&& textualRepresentationStereotype == null; i++) {
				if (applicableStereotypes.get(i).getName().equals(TEXTUAL_REPRESENTATION)) {
					textualRepresentationStereotype = applicableStereotypes.get(i);
				}
			}
		}
		return textualRepresentationStereotype != null;
	}

	/**
	 * @param element
	 * @return
	 */
	public static boolean isActionLanguageProfileApplied(Element element) {
		if (actionLanguageProfile == null) {
			IRegisteredProfile registeredActionLanguageProfile = Registry.getRegisteredProfile(ACTION_LANGUAGE_PROFILE_NAME, null);
			URI modelUri = registeredActionLanguageProfile.getUri();
			Package root = PackageUtil.getRootPackage(element);

			Resource modelResource = root.eResource().getResourceSet().getResource(modelUri, true);
			if (modelResource.getContents().get(0) instanceof Profile) {
				actionLanguageProfile = (Profile) modelResource.getContents().get(0);
			}
		}
		List<Profile> appliedProfiles = PackageUtil.getRootPackage(element).getAppliedProfiles();
		return appliedProfiles.contains(actionLanguageProfile);
	}

	/**
	 * @param element
	 * @param languageId
	 * @return
	 */
	public static Comment createTextualRepresentationComment(Element element, String languageId) {
		Comment textualRepresentationComment = element.createOwnedComment();
		if (!isActionLanguageProfileApplied(element)) {
			PackageUtil.applyProfile(
					PackageUtil.getRootPackage(element),
					actionLanguageProfile, true);
		}
		clean();
		// Force retrieval of variable textualRepresentationStereotype (side effect of evaluation below)
		isATextualRepresentationComment(textualRepresentationComment);
		textualRepresentationComment
				.applyStereotype(textualRepresentationStereotype);
		textualRepresentationComment.setValue(textualRepresentationStereotype, LANGUAGE, languageId);
		return textualRepresentationComment;
	}

	public static void clean() {
		actionLanguageProfile = null;
		textualRepresentationStereotype = null;
	}
}
