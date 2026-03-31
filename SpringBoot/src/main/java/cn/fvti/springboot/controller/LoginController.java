package cn.fvti.springboot.controller;

import cn.fvti.springboot.entity.User;
import cn.fvti.springboot.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("测试接口被调用");
        return new ResponseEntity<>("测试接口调用成功", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        System.out.println("登录请求：用户名=" + username + ", 密码=" + password);

        User user = userService.login(username, password);
        System.out.println("登录验证结果：user=" + user);

        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            // 生成token
            String token = generateToken(user.getUsername());
            System.out.println("生成token：" + token);

            // 构建响应数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            // 根据用户名生成路由
            Map<String, Object> router = new HashMap<>();
            if (username != null && username.equals("admin")) {
                router.put("path", "/admin");
                router.put("name", "admin");
            }
            data.put("router", router);

            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("data", data);

            response.put("data", result);
            System.out.println("登录成功，返回响应：" + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
            result.put("data", null);

            response.put("data", result);
            System.out.println("登录失败，返回响应：" + response);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        System.out.println("注册请求：用户名=" + username + ", 密码=" + password);

        Map<String, Object> response = new HashMap<>();

        // 验证参数
        if (username == null || username.trim().isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 400);
            result.put("message", "用户名不能为空");
            result.put("data", null);
            response.put("data", result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (password == null || password.trim().isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 400);
            result.put("message", "密码不能为空");
            result.put("data", null);
            response.put("data", result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 检查用户名是否已存在
        if (userService.isUsernameExists(username)) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 400);
            result.put("message", "用户名已存在");
            result.put("data", null);
            response.put("data", result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 注册用户
        User user = userService.register(username, password);
        if (user != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "注册成功");
            result.put("data", null);
            response.put("data", result);
            System.out.println("注册成功，返回响应：" + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 500);
            result.put("message", "注册失败，请稍后重试");
            result.put("data", null);
            response.put("data", result);
            System.out.println("注册失败，返回响应：" + response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
