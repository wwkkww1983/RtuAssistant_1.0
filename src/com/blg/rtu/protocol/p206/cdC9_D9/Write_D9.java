package com.blg.rtu.protocol.p206.cdC9_D9;

import java.util.HashMap;

import com.blg.rtu.protocol.p206.common.*;
import com.blg.rtu.protocol.p206.util.*;

public class Write_D9 extends ProtocolSupport{
	private static final int len = Constant.Bits_Head 
									+ Constant.Bits_Control
									+ Constant.Bits_RTU_ID 
									+ Constant.Bits_Code 
									+ Constant.Bits_Password 
									+ Constant.Bits_Time 
									+ Constant.Bits_CRC
									+ Constant.Bits_Tail 
									+ 1 ;//数据域长度
	/**
	 * 构造RTU 命令
	 * @param code 功能码
	 * @param controlFunCode 控制域功能码
	 * @param rtuId 终端ID
	 * @param params 参数
	 * @param password 密码
	 * @return
	 * @throws Exception
	 */
	public byte[] create(String code, byte controlFunCode, String rtuId, HashMap<String , Object> params, String password) throws Exception {
		Param_D9 param = (Param_D9)params.get(Param_D9.KEY) ;
		if(param == null ){
			throw new Exception("出错，未提供参数Bean！") ;
		}
		/////////////////////////////
		//构造数据
		byte[] b = new byte[len];

		int fromSite = this.createDownDataHead(rtuId, code, b, len, controlFunCode) ;

		Integer c = param.getInterval_0to10();
		
		b[fromSite] = c.byteValue();
		
		this.createDownDataTail(b, password) ;
		
		return b;
	}


}
