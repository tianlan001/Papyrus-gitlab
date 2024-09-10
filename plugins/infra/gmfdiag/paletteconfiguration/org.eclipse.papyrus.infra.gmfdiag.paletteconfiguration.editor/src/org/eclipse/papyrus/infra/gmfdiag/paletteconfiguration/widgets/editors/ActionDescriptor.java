/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.widgets.editors;

import org.eclipse.papyrus.infra.types.AdviceConfiguration;
import org.eclipse.swt.graphics.Image;

/**
 * Class to permit the description of an action through its corresponding advice class to implement, its icon and its description.
 */
public class ActionDescriptor {

	/** The {@link Class} {@link AdviceConfiguration} */
	private Class<AdviceConfiguration> adviceClass;

	/** The Image for the icon */
	private Image icon;

	/** The description as String */
	private String description;

	/**
	 * 
	 * The constructor.
	 *
	 * @param adviceClass
	 *            The advice {@link Class} corresponding to the action.
	 * @param icon
	 *            The Icon.
	 * @param description
	 *            The description.
	 */
	public ActionDescriptor(final Class<AdviceConfiguration> adviceClass, final Image icon, final String description) {
		this.adviceClass = adviceClass;
		this.icon = icon;
		this.description = description;
	}

	/**
	 * @return the {@link Class} {@link AdviceConfiguration}.
	 */
	public Class<AdviceConfiguration> getAdvice() {
		return adviceClass;
	}

	/**
	 * set the {@link Class} {@link AdviceConfiguration}.
	 * 
	 * @param adviceClass
	 *            The {@link Class} {@link AdviceConfiguration}.
	 */
	public void setAdvice(final Class<AdviceConfiguration> adviceClass) {
		this.adviceClass = adviceClass;
	}

	/**
	 * @return the Icon.
	 */
	public Image getImage() {
		return icon;
	}

	/**
	 * Set the icon as {@link Image}.
	 * 
	 * @param icon
	 *            The Icon to set.
	 */
	public void setIcon(final Image icon) {
		this.icon = icon;
	}

	/**
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of the action.
	 * 
	 * @param description
	 *            The description as {@link String}.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}
}
