/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.properties.ui.modelelement;

import java.util.EventListener;

import org.eclipse.core.databinding.observable.IObservable;


/**
 * A listener protocol for changes to a {@link DataSource} (especially its selection). Data sources are not {@linkplain IObservable observables} because that would confuse the XWT bindings framework.
 *
 * @see DataSource
 * @see DataSource#addDataSourceListener(IDataSourceListener)
 */
public interface IDataSourceListener extends EventListener {

	void dataSourceChanged(DataSourceChangedEvent event);
}
