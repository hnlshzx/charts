/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version v0.1
 */

package org.xclcharts.renderer;

/**
 * @ClassName XChart
 * @Description 所有图表类的基类,定义了图表区，标题，背景等
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * MODIFIED    YYYY-MM-DD   REASON
 */

import org.xclcharts.common.DrawHelper;
import org.xclcharts.renderer.plot.PlotArea;
import org.xclcharts.renderer.plot.PlotAreaRender;
import org.xclcharts.renderer.plot.PlotGrid;
import org.xclcharts.renderer.plot.PlotGridRender;
import org.xclcharts.renderer.plot.PlotTitle;
import org.xclcharts.renderer.plot.PlotTitleRender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class XChart implements IRender {

	// 开放主图表区
	protected PlotAreaRender plotArea = null;
	// 开放主图表区网格
	protected PlotGridRender plotGrid = null;
	// 标题栏
	private PlotTitleRender plotTitle = null;
	// 图大小范围
	private float mLeft = 0.0f;
	private float mTop = 0.0f;
	private float mRight = 0.0f;
	private float mBottom = 5f;
	// 图宽高
	private float mWidth = 0.0f;
	private float mHeight = 0.0f;

	// 图的内边距属性
	private float mPaddingTop = 0f;
	private float mPaddingBottom = 0f;
	private float mPaddingLeft = 0f;
	private float mPaddingRight = 0f;
	// 图表背景色
	private Paint mChartBackgroundPaint = null;
	// 是否画背景色
	private boolean mBackgroundColorVisible = false;
	
	//坐标系原点坐标
	private float[] mTranslateXY = new float[2];		
		

	public XChart() {
		initChart();
	}

	private void initChart() {
		
		mTranslateXY[0] = 0.0f;
		mTranslateXY[1] = 0.0f;
		
		// 图表
		plotArea = new PlotAreaRender();
		plotGrid = new PlotGridRender();
		plotTitle = new PlotTitleRender();
		plotTitle.setTitlePosition(XEnum.Position.CENTER);
		plotTitle.setTitleAlign(XEnum.ChartTitleAlign.CENTER);

		initPaint();
	}

	private void initPaint() {
		// 背景画笔
		mChartBackgroundPaint = new Paint();
		mChartBackgroundPaint.setStyle(Style.FILL);
		mChartBackgroundPaint.setColor(Color.WHITE);
	}

	// 图的内边距属性
	/**
	 * 设置内边距百分比,即绘图区与图边距相隔距离的百分比,不允许负值
	 * 
	 * @param top
	 *            顶
	 * @param bottom
	 *            底
	 * @param left
	 *            左边
	 * @param right
	 *            右边
	 */
	public void setPadding(float top, float bottom, float left, float right) {
		if (top > 0)
			mPaddingTop = top;
		if (bottom > 0)
			mPaddingBottom = bottom;
		if (left > 0)
			mPaddingLeft = left;
		if (right > 0)
			mPaddingRight = right;
	}

	/**
	 * 返回主图表区基类
	 * 
	 * @return 主图表区基类
	 */
	public PlotArea getPlotArea() {
		return plotArea;
	}

	/**
	 * 返回主图表区网格基类
	 * 
	 * @return 网格基类
	 */
	public PlotGrid getPlotGrid() {
		return plotGrid;
	}

	/**
	 * 返回图的标题基类
	 * 
	 * @return 标题基类
	 */
	public PlotTitle getPlotTitle() {
		return plotTitle;
	}

	/**
	 * 设置图表绘制范围,以指定起始点及长度方式确定图表大小.
	 * 
	 * @param startX
	 *            图表起点X坐标
	 * @param startY
	 *            图表起点Y坐标
	 * @param width
	 *            图表宽度
	 * @param height
	 *            图表高度
	 */
	public void setChartRange(float startX, float startY, float width,
			float height) {
		
		if (startX > 0)
			mLeft = startX;
		if (startY > 0)
			mTop = startY;
				
		mRight = startX + width;
		mBottom = startY + height;

		if (width > 0)
			mWidth = width;
		if (height > 0)
			mHeight = height;
	}

	/**
	 * 设置图表绘制范围,以指定上下左右范围方式确定图表大小.
	 * 
	 * @param left
	 *            图表左上X坐标
	 * @param top
	 *            图表左上Y坐标
	 * @param right
	 *            图表右下X坐标
	 * @param bottom
	 *            图表右上Y坐标
	 */
	public void setChartRect(float left, float top, float right, float bottom) {

		if (left > 0)
			mLeft = left;
		if (top > 0)
			mTop = top;
		if (right > 0)
			mRight = right;
		if (bottom > 0)
			mBottom = bottom;
		
		mWidth = Math.abs(right - left);
		mHeight = Math.abs(bottom - top);
	}

	/**
	 * 是否为竖屏显示
	 * 
	 * @return 是否为竖屏
	 */
	public boolean isVerticalScreen() {
		if (mWidth < mHeight) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 开放背景画笔
	 * 
	 * @return 画笔
	 */
	public Paint getBackgroundPaint() {
		return mChartBackgroundPaint;
	}

	/**
	 * 设置标题
	 * 
	 * @param title 标题
	 */
	public void setTitle(String title) {
		plotTitle.setTitle(title);
	}

	/**
	 * 设置子标题
	 * 
	 * @param subtitle 子标题
	 */
	public void addSubtitle(String subtitle) {
		plotTitle.setSubtitle(subtitle);
	}

	/**
	 * 设置标题上下显示位置,即图上边距与绘图区间哪个位置(靠上，居中，靠下).
	 */
	public void setTitlePosition(XEnum.Position position) {
		plotTitle.setTitlePosition(position);
	}

	/**
	 * 设置标题横向显示位置(靠左，居中，靠右)
	 * 
	 * @param align 显示位置
	 */
	public void setTitleAlign(XEnum.ChartTitleAlign align) {
		plotTitle.setTitleAlign(align);
	}

	/**
	 * 返回图表左边X坐标
	 * 
	 * @return 左边X坐标
	 */
	public float getLeft() {
		return mLeft;
	}

	/**
	 * 返回图表上方Y坐标
	 * 
	 * @return 上方Y坐标
	 */
	public float getTop() {
		return mTop;
	}

	/**
	 * 返回图表右边X坐标
	 * 
	 * @return 右边X坐标
	 */
	public float getRight() {
		return mRight;
	}

	/**
	 * 返回图表底部Y坐标
	 * 
	 * @return 底部Y坐标
	 */
	public float getBottom() {
		return mBottom;
	}

	/**
	 * 返回图表宽度
	 * 
	 * @return 宽度
	 */
	public float getWidth() {
		return mWidth;
	}

	/**
	 * 返回图表高度
	 * 
	 * @return 高度
	 */
	public float getHeight() {
		return mHeight;
	}

	/**
	 * 图绘制区相对图底部边距的缩进百分比
	 * 
	 * @return 缩进比例
	 */
	public float getPaddingBottom() {
		return mPaddingBottom;
	}

	/**
	 * 图绘制区相对图左边边距的缩进比例
	 * 
	 * @return 缩进比例
	 */
	public float getPaddingLeft() {
		return mPaddingLeft;
	}

	/**
	 * 图绘制区相对图右边边距的缩进比例
	 * 
	 * @return 缩进比例
	 */
	public float getPaddingRight() {
		return mPaddingRight;
	}
	
	/**
	 * 返回图中心点坐标
	 * @return
	 */
	public double[] getCenterXY()
	{
		double [] xy = new double[2];
		xy[0] = this.getLeft() + this.getWidth() / 2 ;
		xy[1] = this.getTop() + this.getHeight() / 2 ;		
		return xy;
	}
	
	
	/**
	 * 设置绘画时的坐标系原点位置
	 * @param x 原点x位置
	 * @param y 原点y位置
	 */
	public void setTranslateXY(float x,float y)
	{
		mTranslateXY[0] = x;
		mTranslateXY[1] = y;
	}
	
	/**
	 * 返回坐标系原点坐标
	 * @return 原点坐标
	 */
	public float[] getTranslateXY()
	{
		return mTranslateXY;
	}
	

	/**
	 * 设置是否绘制背景
	 * 
	 * @param visible 是否绘制背景
	 */
	public void setApplyBackgroundColor(boolean visible) {
		mBackgroundColorVisible = visible;
	}

	/**
	 * 设置图的背景色
	 * 
	 * @param visible 是否绘制背景
	 * @param color   背景色
	 */
	public void setBackgroundColor(int color) {
		//mBackgroundColorVisible = visible;
		getBackgroundPaint().setColor(color);
		getPlotArea().getBackgroundPaint().setColor(color);
	}

	/**
	 * 绘制图的背景
	 */
	protected void renderChartBackground(Canvas canvas) {
		if (mBackgroundColorVisible)
			canvas.drawRect(mLeft, mTop, mRight,
					mBottom, mChartBackgroundPaint);
	}

	/**
	 * 计算图的显示范围
	 */
	protected void calcPlotRange() {
		DrawHelper dw = new DrawHelper();

		// 图的内边距属性，默认按竖屏算
		float perLeft = mPaddingLeft;
		float perRight = mPaddingRight;
		float perTop = mPaddingTop;
		float perBottom = mPaddingBottom;

		// 要依长宽比，区分横竖屏间的比例
		if (mWidth > this.mHeight) // 当前状态为横屏
		{
			float scrPer = mHeight / mWidth;
			perTop += scrPer;
			perBottom += scrPer;
			perLeft -= scrPer;
			perRight -= scrPer;
		}
		plotArea.setBottom(this.mBottom
				- Math.round(this.mHeight / 100 * perBottom));
		plotArea.setLeft(this.mLeft
				+ Math.round(this.mWidth / 100 * perLeft));
		plotArea.setRight(this.mRight
				- Math.round(this.mWidth / 100 * perRight));

		float renderTop = 0.0f;
		float titleHeight = 0.0f;
		float subtitleHeight = 0.0f;
		
		if (plotTitle.getTitle().length() > 0) {
			titleHeight = dw.getPaintFontHeight(plotTitle.getTitlePaint());
		}
		if (plotTitle.getSubtitle().length() > 0) {
			subtitleHeight = dw.getPaintFontHeight(plotTitle
					.getTitlePaint());
		}
		renderTop = Math.round(this.mHeight / 100 * perTop);

		if (renderTop < titleHeight + subtitleHeight) {
			renderTop = titleHeight + subtitleHeight;
		}
		plotArea.setTop(this.mTop + renderTop);

	}

	// 导出成文件,待实现
	// public void exportAsBmpfile(String fileName)
	// {

	// }

	/**
	 * 绘制标题
	 */
	protected void renderTitle(Canvas canvas) {
		this.plotTitle.renderTitle(mLeft, mRight, mTop,
				mWidth, this.plotArea.getTop(), canvas);
	}

	@Override
	public boolean render(Canvas canvas) throws Exception {
		// TODO Auto-generated method stubcalcPlotRange

		try {
			if (null == canvas)
				return false;
			
				//设置原点位置
				canvas.translate(mTranslateXY[0],mTranslateXY[1]);
			
				// 绘制图背景
				renderChartBackground(canvas);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

}
