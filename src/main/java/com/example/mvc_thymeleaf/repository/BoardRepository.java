package com.example.mvc_thymeleaf.repository;

import com.example.mvc_thymeleaf.domain.Board;

import java.util.List;

public interface BoardRepository {

    // 전체 목록을 가져오는 메서드 정의

    public List<Board> getBoardList();

    // 글 번호를 제시하면 해당하는 번호인 글 하나만 가져오는 메서드 정의
    public Board findBoardByBoardNum(int boardNum);

    // 글 번호를 제시하면 자료가 삭제되는 메서드 정의
    public boolean deleteBoardByBoardNum(int boardNum);

    // 글 전체에 대한 정보를 주면 데이터를 추가로 적재해주는 메서드 정의

    public boolean save(Board board);
}
