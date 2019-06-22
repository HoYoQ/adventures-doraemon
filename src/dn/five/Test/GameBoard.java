package dn.five.Test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dn.five.Entity.Bean;
import dn.five.Entity.Block;
import dn.five.Entity.Eater;
import dn.five.Entity.Enemy;



public class GameBoard extends JPanel implements KeyListener{
	Random ran = new Random();
	protected static int windowWidth = 1200;
	protected static int windowHeight = 800; 	 	 	
		
	static final int RUNNING = 0;
	static final int KNOCKED = 1;
	static final int STOP = 2;
	static final int DEAD = 3;
	
	public static int marginX = 0;
	public static int marginY = 0;
	
	//��Ϸ״̬
	static final int WaitGame = 1;
	static final int inGame = 2;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage background1;
	
	/*д��Ϸ��״̬����*/
	public static final int START = 0;
	public static final int PLAYING = 1;
	public static final int PAUSE = 2;
	public static final int OVER = 3;
	public static final int VICTORY = 4;
	int state;
	
	//��ǰ�ؿ�
	int checkPoint = 1;
	//ͨ������
	Boolean isOpen = false;
	
	
	
	public static int beanNum = 0;
	int index = 0;
	static int inter = 15;
	private int DICTION = 3;
	private Eater eater = new Eater();
	private Enemy[] enemys = {};
	private Bean[] beans = {};
	private int key = ran.nextInt(450);
	private Block[] blocks = {};
	public int[][] arrs = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0,0,0},
			{0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0},
			{0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0},
			{0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,1,0,2,2,0,0,2,2,0,1,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,1,0,2,2,2,2,2,2,0,1,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,1,1,1,1,1,0,0,1,0,2,2,2,2,2,2,0,1,0,0,1,1,1,1,1,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,1,0,2,2,2,2,2,2,0,1,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		};
	//����ͼƬ
	static {
		start = loadImage("/img/start.jpg");
		pause = loadImage("/img/pause.png");
		gameover = loadImage("/img/gameover.png");
		background1 = loadImage("/img/background1.png");
	}
	/*����ͼƬ�ķ��� fileName��ͼƬ·��*/
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(Eater.class.getResource(fileName));
			return img;
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
	}
	/*��ʼ���������飬�ϰ������飬�ͳԶ��ˣ��ͱ�����*/
	public void init() {
		state = START;
		int i,j,k;
		for(k=0;k<5;k++) {
			for(i = 0;i<19;i++) {
				for(j = 0;j<30;j++) {
					if(arrs[i][j] == 0) {
						blocks = Arrays.copyOf(blocks, blocks.length + 1);
						blocks[blocks.length - 1] = new Block(i,j+k*30,k+1);	
						
					}else if(arrs[i][j] == 1) {
						beans = Arrays.copyOf(beans, beans.length + 1);
						beans[beans.length - 1] = new Bean(i,j+k*30,k+1);
						beanNum+=5;
					}
				}
			}
			System.out.println(beans.length);
		}
		for(i=0;i<5;i++) {
			for(j= 0;j<(i*2+1);j++) {
				enemys = Arrays.copyOf(enemys,enemys.length+1);
				enemys[enemys.length-1] = new Enemy(i*windowWidth,0,1+i);
			}
		}
		//�������Key
		key = ran.nextInt(198);
		beans[key].setStyle(checkPoint);
	}
	
	
	//ͨ����
	public void openDoor(int x,int y) {
		for(int i=0;i<blocks.length;i++) {
			if(blocks[i].getI()==x && blocks[i].getJ()==y) {
				blocks[i].setCanGo(true);
				break;
			}
		}
	}
	//ͨ���ر�
	public void closeDoor(int x,int y) {
		for(int i=0;i<blocks.length;i++) {
			if(blocks[i].getI()==x && blocks[i].getJ()==y) {
				blocks[i].setCanGo(false);
				break;
			}
		}
	}
	//��Կ��
	public void findKey() {
		if(beans[key].isHaveEaten() && isOpen == false) {
			if(checkPoint == 5) {
				state = VICTORY;
			}else {
				beans = Arrays.copyOf(beans, beans.length + 3);
				beans[beans.length -3] = new Bean(14,27,1);
				beans[beans.length -2] = new Bean(14,28,1);
				beans[beans.length -1] = new Bean(14,29,1);
				openDoor(14,30+(checkPoint-1)*30);
				openDoor(14,27+(checkPoint-1)*30);
				openDoor(14,28+(checkPoint-1)*30);
				openDoor(14,29+(checkPoint-1)*30);
				isOpen = true;
			}
		}
	}
	/*���ӵ���*/
	public void addEnemy() {
		if(index/700>0 && index%(500-checkPoint*20)==0) {
			enemys = Arrays.copyOf(enemys,enemys.length+1);
			switch(checkPoint) {
				case 1:
					enemys[enemys.length-1] = new Enemy(0,0,1);
					break;
				case 2:
					enemys[enemys.length-1] = new Enemy(1*windowWidth,0,2);
					break;
				case 3:
					enemys[enemys.length-1] = new Enemy(1*windowWidth,0,3);
					break;
				case 4:
					enemys[enemys.length-1] = new Enemy(1*windowWidth,0,4);
					break;
				case 5:
					enemys[enemys.length-1] = new Enemy(1*windowWidth,0,5);
					break;
				default:
					break;
			}
		}
		
	}
	/*��ͼ�ƶ���������һ��*/
	public void changePosition(int marginX,int marginY) {
		eater.setX(eater.getX()+marginX+80);
		for(int i=0;i<blocks.length;i++) {
			blocks[i].setX(blocks[i].getX()+marginX);
		}
		for(int i=0;i<beans.length;i++) {
			beans[i].setX(beans[i].getX()+marginX);
		}
		for(int i=0;i<enemys.length;i++) {
			enemys[i].setX(enemys[i].getX()+marginX);
		}
		checkPoint++;
		closeDoor(14,0+(checkPoint-1)*30);
		closeDoor(14,27+(checkPoint-2)*30);
		closeDoor(14,28+(checkPoint-2)*30);
		closeDoor(14,29+(checkPoint-2)*30);
		isOpen = false;
		key = ran.nextInt(198)+(checkPoint-1)*198;
		beans[key].setStyle(checkPoint);
		index = 0;
		
	}
	/*�ж��Ƿ�ʤ��*/
	public void isVictory(Eater eater) {
		if(eater.getX()>5*windowWidth-40) {
			state = VICTORY;
		}
	}
	
	
	
	public GameBoard() {

		init();
		Timer timer = new Timer();
		/*��ʱ��*/
		timer.schedule(new TimerTask() {
			@Override	
			public void run() {
				if(state == PLAYING) {
					//�ж��Ƿ�ʤ��
					isVictory(eater);
					//�Ƿ��ҵ�Կ��
					findKey();
					//��������
					addEnemy();
					if(eater.getX()<0) {
						marginX = windowWidth;
						changePosition(marginX,marginY);
					}else if(eater.getX()>windowWidth) {
						marginX = -windowWidth;
						changePosition(marginX,marginY);
					}
					
					if(eater.getState() != DEAD) {
						if(index%(6-checkPoint) == 0) {
							for(int i = 0;i<enemys.length;i++) {
								enemys[i].move(blocks);
							}
						}
						if(beanNum == 0) {
							JOptionPane.showMessageDialog(null, "Victory����");
							System.exit(0);
						}
						if(eater.isHitEnemy(enemys)) {
							eater.setState(DEAD);
							state = OVER;
						}
						if(eater.getState() == RUNNING) {
							if(!eater.isHitBlock(blocks) ) {
								eater.step();
							}else {
								eater.setState(KNOCKED);
							}
						}else if(eater.getState() == KNOCKED){
							eater.setState(STOP);
						}else if(eater.getState() == STOP) {
							
						}
						eater.eatBean(beans);
						repaint();
					}else {
						if(eater.getY()<600) {
							eater.setDirection(2);
							eater.step();
							repaint();
						}else {
							state = OVER;
						}
					}
				}else if(state == VICTORY) {
					System.out.println("ͨ����");
					JOptionPane.showMessageDialog(null, "Victory������ �������㣡��");
					System.exit(0);
				}
			}
		},0,15);
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	//���̿��Ʒ���
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO �Զ����ɵķ������
		eater.autoPosition(eater,beans);
		if(e.getKeyCode() == KeyEvent.VK_UP && eater.canChangWay(blocks,1) &&eater.getState()!=DEAD) {
			eater.setDirection(1);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN && eater.canChangWay(blocks,2) &&eater.getState()!=DEAD) {
			eater.setDirection(2);
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT && eater.canChangWay(blocks,3) &&eater.getState()!=DEAD) {
			eater.setDirection(3);
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT && eater.canChangWay(blocks,4) &&eater.getState()!=DEAD) {
			eater.setDirection(4);
		}else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(state == PLAYING) {
				state = PAUSE;
			}else {
				state = PLAYING;
			}
		}
		eater.setState(RUNNING);
		repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void paint(Graphics g) {
		/*���ϰ���Ͷ���*/
		super.paint(g);
		index++;
		int i;
		//g.drawImage(background1, 0,0,null);
		setBackground(Color.white);
		

		for(i = 0;i<blocks.length;i++) {
			if(!blocks[i].isCanGo()) {
				blocks[i].paint(g);
			}
		}
		
		for(i = 0;i<beans.length;i++) {
			if(!beans[i].isHaveEaten()) {
				beans[i].paint(g);
			}
		}
		for(i = 0;i<enemys.length;i++) {
			enemys[i].paint(g);
		}
		
		
		eater.paint(g);
		g.drawString(String.valueOf(beanNum),0, 10);
		switch(state) {
			case START:
				g.drawImage(start, 400,50,null);
				break;
			case PAUSE:
				g.drawImage(pause, 400,50,null);
				break;
			case OVER:
				g.drawImage(gameover,400,50, null);
				break;
			default:break;
		}
	}	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		GameBoard panel = new GameBoard();
		frame.add(panel);
		frame.addKeyListener(panel);
		panel.addKeyListener(panel);

		//���ô��ڴ�С
		frame.setSize(windowWidth,windowHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		//��ʾ����
		frame.setVisible(true);
	
	}
}
