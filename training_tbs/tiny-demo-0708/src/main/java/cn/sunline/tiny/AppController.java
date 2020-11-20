package cn.sunline.tiny;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sunline.tiny.controller.BaseController;


@Controller
public class AppController extends BaseController {

	@RequestMapping("/{tranCode}.tml")
	public void index(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}
}
