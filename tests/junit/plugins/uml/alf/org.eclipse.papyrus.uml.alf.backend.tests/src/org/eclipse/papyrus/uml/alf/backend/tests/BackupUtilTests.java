package org.eclipse.papyrus.uml.alf.backend.tests;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.alf.libraries.helper.BackupState;
import org.eclipse.papyrus.uml.alf.libraries.helper.BackupState.EditionStatus;
import org.eclipse.papyrus.uml.alf.libraries.helper.BackupUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class BackupUtilTests {
	
	@Rule
	public final ModelSetFixture modelSetFixture = new ModelSetFixture();
	
	@Test
	@PluginResource({ "test-models/empty-model.di" })
	public void testBackupProfileNotApplied(){
		Assert.assertFalse(BackupUtil.getInstance().
				isBackupProfileApplied(this.modelSetFixture.getModel()));
	}
	
	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testBackupProfileIsApplied(){
		Assert.assertTrue("The backup profile should be applied", BackupUtil.getInstance().
				isBackupProfileApplied(this.modelSetFixture.getModel()));
	}
	
	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testRetrieveBackupProfileReference(){
		Assert.assertNotNull("It should be possible to obtain a reference on the backup profile",
				BackupUtil.getInstance().getBackupProfile((Model)this.modelSetFixture.getModel()));
	}
	
	@Test
	@PluginResource({ "test-models/empty-model.di" })
	public void testBackupProfileApplication(){
		BackupUtil.getInstance().applyBackupProfile(
				this.modelSetFixture.getModel());
		List<Profile> appliedProfiles = this.modelSetFixture.getModel().getAppliedProfiles();
		Assert.assertEquals("Unexpected number of applied profiles",
				1, appliedProfiles.size());
		BackupUtil.getInstance().applyBackupProfile(
				this.modelSetFixture.getModel());
		appliedProfiles = this.modelSetFixture.getModel().getAppliedProfiles();
		Assert.assertEquals("Unexpected number of applied profiles",
				1, appliedProfiles.size());
		Assert.assertTrue("The backup profile should be detected as being applied", 
				BackupUtil.getInstance().isBackupProfileApplied(this.modelSetFixture.getModel()));
	}

	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testRetrievalOfBackupStereotype(){
		Class testClass1 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass1");
		Stereotype backupStereotype = BackupUtil.getInstance().getBackupStereotype(testClass1);
		Assert.assertNotNull("In this context the Backup stereotype should be accessible", backupStereotype);
		Assert.assertTrue(BackupUtil.getInstance().getBackupProfile((Model)this.modelSetFixture.getModel())
				== backupStereotype.getProfile());
	}
	
	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testRetrievalOfBackupStateDefinition(){
		Class testClass1 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass1");
		Enumeration backupState = BackupUtil.getInstance().getBackupStateDefinition(testClass1);
		Assert.assertNotNull("In this context the BackupState type should be accessible", backupState);
		Assert.assertTrue(BackupUtil.getInstance().getBackupProfile((Model)this.modelSetFixture.getModel())
				== backupState.getOwner());
	}
	
	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testDetectBackupStereotype(){
		Class testClass1 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass1");
		Class testClass2 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass2");
		final Class testClass3 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass3");
		Assert.assertNotNull(testClass1);
		Assert.assertNotNull(testClass2);
		Assert.assertNotNull(testClass3);
		/*A. The backup stereotype must be applied on a comment owned by TestClass1*/
		Assert.assertNotNull(BackupUtil.getInstance().getBackupComment(testClass1));
		/*B. The backup stereotype must be applied on a comment owned by TestClass2*/
		Assert.assertNotNull(BackupUtil.getInstance().getBackupComment(testClass2));
		/*C. The backup stereotype must not be applied on comment owned by TestClass3*/
		Assert.assertNull(BackupUtil.getInstance().getBackupComment(testClass3));
	}
	
	@Test
	@PluginResource({ "test-models/model-with-alf.di" })
	public void testBackupStereotypeApplication(){
		final Class testClass3 = (Class) this.modelSetFixture.getModel().getOwnedType("TestClass3");
		Assert.assertNotNull(testClass3);
		/*A.Apply the stereotype and set the properties values*/
		final Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(this.modelSetFixture.getModel());
		GMFtoEMFCommandWrapper command = new GMFtoEMFCommandWrapper(
				new AbstractTransactionalCommand(domain, "", Collections.EMPTY_LIST) {
					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						BackupState state = new BackupState();
						state.timestamp = timestamp;
						state.status = EditionStatus.MERGED;
						BackupUtil.getInstance().applyBackup(testClass3.getOwnedComments().get(0), state);
						return CommandResult.newOKCommandResult();
					}
		});
		domain.getCommandStack().execute(command);
		/*B. Ensure it was correctly applied (i.e., check properties values)*/
		Comment stereotypedComment = BackupUtil.getInstance().getBackupComment(testClass3);
		Assert.assertNotNull(stereotypedComment);
		BackupState state = BackupUtil.getInstance().getBackupState(stereotypedComment);
		Assert.assertEquals("Timestamps should be equivalent", timestamp, state.timestamp);
		Assert.assertEquals("Edition status should be equivalent", EditionStatus.MERGED, state.status);
		/*C. Another attempt to apply the stereotype should not result in an error*/
		try{
			domain.getCommandStack().execute(command);
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
}
