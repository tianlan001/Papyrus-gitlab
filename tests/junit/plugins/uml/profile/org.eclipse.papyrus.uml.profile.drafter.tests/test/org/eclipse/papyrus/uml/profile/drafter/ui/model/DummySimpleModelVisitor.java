/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.ui.model;

import org.eclipse.papyrus.uml.profile.drafter.tests.TracesRecorder;


/**
 * @author dumoulin
 *
 */
public class DummySimpleModelVisitor extends SimpleModelVisitor {

	TracesRecorder<String, Object> traces = new TracesRecorder<String, Object>();
	
	public DummySimpleModelVisitor() {
		
	}
	
	@Override
	public void doVisit(StereoptypeModel model) {
		super.doVisit(model);
		traces.records("doVisit", "StereoptypeModel", model);
	}
	
	@Override
	public void doVisit( PropertyModel model) {
		super.doVisit(model);
		traces.records("doVisit", "PropertyModel", model);
	}
	
	@Override
	public void doVisit( ExtendedStereotypeModel model) {
		super.doVisit(model);
		traces.records("doVisit", "ExtendedStereotypeModel", model);
	}
	
	@Override
	public void doVisit( MetaclassModel model) {
		super.doVisit(model);
		traces.records("doVisit", "MetaclassesModel", model);
	}
}
