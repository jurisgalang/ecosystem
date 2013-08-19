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

  public void mouseClicked() {
    float x = map(mouseX, 0, width, -width / 2, width / 2);
    float y = map(mouseY, 0, height, -height / 2, height / 2);
    world.populate(new Crab(world, new PVector(x, y)));

    world.populate(new Stingray(world));
    world.populate(new Stingray(world));
    world.populate(new Stingray(world));
    world.populate(new Stingray(world));
    world.populate(new Stingray(world));

    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
    world.populate(new Cell(world));
  }

  public static void main(String args[]) {
    PApplet.main(new String[] { "App" });
  }
}

