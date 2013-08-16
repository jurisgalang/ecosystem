package org.enjin;

import processing.core.*;

public class Crab extends Cell {
  public Crab(PApplet p) {
    super(p);
  }

  float maximumSpeed() {
    return 1.25f;
  }

  void render() {
    p.translate(location.x, location.y);

    float theta = velocity.heading() + p.HALF_PI;
    p.rotate(theta);
    p.noStroke();

    // nucleus
    p.fill(0x77, 0x00, 0x00, 75);
    p.ellipse(0, -6, 5, 5);

    // body
    p.fill(0xa0, 0xa0, 0xa0, 75);
    p.ellipse(0, -5, 15, 10);

    // tail
    p.stroke(0xa0, 0xa0, 0xa0, 75);
    p.line(-2, -5, 0, 25);
    p.line(2, -5, 0, 25);
  }
}

