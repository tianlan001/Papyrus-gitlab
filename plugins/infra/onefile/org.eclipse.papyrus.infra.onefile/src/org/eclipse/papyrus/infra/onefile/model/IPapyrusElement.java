package org.eclipse.papyrus.infra.onefile.model;

import org.eclipse.core.resources.IProject;

/**
 * Basic operations for Papyrus Model Elements
 *
 * @author tristan.faure@atosorigin.com
 *
 */
public interface IPapyrusElement {
	/**
	 * Return the text displayed for the {@link IPapyrusFile}
	 *
	 * @return
	 */
	String getText();

	/**
	 * Return the project containing the {@link IPapyrusFile}
	 *
	 * @return an {@link IProject}
	 */
	IProject getProject();

}
