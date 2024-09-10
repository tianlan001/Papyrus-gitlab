/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *  Tatiana Fesenko (CEA LIST) - Initial API and implementation
 *	Saadia Dhouib (CEA LIST) - Implementation of loading diagrams from template files  (.uml, .di , .notation)
 *  Christian W. Damus (CEA) - bug 422257
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.template;


import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;


/**
 * The Class InitFromTemplateCommand.
 */
public class InitFromTemplateCommand extends org.eclipse.papyrus.uml.diagram.wizards.command.InitFromTemplateCommand {

	public InitFromTemplateCommand(TransactionalEditingDomain editingDomain, ModelSet modelSet, String pluginId, String umlTemplatePath, String notationTemplatePath, String diTemplatePath) {
		super(editingDomain, modelSet, pluginId, umlTemplatePath, notationTemplatePath, diTemplatePath);

	}

}
