/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.tests.deletion;

import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Regression test for deletion of stereotype applications of elements being deleted.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=458685
 */
@RunWith(ScenarioRunner.class)
public class DeleteDependentWithStereotypeApplications458685 {

	@Rule
	public final ModelSetFixture model = new ModelSetFixture();

	public DeleteDependentWithStereotypeApplications458685() {
		super();
	}

	@Scenario({ "command", "elements", "stereotypes", "undo", "redo" })
	@PluginResource("resource/bug458685/model.di")
	public void stereotypeApplicationsDeleted() {
		final Type bean1 = model.getModel().getOwnedType("Bean1");
		final Type bean2 = model.getModel().getOwnedType("Bean2");
		final Dependency proxy = bean1.getClientDependencies().get(0);
		final EObject beanStereotype = bean2.getStereotypeApplications().get(0);
		final EObject proxyStereotype = proxy.getStereotypeApplications().get(0);

		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(bean2);
		ICommand command = elementEditService.getEditCommand(new DestroyElementRequest(bean2, false));

		if (verificationPoint()) {
			assertThat("No deletion command provided", command, notNullValue());
			assertThat("Deletion command is not executable", command.canExecute(), is(true));
		}

		model.execute(command);

		if (verificationPoint()) {
			assertThat("Element not deleted", bean2.eResource(), nullValue());
			assertThat("Dependent not deleted", proxy.eResource(), nullValue());
		}

		if (verificationPoint()) {
			assertThat("Deleted element's stereotype not deleted", beanStereotype.eResource(), nullValue());
			assertThat("Dependent element's stereotype not deleted", proxyStereotype.eResource(), nullValue());
		}

		model.undo();

		if (verificationPoint()) {
			assertThat("Deleted element's stereotype is still deleted", beanStereotype.eResource(), notNullValue());
			assertThat("Deleted element's stereotype not restored to it", UMLUtil.getBaseElement(beanStereotype), is((EObject) bean2));
			assertThat("Dependent element's stereotype is still deleted", proxyStereotype.eResource(), notNullValue());
			assertThat("Dependent element's stereotype not restored to it", UMLUtil.getBaseElement(proxyStereotype), is((EObject) proxy));
		}

		model.redo();

		if (verificationPoint()) {
			assertThat("Deleted element's stereotype not re-deleted", beanStereotype.eResource(), nullValue());
			assertThat("Dependent element's stereotype not re-deleted", proxyStereotype.eResource(), nullValue());
		}
	}
}
