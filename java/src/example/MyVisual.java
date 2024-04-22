package example;

import C22398106.Eadaoinsvisual;
import C22337521.FionansVisual;
import C22339066.Alannahsvisual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PShape;
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
    PShape car;

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
        car = loadShape("sportscar.obj");
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
                colorMode(HSB); 
                background(0);  
                textAlign(CENTER, CENTER); 
                textSize(40);
                fill(255); 
                
                // Display a welcome message 
                text("Welcome to OOP 2024 assignment!", width / 2, height / 4);
                
                // Display mode selection 
                textSize(30); // Adjust text size
                text("Press 1 for ", width / 2, height / 2 - 50);
                text("Press 2 for ", width / 2, height / 2);
                text("Press 3 for", width / 2, height / 2 + 50);
                text("Press 4 for ", width / 2, height / 2 + 100);
                text("Press 5 for ", width / 2, height / 2 + 100);
                text("Press 6 for ", width / 2, height / 2 + 150);

                
                text("Press 'SPACE' to restart the current audio", width / 2, height - 100);

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

            //Both Éadaoin's
            case 5:
            {
                colorMode(HSB);
                background(200);
                stroke(255);
                translate(width/2, height/2);
                for(int i = 0; i < 2000;i+=60)
                {
                    float hue = map(i, 0, ab.size() + (ab.size() / 4), 50, 250);
                    float radius = 100+i;
                    //rotate entire shape
                    rotate(PApplet.radians(frameCount));
                    //map of bluish pinkish colours
                    
                    beginShape();
                    for(float a = 0; a < PApplet.TWO_PI; a+=PApplet.TWO_PI/8)
                    {
                        float x = radius * PApplet.cos(a);
                        float y =  radius * PApplet.sin(a);
                        //changes stroke weight on beat
                        noFill();
                        stroke(hue, 255, 255);
                        vertex(x * smooth, y * smooth);
                    }
                    endShape();
                }
                break;
            }
            case 6:
            {
                colorMode(HSB);
                background(200);
                translate(width/2, height/2);
                for(int i = 0; i < 2000;i+=65)
                {
                    float hue = map(i, 0, ab.size(), 0, 256);
                    float radius = 100+i;
                    //rotate entire shape
                    rotate(PApplet.radians(frameCount));
                    
                    beginShape();
                    for(float a = 0; a < PApplet.TWO_PI; a+=PApplet.TWO_PI/8)
                    {
                        float x = radius * PApplet.cos(a);
                        float y =  radius * PApplet.sin(a);
                        //changes stroke weight on beat
                        noFill();
                        stroke(hue, 150, 200);
			            vertex(x * smooth, y * smooth);
                        for (int j = 0; j < height; j += 25) {
                            float hue1 = map(i, 0, ab.size() , 0, 256);
                            fill(hue1, 255, 255);
                            stroke(15);
                            circle((i*2)/3, (j*2)/3, ((width/2) * smooth) / 2 );
                        }
                    }
                    endShape();
                }
                break;
            }
        }
    }
}
