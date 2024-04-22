package example;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PShape;
import ie.tudublin.*;
import processing.core.*;

public class circles extends poly
{
    public circles(MyVisual mv)
    { 
        super(mv);
    }

    @Override
    public void render(){
        mv.colorMode(PApplet.HSB);
        mv.background(150);
        mv.stroke(255);
        for (int i = 0; i < mv.width + 25; i += 25) {
            for (int j = 0; j < mv.height + 25; j += 25)
            {
                float hue = PApplet.map(i, 0, mv.ab.size(), 0, 256); // Call map on mv (assuming it's a PApplet subclass)
                mv.fill(hue, 255, 255);
                mv.stroke(15);
                mv.circle(i, j, (mv.width /2) * mv.smooth);
            }
        }
    }
}