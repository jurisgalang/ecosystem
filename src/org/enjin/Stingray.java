package org.enjin;

import processing.core.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Stingray extends Cell {
  public Stingray(World world) {
    super(world);
  }

  public Stingray(World world, PVector location) {
    super(world, location);
  }

  float maxSpeed() {
    return 1f;
  }

  List<Cell> nearby = Collections.emptyList();

  void move() {
    stayWithinBounds();    
    wander();
    
    nearby = (List<Cell>)world.nearby(this, 25, Cell.class);
    if (!nearby.isEmpty()) {
      int i = (int)p.random(nearby.size()) * 0;
      Cell target = nearby.get(i);
      seek(target.location);
    }
  }

  float size = 0f;

  void render() {
    p.translate(location.x, location.y);

    float theta = velocity.heading() + p.HALF_PI;
    p.rotate(theta);
    p.noStroke();

    size = p.constrain((size += age), 0f, 1f);
    float head = p.map(size, 0f, 1f, 0f, 5f);
    float body = p.map(size, 0f, 1f, 0f, 15f);
    float tail = p.map(size, 0f, 1f, 0f, 25f);

    // head
    p.fill(0x77, 0x00, 0x00, 75);
    p.ellipse(0, 0, head, head);

    // body
    head *= 0.5;
    p.fill(0xbb, 0xbb, 0xbb, 75);
    p.ellipse(0, head, body, body * 0.666f);

    // tail
    p.stroke(0xa0, 0xa0, 0xa0, 75);
    p.line(-head, 0, 0, tail);
    p.line(head, 0, 0, tail);

    if (nearby.isEmpty()) return;    
    p.noStroke();
    p.fill(0x00, 0xe0, 0x00, 15);
    p.ellipse(0, 0, 25, 25);
  }
}
