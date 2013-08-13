import java.util.Iterator;
import java.util.List;

class World {
  int POPULATION = 500;
  
  List<Cell> cells = new ArrayList<Cell>();
  
  void populate() {
    if (cells.size() < POPULATION) {
      cells.add(new Cell());
    } 
  }
  
  void move() {
    populate();
  }
  
  void draw() {
    pushMatrix();    
    background(255);
    translate(width / 2, height / 2);
    
    Iterator<Cell> i = cells.iterator();
    while (i.hasNext()) {
      Cell cell = i.next();
      cell.move();
      cell.draw();
    }

    noStroke();
    fill(#000e00, 5);
    ellipse(mouseX - width / 2, mouseY - height / 2, 200, 200);
    
    popMatrix();
  }
}
