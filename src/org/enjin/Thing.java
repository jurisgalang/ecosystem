package org.enjin;

import processing.core.*;

abstract class Thing {
  PApplet p;

  public Thing(PApplet p) {
    this.p = p;
  }

  public final void play() {
    p.pushMatrix();
    senescence();
    move();
    render();
    p.popMatrix();
  }

  abstract void move();
  abstract void render();

  float age = 0;

  final void senescence() {
    age += 0.001;
  }

}
