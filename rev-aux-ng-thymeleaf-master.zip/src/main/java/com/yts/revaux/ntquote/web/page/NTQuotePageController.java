package com.yts.revaux.ntquote.web.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NTQuotePageController {

    private static final Logger LOG = LoggerFactory.getLogger(NTQuotePageController.class);

    @GetMapping("/ntquoteui/ntquote")
    public String getNtQuoteDatatable() {
    	System.out.println("getNtQuoteDatatable");
//       return "ntquoteui/ntquote";	
    	return "ntquoteui/index";
    }

    @GetMapping("/login2")
    public String getLogin() {
        // Return the Thymeleaf template name (without extension)

        LOG.info("***** Test Change after 133 ******");
        System.out.println("getLogin");
        return "login";
    }

    @GetMapping("/ntquoteui")
    public String getIndex() {
        // Return the Thymeleaf template name (without extension)
        return "index";
    }
    
    @GetMapping("/hello")
    public String getHello() {
        // Return the Thymeleaf template name (without extension)
    	return "ntquoteui/index";
    }
    
    
}
