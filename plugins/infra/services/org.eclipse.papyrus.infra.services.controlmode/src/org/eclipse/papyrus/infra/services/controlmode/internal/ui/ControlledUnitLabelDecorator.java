/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.services.controlmode.internal.ui;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.papyrus.infra.emf.internal.resource.CrossReferenceIndex;
import org.eclipse.papyrus.infra.emf.internal.resource.index.IndexManager;
import org.eclipse.papyrus.infra.emf.resource.ICrossReferenceIndex;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.papyrus.infra.services.controlmode.ControlModePlugin;
import org.eclipse.papyrus.infra.tools.util.CoreExecutors;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * A label decorator for controlled-unit resources in the Project Explorer.
 */
public class ControlledUnitLabelDecorator extends BaseLabelProvider implements ILightweightLabelDecorator {

	private ICrossReferenceIndex index;
	private Runnable unregisterHandler;

	/**
	 * Initializes me.
	 */
	public ControlledUnitLabelDecorator() {
		super();
		index = null;
	}

	@Override
	public void dispose() {
		try {
			if (unregisterHandler != null) {
				unregisterHandler.run();
				unregisterHandler = null;
			}
		} finally {
			super.dispose();
		}
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (IndexManager.getInstance().isStarted()) {
			if (element instanceof IFile) {
				checkIndex();
				decorateFile((IFile) element, decoration);
			} else if (element instanceof IPapyrusFile) {
				checkIndex();
				decorateFile((IPapyrusFile) element, decoration);
			}
		}
	}

	/**
	 * check whether index reference is already initialized, register listener
	 * and initialize index variable
	 */
	private void checkIndex() {
		if (index == null) {
			index = CrossReferenceIndex.getInstance();

			// A very coarse-grained label change event
			unregisterHandler = ((CrossReferenceIndex) index).onIndexChanged(
					__ -> fireLabelProviderChanged(new LabelProviderChangedEvent(this)),
					CoreExecutors.getUIExecutorService());
		}
	}

	private void decorateFile(IFile file, IDecoration decoration) {
		ListenableFuture<SubunitKind> futureKind = getSubunitKind(file);
		if (futureKind.isDone()) {
			SubunitKind kind = Futures.getUnchecked(futureKind);
			if (kind != SubunitKind.NONE) {
				addOverlay(decoration, kind);
			}
		} else {
			futureKind.addListener(postUpdate(file), CoreExecutors.getUIExecutorService());
		}
	}

	private void decorateFile(IPapyrusFile file, IDecoration decoration) {
		List<ListenableFuture<SubunitKind>> kindFutures = Lists.newArrayListWithExpectedSize(3);

		for (IResource resource : file.getAssociatedResources()) {
			if (resource.getType() == IResource.FILE) {
				kindFutures.add(getSubunitKind((IFile) resource));
			}
		}

		ListenableFuture<List<SubunitKind>> futureKinds = Futures.allAsList(kindFutures);
		if (futureKinds.isDone()) {
			SubunitKind kind = SubunitKind.max(Futures.getUnchecked(futureKinds));

			if (kind != SubunitKind.NONE) {
				addOverlay(decoration, kind);
			}
		} else {
			futureKinds.addListener(postUpdate(file), CoreExecutors.getUIExecutorService());
		}
	}

	private void addOverlay(IDecoration decoration, SubunitKind kind) {
		decoration.addOverlay(kind.getIcon(), IDecoration.TOP_RIGHT); // $NON-NLS-1$
	}

	private ListenableFuture<SubunitKind> getSubunitKind(IFile file) {
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toPortableString(), true);

		// If it has any parents, it is at least a sub-model unit
		ListenableFuture<Set<URI>> parents = index.getParentsAsync(uri, false);
		ListenableFuture<Boolean> isShard = index.isShardAsync(uri);

		Function<Object, SubunitKind> kindFunction = __ -> {
			SubunitKind result = SubunitKind.NONE;

			// Both are guaranteed done now
			if (!Futures.getUnchecked(parents).isEmpty()) {
				// It is at least a sub-model unit
				result = Futures.getUnchecked(isShard)
						? SubunitKind.SHARD
						: SubunitKind.SUBMODEL;
			}

			return result;
		};

		@SuppressWarnings("unchecked")
		ListenableFuture<?> combined = Futures.allAsList(parents, isShard);
		return Futures.transform(combined, kindFunction, MoreExecutors.directExecutor()); // Added because of compilation error on the executor-less method call
	}

	private Runnable postUpdate(Object element) {
		return new Runnable() {

			@Override
			public void run() {
				fireLabelProviderChanged(new LabelProviderChangedEvent(ControlledUnitLabelDecorator.this, element));
			}
		};
	}

	//
	// Nested types
	//

	private enum SubunitKind {
		NONE, SUBMODEL, SHARD;

		ImageDescriptor getIcon() {
			ImageDescriptor result;

			switch (this) {
			case SHARD:
				result = ControlModePlugin.getDefault().getIcon("full/ovr16/shard_deco.png"); //$NON-NLS-1$
				break;
			case SUBMODEL:
				result = ControlModePlugin.getDefault().getIcon("full/ovr16/submodel_deco.png"); //$NON-NLS-1$
				break;
			default:
				result = null;
				break;
			}

			return result;
		}

		static SubunitKind max(Collection<SubunitKind> kinds) {
			return kinds.isEmpty() ? NONE : Collections.max(kinds);
		}
	}
}
