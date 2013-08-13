World world = new World();

void setup() {
  size(800, 400, P3D);
  smooth();
}

void draw() {
  world.move();
  world.draw();
}
