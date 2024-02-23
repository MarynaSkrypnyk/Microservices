package cashbackservice.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import cashbackservice.model.Cashback;
import cashbackservice.service.CashbackService;

import java.io.IOException;

@WebServlet("/cashback")
public class CashbackServlet extends HttpServlet {
    private final CashbackService cashbackService = new CashbackService();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productCashback = req.getParameter("productCashback");
        double price = cashbackService.getSum(productCashback);

        Cashback cashback = new Cashback(price);
        String jsonString = mapper.writeValueAsString(cashback);
        resp.setContentType("application/json");
        resp.getWriter().println(jsonString);
    }
}
