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

package org.eclipse.papyrus.uml.m2m.qvto.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.m2m.internal.qvt.oml.trace.EValue;
import org.eclipse.m2m.internal.qvt.oml.trace.Trace;
import org.eclipse.m2m.internal.qvt.oml.trace.TraceRecord;
import org.eclipse.m2m.internal.qvt.oml.trace.VarParameterValue;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;
import org.eclipse.m2m.qvt.oml.util.IContext;
import org.eclipse.m2m.qvt.oml.util.ISessionData;

/**
 * Query operations that introspect the trace model.
 */
@SuppressWarnings("restriction")
public class TraceHelper {
	/**
	 * QVTo {@link ExecutionContext} session data key for the cumulative trace history.
	 * This is distinct from the session data for incremental update traces because it
	 * is not used for incremental update, but as a "memory" of trace relationships
	 * established by past transformations.
	 */
	public static ISessionData.SimpleEntry<org.eclipse.m2m.qvt.oml.util.Trace> TRACE_HISTORY = new ISessionData.SimpleEntry<org.eclipse.m2m.qvt.oml.util.Trace>();

	private final Map<String, EClass> eclasses = new HashMap<String, EClass>();

	/**
	 * Initializes me.
	 */
	public TraceHelper() {
		super();
	}

	/**
	 * Gets the source object from which a given {@code self} object traces.
	 *
	 * @param context
	 *            the execution context
	 * @param self
	 *            the object for which to trace its original in the source model
	 * 
	 * @return
	 * 		the source object, or {@code null} if not found in the traces
	 */
	@Operation(contextual = true, kind = Kind.QUERY, withExecutionContext = true)
	public Object traceFrom(IContext context, Object self) {
		return traceFrom(context, self, null);
	}

	/**
	 * Gets the source object from which a given {@code self} object traces.
	 *
	 * @param context
	 *            the execution context
	 * @param self
	 *            the object for which to trace its original in the source model
	 * @param type
	 *            the type of source object to obtain, in case multiple sources
	 *            map to the same target (may be {@code null} to get any source)
	 * 
	 * @return
	 * 		the source object, or {@code null} if not found in the traces
	 */
	@Operation(contextual = true, kind = Kind.QUERY, withExecutionContext = true)
	public Object traceFrom(IContext context, Object self, String type) {
		EObject result = null;

		org.eclipse.m2m.qvt.oml.util.Trace history = context.getSessionData().getValue(TRACE_HISTORY);
		List<EObject> traces = (history != null) ? history.getTraceContent() : Collections.<EObject> emptyList();

		for (EObject next : traces) {
			if (next instanceof Trace) {
				Trace trace = (Trace) next;
				EList<TraceRecord> inverse = trace.getTargetToTraceRecordMap().get(self);
				if (inverse != null) {
					for (TraceRecord record : inverse) {
						VarParameterValue source = record.getContext().getContext();
						EValue sourceValue = (source == null) ? null : source.getValue();
						EObject sourceElement = (sourceValue == null) ? null : sourceValue.getModelElement();

						if ((sourceElement != null) && ((type == null) || isA(sourceElement.eClass(), type))) {
							result = sourceElement;
							break;
						}
					}
				}
			}
		}

		return result;
	}

	private boolean isA(EClass eclass, String type) {
		boolean result;

		EClass target = eclasses.get(type);
		if (target != null) {
			result = target.isSuperTypeOf(eclass);
		} else {
			result = __isA(eclass, type);
		}

		return result;
	}

	private boolean __isA(EClass eclass, String type) {
		boolean result;

		String qname = qname(eclass);
		result = qname.equals(type);
		if (!result) {
			for (EClass next : eclass.getESuperTypes()) {
				result = isA(next, type);
				if (result) {
					break;
				}
			}
		}

		return result;
	}

	private String qname(EClass eclass) {
		StringBuilder buf = new StringBuilder();
		buf.append(eclass.getEPackage().getName());
		buf.append("::"); //$NON-NLS-1$
		buf.append(eclass.getName());

		for (EPackage nesting = eclass.getEPackage().getESuperPackage(); nesting != null; nesting = nesting.getESuperPackage()) {
			buf.insert(0, "::"); //$NON-NLS-1$
			buf.insert(0, nesting.getName());
		}

		String result = buf.toString();
		eclasses.put(result, eclass);
		return result;
	}
}
