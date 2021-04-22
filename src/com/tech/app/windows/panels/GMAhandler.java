package com.tech.app.windows.panels;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.tech.app.models.Model;
import com.tech.app.models.gma.CustomEdge;
import com.tech.app.models.gma.Node;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static javax.swing.GroupLayout.DEFAULT_SIZE;

public class GMAhandler {

    private final JFrame frame;
    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private ListenableGraph<String, DefaultEdge> g;
    private List<Node> liste_nodes;


    public GMAhandler(JFrame frame, List<Node> liste_nodes) {
        this.frame = frame;
        this.liste_nodes = liste_nodes;
        this.g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));
        this.jgxAdapter = new JGraphXAdapter<>(g);
    }

    public void init() {
        // create a visualization using JGraph, via an adapter

        for (Node n: liste_nodes) {
            //System.out.println(n.getMarquage());
            g.addVertex(n.getMarquage());
        }

        for (Node n : liste_nodes) {
            for(int i=0; i< n.getParents().size(); i++){
                //System.out.println(n.getMarquage()+" - "+n.getParents().get(i).node.getMarquage());
                g.addEdge(n.getMarquage(),n.getParents().get(i).node.getMarquage(), new CustomEdge(n.getParents().get(i).transition.getName()));
            }
        }





        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        this.frame.getContentPane().add(component);

        var layout = new mxHierarchicalLayout(jgxAdapter, SwingConstants.WEST);
        layout.execute(jgxAdapter.getDefaultParent());


        /*
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        mxCompactTreeLayout layout2 = new mxCompactTreeLayout(jgxAdapter);

        float edgeDistance = 10;
        layout2.setUseBoundingBox(true);
        layout2.setResizeParent(true);
        layout2.setLevelDistance((int)(edgeDistance*1.5f));
        layout2.setNodeDistance((int)(0.2f * edgeDistance*edgeDistance*2f));
        layout2.setInvert(true);



        // center the circle
        int radius = 100;

        layout.setX0((this.frame.getWidth() / 2.0) - radius);
        layout.setY0((this.frame.getHeight() / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
        */

    }

}
