package top.mylady.cart.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mylady.utils.pojos.cart.Cart;
import top.mylady.cart.service.CartService;
import top.mylady.utils.dtos.ResponseResult;


/**
 * 购物车路由层
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/ok")
    public ResponseResult test(){
        return ResponseResult.okResult("ok");
    }

    @PostMapping("/add")
    public ResponseResult addCart(@RequestBody Cart cart){
        return cartService.addCart(cart);
    }
}
