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

package org.eclipse.papyrus.infra.emf.internal.resource;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.core.internal.language.ILanguageModel;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.Activator;

/**
 * Miscellaneous internal utilities supporting or using the model indexing facilities.
 */
public class InternalIndexUtil {

	/** Tracing options key for the indexer in general. */
	public static final String TRACE_INDEXER = "indexer"; //$NON-NLS-1$

	/** Tracing options key for the details of files being indexed. */
	public static final String TRACE_INDEXER_FILES = "indexer/files"; //$NON-NLS-1$

	/** Tracing options key for the details of matching which files are to be indexed. */
	public static final String TRACE_INDEXER_FILES_MATCHING = "indexer/files/matching"; //$NON-NLS-1$

	/** Tracing options key for the details of indexer event notifications. */
	public static final String TRACE_INDEXER_EVENTS = "indexer/events"; //$NON-NLS-1$

	/**
	 * Not instantiable by clients.
	 */
	private InternalIndexUtil() {
		super();
	}

	/**
	 * Queries whether indexer tracing is enabled.
	 * Useful to avoid doing unnecessary work preparing a message.
	 * 
	 * @return whether indexer tracing is enabled
	 */
	public static boolean isTracing() {
		return Activator.log.isTraceEnabled(TRACE_INDEXER);
	}

	/**
	 * Logs a general debug message.
	 * 
	 * @param format
	 *            the message format
	 * @param args
	 *            the message arguments
	 * 
	 * @see String#format(String, Object...)
	 */
	public static void tracef(String format, Object... args) {
		Activator.log.trace(TRACE_INDEXER, String.format(format, args));
	}

	/**
	 * Logs a specific debug message.
	 * 
	 * @param trace
	 *            the tracing key under which to write the trace
	 * @param format
	 *            the message format
	 * @param args
	 *            the message arguments
	 * 
	 * @see String#format(String, Object...)
	 */
	public static void detailf(String trace, String format, Object... args) {
		Activator.log.trace(trace, String.format(format, args));
	}

	/**
	 * Determine the resource file extensions that contain "semantic model" content,
	 * using heuristics if necessary to make a best guess.
	 * 
	 * @param resourceSet
	 *            a resource set
	 * @return the set of file extensions for resources that are expected to contain
	 *         semantic model content that is interesting to index
	 */
	// in which the shard loading is important
	public static Set<String> getSemanticModelFileExtensions(ResourceSet resourceSet) {
		Set<String> result = null;

		try {
			if (resourceSet instanceof ModelSet) {
				ILanguageService.getLanguageModels((ModelSet) resourceSet).stream()
						.map(m -> m.getAdapter(ILanguageModel.class))
						.filter(Objects::nonNull) // Not all models provide the adapter
						.map(ILanguageModel::getModelFileExtension)
						.filter(Objects::nonNull) // They really should provide this, though
						.collect(Collectors.toSet());
			}
		} catch (Exception e) {
			// We seem not to have the Language Service? That's fine
		} catch (LinkageError e) {
			// We seem to be operating without the Eclipse/OSGi run-time? That's fine
		}

		if (result == null) {
			// Best guess for common Papyrus applications
			result = Collections.singleton("uml"); //$NON-NLS-1$
		}

		return result;
	}
}
