package bounce;
import java.awt.Color;
public class DynamicRectangleShape extends RectangleShape {
   protected Color currentColor;
   private int borderWidth, borderHeight;
   private boolean isSide = true;
public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height) {
        super(x, y, deltaX, deltaY, width, height);
        this.currentColor = Color.red;
}

public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, Color p) {
        super(x, y, deltaX, deltaY, width, height);
       this.currentColor = p;
}
/**
	 * Creates a RectangleShape instance with specified values for instance 
	 * variables.
	 * @param x x position.
	 * @param y y position.
	 * @param deltaX speed (pixels per move call) and direction for horizontal 
	 *        axis.
	 * @param deltaY speed (pixels per move call) and direction for vertical 
	 *        axis.
	 * @param width width in pixels.
	 * @param height height in pixels.
	 * @param text the string to paint.
    * @param color is the colour of the text.
	 */
	public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, String text, Color p) {
		super(x,y,deltaX,deltaY,width,height, text);
      this.currentColor = p;
	}

@Override
public void move(int w, int h) {
    this.borderWidth = w;
    this.borderHeight = h;
		int nextX = _x + _deltaX;
		int nextY = _y + _deltaY;
		

		if (nextY <= 0) {
			nextY = 0;
			_deltaY = -_deltaY;
			this.isSide = false;
			
		} else if (nextY + _height >= borderHeight) {
			nextY = borderHeight - _height;
			_deltaY = -_deltaY;
			this.isSide = false;
		}
		if (nextX <= 0) {
			nextX = 0;
			_deltaX = -_deltaX;
			this.isSide = true;
			
		} else if (nextX + _width >= borderWidth) {
			nextX = borderWidth - _width;
			_deltaX = -_deltaX;
			this.isSide = true;
		}
		_x = nextX;
		_y = nextY;
		
	}
@Override
 protected void doPaint(Painter p) {
    if (isSide) {
    p.setColor(this.currentColor);
    p.fillRect(x(), y(), width(), height()); 
    p.setColor(Color.black);
    }
    else if (!isSide) {
    p.drawRect(x(), y(), width(), height());    
    }
 }

}