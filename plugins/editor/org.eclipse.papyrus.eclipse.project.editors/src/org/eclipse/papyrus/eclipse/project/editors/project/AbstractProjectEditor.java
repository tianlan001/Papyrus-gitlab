/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.file.AbstractFileEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IProjectEditor;

/**
 *
 * This class provides useful method for project editor
 *
 */
public abstract class AbstractProjectEditor extends AbstractFileEditor implements IProjectEditor {


	/** the header for XML files */
	public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"; //$NON-NLS-1$

	/**
	 * the project description
	 */
	private final IProjectDescription description;

	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 * @throws CoreException
	 */
	public AbstractProjectEditor(final IProject project) throws CoreException {
		super(project);
		this.description = getProject().getDescription();
	}

	/**
	 * Initializes me as a slave to another editor, which maintains the canonical
	 * project description.
	 *
	 * @param master
	 *            my master editor
	 */
	AbstractProjectEditor(AbstractProjectEditor master) {
		super(master.getProject());

		this.description = master.description;
	}

	@Override
	public boolean exists() {
		return super.exists() && getMissingNature().size() == 0 && getMissingBuildCommand().size() == 0;
	}

	@Override
	public void create() {
		createFiles(getMissingFiles());
		addNatures(getMissingNature());
		addBuildCommands(getMissingBuildCommand());
		init();
	}

	@Override
	public boolean hasNature(final String nature) {
		List<String> natures = new LinkedList<String>(Arrays.asList(this.description.getNatureIds()));
		return natures.contains(nature);
	}

	@Override
	public Set<String> getMissingNature() {
		return new HashSet<String>();
	}

	@Override
	public void addNatures(final Set<String> natures) {
		List<String> existingNatures = new LinkedList<String>(Arrays.asList(this.description.getNatureIds()));
		boolean added = false;
		Iterator<String> iter = natures.iterator();
		while (iter.hasNext()) {
			String nature = iter.next();
			if (!existingNatures.contains(nature)) {
				existingNatures.add(nature);
				added = true;
			}
		}

		if (added) {
			touch();
			this.description.setNatureIds(existingNatures.toArray(new String[existingNatures.size()]));
		}
	}

	@Override
	public boolean hasBuildCommand(final String command) {
		ICommand[] buildSpec = this.description.getBuildSpec();
		for (int i = 0; i < buildSpec.length; i++) {
			if (buildSpec[i].getBuilderName().equals(command)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addBuildCommands(final Set<String> commands) {
		ICommand[] buildSpec = this.description.getBuildSpec();

		List<ICommand> buildSpecList = new ArrayList<ICommand>();
		buildSpecList.addAll(Arrays.asList(buildSpec));

		boolean added = false;
		Iterator<String> iter = commands.iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			if (!hasBuildCommand(name)) {
				ICommand cmd = this.description.newCommand();
				cmd.setBuilderName(name);
				buildSpecList.add(cmd);
				added = true;
			}
		}

		if (added) {
			touch();
			this.description.setBuildSpec(buildSpecList.toArray(new ICommand[buildSpecList.size()]));
		}
	}

	@Override
	protected void doSave() {
		if (this.description != null) {
			try {
				getProject().setDescription(this.description, null);
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
	}

	@Override
	public Set<String> getMissingBuildCommand() {
		return new HashSet<String>();
	}

}
