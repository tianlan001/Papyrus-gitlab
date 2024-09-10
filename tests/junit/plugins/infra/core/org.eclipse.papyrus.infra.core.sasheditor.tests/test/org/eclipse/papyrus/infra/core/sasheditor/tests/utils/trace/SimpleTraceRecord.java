/*****************************************************************************
 * Copyright (c) 2012 Cedric Dumoulin.
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

package org.eclipse.papyrus.infra.core.sasheditor.tests.utils.trace;

import java.util.List;


/**
 * This class represent one simple record, with only one associated value.
 * @author Cedric Dumoulin
 *
 */
public class SimpleTraceRecord extends TraceRecord {

	public Object value;
	
	/**
	 * Constructor.
	 *
	 * @param namespace
	 * @param trace
	 * @param value
	 */
	public SimpleTraceRecord(String namespace, String trace, Object value) {
		super(namespace, trace);
		this.value = value;
	}
	
	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.tests.utils.trace.TraceRecord#getValue()
	 *
	 * @return
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.tests.utils.trace.TraceRecord#getValues()
	 *
	 * @return
	 */
	@Override
	public List<Object> getValues() {
		// TODO Return an List backuped by the single value.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.core.sasheditor.tests.utils.trace.TraceRecord#clear()
	 *
	 */
	@Override
	public void clear() {
		
		super.clear();
		value = null;
	}
}
