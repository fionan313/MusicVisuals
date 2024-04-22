package example;
import processing.core.PApplet;

public class trimatrix extends poly {

    public trimatrix(MyVisual mv)
    { 
        super(mv);
    }

    @Override
    public void render() {

        mv.background(0);
            mv.lights();
            //float xx = 0, yy= 0, z= 0, centerX= 0, centerY= 0, centerZ = 0, upX= 0, upY= 0, upZ = 0;
            //camera(xx, yy, z, centerX, centerY, centerZ, upX, upY, upZ);

            //camera(500, 500, 500, 0, 0, 0, 0, 1, 0);

            mv.background(mv.lerpedAvg * 255); // Background colour react to audio

            for (int x = 0; x <= mv.width; x += 60) {
                for (int y = 0; y <= mv.height; y += 60) {
                mv.pushMatrix();
                mv.translate(x, y);

                // Rotation reacts to music
                mv.rotateY(PApplet.map(mv.smoothedAmplitude, 0, 1, 0, PApplet.PI)); 
                mv.rotateX(PApplet.map(mv.smoothedAmplitude, 0, 1, 0, PApplet.PI));
                
                // Size reacts to music
                mv.box(90 + mv.lerpedAvg * 50); 
                mv.popMatrix();
                }
            }
        
            // Map colour based on mv.mv.mv.lerpedAvg
            float hue = PApplet.map(mv.lerpedAvg, 0, 1, 0, 255);
            mv.stroke(hue, 255, 255);
            mv.fill(hue, 255, 255);

            mv.translate(0, 0, 200); // Move forward in Z before drawing other shapes - (to avoid the triangle to disappear behind the matrix)
            // ellipse(width/2, height/2, 100, 100);

            

            // Audio-reactive triangle
            float triangleSize = mv.lerpedAvg * mv.height/2 * 5;
            float angle = PApplet.radians(mv.frameCount) + mv.lerpedAvg * PApplet.TWO_PI; // Rotation with music
                        
            float x1 = mv.width/2 + PApplet.cos(angle) * triangleSize;
            float y1 = mv.height/2 + PApplet.sin(angle) * triangleSize;
            float x2 = mv.width/2 + PApplet.cos(angle + PApplet.TWO_PI / 3) * triangleSize;
            float y2 = mv.height/2 + PApplet.sin(angle + PApplet.TWO_PI / 3) * triangleSize;
            float x3 = mv.width/2 + PApplet.cos(angle + 2 * PApplet.TWO_PI / 3) * triangleSize;
            float y3 = mv.height/2 + PApplet.sin(angle + 2 * PApplet.TWO_PI / 3) * triangleSize;

            // Colour changes based on mv.mv.mv.lerpedAvg
            float hue1 = mv.frameCount % 255; 
            mv.stroke(hue1, 255, 255);
            mv.fill(hue1, 255, 255);

            // Set the stroke and disable fill
            mv.stroke(0); // Black color
            mv.strokeWeight(2); // Adjust stroke weight if desired

        
            mv.triangle(x1, y1, x2, y2, x3, y3);

    }
    

}
