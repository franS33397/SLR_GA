/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SLR_GA;


/**
 *
 * @author Fran33
 */


import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import javax.swing.*;

public class SLR_GA_Agent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");

        addBehaviour(new GABehaviour());
    }



    private class GABehaviour extends OneShotBehaviour {

        public void action() {
            
            int individualSize =8;//= Integer.parseInt(JOptionPane.showInputDialog("Tama\u00f1o de Individuo: "));
            GA g = new GA(100,individualSize , 1000);

            g.runGA();
        }

        public int onEnd() {
			myAgent.doDelete();
			return super.onEnd();
		}
        
    }

}