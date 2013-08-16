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

    if (debug) {
      noFill();
      stroke(0x1a, 0x1a, 0x1a, 75);
      rect(50, 50, width - 100, height - 100);
    }
  }

  boolean debug = false;

  public void keyPressed() {
    debug = !debug;
  }

  public static void main(String args[]) {
    PApplet.main(new String[] { "App" });
  }
}

