/*****************************************************************************
 * Copyright (c) 2016, 2020 Christian W. Damus and others.
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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 565006
 *****************************************************************************/

package org.eclipse.papyrus.dev.project.management.handlers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.dev.project.management.Activator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.statushandlers.StatusManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Ensures that <tt>pom.xml</tt> files have the same version number as their
 * corresponding <tt>MANIFEST.MF</tt> or <tt>feature.xml</tt> file.
 *
 * @since 1.2
 */
public class SyncManifestAndPOMVersions extends AbstractHandler {
	private static final Pattern BUNDLE_VERSION = Pattern.compile("^Bundle-Version:\\s*(\\S+)\\s*$", Pattern.MULTILINE); //$NON-NLS-1$
	private static final Pattern POM_FIXUP = Pattern.compile("^(<\\?xml\\s.*?\\?>)\\s*(<project>)\\s*$", Pattern.MULTILINE); //$NON-NLS-1$

	/**
	 * the format processor used to format XML files
	 */

	public SyncManifestAndPOMVersions() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Map<IFile, IFile> pomsByManifest = getAllPOMsByManifest();

		ISchedulingRule[] rules = Stream.concat(
				pomsByManifest.keySet().stream(),
				pomsByManifest.values().stream()).toArray(ISchedulingRule[]::new);
		MultiRule jobRule = new MultiRule(rules);

		Job updateJob = new Job("Synchronize POM versions") { //$NON-NLS-1$

			{
				setRule(jobRule);
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor sub = SubMonitor.convert(monitor, "Updating POMs...", pomsByManifest.size()); //$NON-NLS-1$

				pomsByManifest.forEach((manifest, pom) -> {
					try {
						if (sub.isCanceled()) {
							throw new OperationCanceledException();
						}

						String version = getVersion(manifest);
						if (version != null) {
							version = version.replace(".qualifier", "-SNAPSHOT"); //$NON-NLS-1$//$NON-NLS-2$
							Document xml = slurpXML(pom);
							Element versionElement = findVersion(xml);
							if (versionElement != null) {
								if (!version.equals(versionElement.getTextContent())) {
									versionElement.setTextContent(version);
									write(xml, pom);
									formatXMLFile(pom);
								}
							}
						}

						sub.worked(1);
					} catch (CoreException e) {
						StatusManager.getManager().handle(e.getStatus(), StatusManager.SHOW);
					}
				});

				sub.done();

				return Status.OK_STATUS;
			}
		};
		updateJob.schedule();

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IProgressService progress = window.getWorkbench().getProgressService();
		progress.showInDialog(window.getShell(), updateJob);

		return null;
	}

	Map<IFile, IFile> getAllPOMsByManifest() {
		Map<IFile, IFile> result = new HashMap<>();

		Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects())
				.filter(this::hasPluginOrFeatureNature)
				.forEach(project -> {
					IFile pom = project.getFile("pom.xml"); //$NON-NLS-1$
					if ((pom != null) && pom.isAccessible()) {
						IFile manifest = project.getFile("feature.xml"); //$NON-NLS-1$
						if (!manifest.isAccessible()) {
							manifest = project.getFile("META-INF/MANIFEST.MF"); //$NON-NLS-1$
						}

						if (manifest.isAccessible()) {
							result.put(manifest, pom);
						}
					}
				});

		return result;
	}

	String slurpText(IFile file) throws CoreException {
		StringBuilder result = new StringBuilder();
		CharBuffer buf = CharBuffer.allocate(4096);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getContents(), Charset.forName(file.getCharset())))) {
			for (;;) {
				if (reader.read(buf) < 0) {
					break; // Done
				}
				buf.flip();
				result.append(buf);
				buf.rewind();
			}
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to read manifest", e)); //$NON-NLS-1$
		}

		return result.toString();
	}

	Document slurpXML(IFile xmlFile) throws CoreException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			try (InputStream input = xmlFile.getContents()) {
				return builder.parse(input);
			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to read manifest file", e)); //$NON-NLS-1$
		}
	}

	void write(Document xml, IFile xmlFile) throws CoreException {
		try (StringWriter writer = new StringWriter()) {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
			xml.setXmlStandalone(true);
			xml.setXmlVersion("1.0"); //$NON-NLS-1$

			Result result = new StreamResult(writer);
			transformer.transform(new DOMSource(xml), result);
			String text = writer.toString();

			// Fix up the transformer's sometime failure to put a newline after the XML declaration
			Matcher fixup = POM_FIXUP.matcher(text);
			if (fixup.find()) {
				StringBuffer fixed = new StringBuffer(text.length() + 2);
				fixup.appendReplacement(fixed, fixup.group(1) + '\n' + fixup.group(2));
				fixup.appendTail(fixed);
				text = fixed.toString();
			}

			// Write it out
			ByteArrayInputStream input = new ByteArrayInputStream(text.getBytes(Charset.forName("UTF-8"))); //$NON-NLS-1$
			xmlFile.setContents(input, false, true, null);
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to update POM", e)); //$NON-NLS-1$
		}
	}

	String getVersion(IFile manifest) throws CoreException {
		String result = null;

		switch (manifest.getName()) {
		case "MANIFEST.MF": //$NON-NLS-1$
			String text = slurpText(manifest);
			Matcher m = BUNDLE_VERSION.matcher(text);
			if (m.find()) {
				result = m.group(1);
			}
			break;
		case "feature.xml": //$NON-NLS-1$
			Document xml = slurpXML(manifest);
			result = xml.getDocumentElement().getAttribute("version"); //$NON-NLS-1$
		}

		return result;
	}

	Element findVersion(Document pom) {
		Element project = pom.getDocumentElement();
		return stream(project.getChildNodes())
				.filter(n -> "version".equals(n.getNodeName())) //$NON-NLS-1$
				.filter(Element.class::isInstance).map(Element.class::cast)
				.findFirst().orElse(null);
	}

	Stream<Node> stream(NodeList nodes) {
		Stream.Builder<Node> result = Stream.builder();
		for (int i = 0; i < nodes.getLength(); i++) {
			result.add(nodes.item(i));
		}
		return result.build();
	}

	boolean hasPluginOrFeatureNature(IProject project) {
		boolean result = false;

		if (project.isAccessible()) {
			try {
				IProjectDescription desc = project.getDescription();
				List<String> natures = Arrays.asList(desc.getNatureIds());
				result = natures.contains("org.eclipse.pde.PluginNature") //$NON-NLS-1$
						|| natures.contains("org.eclipse.pde.FeatureNature"); //$NON-NLS-1$
			} catch (CoreException e) {
				// Guess it's not an interesting project
				Activator.log.log(e.getStatus());
			}
		}

		return result;
	}

	/**
	 *
	 * @param filePath
	 *            the path of the file to format
	 * @since 2.0
	 */
	protected static final void formatXMLFile(final IFile file) {
		if (null != file && file.exists()) {
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
}
