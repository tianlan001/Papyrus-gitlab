/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.application

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenApplication
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Externalizer

@com.google.inject.Singleton class WizardNewFileCreationPage {
	@Inject extension Common;

	@Inject Externalizer xptExternalizer;

	def className(GenApplication it) '''WizardNewFileCreationPage'''

	def packageName(GenApplication it) '''«it.packageName»'''

	def qualifiedClassName(GenApplication it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenApplication it) '''«qualifiedClassName(it)»'''

	def WizardNewFileCreationPage(GenApplication it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» extends org.eclipse.jface.wizard.WizardPage {
		
			«generatedMemberComment»
			private final org.eclipse.jface.viewers.IStructuredSelection currentSelection;
		
			«generatedMemberComment»
			private String initialFileName;
		
			«generatedMemberComment»
			private org.eclipse.core.runtime.IPath initialContainerFullPath;
		
			«generatedMemberComment»
			private org.eclipse.swt.widgets.Text fileNameEditor;
		
			«generatedMemberComment»
			public «className(it)»(String name, org.eclipse.jface.viewers.IStructuredSelection currentSelection) {
				super(name);
				this.currentSelection = currentSelection;
				String home = System.getProperty("user.home"); //$NON-NLS-1$
				if (home != null) {
					initialContainerFullPath = new org.eclipse.core.runtime.Path(home);
				}
			}
		
			«generatedMemberComment»
			protected org.eclipse.jface.viewers.IStructuredSelection getSelection() {
				return currentSelection;
			}
		
			«generatedMemberComment»
			public String getFileName() {
				if (fileNameEditor == null) {
					return initialFileName;
				}
				org.eclipse.core.runtime.IPath path = getFilePath();
				if (path == null || path.isEmpty() || path.hasTrailingSeparator()) {
					return null;
				}
				return path.lastSegment();
			}
		
			«generatedMemberComment»
			public void setFileName(String fileName) {
				if (fileNameEditor == null) {
					initialFileName = fileName;
					return;
				}
				setFilePath(getContainerFullPath(), fileName);
			}
		
			«generatedMemberComment»
			public org.eclipse.core.runtime.IPath getContainerFullPath() {
				if (fileNameEditor == null) {
					return initialContainerFullPath;
				}
				org.eclipse.core.runtime.IPath path = getFilePath();
				if (path == null || path.isEmpty()) {
					return null;
				}
				if (path.hasTrailingSeparator()) {
					return path;
				}
				path = path.removeLastSegments(1);
				if (path.isEmpty()) {
					return null;
				}
				return path.addTrailingSeparator();
			}
		
			«generatedMemberComment»
			public void setContainerFullPath(org.eclipse.core.runtime.IPath containerPath) {
				if (fileNameEditor == null) {
					initialContainerFullPath = containerPath;
					return;
				}
				setFilePath(containerPath, getFileName());
			}
		
			«generatedMemberComment»
			protected org.eclipse.core.runtime.IPath getFilePath() {
				String fileName = fileNameEditor.getText().trim();
				if (fileName.length() == 0) {
					return null;
				}
				return new org.eclipse.core.runtime.Path(fileNameEditor.getText());
			}
		
			«generatedMemberComment»
			protected void setFilePath(org.eclipse.core.runtime.IPath containerPath, String fileName) {
				if (fileName == null) {
					fileName = ""; //$NON-NLS-1$
				} else {
					fileName = fileName.trim();
				}
				if (containerPath == null) {
					fileNameEditor.setText(fileName);
				} else {
					if (!containerPath.hasTrailingSeparator()) {
						containerPath = containerPath.addTrailingSeparator();
					}
					org.eclipse.core.runtime.IPath path = fileName.length() == 0 ? containerPath : containerPath.append(fileName);
					fileNameEditor.setText(path.toOSString());
				}
				setPageComplete(validatePage());
			}
		
			«generatedMemberComment»
			public void createControl(org.eclipse.swt.widgets.Composite parent) {
				org.eclipse.swt.widgets.Composite plate = new org.eclipse.swt.widgets.Composite(parent, org.eclipse.swt.SWT.NONE);
				plate.setLayout(new org.eclipse.swt.layout.GridLayout(2, false));
				org.eclipse.swt.widgets.Label label = new org.eclipse.swt.widgets.Label(plate, org.eclipse.swt.SWT.NONE);
				label.setText(«xptExternalizer.accessorCall(editorGen, i18nKeyForNewFileWizardFileLabel(it))»);
				label.setLayoutData(new org.eclipse.swt.layout.GridData(org.eclipse.swt.SWT.BEGINNING, org.eclipse.swt.SWT.CENTER, false, false, 2, 1));
				fileNameEditor = new org.eclipse.swt.widgets.Text(plate, org.eclipse.swt.SWT.SINGLE | org.eclipse.swt.SWT.BORDER);
				fileNameEditor.setLayoutData(new org.eclipse.swt.layout.GridData(org.eclipse.swt.SWT.FILL, org.eclipse.swt.SWT.CENTER, true, false));
				org.eclipse.swt.widgets.Button button = new org.eclipse.swt.widgets.Button(plate, org.eclipse.swt.SWT.PUSH);
				button.setText(«xptExternalizer.accessorCall(editorGen, i18nKeyForNewFileWizardBrowseButton(it))»);
				button.setLayoutData(new org.eclipse.swt.layout.GridData(org.eclipse.swt.SWT.BEGINNING, org.eclipse.swt.SWT.CENTER, false, false));
		
				// logic
				fileNameEditor.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
		
					public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
						setPageComplete(validatePage());
					}
				});
				button.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
		
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						org.eclipse.swt.widgets.FileDialog dialog = new org.eclipse.swt.widgets.FileDialog(getShell(), org.eclipse.swt.SWT.SAVE);
						dialog.setText(«xptExternalizer.accessorCall(editorGen, i18nKeyForNewFileWizardSelectDialog(it))»);
						dialog.setFileName(getFileName());
						String fileName = dialog.open();
						if (fileName != null) {
							fileNameEditor.setText(fileName);
							setPageComplete(validatePage());
						}
					}
		
					public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		
				// init
				setFilePath(initialContainerFullPath, initialFileName);
				setControl(plate);
			}
		
			«generatedMemberComment»
			protected boolean validatePage() {
				String fileName = fileNameEditor.getText().trim();
				if (fileName.length() == 0) {
					setErrorMessage(«xptExternalizer.accessorCall(editorGen, i18nKeyForNewFileWizardEmpty(it))»);
					return false;
				}
				if (!new org.eclipse.core.runtime.Path("").isValidPath(fileName)) { //$NON-NLS-1$
					setErrorMessage(«xptExternalizer.accessorCall(editorGen, i18nKeyForNewFileWizardInvalid(it))»);
					return false;
				}
				setErrorMessage(null);
				return true;
			}
			«additions(it)»
		}
	'''

	def additions(GenApplication it) ''''''

	@Localization def i18nValues(GenApplication it) '''
		«xptExternalizer.messageEntry(i18nKeyForNewFileWizardFileLabel(it), 'File:')»
		«xptExternalizer.messageEntry(i18nKeyForNewFileWizardBrowseButton(it), 'Browse')»
		«xptExternalizer.messageEntry(i18nKeyForNewFileWizardSelectDialog(it), 'Select new file')»
		«xptExternalizer.messageEntry(i18nKeyForNewFileWizardEmpty(it), 'Specify file name')»
		«xptExternalizer.messageEntry(i18nKeyForNewFileWizardInvalid(it), 'Invalid file name')»
	'''

	@Localization def i18nAccessors(GenApplication it) '''
		«xptExternalizer.accessorField(i18nKeyForNewFileWizardFileLabel(it))»
		«xptExternalizer.accessorField(i18nKeyForNewFileWizardBrowseButton(it))»
		«xptExternalizer.accessorField(i18nKeyForNewFileWizardSelectDialog(it))»
		«xptExternalizer.accessorField(i18nKeyForNewFileWizardEmpty(it))»
		«xptExternalizer.accessorField(i18nKeyForNewFileWizardInvalid(it))»
	'''

	@Localization def String i18nKeyForNewFileWizardFileLabel(GenApplication app) {
		return className(app) + '.FileLabel'
	}

	@Localization def String i18nKeyForNewFileWizardBrowseButton(GenApplication app) {
		return className(app) + '.BrowseButton'
	}

	@Localization def String i18nKeyForNewFileWizardSelectDialog(GenApplication app) {
		return className(app) + '.SelectNewFileDialog'
	}

	@Localization def String i18nKeyForNewFileWizardEmpty(GenApplication app) {
		return className(app) + '.EmptyFileNameError'
	}

	@Localization def String i18nKeyForNewFileWizardInvalid(GenApplication app) {
		return className(app) + '.InvalidFileNameError'
	}

}
