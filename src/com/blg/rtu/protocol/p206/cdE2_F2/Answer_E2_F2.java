package com.blg.rtu.protocol.p206.cdE2_F2;

import android.util.Log;

import com.blg.rtu.protocol.RtuData;
import com.blg.rtu.protocol.p206.common.ControlProtocol;
import com.blg.rtu.protocol.p206.common.ProtocolSupport;
import com.blg.rtu.util.ByteUtilUnsigned;

public class Answer_E2_F2 extends ProtocolSupport{

	private static String tag = Answer_E2_F2.class.getName() ;

	/**
	 * 解析上行数据 
	 * @param rtuId RTU ID
	 * @param b 上行数据
	 * @param cp 控制域解析对象
	 * @param DCtaCode 数据功能吗
	 * @return
	 * @throws Exception
	 */
	public RtuData parse(String rtuId, byte[] b, ControlProtocol cp, String dataCode) throws Exception {
		RtuData d = new RtuData() ;
		int index = this.parseUpDataHead(rtuId, b, cp, dataCode, d);
		this.doParse(b, index, d, cp) ;

		Log.i(tag, "分析<RTU 电源采集校准系数>应答: RTU ID=" + rtuId + " 数据：" + d.getSubData().toString());
		return d;
	}
	private RtuData doParse(byte[] b, int index, RtuData d, ControlProtocol cp) throws Exception {
		Data_E2_F2 subD = new Data_E2_F2() ;
		d.setSubData(subD) ;
		
		// 分析数据域
		byte enable = b[index++] ;
		subD.setEnable_1(enable & 1) ;
		subD.setEnable_2((enable & 2) >> 1) ;
		
		// 分析数据域
		subD.setValue_1(ByteUtilUnsigned.bytes2Short_an(b, index)) ;
		index += 2 ;
		subD.setValue_2(ByteUtilUnsigned.bytes2Short_an(b, index)) ;
		index += 2 ;
		
		return d;
	}
	
}
