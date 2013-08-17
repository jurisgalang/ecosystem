package org.enjin;

import processing.core.*;

import java.util.List;

public class Cell extends Thing {
  World world;

  PVector location     = null;
  PVector velocity     = null;
  PVector acceleration = null;

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

  float maxSpeed() {
    return 0.001f;
  }
  
  void update() {
    move();
    stayWithinBounds();
    velocity.add(acceleration);
    velocity.limit(maxSpeed());
    location.add(velocity);
  }

  private boolean nearbyPrey = false;

  void move() {
    List<Cell> nearby = (List<Cell>)world.nearby(this, 25, Crab.class);
    nearbyPrey = !nearby.isEmpty();
    randomWalk();
  }

  private float size = 0f;

  void render() {
    p.translate(location.x, location.y);
    p.noStroke();
    p.fill(0x88, 0x99, 0x88, 35);

    size = p.constrain((size += age), 0f, 1f);
    float body = p.map(size, 0f, 1f, 0f, 5f);

    p.ellipse(0, 0, body, body);

    if (nearbyPrey) {
      p.noStroke();
      p.fill(0xe0, 0x00, 0x00, 15);
      p.ellipse(0, 0, 25, 25);
    }
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
    int edgeX = (world.width / 2) - 50;
    int edgeY = (world.height / 2) - 50;

    PVector desired = null;

    if (Math.abs(location.x) > edgeX) {
      desired = new PVector(-location.x, velocity.y);
    }

    if (Math.abs(location.y) > edgeY) {
      desired = new PVector(location.x, -location.y);
    }

    if (desired == null) return;

    desired.normalize();
    desired.mult(maxSpeed() * 0.5f);

    PVector steer = PVector.sub(desired, velocity);
    steer.limit(0.15f);

    applyForce(steer);
  }

  void seek(PVector target) {
    PVector desired = PVector.sub(target, location);
    float proximity = desired.mag();

    float speed = (proximity < 25) ? p.map(proximity, 0, 25, 0, maxSpeed()) : maxSpeed();
    desired.normalize();
    desired.mult(speed);

    PVector steer = PVector.sub(desired, velocity);
    steer.limit(0.15f);

    applyForce(steer);
  }
}

