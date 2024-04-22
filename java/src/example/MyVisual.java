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

//import ddf.minim.*;
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
    float[] lerpedBuffer;
    float average = 0;
    int mode = 0;
    PShape car;


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
                text("Press 1 for Éadaoin's Circle Maze", width / 2, height / 2 - 50);
                text("Press 2 for ", width / 2, height / 2);
                text("Press 3 for Alannahs eye visual", width / 2, height / 2 + 50);
                text("Press 4 for Alannahs cube visual", width / 2, height / 2 + 100);
                text("Press 5 for Éadaoin's Kaleidoscope", width / 2, height / 2 + 150);
                text("Press 6 for Éadaoin's Circular Kaleidoscope", width / 2, height / 2 + 200);

                
                text("Press 'SPACE' to restart the current audio", width / 2, height - 100);
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
                float[] lerpedBuffer;
                float y = 0;
                float smoothedY = 0;
                float smoothedAmplitude = 0;

                y = height / 2;
                smoothedY = y;
        
                lerpedBuffer = new float[width];

                float off = 0;

                float lerpedAvg = 0;

                float halfH = height / 2;
                float average = 0;
                float sum1 = 0;
                off += 1;
                // Calculate sum and average of the samples
                // Also lerp each element of buffer;
                for(int i = 0 ; i < ab.size() ; i ++)
                {
                    sum1 += abs(ab.get(i));
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
                }
                average= sum1 / (float) ab.size();
        
                smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
                
                float cx = width / 2;
                float cy = height / 2;
        
                
                float tot = 0;
                for(int i = 0 ; i < ab.size() ; i ++)
                {
                    tot += abs(ab.get(i));
                }
        
                float avg = tot / ab.size();
        
                lerpedAvg = lerp(lerpedAvg, avg, 0.1f);

                background(0);
                lights();
                // float xx = 0, yy= 0, z= 0, centerX= 0, centerY= 0, centerZ = 0, upX= 0, upY= 0, upZ = 0;
                // camera(xx, yy, z, centerX, centerY, centerZ, upX, upY, upZ);

                //camera(500, 500, 500, 0, 0, 0, 0, 1, 0);

                background(lerpedAvg * 255); // Background colour react to audio

                for (int x = 0; x <= width; x += 60) {
                    for (int y1 = 0; y1 <= height; y1 += 60) {
                    pushMatrix();
                    translate(x, y1);

                    // Rotation reacts to music
                    rotateY(map(smoothedAmplitude, 0, 1, 0, PI)); 
                    rotateX(map(smoothedAmplitude, 0, 1, 0, PI));
                    
                    // Size reacts to music
                    box(90 + lerpedAvg * 50); 
                    popMatrix();
                    }
                }
            
                // Map colour based on lerpedAvg
                float hue = map(lerpedAvg, 0, 1, 0, 255);
                stroke(hue, 255, 255);
                fill(hue, 255, 255);

                translate(0, 0, 200); // Move forward in Z before drawing other shapes - (to avoid the triangle to disappear behind the matrix)
                // ellipse(width/2, height/2, 100, 100);

                // Audio-reactive triangle
                float triangleSize = lerpedAvg * height/2 * 5;
                float angle = radians(frameCount) + lerpedAvg * TWO_PI; // Rotation with music
                            
                float x1 = width/2 + cos(angle) * triangleSize;
                float y1 = height/2 + sin(angle) * triangleSize;
                float x2 = width/2 + cos(angle + TWO_PI / 3) * triangleSize;
                float y2 = height/2 + sin(angle + TWO_PI / 3) * triangleSize;
                float x3 = width/2 + cos(angle + 2 * TWO_PI / 3) * triangleSize;
                float y3 = height/2 + sin(angle + 2 * TWO_PI / 3) * triangleSize;

                // Colour changes based on lerpedAvg
                float hue1 = frameCount % 255; 
                stroke(hue1, 255, 255);
                fill(hue1, 255, 255);

                triangle(x1, y1, x2, y2, x3, y3);

                // // Draw webcam with opacity
                // if (cam.available()) {
                //     cam.read();
                //     tint(255, 128);  // 50% opacity
                //     image(cam, 0, 0, width, height); 
                // }
                    
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

            //Alannah's visual
            case 4:
            {
                fft = new FFT(ab.size(), 44100);

                // Display across the whole screen
                nbCubes = (int) (fft.specSize() * specHi); // Make sure this calculation is correct

                cubes = new Cube[nbCubes]; // Ensure it's not null
                
                // Cube initialization
                for (int i = 0; i < nbCubes; i++) {
                    cubes[i] = new Cube(); // Properly initialize each cube
                }
                
                fft.forward(ab);

                // Initialize arrays for line displacements
                lineOffsetsLeft = new float[lineSegments];
                lineOffsetsRight= new float[lineSegments];

                

                // Update line offsets based on FFT data
                updateLineOffsets();

                stroke(255);
                noFill();

                // Draw diagonal lines with wave-like movements
                drawLine(lineOffsetsLeft, 0, 0, width, height);  // Top-left to bottom-right
                drawLine(lineOffsetsRight, width, 0, 0, height);  // Top-right to bottom-left

                

                //smoothedAmplitude=smoothedAmplitude*0.9f+fft.mix.level*0.1f;
                // Display cubes
                stroke(0);
                for (int i = 0; i < nbCubes; i++) {
                    cubes[i].display((float) specLow, (float) specMid, (float) specHi, smoothedAmplitude, 0);
                }
                        

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
