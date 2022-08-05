package sg.edu.nus.iss.D15.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/",""})
public class GreetingsController {
    
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String,String> redisTemplate;

    // May include RequestBody if receive form message and display to website
    @GetMapping
    public String getGreetings(Model model) {
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        // Allows user to set value on redis and be displayed on website
        Object greet = ops.get("greetings");
        model.addAttribute("message", greet);
        return "index";
    }

    @PostMapping
    public String postGreetings(@RequestBody MultiValueMap<String,String> form, Model model) {
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        String greet = form.getFirst("greet");
        ops.set("greetings", greet);
        model.addAttribute("message", greet);
        return "index";
    }
}
