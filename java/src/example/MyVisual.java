package example;

import C22398106.Eadaoinsvisual;
import C22337521.FionansVisual;
import C22339066.Alannahsvisual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import ie.tudublin.*;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    float smooth = 0;
    float[] lerpedBuffer;
    float average = 0;
    int mode = 0;

    public void settings() {
        //size(1024, 500);

        // Use this to make fullscreen
        // fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();
        minim = new Minim(this);
        colorMode(HSB,360,255,255);
        rectMode(CENTER);
        // Call loadAudio to load an audio file to process
        ap = minim.loadFile("Bicep - Glue (Original Mix)_q5rliCxX8xc.mp3", 1024);
        ap.play();
        ab = ap.mix;
        loadAudio("Bicep - Glue (Original Mix)_q5rliCxX8xc.mp3");
        // Call this instead to read audio from the microphone
        //startListening();

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
        lerpedBuffer = new float[width];
    }

    public void keyPressed() {
        if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
		if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
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
    }

    public void draw() {
        if (ab.size() <= 0) {
            System.out.println("Audio buffer is empty or not loaded properly.");
            return; // Exit if audio buffer is empty
        }
        float sum = 0;
        smooth = lerp(smooth, average, 0.1f);
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
        }
        average= sum / (float) ab.size();
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
                break;
            }

            //Éadaoin's visual
            case 1:
            {
                colorMode(HSB);
                background(200);
                stroke(0); 
                for (int i = 0; i < width + 25; i += 25) {
                    for (int j = 0; j < height + 25; j += 25) {
                    float hue = map(i, 0, ab.size() , 0, 256);
                    fill(hue, 255, 255);
                    stroke(15);
                    circle(i, j, (width /2) * smooth);
                    }
                }
                break;
            }

            //Fionán's visual
            case 2:
            {
                break;
            }

            //Alannah's visual
            case 3:
            {
                break;   
            }

            //Group's visual
            case 4:
            {
                break;
            }
        }
    }
}
