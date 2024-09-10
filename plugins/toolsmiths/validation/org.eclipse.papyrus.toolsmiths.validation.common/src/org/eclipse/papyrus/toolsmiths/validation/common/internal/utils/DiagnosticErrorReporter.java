/*******************************************************************************
 *  Copyright (c) 2018, 2021 Julian Honnen, EclipseSource, Christian W. Damus, CEA LIST, and others.
 *
 *  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     Julian Honnen <julian.honnen@vector.com> - initial API and implementation
 *     Remi Schnekenburger (EclipseSource) - Bug 568495
 *     Christian W. Damus - bugs 569357, 570097
 *
 *******************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.pde.internal.core.builders.IncrementalErrorReporter;
import org.eclipse.pde.internal.core.builders.PDEMarkerFactory;

/**
 * Local copy of the {@link IncrementalErrorReporter} from PDE. Overrides marker creation to produce EMF {@link Diagnostic}s,
 * instead, which we can collect, sift, and write out to markers later.
 */
@SuppressWarnings("restriction")
public class DiagnosticErrorReporter extends IncrementalErrorReporter {

	static final String SOURCE_ID = "source_id"; //$NON-NLS-1$

	private final Collection<VirtualMarker> fReportedMarkers = new ArrayList<>();
	private int fErrorCount;

	private final IResource file;
	private final String markerType;
	private final DiagnosticChain diagnostics;

	public DiagnosticErrorReporter(IResource file, String markerType, DiagnosticChain diagnostics) {
		super(file);

		this.file = file;
		this.markerType = markerType;
		this.diagnostics = diagnostics;
	}

	@Override
	public VirtualMarker addMarker(String message, int lineNumber, int severity, int problemID, String category) {
		if (lineNumber < 0) {
			lineNumber = 1;
		}

		if (severity == IMarker.SEVERITY_ERROR) {
			fErrorCount++;
		}

		VirtualMarker marker = new VirtualMarker();
		marker.setAttribute(PDEMarkerFactory.PROBLEM_ID, problemID);
		marker.setAttribute(PDEMarkerFactory.CAT_ID, category);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.SEVERITY, severity);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);

		fReportedMarkers.add(marker);

		return marker;
	}

	@Override
	public void applyMarkers() {
		// Generate diagnostics for virtual markers
		for (VirtualMarker reportedMarker : fReportedMarkers) {
			Map<String, Object> attributes = reportedMarker.getAttributes();
			List<Object> data = new ArrayList<>();
			data.add(file);
			data.add(IPluginChecker2.markerType(markerType));
			for (Map.Entry<String, ?> next : attributes.entrySet()) {
				switch (next.getKey()) {
				case IMarker.MESSAGE:
				case IMarker.SEVERITY:
					// These are handled separately
					break;
				default:
					data.add(new IPluginChecker2.MarkerAttribute(next.getKey(), next.getValue()));
					break;
				}
			}

			Diagnostic diagnostic = new BasicDiagnostic(
					toDiagnosticSeverity((Integer) attributes.getOrDefault(IMarker.SEVERITY, IMarker.SEVERITY_ERROR)),
					(String) attributes.getOrDefault(SOURCE_ID, PluginErrorReporter.class.getName()),
					0,
					(String) attributes.get(IMarker.MESSAGE),
					data.toArray());
			diagnostics.add(diagnostic);
		}
	}

	private static int toDiagnosticSeverity(int markerSeverity) {
		switch (markerSeverity) {
		case IMarker.SEVERITY_INFO:
			return Diagnostic.INFO;
		case IMarker.SEVERITY_WARNING:
			return Diagnostic.WARNING;
		default:
			return Diagnostic.ERROR;
		}
	}

	@Override
	public int getErrorCount() {
		return fErrorCount;
	}

}
