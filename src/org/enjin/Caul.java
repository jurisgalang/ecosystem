package org.enjin;

import processing.core.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Caul extends Cell {
  public Caul(World world, PVector location) {
    super(world, location);
    maxSpeed      = 0.5f;
    steeringForce = 0.025f;
  }

  public Caul(World world) {
    this(world, PVector.random2D());
  }

  void move() {
    float r = p.map(size, 0, 1f, 0, 100f);
    scan(r, Crab.class);

    if (!nearby.isEmpty()) {
      int i       = (int)p.random(nearby.size()) * 1;
      Cell target = ((List<Cell>)nearby).get(i);
      maxSpeed    = 5f;
      chase(target);
    } else {
      maxSpeed = 0.5f;
      wander();
    }
  }

  void render() {
    p.translate(location.x, location.y);

    float theta = velocity.heading() + p.HALF_PI;
    p.rotate(theta);
    p.noStroke();

    float head = p.map(size, 0, 1f, 0, 5f);
    float body = p.map(size, 0, 1f, 0, 20f);
    float tail = p.map(size, 0, 1f, 0, 40f);

    // head
    p.fill(0x77, 0x00, 0x00, 100);
    p.ellipse(0, 0, head, head);

    // caul
    p.fill(0xbb, 0xbb, 0xbb, 75);
    p.arc(0, 5, 5 * head, 3 * head, p.PI - p.QUARTER_PI, p.TWO_PI + p.QUARTER_PI, p.PIE);

    // body
    p.ellipse(0, 10, 0.35f * body, body);

    // tail
    p.stroke(0xdd, 0xa0, 0xa0, 100);
    p.line(0, 10, 0, tail);

    // DEBUG
    // if (nearby.isEmpty()) return;
    // p.noStroke();
    // p.fill(0x00, 0xe0, 0x00, 15);
    // p.ellipse(0, 0, 50, 50);
  }
}
