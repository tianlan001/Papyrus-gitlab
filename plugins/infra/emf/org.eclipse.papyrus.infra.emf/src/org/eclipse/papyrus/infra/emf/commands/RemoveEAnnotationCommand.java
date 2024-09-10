/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.commands;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * This {@link RecordingCommand} removes an eannotation to a given element.
 */
public class RemoveEAnnotationCommand extends org.eclipse.emf.transaction.RecordingCommand {

	// @unused
	public EModelElement getObject() {
		return object;
	}

	// @unused
	public void setObject(EModelElement object) {
		this.object = object;
	}

	// @unused
	public String getEAnnotationName() {
		return eAnnotationName;
	}

	// @unused
	public void setEAnnotationName(String annotationName) {
		eAnnotationName = annotationName;
	}

	/** The object. */
	private EModelElement object;

	/** The e annotation name. */
	private String eAnnotationName;

	private EModelElement eAnnotation;

	/**
	 * Instantiates a new creates the e annotation command.
	 *
	 * @param domain
	 *            the domain
	 * @param object
	 *            the object
	 * @param eannotationName
	 *            the eannotation name
	 */
	public RemoveEAnnotationCommand(TransactionalEditingDomain domain, EModelElement object, String eannotationName) {
		super(domain);
		this.object = object;
		this.eAnnotationName = eannotationName;
	}

	/**
	 * Instantiates a new creates the e annotation command.
	 *
	 * @param domain
	 *            the domain
	 * @param object
	 *            the object
	 * @param eAnnotation
	 *            The Eannotation
	 * 
	 */
	public RemoveEAnnotationCommand(TransactionalEditingDomain domain, EModelElement object, EModelElement eAnnotation) {
		super(domain);
		this.eAnnotation = eAnnotation;
		this.object = object;


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute() {
		if (eAnnotation == null && eAnnotationName != null) {
			eAnnotation = object.getEAnnotation(eAnnotationName);
		}
		object.getEAnnotations().remove(eAnnotation);
	}

}
