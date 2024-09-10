/*****************************************************************************
 * Copyright (c) 2016, 2017, 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 522485
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 535055
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors.richtext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.richtext.RichTextEditor;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * 
 * The richtext editor with the {@link SpellCheckToolbarButton}
 * 
 * @since 2.0
 *
 */
public class GenericRichTextEditor extends RichTextEditor {

	/**
	 * The locale css URL to load for the papyrus CKEditor style.
	 * 
	 * @since 3.1
	 */
	protected static URL cssURL;
	static {
		locateCSSURL();
	}

	/**
	 * Methods to enabled all HTML tags.
	 * Later, contribute to Nebula so we can create
	 * a org.eclipse.nebula.widgets.richtext.RichTextEditor with
	 * CKEDITOR.config.allowContent=true;
	 */
	private boolean tagsEnabled;

	/**
	 * the string used to allows all contents in the ckeditor
	 */
	private static final String CKEDITOR_ALLOWED_CONTENT = "CKEDITOR.config.allowedContent=true;"; //$NON-NLS-1$

	/**
	 * The string used to declare css for the CKEditor.
	 */
	private static final String CKEDITOR_CONTENTS_CSS = "CKEDITOR.config.contentsCss="; //$NON-NLS-1$

	/**
	 * The file scheme for the eclipse bundles.
	 */
	private static final String FILE_SCHEME_FOR_BUNDLES = "file:/"; //$NON-NLS-1$

	/**
	 * The file scheme for javascript.
	 */
	private static final String FILE_SCHEME_FOR_JAVASCRIPT = "file:///"; //$NON-NLS-1$

	/**
	 * The file name for the papyrus css for the CKEditor.
	 */
	private static final String PAPYRUS_CSS_FILENAME = "PapyrusContents.css"; //$NON-NLS-1$

	/**
	 * the edited eobject
	 */
	private EObject editedObject;

	/**
	 * the edited feature for the edited eobject
	 */
	private EStructuralFeature editedFeature;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param toolbarConfig
	 * @param style
	 */
	public GenericRichTextEditor(Composite parent, GenericToolbarConfiguration toolbarConfig, int style) {
		super(parent, toolbarConfig, style);
		toolbarConfig.setBrowser(getEditorConfiguration().getBrowser());
		toolbarConfig.setRichTextEditor(this);		
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param toolbarConfig
	 */
	public GenericRichTextEditor(Composite parent, GenericToolbarConfiguration toolbarConfig) {
		this(parent, toolbarConfig, SWT.NONE);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 */
	public GenericRichTextEditor(Composite parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public GenericRichTextEditor(Composite parent, int style) {
		this(parent, new GenericToolbarConfiguration(), style);
	}

	/**
	 * 
	 * @return <code>true</code> if the tag have been enabled
	 */
	public boolean isTagsEnabled() {
		return tagsEnabled;
	}

	/**
	 * 
	 * @return
	 */
	public boolean enableTags() {
		if (!tagsEnabled) {
			try {
				// Add the papyrus contents css style for the CKeditor
				executeJavascript(CKEDITOR_CONTENTS_CSS + "CKEDITOR.getUrl(\"" + cssURL.toString().replace(FILE_SCHEME_FOR_BUNDLES, FILE_SCHEME_FOR_JAVASCRIPT) + "\");"); //$NON-NLS-1$ //$NON-NLS-2$
				tagsEnabled = executeJavascript(CKEDITOR_ALLOWED_CONTENT);
				// updateToolbar();
			} catch (Exception e) {
				tagsEnabled = false;
			}
		}
		return tagsEnabled;
	}



	/**
	 * 
	 * @param editedEObject
	 *            the edited element
	 * @param editedFeature
	 *            the edited feature for the element
	 */
	public void configureEdition(final EObject editedEObject, final EStructuralFeature editedFeature) {
		this.editedObject = editedEObject;
		this.editedFeature = editedFeature;
	}

	/**
	 * 
	 * @return
	 * 		the edited eobject
	 */
	public EObject getEditedObject() {
		return editedObject;
	}

	/**
	 * 
	 * @return
	 * 		the edited feature of the edited eobject
	 */
	public EStructuralFeature getEditedFeature() {
		return editedFeature;
	}

	/**
	 * This allows to get the locale css url for the CKeditor style.
	 */
	private static void locateCSSURL() {
		cssURL = GenericRichTextEditor.class.getResource("resources/" + PAPYRUS_CSS_FILENAME); //$NON-NLS-1$

		// if we are in an OSGi context, we need to convert the bundle URL to a file URL
		Bundle bundle = FrameworkUtil.getBundle(GenericRichTextEditor.class);
		if (bundle != null) {
			try {
				cssURL = FileLocator.toFileURL(cssURL);
			} catch (IOException e) {
				Activator.log.error(e);
			}
		} else if (cssURL.toString().startsWith("jar")) { //$NON-NLS-1$
			BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

				@Override
				public void run() {
					URL jarURL = GenericRichTextEditor.class.getProtectionDomain().getCodeSource().getLocation();
					File jarFileReference = null;
					if (jarURL.getProtocol().equals("file")) { //$NON-NLS-1$
						try {
							String decodedPath = URLDecoder.decode(jarURL.getPath(), "UTF-8"); //$NON-NLS-1$
							jarFileReference = new File(decodedPath);
						} catch (UnsupportedEncodingException e) {
							Activator.log.error(e);
						}
					} else {
						// temporary download of jar file necessary to be able to unzip the resources
						try {
							final Path jar = Files.createTempFile("richtext", ".jar"); //$NON-NLS-1$ //$NON-NLS-2$
							Files.copy(jarURL.openStream(), jar, StandardCopyOption.REPLACE_EXISTING);
							jarFileReference = jar.toFile();

							// delete the temporary file
							Runtime.getRuntime().addShutdownHook(new Thread() {
								@Override
								public void run() {
									try {
										Files.delete(jar);
									} catch (IOException e) {
										Activator.log.error(e);
									}
								}
							});
						} catch (IOException e) {
							Activator.log.error(e);
						}
					}

					if (jarFileReference != null) {
						try (JarFile jarFile = new JarFile(jarFileReference)) {
							String unpackDirectory = System.getProperty(JAR_UNPACK_LOCATION_PROPERTY);
							// create the directory to unzip to
							final java.nio.file.Path tempDir = (unpackDirectory == null)
									? Files.createTempDirectory("richtext") //$NON-NLS-1$
									: Files.createDirectories(Paths.get(unpackDirectory));

							// only register the hook to delete the temp directory after shutdown if a temporary directory was used
							if (unpackDirectory == null) {
								Runtime.getRuntime().addShutdownHook(new Thread() {
									@Override
									public void run() {
										try {
											Files.walkFileTree(tempDir, new SimpleFileVisitor<Path>() {
												@Override
												public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
													Files.delete(file);
													return FileVisitResult.CONTINUE;
												}

												@Override
												public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
													Files.delete(dir);
													return FileVisitResult.CONTINUE;
												}

											});
										} catch (IOException e) {
											Activator.log.error(e);
										}
									}
								});
							}

							Enumeration<JarEntry> entries = jarFile.entries();
							while (entries.hasMoreElements()) {
								JarEntry entry = entries.nextElement();
								String name = entry.getName();
								if (name.startsWith("org/eclipse/papyrus/infra/widgets/editors/richtext/resources")) { //$NON-NLS-1$
									File file = new File(tempDir.toAbsolutePath() + File.separator + name);
									if (!file.exists()) {
										if (entry.isDirectory()) {
											file.mkdirs();
										} else {
											try (InputStream is = jarFile.getInputStream(entry);
													OutputStream os = new FileOutputStream(file)) {
												while (is.available() > 0) {
													os.write(is.read());
												}
											}
										}
									}

									// found the template.html in the jar entries, so remember the URL for further usage
									if (name.endsWith(PAPYRUS_CSS_FILENAME)) {
										cssURL = file.toURI().toURL();
									}
								}
							}

						} catch (IOException e) {
							Activator.log.error(e);
						}
					}
				}
			});
		}
	}
}
