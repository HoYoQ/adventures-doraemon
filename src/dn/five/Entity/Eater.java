package dn.five.Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import dn.five.Dao.EaterAndEnemy;
import dn.five.Test.GameBoard;


public class Eater extends EaterAndEnemy{
	static final int RUNNING = 0;
	static final int KNOCKED = 1;
	static final int STOP = 2;
	static final int DEAD = 3;

	private int state = RUNNING;
	private int deadIndex;
	private static BufferedImage[] images;
	int index = 0;
	int num = 0;
	
	//����ͼƬ
	static {
		images = new BufferedImage[16];
		for(int i = 0;i<images.length;i++) {
			images[i] = loadImage("/img/hero(" + i + ").png");
		}
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
	/*�����������ȡĳһ��ͼƬ����Ҫ������д*/
	public BufferedImage getImage() {
		if(state!=DEAD){
			if(num%5==0) {
				index++;
			}
			num++;
			switch(this.direction) {
				case 1:
					return images[index%4];
				case 2:
					return images[index%4+4];
				case 3:
					return images[index%4+8];
				case 4:
					return images[index%4+12];
				default:
					return images[index%4];
			}
		}else{
			BufferedImage img = images[deadIndex++];
			if(deadIndex == images.length) {
				state = DEAD;
			}
			return img;
		}
	
	}
	/*���췽��*/
	public Eater(){
		this.x = 13*this.width+GameBoard.marginX;
		this.y = 17*this.height+GameBoard.marginY;
	}
	
	public void paint(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		
	}

	/*�˶�*/
	public void move() {
		
	}

	/*�ж�eater�Ƿ���ԳԶ���*/
	public boolean canBeEat(Bean bean) {
		int x1 = this.x - bean.getWidth();
		int x2 = this.x + this.width;
		int y1 = this.y - bean.getHeight();
		int y2 = this.y + this.height;
		int beanX = bean.getX();
		int beanY = bean.getY();
		
		if(beanX>x1 && beanX<x2 && beanY>y1 && beanY<y2) {
			return true;
		}else {
			return false;
		}
	}
	
	/*�Զ���*/
	public void eatBean(Bean[] beans) {
		for(int i = 0;i<beans.length;i++) {
			if( !beans[i].isHaveEaten() && this.canBeEat(beans[i])) {
				beans[i].setHaveEaten(true);
				GameBoard.beanNum--;
			}
		}
	}
	
	/*Ԥ���Ƿ���Թ���*/
	public boolean canChangWay(Block[] blocks,int dic) {
		int reDic = this.getDirection();
		this.setDirection(dic);
		this.step();
		if(this.isHitBlock(blocks)) {
			this.reStep();
			this.setDirection(reDic);
			return false;
		}else {
			this.reStep();
			this.setDirection(reDic);
			return true;
		}
	}
	
	
	
	/*�Զ�Уλ*/
	public void autoPosition(Eater eater,Bean[] beans) {
		for(int i = 0;i<beans.length;i++) {
			if(eater.canBeEat(beans[i])){
				int x1 = eater.getX();
				int y1 = eater.getY();
				int x2 = beans[i].getX();
				int y2 = beans[i].getY();
				int x3 = x1 - x2;
				int y3 = y1 - y2;
				if(x3>= -eater.getWidth()/2 && x3<=eater.getWidth()/2 && y3 >= -eater.getHeight()/2 && y3 <= eater.getHeight()/2) {
					eater.setX(x2);
					eater.setY(y2);
				}
			}
		}
		
	}
	
	
	/*�ж�eater�������ײ*/
	public Boolean isHitEnemy(Enemy[] enemys) {
		for(int i = 0;i<enemys.length;i++) {
			if(this.hitEnemy(enemys[i])) {
				//System.out.println("ײ��");
				return true;
			}
		}
		return false;
	}
	
	/*�ж��Ƿ�����˷����Ӵ�*/
	public boolean hitEnemy(Enemy enemy) {
		int x1 = this.x - enemy.getWidth()+10;
		int x2 = this.x + this.width - 10;
		int y1 = this.y - enemy.getHeight()+10;
		int y2 = this.y + this.height - 10;
		int enemyX = enemy.getX();
		int enemyY = enemy.getY();
		
		if(enemyX>x1 && enemyX<x2 && enemyY>y1 && enemyY<y2) {
			return true;
		}else {
			return false;
		}
	}
	 
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public synchronized final int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public synchronized final int getState() {
		return state;
	}
	public synchronized final void setState(int state) {
		this.state = state;
	}
	
	
	
}
