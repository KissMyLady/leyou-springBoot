package top.mylady.cart.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.mylady.utils.pojos.cart.Cart;
import top.mylady.cart.service.CartService;
import top.mylady.utils.dtos.ResponseResult;

import java.util.List;


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

    /**
     * 增加购物车
     */
    @PostMapping("/add")
    public ResponseResult addCart(@RequestBody Cart cart){
        return cartService.addCart(cart);
    }

    /**
     * 查询购物车
     */
    @GetMapping("/query")
    public ResponseEntity<List<Cart>> queryCartList(){
        return this.cartService.queryCart();
    }

    /**
     * 修改购物车数量
     */
    @GetMapping("/update")
    public ResponseResult updateCart(@RequestBody Cart cart){
        return this.cartService.updateCart(cart);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{skuId}")
    public ResponseResult deleteCart(@PathVariable("skuId") String skuId){
        return this.cartService.deleteCart(skuId);
    }
}
