package edu.gatech.sophia;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.util.JmeFormatter;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.CardLayout;
import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * This class contains the entry point and is responsible
 * for initializing all views and controllers
 */
public class SophiaView {
    private static JmeCanvasContext context;
    private static Canvas canvas;
    private static Application app;
    private static JFrame frame;
    private static JPanel cards;
    private static Container canvasPanel;
    private static final String appClass = "edu.gatech.sophia.PlaybackView";

    /**
     * Name of file picker view for card switching
     */
    private final static String SFILEPICKERPANEL = "SFilePicker";
    private final static String SIMPARAMPANEL = "SimParam";

    /**
     * Controllers
     */
    private static SimulationController simController;
    private static VisualizationController vController;
    
    /**
     *Sets up the JPanel that the jME canvas will be in
     */
    private static void createPlaybackPanel() {
        canvasPanel = new JPanel();
        canvasPanel.setLayout(new BorderLayout());
        frame.add("Center", canvasPanel);
    }
    
    /**
     * Creates the top menu bar
     */
    private static void createMenu(){
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        /*
         * File menu
         */
        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);
        
        final JMenuItem itemOpen = new JMenuItem("Open");
        menuFile.add(itemOpen);
        itemOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        final JMenuItem itemClose = new JMenuItem("Close");
        menuFile.add(itemClose);
        itemClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        JMenuItem itemExit = new JMenuItem("Exit");
        menuFile.add(itemExit);
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.dispose();
                app.stop();
            }
        });
        
        /*
         * Edit menu
         */
        JMenu menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);
        
        final JMenuItem simParams = new JMenuItem("Simulation Parameters");
        menuEdit.add(simParams);
        simParams.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //TODO: open the simparams window
                
                
            }
        });
    }
    
    /**
     * Sets up the main SOPHIA application window and initializes views
     */
    private static void createFrame(){
        frame = new JFrame("SOPHIA");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                app.stop();
            }
        });
        
        //Set up the panel for the 3D simulation playback canvas
        createPlaybackPanel();

        //Set up card stack for switching GUIs
        cards = new JPanel(new CardLayout());
        
        //Create the file picker view
        FilePickerView fpView = new FilePickerView(simController);
        simController.setFilePickerView(fpView);
        cards.add(fpView, SFILEPICKERPANEL);
        
        //Set card stack to left side of window
        frame.add("West", cards);
        
        //Create the menu bar
        createMenu();
    }

    /**
     * Switches the left side view to the supplied card name
     *
     * @param cardName String representing the card to switch to
     */
    private static void switchView(String cardName) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, cardName);
    }

    /**
     * Sets up the jME canvas which will be added to the swing GUI
     *
     * @param appClass The name of the class to run as a jME application in the canvas
     */
    public static void createCanvas(String appClass){
        //Load default settings and set resolution
        AppSettings settings = new AppSettings(true);
        settings.setWidth(640);
        settings.setHeight(480);

        //Initialize the jME Application instance
        try{
            Class<? extends Application> clazz = (Class<? extends Application>) Class.forName(appClass);
            app = clazz.newInstance();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (InstantiationException ex){
            ex.printStackTrace();
        }catch (IllegalAccessException ex){
            ex.printStackTrace();
        }

        //Make sure rendering continues if user clicks somewhere else
        app.setPauseOnLostFocus(false);
        
        //Standard initialization for SimpleApplication
        app.setSettings(settings);
        app.createCanvas();
        app.startCanvas();

        //Get the canvas from jME for use in the GUI
        context = (JmeCanvasContext) app.getContext();
        canvas = context.getCanvas();
        canvas.setSize(settings.getWidth(), settings.getHeight());
    }

    /**
     *Starts the jME rendering
     */
    public static void startApp(){
        app.startCanvas();
        app.enqueue(new Callable<Void>(){
            public Void call(){
                if (app instanceof SimpleApplication){
                    SimpleApplication simpleApp = (SimpleApplication) app;
                    simpleApp.getFlyByCamera().setDragToRotate(true);
                }
                return null;
            }
        });
    }

    /**
     * Main entry point
     */
    public static void main(String[] args){
        //Logging setup
        JmeFormatter formatter = new JmeFormatter();
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        Logger.getLogger("").removeHandler(Logger.getLogger("").getHandlers()[0]);
        Logger.getLogger("").addHandler(consoleHandler);

        //Set up visualization controller
        vController = new VisualizationController();

        //Set up simulation controller for view callbacks
        simController = new SimulationController();
        simController.setVisualizationController(vController);
        
        //Set up the jME canvas
        createCanvas(appClass);
        vController.setPlaybackView((PlaybackView)app);
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
        
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JPopupMenu.setDefaultLightWeightPopupEnabled(false);

                createFrame();
                
                canvasPanel.add(canvas, BorderLayout.CENTER);
                frame.pack();
                startApp();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
