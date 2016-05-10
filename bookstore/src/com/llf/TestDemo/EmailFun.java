package com.llf.TestDemo;

import org.junit.Test;

import java.text.MessageFormat;

public class EmailFun {
//	@Test
//	public void fun() throws Exception{
//		Session session = MailUtils.createSession("smtp.sina.cn","15659124912", "876331806");//得到session
//		Mail mail = new Mail("15659124912@sina.cn","15659124912@163.com","收到邮件没！","什么鬼");//创建邮件对象		
//			
//				MailUtils.send(session, mail);
//			//发邮件！		
//	}

    @Test
    public void fun1() {
        /*
         * 包含了点位符的字符串就是模板！
		 * 点位符：{0}、{1}、{2}
		 * 可变参数，需要指定模板中的点位符的值！有几个点位符就要提供几个参数
		 */

        String s = MessageFormat.format("{0}或{1}错误！", "用户名", "密码");
        System.out.println(s);
    }


    /**
     * https://www.yeepay.com/app-merchant-proxy/node?p0_Cmd=Buy&p1_MerId=10001126856&p2_Order=123456&p3_Amt=10&p4_Cur=CNY&p5_Pid=&p6_Pcat=&p7_Pdesc=&p8_Url=http://localhost:8080/bookstore/OrderServlet?method=back&p9_SAF=&pa_MP=&pd_FrpId=ICBC-NET-B2C&pr_NeedResponse=1&hmac=7d8bf573417839e36a151bec660d50c4
     */
    @Test
    public void fun5() {
        String hmac = PaymentUtil.buildHmac("Buy", "10001126856", "123456", "10", "CNY",
                "", "", "", "http://localhost:8080/bookstore/OrderServlet?method=back",
                "", "", "ICBC-NET-B2C", "1", "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
        System.out.println(hmac);
    }
}
