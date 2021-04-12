package com.tech.app.windows.handlers;

import com.tech.app.models.Model;

import java.io.*;

public class SaveManager {

    public boolean save(File f, Model model) {
        try {
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(model);
            out.close();
            fileOut.close();
            System.out.println("Model Object Serialized saved in " + f.getName());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Model load(File f) {
        Model model = null;
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            model = (Model) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Model loaded: " + model);
            return model;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
