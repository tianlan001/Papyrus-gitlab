/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;

public class DefaultParserHintAdapter extends ParserHintAdapter {

	private Diagram diagram = null;

	/** Constructor */
	public DefaultParserHintAdapter(Diagram diagram, EObject element, String parserHint) {
		super(element, parserHint);
		this.diagram = diagram;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {

		if (adapter.equals(Diagram.class)) {
			return diagram;
		}

		return super.getAdapter(adapter);
	}
}
