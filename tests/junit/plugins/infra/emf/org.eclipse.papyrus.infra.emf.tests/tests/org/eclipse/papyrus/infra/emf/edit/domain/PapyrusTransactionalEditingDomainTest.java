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

package org.eclipse.papyrus.infra.emf.edit.domain;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.size;
import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.tests.StrictModelSetFixture;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.resources.ChangeCapture;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Test suite for the {@link PapyrusTransactionalEditingDomain} class.
 */
@RunWith(ScenarioRunner.class)
@PluginResource("resources/editingdomaintest.uml")
public class PapyrusTransactionalEditingDomainTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture modelSet = new StrictModelSetFixture();

	private TransactionalEditingDomain mirror;

	private Package mirrorModel;

	private ActivityEdge a;
	private ActivityNode start;
	private ActivityNode do_;
	private ActivityEdge reA;

	private ActivityEdge aMirror;

	public PapyrusTransactionalEditingDomainTest() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Scenario({ "commands", "delete", "changes" })
	public void testDeleteWithCrossReferences() {
		Command defaultCommand = DeleteCommand.create(mirror, aMirror);
		Command papyrusCommand = DeleteCommand.create(modelSet.getEditingDomain(), a);

		if (verificationPoint()) {
			assertThat("Papyrus editing domain provided default command", papyrusCommand.getClass(), not(equalTo((Object) defaultCommand.getClass())));
		}

		ChangeDescription defaultChanges = execute(mirror, defaultCommand);
		ChangeDescription papyrusChanges = execute(modelSet.getEditingDomain(), papyrusCommand);

		if (verificationPoint()) {
			assertThat(a.eResource(), nullValue());
			assertThat(start.getOutgoings(), isEmpty());
			assertThat(do_.getIncomings(), isEmpty());
			assertThat(reA.getRedefinedEdges(), isEmpty());
		}

		if (verificationPoint()) {
			assertThat("Wrong number of objects changed by Papyrus command",
					papyrusChanges.getObjectChanges().size(), is(defaultChanges.getObjectChanges().size()));
			assertThat("Wrong number of total changes by Papyrus command",
					size(concat(papyrusChanges.getObjectChanges())), is(size(concat(defaultChanges.getObjectChanges()))));
			assertThat("Wrong number of objects deleted by Papyrus command",
					papyrusChanges.getObjectsToAttach().size(), is(defaultChanges.getObjectsToAttach().size()));
		}
	}

	//
	// Test framework
	//

	@Before
	public void createMirror() {
		mirror = houseKeeper.createSimpleEditingDomain();
		mirrorModel = UML2Util.load(mirror.getResourceSet(), modelSet.getModelResourceURI(), UMLPackage.Literals.PACKAGE);

		Class class1 = (Class) modelSet.getModel().getOwnedType("Class1");
		Class class2 = (Class) modelSet.getModel().getOwnedType("Class2");

		Activity class1Activity = (Activity) class1.getClassifierBehavior();
		Activity class2Activity = (Activity) class2.getClassifierBehavior();

		a = class1Activity.getEdge("a");
		start = a.getSource();
		do_ = a.getTarget();
		reA = getRedefiningEdge(class2Activity, a);

		aMirror = EMFHelper.reloadIntoContext(a, mirrorModel);
	}

	ActivityEdge getRedefiningEdge(Activity activity, ActivityEdge redefined) {
		ActivityEdge result = null;

		for (ActivityEdge next : activity.getEdges()) {
			if (next.getRedefinedEdges().contains(redefined)) {
				result = next;
				break;
			}
		}

		return result;
	}

	ChangeDescription execute(TransactionalEditingDomain domain, Command command) {
		ChangeDescription result = null;

		try (ChangeCapture capture = new ChangeCapture(domain)) {
			domain.getCommandStack().execute(command);
			result = capture.getChangeDescription();
		}

		return result;
	}
}
