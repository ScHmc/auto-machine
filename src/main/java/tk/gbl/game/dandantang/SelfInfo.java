package tk.gbl.game.dandantang;

/**
 * Date: 2015/8/1
 * Time: 16:51
 *
 * @author Tian.Dong
 */
public class SelfInfo {

  /**
   * 角度
   */
  Integer angle;

  /**
   * 力量
   */
  Integer power;

  Integer width;

  Integer height;

  public Integer getAngle() {
    return angle;
  }

  public void setAngle(Integer angle) {
    this.angle = angle;
  }

  public Integer getPower() {
    return power;
  }

  public void setPower(Integer power) {
    this.power = power;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  @Override
  public String toString() {
    return "SelfInfo{" +
        "angle=" + angle +
        ", power=" + power +
        ", width=" + width +
        ", height=" + height +
        '}';
  }
}
