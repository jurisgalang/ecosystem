package org.enjin;

import processing.core.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Crab extends Cell {
  public Crab(World world) {
    super(world);
  }

  public Crab(World world, PVector location) {
    super(world, location);
  }

  float maxSpeed() {
    return 2f;
  }

  // List<Cell> nearby = Collections.emptyList();

  void move() {
    // http://ns2.rvok.net/tmp/Reilly.AI.for.Game.Developers/ch02_sect1_005.html
    stayWithinBounds(); 
    scan(100, Stingray.class);

    if (!nearby.isEmpty()) {
      int i       = (int)p.random(nearby.size()) * 1;
      Cell target = ((List<Cell>)nearby).get(i);
      chase(target);
    } else {
      wander();
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
    p.fill(0x77, 0x00, 0x00, 100);
    p.ellipse(0, 0, 5, 5);

    p.fill(0xbb, 0xbb, 0xbb, 75);
    p.arc(0, 5, 25, 15, p.PI - p.QUARTER_PI, p.TWO_PI + p.QUARTER_PI, p.PIE);

    // body
    p.ellipse(0, 10, 7, 20);

    // tail
    p.stroke(0xdd, 0xa0, 0xa0, 100);
    p.line(0, 10, 0, 40);

    if (nearby.isEmpty()) return;    
    p.noStroke();
    p.fill(0x00, 0xe0, 0x00, 15);
    p.ellipse(0, 0, 50, 50);
  }
}
