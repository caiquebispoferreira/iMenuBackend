package ca.ibs.imenu.controller;

import ca.ibs.imenu.entity.Order;
import ca.ibs.imenu.entity.OrderItem;
import ca.ibs.imenu.entity.User;
import ca.ibs.imenu.service.OrderService;
import ca.ibs.imenu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/commitSaveOrder", method = RequestMethod.POST)
    public String commitSaveOrder(Model model, Order order) {
        orderService.save(order);
        return "redirect:listOrder";
    }

    @RequestMapping(value = "/commitDeleteOrder", method = RequestMethod.POST)
    public String commitDeleteOrder(Model model, Order order) {
        orderService.delete(orderService.findById(order.getId()));
        return "redirect:listOrder";
    }

    @RequestMapping(value = "/listOrder", method = RequestMethod.GET)
    public String listOrder(Model model) {
        model.addAttribute("body","orders.jsp");
        model.addAttribute("object",orderService.findAll());
        model.addAttribute("title", "List of Orders");
        return "adminTemplate";
    }

    @RequestMapping(value = "/listOrderByTable", method = RequestMethod.GET)
    public String listOrderByTable(Model model, int tableNumber) {
        model.addAttribute("body","order.jsp");
        model.addAttribute("object",orderService.findByTableNumber(tableNumber));
        model.addAttribute("title", "List Order by Table");
        return "adminTemplate";
    }

    @RequestMapping(value = "/changeTableNumber", method = RequestMethod.POST)
    public String changeTableNumber(Model model, int newTableNumber, Order order) {
        model.addAttribute("body","order.jsp");
        model.addAttribute("object",orderService.changeTableNumber(order,newTableNumber));
        model.addAttribute("title", "Change table number");
        return "adminTemplate";
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.GET)
    public String addOrder(Model model) {
        model.addAttribute("body","order.jsp");
        model.addAttribute("object",new Order());
        model.addAttribute("action","/commitSaveOrder");
        model.addAttribute("title", "Add Order");
        model.addAttribute("readonly", false);
        return "adminTemplate";
    }

    @RequestMapping(value = "/editOrder", method = RequestMethod.GET)
    public String editOrder(Model model, @RequestParam(name = "id") Long id) {
        model.addAttribute("body","order.jsp");
        model.addAttribute("object",orderService.findById(id));
        model.addAttribute("action","/commitSaveOrder");
        model.addAttribute("title", "Edit Order");
        model.addAttribute("readonly", false);
        return "adminTemplate";
    }

    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    public String deleteOrder(Model model, @RequestParam(name = "id") Long id) {
        model.addAttribute("body","order.jsp");
        model.addAttribute("object",orderService.findById(id));
        model.addAttribute("action","/commitDeleteOrder");
        model.addAttribute("title", "Delete Order");
        model.addAttribute("readonly", true);
        return "adminTemplate";
    }

    @RequestMapping(value = "/myOrder", method = RequestMethod.GET)
    public String myOrder(Model model, int tableNumber){
        model.addAttribute("object",orderService.findByStatusAndTableNumber(tableNumber));
        return "myOrder";
    }

    @RequestMapping(value= "/addItemToOrder", method = RequestMethod.GET)
    public String addItemToOrder(Long productId, int quantity, int tableNumber){
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(productService.findById(productId));
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(orderItem.getProduct().getUnitPrice());
        orderItem.setTotalPrice(orderItem.getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity())));

        Order order = orderService.findByStatusAndTableNumber(tableNumber);
        if (order==null) {
            order = new Order();
            order.setDate(new Date());
            order.setTableNumber(tableNumber);
            order.setNote("");

        }
        order.addItem(orderItem);
        orderService.save(order);
        return "redirect:ListCart?tableNumber=1";
    }
}
