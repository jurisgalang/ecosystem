package org.enjin;

import processing.core.*;

import java.util.Collection;
import java.util.Collections;
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
    //location.mult(p.random(world.height / 2));
    location.mult(world.height / 2);
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
    velocity.add(acceleration);
    velocity.limit(maxSpeed());
    location.add(velocity);    
    acceleration.mult(0);
  }

  Collection<Cell> nearby = Collections.emptyList();;

  void move() {
    nearby = world.nearby(this, 25, Stingray.class);
    stayWithinBounds();
    randomWalk();
  }

  float size = 0f;

  void render() {
    p.translate(location.x, location.y);
    p.noStroke();
    p.fill(0x88, 0x99, 0x88, 35);

    size = p.constrain((size += age), 0f, 1f);
    float body = p.map(size, 0f, 1f, 0f, 5f);

    p.ellipse(0, 0, body, body);

    if (nearby.isEmpty()) return;
    
    p.noStroke();
    p.fill(0xe0, 0x00, 0x00, 15);
    p.ellipse(0, 0, 25, 25);
  }

  void applyForce(PVector force) {
    acceleration.add(force);
  }

  void randomWalk() {
    PVector force = PVector.random2D();
    force.mult(p.random(1));
    force.normalize();
    applyForce(force);
  }

  public void stayWithinBounds() {
    PVector desired = null;
  
    double edgeX = (world.width - 100) * 0.5;
    double edgeY = (world.height - 100) * 0.5;
    
    if (Math.abs(location.x) > edgeX) {
      float speed = (location.x < 0) ?  maxSpeed() : -maxSpeed();
      desired = new PVector(speed, velocity.y);
    }
    
    if (Math.abs(location.y) > edgeY) {
      float speed = (location.y < 0) ?  maxSpeed() : -maxSpeed();
      desired = new PVector(velocity.x, speed);
    }

    if (desired != null) {
      desired.normalize();
      desired.mult(maxSpeed());
      desired.sub(velocity);
      desired.limit(0.025f);
      applyForce(desired);
    }  
  }

  float wanderTheta = 0;
  
  void wander() {
    // get a point some distance from the current location
    PVector location = this.location.get();
    PVector velocity = this.velocity.get();
    velocity.normalize();
    velocity.mult(75);
    location.add(velocity);
    
    // use that location as the center of a circle of some radius
    wanderTheta   += p.random(-0.3f, 0.3f);
    float r        = 25f;
    float theta    = velocity.heading() + wanderTheta;
    float x        = r * p.cos(theta);
    float y        = r * p.sin(theta);

    // and pick a random point along the circumference of that circle
    PVector target = PVector.add(location, new PVector(x, y));

    // using it as the next waypoint
    seek(target);

    // DEBUG    
    // p.smooth();
    // p.stroke(0xdd, 0xdd, 0xdd, 15);
    // p.line(this.location.x, this.location.y, location.x, location.y);
    // p.noFill();
    // p.ellipse(location.x, location.y, r * 2, r * 2);
    // p.line(location.x, location.y, target.x, target.y);
    // p.fill(0xdd, 0xdd, 0xdd, 15);
    // p.ellipse(target.x, target.y, 10, 10);
    // p.stroke(0x00, 0xee, 0x00, 15);
    // p.line(this.location.x, this.location.y, target.x, target.y);
  }

  void scan(float distance, Class... kinds) {
    // get a point some distance from the current location
    PVector location = this.location.get();
    PVector velocity = this.velocity.get();
    velocity.normalize();
    velocity.mult(distance);
    location.add(velocity);

    // use that location as the center of a circle of some radius
    this.nearby = (List<Cell>)world.nearby(this, distance, kinds);

    // DEBUG
    p.noFill();
    p.stroke(0x00, 0xe0, 0xe0, 15);
    p.ellipse(location.x, location.y, distance * 2, distance * 2);

    for(Cell cell : nearby) {
      p.ellipse(cell.location.x, cell.location.y, 25, 25);
    }
  }

  void chase(Cell prey) {
    PVector location = PVector.sub(prey.location, this.location); // range to close
    PVector velocity = PVector.sub(prey.velocity, this.velocity); // closing velocity
    velocity.normalize();

    float magnitude = location.mag() / velocity.mag();           // time to close
    velocity.mult(magnitude);              

    PVector target = PVector.add(prey.location, velocity);       // intercept location
    seek(target);

    // DEBUG    
    p.smooth();
    p.stroke(0xdd, 0xdd, 0xdd, 75);
    p.line(prey.location.x, prey.location.y, target.x, target.y);
    p.stroke(0xdd, 0x00, 0x00, 75);
    p.line(this.location.x, this.location.y, target.x, target.y);
    p.noStroke();
    p.fill(0xff, 0x00, 0x00, 15);
    p.ellipse(target.x, target.y, 30, 30);
  }

  void seek(PVector target) {
    PVector desired = PVector.sub(target, location);
    float proximity = desired.mag();
  
    float speed = (proximity < 25) ? p.map(proximity, 0, 25, 0, maxSpeed()) : maxSpeed();
    desired.normalize();
    desired.mult(speed);
    desired.sub(velocity);
    desired.limit(0.025f);
    applyForce(desired);
  }
}
