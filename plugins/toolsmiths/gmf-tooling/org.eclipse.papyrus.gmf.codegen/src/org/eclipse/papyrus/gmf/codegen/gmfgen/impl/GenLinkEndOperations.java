/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkEnd;

/**
 * In future, when we hopefully will have GenLink#sources and GenLink#targets directly editable, 
 * we will mark the GenLinkEnd#incomingLinks and GenLinkEnd#outgoingLinks to be opposite features. 
 * 
 * Right now, during the migration phase, we can't do it because 'base' features are derived.
 * Nevertheless, also more effective methods of computing are possible, 
 * we want to explicitly emulate the opposite'ness by traversing all GenLinks and selecting them 
 * by the values of GenLink#sources and GenLink#targets 
 */
class GenLinkEndOperations {

	/**
	 * XXX: Extremely inefficient implementation, nextLink#getTargets() is recomputed each time
	 */
	/*package*/static EList<GenLink> getGenIncomingLinks(GenLinkEnd linkEnd) {
		BasicEList<GenLink> result = new BasicEList<GenLink>();
		for (GenLink nextLink : linkEnd.getDiagram().getLinks()) {
			if (nextLink.getTargets().contains(linkEnd)) {
				result.add(nextLink);
			}
		}
		return result;
	}
	
	/**
	 * XXX: Extremely inefficient implementation, nextLink#getSources() is recomputed each time
	 */
	/*package*/static EList<GenLink> getGenOutgoingLinks(GenLinkEnd linkEnd) {
		BasicEList<GenLink> result = new BasicEList<GenLink>();
		for (GenLink nextLink : linkEnd.getDiagram().getLinks()) {
			if (nextLink.getSources().contains(linkEnd)) {
				result.add(nextLink);
			}
		}
		return result;
	}
	
}
