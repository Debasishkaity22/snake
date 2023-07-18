import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements ActionListener , KeyListener {
    int height=400,width=400;
    int max_dots=1600;
    int dots_size=10,dots;
    int x[]=new int[max_dots];
    int y[]=new int[max_dots];
    int apple_x,apple_y;
    Image body,head,apple;
    Timer timer;
   int delay=150;
    boolean left=true;
    boolean right=false;
    boolean up=false;
    boolean down=false;
    boolean GameOver=true;
    int score;
    GamePanel(){
        intgame();
        Loadimage();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer=new Timer(delay,this);
        timer.start();
    }
    public void intgame(){
        dots=3;
        x[0]=250;
        y[0]=250;
        for(int i=1;i<dots;i++){
         x[i]=x[0]+dots_size*i;
         y[i]=y[0];
        }
       locateApple();
    }
    public void Loadimage(){
        ImageIcon bodyicon=new ImageIcon("src/resources/snake body.jpeg");
        ImageIcon bodyicon1=new ImageIcon("src/resources/snakehead.jpeg");
        ImageIcon bodyicon2=new ImageIcon("src/resources/apple.jpeg");
        body=bodyicon.getImage();
        head=bodyicon1.getImage();
        apple=bodyicon2.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(GameOver){
            collidesWithApple();
            collidesWithBody();
            move();
        }
          repaint();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
       doDrawing(g);

    }
    public void doDrawing(Graphics g){
       if(GameOver){
           g.drawImage(apple,apple_x,apple_y,this);
           for(int i=0;i<dots;i++){
               if(i==0){
                   g.drawImage(head,x[0],y[0],this);
               }
               else{
                   g.drawImage(body,x[i],y[i],this);
               }
           }
       }
       else{
           gameover(g);
           timer.stop();
       }
    }
    public void locateApple(){
        apple_x=((int)(Math.random()*37))*dots_size;
        apple_y=((int)(Math.random()*37))*dots_size;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_LEFT &&(!right)){
            left=true;
            right=false;
            up=false;
            down=false;

        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT &&(!left)){
            left=false;
            right=true;
            up=false;
            down=false;

        }
        if(e.getKeyCode()==KeyEvent.VK_UP &&(!down)){
            left=false;
            right=false;
            up=true;
            down=false;

        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN &&(!up)){
            left=false;
            right=false;
            up=false;
            down=true;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void move(){
        for(int i=dots-1;i>0;i--){
           x[i]=x[i-1];
           y[i]=y[i-1];
        }
        if(left){
            x[0]-=dots_size;
        }
        if(right){
            x[0]+=dots_size;
        }
        if(up){
            y[0]-=dots_size;
        }
        if(down){
            y[0]+=dots_size;
        }
    }
    private void collidesWithBody() {
        for(int i=1;i<dots;i++){
            if(x[i]==x[0] && y[i]==y[0]&&i>4){

                GameOver=false;
            }
        }
        if((x[0]<0)){

            GameOver=false;
        }
        if((x[0]>=width)){
            GameOver=false;
        }
        if((y[0]<0)){
            timer.stop();
            GameOver=false;
        }
        if((x[0]>=height)){

            GameOver=false;
        }
    }
    public void gameover(Graphics g){
        String msg="Game Over";
        score=(dots-3)*100;
        String Scoremsg="Score: "+Integer.toString(score);
        Font small=new Font("Halvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(width-fontMetrics.stringWidth(msg))/2,height/4);
        g.drawString(Scoremsg,(width-fontMetrics.stringWidth(Scoremsg))/2,3*height/4);

    }
    private void collidesWithApple() {
        if((x[0]==apple_x)&&(y[0]==apple_y)){
            locateApple();
            dots++;

        }
    }
    private void restart(){
        GameOver=true;
        score=0;
        dots=3;
        left=true;
        right=false;
        up=false;
        down=false;
        timer.start();
        repaint();
    }
}
