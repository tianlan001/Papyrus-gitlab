/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Thibault Le Ouay t.leouay@sherpa-eng.com
 *  Christian W. Damus (CEA) - bug 422257
 *
 *****************************************************************************/
package org.eclipse.papyrus.customization.properties.generation.generators;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.customization.properties.generation.messages.Messages;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

/**
 * Incubation
 *
 * An implementation of IGenerator used to re-generate a Context from an existing one
 *
 * @author Camille Letavernier
 */
public class EditContextGenerator implements IGenerator {

	public void dispose() {
		

	}

	public List<Context> generate(List<URI> targetURI) {
		
		return null;
	}

	public void createControls(Composite parent) {
		

	}

	public String getDescription() {
		return Messages.EditContextGenerator_generateNewContext;
	}

	public boolean isReady() {
		
		return false;
	}

	public String getName() {
		return Messages.EditContextGenerator_importExistingContext;
	}

	public boolean isSelectedSingle(Property property) {
		
		return false;
	}

	public boolean isSelectedMultiple(Property property) {
		
		return false;
	}

	public boolean isSelectedSingle(Property property, DataContextElement element) {
		
		return false;
	}

	public boolean isSelectedMultiple(Property property, DataContextElement element) {
		
		return false;
	}

	public void addListener(Listener listener) {
		
	}

	public List<DataContextElement> getContextElementsFor(Collection<Context> contexts, View view) {
		
		throw new UnsupportedOperationException();
	}

	public void removeListener(Listener listener) {
		

	}

	public IObservableValue getObservableValue() {
		
		return null;
	}

	public List<Object> getExternalReference() {
		
		return null;
	}

	public void setStrategy(int strategy) {
		

	}

	public void addCheckElement(Object obj) {
		

	}

}
