package com.example.mvc_thymeleaf.repository;

import com.example.mvc_thymeleaf.domain.Board;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // 빈 컨테이너에 레포지토리 클래스로 등록
public class BoardMeomoryRepository implements BoardRepository{ // 컨테이너에 들어가 있는 형태

    // 현재 연결된 db가 없기 때문에, db 대신에 메모리에 로딩데이터를 저장
    // 해당 저장 데이터는 map 형식으로 저장할 예정

    private static final Map<Integer, Board> boardMap;
    // 글번호에 사용할 일련번호

    private static int sequence; //글번호를 체크해주는 변수, 0으로 자동초기화

    // 정적변수 boardMap을 초기화해 줄 블록

    static{
        boardMap = new HashMap<>();

        System.out.println("데이터 적재 전 : " + boardMap);

        Board board1 = new Board(++sequence, "서울여대", "안녕", LocalDateTime.now());
        Board board2 = new Board(++sequence, "KDT", "수업", LocalDateTime.now());
        Board board3 = new Board(++sequence, "이선희", "인연", LocalDateTime.now());

        boardMap.put(board1.getBoardNum(), board1);
        boardMap.put(board2.getBoardNum(), board2);
        boardMap.put(board3.getBoardNum(), board3);
        System.out.println("데이터 적재 후 : " + boardMap);

    }

    @Override
    public List<Board> getBoardList() {
        // Map의 values() 이용해서 게시물 전체 정보를 얻어옵니다.
        // 단, 이해가 안 간다면 sout에 찍어보세용

        // 빈 ArrayList 생성
        List<Board> resultList = new ArrayList<>();

        // 반복문 이용해서 resultList에 Board 객체 정리해서 집어넣기
        for(Board board : boardMap.values()) {
            resultList.add(board);
        }
        System.out.println("resultList 적재 후 : " + resultList);

        return resultList;
    }

    @Override
    public Board findBoardByBoardNum(int boardNum) {
        return boardMap.get(boardNum);
    }

    @Override
    public boolean deleteBoardByBoardNum(int boardNum) {

        // 만약 존재하지 않는 키값을 집어넣는다면
        if(!boardMap.containsKey(boardNum)) return false;
        boardMap.remove(boardNum);
        return true;
    }

    @Override
    public boolean save(Board board) {
        // 이미 존재하는 글 번호를 저장하는 경우는 저장 실패
        if(boardMap.containsKey(board.getBoardNum())) return false;

        // 사용된 적 없는 글 번호를 활용해 신규 저장
        board.setBoardNum(++sequence);
        boardMap.put(board.getBoardNum(), board);
        return true;
    }
}
