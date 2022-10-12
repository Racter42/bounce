package bounce.forms;

import bounce.ImageRectangleShape;

import bounce.NestingShape;

import bounce.ShapeModel;
import java.util.concurrent.ExecutionException;
import bounce.forms.util.Form;

import bounce.forms.util.FormHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

/* Used to resize images taken from the ImageRectangleShape class  
 */
 
public class ImageShapeFormHandler<T> implements FormHandler {
    
    private ShapeModel _model;
	private NestingShape _parentOfNewShape;
	private Form _imageForm;
	private SwingWorker<BufferedImage, Void> _swingWorker;
    
	
	public ImageShapeFormHandler(ShapeModel model, NestingShape parentOfNewShape) {
	    this._model = model;
	    this._parentOfNewShape = parentOfNewShape;
	}

    @Override 
    public void processForm(Form form) {
    
         this._imageForm = form;
        _swingWorker = new ImageWorker( _model, _imageForm, _parentOfNewShape );
        _swingWorker.execute();
 }

 class ImageWorker extends SwingWorker<BufferedImage, Void> {
     
     private File filename;
     private int width;
     private int deltaX;
     private int deltaY;
     private int fullImageWidth;
     private int fullImageHeight;
     private Form _imageForm;
     private NestingShape _parentOfNewShape;
     
     private ShapeModel _model;
     public ImageWorker(ShapeModel _model, Form _imageForm, NestingShape _parentOfNewShape) {
         this._model = _model;
         this._imageForm = _imageForm;
         this._parentOfNewShape = _parentOfNewShape;
     }
     @Override
    protected BufferedImage doInBackground() throws Exception {     
    
     filename = (File)_imageForm.getFieldValue(File.class, ImageFormElement.IMAGE);
		width = _imageForm.getFieldValue(Integer.class, ShapeFormElement.WIDTH);
		deltaX = _imageForm.getFieldValue(Integer.class, ShapeFormElement.DELTA_X);
		deltaY = _imageForm.getFieldValue(Integer.class, ShapeFormElement.DELTA_Y);
    

		BufferedImage fullImage = null;
		try {
			fullImage = ImageIO.read(filename);
		} catch(IOException e) {
			System.out.println(e);
			System.out.println("Error loading image.");
		}
	
    
		fullImageWidth = fullImage.getWidth();
		fullImageHeight = fullImage.getHeight();
    
        long startTime = System.currentTimeMillis();
		BufferedImage scaledImage = fullImage;
        int height = fullImageHeight;
        if (fullImageWidth > width) {
            double scaleFactor = (double) width / (double) fullImageWidth;
            height = (int)((double)fullImageHeight * scaleFactor);
        }
        scaledImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics2D g = scaledImage.createGraphics();
			g.drawImage(fullImage, 0, 0, width, height, null);
		ImageRectangleShape imageShape = new ImageRectangleShape(deltaX, deltaY, scaledImage);
		_model.add(imageShape, _parentOfNewShape);
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Image loading and scaling took " + elapsedTime + "ms.");
		return scaledImage;
    }
    @Override
    protected void done() {
        try {

				BufferedImage image = this.get();
				ImageRectangleShape imageShape = new ImageRectangleShape(deltaX, deltaY, image);
				_model.add(imageShape, _parentOfNewShape);
			} catch (InterruptedException | ExecutionException e) {

				e.printStackTrace();

			}
    }
 }
}



 


