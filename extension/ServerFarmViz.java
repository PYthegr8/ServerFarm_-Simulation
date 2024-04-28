import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ServerFarmViz {
    JFrame win;
    protected JobDispatcher dispatcher;
    private LandscapePanel canvas;
    private boolean showViz ;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    private JButton pauseButton;

    private boolean paused;

    /**
     * Initializes a display window for a Landscape.
     * 
     * @param scape the Landscape to display
     * @param scale controls the relative size of the display
     */
    public ServerFarmViz(JobDispatcher dispatcher , boolean showViz ) {
        this.showViz = showViz;
        this.paused = false;

        if ( this.showViz ) {
            // setup the window
            this.win = new JFrame("Grid-Search");
            this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            this.dispatcher = dispatcher;

            // create a panel in which to display the Landscape
            // put a buffer of two rows around the display grid
            this.canvas = new LandscapePanel(WIDTH, HEIGHT);


            pauseButton = new JButton("Pause");
            pauseButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    togglePause();
                }
            });
            JPanel controlPanel = new JPanel();
            controlPanel.add(pauseButton);

            this.win.add(controlPanel, BorderLayout.NORTH);


            // add the panel to the window, layout, and display
            this.win.add(this.canvas, BorderLayout.CENTER);
            this.win.pack();
            this.win.setVisible(true);
        }
    }

    public void setJobDispatcher(JobDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void closeWindow() {
        this.win.dispose();
    }

    /**
     * Saves an image of the display contents to a file. The supplied
     * filename should have an extension supported by javax.imageio, e.g.
     * "png" or "jpg".
     *
     * @param filename the name of the file to save
     */
    public void saveImage(String filename) {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // create an image buffer to save this component
        Component tosave = this.win.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        // save the image
        try {
            ImageIO.write(image, ext, new File(filename));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private void togglePause() {
        paused = !paused;
        pauseButton.setText(paused ? "Resume" : "Pause");
        
    }

    public boolean isPaused() {
        return paused;
    }
    /**
     * This inner class provides the panel on which Landscape elements
     * are drawn.
     */
    private class LandscapePanel extends JPanel {
        /**
         * Creates the panel.
         * 
         * @param width  the width of the panel in pixels
         * @param height the height of the panel in pixels
         */
        public LandscapePanel(int width, int height) {
            super();
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.lightGray);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen. The supplied Graphics
         * object is used to draw.
         * 
         * @param g the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            // take care of housekeeping by calling parent paintComponent
            super.paintComponent(g);

            // call the Landscape draw method here
            dispatcher.draw(g);
        } // end paintComponent

    } // end LandscapePanel

    public void repaint() {

        while (paused) {
            try {
                Thread.sleep(10); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.showViz && !isPaused()) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.win.repaint();
        } 
        
    }
}