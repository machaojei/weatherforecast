package tools.com.vera;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class VeraDrawerLayout extends DrawerLayout {

  public VeraDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public VeraDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public VeraDrawerLayout(@NonNull Context context) {
    super(context);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev){
    //switch(ev.getAction()) {
    //  case MotionEvent.ACTION_DOWN:
    //    final float x = ev.getX();
    //    final float y = ev.getY();
    //    final View touchedView = findTopChildUnder((int) x, (int) y);
    //    if (touchedView != null ) {
    //      return false;
    //    }
    //    break;
    //
    //  default:
    //    break;
    //}
    return super.onInterceptTouchEvent(ev);
  }

  /** * 判断点击位置是否位于相应的View内 * @param x * @param y * @return */
  public View findTopChildUnder(int x, int y) {
      if (x >= getLeft() && x < getRight() &&
          y >= getTop() && y < getBottom()) {
        return this;
      }
    return null;
  }

  /** * 判断点击触摸点的View是否是ContentView(即是主页面的View) * @param child * @return */
  boolean isContentView(View child) {
    return ((DrawerLayout.LayoutParams) child.getLayoutParams()).gravity == Gravity.NO_GRAVITY;
  }



}
