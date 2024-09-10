package org.eclipse.papyrus.uml.alf.backend.tests;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.alf.libraries.helper.AlfUtil;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class AlfUtilTests extends AbstractPapyrusTest {

	@Rule
	public final ModelSetFixture modelSetFixture = new ModelSetFixture();

	@Test
	@PluginResource({ "test-models/empty-model.di" })
	public void testActionLanguageNotApplied() {
		Assert.assertFalse(AlfUtil.getInstance().isActionLanguageProfileApplied(this.modelSetFixture.getModel()));
	}

	@Test
	@PluginResource({ "test-models/empty-model.di" })
	public void testStandardProfileNotapplied() {
		Assert.assertFalse(AlfUtil.getInstance().isStandardProfileApplied(this.modelSetFixture.getModel()));
	}

	@Test
	@PluginResource({ "test-models/empty-model.di" })
	public void testActionLanguageProfileApplication() {
		AlfUtil.getInstance().applyActionLanguageProfile(
				this.modelSetFixture.getModel());
		List<Profile> appliedProfiles = this.modelSetFixture.getModel().getAppliedProfiles();
		Assert.assertEquals("Unexpected number of applied profiles",
				1, appliedProfiles.size());
		Assert.assertTrue(appliedProfiles.get(0).getName().equals("ActionLanguage"));
		AlfUtil.getInstance().applyActionLanguageProfile(
				this.modelSetFixture.getModel());
		appliedProfiles = this.modelSetFixture.getModel().getAppliedProfiles();
		Assert.assertEquals("Unexpected number of applied profiles",
				1, appliedProfiles.size());
		Assert.assertTrue("The action language profile should detected as applied", AlfUtil.getInstance().isActionLanguageProfileApplied(this.modelSetFixture.getModel()));
	}

	@Test
	@PluginResource({ "test-models/empty-model.di" })
	public void testStandardProfileApplication() {
		AlfUtil.getInstance().applyStandardProfile(
				this.modelSetFixture.getModel());
		List<Profile> appliedProfiles = this.modelSetFixture.getModel().getAppliedProfiles();
		Assert.assertEquals("Unexpected number of applied profiles",
				1, appliedProfiles.size());
		AlfUtil.getInstance().applyStandardProfile(
				this.modelSetFixture.getModel());
		appliedProfiles = this.modelSetFixture.getModel().getAppliedProfiles();
		Assert.assertEquals("Unexpected number of applied profiles",
				1, appliedProfiles.size());
		Assert.assertTrue("The standard profile should detected as applied", AlfUtil.getInstance().
				isStandardProfileApplied(this.modelSetFixture.getModel()));
	}

	@Test
	@PluginResource({ "test-models/empty-model.di" })
	public void testProfileLoading() {
		Iterator<Resource> resourcesIterator = this.modelSetFixture.getResourceSet().getResources().iterator();
		IRegisteredProfile registeredProfile = RegisteredProfile.getRegisteredProfile("ActionLanguage");
		if (registeredProfile == null) {
			Assert.fail("The Action language profile should a registered profile of Papyrus");
		}
		while (resourcesIterator.hasNext()) {
			if (resourcesIterator.next().getURI().equals(registeredProfile.getUri())) {
				Assert.fail("The Action language profile should not already be loaded");
			}
		}
		/* A. The profile must be loaded in a Resource located in the resource set of the current model */
		Profile actionLanguageProfileRef1 = AlfUtil.getInstance().loadProfile((Model) this.modelSetFixture.getModel(), "ActionLanguage");
		resourcesIterator = this.modelSetFixture.getResourceSet().getResources().iterator();
		Resource foundResource = null;
		while (foundResource == null && resourcesIterator.hasNext()) {
			Resource current = resourcesIterator.next();
			if (current.getURI().equals(registeredProfile.getUri())) {
				foundResource = current;
			}
		}
		Assert.assertNotNull("The action language profile was not loaded", foundResource);
		/* B. If we retry to load the profile the number of loaded resource does not vary */
		int sizeBeforeLoading = this.modelSetFixture.getResourceSet().getResources().size();
		Profile actionLanguageProfileRef2 = AlfUtil.getInstance().loadProfile((Model) this.modelSetFixture.getModel(), "ActionLanguage");
		int sizeAfterLoading = this.modelSetFixture.getResourceSet().getResources().size();
		Assert.assertEquals("The number of loaded resources must be the same", sizeBeforeLoading,
				sizeAfterLoading);
		Assert.assertTrue("Both references should point on the same profile instance", 
				actionLanguageProfileRef1==actionLanguageProfileRef2);
	}

	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testDetectTextualRepresentationStereotype() {
		Class testClass1 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass1");
		Class testClass2 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass2");
		final Class testClass3 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass3");
		Assert.assertNotNull(testClass1);
		Assert.assertNotNull(testClass2);
		Assert.assertNotNull(testClass3);
		/* A. TestClass1 must have a textual representation comment */
		Assert.assertNotNull(AlfUtil.getInstance().getTextualRepresentationComment(testClass1));
		/* B. TestClass2 must have a textual representation comment */
		Assert.assertNotNull(AlfUtil.getInstance().getTextualRepresentationComment(testClass2));
		/* C. TestClass3 must not have a textual representation comment */
		Assert.assertNull(AlfUtil.getInstance().getTextualRepresentationComment(testClass3));
		/* D. TestClass3 must now have a textual representation comment */
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(this.modelSetFixture.getModel());
		GMFtoEMFCommandWrapper command = new GMFtoEMFCommandWrapper(
				new AbstractTransactionalCommand(domain, "", Collections.EMPTY_LIST) {
					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						AlfUtil.getInstance().applyTextualRepresentation(testClass3.getOwnedComments().get(0));
						return CommandResult.newOKCommandResult();
					}
		});
		domain.getCommandStack().execute(command);
		Assert.assertNotNull(AlfUtil.getInstance().getTextualRepresentationComment(testClass3));
		/*E. Another attempt to apply the textual representation stereotype should not result in an error*/
		try{
			domain.getCommandStack().execute(command);
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testRetrieveActionLanguageProfileReference(){
		Assert.assertNotNull("It should be possible to obtain a reference on the action language profile",
				AlfUtil.getInstance().getActionLanguageProfile((Model)this.modelSetFixture.getModel()));
	}
	
	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testRetrieveStandardProfileReference(){
		Assert.assertNotNull("It should be possible to obtain a reference on the standard profile",
				AlfUtil.getInstance().getStandardProfile((Model)this.modelSetFixture.getModel()));
	}
}
