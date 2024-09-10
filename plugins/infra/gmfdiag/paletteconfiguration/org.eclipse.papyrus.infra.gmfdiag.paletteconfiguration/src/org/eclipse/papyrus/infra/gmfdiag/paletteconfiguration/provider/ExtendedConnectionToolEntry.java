/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, ALL4TEC, Esterel Technologies SAS and others.
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
 *  Micka�l ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 512110
 *  Sebastien Gabel (Esterel Technologies SAS) - Bug 513803
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.internal.services.palette.PaletteToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteFactory;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;


/**
 * Extended connection Tool Entry
 */
@SuppressWarnings("restriction")
public class ExtendedConnectionToolEntry extends PaletteToolEntry implements IElementTypesBasedTool, IClassBasedTool, IExtendedPaletteEntry {

	/** list of element types created by this tool */
	final private List<IElementType> elementTypes = new ArrayList<IElementType>();

	/** List of Element Descriptors for this tool */
	private final List<ElementDescriptor> elementDescriptors;

	/** The name of the tool class which should be used as tool. */
	private String toolClassName;

	/**
	 * Constructor.
	 *
	 * @param label
	 *            the label of the tool
	 * @param factory
	 *            the factory used by the entry to create the tool
	 * @param id
	 *            unique identifier of the tool
	 * @param elementDescriptors
	 *            list of element descriptors created by the tool
	 */
	public ExtendedConnectionToolEntry(String id, String label, PaletteFactory factory, List<ElementDescriptor> elementDescriptors) {
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
