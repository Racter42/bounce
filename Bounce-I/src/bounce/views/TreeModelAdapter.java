package bounce.views;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeModel;
import javax.swing.event.TreeModelListener;
import java.util.*;
import java.awt.Event;
import bounce.NestingShape;

import bounce.Shape;

import bounce.ShapeModel;
public class TreeModelAdapter implements TreeModel {
private ShapeModel adapter;

public TreeModelAdapter(ShapeModel model) {
    adapter = model;
}
@Override 
public NestingShape getRoot() {
    return adapter.root();
}
@Override
public int getChildCount(Object parent) {
    if (parent instanceof NestingShape) {
        return ((NestingShape) parent).shapeCount(); /* Via method defined in NestingShape*/
    }
    else {
    return 0;
    }
}

@Override
public int getIndexOfChild(Object parent, Object child) {
    if (parent instanceof NestingShape && child instanceof Shape) {
        return ((NestingShape) parent).indexOf((Shape) child);
    }
    else {
        return -1;
    }
}
public Object getChild(Object parent, int index) {
    if (parent instanceof NestingShape && index < getChildCount(parent) && index >= 0) {
        return ((NestingShape) parent).shapeAt(index); 
    }
    else {
        return null;
    }
}
@Override
public boolean isLeaf(Object node) {
    if((node instanceof NestingShape)) {
       if (getChildCount(node) > 2) {
           return true;
       }
       else {
           return false;
       }
    }
    else {
        return getChildCount(node) == 0;
    }
}

@Override
public void valueForPathChanged(TreePath path, Object obj) {
    throw new UnsupportedOperationException();
}
@Override
public void addTreeModelListener(TreeModelListener listen) {
    throw new UnsupportedOperationException();
}
@Override
public void removeTreeModelListener(TreeModelListener listen) {
    throw new UnsupportedOperationException();
}
}