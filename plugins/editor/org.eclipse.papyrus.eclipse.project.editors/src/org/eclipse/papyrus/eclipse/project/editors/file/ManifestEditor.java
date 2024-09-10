/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 485220, 489075, 569105
 *  Vincent Lorenzo (CEA LIST) - bug 525876
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.papyrus.eclipse.project.editors.project.ProjectEditor;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.eclipse.pde.core.project.IPackageImportDescription;
import org.eclipse.pde.core.project.IRequiredBundleDescription;

public class ManifestEditor extends ProjectEditor implements IManifestEditor {

	// string constants
	private static final String CRNL = "\r\n"; //$NON-NLS-1$

	private static final String CRNLSP = "\r\n "; //$NON-NLS-1$

	private static final String SEMICOLON = ";"; //$NON-NLS-1$

	private static final String COMMA = ","; //$NON-NLS-1$

	// Pattern for splitting on commas that are not within version ranges
	private static final String COMMA_SPLIT = ",(?!\\s*\\d)"; //$NON-NLS-1$

	private static final String EQUALS = "="; //$NON-NLS-1$

	private static final String ASSIGN = ":="; //$NON-NLS-1$

	private static final String BUNDLE_SYMBOLIC_NAME = "Bundle-SymbolicName"; //$NON-NLS-1$

	private static final String IMPORT_PACKAGE = "Import-Package"; //$NON-NLS-1$

	private static final String EXPORT_PACKAGE = "Export-Package"; //$NON-NLS-1$

	private static final String FRAGMENT_HOST = "Fragment-Host"; //$NON-NLS-1$

	private static final String SINGLETON = "singleton:="; //$NON-NLS-1$

	private static final String VISIBILITY = "visibility"; //$NON-NLS-1$

	private static final String REEXPORT = "reexport"; //$NON-NLS-1$

	private static final String VERSION = "version"; //$NON-NLS-1$

	private static final String NAME_ATTRIBUTE = "Name"; //$NON-NLS-1$

	/** the manifest file */
	private IFile manifestFile;

	/** the manifest itself */
	private Optional<Manifest> manifest = Optional.empty();

	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 * @throws IOException
	 * @throws CoreException
	 */
	public ManifestEditor(final IProject project) throws IOException, CoreException {
		super(project);
	}

	public boolean initOk() {
		return manifest.isPresent() && (manifestFile != null);
	}

	@Override
	public void addDependency(final String dependency) {
		addDependency(dependency, null);
	}

	@Override
	public void init() {
		if (initOk()) {
			return;
		}
		if (manifestFile == null) {
			manifestFile = getManifestFile();
		}
		if (manifestFile != null) {
			try {
				manifest = Optional.of(new Manifest(manifestFile.getContents()));
			} catch (final IOException e) {
				Activator.log.error(e);
				// assure that exception is not silently captured (for users not examining the error log)
				throw new RuntimeException(e);
			} catch (final CoreException e) {
				Activator.log.error(e);
				// assure that exception is not silently captured (for users not examining the error log)
				throw new RuntimeException(e);
			}
		}

	}

	@Override
	public void addDependency(final String dependency, final String version) {
		if (hasDependency(dependency)) {
			// Easy case: Just update the version
			Map<String, String> attributes = (version == null)
					? null
					: Collections.singletonMap("bundle-version", version); //$NON-NLS-1$
			updateDependencies(dependency::equals, attributes, null);
		} else {
			// Only slightly more difficult case: Add the new dependency
			String newDependency = (version == null)
					? dependency
					: String.format("%s;bundle-version=\"%s\"", dependency, version); //$NON-NLS-1$

			String requireBundle = getMainAttribute(REQUIRED_BUNDLE)
					.map(rb -> rb + COMMA + newDependency)
					.orElse(newDependency);

			setMainAttribute(REQUIRED_BUNDLE, requireBundle);
		}
	}

	@Override
	public boolean hasDependency(final String dependency) {
		// Match a dependency if it is
		// (a) preceded by another dependency or the beginning of the input, AND
		// (b) followed by another dependency or an attribute/directive or the end of the input
		Pattern namePattern = Pattern.compile(String.format("(?<=^|,)\\Q%s\\E(?=,|;|$)", dependency)); //$NON-NLS-1$
		return getMainAttribute(REQUIRED_BUNDLE)
				.map(rb -> namePattern.matcher(rb).find()) // $NON-NLS-1$
				.orElse(false);
	}

	/**
	 * @since 2.0
	 */
	public boolean hasPackage(final String packageName, final String type) {
		// Match a package if it is
		// (a) preceded by another dependency or the beginning of the input, AND
		// (b) followed by another dependency or an attribute/directive or the end of the input
		Pattern namePattern = Pattern.compile(String.format("(?<=^|,)\\Q%s\\E(?=,|;|$)", packageName)); //$NON-NLS-1$
		return getMainAttribute(type)
				.map(ip -> namePattern.matcher(ip).find()) // $NON-NLS-1$
				.orElse(false);
	}

	@Override
	@Deprecated
	public void setDependenciesVersion(final String dependencyPattern, final String newVersion) {
		updateDependencies(
				name -> name.contains(dependencyPattern), // Update dependencies like this
				Collections.singletonMap("bundle-version", newVersion), // To have this version attribute //$NON-NLS-1$
				null); // And don't change directives such as optionality
	}

	private void updateDependencies(Predicate<String> predicate, Map<String, String> attributes, Map<String, String> directives) {
		updateHeader(REQUIRED_BUNDLE, predicate, attributes, directives);
	}

	private void updatePackages(Predicate<String> predicate, String type, Map<String, String> attributes, Map<String, String> directives) {
		updateHeader(type, predicate, attributes, directives);
	}

	private void updateHeader(String headerName, Predicate<String> predicate, Map<String, String> attributes, Map<String, String> directives) {
		// Match an attribute or directive, capturing:
		// 1 - the attribute/directive name
		// 2 - the equality/assignment operator
		// 3 - the quotation delimiter, if any
		Pattern attributeOrDirective = Pattern.compile("([^:]+)(:?=)([\"']?).*\\3"); //$NON-NLS-1$

		transformHeader(headerName, predicate, (dependency, attrs) -> {
			StringBuilder result = new StringBuilder();

			Map<String, String> newAttributes = (attributes == null) ? Collections.emptyMap() : new HashMap<>(attributes);
			Map<String, String> newDirectives = (directives == null) ? Collections.emptyMap() : new HashMap<>(directives);
			result.append(dependency);

			for (String next : attrs) {
				Matcher m = attributeOrDirective.matcher(next);

				if (!m.matches()) {
					// Unexpected formulation. Just append it as is
					result.append(SEMICOLON).append(next);
				} else {
					String name = m.group(1);
					boolean explicit = newAttributes.containsKey(name) || newDirectives.containsKey(name);
					String value = newAttributes.remove(name);
					if (value == null) {
						value = newDirectives.remove(name);
					}

					if (value != null) {
						// Replace this attribute or directive
						result.append(SEMICOLON);
						result.append(name).append(m.group(2));
						result.append(m.group(3)).append(value).append(m.group(3));
					} else if (!explicit) {
						// Just append it as is
						result.append(SEMICOLON).append(m.group());
					} // else an explicit null value: remove the attribute or directive
				}
			}

			// Left-overs
			for (Map.Entry<String, String> next : newAttributes.entrySet()) {
				// Do not add an attribute that was to be removed when it doesn't actually exist (bug 569105)
				if (next.getValue() != null) {
					result.append(SEMICOLON);
					result.append(next.getKey()).append(EQUALS).append('"').append(next.getValue()).append('"');
				}
			}
			for (Map.Entry<String, String> next : newDirectives.entrySet()) {
				// Do not add a directive that was to be removed when it doesn't actually exist (bug 569105)
				if (next.getValue() != null) {
					result.append(SEMICOLON);
					result.append(next.getKey()).append(ASSIGN).append(next.getValue());
				}
			}

			return result.toString();
		});
	}

	private void transformDependencies(Predicate<String> predicate, BiFunction<String, List<String>, String> transformation) {
		transformHeader(REQUIRED_BUNDLE, predicate, transformation);
	}

	private void transformImportedPackages(Predicate<String> predicate, BiFunction<String, List<String>, String> transformation) {
		transformHeader(IMPORT_PACKAGE, predicate, transformation);
	}

	private void transformHeader(String headerName, Predicate<String> predicate, BiFunction<String, List<String>, String> transformation) {
		final String entryList = getMainAttribute(headerName).orElse(""); //$NON-NLS-1$
		final String[] entries = entryList.isEmpty() ? new String[0] : entryList.split(COMMA_SPLIT);
		StringBuilder newEntryList = new StringBuilder();
		for (int i = 0; i < entries.length; i++) {// we iterate on the declared dependencies
			String entry = entries[i];
			List<String> parts = Arrays.asList(entry.split(SEMICOLON));
			String dependencyName = parts.get(0);

			if (!predicate.test(dependencyName)) {
				// Retain this one as is
				if (newEntryList.length() > 0) {
					newEntryList.append(COMMA);
				}
				newEntryList.append(entry);
			} else {
				String transformed = transformation.apply(dependencyName, parts.subList(1, parts.size()));
				if (transformed != null) {
					if (newEntryList.length() > 0) {
						newEntryList.append(COMMA);
					}
					newEntryList.append(transformed);
				}
			}
		}

		setMainAttribute(headerName, (newEntryList.length() == 0) ? null : newEntryList.toString());
	}

	@Override
	public void setValue(final String key, final String value) {
		setMainAttribute(key, value);
	}

	@Override
	public void setValue(final String key, final String name, final String value) {
		setAttribute(key, name, value);
	}

	void setMainAttribute(String name, String value) {
		if (value == null) {
			removeMainAttribute(name);
		} else {
			manifest.map(Manifest::getMainAttributes).ifPresent(attrs -> {
				if (!Objects.equals(attrs.getValue(name), value)) {
					touch();
					attrs.putValue(name, value);
				}
			});
		}
	}

	void setAttribute(String section, String name, String value) {
		if (value == null) {
			removeAttribute(section, name);
		} else {
			// Implicitly create the section in order to set an attribute value
			Optional<Attributes> attributes = manifest.map(m -> m.getEntries().computeIfAbsent(section,
					__ -> new Attributes()));
			attributes.ifPresent(attrs -> {
				if (!Objects.equals(attrs.getValue(name), value)) {
					touch();
					attrs.putValue(name, value);
				}
			});
		}
	}

	@Override
	public void removeValue(final String section, final String name) {
		removeAttribute(section, name);
	}

	@Override
	public void removeValue(final String key) {
		removeMainAttribute(key);
	}

	void removeMainAttribute(String name) {
		manifest.map(m -> m.getMainAttributes()).ifPresent(attrs -> {
			if (attrs.remove(new Attributes.Name(name)) != null) {
				touch();
			}
		});
	}

	void removeAttribute(String section, String name) {
		manifest.map(m -> m.getAttributes(section)).ifPresent(attrs -> {
			if (attrs.remove(new Attributes.Name(name)) != null) {
				touch();
				if (attrs.isEmpty()) {
					manifest.get().getEntries().remove(section);
				}
			}
		});
	}

	private IFile getManifestFile() {
		final IFile manifest = getProject().getFile("META-INF/MANIFEST.MF"); //$NON-NLS-1$
		if (manifest.exists()) {
			return manifest;
		}
		return null;
	}

	@Override
	public boolean exists() {
		return super.exists() && getManifestFile() != null && getSymbolicBundleName() != null && getBundleVersion() != null;
	}

	/**
	 * @since 2.0
	 */
	@Override
	protected void doSave() {
		if (manifest.isPresent()) {
			HeaderOrder headerOrder = getHeaderOrder(manifestFile);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				manifest.get().write(os);

				final StringReader reader = new StringReader(
						format(sortHeaders(os.toString("UTF-8"), headerOrder))); //$NON-NLS-1$
				manifestFile.setContents(new InputStream() {

					@Override
					public int read() throws IOException {
						return reader.read();
					}
				}, true, true, null);

			} catch (final IOException ex) {
				Activator.log.error(ex);
			} catch (final CoreException ex) {
				Activator.log.error(ex);
			}
		}
	}

	/**
	 * Simple formatting of the MANIFEST. Do not use the PDE formatter, since this makes an already opened
	 * MANIFEST editor dirty (see bug 447548 [OCL for Papyrus] Buggy DSML plugin generator)
	 */
	protected String format(String text) {
		// 1. undo 72safe formatting
		String[] lines = text.split(CRNLSP);
		String non72safe = ""; //$NON-NLS-1$
		for (String line : lines) {
			non72safe += line;
		}
		// 2. split lines on comma (but not within version ranges)
		lines = non72safe.split(COMMA_SPLIT);
		String newText = ""; //$NON-NLS-1$
		for (int i = 0; i < lines.length; i++) {
			newText += lines[i].trim();
			if (i < lines.length - 1) {
				newText += COMMA + CRNLSP;
			}
		}
		return newText + CRNL;
	}

	private HeaderOrder getHeaderOrder(IFile manifestFile) {
		Predicate<String> blankLine = Pattern.compile("^\\s*$").asPredicate(); //$NON-NLS-1$

		HeaderOrder.Builder builder = HeaderOrder.builder();

		try (BufferedReader input = new BufferedReader(new InputStreamReader(manifestFile.getContents(), manifestFile.getCharset()))) {
			input.lines().forEach(line -> {
				if (blankLine.test(line)) {
					// New section
					builder.newSection();
				} else if (!line.startsWith(" ")) { //$NON-NLS-1$
					int colon = line.indexOf(':');
					if (colon > 0) {
						String headerName = line.substring(0, colon).trim();
						if (NAME_ATTRIBUTE.equals(headerName)) {
							// For a section name, use the value, not the attribute name
							builder.sectionName(line.substring(colon + 1).trim());
						} else {
							builder.addAttribute(headerName);
						}
					}
				}
			});
		} catch (Exception e) {
			Activator.log.error("Failed to scan manifest for headers", e); //$NON-NLS-1$
		}

		return builder.build();
	}

	private String sortHeaders(String manifest, HeaderOrder headerOrder) {
		StringBuilder result = new StringBuilder(manifest.length());

		String[] sections = Pattern.compile("^\\s*$", Pattern.DOTALL | Pattern.MULTILINE) //$NON-NLS-1$
				.split(manifest);
		Pattern attrPattern = Pattern.compile("^(\\w[^:]+):.*?(?=^\\w|\\z)", Pattern.DOTALL | Pattern.MULTILINE); //$NON-NLS-1$
		Pattern namePattern = Pattern.compile("^Name:(.*?)$", Pattern.DOTALL | Pattern.MULTILINE); //$NON-NLS-1$

		String mainSection = sections[0];
		Map<String, String> additionalSections = new HashMap<>();
		for (int i = 1; i < sections.length; i++) {
			Matcher nameMatcher = namePattern.matcher(sections[i]);
			if (nameMatcher.find()) {
				additionalSections.put(nameMatcher.group(1).trim(), sections[i]);
			}
		}

		// Process all of the sections we had before, in the same order, followed by new sections
		headerOrder.sectionsAsStream(additionalSections.keySet()).forEachOrdered(sectionOrder -> {
			String section = sectionOrder.isMainSection() ? mainSection : additionalSections.remove(sectionOrder.getName());

			// A named section might have been removed from the manifest
			if (section != null) {
				// Maintain relative ordering of new headers, but they will appear after the
				// existing ones
				Map<String, String> headers = new LinkedHashMap<>();

				Matcher header = attrPattern.matcher(section);
				while (header.find()) {
					headers.put(header.group(1), header.group());
				}

				// Separate the sections
				if (!sectionOrder.isMainSection()) {
					result.append(CRNL);
				}

				// Now, output the headers in order
				for (String headerName : sectionOrder.getAttributeNames()) {
					String next = headers.remove(headerName);
					if (next != null) { // This header may have been deleted
						result.append(next); // This includes the trailing newline
					}
				}
				// And whatever is new
				for (String remaining : headers.values()) {
					result.append(remaining); // This includes the trailing newline
				}
			}
		});

		return result.toString();
	}

	@Override
	public Set<String> getMissingFiles() {
		final Set<String> files = super.getMissingFiles();
		final IFile classpath = getProject().getFile(MANIFEST_PATH);
		if (!classpath.exists()) {
			files.add(MANIFEST_PATH);
		}
		return files;
	}

	@Override
	public void createFiles(final Set<String> files) {
		if (files.contains(MANIFEST_PATH)) {
			manifestFile = getProject().getFile(MANIFEST_PATH);
			if (!manifestFile.exists()) {
				try {
					final String input = "Manifest-Version: 1.0\n"; // without the "/n", it doesn't work!!!!! //$NON-NLS-1$
					if (!manifestFile.getParent().exists()) {
						final IContainer parent = manifestFile.getParent();
						if (parent instanceof IFolder) {
							if (!parent.exists()) {
								((IFolder) parent).create(true, false, null);
							}
						}
					}
					manifestFile.create(getInputStream(input), true, null);
					manifestFile = getProject().getFile(MANIFEST_PATH);

				} catch (final CoreException ex) {
					Activator.log.error(ex);
				}
			}

			try (InputStream contents = manifestFile.getContents()) {
				manifest = Optional.of(new Manifest(contents));
			} catch (IOException e) {
				Activator.log.error(e);
			} catch (CoreException e) {
				Activator.log.error(e);
			}

			if (getSymbolicBundleName() == null) {
				setSymbolicBundleName(getProject().getName());
			}

			if (getBundleVersion() == null) {
				setBundleVersion("0.0.1"); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void setSymbolicBundleName(String newName) {
		if (newName == null) {
			newName = "noName"; //$NON-NLS-1$
		}
		setMainAttribute(BUNDLE_SYMBOLIC_NAME, newName);
	}

	@Override
	public String getSymbolicBundleName() {
		// Without the <String> hint, javac balks but JDT does not
		return getMainAttribute(BUNDLE_SYMBOLIC_NAME)
				.<String> map(name -> {
					int semiColon = name.indexOf(SEMICOLON);
					return semiColon >= 0 ? name.substring(0, semiColon) : name;
				}).orElse(null);
	}

	@Override
	public String getBundleVersion() {
		return getMainAttribute(BUNDLE_VERSION).orElse(null);
	}

	@Override
	public void setBundleVersion(final String version) {
		setMainAttribute(BUNDLE_VERSION, version);
	}

	@Override
	public String getBundleVendor() {
		return getMainAttribute(BUNDLE_VENDOR).orElse(null);
	}

	@Override
	public void setBundleVendor(final String vendor) {
		setMainAttribute(BUNDLE_VENDOR, vendor);
	}

	@Override
	public String getValue(final String name) {
		return getMainAttribute(name).orElse(null);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getValue(String key, String name) {
		return getAttribute(key, name).orElse(null);
	}

	Optional<String> getMainAttribute(String name) {
		return manifest.map(Manifest::getMainAttributes).map(attrs -> attrs.getValue(name));
	}

	Optional<String> getAttribute(String section, String name) {
		return manifest.map(m -> m.getAttributes(section)).map(attrs -> attrs.getValue(name));
	}

	@Override
	public String getBundleName() {
		return getMainAttribute(BUNDLE_NAME).orElse(null);
	}

	@Override
	public void setBundleName(String newName) {
		if (newName == null) {
			newName = "noName"; //$NON-NLS-1$
		}
		setMainAttribute(BUNDLE_NAME, newName);
	}

	@Override
	public String getBundleLocalization() {
		return getMainAttribute(BUNDLE_LOCALIZATION).orElse(null);
	}

	@Override
	public void setSingleton(final boolean singleton) {
		String value = getMainAttribute(BUNDLE_SYMBOLIC_NAME).orElse(""); //$NON-NLS-1$
		final String[] directives = value.isEmpty() ? new String[0] : value.split(SEMICOLON);

		if (directives.length == 0) {
			return; // This should not happen if the Manifest is well-formed
		} else {
			value = directives[0];
			boolean isDefined = false;
			for (int i = 1; i < directives.length; i++) {
				String directive = directives[i];
				if (directive.startsWith(SINGLETON)) {
					directive = SINGLETON + singleton;
					isDefined = true;
				}
				value += SEMICOLON + directive;
			}
			if (!isDefined) {
				value += SEMICOLON + SINGLETON + singleton;
			}
		}

		setMainAttribute(BUNDLE_SYMBOLIC_NAME, value);
	}

	/**
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#addImportPackage(java.lang.String)
	 *
	 * @param packageName
	 * @since 2.0
	 */
	@Override
	public void addImportPackage(String packageName) {
		addImportPackage(packageName, null);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void addImportPackage(String packageName, String version) {
		addPackage(packageName, IMPORT_PACKAGE, version);
	}

	/**
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#addExportPackage(java.lang.String)
	 *
	 * @param packageName
	 * @since 2.0
	 */
	@Override
	public void addExportPackage(String packageName) {
		addExportPackage(packageName, null);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#addExportPackage(java.lang.String, java.lang.String)
	 *
	 * @param packageName
	 * @param version
	 * @since 2.0
	 */
	@Override
	public void addExportPackage(String packageName, String version) {
		addPackage(packageName, EXPORT_PACKAGE, version);
	}

	/**
	 * Adds a package name in a manifest header type.
	 *
	 * @param packageName
	 *            the package name to add
	 * @param type
	 *            IMPORT_PACKAGE or EXPORT_PACKAGE
	 */
	private void addPackage(String packageName, String type, String version) {
		if (hasPackage(packageName, type)) {
			// Easy case: Just update the version
			Map<String, String> attributes = (version == null)
					? null
					: Collections.singletonMap(VERSION, version); // $NON-NLS-1$
			updatePackages(packageName::equals, type, attributes, null);
		} else {
			// Only slightly more difficult case: Add the new import
			String newPackage = (version == null)
					? packageName
					: String.format("%s;version=\"%s\"", packageName, version); //$NON-NLS-1$

			String importPackage = getMainAttribute(type)
					.map(ip -> ip + COMMA + newPackage)
					.orElse(newPackage);

			setMainAttribute(type, importPackage);
		}
	}

	/**
	 * @since 2.0
	 */
	@Override
	public List<IRequiredBundleDescription> getRequiredBundles() {
		List<IRequiredBundleDescription> result = new ArrayList<>();

		Pattern versionPattern = Pattern.compile("bundle-version=([\"']?)(.*)\\1"); //$NON-NLS-1$
		Pattern optionalPattern = Pattern.compile("resolution:?=([\"']?)optional\\1"); //$NON-NLS-1$
		Pattern reexportPattern = Pattern.compile("visibility:?=([\"']?)reexport\\1"); //$NON-NLS-1$

		IBundleProjectService service = Activator.getDefault().getBundleProjectService();
		String requireBundles = getMainAttribute(REQUIRED_BUNDLE).orElse(""); //$NON-NLS-1$
		String[] bundles = requireBundles.isEmpty() ? new String[0] : requireBundles.split(COMMA_SPLIT);

		for (int i = 0; i < bundles.length; i++) {
			String dependency = bundles[i];
			String[] parts = dependency.split(SEMICOLON);

			String name = parts[0];
			VersionRange version = VersionRange.emptyRange;
			boolean optional = false;
			boolean reexported = false;

			for (int j = 1; j < parts.length; j++) {
				Matcher m = versionPattern.matcher(parts[j]);
				if (m.matches()) {
					version = new VersionRange(m.group(2));
				} else {
					m = optionalPattern.matcher(parts[j]);
					if (m.matches()) {
						optional = true;
					} else {
						m = reexportPattern.matcher(parts[j]);
						if (m.matches()) {
							reexported = true;
						}
					}
				}
			}

			result.add(service.newRequiredBundle(name, version, optional, reexported));
		}

		return result;
	}

	/**
	 * @since 2.0
	 */
	@Override
	public List<IPackageImportDescription> getImportedPackages() {
		List<IPackageImportDescription> result = new ArrayList<>();

		Pattern versionPattern = Pattern.compile("version=([\"']?)(.*)\\1"); //$NON-NLS-1$
		Pattern optionalPattern = Pattern.compile("resolution:?=([\"']?)optional\\1"); //$NON-NLS-1$

		IBundleProjectService service = Activator.getDefault().getBundleProjectService();
		String importedPackages = getMainAttribute(IMPORT_PACKAGE).orElse(""); //$NON-NLS-1$
		String[] packages = importedPackages.isEmpty() ? new String[0] : importedPackages.split(COMMA_SPLIT);

		for (int i = 0; i < packages.length; i++) {
			String package_ = packages[i];
			String[] parts = package_.split(SEMICOLON);

			String name = parts[0];
			VersionRange version = VersionRange.emptyRange;
			boolean optional = false;

			for (int j = 1; j < parts.length; j++) {
				Matcher m = versionPattern.matcher(parts[j]);
				if (m.matches()) {
					version = new VersionRange(m.group(2));
				} else {
					m = optionalPattern.matcher(parts[j]);
					if (m.matches()) {
						optional = true;
					}
				}
			}

			result.add(service.newPackageImport(name, version, optional));
		}

		return result;
	}


	/**
	 * @since 2.0
	 */
	@Override
	public void setRequiredBundleExported(String bundleName, boolean exported) {
		updateDependencies(
				bundleName::equals, // Update bundles having this name
				null, // Don't change attributes such as version
				Collections.singletonMap(VISIBILITY, exported ? REEXPORT : null)); // Set the visibility directive
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void removeRequiredBundle(String bundleName) {
		transformDependencies(bundleName::equals, (dep, parts) -> null);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void removeImportedPackage(String packageName) {
		transformImportedPackages(packageName::equals, (dep, parts) -> null);
	}

	//
	// Nested types
	//

	/**
	 * Ordering of headers in the manifest as it was in the current edition (which
	 * is the edition prior to the one being saved).
	 */
	private static class HeaderOrder {
		private final Section main = new Section();

		// Maintain a defined ordering of sections as well as attributes within them
		private final Map<String, Section> sections = new LinkedHashMap<>();

		private HeaderOrder() {
			super();
		}

		public Section getMainSection() {
			return main;
		}

		public Section getSection(String name) {
			// If it's a header with attribute and value both, get just the value
			name = name.substring(name.indexOf(':') + 1).trim();
			return sections.computeIfAbsent(name, Section::new);
		}

		public static Builder builder() {
			return new Builder();
		}

		public Stream<Section> sectionsAsStream(Collection<String> implicitSectionNames) {
			// Compute the sections that are new (didn't exist in the previous edition of the manifest)
			List<String> newSectionNames = new ArrayList<>(implicitSectionNames);
			newSectionNames.removeAll(sections.keySet());

			// The main section is always first, followed by those that we had before, and then
			// the new sections in whatever non-deterministic order
			return Stream.concat(Stream.concat(
					Stream.of(main),
					sections.values().stream()),
					newSectionNames.stream().map(this::getSection));
		}

		static final class Builder {
			private HeaderOrder product = new HeaderOrder();
			private Section currentSection = product.getMainSection();

			Builder() {
				super();
			}

			public Builder newSection() {
				// Ignore multiple blank lines in sequence: they don't each denote sections
				if (!currentSection.attributeNames.isEmpty()) {
					currentSection = new Section();
				}
				return this;
			}

			public Builder sectionName(String name) {
				currentSection.setName(name);
				product.sections.put(name, currentSection);

				// And we also need to add the Name attribute at this index
				return addAttribute(NAME_ATTRIBUTE);
			}

			public Builder addAttribute(String name) {
				currentSection.addAttribute(name);
				return this;
			}

			public HeaderOrder build() {
				return product;
			}
		}
	}

	/**
	 * An ordering of attributes with a named section or the main section.
	 */
	private static class Section {
		private static final String MAIN = "$main$"; //$NON-NLS-1$

		private String name;
		private final List<String> attributeNames = new ArrayList<>();

		Section() {
			this(MAIN);
		}

		Section(String name) {
			super();

			this.name = name;
		}

		public boolean isMainSection() {
			return MAIN.equals(name);
		}

		public String getName() {
			return name;
		}

		void setName(String name) {
			this.name = name;
		}

		void addAttribute(String name) {
			attributeNames.add(name);
		}

		public Iterable<String> getAttributeNames() {
			return attributeNames;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#setFragmentHost(java.lang.String)
	 */
	@Override
	public void setFragmentHost(final String fragmentHost) {
		setFragmentHost(fragmentHost, null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#setFragmentHost(java.lang.String, java.lang.String)
	 */
	@Override
	public void setFragmentHost(final String fragmentHost, final String version) {
		String fragmentHostStringValue = (version == null)
				? fragmentHost
				: String.format("%s;version=\"%s\"", fragmentHost, version); //$NON-NLS-1$

		setMainAttribute(FRAGMENT_HOST, fragmentHostStringValue);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#getFragmentHost()
	 */
	@Override
	public String getFragmentHost() {
		// Without the <String> hint, javac balks but JDT does not
		return getMainAttribute(FRAGMENT_HOST)
				.<String> map(name -> {
					int semiColon = name.indexOf(SEMICOLON);
					return semiColon >= 0 ? name.substring(0, semiColon) : name;
				}).orElse(null);
	}
}
