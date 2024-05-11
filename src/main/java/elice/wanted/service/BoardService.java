package elice.wanted.service;

import elice.wanted.dto.BoardResponseDto;
import elice.wanted.dto.BoardSaveDto;
import elice.wanted.dto.BoardUpdateDto;
import elice.wanted.entity.Board;
import elice.wanted.entity.Member;
import elice.wanted.entity.Post;
import elice.wanted.exception.NoSuchBoardException;
import elice.wanted.repository.BoardRepository;
import elice.wanted.repository.MemberRepository;
import elice.wanted.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board save(BoardSaveDto boardSaveDto) throws IOException {
        Board board = new Board();

        board.setSubject(boardSaveDto.getSubject());
        Member member = memberRepository.findById(boardSaveDto.getMemberSessionInfo().getId()).get();
        board.setMember(member);
        Board savedBoard = boardRepository.save(board);
        return savedBoard;
    }

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new NoSuchBoardException("해당 게시판이 존재하지 않습니다."));
    }

    public BoardResponseDto edit(BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardUpdateDto.getId()).orElseThrow(() -> new NoSuchBoardException("해당 게시판이 존재하지 않습니다."));
        board.setSubject(boardUpdateDto.getSubject());
        Board edited = boardRepository.save(board);
        log.info(edited.getSubject());
        return edited.toResponseDto();
    }

    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }


//    public List<Post> findByBoardAndTitle(Long boardId, String title) {
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchBoardException("해당 게시판이 존재하지 않습니다."));
//        return postRepository.findByBoardAndTitle(board, title);
//    }
}
