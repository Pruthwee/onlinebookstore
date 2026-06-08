package com.bittercode.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.bittercode.model.SessionCart;
import com.bittercode.model.UserRole;

/*
 * Store UTil File To Store Commonly used methods
 */
public class StoreUtil {

    private static final String SESSION_CART_KEY = "sessionCart";

    /**
     * Check if the User is logged in with the requested role
     */
    public static boolean isLoggedIn(UserRole role, HttpServletRequest request) {
        return SessionStateManager.isLoggedIn(request, role.toString());
    }

    /**
     * Modify the active tab in the page menu bar
     */
    public static void setActiveTab(PrintWriter pw, String activeTab) {

        pw.println("<script>document.getElementById(activeTab).classList.remove(\"active\");activeTab=" + activeTab
                + "</script>");
        pw.println("<script>document.getElementById('" + activeTab + "').classList.add(\"active\");</script>");

    }

    /**
     * Add/Remove/Update Item in the cart using externalized session state.
     */
    public static void updateCartItems(HttpServletRequest req) {
        String selectedBookId = req.getParameter("selectedBookId");
        if (selectedBookId == null) {
            return;
        }

        SessionCart cart = getSessionCart(req);
        if (req.getParameter("addToCart") != null) {
            cart.addItem(selectedBookId);
        } else {
            cart.removeItem(selectedBookId);
        }
        SessionStateManager.setAttribute(req, SESSION_CART_KEY, cart);
    }

    public static SessionCart getSessionCart(HttpServletRequest req) {
        return SessionStateManager.getAttribute(req, SESSION_CART_KEY, SessionCart.class).orElseGet(SessionCart::new);
    }

    public static void clearCart(HttpServletRequest req) {
        SessionStateManager.removeAttribute(req, SESSION_CART_KEY);
        SessionStateManager.removeAttribute(req, "cartItems");
        SessionStateManager.removeAttribute(req, "amountToPay");
        SessionStateManager.removeAttribute(req, "selectedBookId");
    }
}
