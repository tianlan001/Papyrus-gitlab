/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Thibault Le Ouay t.leouay@sherpa-eng.com - Strategy improvement of generated files
 *  Christian W. Damus (CEA) - bug 422257
 *  Christian W. Damus - bug 573987
 *
 *****************************************************************************/
package org.eclipse.papyrus.customization.properties.generation.generators;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.customization.properties.generation.Activator;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

/**
 * A Generator is intended to output a partial Context model.
 * This context model should only contain DataContextElements and Properties
 * (i.e. it should not contain any View or Tabs)
 * The Generator should also implement an heuristic, which will determine
 * for each Property if it should be displayed in the Property view or not,
 * for both Single and Multiple selection
 *
 * @author Camille Letavernier
 *
 */
public interface IGenerator {

	/**
	 * Generates the partial context, and persists it to the given target URI
	 *
	 * @param targetURI
	 * @return The generated Context
	 */
	public List<Context> generate(List<URI> targetURI);

	/**
	 * Creates the controls for this Generator. The generator is responsible
	 * for displaying any Control useful for editing its options, and listening
	 * for changes on them.
	 *
	 * @param parent
	 *            The Composite in which the controls will be displayed
	 *
	 * @deprecated Implement the {@link #createControls(Composite, IFile)} method, instead
	 */
	@Deprecated
	public default void createControls(Composite parent) {
		// Extant classes already override this. New ones should not
		throw new AbstractMethodError("createControls(Composite)"); //$NON-NLS-1$
	}

	/**
	 * Creates the controls for this Generator. The generator is responsible
	 * for displaying any Control useful for editing its options, and listening
	 * for changes on them.
	 *
	 * @param parent
	 *            The Composite in which the controls will be displayed
	 * @param workbenchSelection
	 *            the file currently selected in the workbench, if any.
	 *            May be {@code null}
	 */
	public default void createControls(Composite parent, IFile workbenchSelection) {
		createControls(parent);
	}

	/**
	 * Gets the description for this Generator
	 *
	 * @return The description
	 */
	public String getDescription();

	/**
	 * Tests if this Generator's settings are all set and valid
	 *
	 * @return true if all options are set and valid
	 */
	public boolean isReady();

	/**
	 * Gets the name for this Generator
	 *
	 * @return The name
	 */
	public String getName();

	/**
	 * Tests if a field should be displayed for this Property when
	 * exactly one instance of this property's parent element is selected.
	 *
	 * @param property
	 * @return
	 */
	public boolean isSelectedSingle(Property property);

	/**
	 * Tests if a field should be displayed for this Property when
	 * at least two instances of this property's parent element are selected.
	 *
	 * @param property
	 * @return
	 */
	public boolean isSelectedMultiple(Property property);

	/**
	 * Tests if a field should be displayed for this Property when
	 * exactly one instance of the given element is selected. The difference
	 * with {@link #isSelectedSingle(Property)} is that this method takes the inheritance
	 * into account, i.e. the property belongs to a Superclass of the given DataContextElement
	 *
	 * @param property
	 * @param element
	 * @return
	 */
	public boolean isSelectedSingle(Property property, DataContextElement element);

	/**
	 * Tests if a field should be displayed for this Property when
	 * at least two instances of the given element are selected. The difference
	 * with {@link #isSelectedMultiple(Property)} is that this method takes the inheritance
	 * into account, i.e. the property belongs to a Superclass of the given DataContextElement
	 *
	 * @param property
	 * @param element
	 * @return
	 */
	public boolean isSelectedMultiple(Property property, DataContextElement element);

	/**
	 * Adds a Change Listener to this generator. The Listener should be notified
	 * each time the generator's {@link #isReady()} value changes
	 *
	 * @param listener
	 */
	public void addListener(Listener listener);

	/**
	 * Removes a Change Listener from this generator.
	 *
	 * @param listener
	 */
	public void removeListener(Listener listener);

	public List<Object> getExternalReference();

	public IObservableValue getObservableValue();

	public void setStrategy(int strategy);

	public void addCheckElement(Object obj);

	/**
	 * Disposes of any resources allocated by me when I am no longer needed.
	 */
	public void dispose();

	/**
	 * Query the preferred source model for generation of a <em>Properties Context</em> model based on
	 * the given file selection in the workbench.
	 *
	 * @param file
	 *            the file selection in the workbench
	 * @param contentType
	 *            the content type of the {@code file}. May be {@code null} if the content type cannot
	 *            be determined or if the {@code file}'s content type is not registered in the platform
	 * @return the preferred file to use as input for the generation. This should be related in some way to
	 *         the selected {@code file}, usually being that {@code file} itself. Or else {@code null} to indicate
	 *         that the given {@code file} is not handled at all by this generator
	 *
	 * @see #getSourceFile(IFile, IContentType)
	 */
	public default IFile getSourceFile(IFile file) {
		try {
			Optional<IContentType> contentType = Optional.ofNullable(file.getContentDescription()).map(IContentDescription::getContentType);
			return getSourceFile(file, contentType.orElse(null));
		} catch (CoreException e) {
			Activator.log.error("Could not determine content type of " + file, e); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * Query the preferred source model for generation of a <em>Properties Context</em> model based on
	 * the given file selection in the workbench.
	 *
	 * @param file
	 *            the file selection in the workbench
	 * @param contentType
	 *            the content type of the {@code file}. May be {@code null} if the content type cannot
	 *            be determined or if the {@code file}'s content type is not registered in the platform
	 * @return the preferred file to use as input for the generation. This should be related in some way to
	 *         the selected {@code file}, usually being that {@code file} itself. Or else {@code null} to indicate
	 *         that the given {@code file} is not handled at all by this generator
	 */
	public default IFile getSourceFile(IFile file, IContentType contentType) {
		return null;
	}

	/**
	 * Query whether I can generate a <em>Properties Context</em> model from the given file selected in the workbench.
	 *
	 * @param selectedFile
	 *            the file currently selected in the workbench. Must not be {@code null}
	 * @param contentType
	 *            the content type of the selected file. May be {@code null} if the content type cannot
	 *            be determined or if the selected file's content type is not registered in the platform
	 * @return
	 */
	public default boolean canGenerate(IFile selectedFile, IContentType contentType) {
		return getSourceFile(selectedFile, contentType) != null;
	}

}
