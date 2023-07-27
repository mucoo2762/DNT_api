package kr.dnt.api.service.impl;

import kr.dnt.api.dto.UserDto;
import kr.dnt.api.entity.User;
import kr.dnt.api.exception.ApiMessageException;
import kr.dnt.api.repository.UserRepository;
import kr.dnt.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * -------------------------------------------------------------------------------------
 * ::::::'OO::::'OOO::::'OO:::'OO:'OO::::'OO:'OOOOOOOO:::'OOOOOOO::'OO::::'OO:'OO....OO:
 * :::::: OO:::'OO OO:::. OO:'OO:: OO::::.OO: OO.....OO:'OO.....OO: OO:::: OO: OOO...OO:
 * :::::: OO::'OO:..OO:::. OOOO::: OO::::.OO: OO::::.OO: OO::::.OO: OO:::: OO: OOOO..OO:
 * :::::: OO:'OO:::..OO:::. OO:::: OO::::.OO: OOOOOOOO:: OO::::.OO: OO:::: OO: OO.OO.OO:
 * OO:::: OO: OOOOOOOOO:::: OO:::: OO::::.OO: OO.. OO::: OO::::.OO: OO:::: OO: OO..OOOO:
 * :OO::::OO: OO.....OO:::: OO:::: OO::::.OO: OO::. OO:: OO::::.OO: OO:::: OO: OO:..OOO:
 * ::OOOOOO:: OO:::..OO:::: OO::::. OOOOOOO:: OO:::. OO:. OOOOOOO::. OOOOOOO:: OO::..OO:
 * :......:::..:::::..:::::..::::::.......:::..:::::..:::.......::::.......:::..::::..::
 * <p>
 * packageName    : kr.dnt.api.service.impl
 * fileName       : UserServiceImpl
 * author         : dmsruf
 * date           : 2023/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/11        dmsruf       최초 생성
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Override
    public UserDto save(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = modelMapper.map(userDto, User.class);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findBySeq(Long seq) {
        User user = userRepository.findById(seq).orElseThrow(() -> new ApiMessageException("데이터를 찾을 수 없습니다."));
        if (user == null) {
            return null;
        } else {
            return modelMapper.map(user, UserDto.class);
        }
    }

    @Override
    public Page<UserDto> findList(int page, int limit, String name, String email, String security_key, String client_id) {
        return userRepository.findList(PageRequest.of(page, limit), name, email, security_key, client_id);
    }

    @Override
    public void deleteBySeq(Long seq) throws Exception {
        userRepository.deleteById(seq);
    }
}
