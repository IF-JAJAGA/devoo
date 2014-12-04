package fr.insaif.jajagaa.control.Commands;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Interface du design pattern Command
 * @author alicia
 */
public interface Command {
    public void execute();
    public void undo();
}