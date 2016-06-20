/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.jshared.common.model;

import java.io.Serializable;

/**
 *
 * @author LAB-315
 */
public class NuevoUsuario implements Serializable{
    public String nick;

    public NuevoUsuario(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    
    
}
