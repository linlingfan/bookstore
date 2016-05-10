package com.fjnu.bookstore.book.web.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.fjnu.bookstore.book.service.BookService;

public class BookServlet extends BaseServlet {
	
	private BookService bookService=new BookService();
	
	/**
	 * 查询所有的图书功能模块！
	 * 
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			request.setAttribute("bookList",bookService.findAll() );
				return "f:/jsps/book/list.jsp";			
			
	}
	/**
	 * 按图书分类查询！
	 */
	public String findByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String cid=request.getParameter("cid");
			request.setAttribute("bookList",bookService.findByCategory(cid) );
				return "f:/jsps/book/list.jsp";			
			
	}

	/**
	 * 加载某本图书的具体信息load
	 */

	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String bid=request.getParameter("bid");
			request.setAttribute("book",bookService.findBybid(bid));
			//测试有没查询到数据
//			String s=bookService.findBybid(bid).toString();
//			System.out.println(s);
				return "f:/jsps/book/desc.jsp";	
	}	
}
