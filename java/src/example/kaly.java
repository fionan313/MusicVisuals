package example;
import processing.core.PApplet;
public class kaly extends poly
{
    public kaly(MyVisual mv)
    { 
        super(mv);
    }

    @Override
    public void render(){
        mv.colorMode(mv.HSB);
        mv.background(200);
        mv.stroke(255);
        mv.translate(mv.width/2, mv.height/2);
        for(int i = 0; i < 2000;i+=60)
        {
            float hue = PApplet.map(i, 0, mv.ab.size() + (mv.ab.size() / 4), 50, 250);
            float radius = 100+i;
            //rotate entire shape
            mv.rotate(PApplet.radians(mv.frameCount));
            //map of bluish pinkish colours        
            mv.beginShape();
            for(float a = 0; a < PApplet.TWO_PI; a+=PApplet.TWO_PI/8)
            {
                float x = radius * PApplet.cos(a);
                float y =  radius * PApplet.sin(a);
                //changes stroke weight on beat
                mv.noFill();
                mv.stroke(hue, 255, 255);
                mv.vertex(x * mv.smooth, y * mv.smooth);
            }
            mv.endShape();
        }
    }
}