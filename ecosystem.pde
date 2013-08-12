import java.util.Iterator;
import java.util.List;

class Cell {  
  PVector location;
  
  Cell() {
    location = PVector.random2D();
  }
  
  void draw() {
    pushMatrix();
    translate(location.x, location.y);    
    
    noStroke();
    
    fill(#770101, 75);
    ellipse(location.x, location.y, 5, 5);        // nucleus
    
    fill(#a0a0a0, 75);
    ellipse(location.x, location.y, 10, 10);      // body
    
    stroke(#a1a1a1, 75);
    line(location.x, location.y, location.x, 25); // tail 

    popMatrix();
  }
}


class World {
  int POPULATION = 1;
  List<Cell> cells;
  
  World() {
    seedPopulation();
  }
  
  void seedPopulation() {
    cells = new ArrayList<Cell>();
    while (cells.size() < POPULATION) {
      cells.add(new Cell());
    }
  }
  
  void draw() {
    Iterator<Cell> i = cells.iterator();
    while (i.hasNext()) {
      i.next().draw();
    }
  }
}


World world;

void setup() {
  size(800, 400);
  world = new World();
  smooth();
}

void draw() {
  pushMatrix();
  translate(width / 2, height / 2);

  background(255);
  world.draw();
    
  popMatrix();
}
