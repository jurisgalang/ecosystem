package org.enjin;

import processing.core.*;

abstract class Thing {
  PApplet p;

  public Thing(PApplet p) {
    this.p = p;
  }

  public final void play() {
    p.pushMatrix();
    move();
    render();
    p.popMatrix();
  }

  abstract void move();
  abstract void render();
}
