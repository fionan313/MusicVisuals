package C22337521;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PShape;



public class FionansVisual extends Visual {

    PShape car;
    float ry;

    public void settings() {
        //size(1024, 500);
        
        

        // Use this to make fullscreen
        // fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();
        


        size(640, 360, P3D);
        // Call loadAudio to load an audio file to process
        loadAudio("Bicep - Glue (Original Mix)_q5rliCxX8xc.mp3");
        car = loadShape("sportscar.obj");
    }

    public void draw() {
        background(0);
        try {
            // Call this if you want to use FFT data
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        // Call this is you want to use frequency bands
        calculateFrequencyBands();

        // Call this is you want to get the average amplitude
        calculateAverageAmplitude();

        lights();
    
        translate(width/2, height/2 + 100, -200);
        rotateZ(PI);
        rotateY(ry);
        shape(car);
        
        ry += 0.02;
    }

    
    
}
