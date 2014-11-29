/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control;

/**
 *
 * @author alicia
 */
public interface Command
{
  public void execute();
  public void undo();
}