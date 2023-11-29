package firstgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalbriks = 21 ;
    private Timer timer ;
    private int delay = 8 ;
     private int playerx = 320 ;
     private int ballposX = 120 ;
     private int ballposY = 350 ;
     private int ballXdir = -1;
     private int ballYdir = -2 ;


     private MapGenerator map;



    public Gameplay(){
        map = new MapGenerator(3,7);
         addKeyListener(this);
         setFocusable(true);
         setFocusTraversalKeysEnabled(false);
          timer  = new Timer(delay,this);
         timer.start();
     }

       public void paint(Graphics g){
        //background

           g.setColor(Color.black);
         g.fillRect(1,1,692,592);

         // drawing map
           map.draw((Graphics2D)g);

         //borders
           g.setColor(Color.yellow);
           g.fillRect(0,0,5,592);
           g.fillRect(0,0,692,3);
           g.fillRect(691,0,3,592);

           //scores
           g.setColor(Color.white);
           g.setFont(new Font("serif",Font.BOLD,25));
           g.drawString(""+score,590,30);


           // paddle
           g.setColor(Color.green);
           g.fillRect(playerx,550,100,8);


           // ball
           g.setColor(Color.red);
           g.fillOval(ballposX,ballposY,20,20);

           if (totalbriks <=0){
               play = false ;
               ballXdir =0 ;
               ballYdir = 0 ;
               g.setColor(Color.red);
               g.setFont(new Font("serif",Font.BOLD, 30));
               g.drawString("YOU WON",260 , 300 );
               g.setFont(new Font("serif",Font.BOLD, 20));
               g.drawString("HIT Enter to restart",230 , 350 );

           }

           if (ballposY > 570) {
               play = false ;
               ballXdir =0 ;
               ballYdir = 0 ;
               g.setColor(Color.red);
               g.setFont(new Font("serif",Font.BOLD, 30));
               g.drawString("GAME OVER",230 , 300 );



               g.setFont(new Font("serif",Font.BOLD, 20));
               g.drawString("HIT Enter to restart",230 , 350 );

           }

           g.dispose();


       }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerx, 550, 100, 8))){
                ballYdir = -ballYdir;

        }

            A : for(int i = 0 ; i < map.map.length; i++ ){
                for (int j = 0 ; j< map. map[0]. length; j++){
                    if (map.map[i][j] > 0){
                        int brickX =  j* map.brickWidth +80;
                        int brickY = i * map.brickHeight + 50 ;
                        int brickwidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY,20 , 20);
                        Rectangle brickRect = rect;

                        if ( ballRect.intersects(brickRect)){
                            map.setBrickValue(0 ,i,j);
                            totalbriks--;
                            score+=5;
                            // Determine the direction of the ball after collision
    if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
    // If the ball hits the left or right side of the brick, reverse the horizontal direction
    ballXdir = -ballXdir;
        } 

    if (ballposY + 19 <= brickRect.y || ballposY + 1 >= brickRect.y + brickRect.height) {
    // If the ball hits the top or bottom of the brick
    // and also hits the left or right side of the brick, 
    // you may want to decide the direction based on the relative positions
    // For example, if the ball is closer to the top side of the brick, reverse the vertical direction,
    // and if it's closer to the left side, reverse the horizontal direction
    double centerBrickX = brickRect.x + brickRect.width / 2.0;
    double centerBrickY = brickRect.y + brickRect.height / 2.0;

    if (Math.abs(ballposX + 10 - centerBrickX) < Math.abs(ballposY + 10 - centerBrickY)) {
        // Closer to the left/right side, reverse the horizontal direction
        ballXdir = -ballXdir;
    } else {
        // Closer to the top/bottom side, reverse the vertical direction
        ballYdir = -ballYdir;
    }
}

                            else{
                                ballYdir = - ballYdir;
                            }
                            break A ;

                        }

                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX<0){
                ballXdir = -ballXdir;
            }

            if (ballposY<0){
                ballYdir = -ballYdir;
            }

            if (ballposX > 670){
                ballXdir = -ballXdir;
            }

        }



        repaint();


    }

    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerx >= 600) {
                playerx = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerx < 10) {
                playerx = 10;
            } else {
                moveleft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true ;
                ballposX=120 ;
                ballposY =350 ;
                ballXdir = -1;
                ballYdir = -2;
                playerx = 310 ;
                score = 0 ;
                totalbriks = 21 ;
                map = new MapGenerator(3,7);


                repaint();

            }
        }
    }
    public void moveRight() {
        play = true;
        playerx += 20;
    }

            public  void moveleft(){
    play = true;
    playerx -=20;
    }
}






