package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class AppController {
	@Autowired
	
	private ProductService service;
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Product> listProducts =service.listAll();
		model.addAttribute("listProducts", listProducts);
		
		return "index";
	}
	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Product product =new Product();
		model.addAttribute("product",product);
		return "new_product";
	}
	

	@RequestMapping(value="/save",method =RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product")Product product) {
		service.save(product);
		return "redirect:/";
	}
	

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name="id")Long id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product= service.get(id);
		mav.addObject("product",product);
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name ="id")Long id) {
		service.delete(id);
		return "redirect:/";
	}
	
	$(document).on("click","#btnLogin",function(){
		$("#divStsMsgInsert").html("");
		var result = isValidFormInsert();
		
		if(result == "true"){
			$.ajax(
			{
				type:"post",
				url : "new_product",
				data : $("#formInsert").serialize(),
				complete : function(response,status){
					onLoginComplete(response.responseText,status);
				}
			});		
			
		}
		else{
			$("#disStsMsgInsert").html(result);
		}
		});
	
	function onInsertComplete(response,status){
		if(status == "success")
			{
			if(response = "SUCCESS"){
				document.location = "new_product.jsp";
			}
			else{
				$("#divStsMsgInsert").html(response);
			}
			
			}
		else if(status == "error"){
			$("#divStsMsgInsert").html("Error while authenticating");
			
		}
		else{
			$("#divStsMsgInsert").html("unknown error while authenticating");
			
		}
	}
	
	$(document).con("click", ""#btnRefresh", function){
			$("#divStsMsgProduct").html("Loading...........");
		
			$.ajax(
			{
					type : "get",
					url : "New_product",
					complete : function(response,status){
						
						onRefreshComplete(response.responseText,status);
					}
						
						
			});
	});
	
	function onRefreshComplete(response,status)
	{
		if(status == "success"){
			$("#divproductTable").html("Loaded successfully");
		}
		elseif(status == "success"){
			$("#divproductTable").html(response);
			$("#divStsMsgProduct".html("Loaded successfully");
			
		}
		else if(status = "error"){
			$("#divStsMsgProduct").html("Error while authenticating");
			
		}
		else{
			$("#divStsMsgLogin").html("unknown error while authenticating");
		}
		}
	 

}
