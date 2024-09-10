/*******************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Christian W. Damus - Initial implementation and API
 *     
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.IControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.participants.IControlCommandApprover;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;

/**
 * Tests for the {@link ControlModeManager} class.
 */
@PluginResource("model/ControlModeTest/ControlModeTestModel.di")
public class ControlModeManagerTest extends AbstractControlModeTest {
	static final String DENY_ME = "test.parameter.deny.me";

	private IControlModeManager mgr = ControlModeManager.getInstance();

	@Test
	public void canCreateSubModel() {
		Package package_ = editorFixture.getModel().getNestedPackage("Package");
		Class class_ = (Class) package_.getOwnedType("Class");

		assertThat("Cannot make a package independently openable",
				mgr.canCreateSubmodel(package_), is(true));
		assertThat("Can make a class independently openable",
				mgr.canCreateSubmodel(class_), is(false));
	}

	@Test
	public void approveControlRequest() throws Exception {
		EObject object = editorFixture.getModel().getNestedPackage("Package");

		ControlModeRequest request = ControlModeRequest.createUIControlModelRequest(
				editorFixture.getEditingDomain(),
				object,
				URI.createURI("platform:/resource/bogus/model.di"));

		Diagnostic diagnostic = mgr.approveRequest(request);
		assertThat("Request not approved", diagnostic.getSeverity(), is(Diagnostic.OK));
	}

	@Test
	public void disapproveControlRequest() throws Exception {
		IControlModeManager mgr = ControlModeManager.getInstance();
		EObject object = editorFixture.getModel().getNestedPackage("Package");

		ControlModeRequest request = ControlModeRequest.createUIControlModelRequest(
				editorFixture.getEditingDomain(),
				object,
				URI.createURI("platform:/resource/bogus/model.di"));
		request.setParameter(DENY_ME, true);

		Diagnostic diagnostic = mgr.approveRequest(request);
		assertThat("Request approved", diagnostic.getSeverity(), is(Diagnostic.ERROR));
	}

	@Test
	@PluginResource("model/UncontrolModeTest/ReintegrateTestModel.di")
	public void approveUncontrolRequest() throws Exception {
		EObject object = editorFixture.getModel().getNestedPackage("Subpackage");

		ControlModeRequest request = ControlModeRequest.createUIUncontrolModelRequest(
				editorFixture.getEditingDomain(),
				object);

		Diagnostic diagnostic = mgr.approveRequest(request);
		assertThat("Request not approved", diagnostic.getSeverity(), is(Diagnostic.OK));
	}

	@Test
	@PluginResource("model/UncontrolModeTest/ReintegrateTestModel.di")
	public void disapproveUncontrolRequest() throws Exception {
		IControlModeManager mgr = ControlModeManager.getInstance();
		EObject object = editorFixture.getModel().getNestedPackage("Subpackage");

		ControlModeRequest request = ControlModeRequest.createUIUncontrolModelRequest(
				editorFixture.getEditingDomain(),
				object);
		request.setParameter(DENY_ME, true);

		Diagnostic diagnostic = mgr.approveRequest(request);
		assertThat("Request approved", diagnostic.getSeverity(), is(Diagnostic.ERROR));
	}

	//
	// Nested types
	//

	public static final class TestApproverParticipant implements IControlCommandApprover {

		@Override
		public String getID() {
			return "org.eclipse.papyrus.uml.controlmode.profile.tests.TestApprover";
		}

		@Override
		public int getPriority() {
			return 10;
		}

		@Override
		public Diagnostic approveRequest(ControlModeRequest request) {
			return (request.getParameter(DENY_ME) == null)
					? null
					: new BasicDiagnostic(Diagnostic.ERROR,
							"org.eclipse.papyrus.uml.controlmode.profile.tests",
							1, "Permission denied", null);
		}

	}
}
