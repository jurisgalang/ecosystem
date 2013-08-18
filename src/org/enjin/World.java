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
  float width, height;

  int POPULATION = 500;

  List<Cell> cells = new ArrayList<Cell>();

  public World(PApplet p) {
    super(p);
    width  = p.width;
    height = p.height;
  }

  Collection<Cell> nearby(final Cell from, float distance, Class... kinds) {
    List<Cell> list = new ArrayList<Cell>();

    for(Cell cell : cells) {
      if (Arrays.asList(kinds).contains(cell.getClass()) &&
          (cell != from) &&
          (from.location.dist(cell.location) < distance)) {
        list.add(cell);
      }
    }

    Comparator<Cell> comparator = new Comparator<Cell>() {
      public int compare(Cell m, Cell n) {
        float result = from.location.dist(m.location) - from.location.dist(n.location);
        return (int)result;
      }
    };

    Collections.sort(list, comparator);
    
    return list;
  }

  void update() {
    if (cells.size() >= POPULATION) return;
    Cell cell = p.random(1) < 0.05 ? new Crab(this) : new Cell(this);
    cells.add(cell);
  }

  void render() {
    p.background(0x33, 0x33, 0x33);
    p.pushMatrix();
    p.translate(width / 2, height / 2);
    renderPopulation();    
    p.popMatrix();
  }
  
  private void renderPopulation() {
    Iterator<Cell> i = cells.iterator();
    while (i.hasNext()) {
      Cell cell = i.next();
      cell.play();
    }
  }

  public void populate(Cell cell) {
    cells.add(cell);
  }
}
