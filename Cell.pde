class Cell {
  PVector location;
  PVector velocity;

  float maximumVelocity     = random(5, 10);
  float maximumAcceleration = random(0.2, 0.4); // ...actually the ± high-end ranges

  Cell() {
    location = PVector.random2D();
    location.mult(random(500));
    velocity = PVector.random2D();
  }

  void move() {
    // get older
    senescence();

    // decide where to go
    PVector acceleration;

    // seek mouse position if mouse is close enough
    int mx = mouseX - (width / 2);
    int my = mouseY - (height / 2);
    PVector target = new PVector(mx, my);
    float proximity = location.dist(target);

    if (proximity <= 150) {
      acceleration = new PVector(0, 0);
      PVector desired = PVector.sub(target, location);
      desired.normalize();
      float speed = maximumAcceleration * 10;
      if (proximity < 100) speed = map(proximity, 0, 100, 0, speed);
      desired.mult(speed);

      PVector steer = PVector.sub(desired, velocity);
      //steer.limit(speed  * 0.01);
      steer.limit(0.005);

      acceleration.add(steer);
    } else {
      acceleration = PVector.random2D();
      acceleration.mult(maximumAcceleration);
      // acceleration.mult(random(0.2, 0.6));
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

