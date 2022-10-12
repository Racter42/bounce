package bounce;
import java.util.*;
import java.io.IOException;
import java.awt.Image;
public class Main {
   public static void main(String[] args) throws IOException {
      final int SHAPE_WIDTH = 100;
      Image image = ImageRectangleShape.makeImage("Holden.jpg", SHAPE_WIDTH);
      Shape shape = new ImageRectangleShape(1, 1, image);
   
      Painter painter = new MockPainter();
      shape.paint(painter);
      System.out.println(TestUtility.insertLineBreaks(painter.toString()));
   }
}