package org.eclipse.papyrus.infra.core.sasheditor.tests.texteditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.part.FileEditorInput;

/**
 * A fake {@link IEditorInput} suitable for creating TextEditor.
 * This IEditorInput allows to carry the {@link ISashWindowsContentProvider} to the created Editor.
 *
 *
 * @copy Copied from org.eclipse.ui.internal.part.NullEditorInput v3.8
 */
public class FakeEditorInput extends FileEditorInput implements IEditorInput {

	protected ISashWindowsContentProvider contentProvider;

	static int count = 0;

	/**
	 * Create a temporary file to pass to the texteditor.
	 * 
	 * @return
	 */
	static IFile createIFile() {
		// the name used is no important as nothing is created.
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject("org.eclipse.papyrus.tests.tmp");
		IFile model1File = p.getFile("tmp/model" + count++ + ".txt");
		// System.err.println("file: " + model1File.getName());
		return model1File;
	}

	/**
	 * Creates a <code>FakeEditorInput</code>.
	 */
	public FakeEditorInput() {
		super(createIFile());
	}

	/**
	 * Constructor.
	 * Carry the contentProvider to the created editor.
	 *
	 * @param contentProvider
	 */
	public FakeEditorInput(ISashWindowsContentProvider contentProvider) {
		super(createIFile());
		this.contentProvider = contentProvider;
	}

	/**
	 *
	 * @return
	 */
	public ISashWindowsContentProvider getContentProvider() {
		return contentProvider;
	}

	/**
	 *
	 * @param contentProvider
	 */
	public void setContentProvider(ISashWindowsContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	@Override
	public boolean exists() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	// public String getName() {
	// String result = null;
	// return ""; //$NON-NLS-1$
	// }

	/**
	 * We need to implement this method to avoid possible exceptions.
	 * Moreover, we get easier way to get the file path.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.FileEditorInput#getPath()
	 */
	@Override
	public IPath getPath() {
		return getFile().getFullPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

}
