/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Esterel Technologies SAS and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Sebastien Gabel (Esterel Technologies SAS) - bug 513803
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.palette.PaletteToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteFactory;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;


/**
 * Extended version of the {@link CombinedTemplateCreationEntry}.
 */
@SuppressWarnings("restriction")
public class ExtendedCreationToolEntry extends PaletteToolEntry implements IElementTypesBasedTool, IClassBasedTool, IExtendedPaletteEntry {

	/** list of element types created by this tool */
	final private List<IElementType> elementTypes = new ArrayList<IElementType>();

	/** List of Element Descriptors for this tool */
	private final List<ElementDescriptor> elementDescriptors;

	/** The tool class name to be instantiate. */
	private String toolClassName;

	/**
	 * Creates a new CreationToolEx.
	 *
	 * @param label
	 *            the label of the tool
	 * @param factory
	 *            the factory that creates the tool from this entry
	 * @param id
	 *            the unique identifier of the tool
	 * @param elementDescriptors
	 *            list of element descriptors created by the tool
	 */
	public ExtendedCreationToolEntry(String id, String label, PaletteFactory factory, List<ElementDescriptor> elementDescriptors) {
		super(id, label, factory);
		this.elementDescriptors = elementDescriptors;

		for (ElementDescriptor elementDescriptor : elementDescriptors) {
			ElementTypeConfiguration elementType = elementDescriptor.getElementType();
			if (null != elementType) {
				elementTypes.add(ElementTypeRegistry.getInstance().getType(elementType.getIdentifier()));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IElementType> getElementTypes() {
		return elementTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ElementDescriptor> getElementDescriptors() {
		return elementDescriptors;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.IElementTypesBasedTool#getToolClassName()
	 */
	@Override
	public String getToolClassName() {
		return toolClassName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.IClassBasedTool#setToolClassName(java.lang.String)
	 */
	@Override
	public void setToolClassName(final String toolClassName) {
		this.toolClassName = toolClassName;
	}

}
