
package org.eclipse.papyrus.dev.project.management.handlers.plugins;

import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.papyrus.dev.project.management.internal.operations.SingletonBundle;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;


public class SingletonBundleHandler extends AbstractManifestUpdateHandler {

	public SingletonBundleHandler() {
		super();
	}

	@Override
	protected IUndoableOperation createUpdateOperation(Map<? extends IFile, ? extends IManifestEditor> manifests) {
		return new SingletonBundle(manifests);
	}
}
