package org.enjin;

import processing.core.*;

public class Cell extends Thing {
  PVector location;
  PVector velocity;
  PVector acceleration;

  public Cell(PApplet p) {
    super(p);
    acceleration = new PVector(0, 0);
    velocity     = PVector.random2D();
    location     = PVector.random2D();
    location.mult(p.random(300));
  }

  float maximumSpeed() {
    return 0.5f;
  }

  void move() {
    randomWalk();

    if (Math.abs(location.x) > 25) {
      PVector desired = new PVector(maximumSpeed(), velocity.y);
      PVector steer = PVector.sub(desired, velocity);
      steer.limit(1.0f);
      applyForce(steer);
    }

    velocity.add(acceleration);
    velocity.limit(maximumSpeed());

    location.add(velocity);
  }

  void render() {
    p.translate(location.x, location.y);
    p.noStroke();
    p.fill(0, 17, 17, 15);
    p.ellipse(0, 0, 10, 10);
  }

  void applyForce(PVector force) {
    acceleration.add(force);
  }

  private void randomWalk() {
    PVector force = PVector.random2D();
    force.mult(p.random(1));
    applyForce(force);
  }
}

