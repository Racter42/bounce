package bounce;

import java.util.*;
import java.io.IOException;
public class NestingShape extends Shape {
    protected List<Shape> nestListShapes = new ArrayList<Shape>();
    
    public NestingShape() {
        super();
    }
    
    public NestingShape(int x, int y) {
		this(x, y, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    /**
	 * Creates a NestingShape instance with specified values for instance 
	 * variables.
	 * @param x x position.
	 * @param y y position.
	 * @param deltaX speed and direction for horizontal axis.
	 * @param deltaY speed and direction for vertical axis.
	 */
	public NestingShape(int x, int y, int deltaX, int deltaY) {
		super(x,y,deltaX,deltaY);
	}
	
	/**
	 * Creates a NestingShape instance with specified values for instance 
	 * variables.
	 * @param x x position.
	 * @param y y position.
	 * @param deltaX speed (pixels per move call) and direction for horizontal 
	 *        axis.
	 * @param deltaY speed (pixels per move call) and direction for vertical 
	 *        axis.
	 * @param width width in pixels.
	 * @param height height in pixels.
	 */
	public NestingShape(int x, int y, int deltaX, int deltaY, int width, int height) {
		super(x,y,deltaX,deltaY,width,height);
	}
	/**
	 * Creates a NestingShape instance with specified values for instance 
	 * variables.
	 * @param x x position.
	 * @param y y position.
	 * @param deltaX speed (pixels per move call) and direction for horizontal 
	 *        axis.
	 * @param deltaY speed (pixels per move call) and direction for vertical 
	 *        axis.
	 * @param width width in pixels.
	 * @param height height in pixels.
    * @param text string
	 */
	public NestingShape(int x, int y, int deltaX, int deltaY, int width, int height, String text) {
		super(x,y,deltaX,deltaY,width,height, text);
	}


/**
* Moves a NestingShape object (including its children) within the bounds specified by arguments
* width and height. A NestingShape first moves itself, and then moves its children.
*/
public void move (int width, int height) {
    super.move(width, height);
    for (Shape instance:nestListShapes) {
        instance.move(width(), height());
    }
}

/**
* Paints a NestingShape object by drawing a rectangle around the edge of its bounding box. 
*/
@Override
protected void doPaint (Painter p){
    p.drawRect(x(), y(), width(), height());
    p.translate(x(), y());
    for (Shape instance:nestListShapes) {
        instance.paint(p);
    }
    p.translate(-x(), -y());
}
void add (Shape shape) throws IllegalArgumentException {
    if (shape.x() < this.x()) {
        throw new IllegalArgumentException();
    }
    else if (shape.y() < this.y()) {
         throw new IllegalArgumentException();
    }
    else if (shape.x() + shape.width() > this.x() + this.width()) {
         throw new IllegalArgumentException();
    }
    else if (shape.y() + shape.height()> this.y() + this.height()) {
         throw new IllegalArgumentException();
    }
    else if (shape._parent != null) {
        throw new IllegalArgumentException();
    }
    else {
        nestListShapes.add(shape);
        shape.setParent(this);
    }
}

void remove (Shape shape) {
    nestListShapes.remove(shape);
    shape._parent = null;
}

public Shape shapeAt (int index) throws IndexOutOfBoundsException {
    if (index >= nestListShapes.size()) {
        throw new IndexOutOfBoundsException();
    }
    else {
        return nestListShapes.get(index);
    }
}

public int shapeCount () {
    return nestListShapes.size();
}

public int indexOf (Shape shape) {
    if (this.contains(shape)) {
        return nestListShapes.indexOf(shape);
    }
    else {
        return -1;
    }
}

public boolean contains (Shape shape) {
    return nestListShapes.contains(shape);
}
}