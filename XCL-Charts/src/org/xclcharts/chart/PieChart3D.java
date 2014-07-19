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
package org.xclcharts.chart;

import java.util.List;

import org.xclcharts.common.DrawHelper;
import org.xclcharts.common.MathHelper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * @ClassName Pie3DChart
 * @Description  3D饼图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class PieChart3D extends PieChart{
	
	private static final String TAG="PieChart3D";
	
	//渲染层数
	private final int mRender3DLevel = 15; 
		
	public PieChart3D() {
		// TODO Auto-generated constructor stub
		super();	     
	}

	@Override 
	protected boolean renderPlot(Canvas canvas)
	{		
		//数据源
 		List<PieData> chartDataSource = this.getDataSource();
 		if(null == chartDataSource)
		{
 			Log.e(TAG,"数据源为空.");
 			return false;
		}
 		
		//计算中心点坐标		
		float cirX = plotArea.getCenterX();
	    float cirY = plotArea.getCenterY();	     
        float radius = getRadius();
              
        //确定去饼图范围
        float arcLeft = sub(cirX , radius);  
        float arcTop  = sub(cirY , radius);  
        float arcRight = add(cirX , radius) ;  
        float arcBottom = add(cirY , radius) ;  
        RectF arcRF0 = new RectF(arcLeft ,arcTop,arcRight,arcBottom);   
        	       
        //画笔初始化
		Paint paintArc = new Paint();  
		paintArc.setAntiAlias(true);					
		
 		float initOffsetAgent = mOffsetAgent;		
		//3D
        float currentAgent = 0.0f;	             
     
		for(int i=0;i < mRender3DLevel;i++)
		{
            canvas.save(Canvas.MATRIX_SAVE_FLAG);
            canvas.translate(0,mRender3DLevel - i );
		  for(int j=0;j< chartDataSource.size();j++)
		  {			  
			    PieData cData =  chartDataSource.get(j);			  
				paintArc.setColor(cData.getSliceColor());				
				currentAgent = cData.getSliceAgent();
				if(Float.compare(currentAgent,0.0f) == 0 
						|| Float.compare(currentAgent,0.0f) == -1 )continue;	
				
			    if(cData.getSelected()) //指定突出哪个块
	            {				    			    	
			    	//偏移圆心点位置(默认偏移半径的1/10)
			    	float newRadius = div(radius , SELECTED_OFFSET);
			    	 //计算百分比标签
			    	MathHelper.getInstance().calcArcEndPointXY(cirX,cirY,newRadius,
			    											add(mOffsetAgent, div(currentAgent,2f))); 	
			        
			        float arcLeft2 = sub(MathHelper.getInstance().getPosX() , radius);  
			        float arcTop2  = sub(MathHelper.getInstance().getPosY() , radius) ;  
			        float arcRight2 = add(MathHelper.getInstance().getPosX() , radius) ;  
			        float arcBottom2 = add(MathHelper.getInstance().getPosY() , radius) ;  
			        
			        RectF arcRF1 = new RectF(arcLeft2 ,arcTop2,arcRight2,arcBottom2);
                    canvas.drawArc(arcRF1, mOffsetAgent, currentAgent, true,paintArc);
	            }else{
                    canvas.drawArc(arcRF0, mOffsetAgent, currentAgent, true,paintArc);
	            }			    			    
	            //下次的起始角度  
			    mOffsetAgent = add(mOffsetAgent,currentAgent);  	            
	            //k += 2;
			}
            canvas.restore();
		    mOffsetAgent = initOffsetAgent;
		}
	
		
		//平面		
		currentAgent = 0.0f;	
		mOffsetAgent = initOffsetAgent;
	
		for(int j=0;j< chartDataSource.size();j++)
		{
		 	PieData cData =  chartDataSource.get(j);	
		 	currentAgent = cData.getSliceAgent();
		 	int darkColor =  DrawHelper.getInstance().getDarkerColor((int)cData.getSliceColor());			  
		  	paintArc.setColor( darkColor); 						
		  	
		    if(cData.getSelected()) //指定突出哪个块
            {					    					    	
		    	//偏移圆心点位置(默认偏移半径的1/10)
		    	float newRadius = div(radius , SELECTED_OFFSET);
		    	 //计算百分比标签
		    	MathHelper.getInstance().calcArcEndPointXY(
		    				cirX,cirY,newRadius,add(mOffsetAgent , div(currentAgent,2f))); 	
		        
		        float arcLeft2 = sub(MathHelper.getInstance().getPosX() , radius);  
		        float arcTop2  = sub(MathHelper.getInstance().getPosY() , radius );  
		        float arcRight2 = add(MathHelper.getInstance().getPosX() , radius );  
		        float arcBottom2 = add(MathHelper.getInstance().getPosY() , radius) ;  
		        
		        RectF arcRF1 = new RectF(arcLeft2 ,arcTop2,arcRight2,arcBottom2);
                canvas.drawArc(arcRF1, mOffsetAgent, (float) currentAgent, true,paintArc);
		        renderLabel(canvas,cData.getLabel(),MathHelper.getInstance().getPosX(),
		        									MathHelper.getInstance().getPosY(),
		        			radius,mOffsetAgent,currentAgent);                
            }else{
                canvas.drawArc(arcRF0, mOffsetAgent, (float) currentAgent, true, paintArc);
     	        renderLabel(canvas,cData.getLabel(),cirX, cirY,radius,mOffsetAgent,currentAgent);
     	    }				    		    
           //下次的起始角度  
		    mOffsetAgent = add(mOffsetAgent,currentAgent);
		}			
		//图KEY
		plotLegend.renderPieKey(canvas,this.getDataSource());
		return true;
	}

}
