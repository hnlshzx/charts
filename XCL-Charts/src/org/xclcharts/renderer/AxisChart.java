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
 * @version 1.0
 */
package org.xclcharts.renderer;


import org.xclcharts.common.DrawHelper;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.renderer.axis.CategoryAxis;
import org.xclcharts.renderer.axis.CategoryAxisRender;
import org.xclcharts.renderer.axis.DataAxis;
import org.xclcharts.renderer.axis.DataAxisRender;
import org.xclcharts.renderer.plot.Legend;
import org.xclcharts.renderer.plot.LegendRender;
import org.xclcharts.renderer.plot.PlotKey;
import org.xclcharts.renderer.plot.PlotKeyRender;

import android.graphics.Canvas;

/**
 * @ClassName AxisChart
 * @Description 所有用到坐标类的图表的基类,主要用于定义和绘制坐标轴
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class AxisChart extends XChart {
		
	//数据轴
	protected DataAxisRender dataAxis  = null;
	//标签轴
	protected CategoryAxisRender categoryAxis  = null;	
	//图例类
	protected LegendRender legend = null;
	//图的Key基类
	protected PlotKeyRender plotKey = null;
	
	//格式化柱形顶上或线交叉点的标签
	private IFormatterDoubleCallBack mItemLabelFormatter;
	
	public AxisChart() {
		// TODO Auto-generated constructor stub		
		super();
		initChart();		
	}
	
	
	/**
	 * 初始化设置
	 */
	private void initChart()
	{		
		//为坐标系设置一个默认间距
		//this.setPaddingPercent(15f, 15f, 20f, 5f);
		
		//数据轴
		dataAxis  = new DataAxisRender();
		//标签轴
		categoryAxis  = new CategoryAxisRender();				
		//图例
		legend = new LegendRender();
		
		//key值
		plotKey = new PlotKeyRender(this);
	}
	
	
	/**
	 * 开放图的key基类
	 * @return	基类
	 */
	public PlotKey getPlotKey()
	{
		return plotKey;
	}		

	 /**
	  * 开放数据轴绘制类
	  * @return 数据轴绘制类
	  */
	public DataAxis getDataAxis() {
		return dataAxis;
	}

	/**
	 * 开放标签轴绘制类
	 * @return 标签轴绘制类
	 */
	public CategoryAxis getCategoryAxis() {
		return categoryAxis;
	}

	/**
	 * 开放图例绘制类
	 * @return 图例绘制类
	 */
	public Legend getLegend() {
		return legend;
	}
	
	
	
	/**
	 * 计算主图表区范围内,这个还不完善, 如3D底座厚度...., 
	 *  本意是依标题之类，灵活计算高度，但发现在多图表混合时，不太好用.
	 */
	@Override
	protected void calcPlotRange()
	{				
		super.calcPlotRange();
				
		//图的内边距属性
		float perLeft = getPaddingLeft();
		float perRight = getPaddingRight();
		//float perTop = mPaddingPercentTop;
		float perBottom =  getPaddingBottom();	
		
		float width = getWidth();
		float height = getHeight();		

		// 要依长宽比，区分横竖屏间的比例 [应当依长宽比设置对应的横竖比]
		if(width > height) //横屏
		{
			float scrPer = height / width;			
			//perTop += scrPer;
			perBottom += scrPer;
			perLeft -= scrPer;
			perRight -= scrPer;		
		}			
		
		if(perLeft > 0)
		{
			float rederLeft = Math.round( height / 100 * perLeft);		
			if(this.getLegend().getLeftLegend().length() > 0)
			{	
				float legendLength = DrawHelper.getInstance().getPaintFontHeight(getLegend().getLeftLegendPaint());
				if(legendLength > rederLeft) rederLeft = legendLength;
			}
			plotArea.setLeft( getLeft() + rederLeft);
		}
		
		if(perRight > 0 )
		{
			float rederRight =  Math.round( width / 100 * perRight);
			if(this.getLegend().getRightLegend().length() > 0)
			{	
				float legendLength = DrawHelper.getInstance().getPaintFontHeight(
												getLegend().getRightLegendPaint());	
				if(legendLength > rederRight ) rederRight = legendLength;
			}
			plotArea.setRight(this.getRight() - rederRight);	
		}
		
		if(perBottom > 0 )
		{
			float rederBottom = Math.round( height / 100 * perBottom );
			if(this.getLegend().getLowerLegend().length() > 0)
			{			
				float legendHeight = DrawHelper.getInstance().getPaintFontHeight(
													getLegend().getLowerLegendPaint());	
				if(legendHeight > rederBottom ) rederBottom = legendHeight;
			}
			plotArea.setBottom(this.getBottom() - rederBottom);	
		}
	}
	
	
	/**
	 * 轴所占的屏幕宽度
	 * @return  屏幕宽度
	 */
	protected float getAxisScreenWidth()
	{
		return(Math.abs(plotArea.getRight() - plotArea.getLeft()));
	}
	
	/**
	 * 轴所占的屏幕高度
	 * @return 屏幕高度
	 */
	protected float getAxisScreenHeight()
	{
		return( Math.abs(plotArea.getBottom() - plotArea.getTop()));
	}
	
	/**
	 * 设置标签显示格式
	 * 
	 * @param callBack
	 *            回调函数
	 */
	public void setItemLabelFormatter(IFormatterDoubleCallBack callBack) {
		this.mItemLabelFormatter = callBack;
	}

	/**
	 * 返回标签显示格式
	 * 
	 * @param value 传入当前值
	 * @return 显示格式
	 */
	protected String getFormatterItemLabel(double value) {
		String itemLabel = "";
		try {
			itemLabel = mItemLabelFormatter.doubleFormatter(value);
		} catch (Exception ex) {
			itemLabel = Double.toString(value);
			// DecimalFormat df=new DecimalFormat("#0");
			// itemLabel = df.format(value).toString();
		}
		return itemLabel;
	}
	
	protected boolean postRender(Canvas canvas) throws Exception
	{
		try {
			//计算主图表区范围
			 calcPlotRange();
			//画Plot Area背景			
			 plotArea.render(canvas);
			//画奇偶行填充,横竖网格线			
			// plotGrid.render(canvas);
			//画图例Legend
	
			//绘制标题
			renderTitle(canvas);
			//绘制图例
			legend.setRange(this);
			legend.render(canvas);
		
		}catch( Exception e){
			 throw e;
		}
		return true;
	}

	public boolean render(Canvas canvas) throws Exception {
		// TODO Auto-generated method stub
	
		try {		
			super.render(canvas);
									
		}catch( Exception e){
			 throw e;
		}
		return true;
	}		
	 
	

}
