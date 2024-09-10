/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.model.xwt.format;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.views.properties.model.xwt.Activator;

/**
 * A Helper for formatting XML Files
 *
 * @author Camille Letavernier
 */
public class XMLFormatter {

	public static void format(IFile file) {
		try {
			InputStream input = file.getContents();
			Source xmlInput = new StreamSource(input);
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", 12); //$NON-NLS-1$
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.transform(xmlInput, xmlOutput);
			InputStream in = new ByteArrayInputStream(stringWriter.toString().getBytes());
			file.setContents(in, IResource.FORCE, new NullProgressMonitor());
		} catch (CoreException ex) {
			Activator.log.error(NLS.bind("Exception during the formatting of {0}", file.getFullPath()), ex); //$NON-NLS-1$
		} catch (TransformerConfigurationException e) {
			Activator.log.error(NLS.bind("Exception during the formatting of {0}", file.getFullPath()), e); //$NON-NLS-1$
		} catch (TransformerException e) {
			Activator.log.error(NLS.bind("Exception during the formatting of {0}", file.getFullPath()), e); //$NON-NLS-1$

		}
	}
}
