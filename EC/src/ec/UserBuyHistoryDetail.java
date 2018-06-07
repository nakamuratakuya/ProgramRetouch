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
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {


		    Object status = session.getAttribute("userId");

		    if (status == null){
		    	response.sendRedirect("Login");
		    	return;
		    }

			int buyid = Integer.parseInt(request.getParameter("buy_id"));
			System.out.println(buyid);

			BuyDataBeans bdb =BuyDAO.getBuyDataBeansByBuyId(buyid);


		//ArrayList<BuyDataBeans> bdbList = BuyDAO.getBuyDataBeansByUserId(buyid);
		//for(BuyDataBeans bdb :bdbList) {
			//System.out.println(bdb.getTotalPrice());

		//}
		// = new BuyDataBeans();

		//bdb.setUserId(bdbList);
		//bdb.setTotalPrice(totalPrice);
		//bdb.setDelivertMethodId(userSelectDMB.getId());
		//bdb.setDeliveryMethodPrice(userSelectDMB.getPrice());
		//bdb.setDeliveryMethodName(userSelectDMB.getName());

	//	for(BuyDataBeans bdb : bdbList ) {
		//	System.out.println(bdb.getId());
		//}

		ArrayList<ItemDataBeans> buyDetailItemList = BuyDetailDAO.getItemDataBeansListByBuyId(buyid);
		//for(ItemDataBeans idb :buyDetailItemList) {
			//System.out.println(idb.getName());
		//}

		request.setAttribute("buyDatailItemList",buyDetailItemList);
		request.setAttribute("bdb", bdb);
		request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);
		}catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMessage", e.toString());
				response.sendRedirect("Error");

		}
	}


}
