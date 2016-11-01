package com.blg.rtu.protocol.p206.cdE0;

import android.util.Log;

import com.blg.rtu.protocol.RtuData;
import com.blg.rtu.protocol.p206.common.ControlProtocol;
import com.blg.rtu.protocol.p206.common.ProtocolSupport;
import com.blg.rtu.util.ByteUtilUnsigned;

public class Answer_E0 extends ProtocolSupport{

	private static String tag = Answer_E0.class.getName() ;

	/**
	 * 解析上行数据
	 * @param rtuId
	 * @param b
	 * @param cp
	 * @param dataCode
	 * @return
	 * @throws Exception
	 */
	public RtuData parse(String rtuId, byte[] b, ControlProtocol cp, String dataCode) throws Exception {
		RtuData d = new RtuData() ;
		int index = this.parseUpDataHead(rtuId, b, cp, dataCode, d);
		this.doParse(b, index, d, cp) ;

		Log.i(tag, "分析<RTU 供电方式及电压>应答: RTU ID=" + rtuId + " 数据：" + d.getSubData().toString());
		return d;
	}
	private RtuData doParse(byte[] b, int index, RtuData d, ControlProtocol cp) throws Exception {
		Data_E0 subD = new Data_E0() ;
		d.setSubData(subD) ;
		
		// 分析数据域
		subD.setType((int)b[index++]) ;
		
		int v = ByteUtilUnsigned.bytes2Short_an(b, index) ;
		subD.setVoltage((float)(v/100.0)) ;
		
		return d;
	}
	
}
