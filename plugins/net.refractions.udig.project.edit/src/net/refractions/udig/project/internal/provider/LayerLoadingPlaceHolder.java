package net.refractions.udig.project.internal.provider;

import net.refractions.udig.project.edit.internal.Messages;
import net.refractions.udig.project.internal.Layer;
import net.refractions.udig.project.internal.LayerDecorator;
import net.refractions.udig.project.internal.ProjectFactory;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * Placeholder that will be placed in viewers while layers are being loaded.
 * 
 * @author Jesse
 * @since 1.1.0
 */
public class LayerLoadingPlaceHolder extends LayerDecorator implements LoadingPlaceHolder {

    /**
     * Placeholder used to indicate to user that we are "Loading ...."
     */
    public static final LayerLoadingPlaceHolder LOADING_LAYER;
    static {
        LOADING_LAYER = new LayerLoadingPlaceHolder(ProjectFactory.eINSTANCE.createLayer());
        LOADING_LAYER.setName(Messages.ProjectItemProvider_loading);
        Bundle bundle = ProjectEditPlugin.getPlugin().getBundle();

        IPath path = new Path("icons/full/obj16/Layer.gif"); //$NON-NLS-1$
        ImageDescriptor image = ImageDescriptor.createFromURL(FileLocator.find(bundle, path, null));
        LOADING_LAYER.setIcon(image);
        LOADING_LAYER.setVisible(true);
    }

    public LayerLoadingPlaceHolder( Layer layer ) {
        super(layer);
    }

    public Image getImage() {
        return getIcon().createImage();
    }

    public String getText() {
        return getName(); // ie Loading ...
    }

    public int getZorder() {
        return Integer.MAX_VALUE; // always sort to the top of the layer list
    }
}