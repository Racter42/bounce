package bounce;

public class OvalShape extends Shape {
   public OvalShape() {
      super();
   }
   public OvalShape(int x, int y) {
      super(x, y);
   }
   public OvalShape(int x, int y, int deltaX, int deltaY) {
      super(x, y, deltaX, deltaY);
   }
   public OvalShape(int x, int y, int deltaX, int deltaY, int width, int height) {
      super(x, y, deltaX, deltaY, width, height);
   }
   protected void doPaint(Painter p) {   
      p.drawOval(x(), y(), width(), height());
   }
}