package com.tech.app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ModelGMA {
    public int nbMarquages;
    public Model R;

    public List<Marquages> markVector;
    Vector<Integer> M0;

    public ModelGMA() {
        this.nbMarquages = 0;
        this.M0 = R.fill_M0();
        this.markVector = new ArrayList<Marquages>();
    }

    /*public void calcGMA(Marquage M)
    {
        //for(i = 0, i < this.r.nbTransition; i++)
            //if(Transition n°i transmissible)
                //Appel de NextM
                //Si NextM n'est pas déjà dans la liste des marquages
                    //Création d'un nouvel objet Marquage et Ajout dans liste des Marquages
                    //Création arc entre M et Mnext
                //endif
                //calcGMA(Mnext)
            //endif
        //endfor



    }*/
}

