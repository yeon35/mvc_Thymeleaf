package com.example.mvc_thymeleaf.controller;

import com.example.mvc_thymeleaf.domain.Board;
import com.example.mvc_thymeleaf.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("board")
@AllArgsConstructor
public class BoardController {

    // 컨트롤러는 원래 서비스를 호출해야 하지만, 편의상 먼저 레포지토리를 호출

    private final BoardRepository boardRepository; // 빈 컨테이너에 자동 주입된다.

    // 게시판 전체 글 목록을 조회할 수 있는 페이지
    // http://localhost:8080/board/list
    @GetMapping("list")
    public String goBoardList(Model model){
        // 레포지토리의 데이터를 컨트롤러 내부로 가져오기
        List<Board> boardList = boardRepository.getBoardList();
        System.out.println("컨트로러가 가져온 전체 글 정보 : " + boardList);
        // 가져온 데이터는 화면으로 보내주기
        model.addAttribute("boardList", boardList);
        return "board/board-list";
    }


    @GetMapping("write")
    public String goBoardForm(){

        return "board/board-form";

    }

    @PostMapping("board-write")
    public String saveBoard(Board board){
        // 파라미터로 선언한 Board타입 변수 board의 필드에 매칭
        System.out.println("시간 지정 전 : " + board);
        // board에 이어서 현재 시간을 저장
        board.setCreatedAt(LocalDateTime.now());
        System.out.println("시간 지정 후 : " + board);
        // 글에 대한 정보를 적재하고 있는 board변수를 db에 저장
        boardRepository.save(board);
        // 글 저장 후에 전체목록으로 다시 돌아가게 만들기
        return "redirect:/board/list";
    }

    @GetMapping("detail/{boardNum}")
    public String goDetailPage(@PathVariable int boardNum, Model model){
        // 가져온 글 번호에 해당하는 게시물 하나의 정보를 받는다.
        Board board = boardRepository.findBoardByBoardNum(boardNum);
        // 해당 게시물 정보를 addAtribute()로 화면단에 전송
        model.addAttribute("board", board);
        // 화면단을 출력할 .html파일 명칭 지정
        // 해당 .jsp파일에 ${}를 이용해 글 하나의 정보를 정리되지 않아도 좋으니 출력
        return "/board/board-detail";
    }

    // 삭제 로직 생성
    @PostMapping("delete") //$boardNum
    public String deleteBoard(int boardNum){
        boardRepository.deleteBoardByBoardNum(boardNum);
        //리스트 페이지 복귀
        return "redirect:/board/list";
    }

    // 주소창에 직접 기입은 get
    // form형태로 들어가면 post방식
}
