package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.UserDataBeans;
import dao.BuyDAO;
import dao.UserDAO;

/**
 * ユーザー情報画面
 *
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserData")
public class UserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッション開始
		HttpSession session = request.getSession();

		try {
			 Object status = session.getAttribute("userId");

			    if (status == null){
			    	response.sendRedirect("Login");
			    	return;
			    }

			// ログイン時に取得したユーザーIDをセッションから取得
			int userId = (int) session.getAttribute("userId");
			System.out.println(userId);



			// 更新確認画面から戻ってきた場合Sessionから取得。それ以外はuserIdでユーザーを取得
			UserDataBeans udb = session.getAttribute("returnUDB") == null ? UserDAO.getUserDataBeansByUserId(userId) : (UserDataBeans) EcHelper.cutSessionAttribute(session, "returnUDB");

		//	System.out.println(udb.getId());
			ArrayList<BuyDataBeans> bdbList = BuyDAO.getBuyDataBeansByUserId(udb.getId());
			//System.out.println("要素数"+bdbList.size());

			//for(BuyDataBeans bdb : bdbList) {
				//System.out.println(bdb.getId());
				//System.out.println(bdb.getTotalPrice());
			//}


//			ArrayList<BuyDetailDataBeans> bddbList = BuyDetailDAO.getBuyDataBeansListByBuyId(userId);
//			for(BuyDetailDataBeans bddb : bddbList) {
//				System.out.println(bddb.getBuyId()+"buyId");
//				System.out.println(bddb.getItemId()+"ItemId");
//			}



			// 入力された内容に誤りがあったとき等に表示するエラーメッセージを格納する
			String validationMessage = (String) EcHelper.cutSessionAttribute(session, "validationMessage");


			request.setAttribute("validationMessage", validationMessage);
			request.setAttribute("udb", udb);
			request.setAttribute("bdbList", bdbList);
			request.getRequestDispatcher(EcHelper.USER_DATA_PAGE).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

}
