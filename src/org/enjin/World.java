package org.enjin;

import processing.core.*;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class World extends Thing {
  int POPULATION = 1;

  List<Cell> cells = new ArrayList<Cell>();

  public World(PApplet p) {
    super(p);
  }

  void move() {
    if (cells.size() < POPULATION) {
      //Cell cell = p.random(1) < 0.25 ? new Crab(p) : new Cell(p);
      Cell cell = new Crab(p);
      cells.add(cell);
    }
  }

  void render() {
    p.translate(p.width / 2, p.height / 2);
    p.background(255);

    Iterator<Cell> i = cells.iterator();
    while (i.hasNext()) {
      Cell cell = i.next();
      cell.play();
    }
  }
}
