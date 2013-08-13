class Cell {  
  PVector location;
  PVector velocity;
  
  float maximumVelocity     = random(5, 10);
  float maximumAcceleration = 0.5; // ...actually the ± high-end ranges
  
  Cell() {
    location = PVector.random2D();
    location.mult(random(500));
    velocity = PVector.random2D();
  }
  
  void move() {
    // get older
    senescence();
    
    // decide where to go
    float speed = random(maximumAcceleration);
    PVector acceleration = PVector.random2D();
    acceleration.normalize();
    acceleration.mult(speed);
    
    // seek if mouse is close enough
    int mx = mouseX - (width / 2);
    int my = mouseY - (height / 2);
    PVector target = new PVector(mx, my);
    if (location.dist(target) < 200) {
      PVector desired = PVector.sub(target, location);
      desired.normalize();
      // desired.mult(speed);
      PVector steer = PVector.sub(desired, velocity);
      acceleration.add(steer);
    }
    
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
    if (tailLength  < 20) tailLength  *= (1 + age / 4);
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
    line(-2, -5, 0, tailLength); 
    line(2, -5, 0, tailLength); 

    popMatrix();
  }
}

