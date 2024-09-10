/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.mwe2.utils.components;

import java.util.Collection;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.emf.mwe.utils.Reader;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * A {@link Reader} component that accumulates multiple models into a collection in the
 * model slot.
 */
public class MultiReader extends Reader {

	public MultiReader() {
		super();
	}

	@Override
	public void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		final String slot = getModelSlot();

		Object currentModelSlotContents = ctx.get(slot);

		super.invokeInternal(ctx, monitor, issues);

		Object newModelSlotContents = ctx.get(slot);

		Object contents;
		if (newModelSlotContents == null) {
			contents = currentModelSlotContents;
		} else if (currentModelSlotContents == null) {
			contents = newModelSlotContents;
		} else if (currentModelSlotContents instanceof Iterable<?>) {
			Collection<Object> collection = Lists.newArrayList((Iterable<?>) currentModelSlotContents);
			if (newModelSlotContents instanceof Iterable<?>) {
				Iterables.addAll(collection, (Iterable<?>) newModelSlotContents);
			} else if (newModelSlotContents != null) {
				collection.add(newModelSlotContents);
			}
			contents = collection;
		} else if (newModelSlotContents instanceof Iterable<?>) {
			Collection<Object> collection = Lists.newArrayList();
			collection.add(currentModelSlotContents);
			Iterables.addAll(collection, (Iterable<?>) newModelSlotContents);
			contents = collection;
		} else {
			Collection<Object> collection = Lists.newArrayListWithExpectedSize(2);
			collection.add(currentModelSlotContents);
			collection.add(newModelSlotContents);
			contents = collection;
		}

		ctx.set(slot, contents);
	}

}
