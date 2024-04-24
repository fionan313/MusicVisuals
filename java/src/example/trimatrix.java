package example; //declare package example
import processing.core.PApplet; //import the core Processing library

public class trimatrix extends poly {

    public trimatrix(MyVisual mv)
    { 
        super(mv);
    }

    @Override
    public void render() {

            mv.background(0); //set background to black
            mv.lights(); //enable processing lighting for 3D shapes
            
            // TEST CODE FOR CAMERA ANGLES
            //float xx = 0, yy= 0, z= 0, centerX= 0, centerY= 0, centerZ = 0, upX= 0, upY= 0, upZ = 0;
            //camera(xx, yy, z, centerX, centerY, centerZ, upX, upY, upZ);

            //camera(500, 500, 500, 0, 0, 0, 0, 1, 0);

            mv.background(mv.lerpedAvg * 255); //background colour react to audio

            // nested loop for matrix grid pattern
            for (int x = 0; x <= mv.width; x += 60) {
                for (int y = 0; y <= mv.height; y += 60) {
                mv.pushMatrix();
                mv.translate(x, y); //positions each box within grid

                //rotates each box along the Y and X-axis based on music
                mv.rotateY(PApplet.map(mv.smoothedAmplitude131, 0, 1, 0, PApplet.PI)); 
                mv.rotateX(PApplet.map(mv.smoothedAmplitude131, 0, 1, 0, PApplet.PI));
                
                mv.box(90 + mv.lerpedAvg * 50); //size reacts to music
                mv.popMatrix();
                }
            }
        
            //map colour based on mv.lerpedAvg
            float hue = PApplet.map(mv.lerpedAvg, 0, 1, 0, 255);
            mv.stroke(hue, 255, 255); //set outline colour
            mv.fill(hue, 255, 255); //set fill colour

            mv.translate(0, 0, 200); //move forward in Z before drawing other shapes - (to avoid the triangle to disappear behind the matrix)

            //audio-reactive triangle
            float triangleSize = mv.lerpedAvg * mv.height/2 * 5;
            float angle = PApplet.radians(mv.frameCount) + mv.lerpedAvg * PApplet.TWO_PI; //rotation with music
                        
            float x1 = mv.width/2 + PApplet.cos(angle) * triangleSize; //calculate X coordinates of the first triangle vertex
            float y1 = mv.height/2 + PApplet.sin(angle) * triangleSize; //calculate Y coordinates of the first triangle vertex
            //same calculations for vertex 2 + 3
            float x2 = mv.width/2 + PApplet.cos(angle + PApplet.TWO_PI / 3) * triangleSize;
            float y2 = mv.height/2 + PApplet.sin(angle + PApplet.TWO_PI / 3) * triangleSize;
            float x3 = mv.width/2 + PApplet.cos(angle + 2 * PApplet.TWO_PI / 3) * triangleSize;
            float y3 = mv.height/2 + PApplet.sin(angle + 2 * PApplet.TWO_PI / 3) * triangleSize;

            //colour changes based on mv.lerpedAvg
            float hue1 = mv.frameCount % 255; 
            mv.stroke(hue1, 255, 255);
            mv.fill(hue1, 255, 255);

            // Set the stroke and disable fill
            mv.stroke(0); //black
            mv.strokeWeight(2);

        
            mv.triangle(x1, y1, x2, y2, x3, y3); //draw the triangle

    }
    

}
