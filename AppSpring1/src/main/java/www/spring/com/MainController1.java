package www.spring.com;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import model.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController1 {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController1.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	@RequestMapping(value="/sayfa2", method = RequestMethod.GET)
	public String page2(Model model2){
		String hello = "Hello My Dear!";
		String [] names = {"kancer","yavuz","ali"};
		
		model2.addAttribute("selam",hello);
		model2.addAttribute("dizinIsimler",names);
		
		return "yeniSayfa";
	}
	
	@RequestMapping(value = "/sayfa3", method = RequestMethod.GET)
	public String page3(Model model3){
		//String nasilsin = "How are you?";
		String [] feelings = {"iyiyim","kotuyum","orta sekerli"}; // bunlardan birini şecebilecek sekilde kodu guncelle
		
		model3.addAttribute("hisler",feelings);
		
		return "yeniSayfa2";
	}
	@RequestMapping(value = "/sayfa4", method = RequestMethod.GET)
	public String page4(@RequestParam(required= false) String ad,@RequestParam(required= false) String soyad,@RequestParam(required= false, defaultValue="0") Integer yas, Model model4){
	
//@RequestMapping(value = "/sayfa4/{ad}/{soyad}/{yas}", method = RequestMethod.GET)
// @PathVariable String ad, @PathVariable String soyad, @PathVariable Integer yas
		
		Student s =new Student();
		s.setName("kancer");
		s.setSurname("Gokirmak");
		s.setAge(24);
		s.setAddress("Atasehir");
		s.setDepartment("Computer Engineer");
		s.addLessons("matematik");
		s.addLessons("Bilg. Muh. Giris");
		s.addLessons("Fizik");
		s.addLessons("Yazilim Muh.");
		s.addFriends("Hayri");
		s.addFriends("Merve");
		s.addFriends("Ayse");
		s.addFriends("Sakir");
		
		if(ad != null || soyad != null){
			s.setName(ad);
			s.setSurname(soyad);
			s.setAge(yas);
		}
		
		model4.addAttribute("student_features", s);
		return "yeniSayfa3";
	}
	
	// POST ORNEGI xml ile
	
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)  // value degeri degisse asagiyi degistirmek gerekir mi?
	public ModelAndView student(){
		return new ModelAndView("student", "command", new Student());
	}
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String addStudent(@ModelAttribute Student student, ModelMap model){
		
		model.addAttribute("name", student.getName());
		model.addAttribute("surname", student.getSurname());
		model.addAttribute("age", student.getAge());
	      
	      return "result";
	}
	
	//POST Ornegi json ile
	
	@RequestMapping(value="/studentJson")
	public ResponseEntity<Student> get(){
		Student st =new Student();
		
		st.setName("kancer2");
		st.setSurname("gokirmak2");
		st.setAge(77);
		
		return new ResponseEntity<Student>(st, HttpStatus.OK);
	}
	@RequestMapping(value="/studentJson2", method = RequestMethod.POST)
	public ResponseEntity<Student> update(@RequestBody Student stu){
		
		if(stu != null){
			stu.setAge(stu.getAge()+34);
		}
		return new ResponseEntity<Student>(stu, HttpStatus.OK);
	}
}
