package example;
import processing.core.PApplet;

public class tricar extends poly {

    public tricar(MyVisual mv)
    { 
        super(mv);
    }

    @Override
    public void render() { 
        mv.background(0);
            mv.lights();

            // Map colour based on lerpedAvg
            float hue2 = PApplet.map(mv.lerpedAvg, 0, 1, 0, 255);
            mv.stroke(hue2, 255, 255);
            mv.fill(hue2, 255, 255);

            mv.translate(0, 0, 200); // Move forward in Z before drawing other shapes

            // Parameters for small triangles
            float triangleSize1 = 80;  // Adjust this for desired size
            float spacing = 120;      // Adjust for desired spacing

            // Nested loop to place multiple triangles
            for (int x = 0; x <= mv.width; x += spacing) {
                for (int y = 0; y <= mv.height; y += spacing) {

                int row = y / 60; 

                // Condition to skip the middle three rows 
                if (row >= 8 && row <= mv.height/60 - 7) {
                    continue; // Skip to the next iteration of the loop 
                }

                mv.pushMatrix();
                mv.translate(x, y, 0); // Centre each triangle


                // Audio-reactive rotation
                float angle1 = PApplet.radians(mv.frameCount + x + y) + mv.lerpedAvg * PApplet.TWO_PI; 

                float xx1 = PApplet.cos(angle1) * triangleSize1;
                float yy1 = PApplet.sin(angle1) * triangleSize1;
                float xx2 = PApplet.cos(angle1 + PApplet.TWO_PI / 3) * triangleSize1;
                float yy2 = PApplet.sin(angle1 + PApplet.TWO_PI / 3) * triangleSize1;
                float xx3 = PApplet.cos(angle1 + 2 * PApplet.TWO_PI / 3) * triangleSize1;
                float yy3 = PApplet.sin(angle1 + 2 * PApplet.TWO_PI / 3) * triangleSize1;

                mv.triangle(xx1, yy1, xx2, yy2, xx3, yy3);
                mv.popMatrix();
                }
            }

            // Bounce Logic
            float bounceVelocity = 0.0f;
            float bounceFactor = 5.0f; 
            float gravity = 0.2f;     

            bounceVelocity += mv.smoothedAmplitude * bounceFactor;
            bounceVelocity -= gravity;

            float bounceY = mv.height/2 + 100 + bounceVelocity * 50; 

            mv.translate(mv.width/2, bounceY, -200); // Apply the bounce 
            mv.rotateZ(PApplet.PI);
            mv.rotateY(mv.ry);
            mv.scale(225.5f);
            mv.shape(mv.car); 

            mv.ry += 0.02;
    }

    
}
