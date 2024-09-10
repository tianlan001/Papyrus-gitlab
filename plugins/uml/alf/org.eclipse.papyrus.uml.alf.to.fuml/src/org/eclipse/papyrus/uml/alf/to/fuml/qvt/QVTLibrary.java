/*****************************************************************************
 * Copyright (c) 2013-2015 CEA LIST.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ed Seidewitz (IJI/MDS)
 * 
 *****************************************************************************/

package org.eclipse.papyrus.uml.alf.to.fuml.qvt;

import java.io.StringReader;

import org.eclipse.m2m.qvt.oml.blackbox.java.Module;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;
import org.eclipse.papyrus.uml.alf.AlfStandaloneSetup;
import org.eclipse.papyrus.uml.alf.Expression;
import org.eclipse.papyrus.uml.alf.SyntaxElement;
import org.eclipse.papyrus.uml.alf.services.AlfGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;

import com.google.inject.Injector;

@Module(packageURIs = { "http://www.omg.org/spec/ALF/20120827" })
public class QVTLibrary {

	public QVTLibrary() {
		super();
	}

	@Operation(contextual = true, kind = Kind.QUERY)
	public static String serialize(SyntaxElement element) {
		Injector injector = new AlfStandaloneSetup().createInjectorAndDoEMFRegistration();
		ISerializer serializer = injector.getInstance(ISerializer.class);
		SaveOptions options = SaveOptions.newBuilder().format().noValidation().getOptions();
		return serializer.serialize(element, options);
	}
	
	@Operation(contextual = true, kind = Kind.QUERY)
	public static Expression parseExpression(String text) {
		Injector injector = new AlfStandaloneSetup().createInjectorAndDoEMFRegistration();
		IParser parser = injector.getInstance(IParser.class);
		AlfGrammarAccess grammar = (AlfGrammarAccess) injector.getInstance(IGrammarAccess.class);
		IParseResult result = parser.parse(grammar.getExpressionRule(), new StringReader(text));
		return result.hasSyntaxErrors()? null: (Expression)result.getRootASTElement();
	}

}
