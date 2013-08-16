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
    avoidEdges();

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
  
  void avoidEdges() {
    int ex = (p.width  / 2) - 50;
    int ey = (p.height / 2) - 50;
    
    PVector desired = null;
    
    if (location.x > ex) {
      desired = new PVector(-1, velocity.y);
    } else if (location.x < -ex) {
      desired = new PVector(1, velocity.y);
    }
    
    if (location.y > ey) {
      desired = new PVector(velocity.x, -1);
    } else if (location.y < -ey)  {
      desired = new PVector(velocity.x, 1);
    }

    if (desired == null) return;
    
    desired.normalize();
    desired.mult(maximumSpeed());
    
    PVector steer = PVector.sub(desired, velocity);
    steer.limit(0.15f);
    
    applyForce(steer);
  }
}

