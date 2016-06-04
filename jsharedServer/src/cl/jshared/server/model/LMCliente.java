/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.jshared.server.model;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author LAB-315
 */
public class LMCliente implements ListModel{

    @Override
    public int getSize() {
        return Server.getInstance().getClientes().size();
    }

    @Override
    public Object getElementAt(int index) {
        return Server.getInstance().getClientes().get(index).getNick();
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }
    
}
