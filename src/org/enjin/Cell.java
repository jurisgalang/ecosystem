package org.enjin;

import processing.core.*;

public class Cell extends Thing {
  World world;

  PVector location;
  PVector velocity;
  PVector acceleration;

  public Cell(World world) {
    super(world.p);
    this.world   = world;
    acceleration = new PVector(0, 0);
    velocity     = PVector.random2D();
    location     = PVector.random2D();
    location.mult(p.random(300));
  }

  public Cell(World world, PVector location) {
    this(world);
    this.location = location;
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

  private float size = 0f;

  void render() {
    p.translate(location.x, location.y);
    p.noStroke();
    p.fill(0x88, 0x99, 0x88, 35);

    size = p.constrain((size += age), 0f, 1f);
    float body = p.map(size, 0f, 1f, 0f, 5f);

    p.ellipse(0, 0, body, body);
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
    int ex = world.width / 2;
    int ey = world.height / 2;

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

