/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.emf.internal.resource.index;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

/**
 * Internal implementation details of a {@link WorkspaceModelIndex}.
 */
public abstract class InternalModelIndex {

	private final QualifiedName indexKey;
	private final int maxIndexJobs;

	/** Clients can trigger 'afterIndex' calls before the manager is assigned. */
	private final ListenableFuture<IndexManager> manager = SettableFuture.create();

	/** A class loader that knows the classes of the owner (bundle) context. */
	private ClassLoader ownerClassLoader;

	/**
	 * Initializes me.
	 */
	public InternalModelIndex(QualifiedName indexKey, int maxIndexJobs) {
		super();

		this.indexKey = indexKey;
		this.maxIndexJobs = maxIndexJobs;
	}

	/**
	 * Initializes me.
	 */
	public InternalModelIndex(QualifiedName indexKey) {
		this(indexKey, 0);
	}

	public final QualifiedName getIndexKey() {
		return indexKey;
	}

	public final int getMaxIndexJobs() {
		return maxIndexJobs;
	}

	/**
	 * Obtains the content types matching a {@code file}.
	 *
	 * @param file
	 *            a file in the workspace
	 * @return the content types of the {@code file}, or an empty array if none
	 *
	 * @precondition The {@link IndexManager} must have already {@linkplain #start() started} me.
	 */
	protected final IContentType[] getContentTypes(IFile file) {
		return Futures.getUnchecked(manager).getContentTypes(file);
	}

	/**
	 * Obtains an asynchronous future result that is scheduled to run after
	 * any pending indexing work has completed.
	 *
	 * @param callable
	 *            the operation to schedule
	 *
	 * @return the future result of the operation
	 */
	protected <V> ListenableFuture<V> afterIndex(final Callable<V> callable) {
		AsyncFunction<IndexManager, V> indexFunction = mgr -> mgr.afterIndex(this, callable);
		return Futures.transformAsync(manager, indexFunction, MoreExecutors.directExecutor()); // Added because of compilation error on the executor-less method call
	}

	/**
	 * Executes the specified {@code callable} on the index if it is ready now to provide
	 * a result.
	 *
	 * @param callable
	 *            an index operation
	 * @return the result, or {@code null} if the index is not now ready
	 *
	 * @throws CoreException
	 *             on failure to get the index manager or exception in the
	 *             {@code callable}
	 */
	protected <V> V ifAvailable(Callable<V> callable) throws CoreException {
		try {
			return manager.get().ifAvailable(callable);
		} catch (ExecutionException | InterruptedException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Index manager not available", e)); //$NON-NLS-1$
		}
	}

	void setOwnerClassLoader(ClassLoader ownerClassLoader) {
		this.ownerClassLoader = ownerClassLoader;
	}

	protected final ObjectInputStream createObjectInput(InputStream underlying) throws IOException {
		return (ownerClassLoader == null)
				? new ObjectInputStream(underlying)
				: new ObjectInputStream(underlying) {
					@Override
					protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
						return Class.forName(desc.getName(), true, ownerClassLoader);
					}
				};
	}

	protected abstract void dispose();

	void start(IndexManager manager) {
		try {
			start();
		} finally {
			((SettableFuture<IndexManager>) this.manager).set(manager);
		}
	}

	protected final ListenableFuture<IndexManager> getManager() {
		return manager;
	}

	protected abstract void start();

	protected abstract boolean match(IFile file);

	protected abstract void process(IFile file) throws CoreException;

	protected abstract void remove(IProject project, IFile file) throws CoreException;

	protected abstract void remove(IProject project) throws CoreException;

	/**
	 * Do I have an up-to-date index for the given {@code project}?
	 *
	 * @param project
	 *            a project
	 * @return whether I have an up-to-date index for it
	 */
	protected abstract boolean hasIndex(IProject project);
}
