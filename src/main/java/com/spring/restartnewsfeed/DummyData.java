package com.spring.restartnewsfeed;

import com.spring.restartnewsfeed.common.aop.TrackTime;
import com.spring.restartnewsfeed.user.domain.User;
import com.spring.restartnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @TrackTime
    public void run(String... args) throws Exception {

//        List<String> secondName = Arrays.asList("하늘색", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
//                "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
//                "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
//                "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
//                "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용"
//        );
//
//        List<String> firstName = Arrays.asList("사과", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
//                "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
//                "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
//                "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
//                "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
//                "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
//                "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
//                "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
//                "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
//                "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련"
//        );
//
//
//        List<User> userList = new ArrayList<>();
//
//        for (int i = 0; i < 1000000; i++) {
//            Collections.shuffle(secondName);
//            Collections.shuffle(firstName);
//
//            String email = "xxxx" + i + "@naver.com";
//
//            String name = secondName.get(0) + firstName.get(0) + firstName.get(1);
//
//            User user = new User(name, email);
//
//            userList.add(user);
//        }
//
//        userRepository.saveAll(userList);
//

        List<User> userList = new ArrayList<>();

        Faker faker = new Faker();

        for (int i = 0; i < 1000000; i++) {

            String userName = faker.name().lastName();

            String emailName = faker.name().firstName();

            int age = faker.number().numberBetween(10, 60);

            String email = String.format("%s@test.com", emailName);

            log.info(userName + ", " + email);

            User user = new User(userName, email, age);

            userList.add(user);

        }
        batchInsert(1000, userList);
//        userRepository.saveAll(userList);
    }

    // 한번에 삽입할 사용자의 수와 , 객체 리스트
    private void batchInsert(int batchSize, List<User> users) {
        // totalSize = user 리스트의 총 크키
        int totalSize = users.size();
        // int i는 0, i가 전체 사이즈보다 클 수 없음, i에 배치사이즈 수만큼 더해주기
        for (int i = 0; i < totalSize; i += batchSize) {
            //i랑 totalSize 중에 작은걸 골라서 end 에 넣기
            //i + batchSize 의 크기가 totalSize 를 넘을 수 없음.
            int end = Math.min(i + batchSize, totalSize);
            //batchList 는 유저리스트의 i부터 end 까지
            List<User> batchList = users.subList(i, end);

            jdbcTemplate.batchUpdate("INSERT INTO USER (NAME, EMAIL, AGE, IS_DELETED) VALUES (?, ?, ?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, users.get(i).getName());
                            ps.setString(2, users.get(i).getEmail());
                            ps.setInt(3, users.get(i).getAge());
                            ps.setBoolean(4, false);
                        }

                        //한번에 처리할 데이터의 개수
                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    }
            );
        }

    }
}
