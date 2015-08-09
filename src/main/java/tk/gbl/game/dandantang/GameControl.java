package tk.gbl.game.dandantang;

import tk.gbl.game.dandantang.bean.DistanceInfo;
import tk.gbl.game.dandantang.bean.SelfInfo;
import tk.gbl.game.dandantang.bean.WorldInfo;
import tk.gbl.game.dandantang.recognition.InfoSpider;

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
    }
    int count = 20;
    while (count-- > 0) {
      robot.keyPress(KeyEvent.VK_UP);
      robot.keyRelease(KeyEvent.VK_UP);
    }
    SelfInfo selfInfo = InfoSpider.getSelfInfo();

    //把向左的，等效为向右的情况
    distanceInfo.setWidth(Math.abs(distanceInfo.getWidth()));

    double radian = selfInfo.getAngle() * 2 * Math.PI / 360;
    double tan = Math.tan(radian);
    double cos = Math.cos(radian);
    double up = WorldInfo.getGravity() * distanceInfo.getWidth() * distanceInfo.getWidth();
    double down = 2 * (tan * distanceInfo.getWidth() - distanceInfo.getHeight());
    double vx = Math.sqrt(up / down);
    double v = vx / cos;

    selfInfo.setPower(v / WorldInfo.getPowerRatio());

    System.out.println("revise");
    System.out.println("selfInfo" + selfInfo);
    System.out.println("distanceInfo" + distanceInfo);

    System.out.println("速度" + v);

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
