/* $Header: Harness.java 21-nov-2007.19:07:53 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       11/21/07 - Bug 6624651 - fix compilation warning.
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;

import java.util.HashSet;
import java.util.Random;

import java.util.Set;

import javax.swing.Icon;

import javax.swing.JFrame;

import javax.swing.Timer;

import oracle.ide.model.Node;

import oracle.ide.net.URLFactory;

import oracle.javatools.icons.OracleIcons;

public class Harness {
    
    private final Random random = new Random();
    private final Set<Node> openNodes = new HashSet<Node>();
    private final NodeListPanel panel = new NodeListPanel();
    private int counter = 0;
    

    public static void main( String[] args ) {
        new Harness().run();
    }
    
    private void run() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        f.setContentPane( panel );
        
        f.pack();
        f.setVisible( true );
        
        Timer t = new Timer( 3000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                whenTimerFires();
            }
        });
        
        t.setRepeats( true );
        t.start();
    }
    
    private void whenTimerFires() {
        boolean add = random.nextBoolean();
        if ( add ) {
            int count = (int) (random.nextDouble() * 5.0 );
            for ( int i=0; i < count; i++ ) {
                openNodes.add( createNode() );
            }
        }
        else {
            if ( !openNodes.isEmpty() ) {
                openNodes.remove( openNodes.iterator().next() );
            }
        }
        panel.refresh( openNodes );   
    }


    private DummyNode createNode() {
        String name = "Node" + counter;
        counter++;
        return new DummyNode( URLFactory.newURL( "file:/" + name ), OracleIcons.getIcon( OracleIcons.FILE ), name );
    }

    private class DummyNode extends Node {
        private final Icon icon;
        private final String shortLabel;
        DummyNode( URL url, Icon icon, String shortLabel ) {
            super( url );
            this.icon = icon;
            this.shortLabel = shortLabel;
        }

        @Override
        public String getShortLabel() {
            return shortLabel;
        }

        @Override
        public Icon getIcon() {
            return icon;
        }
    }
}
