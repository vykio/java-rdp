package com.tech.app.windows.handlers;

import com.tech.app.models.Model;
import com.tech.app.models.SaveObject;

import java.io.*;

public class SaveManager {

    public boolean save(File f, Model model) {

        if (!f.getAbsolutePath().endsWith(".jrdp")) {
            f = (new File(f.getAbsolutePath() + ".jrdp"));
        }

        try {
            //SaveObject so = new SaveObject(model.placeVector, model.transitionVector);
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(model);
            out.close();
            fileOut.close();
            //System.out.println("Model Object Serialized saved in " + f.getName());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Model load(File f, Model model) {
        Model mo = null;
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mo = (Model) in.readObject();
            model = mo;
            in.close();
            fileIn.close();

            //System.out.println("Model loaded: " + model);
            return model;
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

}
