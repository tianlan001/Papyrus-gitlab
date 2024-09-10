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

package org.eclipse.papyrus.bundles.tests.apireport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.papyrus.bundles.tests.Activator;

/**
 * Encapsulation of the report resources in the workspace metadata area.
 */
public class ReportFixture {
	private static final IPath XML_REPORTS = new Path("apireports/xml"); //$NON-NLS-1$
	private static final IPath HTML_REPORTS = new Path("apireports/html"); //$NON-NLS-1$

	private static final IPath XML_REPORT_FILE = XML_REPORTS.append("api.xml"); //$NON-NLS-1$
	private static final IPath HTML_REPORT_FILE = HTML_REPORTS.append("api.html"); //$NON-NLS-1$

	private final File xmlReportFile;
	private final File htmlReportFile;

	/**
	 * Initializes the XML and HTML outputs of the API report. For example, certain
	 * stylesheets and images are emitted if necessary for the HTML report.
	 *
	 * @param baseOutputDir
	 *            the base directory in which to generate the resulting reports
	 * 
	 * @throws IOException
	 *             on any problem in initializing the contents of the output directory
	 */
	public ReportFixture(IPath baseOutputDir) throws IOException {
		super();

		xmlReportFile = baseOutputDir.append(XML_REPORT_FILE).toFile();
		htmlReportFile = baseOutputDir.append(HTML_REPORT_FILE).toFile();

		ensureContents(xmlReportFile.getParentFile(), XML_REPORTS);
		ensureContents(htmlReportFile.getParentFile(), HTML_REPORTS);
	}

	public File getXMLReportFile() {
		return xmlReportFile;
	}

	public File getHTMLReportFile() {
		return htmlReportFile;
	}

	private void ensureContents(File directory, IPath resourcePath) throws IOException {
		if (!directory.exists()) {
			directory.mkdirs();
		}

		IPath base = new Path(directory.getAbsolutePath());

		// Initial queue of resources to fetch
		Queue<String> queue = new LinkedList<>();
		enqueueResources(new Path("resources").append(resourcePath).toString(), queue); //$NON-NLS-1$

		for (String next = queue.poll(); next != null; next = queue.poll()) {
			// Enqueue further resources
			enqueueResources(next, queue);

			IPath path = new Path(next);
			if (!path.hasTrailingSeparator()) {
				// It's a file to be copied
				URL url = Activator.getDefault().getBundle().getEntry(next);
				if (url != null) {
					// Strip the "resources" segment also (the +1)
					copyResource(url, base.append(path.removeFirstSegments(resourcePath.segmentCount() + 1)));
				}
			}
		}
	}

	private void enqueueResources(String basePath, Queue<? super String> queue) {
		Enumeration<String> entries = Activator.getDefault().getBundle().getEntryPaths(basePath);
		if (entries != null) {
			while (entries.hasMoreElements()) {
				queue.add(entries.nextElement());
			}
		}
	}

	private void copyResource(URL source, IPath destination) throws IOException {
		File localFile = destination.toFile();

		if (!localFile.exists()) {
			if (!localFile.getParentFile().exists()) {
				localFile.getParentFile().mkdirs();
			}

			try (InputStream input = source.openStream()) {
				Files.copy(input, Paths.get(localFile.getAbsolutePath()));
			}
		}
	}
}
