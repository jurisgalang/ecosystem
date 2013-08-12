import java.util.Iterator;
import java.util.List;

class World {
  int POPULATION = 100;
  
  List<Cell> cells = new ArrayList<Cell>();
  
  World() {
    seedPopulation();
  }
  
  void seedPopulation() {
    while (cells.size() < POPULATION) {
      cells.add(new Cell());
    }
  }
  
  void draw() {
    Iterator<Cell> i = cells.iterator();
    while (i.hasNext()) {
      Cell cell = i.next();
      
      cell.move();
      if (cell.isDead()) {
        i.remove();
        continue;
      }
      
      cell.draw();
    }
    
    seedPopulation();
  }
}

class Cell {  
  PVector location;
  PVector velocity;
  
  float age    = 0.01;
  float energy = random(0.05, 1);
  
  float maximumVelocity     = 10;
  float maximumAcceleration = 0.5; // ...actually the ± high-end ranges
  
  boolean resting = true;
  
  Cell() {
    location = PVector.random2D();
    velocity = PVector.random2D();
  }
  
  boolean isDead() {
    return energy <= 0;
  }

  boolean isWeak() {
    return (energy > 0) && (energy <= 0.1);
  }
  
  boolean isStrong() {
    return energy > 0.5;
  }
  
  boolean isResting() {
    return resting;
  }

  float vitality() {
    return energy * age;
  }
  
  void move() {
    // don't do anything if already dead
    if (isDead()) return;

    // get older
    age += 0.01;

    if (isResting()) {
      energy += 0.015;
      if (isStrong()) resting = !resting;
      return;
    }
    
    // rest if weak 
    if (isWeak()) {
      resting = true;
      return;
    }

    // expel energy
    float expenditure = random(0.005, 0.007); 
    energy -= expenditure;
    
    // decide where to go
    PVector acceleration = PVector.random2D();
    float a = random(maximumAcceleration  * vitality());
    acceleration.mult(a);
    velocity.add(acceleration);
    
    float v = maximumVelocity * vitality();
    velocity.limit(v);
    location.add(velocity);
  }
  
  void draw() {
    pushMatrix();
    translate(location.x, location.y);

    float theta = velocity.heading() + HALF_PI;
    rotate(theta);
    
    noStroke();
    
    // nucleus
    float alpha = 75 * vitality();
    
    fill(#770000, alpha);
    ellipse(0, -7.5, 5, 5);

    // body
    fill(#a0a0a0, 75);
    ellipse(0, -5, 15, 18);
    
    // tail  
    stroke(#a1a1a1, 75);
    line(0, -5, 0, 25); 

    popMatrix();
  }
}

World world;

void setup() {
  size(800, 400, P3D);
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
