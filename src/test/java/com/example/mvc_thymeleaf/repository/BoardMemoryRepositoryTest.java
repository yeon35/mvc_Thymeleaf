package com.example.mvc_thymeleaf.repository;

// 테스트 코드를 작성하는 클래스 상단에는 @SpringBootTest 애너테이션을 붙여준다.

import com.example.mvc_thymeleaf.domain.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class BoardMemoryRepositoryTest {

    // 테스트할 클래스를 아래에 선언학, 의존성 주입을 위해 @Autowired 애너테이션을
    // 위 쪽에 붙여줍니다.

    @Autowired
    private BoardRepository boardRepository;

    // 해당 클래스의 특정 기능을 호출하기 위해서는
    // @Test애너테이션을 위에 붙인 메서드를 하단에 선언
    // 마치 이 메서드가 메인메서드처럼 동작해서 검증을 돕습니다.

    @Test
    // 테스트 결과 창에서 보여질 멘트를 지정하는 애너테이션
    @DisplayName("전체 글을 가져와서 첫 번째 글의 글쓴이를 조회하면 서울여대일 것이다.")
    public void getBoardListTest(){

        // given : when절에서 활용할 테스트 코드에 대한 사전 준비
        // getBoardList는 제공해야 하는 파라미터나 사전 조건이 없다.

        // when : 실제로 기능을 돌려서 수행시키는 단계
        List<Board> boardList = boardRepository.getBoardList();

        // then : when절에서 돌린 기능이 의도대로 돌았는지 검증하는 부분
        // 보통 단언문인 assertEquals 등으로 검증.
        // assertEquals(); alt + enter를 누르고
        // import static methods 클릭 후 jupiter... 를 입력
        // expect와 actual 2개의 데이터를 입력

        // when절에서 얻어온 boardList의 첫 자료의 writer는 "서울여대" 일 것이라고 단언한다.
        assertEquals("KDT",boardList.get(0).getWriter());
        assertEquals("서울여대학원",boardList.get(0).getWriter());
    }

    @Test
    public void findBoardByBoardIdTest(){
        // given : 사전준비
        // 대부분의 코드에서 매직넘버를 직접 쓰지 않는 것이 좋습니다
        // findBoardByBoardNum() 호출 시 int자료를 하나 넘겨야 하므로
        // given절에서 해당 자료를 미리 선언
        final int BOARD_NUM = 2; // 2번글에 대한 조회를 할 예정

        // when : 실제 기능 호출
        Board board = boardRepository.findBoardByBoardNum(BOARD_NUM);

        // then : when절 결과에 대한 단언
        assertEquals("수업 진행중임!", board.getContent());
        assertEquals("KDT", board.getWriter());
    }

    // 테스트코드 양식

    @Test
    public void deleteBoardByBoardNum(){
        // given : 삭제할 글을 아래에 변수로 저장
        final int BOARD_NUM = 2; // 2번글에 대한 조회를 할 예정

        // when : 삭제 후에 전체 데이터의 개수를 .size()로 조회해 변수에 저장
        //        그 다음, 이미 삭제된 글 번호로 또 삭제를 수행하고 결과를 변수에 저장
        boardRepository.deleteBoardByBoardNum(BOARD_NUM);
        int boardListSize = boardRepository.getBoardList().size();
        boolean deleteBool = boardRepository.deleteBoardByBoardNum(BOARD_NUM);

        // then : 단언문에서 데이터 개수를 단언해주시고, boolean 결과도 단언
        assertEquals(2, boardListSize);
        assertEquals(false, deleteBool);

    }

    // save 로직에 대해서 테스트 코드 작성

    @Test
    @DisplayName("4번 글을 저장한 다음, 4번 글을 얻어오면 입력한 정보가 조회")
    public void saveTest(){
        // given

        // 글번호를 제외한 나머지를 미리 변수에 저장
        final String WRITER = "인프라 개발자";
        final LocalDateTime NOW = LocalDateTime.now();
        final String CONTENT = "퍼블릭클라우드 하고싶다~~~~";
        final int BOARD_NUM =4;

        // boardNum은 어차피 save 도중 ++sequence에 의해 보정되므로 0으로 기입
        Board board = new Board(BOARD_NUM, WRITER, CONTENT, NOW); //순서가 관련이 있음?

        // when : 구현해놓은 .save()가 실제로 동작하는지 검증
        // 저장로직을 돌린 다음, 실제로 저장되었는지 확인을 해야함
        // 개수는 4개, 4번글을 가져왔을 때 위에 given에 정의한 데이터가 그대로 나와야 함
        boardRepository.save(board);
        Board result = boardRepository.findBoardByBoardNum(BOARD_NUM);
        int boardListSize = boardRepository.getBoardList().size();

        //then
        // 개수는 4개, 4번글을 가져왔을 때 위에 given에 정의한 데이터가 그대로 나와야 함.
        assertEquals(4, boardListSize);
        assertEquals(WRITER, result.getWriter());
        assertEquals(CONTENT, result.getContent());
    }

}
