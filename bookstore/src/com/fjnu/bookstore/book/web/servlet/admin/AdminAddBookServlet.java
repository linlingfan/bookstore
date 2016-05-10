package com.fjnu.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import com.fjnu.bookstore.book.domain.Book;
import com.fjnu.bookstore.book.service.BookService;
import com.fjnu.bookstore.category.domain.Category;
import com.fjnu.bookstore.category.service.CategoryService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/3/22.
 */
@WebServlet("/AdminAddBookServlet")
/**
 * 添加图书
 */
public class AdminAddBookServlet extends HttpServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService=new CategoryService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /*
		 * 1. 把表单数据封装到Book对象中
		 *   * 上传三步
		 */
        // 创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory(40 * 1024, new File("D:/photos"));
        // 得到解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //设置文件大小为40kb
        sfu.setFileSizeMax(40 * 1024);
        // 使用解析器去解析request对象，得到List<FileItem>

        try {

            List<FileItem> fileItemList = sfu.parseRequest(request);
             /*
			 * * 把fileItemList中的数据封装到Book对象中
			 *   > 把所有的普通表单字段数据先封装到Map中
			 *   > 再把map中的数据封装到Book对象中
			 */
            Map<String, String> map = new HashMap<String, String>();
            for (FileItem fileItem : fileItemList) {
                if (fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
                }
            }

            Book book = CommonUtils.toBean(map, Book.class);
            // 为book指定bid
            book.setBid(CommonUtils.uuid());

            /*
			 * 需要把Map中的cid封装到Category对象中，再把Category赋给Book
			 */
            Category category = CommonUtils.toBean(map, Category.class);
            book.setCategory(category);

            /*
			 * 2. 保存上传的文件
			 *   * 保存的目录
			 *   * 保存的文件名称
			 */
            // 得到保存的目录
            String savepath = this.getServletContext().getRealPath("/book_img");

            // 得到文件名称：给原来文件名称添加uuid前缀！避免文件名冲突
            String filename = CommonUtils.uuid() + "_" + fileItemList.get(1).getName();

            /*
			 * 校验文件的扩展名
			 */
            if (filename.toLowerCase().endsWith("jgp")) {
                request.setAttribute("msg", "你上传的图片不是jgp扩展名！");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
                return;
            }

            // 使用目录和文件名称创建目标文件
            File destFile = new File(savepath, filename);
            // 保存上传文件到目标文件位置
            fileItemList.get(1).write(destFile);

            /*
			 * 3. 设置Book对象的image，即把图片的路径设置给Book的image
			 */
            book.setImage("book_img/" + filename);

			/*
			 * 4. 使用BookService完成保存
			 */
            bookService.add(book);

            /*
			 * 校验图片的尺寸
			 */
            Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
            if (image.getWidth(null) > 600 || image.getHeight(null) > 600) {
                destFile.delete();//删除这个文件！
                request.setAttribute("msg", "您上传的图片尺寸超出了600 * 600！");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                        .forward(request, response);
                return;
            }


			/*
			 * 5. 返回到图书列表
			 */
            request.getRequestDispatcher("/AdminBookServlet?method=findAll")
                    .forward(request, response);


        } catch (Exception e) {
            if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
                request.setAttribute("msg", "您上传的文件超出了15KB");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                        .forward(request, response);
            }

        }
    }
}
