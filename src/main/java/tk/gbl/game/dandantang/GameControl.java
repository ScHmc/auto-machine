package tk.gbl.game.dandantang;

import tk.gbl.game.dandantang.bean.DistanceInfo;
import tk.gbl.game.dandantang.bean.SelfInfo;
import tk.gbl.game.dandantang.bean.Wind;
import tk.gbl.game.dandantang.bean.WorldInfo;
import tk.gbl.game.dandantang.recognition.InfoSpider;
import tk.gbl.game.dandantang.recognition.WindInfoSpider;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Date: 2015/8/2
 * Time: 10:27
 *
 * @author Tian.Dong
 */
public class GameControl {
  static Robot robot;


  static {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  /**
   * S = Vx * t
   * H = (1/2)(Vy * t1 - Vy * g * t1)
   * t = 2* t0 + t1
   * Vy = g * t0
   * tan(radian) = Vy / Vx
   * ->
   * <p>
   * V = Vx / cos(radian)
   */
  public static SelfInfo revise(DistanceInfo distanceInfo) {
    if (GlobalValue.leftOrRight == null) {
      if (distanceInfo.getWidth() > 0) {
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        GlobalValue.leftOrRight = 0;
      } else {
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        GlobalValue.leftOrRight = 1;
      }
    } else {
      if (distanceInfo.getWidth() > 0) {
        if(GlobalValue.leftOrRight == 1) {
          robot.keyPress(KeyEvent.VK_RIGHT);
          robot.keyRelease(KeyEvent.VK_RIGHT);
          GlobalValue.leftOrRight = 0;
        }
      } else {
        if(GlobalValue.leftOrRight == 0) {
          robot.keyPress(KeyEvent.VK_LEFT);
          robot.keyRelease(KeyEvent.VK_LEFT);
          GlobalValue.leftOrRight = 1;
        }
      }
    }
    int count = 10;
    while (count-- > 0) {
      robot.keyPress(KeyEvent.VK_UP);
      robot.keyRelease(KeyEvent.VK_UP);
    }
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    SelfInfo selfInfo = InfoSpider.getSelfInfo();

    double a = 0;//风力引起的x轴加速度
    Wind wind = WindInfoSpider.getWind();
    a = wind.getValue();
    if ((wind.isLeft() && distanceInfo.getWidth() < 0)
        || (!wind.isLeft() && distanceInfo.getWidth() > 0)) {
      //风从左往右吹，敌人在左边
      //风从右往左吹，敌人在右边
      a = -a;
    }
    a *= WorldInfo.windRatio;
    //把向左的，等效为向右的情况
    distanceInfo.setWidth(Math.abs(distanceInfo.getWidth()));

    double radian = selfInfo.getAngle() * 2 * Math.PI / 360;
    double tan = Math.tan(radian);
    double cos = Math.cos(radian);
    double up = WorldInfo.getGravity() * distanceInfo.getWidth();
    double down = 2 * (tan - distanceInfo.getHeight() / distanceInfo.getWidth() + a * tan * tan / WorldInfo.getGravity());
    double vx = Math.sqrt(up / down);
    double v = vx / cos;

    selfInfo.setPower(v / WorldInfo.getPowerRatio());

    down = 2 * (tan - distanceInfo.getHeight() / distanceInfo.getWidth());
    vx = Math.sqrt(up / down);
    v = vx / cos;
    selfInfo.setOriginalPower(v / WorldInfo.getPowerRatio());


    System.out.println("windInfo:" + wind);
    System.out.println("distanceInfo:" + distanceInfo);
    System.out.println("selfInfo:" + selfInfo);
    GlobalValue.wind = wind;
    GlobalValue.distanceInfo = distanceInfo;
    GlobalValue.selfInfo = selfInfo;
    GlobalValue.infoFrame.refreshInfo();

    robot.keyPress(KeyEvent.VK_B);
    robot.keyPress(KeyEvent.VK_1);
    robot.keyRelease(KeyEvent.VK_1);
//    robot.keyPress(KeyEvent.VK_1);
//    robot.keyRelease(KeyEvent.VK_1);

    robot.keyPress(KeyEvent.VK_4);
    robot.keyPress(KeyEvent.VK_5);
    robot.keyPress(KeyEvent.VK_6);
    robot.keyPress(KeyEvent.VK_3);
    robot.keyPress(KeyEvent.VK_7);
    robot.keyPress(KeyEvent.VK_8);
    robot.keyPress(KeyEvent.VK_8);
    robot.keyPress(KeyEvent.VK_8);
    robot.keyPress(KeyEvent.VK_8);
    robot.keyPress(KeyEvent.VK_8);

    return selfInfo;
  }

  public static void keyPressSpace() {
    robot.keyPress(KeyEvent.VK_SPACE);
  }

  public static void keyReleaseSpace() {
    robot.keyRelease(KeyEvent.VK_SPACE);
  }
}
