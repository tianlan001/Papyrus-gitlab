/**
 *
 */
package org.eclipse.papyrus.infra.ui.extension.diagrameditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.ui.editorsfactory.IEditorIconFactoryExtended;
import org.eclipse.swt.graphics.Image;

/**
 * A factory used to create the Icon associated to an editor TODO Lets have a
 * common ancestor for {@link EditorIconFactory} and {@link EditorFactoryProxy}
 *
 * @author cedric dumoulin
 * @since 1.2
 *
 */
public class EditorIconFactory implements IEditorIconFactoryExtended {

	/**
	 * The concrete implementation.
	 */
	private IPluggableEditorFactory editorFactory;

	/**
	 * EditorDescriptor associated to the factory.
	 */
	protected EditorDescriptor editorDescriptor;

	/**
	 * Cached image for reuse.
	 */
	protected Image cachedImage;

	/**
	 * Constructor.
	 *
	 * @param serviceRegistry
	 * @param editorDescriptor
	 */
	public EditorIconFactory(EditorDescriptor editorDescriptor) {
		this.editorDescriptor = editorDescriptor;
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IEditorIconFactory#getEditorIcon(java.lang.Object)
	 *
	 * @param pageIdentifier
	 * @return
	 */
	@Override
	public Image getEditorIcon(Object pageIdentifier) {

		if (cachedImage == null) {
			cachedImage = createEditorIcon(pageIdentifier);
		}

		return cachedImage;
	}

	/**
	 * Create an Image associated to the editor used to render the specified
	 * pageIdentifier
	 *
	 * @return
	 */
	@Override
	public Image createEditorIcon(Object pageIdentifier) {
		ImageDescriptor imageDescriptor = editorDescriptor.getIcon();
		if (imageDescriptor == null) {
			return null;
		}
		Image image = imageDescriptor.createImage();
		return image;
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IEditorFactory#isPageModelFactoryFor(java.lang.Object)
	 *
	 * @param pageIdentifier
	 * @return
	 */
	@Override
	public boolean isPageModelFactoryFor(Object pageIdentifier) {
		return getEditorFactory().isPageModelFactoryFor(pageIdentifier);
	}

	/**
	 * @return the editorFactory
	 */
	protected IPluggableEditorFactory getEditorFactory() {

		if (editorFactory == null) {
			editorFactory = createEditorFactory();
		}

		return editorFactory;

	}

	/**
	 * Create an instance of IPluggableEditorFactory as described in the
	 * editorDescriptor. TODO let propagate the exceptions.
	 *
	 * @return
	 */
	private IPluggableEditorFactory createEditorFactory() {
		// Create the requested class.
		try {
			editorFactory = editorDescriptor.getEditorFactoryClass().newInstance();
			// Set the descriptor. USed by the factory to get the ActionBarId
			// and Icon
			// editorFactory.init(serviceRegistry, editorDescriptor);
			return editorFactory;
		} catch (InstantiationException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		}

	}

	/**
	 * Return the URL of the main icon used to create this icon
	 *
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IEditorIconFactory#getURLMainIcon(java.lang.Object)
	 *
	 * @param pageIdentifier
	 * @return
	 */
	@Override
	public String getURLMainIcon(Object pageIdentifier) {
		return editorDescriptor.getIconURL();
	}

	/**
	 * {@inheritDoc}
	 *
	 * Dispose the cached image
	 */
	@Override
	public void dispose() {
		if (cachedImage != null) {
			cachedImage.dispose();
			cachedImage = null;
		}
	}

}
