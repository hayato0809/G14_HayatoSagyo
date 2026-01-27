//package servlet;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import dao.KitchenDAO;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/kitchen/completed/clear")
//public class KitchenCompletedClearServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        try {
//            new KitchenDAO().hideAllCompleted();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        response.sendRedirect(request.getContextPath() + "/kitchen/completed");
//    }
//}
