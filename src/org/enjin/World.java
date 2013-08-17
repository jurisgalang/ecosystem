package org.enjin;

import processing.core.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class World extends Thing {
  int width, height;

  int POPULATION = 100;

  List<Cell> cells = new ArrayList<Cell>();

  public World(PApplet p) {
    super(p);
    width  = p.width;
    height = p.height;
  }

  void update() {
    if (cells.size() < POPULATION) {
      Cell cell = p.random(1) < 0.05 ? new Crab(this) : new Cell(this);
      cells.add(cell);
    }
  }

  void render() {
    p.background(255);
    p.translate(width / 2, height / 2);

    Iterator<Cell> i = cells.iterator();
    while (i.hasNext()) {
      Cell cell = i.next();
      cell.play();
    }
  }

  Collection<Cell> nearby(final Cell from, float distance, Class... kinds) {
    Collection<Cell> list = new ArrayList<Cell>();

    for(Cell cell : cells) {
      if (Arrays.asList(kinds).contains(cell.getClass()) &&
          (cell != from) &&
          (from.location.dist(cell.location) < distance)) {
        list.add(cell);
      }
    }

    // Comparator<Cell> comparator = new Comparator<Cell>() {
    //   public int compare(Cell m, Cell n) {
    //     float result = from.location.dist(m.location) - from.location.dist(n.location);
    //     return (int)result;
    //   }
    // };

    // Collections.sort(list, comparator);

    return list;
  }

  public void populate(Cell cell) {
    cells.add(cell);
  }
}
