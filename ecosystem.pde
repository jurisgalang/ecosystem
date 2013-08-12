import java.util.Iterator;
import java.util.List;

class Cell {  
  PVector location;
  PVector velocity;
  
  float maximumVelocity     = 5;
  float maximumAcceleration = 0.5; // ...actually the ± high-end ranges
  
  Cell() {
    location = PVector.random2D();
    velocity = new PVector(0, 0);
  }

  void move() {
    PVector acceleration = PVector.random2D(); 
    acceleration.mult(random(maximumAcceleration));
    
    velocity.add(acceleration);
    velocity.limit(maximumVelocity);
    location.add(velocity); 
  }
  
  void draw() {
    pushMatrix();
    translate(location.x, location.y);

    float theta = velocity.heading() + PI / 2;
    rotate(theta);
    
    noStroke();
    
    // nucleus
    fill(#770000, 150);
    ellipse(0, 0, 5, 5);

    // body
    fill(#a0a0a0, 75);
    ellipse(0, 0, 10, 10);
    
    // tail  
    stroke(#a1a1a1, 75);
    line(0, 0, 0, 25); 

    popMatrix();
  }
}


class World {
  int POPULATION = 100;
  
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
      Cell cell = i.next();
      cell.move();
      cell.draw();
    }
  }
}


World world;

void setup() {
  size(800, 400);
  smooth();
  world = new World();
}

void draw() {
  pushMatrix();
  translate(width / 2, height / 2);

  background(255);
  world.draw();
    
  popMatrix();
}
