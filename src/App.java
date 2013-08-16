import processing.core.*;
import org.enjin.*;

public class App extends PApplet {
  World world;

  public void setup() {
    size(800, 400, P3D);
    smooth();
    world = new World(this);
  }

  public void draw() {
    pushMatrix();
    world.play();
    popMatrix();
  }

  public static void main(String args[]) {
    PApplet.main(new String[] { "App" });
  }
}

