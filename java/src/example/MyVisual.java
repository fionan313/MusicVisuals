package example;

import C22398106.Eadaoinsvisual;
import C22337521.FionansVisual;
import C22339066.Alannahsvisual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
//import processing.core.PApplet;
import ie.tudublin.*;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    float smooth = 0;
    int mode = 0;

    public void settings() {
        //size(1024, 500);

        // Use this to make fullscreen
        // fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        minim = new Minim(this);
        colorMode(HSB,360,255,255);

        // Call loadAudio to load an audio file to process
        ap = minim.loadFile("Bicep - Glue (Original Mix)_q5rliCxX8xc.mp3");
        ap.play();
        ab = ap.mix;
        rectMode(CENTER);
        // Call this instead to read audio from the microphone
        //startListening();

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
    }

    //public void keyPressed() {
        //if (key == ' ') {
        //    getAudioPlayer().cue(0);
        //    getAudioPlayer().play();
        //}

        //Éadaoin
        //if (key == '1')
        //{
            //Code here


            //ignore
            /*String[] a = { "MAIN" };
            processing.core.PApplet.runSketch(a, new Eadaoinsvisual());*/
        //}

        //Fionán
        //if (key == '2')
        //{
            //Code here


            //ignore
            /*String[] a = { "MAIN" };
            processing.core.PApplet.runSketch(a, new FionansVisual());*/
        //}

        //Alannah
        //if (key == '3')
        //{
            //Code here


            //ignore
            /*String[] a = { "MAIN" };
            processing.core.PApplet.runSketch(a, new Alannahsvisual());*/
        //}
    //}

    public void draw() {
        background(0);
        stroke(200,200,200);
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
        wf.render();
        abv.render();

        switch(mode)
        {
            case 0:
            {
                //Add Starter Screen Here
                //Like say press 1 for ""
                //Press 2 for ""
            }

            //Éadaoin's visual
            case 1:
            {
        
            }

            //Fionán's visual
            case 2:
            {
                
            }

            //Alannah's visual
            case 3:
            {
                
            }

            //Group's visual
            case 4:
            {
                
            }
        }
    }
}
