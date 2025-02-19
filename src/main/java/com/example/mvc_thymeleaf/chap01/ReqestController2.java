package com.example.mvc_thymeleaf.chap01;

import org.springframework.ui.Model;
import com.example.mvc_thymeleaf.chap01.domain.Human;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("regres")
public class ReqestController2 {

    // ㅣlocalhost:8080/regres/login-form
    // regres 파일 안에 있는 loginform.html과 연결
    @RequestMapping("/login-form")
    public String goLoginForm(){
        return"regres/loginform";
        }

        // @RequestMapping 애너테이션의 value(기본지정)은 주소, method는 허용메서드
//    @RequestMapping(value = "login", method= RequestMethod.POST)
    @PostMapping("login")
    public String checkLoginReq( // id, pw라는 변수에 담겨오는 값 처리
           @RequestParam(value = "id", defaultValue = "아이디없음") String id,
           @RequestParam(value = "pw" ,defaultValue = "비번없음") String pw,
            Model model){
        System.out.println("로그인 요청 아이디 : " + id);
        System.out.println("로그인 요청 비번 : " + pw);
        // 자바 내부에 들어온 데이터를 loginresult로 보내기 위해서는
        // addAtribute("보낼이름", "자료")가 피룡
        model.addAttribute("uid", id);
        model.addAttribute("upw", pw);// id 변수에 든 값을 "uid"라는 이름으로
        return "regres/loginresult";
    }

    // regres/human

    @GetMapping("human")//get방식만 허용하는 컨트롤러
    public String showHuman(Human human, Model model){ //화면으로 자료 보내줌
        System.out.println(human);
        model.addAttribute("human", human);
        return "regres/human-result";
    }

    // human-form 주소로 접속했을 때 해당하는 데이터들을
    // 화면에 보여줄 수 있는 jsp 파일 작성
    // 해당 주소로 접속하면 form 페이지가 나오고 여기서 제출버튼을 누르면
    // 커맨드 객체 human이 해당 페이지에서 보낸 자료를 받는다.

    @GetMapping("human-form")
    public String goHumanForm(Human human){
        System.out.println(human);
        return "regres/human-form";
    }


    @GetMapping("book/{page}")
    public String digitalBook(@PathVariable("page") int page, Model model){
        model.addAttribute("page", page);
        System.out.println("200");
        return "regres/digital-book";
    }

    @GetMapping("naver")
    public String goNaver(){
        // 네이버로 강제 이동시키기
        return "redirect:https://www.naver.com";
    }

    //서울여대 홈페이지로 리다이렉트 되는 엔드포인트 설정
    @GetMapping("swu")
    public String goSWU(Model model){
        return "redirect:http://www.swu.ac.kr/index.do";
    }



}
