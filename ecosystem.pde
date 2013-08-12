import java.util.Iterator;
import java.util.List;

class World {
  int POPULATION = 10;
  
  List<Cell> cells = new ArrayList<Cell>();
  
  void seedPopulation() {
    if (cells.size() < POPULATION) {
      cells.add(new Cell());
    } 
  }
  
  void move() {
    seedPopulation();
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

class Cell {  
  PVector location;
  PVector velocity;
  
  float maximumVelocity     = 3;
  float maximumAcceleration = 0.5; // ...actually the ± high-end ranges
  
  Cell() {
    location = PVector.random2D();
    velocity = PVector.random2D();
    velocity.mult(random(1, 3));
  }
  
  void move() {
    // get older
    senescence();
    
    // decide where to go
    PVector acceleration = PVector.random2D();
    acceleration.mult(maximumAcceleration);
    velocity.add(acceleration);
    
    velocity.limit(maximumVelocity);
    location.add(velocity);
  }

  float age         = 0.01;
  float nucleusSize = 0.01;
  float bodyWidth   = 0.01;
  float bodyHeight  = 0.01;
  float tailLength  = 0.01;
  
  void senescence() {
    age += 0.005;
    if (nucleusSize < 5)  nucleusSize += age;
    if (bodyWidth   < 20) bodyWidth   += age;
    if (bodyHeight  < 12) bodyHeight  += age;
    if (tailLength  < 40) tailLength  *= (1 + age / 2);
  }
  
  void draw() {
    pushMatrix();
    translate(location.x, location.y);

    float theta = velocity.heading() + HALF_PI;
    rotate(theta);
    
    noStroke();
    
    // nucleus
    float alpha = 75;
    
    fill(#770000, alpha);
    ellipse(0, -6, nucleusSize, nucleusSize);

    // body
    fill(#a0a0a0, alpha);
    ellipse(0, -5, bodyWidth, bodyHeight);
    
    // tail  
    stroke(#a1a1a1, alpha);
    line(-1, -5, 0, tailLength); 
    line(1, -5, 0, tailLength); 

    popMatrix();
  }
}

World world;

void setup() {
  size(1600, 800, P3D);
  smooth();
  world = new World();
}

void draw() {
  pushMatrix();
  translate(width / 2, height / 2);

  background(255);
  world.move();
  world.draw();
    
  popMatrix();
}
