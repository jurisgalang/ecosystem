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
    update();
    render();
    p.popMatrix();
  }

  abstract void update();
  abstract void render();

  float age    = 0;
  float size   = 0;
  float energy = 1.0f;

  final void senescence() {
    age += 0.0000001f;
    size = p.constrain((size += age), 0, 1f);
  }
}
