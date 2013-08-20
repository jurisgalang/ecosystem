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

  boolean debug = false;

  public void mouseClicked() {
    float x = map(mouseX, 0, width, -width / 2, width / 2);
    float y = map(mouseY, 0, height, -height / 2, height / 2);

    world.populate(new Crab(world, new PVector(x, y)));
    world.populate(new Stingray(world));
    world.populate(new Stingray(world));

    // for(int i=0; i<(int)random(5, 10); i++) {
    //   world.populate(new Stingray(world));
    // }

    // for(int i=0; i<(int)random(10, 50); i++) {
    //   world.populate(new Cell(world));
    // }
  }

  public static void main(String args[]) {
    PApplet.main(new String[] { "App" });
  }
}

