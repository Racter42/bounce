package bounce.views;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeModel;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeModelEvent;
import java.util.*;
import java.awt.Event;
import bounce.ShapeModel;

import bounce.ShapeModelEvent;

import bounce.ShapeModelListener;



public class TreeModelAdapterWithShapeModelListener extends TreeModelAdapter implements ShapeModelListener {
    /* List that acts as treepath*/
    private List<TreeModelListener> listenPath = new ArrayList<TreeModelListener>(); 
    
    public TreeModelAdapterWithShapeModelListener(ShapeModel model) {
        super(model); /*uses constructor in TreeModelAdapter*/
    }
    /*Implements ShapeModelListener method */
    public void update(ShapeModelEvent event) {
        TreeModelEvent treeEvents;
        try {
            treeEvents = transferEvent(event);
        }
        catch (NullPointerException e) {
            return;
        }
        
        fireTreeModelEvent(event, treeEvents);
    }
    protected TreeModelEvent transferEvent(ShapeModelEvent event) {
        /* event = new TreeModelEvent(source, path,
          childIndices, children) uses methods in ShapeModelEvent*/
        return new TreeModelEvent(event.source(), new TreePath(event.parent().path().toArray()),
                            new int[] {event.index()}, new Object[] {event.operand()});
    } 
    /*
    javax.swing.event.TreeModelEvent event (ShapeModelEvent),
    javax.swing.event.TreeModelListener listener
    */
    protected void fireTreeModelEvent (ShapeModelEvent modelEvent, TreeModelEvent treeEvent) {
        switch (modelEvent.eventType()) {
            case ShapeAdded:
                fireTreeNodesInserted(treeEvent);
                break;
            case ShapeRemoved:
                fireTreeNodesRemoved(treeEvent);
                break;
            default:
                break;
        
        }
    }
    
    protected void fireTreeNodesInserted(TreeModelEvent e) {
        for (TreeModelListener treeListen: listenPath) {
            treeListen.treeNodesInserted(e);
        }
    }
    protected void fireTreeNodesRemoved(TreeModelEvent e) {
    for (TreeModelListener treeListen: listenPath) {
            treeListen.treeNodesRemoved(e);
        }
    } 
    @Override 
    public void addTreeModelListener(TreeModelListener listen) {
        listenPath.add(listen);
    }
     @Override 
    public void removeTreeModelListener(TreeModelListener listen) {
        listenPath.remove(listen);
    }
} 




