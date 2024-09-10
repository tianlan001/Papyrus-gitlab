/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
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
 *  IJI - Initial implementation
 * 
 *****************************************************************************/

package org.eclipse.papyrus.uml.alf.tests.mapper;

import java.io.StringReader;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.alf.MappingError;
import org.eclipse.papyrus.uml.alf.ParsingError;
import org.eclipse.papyrus.uml.alf.tests.mapper.AlfMapper;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;

public class AlfCompiler extends AlfMapper {

	protected Profile actionLanguageProfile = null;
	protected Stereotype textualRepresentationStereotype = null;

	protected IParser parser = null;

	public AlfCompiler() throws Exception {
		super();

		this.parser = this.getInjector().getInstance(IParser.class);

		this.actionLanguageProfile = this.getActionLanguageProfile();
		for (Stereotype stereotype : this.actionLanguageProfile.getOwnedStereotypes()) {
			if ("TextualRepresentation".equals(stereotype.getName())) {
				this.textualRepresentationStereotype = stereotype;
				break;
			}
		}
	}

	public EObject parse(String textualRepresentation) throws ParsingError {
		IParseResult result = parser.parse(new StringReader(textualRepresentation));
		if (result.hasSyntaxErrors()) {
			throw new ParsingError(result.getSyntaxErrors());
		} else {
			return result.getRootASTElement();
		}
	}
	
	public Comment getTextualRepresentationComment(Element element) {
		for (Comment comment : element.getOwnedComments()) {
			if (comment.isStereotypeApplied(this.textualRepresentationStereotype) &&
					"Alf".equals(comment.getValue(this.textualRepresentationStereotype, "language"))) {
				return comment;
			}
		}
		return null;
	}

	public String getTextualRepresentation(Element element) {
		Comment comment = this.getTextualRepresentationComment(element);
		return comment == null? null: comment.getBody();
	}

	public void addTextualRepresentation(Element element, String textualRepresentation) {
		Comment comment = UMLFactory.eINSTANCE.createComment();
		comment.setBody(textualRepresentation);
		comment.getAnnotatedElements().add(element);
		element.getOwnedComments().add(comment);
		if (!comment.isStereotypeApplicable(this.textualRepresentationStereotype)) {
			element.getModel().applyProfile(this.actionLanguageProfile);
		}
		comment.applyStereotype(this.textualRepresentationStereotype);
		comment.setValue(this.textualRepresentationStereotype, "language", "Alf");
	}
	
	public void updateTextualRepresentation(Element element, String textualRepresentation) {
		Comment comment = this.getTextualRepresentationComment(element);
		if (comment == null) {
			this.addTextualRepresentation(element, textualRepresentation);
		} else {
			comment.setBody(textualRepresentation);
		}
	}

	public void compile(NamedElement contextElement) throws ParsingError, MappingError {
		String textualRepresentation = this.getTextualRepresentation(contextElement);
		if (textualRepresentation != null) {
			this.compile(contextElement, textualRepresentation);
		}
	}

	public void compile(NamedElement contextElement, String textualRepresentation)
			throws ParsingError, MappingError {

		List<EObject> alf = new BasicEList<EObject>();
		alf.add(this.parse(textualRepresentation));
		this.map(contextElement, alf);

		this.updateTextualRepresentation(contextElement, textualRepresentation);
	}

}
