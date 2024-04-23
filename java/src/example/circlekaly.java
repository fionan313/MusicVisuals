package example;
import processing.core.PApplet;
public class circlekaly extends poly
{
    public circlekaly(MyVisual mv)
    { 
        super(mv);
    }

    @Override
    public void render(){
        mv.colorMode(mv.HSB);
        mv.background(200);
        mv.translate(mv.width/2, mv.height/2);
        for(int i = 0; i < 2000;i+=65)
        {
            float hue = PApplet.map(i, 0, mv.ab.size(), 0, 256);
            float radius = 100+i;
            //rotate entire shape
            mv.rotate(PApplet.radians(mv.frameCount));
                    
            mv.beginShape();
            for(float a = 0; a < PApplet.TWO_PI; a+=PApplet.TWO_PI/8)
            {
                float x = radius * PApplet.cos(a);
                float y =  radius * PApplet.sin(a);
                //changes stroke weight on beat
                mv.noFill();
                mv.stroke(hue, 150, 200);
	            mv.vertex(x * mv.smooth, y * mv.smooth);
                for (int j = 0; j < mv.height; j += 25) {
                    float hue1 = PApplet.map(i, 0, mv.ab.size() , 0, 256);
                    mv.fill(hue1, 255, 255);
                    mv.stroke(15);
                    mv.circle((i*2)/3, (j*2)/3, ((mv.width/2) * mv.smooth) / 2 );
                }
            }
            mv.endShape();
        }
    }
}