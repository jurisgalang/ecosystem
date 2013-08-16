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
    return 0.15f;
  }

  void move() {
    randomWalk();
    stayWithinBounds();

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

  void randomWalk() {
    PVector force = PVector.random2D();
    force.mult(p.random(1));
    applyForce(force);
  }

  void stayWithinBounds() {
    int ex = (p.width  / 2);
    int ey = (p.height / 2);

    PVector desired = null;

    if (Math.abs(location.x) > ex) {
      desired = new PVector(-location.x, velocity.y);
    }

    if (Math.abs(location.y) > ey) {
      desired = new PVector(location.x, -location.y);
    }

    if (desired == null) return;

    desired.normalize();
    desired.mult(maximumSpeed());

    PVector steer = PVector.sub(desired, velocity);
    steer.limit(0.15f);

    applyForce(steer);
  }
}

