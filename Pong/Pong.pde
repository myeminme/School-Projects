// Project 3 Pong, Mykha Floresca, 121250643, 0205, 4/11/2025 
// I pledge on my honor that I have not given or received any unauthorized assistance on this assignment/examination 

/*--------------------------------------VARIABLES-----------------------------------------------*/ 
boolean tPressed = false; // start 
boolean wPressed = false; // p1 up 
boolean sPressed = false; // p1 down 
boolean iPressed = false; // p2 up 
boolean kPressed = false; // p2 down 
boolean nPressed = false; // reset 
boolean cPressed = false; // continue after life lost 

float bx, by;  // ball x and y 
float bvx, bvy;// ball velocities 
float radius = 20; 

int p1lives = 1; 
int p2lives = 1; 
int lastMissed = 0; // 1 = Player One missed, 2 = Player Two missed 

float paddleh = 100; 
float paddlew = 20; 
float p1y;  // player one paddle y 
float p2y;  // player two paddle y 

boolean gameStart = false; 
boolean gameOver = false; 
boolean first_roundStart = true; 

final int TRAIL = 20; 
float[] xTrail = new float[TRAIL]; 
float[] yTrail = new float[TRAIL]; 

/*------------------------------------SETUP AND DRAW--------------------------------------------*/ 

void setup() { 
  size(800, 600); 
  resetGame(); 
} 

void draw() { 
  background(0); 
  noStroke(); 

  //Game over screen 
  if (gameOver) { 
    gameOverScreen(); 
    if (nPressed) { 
      resetGame(); 
    } 
    return; 
  } 

  //Visuals 
  ui(); 
  trailDraw(); 

  // Paddle behaviors 
  movePaddle(); 
  rect(0, p1y, paddlew, paddleh); 
  rect(width - paddlew, p2y, paddlew, paddleh); 

  //resets 
  if (nPressed) { 
    resetGame(); 
    nPressed = false; 
  } 

  //Starts round if c is pressed and it's ended 
  if (!gameStart && cPressed) { 
    roundStart(); 
    gameStart = true; 
    cPressed = false; 
  } 

  //Ball logic during game 
  if (gameStart) { 
    bx += bvx; 
    by += bvy; 
    checkWall(); 
    checkPaddleCollision(); 
    trailUpdate(); 
    checkGameOver();  
  } 
} 

/*---------------------------------------FUNCTIONS----------------------------------------------*/ 

//Creates game over screen. 
void gameOverScreen() { 
  fill(255); 
  textSize(40); 
  textAlign(CENTER, CENTER); 

  //When someone's life reaches 0, display text 
  if (p1lives < 0) { 
    text("GAME OVER\nPlayer Two Wins!", width/2, height/2 - 20); 
  } else if (p2lives < 0) { 
    text("GAME OVER\nPlayer One Wins!", width/2, height/2 - 20); 
  } 
  textSize(20); 
  text("Press 'n' to play again", width/2, height/2 + 50); 
} 

void checkPaddleCollision() { 
  // Player One (Left Paddle) 
  if (bx - radius/2 <= paddlew &&  
    bx - radius/2 >= 0 &&         
    by + radius/2 >= p1y &&       
    by - radius/2 <= p1y + paddleh) {  
    bx = paddlew + radius/2 + 1; 
    bvx *= -1;  // Reverse x-velocity 
  } 
  // Player Two (Right Paddle) 
  if (bx + radius/2 >= width - paddlew &&   
    bx + radius/2 <= width &&             
    by + radius/2 >= p2y &&               
    by - radius/2 <= p2y + paddleh) {     
     bx = width - paddlew - radius/2 - 1; 
    bvx *= -1;  // Reverse x-velocity  
  } 
} 

void checkWall() { 
  // Left wall 
  if (bx + radius < -50) { 
    p1lives -= 1; 
    lastMissed = 1; 
    gameStart = false; 
  } 
  // Right wall 
  if (bx - radius > width+50) { 
    p2lives -= 1; 
    lastMissed =2; 
    gameStart = false; 
  } 
  // Top wall 
  if (by - radius < 0+30) { 
    bvy *= -1; 
  } 
  // Bottom wall 
  if (by + radius > height-30) { 
    bvy *= -1; 
  } 
} 

void trailDraw() { 
  for (int i = TRAIL - 1; i >= 0; i--) { 
    float r = map(i, 0, TRAIL - 1, radius, 5); //Makes balls smaller 
    float alpha = map(i, 0, TRAIL - 1, 255, 10); //Changes opacity 
    fill(255, alpha); 
    ellipse(xTrail[i], yTrail[i], r, r); //Draws the balls 
  } 
} 

//Cycle through the trail of the ball using arrays 
void trailUpdate() { //Updates location 
  for (int i = TRAIL - 1; i > 0; i--) { 
    xTrail[i] = xTrail[i - 1]; 
    yTrail[i] = yTrail[i - 1]; 
  } 
  xTrail[0] = bx; 
  yTrail[0] = by; 
} 

void movePaddle() { 
  if (wPressed && p1y > 42) 
    p1y -= 5; 
  if (sPressed && p1y < height - paddleh - 40) 
    p1y += 5; 
  if (iPressed && p2y > 42) 
    p2y -= 5; 
  if (kPressed && p2y < height - paddleh - 40) 
    p2y += 5; 
} 

void roundStart() { 
  if (first_roundStart) { 
    // Very start of the game 
    bx = width / 2; 
    by = height / 2; 
    // Randomly choose either -4 or 4 for x-velocity 
    bvx = random(1) < 0.5 ? -4 : 4; 
    // Randomly choose y-velocity within [-10, -3) or [3, 10) 
    bvy = (random(1) < 0.5) ? random(-10, -3) : random(3, 10); 
    first_roundStart = false; 
  } else { 
    if (lastMissed == 1) { 
      // Player 1 missed, start at the center of Player 2's paddle 
      bx = width - paddlew - radius - 1; 
      by = p2y + paddleh / 2; 
      bvx = random(1) < 0.5 ? -4 : 4;  //random x velocity 
    } else if (lastMissed == 2) { 
      // Player 2 missed, start at the center of Player 1's paddle 
      bx = paddlew + radius + 1; 
      by = p1y + paddleh / 2; 
      bvx = random(1) < 0.5 ? -4 : 4;   
    } else { 
      bx = width / 2; 
      by = height / 2; 
      bvx = random(1) < 0.5 ? -4 : 4;  // Random x velocity 
    } 

    // Randomly choose y-velocity within [-10, -3) or [3, 10) 
    bvy = (random(1) < 0.5) ? random(-10, -3) : random(3, 10); 
  } 

  // Reset trails 
  for (int i = 0; i < TRAIL; i++) { 
    xTrail[i] = bx; 
    yTrail[i] = by; 
  } 
} 

//Resets all values 
void resetGame() { 
  bx = width / 2; 
  by = height / 2; 
  p1y = height / 2 - paddleh / 2; 
  p2y = height / 2 - paddleh / 2; 
  p1lives = 1; 
  p2lives = 1; 

  gameOver = false;      
  gameStart = false;    
  lastMissed = 0; 

  for (int i = 0; i < TRAIL; i++) { 
    xTrail[i] = bx; 
    yTrail[i] = by; 
  } 
} 

//Draw the UI such as the text and lines. 
void ui() { 
  fill(255); 
  textAlign(LEFT); 
  textSize(20); 
  text("PLAYER ONE LIVES: " + p1lives, 10, 30); 
  text("PLAYER TWO LIVES: " + p2lives, width - 220, height - 15); 
  stroke(255); 
  line(0, 40, width, 40); 
  line(0, height - 40, width, height - 40); 
  noStroke(); 
} 

void checkGameOver() { 
  if (p1lives < 0 || p2lives < 0) { 
    gameOver = true; 
    gameStart = false; 
  } 
} 

/*----------------------------------------KEYS------------------------------------------------*/ 

void keyPressed() { 
  if (key == 't' || key == 'T') { 
    roundStart(); 
    gameStart = true; 
  } else if (key == 'w' || key == 'W') { 
    wPressed = true; 
  } else if (key == 's' || key == 'S') { 
    sPressed = true; 
  } else if (key == 'i' || key == 'I') { 
    iPressed = true; 
  } else if (key == 'k' || key == 'K') { 
    kPressed = true; 
  } else if (key == 'n' || key == 'N') { 
    nPressed = true; 
  } else if (key == 'c' || key == 'C') { 
    cPressed = true; 
  } 
} 

void keyReleased() { 
  if (key == 'w' || key == 'W') { 
    wPressed = false; 
  } else if (key == 's' || key == 'S') { 
    sPressed = false; 
  } else if (key == 'i' || key == 'I') { 
    iPressed = false; 
  } else if (key == 'k' || key == 'K') { 
    kPressed = false; 
  } 
} 
```
