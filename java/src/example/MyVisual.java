package example;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PShape;
import ie.tudublin.*;
import ddf.minim.analysis.*;
import processing.core.PVector;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    poly play;

    float smooth = 0;

    // variables used in alannahs code
    FFT fft;
    float smoothedAmplitude = 4;
    //cube related code
    int nbCubes;
    double specLow = 0.03; // 3%
    double specMid = 0.125; // 12.5%
    double specHi = 0.20;   // 20%
    Cube[] cubes;

    //line related code
    int x = width;
    int y = height;
    int x0 = 10; int y0 = 10;
    int lineSegments = 200; 
    float[] lineOffsetsLeft;
    float[] lineOffsetsRight;

    //eye related code
    PVector[] eyePositions;
    int[] eyeSizes;

    // FIONAN
    int mode = 0;

    float[] lerpedBuffer;

    float[] lerpedBuffer131;
    float y131 = 0;
    float smoothedY = 0;
    float smoothedAmplitude131 = 0;

    PShape car;
    PShape homer;

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

        y131 = height / 2;
        smoothedY = y131;

        lerpedBuffer131 = new float[width];

        car = loadShape("java/data/sportsCar.obj");
        homer = loadShape("java/data/homer.obj");
    }

    float off = 0;
    float lerpedAvg = 0;

    public void keyPressed() {
        if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
		if (keyCode == ' ') {
            if (ap.isPlaying()) {
                ap.pause();
            } else {
                ap.rewind();
                ap.play();
            }
        }
    }

    public void draw() {
        if (ab.size() <= 0) {
            System.out.println("Audio buffer is empty or not loaded properly.");
            return; // Exit if audio buffer is empty
        }
        float sum = 0;
        
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
        }
        float average = 0;
        average= sum / (float) ab.size();
        smooth = lerp(smooth, average, 0.1f);
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

        float average131 = 0;
        float sum131 = 0;
        off += 1;

        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum131 += abs(ab.get(i));
            lerpedBuffer131[i] = lerp(lerpedBuffer131[i], ab.get(i), 0.05f);
        }

        average131= sum131 / (float) ab.size();

        smoothedAmplitude131 = lerp(smoothedAmplitude131, average131, 0.1f);

        float tot = 0;
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            tot += abs(ab.get(i));
        }

        float avg = tot / ab.size();

        lerpedAvg = lerp(lerpedAvg, avg, 0.1f);

        switch(mode)
        {
            case 0:
            { 
                float hue = frameCount % 360;
                colorMode(HSB);
                background(hue, 255, 255);
                textAlign(CENTER, CENTER); 
                textSize(40);
                fill(255); 
                
                // Display a welcome message 
                text("Welcome to OOP 2024 assignment!", width / 2, height / 4);
                
                // Display mode selection 
                textSize(30); // Adjust text size
                text("Press 1 for Éadaoin's Circle Maze", width / 2, height / 2 - 50);
                text("Press 2 for Fionán's reactive triangle and 3D Matrix", width / 2, height / 2);
                text("Press 3 for Alannahs eye visual", width / 2, height / 2 + 50);
                text("Press 4 for Alannahs cube visual", width / 2, height / 2 + 100);
                text("Press 5 for Éadaoin's Kaleidoscope", width / 2, height / 2 + 150);
                text("Press 6 for Éadaoin's Circular Kaleidoscope", width / 2, height / 2 + 200);
                text("Press 7 for Fionán's 3D sports car and reactive triangles", width / 2, height / 2 + 250);
                text("Press 'SPACE' to pause the current audio", width / 2, height -50);

                break;
            }

            //Éadaoin's visual
            case 1:
            {
                play = new circles(this);
                play.render();
                break;
            }

            //Fionán's visual
            case 2:
            {
                play = new trimatrix(this);
                play.render();
                break;
            }

            //Alannah's visual
            case 3:
            {
                eyePositions = new PVector[]{
                    new PVector(width * 0.25f, height * 0.5f), 
                    new PVector(width * 0.75f, height * 0.5f), 
                    new PVector(width * 0.1f, height * 0.2f),  
                    new PVector(width * 0.9f, height * 0.2f),  
                    new PVector(width * 0.5f, height * 0.1f),  
                    new PVector(width * 0.1f, height * 0.8f),  
                    new PVector(width * 0.9f, height * 0.8f),  
                    new PVector(width * 0.5f, height * 0.9f)   
                };
        
                eyeSizes = new int[]{
                    300, 
                    300, 
                    200, 
                    200, 
                    200, 
                    200, 
                    200, 
                    200  
                };

                displayBackgroundEffect();
                drawEyes();
                break;   
            }

            case 4:
            {
                
                fft = new FFT(ab.size(), 44100);
                stroke(255);
                noFill();

                // Display across the whole screen
                nbCubes = (int) (fft.specSize() * specHi); // Make sure this calculation is correct

                cubes = new Cube[nbCubes]; // Ensure it's not null
                
                // Cube initialization
                for (int i = 0; i < nbCubes; i++) {
                    cubes[i] = new Cube(); // Properly initialize Aeach cube
                }
                
                fft.forward(ab);

                // Initialize arrays for line displacements
                lineOffsetsLeft = new float[lineSegments];
                lineOffsetsRight= new float[lineSegments];

                // Update line offsets based on FFT data
                updateLineOffsets();

                

                // Draw diagonal lines with wave-like movements
                drawLine(lineOffsetsLeft, 0, 0, width, height);  // Top-left to bottom-right
                drawLine(lineOffsetsRight, width, 0, 0, height);  // Top-right to bottom-left

                //smoothedAmplitude=smoothedAmplitude*0.9f+fft.mix.level*0.1f;
                // Display cubes
        
                for (int i = 0; i < nbCubes; i++) {
                    cubes[i].display((float) specLow, (float) specMid, (float) specHi, smoothedAmplitude, 0);
                }

                break;
            }

            //Both Éadaoin's
            case 5:
            {
                play = new vertex(this);
                play.render();   
                break;
            }
            case 6:
            {
                play = new circlevertex(this);
                play.render();   
                break;
                
            }
            //Fionán's visual
            case 7:
            {
                play = new tricar(this);
                play.render();
                break;
            }
        }
    }

    //functions for alannahs visuals

    private void displayBackgroundEffect() {
        float amp = 1 + smoothedAmplitude * 2; 
        float waveHeight = sin(frameCount / 10.0f) * amp * 50; 
        
        float centerX = width / 2; // Center of the screen
        float centerY = height / 2; // Center of the screen
    
        float numPoints = ab.size();
        float thetaInc = TWO_PI / numPoints;

        // Set the hue for blue 
        int blue = 210; 

        stroke(blue); // Always blue
        
        noFill();
    
        beginShape();
        for (int i = 0; i < numPoints; i++) {
            float px = centerX + cos(thetaInc * i) * waveHeight; // X coordinate relative to the center
            float py = centerY + sin(thetaInc * i) * waveHeight; // Y coordinate relative to the center
            vertex(px, py);
        }
        endShape(CLOSE); 
    }

    private void drawEyes() {
        for (int i = 0; i < eyePositions.length; i++) {
            PVector eye = eyePositions[i];

            PVector mouseVector = new PVector(mouseX, mouseY);

            // Calculate pupil positions
            PVector copyOfEye = eye.copy();
            PVector pupilDifference = mouseVector.copy().sub(eye).setMag(40);

            PVector pupil = copyOfEye.add(pupilDifference);

            // Draw eyes
            fill(255);
            ellipse(eye.x, eye.y, eyeSizes[i], eyeSizes[i]); 

            // Draw pupils
            fill(0);
            ellipse(pupil.x, pupil.y, eyeSizes[i] / 5, eyeSizes[i] / 5); // Proportionally larger pupil
        }
    }

    //functions for Alannahs cubes and lines, case 4
    private void updateLineOffsets() {
        for (int i = 0; i < lineSegments; i++) {
            int fftIndex = PApplet.floor(map(i, 0, lineSegments - 1, 0, fft.specSize()));
            float displacement = map(fft.getBand(fftIndex), 0, 10, -100, 100);

            lineOffsetsLeft[i] = displacement;  // For the diagonal line from top-left to bottom-right
            lineOffsetsRight[i] = displacement; // For the diagonal line from top-right to bottom-left
        }
    }

    private void drawLine(float[] offsets, float startX, float startY, float endX, float endY) {
        beginShape();
        for (int i = 0; i < lineSegments; i++) {
            float t = map(i, 0, lineSegments - 1, 0, 1);
            float x = lerp(startX, endX, t);
            float y = lerp(startY, endY, t) + offsets[i];  // Apply the calculated offset

            vertex(x, y);
        }
        endShape();
    }

    class Cube {
        float x, y, z; // Position
        float rotX, rotY, rotZ; // Rotation
        float sumRotX, sumRotY, sumRotZ; // Cumulative rotation
        float size; // Size of the cube
        float speed; // Speed of the cube
        float angle;
    
        Cube() {
            x = random(width);
            y = random(height);
            z = random(-10000, 1000);
            rotX = random(TWO_PI);
            rotY = random(TWO_PI);
            rotZ = random(TWO_PI);
            size = random(50, 200);
            speed = 1.0f; // Set default speed
        }
    
        void display(float scoreLow, float scoreMid, float scoreHi, float intensity, float scoreGlobal) {
            pushMatrix(); // Save current transformation matrix
            translate(x, y, z); // Move to the position of the cube
            rotateX(rotX); // Apply rotation around x-axis
            rotateY(rotY); // Apply rotation around y-axis
            rotateZ(rotZ); // Apply rotation around z-axis
            // Draw the cube
            fill(0, 255, 0);
            box(size); // Draw a cube with the given size
            popMatrix();
    
            // Update cube position
            updateSpeed();
            z += speed; // Move the cube forward

            float amp = 1 + smoothedAmplitude * 5;
            float waveHeight = sin(angle) * amp * size * -3;
            
            float numPoints = ab.size();
            float thetaInc = TWO_PI / numPoints;
        
            beginShape();
            for (int i = 0; i < numPoints; i++) {
                float px = x + cos(thetaInc * i) * waveHeight; // X coordinate with diagonal offset
                float py = y + sin(thetaInc * i) * waveHeight; // Y coordinate with diagonal offset
                vertex(px, py);
            }
            endShape(CLOSE); // Close the shape to connect the last point with the first
        }

        void updateSpeed() {
            float minAmplitude = 0.0f; 
            float maxAmplitude = 1.0f;
            float minSpeed = 0.5f; 
            float maxSpeed = 5.0f;
        
            speed = map(smoothedAmplitude, minAmplitude, maxAmplitude, minSpeed, maxSpeed);
        
            if (z > 1000) {
                // Reset position within visible range
                z = random(-10000, -1000); 
                x = random(width);
                y = random(height);
                rotX = random(TWO_PI);
                rotY = random(TWO_PI);
                rotZ = random(TWO_PI);
                size = random(50, 200); 
            }
        }
    }

    class VisualEffect {
        float x, y; // Position
        float size; // Size
        float speed; // Speed of reaction to music
        float hue; // Color
        float angle; // Angle for animation

        VisualEffect(float x, float y, float size, float speed, float hue) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
            this.hue = hue;
            angle = random(TWO_PI); // Random starting angle
        }

        void update() {
            // Update properties based on music
            float offset = map(smoothedAmplitude, 0, 1, -TWO_PI, TWO_PI);
            angle += speed + offset;
        }
    }
}
