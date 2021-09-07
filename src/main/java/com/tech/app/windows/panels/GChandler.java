package com.tech.app.windows.panels;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.tech.app.models.gma.CustomEdge;
import com.tech.app.models.gma.Node;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import javax.swing.*;
import java.util.List;

/**
 * Classe qui gère l'affichage du Graphe de couverture.
 */
public class GChandler {

    private final JFrame frame;
    private final JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private final ListenableGraph<String, DefaultEdge> g;
    private final List<Node> liste_nodes;

    /**
     * Constructeur du GChandler, qui nous permet
     * d'afficher le GMA/Graphe de couverture dans la fenêtre dans laquelle
     * il est appelé
     * @param frame Fenêtre d'appel
     * @param liste_nodes Liste des noeuds du GMA
     */
    public GChandler(JFrame frame, List<Node> liste_nodes) {
        this.frame = frame;
        this.liste_nodes = liste_nodes;
        this.g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));
        this.jgxAdapter = new JGraphXAdapter<>(g);
    }

    /**
     * Visualiser le GMA/ Graphe de couverture
     */
    public void init() {
        // create a visualization using JGraph, via an adapter
        for (Node n: liste_nodes) {
            g.addVertex(n.getMarquage());
        }

        for (Node n : liste_nodes) {
            for(int i=0; i< n.getChildren().size(); i++){
                g.addEdge(n.getMarquage(),n.getChildren().get(i).node.getMarquage(), new CustomEdge(n.getChildren().get(i).transition.getName()));
            }
        }

        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        this.frame.getContentPane().add(component);
        this.frame.setTitle(this.frame.getTitle() + " | Marquages accessibles : " + g.vertexSet().size());

        var layout = new mxHierarchicalLayout(jgxAdapter, SwingConstants.WEST);
        layout.execute(jgxAdapter.getDefaultParent());
    }
}
