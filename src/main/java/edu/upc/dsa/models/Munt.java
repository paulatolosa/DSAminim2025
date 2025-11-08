package edu.upc.dsa.models;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Munt {


    Stack<Llibre> muntLlibres = new Stack<>();
    int maxLlibres=10;

    public Munt(){
        this.muntLlibres=new Stack<>();
    }

    public Stack<Llibre> getMuntLlibres() {
        return muntLlibres;
    }

    public boolean addLlibre(Llibre llibre){
        if(muntLlibres.size()<maxLlibres){
            muntLlibres.push(llibre);
            return true;
        }
        return false;
    }

    public boolean muntPle(){
        if(muntLlibres.size()==maxLlibres){
            return true;
        }
        return false;
    }

    public boolean muntBuit(){
        if(muntLlibres.size()==0){
            return true;
        }
        return false;
    }

    public Llibre getLlibre(){
        if(muntLlibres.size()==0){
            return null;
        }
        return muntLlibres.pop();
    }

    public int GetMida(){
        return muntLlibres.size();
    }

}
