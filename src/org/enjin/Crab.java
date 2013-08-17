package org.enjin;

import processing.core.*;

import java.util.Collection;
import java.util.List;

public class Crab extends Cell {
  public Crab(World world) {
    super(world);
  }

  public Crab(World world, PVector location) {
    super(world, location);
  }

  float maximumSpeed() {
    return 1.25f;
  }

  private boolean nearbyFood = false;

  void move() {
    List<Cell> nearby = (List<Cell>)world.nearby(this, 25, Cell.class);

    if (nearbyFood = !nearby.isEmpty()) {
      int i     = (int)p.random(nearby.size());
      PVector t = nearby.get(i).location;
      seek(t);
    }

    else {
      randomWalk();
    }
  }

  private float size = 0f;

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
    p.ellipse(0, -6, head, head);

    // body
    p.fill(0xbb, 0xbb, 0xbb, 75);
    p.ellipse(0, -5, body, body * 0.666f);

    // tail
    p.stroke(0xa0, 0xa0, 0xa0, 75);
    p.line(-2, -5, 0, tail);
    p.line(2, -5, 0, tail);

    if (nearbyFood) {
      p.noStroke();
      p.fill(0x00, 0xe0, 0x00, 15);
      p.ellipse(0, -6, 25, 25);
    }
  }
}

